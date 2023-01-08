package net.minecraft.src;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CanvasIsomPreview extends Canvas implements KeyListener, MouseListener, MouseMotionListener, Runnable {
	private int field_1793_a = 0;
	private int zoomLevel = 2;
	private boolean displayHelpText = true;
	private World worldObj;
	private File dataFolder = this.getMinecraftDir();
	private boolean running = true;
	private List imageBufferList = Collections.synchronizedList(new LinkedList());
	private IsoImageBuffer[][] imageBuffers = new IsoImageBuffer[64][64];
	private int field_1785_i;
	private int field_1784_j;
	private int xPosition;
	private int yPosition;

	public File getMinecraftDir() {
		if(this.dataFolder == null) {
			this.dataFolder = this.getAppDir("minecraft");
		}

		return this.dataFolder;
	}

	public File getAppDir(String string1) {
		String string2 = System.getProperty("user.home", ".");
		File file3;
		switch(OsMap.field_1193_a[getOs().ordinal()]) {
		case 1:
		case 2:
			file3 = new File(string2, '.' + string1 + '/');
			break;
		case 3:
			String string4 = System.getenv("APPDATA");
			if(string4 != null) {
				file3 = new File(string4, "." + string1 + '/');
			} else {
				file3 = new File(string2, '.' + string1 + '/');
			}
			break;
		case 4:
			file3 = new File(string2, "Library/Application Support/" + string1);
			break;
		default:
			file3 = new File(string2, string1 + '/');
		}

		if(!file3.exists() && !file3.mkdirs()) {
			throw new RuntimeException("The working directory could not be created: " + file3);
		} else {
			return file3;
		}
	}

	private static EnumOS1 getOs() {
		String string0 = System.getProperty("os.name").toLowerCase();
		return string0.contains("win") ? EnumOS1.windows : (string0.contains("mac") ? EnumOS1.macos : (string0.contains("solaris") ? EnumOS1.solaris : (string0.contains("sunos") ? EnumOS1.solaris : (string0.contains("linux") ? EnumOS1.linux : (string0.contains("unix") ? EnumOS1.linux : EnumOS1.unknown)))));
	}

	public CanvasIsomPreview() {
		for(int i1 = 0; i1 < 64; ++i1) {
			for(int i2 = 0; i2 < 64; ++i2) {
				this.imageBuffers[i1][i2] = new IsoImageBuffer((World)null, i1, i2);
			}
		}

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus();
		this.setBackground(Color.red);
	}

	public void loadWorld(String string1) {
		this.field_1785_i = this.field_1784_j = 0;
		this.worldObj = new World(new SaveHandler(new File(this.dataFolder, "saves"), string1, false), string1, (new Random()).nextLong());
		this.worldObj.skylightSubtracted = 0;
		List list2 = this.imageBufferList;
		synchronized(this.imageBufferList) {
			this.imageBufferList.clear();

			for(int i3 = 0; i3 < 64; ++i3) {
				for(int i4 = 0; i4 < 64; ++i4) {
					this.imageBuffers[i3][i4].func_888_a(this.worldObj, i3, i4);
				}
			}

		}
	}

	private void setTimeOfDay(int i1) {
		List list2 = this.imageBufferList;
		synchronized(this.imageBufferList) {
			this.worldObj.skylightSubtracted = i1;
			this.imageBufferList.clear();

			for(int i3 = 0; i3 < 64; ++i3) {
				for(int i4 = 0; i4 < 64; ++i4) {
					this.imageBuffers[i3][i4].func_888_a(this.worldObj, i3, i4);
				}
			}

		}
	}

	public void func_1272_b() {
		(new ThreadRunIsoClient(this)).start();

		for(int i1 = 0; i1 < 8; ++i1) {
			(new Thread(this)).start();
		}

	}

	public void exit() {
		this.running = false;
	}

	private IsoImageBuffer getImageBuffer(int i1, int i2) {
		int i3 = i1 & 63;
		int i4 = i2 & 63;
		IsoImageBuffer isoImageBuffer5 = this.imageBuffers[i3][i4];
		if(isoImageBuffer5.field_1354_c == i1 && isoImageBuffer5.field_1353_d == i2) {
			return isoImageBuffer5;
		} else {
			List list6 = this.imageBufferList;
			synchronized(this.imageBufferList) {
				this.imageBufferList.remove(isoImageBuffer5);
			}

			isoImageBuffer5.func_889_a(i1, i2);
			return isoImageBuffer5;
		}
	}

	public void run() {
		TerrainTextureManager terrainTextureManager1 = new TerrainTextureManager();

		while(this.running) {
			IsoImageBuffer isoImageBuffer2 = null;
			List list3 = this.imageBufferList;
			synchronized(this.imageBufferList) {
				if(this.imageBufferList.size() > 0) {
					isoImageBuffer2 = (IsoImageBuffer)this.imageBufferList.remove(0);
				}
			}

			if(isoImageBuffer2 != null) {
				if(this.field_1793_a - isoImageBuffer2.field_1350_g < 2) {
					terrainTextureManager1.func_799_a(isoImageBuffer2);
					this.repaint();
				} else {
					isoImageBuffer2.field_1349_h = false;
				}
			}

			try {
				Thread.sleep(2L);
			} catch (InterruptedException interruptedException5) {
				interruptedException5.printStackTrace();
			}
		}

	}

	public void update(Graphics graphics1) {
	}

	public void paint(Graphics graphics1) {
	}

	public void showNextBuffer() {
		BufferStrategy bufferStrategy1 = this.getBufferStrategy();
		if(bufferStrategy1 == null) {
			this.createBufferStrategy(2);
		} else {
			this.drawScreen((Graphics2D)bufferStrategy1.getDrawGraphics());
			bufferStrategy1.show();
		}
	}

	public void drawScreen(Graphics2D graphics2D1) {
		++this.field_1793_a;
		AffineTransform affineTransform2 = graphics2D1.getTransform();
		graphics2D1.setClip(0, 0, this.getWidth(), this.getHeight());
		graphics2D1.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		graphics2D1.translate(this.getWidth() / 2, this.getHeight() / 2);
		graphics2D1.scale((double)this.zoomLevel, (double)this.zoomLevel);
		graphics2D1.translate(this.field_1785_i, this.field_1784_j);
		if(this.worldObj != null) {
			ChunkCoordinates chunkCoordinates3 = this.worldObj.getSpawnPoint();
			graphics2D1.translate(-(chunkCoordinates3.x + chunkCoordinates3.z), -(-chunkCoordinates3.x + chunkCoordinates3.z) + 64);
		}

		Rectangle rectangle17 = graphics2D1.getClipBounds();
		graphics2D1.setColor(new Color(-15724512));
		graphics2D1.fillRect(rectangle17.x, rectangle17.y, rectangle17.width, rectangle17.height);
		byte b4 = 16;
		byte b5 = 3;
		int i6 = rectangle17.x / b4 / 2 - 2 - b5;
		int i7 = (rectangle17.x + rectangle17.width) / b4 / 2 + 1 + b5;
		int i8 = rectangle17.y / b4 - 1 - b5 * 2;
		int i9 = (rectangle17.y + rectangle17.height + 16 + 128) / b4 + 1 + b5 * 2;

		int i10;
		for(i10 = i8; i10 <= i9; ++i10) {
			for(int i11 = i6; i11 <= i7; ++i11) {
				int i12 = i11 - (i10 >> 1);
				int i13 = i11 + (i10 + 1 >> 1);
				IsoImageBuffer isoImageBuffer14 = this.getImageBuffer(i12, i13);
				isoImageBuffer14.field_1350_g = this.field_1793_a;
				if(!isoImageBuffer14.field_1352_e) {
					if(!isoImageBuffer14.field_1349_h) {
						isoImageBuffer14.field_1349_h = true;
						this.imageBufferList.add(isoImageBuffer14);
					}
				} else {
					isoImageBuffer14.field_1349_h = false;
					if(!isoImageBuffer14.field_1351_f) {
						int i15 = i11 * b4 * 2 + (i10 & 1) * b4;
						int i16 = i10 * b4 - 128 - 16;
						graphics2D1.drawImage(isoImageBuffer14.field_1348_a, i15, i16, (ImageObserver)null);
					}
				}
			}
		}

		if(this.displayHelpText) {
			graphics2D1.setTransform(affineTransform2);
			i10 = this.getHeight() - 32 - 4;
			graphics2D1.setColor(new Color(Integer.MIN_VALUE, true));
			graphics2D1.fillRect(4, this.getHeight() - 32 - 4, this.getWidth() - 8, 32);
			graphics2D1.setColor(Color.WHITE);
			String string18 = "F1 - F5: load levels   |   0-9: Set time of day   |   Space: return to spawn   |   Double click: zoom   |   Escape: hide this text";
			graphics2D1.drawString(string18, this.getWidth() / 2 - graphics2D1.getFontMetrics().stringWidth(string18) / 2, i10 + 20);
		}

		graphics2D1.dispose();
	}

	public void mouseDragged(MouseEvent mouseEvent1) {
		int i2 = mouseEvent1.getX() / this.zoomLevel;
		int i3 = mouseEvent1.getY() / this.zoomLevel;
		this.field_1785_i += i2 - this.xPosition;
		this.field_1784_j += i3 - this.yPosition;
		this.xPosition = i2;
		this.yPosition = i3;
		this.repaint();
	}

	public void mouseMoved(MouseEvent mouseEvent1) {
	}

	public void mouseClicked(MouseEvent mouseEvent1) {
		if(mouseEvent1.getClickCount() == 2) {
			this.zoomLevel = 3 - this.zoomLevel;
			this.repaint();
		}

	}

	public void mouseEntered(MouseEvent mouseEvent1) {
	}

	public void mouseExited(MouseEvent mouseEvent1) {
	}

	public void mousePressed(MouseEvent mouseEvent1) {
		int i2 = mouseEvent1.getX() / this.zoomLevel;
		int i3 = mouseEvent1.getY() / this.zoomLevel;
		this.xPosition = i2;
		this.yPosition = i3;
	}

	public void mouseReleased(MouseEvent mouseEvent1) {
	}

	public void keyPressed(KeyEvent keyEvent1) {
		if(keyEvent1.getKeyCode() == 48) {
			this.setTimeOfDay(11);
		}

		if(keyEvent1.getKeyCode() == 49) {
			this.setTimeOfDay(10);
		}

		if(keyEvent1.getKeyCode() == 50) {
			this.setTimeOfDay(9);
		}

		if(keyEvent1.getKeyCode() == 51) {
			this.setTimeOfDay(7);
		}

		if(keyEvent1.getKeyCode() == 52) {
			this.setTimeOfDay(6);
		}

		if(keyEvent1.getKeyCode() == 53) {
			this.setTimeOfDay(5);
		}

		if(keyEvent1.getKeyCode() == 54) {
			this.setTimeOfDay(3);
		}

		if(keyEvent1.getKeyCode() == 55) {
			this.setTimeOfDay(2);
		}

		if(keyEvent1.getKeyCode() == 56) {
			this.setTimeOfDay(1);
		}

		if(keyEvent1.getKeyCode() == 57) {
			this.setTimeOfDay(0);
		}

		if(keyEvent1.getKeyCode() == 112) {
			this.loadWorld("World1");
		}

		if(keyEvent1.getKeyCode() == 113) {
			this.loadWorld("World2");
		}

		if(keyEvent1.getKeyCode() == 114) {
			this.loadWorld("World3");
		}

		if(keyEvent1.getKeyCode() == 115) {
			this.loadWorld("World4");
		}

		if(keyEvent1.getKeyCode() == 116) {
			this.loadWorld("World5");
		}

		if(keyEvent1.getKeyCode() == 32) {
			this.field_1785_i = this.field_1784_j = 0;
		}

		if(keyEvent1.getKeyCode() == 27) {
			this.displayHelpText = !this.displayHelpText;
		}

		this.repaint();
	}

	public void keyReleased(KeyEvent keyEvent1) {
	}

	public void keyTyped(KeyEvent keyEvent1) {
	}

	static boolean isRunning(CanvasIsomPreview canvasIsomPreview0) {
		return canvasIsomPreview0.running;
	}
}
