package net.minecraft.client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.io.File;

import net.minecraft.src.AchievementList;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkProviderLoadOrGenerate;
import net.minecraft.src.ColorizerFoliage;
import net.minecraft.src.ColorizerGrass;
import net.minecraft.src.ColorizerWater;
import net.minecraft.src.EffectRenderer;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.EnumOS2;
import net.minecraft.src.EnumOSMappingHelper;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GameWindowListener;
import net.minecraft.src.GuiAchievement;
import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiConflictWarning;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiErrorScreen;
import net.minecraft.src.GuiGameOver;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSleepMP;
import net.minecraft.src.GuiUnused;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.LoadingScreenRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MinecraftError;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.MinecraftImpl;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.MouseHelper;
import net.minecraft.src.MovementInputFromOptions;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.OpenGlCapsChecker;
import net.minecraft.src.PlayerController;
import net.minecraft.src.PlayerControllerTest;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.SaveConverterMcRegion;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.ScreenShotHelper;
import net.minecraft.src.Session;
import net.minecraft.src.SoundManager;
import net.minecraft.src.StatFileWriter;
import net.minecraft.src.StatList;
import net.minecraft.src.StatStringFormatKeyInv;
import net.minecraft.src.Teleporter;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TextureCompassFX;
import net.minecraft.src.TextureFlamesFX;
import net.minecraft.src.TextureLavaFX;
import net.minecraft.src.TextureLavaFlowFX;
import net.minecraft.src.TexturePackList;
import net.minecraft.src.TexturePortalFX;
import net.minecraft.src.TextureWatchFX;
import net.minecraft.src.TextureWaterFX;
import net.minecraft.src.TextureWaterFlowFX;
import net.minecraft.src.ThreadCheckHasPaid;
import net.minecraft.src.ThreadDownloadResources;
import net.minecraft.src.ThreadSleepForever;
import net.minecraft.src.Timer;
import net.minecraft.src.UnexpectedThrowable;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldRenderer;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public abstract class Minecraft implements Runnable {
	public static byte[] field_28006_b = new byte[10485760];
	private static Minecraft theMinecraft;
	public PlayerController playerController;
	private boolean fullscreen = false;
	private boolean hasCrashed = false;
	public int displayWidth;
	public int displayHeight;
	private OpenGlCapsChecker glCapabilities;
	private Timer timer = new Timer(20.0F);
	public World theWorld;
	public RenderGlobal renderGlobal;
	public EntityPlayerSP thePlayer;
	public EntityLiving renderViewEntity;
	public EffectRenderer effectRenderer;
	public Session session = null;
	public String minecraftUri;
	public Canvas mcCanvas;
	public boolean hideQuitButton = true;
	public volatile boolean isGamePaused = false;
	public RenderEngine renderEngine;
	public FontRenderer fontRenderer;
	public GuiScreen currentScreen = null;
	public LoadingScreenRenderer loadingScreen = new LoadingScreenRenderer(this);
	public EntityRenderer entityRenderer;
	private ThreadDownloadResources downloadResourcesThread;
	private int ticksRan = 0;
	private int leftClickCounter = 0;
	private int tempDisplayWidth;
	private int tempDisplayHeight;
	public GuiAchievement guiAchievement = new GuiAchievement(this);
	public GuiIngame ingameGUI;
	public boolean skipRenderWorld = false;
	public ModelBiped field_9242_w = new ModelBiped(0.0F);
	public MovingObjectPosition objectMouseOver = null;
	public GameSettings gameSettings;
	protected MinecraftApplet mcApplet;
	public SoundManager sndManager = new SoundManager();
	public MouseHelper mouseHelper;
	public TexturePackList texturePackList;
	private File mcDataDir;
	private ISaveFormat saveLoader;
	public static long[] frameTimes = new long[512];
	public static long[] tickTimes = new long[512];
	public static int numRecordedFrameTimes = 0;
	public static long hasPaidCheckTime = 0L;
	public StatFileWriter statFileWriter;
	private String serverName;
	private int serverPort;
	private TextureWaterFX textureWaterFX = new TextureWaterFX();
	private TextureLavaFX textureLavaFX = new TextureLavaFX();
	private static File minecraftDir = null;
	public volatile boolean running = true;
	public String debug = "";
	boolean isTakingScreenshot = false;
	long prevFrameTime = -1L;
	public boolean inGameHasFocus = false;
	private int mouseTicksRan = 0;
	public boolean isRaining = false;
	long systemTime = System.currentTimeMillis();
	private int joinPlayerCounter = 0;

	public Minecraft(Component component1, Canvas canvas2, MinecraftApplet minecraftApplet3, int i4, int i5, boolean z6) {
		StatList.func_27360_a();
		this.tempDisplayHeight = i5;
		this.fullscreen = z6;
		this.mcApplet = minecraftApplet3;
		new ThreadSleepForever(this, "Timer hack thread");
		this.mcCanvas = canvas2;
		this.displayWidth = i4;
		this.displayHeight = i5;
		this.fullscreen = z6;
		if(minecraftApplet3 == null || "true".equals(minecraftApplet3.getParameter("stand-alone"))) {
			this.hideQuitButton = false;
		}

		theMinecraft = this;
	}

	public void onMinecraftCrash(UnexpectedThrowable unexpectedThrowable1) {
		this.hasCrashed = true;
		this.displayUnexpectedThrowable(unexpectedThrowable1);
	}

	public abstract void displayUnexpectedThrowable(UnexpectedThrowable unexpectedThrowable1);

	public void setServer(String string1, int i2) {
		this.serverName = string1;
		this.serverPort = i2;
	}

	public void startGame() throws LWJGLException {
		if(this.mcCanvas != null) {
			Graphics graphics1 = this.mcCanvas.getGraphics();
			if(graphics1 != null) {
				graphics1.setColor(Color.BLACK);
				graphics1.fillRect(0, 0, this.displayWidth, this.displayHeight);
				graphics1.dispose();
			}

			Display.setParent(this.mcCanvas);
		} else if(this.fullscreen) {
			Display.setFullscreen(true);
			this.displayWidth = Display.getDisplayMode().getWidth();
			this.displayHeight = Display.getDisplayMode().getHeight();
			if(this.displayWidth <= 0) {
				this.displayWidth = 1;
			}

			if(this.displayHeight <= 0) {
				this.displayHeight = 1;
			}
		} else {
			Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
		}

		Display.setTitle("Minecraft Minecraft Beta 1.7.3");

		try {
			Display.create();
		} catch (LWJGLException lWJGLException6) {
			lWJGLException6.printStackTrace();

			try {
				Thread.sleep(1000L);
			} catch (InterruptedException interruptedException5) {
			}

			Display.create();
		}

		this.mcDataDir = getMinecraftDir();
		this.saveLoader = new SaveConverterMcRegion(new File(this.mcDataDir, "saves"));
		this.gameSettings = new GameSettings(this, this.mcDataDir);
		this.texturePackList = new TexturePackList(this, this.mcDataDir);
		this.renderEngine = new RenderEngine(this.texturePackList, this.gameSettings);
		this.fontRenderer = new FontRenderer(this.gameSettings, "/font/default.png", this.renderEngine);
		ColorizerWater.func_28182_a(this.renderEngine.func_28149_a("/misc/watercolor.png"));
		ColorizerGrass.func_28181_a(this.renderEngine.func_28149_a("/misc/grasscolor.png"));
		ColorizerFoliage.func_28152_a(this.renderEngine.func_28149_a("/misc/foliagecolor.png"));
		this.entityRenderer = new EntityRenderer(this);
		RenderManager.instance.itemRenderer = new ItemRenderer(this);
		this.statFileWriter = new StatFileWriter(this.session, this.mcDataDir);
		AchievementList.openInventory.setStatStringFormatter(new StatStringFormatKeyInv(this));
		this.loadScreen();
		Keyboard.create();
		Mouse.create();
		this.mouseHelper = new MouseHelper(this.mcCanvas);

		try {
			Controllers.create();
		} catch (Exception exception4) {
			exception4.printStackTrace();
		}

		this.checkGLError("Pre startup");
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearDepth(1.0D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		this.checkGLError("Startup");
		this.glCapabilities = new OpenGlCapsChecker();
		this.sndManager.loadSoundSettings(this.gameSettings);
		this.renderEngine.registerTextureFX(this.textureLavaFX);
		this.renderEngine.registerTextureFX(this.textureWaterFX);
		this.renderEngine.registerTextureFX(new TexturePortalFX());
		this.renderEngine.registerTextureFX(new TextureCompassFX(this));
		this.renderEngine.registerTextureFX(new TextureWatchFX(this));
		this.renderEngine.registerTextureFX(new TextureWaterFlowFX());
		this.renderEngine.registerTextureFX(new TextureLavaFlowFX());
		this.renderEngine.registerTextureFX(new TextureFlamesFX(0));
		this.renderEngine.registerTextureFX(new TextureFlamesFX(1));
		this.renderGlobal = new RenderGlobal(this, this.renderEngine);
		GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);
		this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);

		try {
			this.downloadResourcesThread = new ThreadDownloadResources(this.mcDataDir, this);
			this.downloadResourcesThread.start();
		} catch (Exception exception3) {
		}

		this.checkGLError("Post startup");
		this.ingameGUI = new GuiIngame(this);
		if(this.serverName != null) {
			this.displayGuiScreen(new GuiConnecting(this, this.serverName, this.serverPort));
		} else {
			this.displayGuiScreen(new GuiMainMenu());
		}

	}

	private void loadScreen() throws LWJGLException {
		ScaledResolution scaledResolution1 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, scaledResolution1.field_25121_a, scaledResolution1.field_25120_b, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
		GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);
		GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		Tessellator tessellator2 = Tessellator.instance;
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/title/mojang.png"));
		tessellator2.startDrawingQuads();
		tessellator2.setColorOpaque_I(0xFFFFFF);
		tessellator2.addVertexWithUV(0.0D, (double)this.displayHeight, 0.0D, 0.0D, 0.0D);
		tessellator2.addVertexWithUV((double)this.displayWidth, (double)this.displayHeight, 0.0D, 0.0D, 0.0D);
		tessellator2.addVertexWithUV((double)this.displayWidth, 0.0D, 0.0D, 0.0D, 0.0D);
		tessellator2.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		tessellator2.draw();
		short s3 = 256;
		short s4 = 256;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		tessellator2.setColorOpaque_I(0xFFFFFF);
		this.func_6274_a((scaledResolution1.getScaledWidth() - s3) / 2, (scaledResolution1.getScaledHeight() - s4) / 2, 0, 0, s3, s4);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		Display.swapBuffers();
	}

	public void func_6274_a(int i1, int i2, int i3, int i4, int i5, int i6) {
		float f7 = 0.00390625F;
		float f8 = 0.00390625F;
		Tessellator tessellator9 = Tessellator.instance;
		tessellator9.startDrawingQuads();
		tessellator9.addVertexWithUV((double)(i1 + 0), (double)(i2 + i6), 0.0D, (double)((float)(i3 + 0) * f7), (double)((float)(i4 + i6) * f8));
		tessellator9.addVertexWithUV((double)(i1 + i5), (double)(i2 + i6), 0.0D, (double)((float)(i3 + i5) * f7), (double)((float)(i4 + i6) * f8));
		tessellator9.addVertexWithUV((double)(i1 + i5), (double)(i2 + 0), 0.0D, (double)((float)(i3 + i5) * f7), (double)((float)(i4 + 0) * f8));
		tessellator9.addVertexWithUV((double)(i1 + 0), (double)(i2 + 0), 0.0D, (double)((float)(i3 + 0) * f7), (double)((float)(i4 + 0) * f8));
		tessellator9.draw();
	}

	public static File getMinecraftDir() {
		if(minecraftDir == null) {
			minecraftDir = getAppDir("minecraft");
		}

		return minecraftDir;
	}

	public static File getAppDir(String string0) {
		String string1 = System.getProperty("user.home", ".");
		File file2;
		switch(EnumOSMappingHelper.enumOSMappingArray[getOs().ordinal()]) {
		case 1:
		case 2:
			file2 = new File(string1, '.' + string0 + '/');
			break;
		case 3:
			String string3 = System.getenv("APPDATA");
			if(string3 != null) {
				file2 = new File(string3, "." + string0 + '/');
			} else {
				file2 = new File(string1, '.' + string0 + '/');
			}
			break;
		case 4:
			file2 = new File(string1, "Library/Application Support/" + string0);
			break;
		default:
			file2 = new File(string1, string0 + '/');
		}

		if(!file2.exists() && !file2.mkdirs()) {
			throw new RuntimeException("The working directory could not be created: " + file2);
		} else {
			return file2;
		}
	}

	private static EnumOS2 getOs() {
		String string0 = System.getProperty("os.name").toLowerCase();
		return string0.contains("win") ? EnumOS2.windows : (string0.contains("mac") ? EnumOS2.macos : (string0.contains("solaris") ? EnumOS2.solaris : (string0.contains("sunos") ? EnumOS2.solaris : (string0.contains("linux") ? EnumOS2.linux : (string0.contains("unix") ? EnumOS2.linux : EnumOS2.unknown)))));
	}

	public ISaveFormat getSaveLoader() {
		return this.saveLoader;
	}

	public void displayGuiScreen(GuiScreen guiScreen1) {
		if(!(this.currentScreen instanceof GuiUnused)) {
			if(this.currentScreen != null) {
				this.currentScreen.onGuiClosed();
			}

			if(guiScreen1 instanceof GuiMainMenu) {
				this.statFileWriter.func_27175_b();
			}

			this.statFileWriter.syncStats();
			if(guiScreen1 == null && this.theWorld == null) {
				guiScreen1 = new GuiMainMenu();
			} else if(guiScreen1 == null && this.thePlayer.health <= 0) {
				guiScreen1 = new GuiGameOver();
			}

			if(guiScreen1 instanceof GuiMainMenu) {
				this.ingameGUI.clearChatMessages();
			}

			this.currentScreen = (GuiScreen)guiScreen1;
			if(guiScreen1 != null) {
				this.setIngameNotInFocus();
				ScaledResolution scaledResolution2 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
				int i3 = scaledResolution2.getScaledWidth();
				int i4 = scaledResolution2.getScaledHeight();
				((GuiScreen)guiScreen1).setWorldAndResolution(this, i3, i4);
				this.skipRenderWorld = false;
			} else {
				this.setIngameFocus();
			}

		}
	}

	private void checkGLError(String string1) {
		int i2 = GL11.glGetError();
		if(i2 != 0) {
			String string3 = GLU.gluErrorString(i2);
			System.out.println("########## GL ERROR ##########");
			System.out.println("@ " + string1);
			System.out.println(i2 + ": " + string3);
		}

	}

	public void shutdownMinecraftApplet() {
		try {
			this.statFileWriter.func_27175_b();
			this.statFileWriter.syncStats();
			if(this.mcApplet != null) {
				this.mcApplet.clearApplet();
			}

			try {
				if(this.downloadResourcesThread != null) {
					this.downloadResourcesThread.closeMinecraft();
				}
			} catch (Exception exception9) {
			}

			System.out.println("Stopping!");

			try {
				this.changeWorld1((World)null);
			} catch (Throwable throwable8) {
			}

			try {
				GLAllocation.deleteTexturesAndDisplayLists();
			} catch (Throwable throwable7) {
			}

			this.sndManager.closeMinecraft();
			Mouse.destroy();
			Keyboard.destroy();
		} finally {
			Display.destroy();
			if(!this.hasCrashed) {
				System.exit(0);
			}

		}

		System.gc();
	}

	public void run() {
		this.running = true;

		try {
			this.startGame();
		} catch (Exception exception17) {
			exception17.printStackTrace();
			this.onMinecraftCrash(new UnexpectedThrowable("Failed to start game", exception17));
			return;
		}

		try {
			long j1 = System.currentTimeMillis();
			int i3 = 0;

			while(this.running) {
				try {
					if(this.mcApplet != null && !this.mcApplet.isActive()) {
						break;
					}

					AxisAlignedBB.clearBoundingBoxPool();
					Vec3D.initialize();
					if(this.mcCanvas == null && Display.isCloseRequested()) {
						this.shutdown();
					}

					if(this.isGamePaused && this.theWorld != null) {
						float f4 = this.timer.renderPartialTicks;
						this.timer.updateTimer();
						this.timer.renderPartialTicks = f4;
					} else {
						this.timer.updateTimer();
					}

					long j23 = System.nanoTime();

					for(int i6 = 0; i6 < this.timer.elapsedTicks; ++i6) {
						++this.ticksRan;

						try {
							this.runTick();
						} catch (MinecraftException minecraftException16) {
							this.theWorld = null;
							this.changeWorld1((World)null);
							this.displayGuiScreen(new GuiConflictWarning());
						}
					}

					long j24 = System.nanoTime() - j23;
					this.checkGLError("Pre render");
					RenderBlocks.fancyGrass = this.gameSettings.fancyGraphics;
					this.sndManager.func_338_a(this.thePlayer, this.timer.renderPartialTicks);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					if(this.theWorld != null) {
						this.theWorld.updatingLighting();
					}

					if(!Keyboard.isKeyDown(Keyboard.KEY_F7)) {
						Display.update();
					}

					if(this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock()) {
						this.gameSettings.thirdPersonView = false;
					}

					if(!this.skipRenderWorld) {
						if(this.playerController != null) {
							this.playerController.setPartialTime(this.timer.renderPartialTicks);
						}

						this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks);
					}

					if(!Display.isActive()) {
						if(this.fullscreen) {
							this.toggleFullscreen();
						}

						Thread.sleep(10L);
					}

					if(this.gameSettings.showDebugInfo) {
						this.displayDebugInfo(j24);
					} else {
						this.prevFrameTime = System.nanoTime();
					}

					this.guiAchievement.updateAchievementWindow();
					Thread.yield();
					if(Keyboard.isKeyDown(Keyboard.KEY_F7)) {
						Display.update();
					}

					this.screenshotListener();
					if(this.mcCanvas != null && !this.fullscreen && (this.mcCanvas.getWidth() != this.displayWidth || this.mcCanvas.getHeight() != this.displayHeight)) {
						this.displayWidth = this.mcCanvas.getWidth();
						this.displayHeight = this.mcCanvas.getHeight();
						if(this.displayWidth <= 0) {
							this.displayWidth = 1;
						}

						if(this.displayHeight <= 0) {
							this.displayHeight = 1;
						}

						this.resize(this.displayWidth, this.displayHeight);
					}

					this.checkGLError("Post render");
					++i3;

					for(this.isGamePaused = !this.isMultiplayerWorld() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame(); System.currentTimeMillis() >= j1 + 1000L; i3 = 0) {
						this.debug = i3 + " fps, " + WorldRenderer.chunksUpdated + " chunk updates";
						WorldRenderer.chunksUpdated = 0;
						j1 += 1000L;
					}
				} catch (MinecraftException minecraftException18) {
					this.theWorld = null;
					this.changeWorld1((World)null);
					this.displayGuiScreen(new GuiConflictWarning());
				} catch (OutOfMemoryError outOfMemoryError19) {
					this.func_28002_e();
					this.displayGuiScreen(new GuiErrorScreen());
					System.gc();
				}
			}
		} catch (MinecraftError minecraftError20) {
		} catch (Throwable throwable21) {
			this.func_28002_e();
			throwable21.printStackTrace();
			this.onMinecraftCrash(new UnexpectedThrowable("Unexpected error", throwable21));
		} finally {
			this.shutdownMinecraftApplet();
		}

	}

	public void func_28002_e() {
		try {
			field_28006_b = new byte[0];
			this.renderGlobal.func_28137_f();
		} catch (Throwable throwable4) {
		}

		try {
			System.gc();
			AxisAlignedBB.func_28196_a();
			Vec3D.func_28215_a();
		} catch (Throwable throwable3) {
		}

		try {
			System.gc();
			this.changeWorld1((World)null);
		} catch (Throwable throwable2) {
		}

		System.gc();
	}

	private void screenshotListener() {
		if(Keyboard.isKeyDown(Keyboard.KEY_F2)) {
			if(!this.isTakingScreenshot) {
				this.isTakingScreenshot = true;
				this.ingameGUI.addChatMessage(ScreenShotHelper.saveScreenshot(minecraftDir, this.displayWidth, this.displayHeight));
			}
		} else {
			this.isTakingScreenshot = false;
		}

	}

	private void displayDebugInfo(long j1) {
		long j3 = 16666666L;
		if(this.prevFrameTime == -1L) {
			this.prevFrameTime = System.nanoTime();
		}

		long j5 = System.nanoTime();
		tickTimes[numRecordedFrameTimes & frameTimes.length - 1] = j1;
		frameTimes[numRecordedFrameTimes++ & frameTimes.length - 1] = j5 - this.prevFrameTime;
		this.prevFrameTime = j5;
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, (double)this.displayWidth, (double)this.displayHeight, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
		GL11.glLineWidth(1.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator tessellator7 = Tessellator.instance;
		tessellator7.startDrawing(7);
		int i8 = (int)(j3 / 200000L);
		tessellator7.setColorOpaque_I(536870912);
		tessellator7.addVertex(0.0D, (double)(this.displayHeight - i8), 0.0D);
		tessellator7.addVertex(0.0D, (double)this.displayHeight, 0.0D);
		tessellator7.addVertex((double)frameTimes.length, (double)this.displayHeight, 0.0D);
		tessellator7.addVertex((double)frameTimes.length, (double)(this.displayHeight - i8), 0.0D);
		tessellator7.setColorOpaque_I(0x20200000);
		tessellator7.addVertex(0.0D, (double)(this.displayHeight - i8 * 2), 0.0D);
		tessellator7.addVertex(0.0D, (double)(this.displayHeight - i8), 0.0D);
		tessellator7.addVertex((double)frameTimes.length, (double)(this.displayHeight - i8), 0.0D);
		tessellator7.addVertex((double)frameTimes.length, (double)(this.displayHeight - i8 * 2), 0.0D);
		tessellator7.draw();
		long j9 = 0L;

		int i11;
		for(i11 = 0; i11 < frameTimes.length; ++i11) {
			j9 += frameTimes[i11];
		}

		i11 = (int)(j9 / 200000L / (long)frameTimes.length);
		tessellator7.startDrawing(7);
		tessellator7.setColorOpaque_I(0x20400000);
		tessellator7.addVertex(0.0D, (double)(this.displayHeight - i11), 0.0D);
		tessellator7.addVertex(0.0D, (double)this.displayHeight, 0.0D);
		tessellator7.addVertex((double)frameTimes.length, (double)this.displayHeight, 0.0D);
		tessellator7.addVertex((double)frameTimes.length, (double)(this.displayHeight - i11), 0.0D);
		tessellator7.draw();
		tessellator7.startDrawing(1);

		for(int i12 = 0; i12 < frameTimes.length; ++i12) {
			int i13 = (i12 - numRecordedFrameTimes & frameTimes.length - 1) * 255 / frameTimes.length;
			int i14 = i13 * i13 / 255;
			i14 = i14 * i14 / 255;
			int i15 = i14 * i14 / 255;
			i15 = i15 * i15 / 255;
			if(frameTimes[i12] > j3) {
				tessellator7.setColorOpaque_I(0xFF000000 + i14 * 65536);
			} else {
				tessellator7.setColorOpaque_I(0xFF000000 + i14 * 256);
			}

			long j16 = frameTimes[i12] / 200000L;
			long j18 = tickTimes[i12] / 200000L;
			tessellator7.addVertex((double)((float)i12 + 0.5F), (double)((float)((long)this.displayHeight - j16) + 0.5F), 0.0D);
			tessellator7.addVertex((double)((float)i12 + 0.5F), (double)((float)this.displayHeight + 0.5F), 0.0D);
			tessellator7.setColorOpaque_I(0xFF000000 + i14 * 65536 + i14 * 256 + i14 * 1);
			tessellator7.addVertex((double)((float)i12 + 0.5F), (double)((float)((long)this.displayHeight - j16) + 0.5F), 0.0D);
			tessellator7.addVertex((double)((float)i12 + 0.5F), (double)((float)((long)this.displayHeight - (j16 - j18)) + 0.5F), 0.0D);
		}

		tessellator7.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void shutdown() {
		this.running = false;
	}

	public void setIngameFocus() {
		if(Display.isActive()) {
			if(!this.inGameHasFocus) {
				this.inGameHasFocus = true;
				this.mouseHelper.grabMouseCursor();
				this.displayGuiScreen((GuiScreen)null);
				this.leftClickCounter = 10000;
				this.mouseTicksRan = this.ticksRan + 10000;
			}
		}
	}

	public void setIngameNotInFocus() {
		if(this.inGameHasFocus) {
			if(this.thePlayer != null) {
				this.thePlayer.resetPlayerKeyState();
			}

			this.inGameHasFocus = false;
			this.mouseHelper.ungrabMouseCursor();
		}
	}

	public void displayInGameMenu() {
		if(this.currentScreen == null) {
			this.displayGuiScreen(new GuiIngameMenu());
		}
	}

	private void func_6254_a(int i1, boolean z2) {
		if(!this.playerController.field_1064_b) {
			if(!z2) {
				this.leftClickCounter = 0;
			}

			if(i1 != 0 || this.leftClickCounter <= 0) {
				if(z2 && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE && i1 == 0) {
					int i3 = this.objectMouseOver.blockX;
					int i4 = this.objectMouseOver.blockY;
					int i5 = this.objectMouseOver.blockZ;
					this.playerController.sendBlockRemoving(i3, i4, i5, this.objectMouseOver.sideHit);
					this.effectRenderer.addBlockHitEffects(i3, i4, i5, this.objectMouseOver.sideHit);
				} else {
					this.playerController.resetBlockRemoving();
				}

			}
		}
	}

	private void clickMouse(int i1) {
		if(i1 != 0 || this.leftClickCounter <= 0) {
			if(i1 == 0) {
				this.thePlayer.swingItem();
			}

			boolean z2 = true;
			if(this.objectMouseOver == null) {
				if(i1 == 0 && !(this.playerController instanceof PlayerControllerTest)) {
					this.leftClickCounter = 10;
				}
			} else if(this.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY) {
				if(i1 == 0) {
					this.playerController.attackEntity(this.thePlayer, this.objectMouseOver.entityHit);
				}

				if(i1 == 1) {
					this.playerController.interactWithEntity(this.thePlayer, this.objectMouseOver.entityHit);
				}
			} else if(this.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE) {
				int i3 = this.objectMouseOver.blockX;
				int i4 = this.objectMouseOver.blockY;
				int i5 = this.objectMouseOver.blockZ;
				int i6 = this.objectMouseOver.sideHit;
				if(i1 == 0) {
					this.playerController.clickBlock(i3, i4, i5, this.objectMouseOver.sideHit);
				} else {
					ItemStack itemStack7 = this.thePlayer.inventory.getCurrentItem();
					int i8 = itemStack7 != null ? itemStack7.stackSize : 0;
					if(this.playerController.sendPlaceBlock(this.thePlayer, this.theWorld, itemStack7, i3, i4, i5, i6)) {
						z2 = false;
						this.thePlayer.swingItem();
					}

					if(itemStack7 == null) {
						return;
					}

					if(itemStack7.stackSize == 0) {
						this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
					} else if(itemStack7.stackSize != i8) {
						this.entityRenderer.itemRenderer.func_9449_b();
					}
				}
			}

			if(z2 && i1 == 1) {
				ItemStack itemStack9 = this.thePlayer.inventory.getCurrentItem();
				if(itemStack9 != null && this.playerController.sendUseItem(this.thePlayer, this.theWorld, itemStack9)) {
					this.entityRenderer.itemRenderer.func_9450_c();
				}
			}

		}
	}

	public void toggleFullscreen() {
		try {
			this.fullscreen = !this.fullscreen;
			if(this.fullscreen) {
				Display.setDisplayMode(Display.getDesktopDisplayMode());
				this.displayWidth = Display.getDisplayMode().getWidth();
				this.displayHeight = Display.getDisplayMode().getHeight();
				if(this.displayWidth <= 0) {
					this.displayWidth = 1;
				}

				if(this.displayHeight <= 0) {
					this.displayHeight = 1;
				}
			} else {
				if(this.mcCanvas != null) {
					this.displayWidth = this.mcCanvas.getWidth();
					this.displayHeight = this.mcCanvas.getHeight();
				} else {
					this.displayWidth = this.tempDisplayWidth;
					this.displayHeight = this.tempDisplayHeight;
				}

				if(this.displayWidth <= 0) {
					this.displayWidth = 1;
				}

				if(this.displayHeight <= 0) {
					this.displayHeight = 1;
				}
			}

			if(this.currentScreen != null) {
				this.resize(this.displayWidth, this.displayHeight);
			}

			Display.setFullscreen(this.fullscreen);
			Display.update();
		} catch (Exception exception2) {
			exception2.printStackTrace();
		}

	}

	private void resize(int i1, int i2) {
		if(i1 <= 0) {
			i1 = 1;
		}

		if(i2 <= 0) {
			i2 = 1;
		}

		this.displayWidth = i1;
		this.displayHeight = i2;
		if(this.currentScreen != null) {
			ScaledResolution scaledResolution3 = new ScaledResolution(this.gameSettings, i1, i2);
			int i4 = scaledResolution3.getScaledWidth();
			int i5 = scaledResolution3.getScaledHeight();
			this.currentScreen.setWorldAndResolution(this, i4, i5);
		}

	}

	private void clickMiddleMouseButton() {
		if(this.objectMouseOver != null) {
			int i1 = this.theWorld.getBlockId(this.objectMouseOver.blockX, this.objectMouseOver.blockY, this.objectMouseOver.blockZ);
			if(i1 == Block.grass.blockID) {
				i1 = Block.dirt.blockID;
			}

			if(i1 == Block.stairDouble.blockID) {
				i1 = Block.stairSingle.blockID;
			}

			if(i1 == Block.bedrock.blockID) {
				i1 = Block.stone.blockID;
			}

			this.thePlayer.inventory.setCurrentItem(i1, this.playerController instanceof PlayerControllerTest);
		}

	}

	private void func_28001_B() {
		(new ThreadCheckHasPaid(this)).start();
	}

	public void runTick() {
		if(this.ticksRan == 6000) {
			this.func_28001_B();
		}

		this.statFileWriter.func_27178_d();
		this.ingameGUI.updateTick();
		this.entityRenderer.getMouseOver(1.0F);
		int i3;
		if(this.thePlayer != null) {
			IChunkProvider iChunkProvider1 = this.theWorld.getIChunkProvider();
			if(iChunkProvider1 instanceof ChunkProviderLoadOrGenerate) {
				ChunkProviderLoadOrGenerate chunkProviderLoadOrGenerate2 = (ChunkProviderLoadOrGenerate)iChunkProvider1;
				i3 = MathHelper.floor_float((float)((int)this.thePlayer.posX)) >> 4;
				int i4 = MathHelper.floor_float((float)((int)this.thePlayer.posZ)) >> 4;
				chunkProviderLoadOrGenerate2.setCurrentChunkOver(i3, i4);
			}
		}

		if(!this.isGamePaused && this.theWorld != null) {
			this.playerController.updateController();
		}

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain.png"));
		if(!this.isGamePaused) {
			this.renderEngine.updateDynamicTextures();
		}

		if(this.currentScreen == null && this.thePlayer != null) {
			if(this.thePlayer.health <= 0) {
				this.displayGuiScreen((GuiScreen)null);
			} else if(this.thePlayer.isPlayerSleeping() && this.theWorld != null && this.theWorld.multiplayerWorld) {
				this.displayGuiScreen(new GuiSleepMP());
			}
		} else if(this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
			this.displayGuiScreen((GuiScreen)null);
		}

		if(this.currentScreen != null) {
			this.leftClickCounter = 10000;
			this.mouseTicksRan = this.ticksRan + 10000;
		}

		if(this.currentScreen != null) {
			this.currentScreen.handleInput();
			if(this.currentScreen != null) {
				this.currentScreen.field_25091_h.func_25088_a();
				this.currentScreen.updateScreen();
			}
		}

		if(this.currentScreen == null || this.currentScreen.field_948_f) {
			label301:
			while(true) {
				while(true) {
					while(true) {
						long j5;
						do {
							if(!Mouse.next()) {
								if(this.leftClickCounter > 0) {
									--this.leftClickCounter;
								}

								while(true) {
									while(true) {
										do {
											if(!Keyboard.next()) {
												if(this.currentScreen == null) {
													if(Mouse.isButtonDown(0) && (float)(this.ticksRan - this.mouseTicksRan) >= this.timer.ticksPerSecond / 4.0F && this.inGameHasFocus) {
														this.clickMouse(0);
														this.mouseTicksRan = this.ticksRan;
													}

													if(Mouse.isButtonDown(1) && (float)(this.ticksRan - this.mouseTicksRan) >= this.timer.ticksPerSecond / 4.0F && this.inGameHasFocus) {
														this.clickMouse(1);
														this.mouseTicksRan = this.ticksRan;
													}
												}

												this.func_6254_a(0, this.currentScreen == null && Mouse.isButtonDown(0) && this.inGameHasFocus);
												break label301;
											}

											this.thePlayer.handleKeyPress(Keyboard.getEventKey(), Keyboard.getEventKeyState());
										} while(!Keyboard.getEventKeyState());

										if(Keyboard.getEventKey() == Keyboard.KEY_F11) {
											this.toggleFullscreen();
										} else {
											if(this.currentScreen != null) {
												this.currentScreen.handleKeyboardInput();
											} else {
												if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
													this.displayInGameMenu();
												}

												if(Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.isKeyDown(Keyboard.KEY_F3)) {
													this.forceReload();
												}

												if(Keyboard.getEventKey() == Keyboard.KEY_F1) {
													this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
												}

												if(Keyboard.getEventKey() == Keyboard.KEY_F3) {
													this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
												}

												if(Keyboard.getEventKey() == Keyboard.KEY_F5) {
													this.gameSettings.thirdPersonView = !this.gameSettings.thirdPersonView;
												}

												if(Keyboard.getEventKey() == Keyboard.KEY_F8) {
													this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
												}

												if(Keyboard.getEventKey() == this.gameSettings.keyBindInventory.keyCode) {
													this.displayGuiScreen(new GuiInventory(this.thePlayer));
												}

												if(Keyboard.getEventKey() == this.gameSettings.keyBindDrop.keyCode) {
													this.thePlayer.dropCurrentItem();
												}

												if(Keyboard.getEventKey() == this.gameSettings.keyBindChat.keyCode) {
													this.displayGuiScreen(new GuiChat());
												}
											}

											for(int i6 = 0; i6 < 9; ++i6) {
												if(Keyboard.getEventKey() == Keyboard.KEY_1 + i6) {
													this.thePlayer.inventory.currentItem = i6;
												}
											}

											if(Keyboard.getEventKey() == this.gameSettings.keyBindToggleFog.keyCode) {
												this.gameSettings.setOptionValue(EnumOptions.RENDER_DISTANCE, !Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) ? 1 : -1);
											}
										}
									}
								}
							}

							j5 = System.currentTimeMillis() - this.systemTime;
						} while(j5 > 200L);

						i3 = Mouse.getEventDWheel();
						if(i3 != 0) {
							this.thePlayer.inventory.changeCurrentItem(i3);
							if(this.gameSettings.field_22275_C) {
								if(i3 > 0) {
									i3 = 1;
								}

								if(i3 < 0) {
									i3 = -1;
								}

								this.gameSettings.field_22272_F += (float)i3 * 0.25F;
							}
						}

						if(this.currentScreen == null) {
							if(!this.inGameHasFocus && Mouse.getEventButtonState()) {
								this.setIngameFocus();
							} else {
								if(Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
									this.clickMouse(0);
									this.mouseTicksRan = this.ticksRan;
								}

								if(Mouse.getEventButton() == 1 && Mouse.getEventButtonState()) {
									this.clickMouse(1);
									this.mouseTicksRan = this.ticksRan;
								}

								if(Mouse.getEventButton() == 2 && Mouse.getEventButtonState()) {
									this.clickMiddleMouseButton();
								}
							}
						} else if(this.currentScreen != null) {
							this.currentScreen.handleMouseInput();
						}
					}
				}
			}
		}

		if(this.theWorld != null) {
			if(this.thePlayer != null) {
				++this.joinPlayerCounter;
				if(this.joinPlayerCounter == 30) {
					this.joinPlayerCounter = 0;
					this.theWorld.joinEntityInSurroundings(this.thePlayer);
				}
			}

			this.theWorld.difficultySetting = this.gameSettings.difficulty;
			if(this.theWorld.multiplayerWorld) {
				this.theWorld.difficultySetting = 3;
			}

			if(!this.isGamePaused) {
				this.entityRenderer.updateRenderer();
			}

			if(!this.isGamePaused) {
				this.renderGlobal.updateClouds();
			}

			if(!this.isGamePaused) {
				if(this.theWorld.field_27172_i > 0) {
					--this.theWorld.field_27172_i;
				}

				this.theWorld.updateEntities();
			}

			if(!this.isGamePaused || this.isMultiplayerWorld()) {
				this.theWorld.setAllowedMobSpawns(this.gameSettings.difficulty > 0, true);
				this.theWorld.tick();
			}

			if(!this.isGamePaused && this.theWorld != null) {
				this.theWorld.randomDisplayUpdates(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
			}

			if(!this.isGamePaused) {
				this.effectRenderer.updateEffects();
			}
		}

		this.systemTime = System.currentTimeMillis();
	}

	private void forceReload() {
		System.out.println("FORCING RELOAD!");
		this.sndManager = new SoundManager();
		this.sndManager.loadSoundSettings(this.gameSettings);
		this.downloadResourcesThread.reloadResources();
	}

	public boolean isMultiplayerWorld() {
		return this.theWorld != null && this.theWorld.multiplayerWorld;
	}

	public void startWorld(String string1, String string2, long j3) {
		this.changeWorld1((World)null);
		System.gc();
		if(this.saveLoader.isOldMapFormat(string1)) {
			this.convertMapFormat(string1, string2);
		} else {
			ISaveHandler iSaveHandler5 = this.saveLoader.getSaveLoader(string1, false);
			World world6 = null;
			world6 = new World(iSaveHandler5, string2, j3);
			if(world6.isNewWorld) {
				this.statFileWriter.readStat(StatList.createWorldStat, 1);
				this.statFileWriter.readStat(StatList.startGameStat, 1);
				this.changeWorld2(world6, "Generating level");
			} else {
				this.statFileWriter.readStat(StatList.loadWorldStat, 1);
				this.statFileWriter.readStat(StatList.startGameStat, 1);
				this.changeWorld2(world6, "Loading level");
			}
		}

	}

	public void usePortal() {
		System.out.println("Toggling dimension!!");
		if(this.thePlayer.dimension == -1) {
			this.thePlayer.dimension = 0;
		} else {
			this.thePlayer.dimension = -1;
		}

		this.theWorld.setEntityDead(this.thePlayer);
		this.thePlayer.isDead = false;
		double d1 = this.thePlayer.posX;
		double d3 = this.thePlayer.posZ;
		double d5 = 8.0D;
		World world7;
		if(this.thePlayer.dimension == -1) {
			d1 /= d5;
			d3 /= d5;
			this.thePlayer.setLocationAndAngles(d1, this.thePlayer.posY, d3, this.thePlayer.rotationYaw, this.thePlayer.rotationPitch);
			if(this.thePlayer.isEntityAlive()) {
				this.theWorld.updateEntityWithOptionalForce(this.thePlayer, false);
			}

			world7 = null;
			world7 = new World(this.theWorld, WorldProvider.getProviderForDimension(-1));
			this.changeWorld(world7, "Entering the Nether", this.thePlayer);
		} else {
			d1 *= d5;
			d3 *= d5;
			this.thePlayer.setLocationAndAngles(d1, this.thePlayer.posY, d3, this.thePlayer.rotationYaw, this.thePlayer.rotationPitch);
			if(this.thePlayer.isEntityAlive()) {
				this.theWorld.updateEntityWithOptionalForce(this.thePlayer, false);
			}

			world7 = null;
			world7 = new World(this.theWorld, WorldProvider.getProviderForDimension(0));
			this.changeWorld(world7, "Leaving the Nether", this.thePlayer);
		}

		this.thePlayer.worldObj = this.theWorld;
		if(this.thePlayer.isEntityAlive()) {
			this.thePlayer.setLocationAndAngles(d1, this.thePlayer.posY, d3, this.thePlayer.rotationYaw, this.thePlayer.rotationPitch);
			this.theWorld.updateEntityWithOptionalForce(this.thePlayer, false);
			(new Teleporter()).func_4107_a(this.theWorld, this.thePlayer);
		}

	}

	public void changeWorld1(World world1) {
		this.changeWorld2(world1, "");
	}

	public void changeWorld2(World world1, String string2) {
		this.changeWorld(world1, string2, (EntityPlayer)null);
	}

	public void changeWorld(World world1, String string2, EntityPlayer entityPlayer3) {
		this.statFileWriter.func_27175_b();
		this.statFileWriter.syncStats();
		this.renderViewEntity = null;
		this.loadingScreen.printText(string2);
		this.loadingScreen.displayLoadingString("");
		this.sndManager.playStreaming((String)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		if(this.theWorld != null) {
			this.theWorld.saveWorldIndirectly(this.loadingScreen);
		}

		this.theWorld = world1;
		if(world1 != null) {
			this.playerController.func_717_a(world1);
			if(!this.isMultiplayerWorld()) {
				if(entityPlayer3 == null) {
					this.thePlayer = (EntityPlayerSP)world1.func_4085_a(EntityPlayerSP.class);
				}
			} else if(this.thePlayer != null) {
				this.thePlayer.preparePlayerToSpawn();
				if(world1 != null) {
					world1.entityJoinedWorld(this.thePlayer);
				}
			}

			if(!world1.multiplayerWorld) {
				this.func_6255_d(string2);
			}

			if(this.thePlayer == null) {
				this.thePlayer = (EntityPlayerSP)this.playerController.createPlayer(world1);
				this.thePlayer.preparePlayerToSpawn();
				this.playerController.flipPlayer(this.thePlayer);
			}

			this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
			if(this.renderGlobal != null) {
				this.renderGlobal.changeWorld(world1);
			}

			if(this.effectRenderer != null) {
				this.effectRenderer.clearEffects(world1);
			}

			this.playerController.func_6473_b(this.thePlayer);
			if(entityPlayer3 != null) {
				world1.emptyMethod1();
			}

			IChunkProvider iChunkProvider4 = world1.getIChunkProvider();
			if(iChunkProvider4 instanceof ChunkProviderLoadOrGenerate) {
				ChunkProviderLoadOrGenerate chunkProviderLoadOrGenerate5 = (ChunkProviderLoadOrGenerate)iChunkProvider4;
				int i6 = MathHelper.floor_float((float)((int)this.thePlayer.posX)) >> 4;
				int i7 = MathHelper.floor_float((float)((int)this.thePlayer.posZ)) >> 4;
				chunkProviderLoadOrGenerate5.setCurrentChunkOver(i6, i7);
			}

			world1.spawnPlayerWithLoadedChunks(this.thePlayer);
			if(world1.isNewWorld) {
				world1.saveWorldIndirectly(this.loadingScreen);
			}

			this.renderViewEntity = this.thePlayer;
		} else {
			this.thePlayer = null;
		}

		System.gc();
		this.systemTime = 0L;
	}

	private void convertMapFormat(String string1, String string2) {
		this.loadingScreen.printText("Converting World to " + this.saveLoader.func_22178_a());
		this.loadingScreen.displayLoadingString("This may take a while :)");
		this.saveLoader.convertMapFormat(string1, this.loadingScreen);
		this.startWorld(string1, string2, 0L);
	}

	private void func_6255_d(String string1) {
		this.loadingScreen.printText(string1);
		this.loadingScreen.displayLoadingString("Building terrain");
		short s2 = 128;
		int i3 = 0;
		int i4 = s2 * 2 / 16 + 1;
		i4 *= i4;
		IChunkProvider iChunkProvider5 = this.theWorld.getIChunkProvider();
		ChunkCoordinates chunkCoordinates6 = this.theWorld.getSpawnPoint();
		if(this.thePlayer != null) {
			chunkCoordinates6.x = (int)this.thePlayer.posX;
			chunkCoordinates6.z = (int)this.thePlayer.posZ;
		}

		if(iChunkProvider5 instanceof ChunkProviderLoadOrGenerate) {
			ChunkProviderLoadOrGenerate chunkProviderLoadOrGenerate7 = (ChunkProviderLoadOrGenerate)iChunkProvider5;
			chunkProviderLoadOrGenerate7.setCurrentChunkOver(chunkCoordinates6.x >> 4, chunkCoordinates6.z >> 4);
		}

		for(int i10 = -s2; i10 <= s2; i10 += 16) {
			for(int i8 = -s2; i8 <= s2; i8 += 16) {
				this.loadingScreen.setLoadingProgress(i3++ * 100 / i4);
				this.theWorld.getBlockId(chunkCoordinates6.x + i10, 64, chunkCoordinates6.z + i8);

				while(this.theWorld.updatingLighting()) {
				}
			}
		}

		this.loadingScreen.displayLoadingString("Simulating world for a bit");
		boolean z9 = true;
		this.theWorld.func_656_j();
	}

	public void installResource(String string1, File file2) {
		int i3 = string1.indexOf("/");
		String string4 = string1.substring(0, i3);
		string1 = string1.substring(i3 + 1);
		if(string4.equalsIgnoreCase("sound")) {
			this.sndManager.addSound(string1, file2);
		} else if(string4.equalsIgnoreCase("newsound")) {
			this.sndManager.addSound(string1, file2);
		} else if(string4.equalsIgnoreCase("streaming")) {
			this.sndManager.addStreaming(string1, file2);
		} else if(string4.equalsIgnoreCase("music")) {
			this.sndManager.addMusic(string1, file2);
		} else if(string4.equalsIgnoreCase("newmusic")) {
			this.sndManager.addMusic(string1, file2);
		}

	}

	public OpenGlCapsChecker getOpenGlCapsChecker() {
		return this.glCapabilities;
	}

	public String func_6241_m() {
		return this.renderGlobal.getDebugInfoRenders();
	}

	public String func_6262_n() {
		return this.renderGlobal.getDebugInfoEntities();
	}

	public String func_21002_o() {
		return this.theWorld.func_21119_g();
	}

	public String func_6245_o() {
		return "P: " + this.effectRenderer.getStatistics() + ". T: " + this.theWorld.func_687_d();
	}

	public void respawn(boolean z1, int i2) {
		if(!this.theWorld.multiplayerWorld && !this.theWorld.worldProvider.canRespawnHere()) {
			this.usePortal();
		}

		ChunkCoordinates chunkCoordinates3 = null;
		ChunkCoordinates chunkCoordinates4 = null;
		boolean z5 = true;
		if(this.thePlayer != null && !z1) {
			chunkCoordinates3 = this.thePlayer.getPlayerSpawnCoordinate();
			if(chunkCoordinates3 != null) {
				chunkCoordinates4 = EntityPlayer.func_25060_a(this.theWorld, chunkCoordinates3);
				if(chunkCoordinates4 == null) {
					this.thePlayer.addChatMessage("tile.bed.notValid");
				}
			}
		}

		if(chunkCoordinates4 == null) {
			chunkCoordinates4 = this.theWorld.getSpawnPoint();
			z5 = false;
		}

		IChunkProvider iChunkProvider6 = this.theWorld.getIChunkProvider();
		if(iChunkProvider6 instanceof ChunkProviderLoadOrGenerate) {
			ChunkProviderLoadOrGenerate chunkProviderLoadOrGenerate7 = (ChunkProviderLoadOrGenerate)iChunkProvider6;
			chunkProviderLoadOrGenerate7.setCurrentChunkOver(chunkCoordinates4.x >> 4, chunkCoordinates4.z >> 4);
		}

		this.theWorld.setSpawnLocation();
		this.theWorld.updateEntityList();
		int i8 = 0;
		if(this.thePlayer != null) {
			i8 = this.thePlayer.entityId;
			this.theWorld.setEntityDead(this.thePlayer);
		}

		this.renderViewEntity = null;
		this.thePlayer = (EntityPlayerSP)this.playerController.createPlayer(this.theWorld);
		this.thePlayer.dimension = i2;
		this.renderViewEntity = this.thePlayer;
		this.thePlayer.preparePlayerToSpawn();
		if(z5) {
			this.thePlayer.setPlayerSpawnCoordinate(chunkCoordinates3);
			this.thePlayer.setLocationAndAngles((double)((float)chunkCoordinates4.x + 0.5F), (double)((float)chunkCoordinates4.y + 0.1F), (double)((float)chunkCoordinates4.z + 0.5F), 0.0F, 0.0F);
		}

		this.playerController.flipPlayer(this.thePlayer);
		this.theWorld.spawnPlayerWithLoadedChunks(this.thePlayer);
		this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
		this.thePlayer.entityId = i8;
		this.thePlayer.func_6420_o();
		this.playerController.func_6473_b(this.thePlayer);
		this.func_6255_d("Respawning");
		if(this.currentScreen instanceof GuiGameOver) {
			this.displayGuiScreen((GuiScreen)null);
		}

	}

	public static void func_6269_a(String string0, String string1) {
		startMainThread(string0, string1, (String)null);
	}

	public static void startMainThread(String string0, String string1, String string2) {
		boolean z3 = false;
		Frame frame5 = new Frame("Minecraft");
		Canvas canvas6 = new Canvas();
		frame5.setLayout(new BorderLayout());
		frame5.add(canvas6, "Center");
		canvas6.setPreferredSize(new Dimension(854, 480));
		frame5.pack();
		frame5.setLocationRelativeTo((Component)null);
		MinecraftImpl minecraftImpl7 = new MinecraftImpl(frame5, canvas6, (MinecraftApplet)null, 854, 480, z3, frame5);
		Thread thread8 = new Thread(minecraftImpl7, "Minecraft main thread");
		thread8.setPriority(10);
		minecraftImpl7.minecraftUri = "www.minecraft.net";
		if(string0 != null && string1 != null) {
			minecraftImpl7.session = new Session(string0, string1);
		} else {
			minecraftImpl7.session = new Session("Player" + System.currentTimeMillis() % 1000L, "");
		}

		if(string2 != null) {
			String[] string9 = string2.split(":");
			minecraftImpl7.setServer(string9[0], Integer.parseInt(string9[1]));
		}

		frame5.setVisible(true);
		frame5.addWindowListener(new GameWindowListener(minecraftImpl7, thread8));
		thread8.start();
	}

	public NetClientHandler getSendQueue() {
		return this.thePlayer instanceof EntityClientPlayerMP ? ((EntityClientPlayerMP)this.thePlayer).sendQueue : null;
	}

	public static void main(String[] string0) {
		String string1 = null;
		String string2 = null;
		string1 = "Player" + System.currentTimeMillis() % 1000L;
		if(string0.length > 0) {
			string1 = string0[0];
		}

		string2 = "-";
		if(string0.length > 1) {
			string2 = string0[1];
		}

		func_6269_a(string1, string2);
	}

	public static boolean isGuiEnabled() {
		return theMinecraft == null || !theMinecraft.gameSettings.hideGUI;
	}

	public static boolean isFancyGraphicsEnabled() {
		return theMinecraft != null && theMinecraft.gameSettings.fancyGraphics;
	}

	public static boolean isAmbientOcclusionEnabled() {
		return theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion;
	}

	public static boolean isDebugInfoEnabled() {
		return theMinecraft != null && theMinecraft.gameSettings.showDebugInfo;
	}

	public boolean lineIsCommand(String string1) {
		if(string1.startsWith("/")) {
			;
		}

		return false;
	}
}
