package net.minecraft.src;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class GuiSelectWorld extends GuiScreen {
	private final DateFormat dateFormatter = new SimpleDateFormat();
	protected GuiScreen parentScreen;
	protected String screenTitle = "Select world";
	private boolean selected = false;
	private int selectedWorld;
	private List saveList;
	private GuiWorldSlot worldSlotContainer;
	private String field_22098_o;
	private String field_22097_p;
	private boolean deleting;
	private GuiButton buttonRename;
	private GuiButton buttonSelect;
	private GuiButton buttonDelete;

	public GuiSelectWorld(GuiScreen guiScreen1) {
		this.parentScreen = guiScreen1;
	}

	public void initGui() {
		StringTranslate stringTranslate1 = StringTranslate.getInstance();
		this.screenTitle = stringTranslate1.translateKey("selectWorld.title");
		this.field_22098_o = stringTranslate1.translateKey("selectWorld.world");
		this.field_22097_p = stringTranslate1.translateKey("selectWorld.conversion");
		this.loadSaves();
		this.worldSlotContainer = new GuiWorldSlot(this);
		this.worldSlotContainer.registerScrollButtons(this.controlList, 4, 5);
		this.initButtons();
	}

	private void loadSaves() {
		ISaveFormat iSaveFormat1 = this.mc.getSaveLoader();
		this.saveList = iSaveFormat1.func_22176_b();
		Collections.sort(this.saveList);
		this.selectedWorld = -1;
	}

	protected String getSaveFileName(int i1) {
		return ((SaveFormatComparator)this.saveList.get(i1)).getFileName();
	}

	protected String getSaveName(int i1) {
		String string2 = ((SaveFormatComparator)this.saveList.get(i1)).getDisplayName();
		if(string2 == null || MathHelper.stringNullOrLengthZero(string2)) {
			StringTranslate stringTranslate3 = StringTranslate.getInstance();
			string2 = stringTranslate3.translateKey("selectWorld.world") + " " + (i1 + 1);
		}

		return string2;
	}

	public void initButtons() {
		StringTranslate stringTranslate1 = StringTranslate.getInstance();
		this.controlList.add(this.buttonSelect = new GuiButton(1, this.width / 2 - 154, this.height - 52, 150, 20, stringTranslate1.translateKey("selectWorld.select")));
		this.controlList.add(this.buttonRename = new GuiButton(6, this.width / 2 - 154, this.height - 28, 70, 20, stringTranslate1.translateKey("selectWorld.rename")));
		this.controlList.add(this.buttonDelete = new GuiButton(2, this.width / 2 - 74, this.height - 28, 70, 20, stringTranslate1.translateKey("selectWorld.delete")));
		this.controlList.add(new GuiButton(3, this.width / 2 + 4, this.height - 52, 150, 20, stringTranslate1.translateKey("selectWorld.create")));
		this.controlList.add(new GuiButton(0, this.width / 2 + 4, this.height - 28, 150, 20, stringTranslate1.translateKey("gui.cancel")));
		this.buttonSelect.enabled = false;
		this.buttonRename.enabled = false;
		this.buttonDelete.enabled = false;
	}

	protected void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.enabled) {
			if(guiButton1.id == 2) {
				String string2 = this.getSaveName(this.selectedWorld);
				if(string2 != null) {
					this.deleting = true;
					StringTranslate stringTranslate3 = StringTranslate.getInstance();
					String string4 = stringTranslate3.translateKey("selectWorld.deleteQuestion");
					String string5 = "\'" + string2 + "\' " + stringTranslate3.translateKey("selectWorld.deleteWarning");
					String string6 = stringTranslate3.translateKey("selectWorld.deleteButton");
					String string7 = stringTranslate3.translateKey("gui.cancel");
					GuiYesNo guiYesNo8 = new GuiYesNo(this, string4, string5, string6, string7, this.selectedWorld);
					this.mc.displayGuiScreen(guiYesNo8);
				}
			} else if(guiButton1.id == 1) {
				this.selectWorld(this.selectedWorld);
			} else if(guiButton1.id == 3) {
				this.mc.displayGuiScreen(new GuiCreateWorld(this));
			} else if(guiButton1.id == 6) {
				this.mc.displayGuiScreen(new GuiRenameWorld(this, this.getSaveFileName(this.selectedWorld)));
			} else if(guiButton1.id == 0) {
				this.mc.displayGuiScreen(this.parentScreen);
			} else {
				this.worldSlotContainer.actionPerformed(guiButton1);
			}

		}
	}

	public void selectWorld(int i1) {
		this.mc.displayGuiScreen((GuiScreen)null);
		if(!this.selected) {
			this.selected = true;
			this.mc.playerController = new PlayerControllerSP(this.mc);
			String string2 = this.getSaveFileName(i1);
			if(string2 == null) {
				string2 = "World" + i1;
			}

			this.mc.startWorld(string2, this.getSaveName(i1), 0L);
			this.mc.displayGuiScreen((GuiScreen)null);
		}
	}

	public void deleteWorld(boolean z1, int i2) {
		if(this.deleting) {
			this.deleting = false;
			if(z1) {
				ISaveFormat iSaveFormat3 = this.mc.getSaveLoader();
				iSaveFormat3.flushCache();
				iSaveFormat3.func_22172_c(this.getSaveFileName(i2));
				this.loadSaves();
			}

			this.mc.displayGuiScreen(this);
		}

	}

	public void drawScreen(int i1, int i2, float f3) {
		this.worldSlotContainer.drawScreen(i1, i2, f3);
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
		super.drawScreen(i1, i2, f3);
	}

	static List getSize(GuiSelectWorld guiSelectWorld0) {
		return guiSelectWorld0.saveList;
	}

	static int onElementSelected(GuiSelectWorld guiSelectWorld0, int i1) {
		return guiSelectWorld0.selectedWorld = i1;
	}

	static int getSelectedWorld(GuiSelectWorld guiSelectWorld0) {
		return guiSelectWorld0.selectedWorld;
	}

	static GuiButton getSelectButton(GuiSelectWorld guiSelectWorld0) {
		return guiSelectWorld0.buttonSelect;
	}

	static GuiButton getRenameButton(GuiSelectWorld guiSelectWorld0) {
		return guiSelectWorld0.buttonRename;
	}

	static GuiButton getDeleteButton(GuiSelectWorld guiSelectWorld0) {
		return guiSelectWorld0.buttonDelete;
	}

	static String func_22087_f(GuiSelectWorld guiSelectWorld0) {
		return guiSelectWorld0.field_22098_o;
	}

	static DateFormat getDateFormatter(GuiSelectWorld guiSelectWorld0) {
		return guiSelectWorld0.dateFormatter;
	}

	static String func_22088_h(GuiSelectWorld guiSelectWorld0) {
		return guiSelectWorld0.field_22097_p;
	}
}
