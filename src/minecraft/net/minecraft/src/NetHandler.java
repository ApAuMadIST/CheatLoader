package net.minecraft.src;

public abstract class NetHandler {
	public abstract boolean isServerHandler();

	public void handleMapChunk(Packet51MapChunk packet51MapChunk1) {
	}

	public void registerPacket(Packet packet1) {
	}

	public void handleErrorMessage(String string1, Object[] object2) {
	}

	public void handleKickDisconnect(Packet255KickDisconnect packet255KickDisconnect1) {
		this.registerPacket(packet255KickDisconnect1);
	}

	public void handleLogin(Packet1Login packet1Login1) {
		this.registerPacket(packet1Login1);
	}

	public void handleFlying(Packet10Flying packet10Flying1) {
		this.registerPacket(packet10Flying1);
	}

	public void handleMultiBlockChange(Packet52MultiBlockChange packet52MultiBlockChange1) {
		this.registerPacket(packet52MultiBlockChange1);
	}

	public void handleBlockDig(Packet14BlockDig packet14BlockDig1) {
		this.registerPacket(packet14BlockDig1);
	}

	public void handleBlockChange(Packet53BlockChange packet53BlockChange1) {
		this.registerPacket(packet53BlockChange1);
	}

	public void handlePreChunk(Packet50PreChunk packet50PreChunk1) {
		this.registerPacket(packet50PreChunk1);
	}

	public void handleNamedEntitySpawn(Packet20NamedEntitySpawn packet20NamedEntitySpawn1) {
		this.registerPacket(packet20NamedEntitySpawn1);
	}

	public void handleEntity(Packet30Entity packet30Entity1) {
		this.registerPacket(packet30Entity1);
	}

	public void handleEntityTeleport(Packet34EntityTeleport packet34EntityTeleport1) {
		this.registerPacket(packet34EntityTeleport1);
	}

	public void handlePlace(Packet15Place packet15Place1) {
		this.registerPacket(packet15Place1);
	}

	public void handleBlockItemSwitch(Packet16BlockItemSwitch packet16BlockItemSwitch1) {
		this.registerPacket(packet16BlockItemSwitch1);
	}

	public void handleDestroyEntity(Packet29DestroyEntity packet29DestroyEntity1) {
		this.registerPacket(packet29DestroyEntity1);
	}

	public void handlePickupSpawn(Packet21PickupSpawn packet21PickupSpawn1) {
		this.registerPacket(packet21PickupSpawn1);
	}

	public void handleCollect(Packet22Collect packet22Collect1) {
		this.registerPacket(packet22Collect1);
	}

	public void handleChat(Packet3Chat packet3Chat1) {
		this.registerPacket(packet3Chat1);
	}

	public void handleVehicleSpawn(Packet23VehicleSpawn packet23VehicleSpawn1) {
		this.registerPacket(packet23VehicleSpawn1);
	}

	public void handleArmAnimation(Packet18Animation packet18Animation1) {
		this.registerPacket(packet18Animation1);
	}

	public void func_21147_a(Packet19EntityAction packet19EntityAction1) {
		this.registerPacket(packet19EntityAction1);
	}

	public void handleHandshake(Packet2Handshake packet2Handshake1) {
		this.registerPacket(packet2Handshake1);
	}

	public void handleMobSpawn(Packet24MobSpawn packet24MobSpawn1) {
		this.registerPacket(packet24MobSpawn1);
	}

	public void handleUpdateTime(Packet4UpdateTime packet4UpdateTime1) {
		this.registerPacket(packet4UpdateTime1);
	}

	public void handleSpawnPosition(Packet6SpawnPosition packet6SpawnPosition1) {
		this.registerPacket(packet6SpawnPosition1);
	}

	public void func_6498_a(Packet28EntityVelocity packet28EntityVelocity1) {
		this.registerPacket(packet28EntityVelocity1);
	}

	public void func_21148_a(Packet40EntityMetadata packet40EntityMetadata1) {
		this.registerPacket(packet40EntityMetadata1);
	}

	public void func_6497_a(Packet39AttachEntity packet39AttachEntity1) {
		this.registerPacket(packet39AttachEntity1);
	}

	public void handleUseEntity(Packet7UseEntity packet7UseEntity1) {
		this.registerPacket(packet7UseEntity1);
	}

	public void func_9447_a(Packet38EntityStatus packet38EntityStatus1) {
		this.registerPacket(packet38EntityStatus1);
	}

	public void handleHealth(Packet8UpdateHealth packet8UpdateHealth1) {
		this.registerPacket(packet8UpdateHealth1);
	}

	public void func_9448_a(Packet9Respawn packet9Respawn1) {
		this.registerPacket(packet9Respawn1);
	}

	public void func_12245_a(Packet60Explosion packet60Explosion1) {
		this.registerPacket(packet60Explosion1);
	}

	public void func_20087_a(Packet100OpenWindow packet100OpenWindow1) {
		this.registerPacket(packet100OpenWindow1);
	}

	public void func_20092_a(Packet101CloseWindow packet101CloseWindow1) {
		this.registerPacket(packet101CloseWindow1);
	}

	public void func_20091_a(Packet102WindowClick packet102WindowClick1) {
		this.registerPacket(packet102WindowClick1);
	}

	public void func_20088_a(Packet103SetSlot packet103SetSlot1) {
		this.registerPacket(packet103SetSlot1);
	}

	public void func_20094_a(Packet104WindowItems packet104WindowItems1) {
		this.registerPacket(packet104WindowItems1);
	}

	public void handleSignUpdate(Packet130UpdateSign packet130UpdateSign1) {
		this.registerPacket(packet130UpdateSign1);
	}

	public void func_20090_a(Packet105UpdateProgressbar packet105UpdateProgressbar1) {
		this.registerPacket(packet105UpdateProgressbar1);
	}

	public void handlePlayerInventory(Packet5PlayerInventory packet5PlayerInventory1) {
		this.registerPacket(packet5PlayerInventory1);
	}

	public void func_20089_a(Packet106Transaction packet106Transaction1) {
		this.registerPacket(packet106Transaction1);
	}

	public void func_21146_a(Packet25EntityPainting packet25EntityPainting1) {
		this.registerPacket(packet25EntityPainting1);
	}

	public void handleNotePlay(Packet54PlayNoteBlock packet54PlayNoteBlock1) {
		this.registerPacket(packet54PlayNoteBlock1);
	}

	public void func_27245_a(Packet200Statistic packet200Statistic1) {
		this.registerPacket(packet200Statistic1);
	}

	public void func_22186_a(Packet17Sleep packet17Sleep1) {
		this.registerPacket(packet17Sleep1);
	}

	public void func_22185_a(Packet27Position packet27Position1) {
		this.registerPacket(packet27Position1);
	}

	public void func_25118_a(Packet70Bed packet70Bed1) {
		this.registerPacket(packet70Bed1);
	}

	public void handleWeather(Packet71Weather packet71Weather1) {
		this.registerPacket(packet71Weather1);
	}

	public void func_28116_a(Packet131MapData packet131MapData1) {
		this.registerPacket(packet131MapData1);
	}

	public void func_28115_a(Packet61DoorChange packet61DoorChange1) {
		this.registerPacket(packet61DoorChange1);
	}
}
