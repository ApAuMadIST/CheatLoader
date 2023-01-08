package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class Block {
	public static final StepSound soundPowderFootstep = new StepSound("stone", 1.0F, 1.0F);
	public static final StepSound soundWoodFootstep = new StepSound("wood", 1.0F, 1.0F);
	public static final StepSound soundGravelFootstep = new StepSound("gravel", 1.0F, 1.0F);
	public static final StepSound soundGrassFootstep = new StepSound("grass", 1.0F, 1.0F);
	public static final StepSound soundStoneFootstep = new StepSound("stone", 1.0F, 1.0F);
	public static final StepSound soundMetalFootstep = new StepSound("stone", 1.0F, 1.5F);
	public static final StepSound soundGlassFootstep = new StepSoundStone("stone", 1.0F, 1.0F);
	public static final StepSound soundClothFootstep = new StepSound("cloth", 1.0F, 1.0F);
	public static final StepSound soundSandFootstep = new StepSoundSand("sand", 1.0F, 1.0F);
	public static final Block[] blocksList = new Block[256];
	public static final boolean[] tickOnLoad = new boolean[256];
	public static final boolean[] opaqueCubeLookup = new boolean[256];
	public static final boolean[] isBlockContainer = new boolean[256];
	public static final int[] lightOpacity = new int[256];
	public static final boolean[] canBlockGrass = new boolean[256];
	public static final int[] lightValue = new int[256];
	public static final boolean[] field_28032_t = new boolean[256];
	public static final Block stone = (new BlockStone(1, 1)).setHardness(1.5F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("stone");
	public static final BlockGrass grass = (BlockGrass)(new BlockGrass(2)).setHardness(0.6F).setStepSound(soundGrassFootstep).setBlockName("grass");
	public static final Block dirt = (new BlockDirt(3, 2)).setHardness(0.5F).setStepSound(soundGravelFootstep).setBlockName("dirt");
	public static final Block cobblestone = (new Block(4, 16, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("stonebrick");
	public static final Block planks = (new Block(5, 4, Material.wood)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setBlockName("wood").disableNeighborNotifyOnMetadataChange();
	public static final Block sapling = (new BlockSapling(6, 15)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("sapling").disableNeighborNotifyOnMetadataChange();
	public static final Block bedrock = (new Block(7, 17, Material.rock)).setBlockUnbreakable().setResistance(6000000.0F).setStepSound(soundStoneFootstep).setBlockName("bedrock").disableStats();
	public static final Block waterMoving = (new BlockFlowing(8, Material.water)).setHardness(100.0F).setLightOpacity(3).setBlockName("water").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block waterStill = (new BlockStationary(9, Material.water)).setHardness(100.0F).setLightOpacity(3).setBlockName("water").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block lavaMoving = (new BlockFlowing(10, Material.lava)).setHardness(0.0F).setLightValue(1.0F).setLightOpacity(255).setBlockName("lava").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block lavaStill = (new BlockStationary(11, Material.lava)).setHardness(100.0F).setLightValue(1.0F).setLightOpacity(255).setBlockName("lava").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block sand = (new BlockSand(12, 18)).setHardness(0.5F).setStepSound(soundSandFootstep).setBlockName("sand");
	public static final Block gravel = (new BlockGravel(13, 19)).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("gravel");
	public static final Block oreGold = (new BlockOre(14, 32)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreGold");
	public static final Block oreIron = (new BlockOre(15, 33)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreIron");
	public static final Block oreCoal = (new BlockOre(16, 34)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreCoal");
	public static final Block wood = (new BlockLog(17)).setHardness(2.0F).setStepSound(soundWoodFootstep).setBlockName("log").disableNeighborNotifyOnMetadataChange();
	public static final BlockLeaves leaves = (BlockLeaves)(new BlockLeaves(18, 52)).setHardness(0.2F).setLightOpacity(1).setStepSound(soundGrassFootstep).setBlockName("leaves").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block sponge = (new BlockSponge(19)).setHardness(0.6F).setStepSound(soundGrassFootstep).setBlockName("sponge");
	public static final Block glass = (new BlockGlass(20, 49, Material.glass, false)).setHardness(0.3F).setStepSound(soundGlassFootstep).setBlockName("glass");
	public static final Block oreLapis = (new BlockOre(21, 160)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreLapis");
	public static final Block blockLapis = (new Block(22, 144, Material.rock)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("blockLapis");
	public static final Block dispenser = (new BlockDispenser(23)).setHardness(3.5F).setStepSound(soundStoneFootstep).setBlockName("dispenser").disableNeighborNotifyOnMetadataChange();
	public static final Block sandStone = (new BlockSandStone(24)).setStepSound(soundStoneFootstep).setHardness(0.8F).setBlockName("sandStone");
	public static final Block musicBlock = (new BlockNote(25)).setHardness(0.8F).setBlockName("musicBlock").disableNeighborNotifyOnMetadataChange();
	public static final Block blockBed = (new BlockBed(26)).setHardness(0.2F).setBlockName("bed").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block railPowered = (new BlockRail(27, 179, true)).setHardness(0.7F).setStepSound(soundMetalFootstep).setBlockName("goldenRail").disableNeighborNotifyOnMetadataChange();
	public static final Block railDetector = (new BlockDetectorRail(28, 195)).setHardness(0.7F).setStepSound(soundMetalFootstep).setBlockName("detectorRail").disableNeighborNotifyOnMetadataChange();
	public static final Block pistonStickyBase = (new BlockPistonBase(29, 106, true)).setBlockName("pistonStickyBase").disableNeighborNotifyOnMetadataChange();
	public static final Block web = (new BlockWeb(30, 11)).setLightOpacity(1).setHardness(4.0F).setBlockName("web");
	public static final BlockTallGrass tallGrass = (BlockTallGrass)(new BlockTallGrass(31, 39)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("tallgrass");
	public static final BlockDeadBush deadBush = (BlockDeadBush)(new BlockDeadBush(32, 55)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("deadbush");
	public static final Block pistonBase = (new BlockPistonBase(33, 107, false)).setBlockName("pistonBase").disableNeighborNotifyOnMetadataChange();
	public static final BlockPistonExtension pistonExtension = (BlockPistonExtension)(new BlockPistonExtension(34, 107)).disableNeighborNotifyOnMetadataChange();
	public static final Block cloth = (new BlockCloth()).setHardness(0.8F).setStepSound(soundClothFootstep).setBlockName("cloth").disableNeighborNotifyOnMetadataChange();
	public static final BlockPistonMoving pistonMoving = new BlockPistonMoving(36);
	public static final BlockFlower plantYellow = (BlockFlower)(new BlockFlower(37, 13)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("flower");
	public static final BlockFlower plantRed = (BlockFlower)(new BlockFlower(38, 12)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("rose");
	public static final BlockFlower mushroomBrown = (BlockFlower)(new BlockMushroom(39, 29)).setHardness(0.0F).setStepSound(soundGrassFootstep).setLightValue(0.125F).setBlockName("mushroom");
	public static final BlockFlower mushroomRed = (BlockFlower)(new BlockMushroom(40, 28)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("mushroom");
	public static final Block blockGold = (new BlockOreStorage(41, 23)).setHardness(3.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setBlockName("blockGold");
	public static final Block blockSteel = (new BlockOreStorage(42, 22)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setBlockName("blockIron");
	public static final Block stairDouble = (new BlockStep(43, true)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("stoneSlab");
	public static final Block stairSingle = (new BlockStep(44, false)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("stoneSlab");
	public static final Block brick = (new Block(45, 7, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("brick");
	public static final Block tnt = (new BlockTNT(46, 8)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("tnt");
	public static final Block bookShelf = (new BlockBookshelf(47, 35)).setHardness(1.5F).setStepSound(soundWoodFootstep).setBlockName("bookshelf");
	public static final Block cobblestoneMossy = (new Block(48, 36, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("stoneMoss");
	public static final Block obsidian = (new BlockObsidian(49, 37)).setHardness(10.0F).setResistance(2000.0F).setStepSound(soundStoneFootstep).setBlockName("obsidian");
	public static final Block torchWood = (new BlockTorch(50, 80)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(soundWoodFootstep).setBlockName("torch").disableNeighborNotifyOnMetadataChange();
	public static final BlockFire fire = (BlockFire)(new BlockFire(51, 31)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setBlockName("fire").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block mobSpawner = (new BlockMobSpawner(52, 65)).setHardness(5.0F).setStepSound(soundMetalFootstep).setBlockName("mobSpawner").disableStats();
	public static final Block stairCompactPlanks = (new BlockStairs(53, planks)).setBlockName("stairsWood").disableNeighborNotifyOnMetadataChange();
	public static final Block chest = (new BlockChest(54)).setHardness(2.5F).setStepSound(soundWoodFootstep).setBlockName("chest").disableNeighborNotifyOnMetadataChange();
	public static final Block redstoneWire = (new BlockRedstoneWire(55, 164)).setHardness(0.0F).setStepSound(soundPowderFootstep).setBlockName("redstoneDust").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block oreDiamond = (new BlockOre(56, 50)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreDiamond");
	public static final Block blockDiamond = (new BlockOreStorage(57, 24)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setBlockName("blockDiamond");
	public static final Block workbench = (new BlockWorkbench(58)).setHardness(2.5F).setStepSound(soundWoodFootstep).setBlockName("workbench");
	public static final Block crops = (new BlockCrops(59, 88)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("crops").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block tilledField = (new BlockFarmland(60)).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("farmland");
	public static final Block stoneOvenIdle = (new BlockFurnace(61, false)).setHardness(3.5F).setStepSound(soundStoneFootstep).setBlockName("furnace").disableNeighborNotifyOnMetadataChange();
	public static final Block stoneOvenActive = (new BlockFurnace(62, true)).setHardness(3.5F).setStepSound(soundStoneFootstep).setLightValue(0.875F).setBlockName("furnace").disableNeighborNotifyOnMetadataChange();
	public static final Block signPost = (new BlockSign(63, TileEntitySign.class, true)).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("sign").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block doorWood = (new BlockDoor(64, Material.wood)).setHardness(3.0F).setStepSound(soundWoodFootstep).setBlockName("doorWood").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block ladder = (new BlockLadder(65, 83)).setHardness(0.4F).setStepSound(soundWoodFootstep).setBlockName("ladder").disableNeighborNotifyOnMetadataChange();
	public static final Block rail = (new BlockRail(66, 128, false)).setHardness(0.7F).setStepSound(soundMetalFootstep).setBlockName("rail").disableNeighborNotifyOnMetadataChange();
	public static final Block stairCompactCobblestone = (new BlockStairs(67, cobblestone)).setBlockName("stairsStone").disableNeighborNotifyOnMetadataChange();
	public static final Block signWall = (new BlockSign(68, TileEntitySign.class, false)).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("sign").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block lever = (new BlockLever(69, 96)).setHardness(0.5F).setStepSound(soundWoodFootstep).setBlockName("lever").disableNeighborNotifyOnMetadataChange();
	public static final Block pressurePlateStone = (new BlockPressurePlate(70, stone.blockIndexInTexture, EnumMobType.mobs, Material.rock)).setHardness(0.5F).setStepSound(soundStoneFootstep).setBlockName("pressurePlate").disableNeighborNotifyOnMetadataChange();
	public static final Block doorSteel = (new BlockDoor(71, Material.iron)).setHardness(5.0F).setStepSound(soundMetalFootstep).setBlockName("doorIron").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block pressurePlatePlanks = (new BlockPressurePlate(72, planks.blockIndexInTexture, EnumMobType.everything, Material.wood)).setHardness(0.5F).setStepSound(soundWoodFootstep).setBlockName("pressurePlate").disableNeighborNotifyOnMetadataChange();
	public static final Block oreRedstone = (new BlockRedstoneOre(73, 51, false)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreRedstone").disableNeighborNotifyOnMetadataChange();
	public static final Block oreRedstoneGlowing = (new BlockRedstoneOre(74, 51, true)).setLightValue(0.625F).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreRedstone").disableNeighborNotifyOnMetadataChange();
	public static final Block torchRedstoneIdle = (new BlockRedstoneTorch(75, 115, false)).setHardness(0.0F).setStepSound(soundWoodFootstep).setBlockName("notGate").disableNeighborNotifyOnMetadataChange();
	public static final Block torchRedstoneActive = (new BlockRedstoneTorch(76, 99, true)).setHardness(0.0F).setLightValue(0.5F).setStepSound(soundWoodFootstep).setBlockName("notGate").disableNeighborNotifyOnMetadataChange();
	public static final Block button = (new BlockButton(77, stone.blockIndexInTexture)).setHardness(0.5F).setStepSound(soundStoneFootstep).setBlockName("button").disableNeighborNotifyOnMetadataChange();
	public static final Block snow = (new BlockSnow(78, 66)).setHardness(0.1F).setStepSound(soundClothFootstep).setBlockName("snow");
	public static final Block ice = (new BlockIce(79, 67)).setHardness(0.5F).setLightOpacity(3).setStepSound(soundGlassFootstep).setBlockName("ice");
	public static final Block blockSnow = (new BlockSnowBlock(80, 66)).setHardness(0.2F).setStepSound(soundClothFootstep).setBlockName("snow");
	public static final Block cactus = (new BlockCactus(81, 70)).setHardness(0.4F).setStepSound(soundClothFootstep).setBlockName("cactus");
	public static final Block blockClay = (new BlockClay(82, 72)).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("clay");
	public static final Block reed = (new BlockReed(83, 73)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("reeds").disableStats();
	public static final Block jukebox = (new BlockJukeBox(84, 74)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("jukebox").disableNeighborNotifyOnMetadataChange();
	public static final Block fence = (new BlockFence(85, 4)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setBlockName("fence").disableNeighborNotifyOnMetadataChange();
	public static final Block pumpkin = (new BlockPumpkin(86, 102, false)).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("pumpkin").disableNeighborNotifyOnMetadataChange();
	public static final Block netherrack = (new BlockNetherrack(87, 103)).setHardness(0.4F).setStepSound(soundStoneFootstep).setBlockName("hellrock");
	public static final Block slowSand = (new BlockSoulSand(88, 104)).setHardness(0.5F).setStepSound(soundSandFootstep).setBlockName("hellsand");
	public static final Block glowStone = (new BlockGlowStone(89, 105, Material.rock)).setHardness(0.3F).setStepSound(soundGlassFootstep).setLightValue(1.0F).setBlockName("lightgem");
	public static final BlockPortal portal = (BlockPortal)(new BlockPortal(90, 14)).setHardness(-1.0F).setStepSound(soundGlassFootstep).setLightValue(0.75F).setBlockName("portal");
	public static final Block pumpkinLantern = (new BlockPumpkin(91, 102, true)).setHardness(1.0F).setStepSound(soundWoodFootstep).setLightValue(1.0F).setBlockName("litpumpkin").disableNeighborNotifyOnMetadataChange();
	public static final Block cake = (new BlockCake(92, 121)).setHardness(0.5F).setStepSound(soundClothFootstep).setBlockName("cake").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block redstoneRepeaterIdle = (new BlockRedstoneRepeater(93, false)).setHardness(0.0F).setStepSound(soundWoodFootstep).setBlockName("diode").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block redstoneRepeaterActive = (new BlockRedstoneRepeater(94, true)).setHardness(0.0F).setLightValue(0.625F).setStepSound(soundWoodFootstep).setBlockName("diode").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block lockedChest = (new BlockLockedChest(95)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setBlockName("lockedchest").setTickOnLoad(true).disableNeighborNotifyOnMetadataChange();
	public static final Block trapdoor = (new BlockTrapDoor(96, Material.wood)).setHardness(3.0F).setStepSound(soundWoodFootstep).setBlockName("trapdoor").disableStats().disableNeighborNotifyOnMetadataChange();
	public int blockIndexInTexture;
	public final int blockID;
	protected float blockHardness;
	protected float blockResistance;
	protected boolean blockConstructorCalled;
	protected boolean enableStats;
	public double minX;
	public double minY;
	public double minZ;
	public double maxX;
	public double maxY;
	public double maxZ;
	public StepSound stepSound;
	public float blockParticleGravity;
	public final Material blockMaterial;
	public float slipperiness;
	private String blockName;

	protected Block(int i1, Material material2) {
		this.blockConstructorCalled = true;
		this.enableStats = true;
		this.stepSound = soundPowderFootstep;
		this.blockParticleGravity = 1.0F;
		this.slipperiness = 0.6F;
		if(blocksList[i1] != null) {
			throw new IllegalArgumentException("Slot " + i1 + " is already occupied by " + blocksList[i1] + " when adding " + this);
		} else {
			this.blockMaterial = material2;
			blocksList[i1] = this;
			this.blockID = i1;
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			opaqueCubeLookup[i1] = this.isOpaqueCube();
			lightOpacity[i1] = this.isOpaqueCube() ? 255 : 0;
			canBlockGrass[i1] = !material2.getCanBlockGrass();
			isBlockContainer[i1] = false;
		}
	}

	protected Block disableNeighborNotifyOnMetadataChange() {
		field_28032_t[this.blockID] = true;
		return this;
	}

	protected void initializeBlock() {
	}

	protected Block(int i1, int i2, Material material3) {
		this(i1, material3);
		this.blockIndexInTexture = i2;
	}

	protected Block setStepSound(StepSound stepSound1) {
		this.stepSound = stepSound1;
		return this;
	}

	protected Block setLightOpacity(int i1) {
		lightOpacity[this.blockID] = i1;
		return this;
	}

	protected Block setLightValue(float f1) {
		lightValue[this.blockID] = (int)(15.0F * f1);
		return this;
	}

	protected Block setResistance(float f1) {
		this.blockResistance = f1 * 3.0F;
		return this;
	}

	public boolean renderAsNormalBlock() {
		return true;
	}

	public int getRenderType() {
		return 0;
	}

	protected Block setHardness(float f1) {
		this.blockHardness = f1;
		if(this.blockResistance < f1 * 5.0F) {
			this.blockResistance = f1 * 5.0F;
		}

		return this;
	}

	protected Block setBlockUnbreakable() {
		this.setHardness(-1.0F);
		return this;
	}

	public float getHardness() {
		return this.blockHardness;
	}

	protected Block setTickOnLoad(boolean z1) {
		tickOnLoad[this.blockID] = z1;
		return this;
	}

	public void setBlockBounds(float f1, float f2, float f3, float f4, float f5, float f6) {
		this.minX = (double)f1;
		this.minY = (double)f2;
		this.minZ = (double)f3;
		this.maxX = (double)f4;
		this.maxY = (double)f5;
		this.maxZ = (double)f6;
	}

	public float getBlockBrightness(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
		return iBlockAccess1.getBrightness(i2, i3, i4, lightValue[this.blockID]);
	}

	public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
		return i5 == 0 && this.minY > 0.0D ? true : (i5 == 1 && this.maxY < 1.0D ? true : (i5 == 2 && this.minZ > 0.0D ? true : (i5 == 3 && this.maxZ < 1.0D ? true : (i5 == 4 && this.minX > 0.0D ? true : (i5 == 5 && this.maxX < 1.0D ? true : !iBlockAccess1.isBlockOpaqueCube(i2, i3, i4))))));
	}

	public boolean getIsBlockSolid(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
		return iBlockAccess1.getBlockMaterial(i2, i3, i4).isSolid();
	}

	public int getBlockTexture(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
		return this.getBlockTextureFromSideAndMetadata(i5, iBlockAccess1.getBlockMetadata(i2, i3, i4));
	}

	public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
		return this.getBlockTextureFromSide(i1);
	}

	public int getBlockTextureFromSide(int i1) {
		return this.blockIndexInTexture;
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
		return AxisAlignedBB.getBoundingBoxFromPool((double)i2 + this.minX, (double)i3 + this.minY, (double)i4 + this.minZ, (double)i2 + this.maxX, (double)i3 + this.maxY, (double)i4 + this.maxZ);
	}

	public void getCollidingBoundingBoxes(World world1, int i2, int i3, int i4, AxisAlignedBB axisAlignedBB5, ArrayList arrayList6) {
		AxisAlignedBB axisAlignedBB7 = this.getCollisionBoundingBoxFromPool(world1, i2, i3, i4);
		if(axisAlignedBB7 != null && axisAlignedBB5.intersectsWith(axisAlignedBB7)) {
			arrayList6.add(axisAlignedBB7);
		}

	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
		return AxisAlignedBB.getBoundingBoxFromPool((double)i2 + this.minX, (double)i3 + this.minY, (double)i4 + this.minZ, (double)i2 + this.maxX, (double)i3 + this.maxY, (double)i4 + this.maxZ);
	}

	public boolean isOpaqueCube() {
		return true;
	}

	public boolean canCollideCheck(int i1, boolean z2) {
		return this.isCollidable();
	}

	public boolean isCollidable() {
		return true;
	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
	}

	public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
	}

	public void onBlockDestroyedByPlayer(World world1, int i2, int i3, int i4, int i5) {
	}

	public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
	}

	public int tickRate() {
		return 10;
	}

	public void onBlockAdded(World world1, int i2, int i3, int i4) {
	}

	public void onBlockRemoval(World world1, int i2, int i3, int i4) {
	}

	public int quantityDropped(Random random1) {
		return 1;
	}

	public int idDropped(int i1, Random random2) {
		return this.blockID;
	}

	public float blockStrength(EntityPlayer entityPlayer1) {
		return this.blockHardness < 0.0F ? 0.0F : (!entityPlayer1.canHarvestBlock(this) ? 1.0F / this.blockHardness / 100.0F : entityPlayer1.getCurrentPlayerStrVsBlock(this) / this.blockHardness / 30.0F);
	}

	public final void dropBlockAsItem(World world1, int i2, int i3, int i4, int i5) {
		this.dropBlockAsItemWithChance(world1, i2, i3, i4, i5, 1.0F);
	}

	public void dropBlockAsItemWithChance(World world1, int i2, int i3, int i4, int i5, float f6) {
		if(!world1.multiplayerWorld) {
			int i7 = this.quantityDropped(world1.rand);

			for(int i8 = 0; i8 < i7; ++i8) {
				if(world1.rand.nextFloat() <= f6) {
					int i9 = this.idDropped(i5, world1.rand);
					if(i9 > 0) {
						this.dropBlockAsItem_do(world1, i2, i3, i4, new ItemStack(i9, 1, this.damageDropped(i5)));
					}
				}
			}

		}
	}

	protected void dropBlockAsItem_do(World world1, int i2, int i3, int i4, ItemStack itemStack5) {
		if(!world1.multiplayerWorld) {
			float f6 = 0.7F;
			double d7 = (double)(world1.rand.nextFloat() * f6) + (double)(1.0F - f6) * 0.5D;
			double d9 = (double)(world1.rand.nextFloat() * f6) + (double)(1.0F - f6) * 0.5D;
			double d11 = (double)(world1.rand.nextFloat() * f6) + (double)(1.0F - f6) * 0.5D;
			EntityItem entityItem13 = new EntityItem(world1, (double)i2 + d7, (double)i3 + d9, (double)i4 + d11, itemStack5);
			entityItem13.delayBeforeCanPickup = 10;
			world1.entityJoinedWorld(entityItem13);
		}
	}

	protected int damageDropped(int i1) {
		return 0;
	}

	public float getExplosionResistance(Entity entity1) {
		return this.blockResistance / 5.0F;
	}

	public MovingObjectPosition collisionRayTrace(World world1, int i2, int i3, int i4, Vec3D vec3D5, Vec3D vec3D6) {
		this.setBlockBoundsBasedOnState(world1, i2, i3, i4);
		vec3D5 = vec3D5.addVector((double)(-i2), (double)(-i3), (double)(-i4));
		vec3D6 = vec3D6.addVector((double)(-i2), (double)(-i3), (double)(-i4));
		Vec3D vec3D7 = vec3D5.getIntermediateWithXValue(vec3D6, this.minX);
		Vec3D vec3D8 = vec3D5.getIntermediateWithXValue(vec3D6, this.maxX);
		Vec3D vec3D9 = vec3D5.getIntermediateWithYValue(vec3D6, this.minY);
		Vec3D vec3D10 = vec3D5.getIntermediateWithYValue(vec3D6, this.maxY);
		Vec3D vec3D11 = vec3D5.getIntermediateWithZValue(vec3D6, this.minZ);
		Vec3D vec3D12 = vec3D5.getIntermediateWithZValue(vec3D6, this.maxZ);
		if(!this.isVecInsideYZBounds(vec3D7)) {
			vec3D7 = null;
		}

		if(!this.isVecInsideYZBounds(vec3D8)) {
			vec3D8 = null;
		}

		if(!this.isVecInsideXZBounds(vec3D9)) {
			vec3D9 = null;
		}

		if(!this.isVecInsideXZBounds(vec3D10)) {
			vec3D10 = null;
		}

		if(!this.isVecInsideXYBounds(vec3D11)) {
			vec3D11 = null;
		}

		if(!this.isVecInsideXYBounds(vec3D12)) {
			vec3D12 = null;
		}

		Vec3D vec3D13 = null;
		if(vec3D7 != null && (vec3D13 == null || vec3D5.distanceTo(vec3D7) < vec3D5.distanceTo(vec3D13))) {
			vec3D13 = vec3D7;
		}

		if(vec3D8 != null && (vec3D13 == null || vec3D5.distanceTo(vec3D8) < vec3D5.distanceTo(vec3D13))) {
			vec3D13 = vec3D8;
		}

		if(vec3D9 != null && (vec3D13 == null || vec3D5.distanceTo(vec3D9) < vec3D5.distanceTo(vec3D13))) {
			vec3D13 = vec3D9;
		}

		if(vec3D10 != null && (vec3D13 == null || vec3D5.distanceTo(vec3D10) < vec3D5.distanceTo(vec3D13))) {
			vec3D13 = vec3D10;
		}

		if(vec3D11 != null && (vec3D13 == null || vec3D5.distanceTo(vec3D11) < vec3D5.distanceTo(vec3D13))) {
			vec3D13 = vec3D11;
		}

		if(vec3D12 != null && (vec3D13 == null || vec3D5.distanceTo(vec3D12) < vec3D5.distanceTo(vec3D13))) {
			vec3D13 = vec3D12;
		}

		if(vec3D13 == null) {
			return null;
		} else {
			byte b14 = -1;
			if(vec3D13 == vec3D7) {
				b14 = 4;
			}

			if(vec3D13 == vec3D8) {
				b14 = 5;
			}

			if(vec3D13 == vec3D9) {
				b14 = 0;
			}

			if(vec3D13 == vec3D10) {
				b14 = 1;
			}

			if(vec3D13 == vec3D11) {
				b14 = 2;
			}

			if(vec3D13 == vec3D12) {
				b14 = 3;
			}

			return new MovingObjectPosition(i2, i3, i4, b14, vec3D13.addVector((double)i2, (double)i3, (double)i4));
		}
	}

	private boolean isVecInsideYZBounds(Vec3D vec3D1) {
		return vec3D1 == null ? false : vec3D1.yCoord >= this.minY && vec3D1.yCoord <= this.maxY && vec3D1.zCoord >= this.minZ && vec3D1.zCoord <= this.maxZ;
	}

	private boolean isVecInsideXZBounds(Vec3D vec3D1) {
		return vec3D1 == null ? false : vec3D1.xCoord >= this.minX && vec3D1.xCoord <= this.maxX && vec3D1.zCoord >= this.minZ && vec3D1.zCoord <= this.maxZ;
	}

	private boolean isVecInsideXYBounds(Vec3D vec3D1) {
		return vec3D1 == null ? false : vec3D1.xCoord >= this.minX && vec3D1.xCoord <= this.maxX && vec3D1.yCoord >= this.minY && vec3D1.yCoord <= this.maxY;
	}

	public void onBlockDestroyedByExplosion(World world1, int i2, int i3, int i4) {
	}

	public int getRenderBlockPass() {
		return 0;
	}

	public boolean canPlaceBlockOnSide(World world1, int i2, int i3, int i4, int i5) {
		return this.canPlaceBlockAt(world1, i2, i3, i4);
	}

	public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
		int i5 = world1.getBlockId(i2, i3, i4);
		return i5 == 0 || blocksList[i5].blockMaterial.getIsGroundCover();
	}

	public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
		return false;
	}

	public void onEntityWalking(World world1, int i2, int i3, int i4, Entity entity5) {
	}

	public void onBlockPlaced(World world1, int i2, int i3, int i4, int i5) {
	}

	public void onBlockClicked(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
	}

	public void velocityToAddToEntity(World world1, int i2, int i3, int i4, Entity entity5, Vec3D vec3D6) {
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
	}

	public int getRenderColor(int i1) {
		return 0xFFFFFF;
	}

	public int colorMultiplier(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
		return 0xFFFFFF;
	}

	public boolean isPoweringTo(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
		return false;
	}

	public boolean canProvidePower() {
		return false;
	}

	public void onEntityCollidedWithBlock(World world1, int i2, int i3, int i4, Entity entity5) {
	}

	public boolean isIndirectlyPoweringTo(World world1, int i2, int i3, int i4, int i5) {
		return false;
	}

	public void setBlockBoundsForItemRender() {
	}

	public void harvestBlock(World world1, EntityPlayer entityPlayer2, int i3, int i4, int i5, int i6) {
		entityPlayer2.addStat(StatList.mineBlockStatArray[this.blockID], 1);
		this.dropBlockAsItem(world1, i3, i4, i5, i6);
	}

	public boolean canBlockStay(World world1, int i2, int i3, int i4) {
		return true;
	}

	public void onBlockPlacedBy(World world1, int i2, int i3, int i4, EntityLiving entityLiving5) {
	}

	public Block setBlockName(String string1) {
		this.blockName = "tile." + string1;
		return this;
	}

	public String translateBlockName() {
		return StatCollector.translateToLocal(this.getBlockName() + ".name");
	}

	public String getBlockName() {
		return this.blockName;
	}

	public void playBlock(World world1, int i2, int i3, int i4, int i5, int i6) {
	}

	public boolean getEnableStats() {
		return this.enableStats;
	}

	protected Block disableStats() {
		this.enableStats = false;
		return this;
	}

	public int getMobilityFlag() {
		return this.blockMaterial.getMaterialMobility();
	}

	static {
		Item.itemsList[cloth.blockID] = (new ItemCloth(cloth.blockID - 256)).setItemName("cloth");
		Item.itemsList[wood.blockID] = (new ItemLog(wood.blockID - 256)).setItemName("log");
		Item.itemsList[stairSingle.blockID] = (new ItemSlab(stairSingle.blockID - 256)).setItemName("stoneSlab");
		Item.itemsList[sapling.blockID] = (new ItemSapling(sapling.blockID - 256)).setItemName("sapling");
		Item.itemsList[leaves.blockID] = (new ItemLeaves(leaves.blockID - 256)).setItemName("leaves");
		Item.itemsList[pistonBase.blockID] = new ItemPiston(pistonBase.blockID - 256);
		Item.itemsList[pistonStickyBase.blockID] = new ItemPiston(pistonStickyBase.blockID - 256);

		for(int i0 = 0; i0 < 256; ++i0) {
			if(blocksList[i0] != null && Item.itemsList[i0] == null) {
				Item.itemsList[i0] = new ItemBlock(i0 - 256);
				blocksList[i0].initializeBlock();
			}
		}

		canBlockGrass[0] = true;
		StatList.func_25154_a();
	}
}
