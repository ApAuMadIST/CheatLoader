package net.minecraft.src;

import java.io.File;
import java.util.Random;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager {
	private static SoundSystem sndSystem;
	private SoundPool soundPoolSounds = new SoundPool();
	private SoundPool soundPoolStreaming = new SoundPool();
	private SoundPool soundPoolMusic = new SoundPool();
	private int field_587_e = 0;
	private GameSettings options;
	private static boolean loaded = false;
	private Random rand = new Random();
	private int ticksBeforeMusic = this.rand.nextInt(12000);

	public void loadSoundSettings(GameSettings gameSettings1) {
		this.soundPoolStreaming.field_1657_b = false;
		this.options = gameSettings1;
		if(!loaded && (gameSettings1 == null || gameSettings1.soundVolume != 0.0F || gameSettings1.musicVolume != 0.0F)) {
			this.tryToSetLibraryAndCodecs();
		}

	}

	private void tryToSetLibraryAndCodecs() {
		try {
			float f1 = this.options.soundVolume;
			float f2 = this.options.musicVolume;
			this.options.soundVolume = 0.0F;
			this.options.musicVolume = 0.0F;
			this.options.saveOptions();
			SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
			SoundSystemConfig.setCodec("mus", CodecMus.class);
			SoundSystemConfig.setCodec("wav", CodecWav.class);
			sndSystem = new SoundSystem();
			this.options.soundVolume = f1;
			this.options.musicVolume = f2;
			this.options.saveOptions();
		} catch (Throwable throwable3) {
			throwable3.printStackTrace();
			System.err.println("error linking with the LibraryJavaSound plug-in");
		}

		loaded = true;
	}

	public void onSoundOptionsChanged() {
		if(!loaded && (this.options.soundVolume != 0.0F || this.options.musicVolume != 0.0F)) {
			this.tryToSetLibraryAndCodecs();
		}

		if(loaded) {
			if(this.options.musicVolume == 0.0F) {
				sndSystem.stop("BgMusic");
			} else {
				sndSystem.setVolume("BgMusic", this.options.musicVolume);
			}
		}

	}

	public void closeMinecraft() {
		if(loaded) {
			sndSystem.cleanup();
		}

	}

	public void addSound(String string1, File file2) {
		this.soundPoolSounds.addSound(string1, file2);
	}

	public void addStreaming(String string1, File file2) {
		this.soundPoolStreaming.addSound(string1, file2);
	}

	public void addMusic(String string1, File file2) {
		this.soundPoolMusic.addSound(string1, file2);
	}

	public void playRandomMusicIfReady() {
		if(loaded && this.options.musicVolume != 0.0F) {
			if(!sndSystem.playing("BgMusic") && !sndSystem.playing("streaming")) {
				if(this.ticksBeforeMusic > 0) {
					--this.ticksBeforeMusic;
					return;
				}

				SoundPoolEntry soundPoolEntry1 = this.soundPoolMusic.getRandomSound();
				if(soundPoolEntry1 != null) {
					this.ticksBeforeMusic = this.rand.nextInt(12000) + 12000;
					sndSystem.backgroundMusic("BgMusic", soundPoolEntry1.soundUrl, soundPoolEntry1.soundName, false);
					sndSystem.setVolume("BgMusic", this.options.musicVolume);
					sndSystem.play("BgMusic");
				}
			}

		}
	}

	public void func_338_a(EntityLiving entityLiving1, float f2) {
		if(loaded && this.options.soundVolume != 0.0F) {
			if(entityLiving1 != null) {
				float f3 = entityLiving1.prevRotationYaw + (entityLiving1.rotationYaw - entityLiving1.prevRotationYaw) * f2;
				double d4 = entityLiving1.prevPosX + (entityLiving1.posX - entityLiving1.prevPosX) * (double)f2;
				double d6 = entityLiving1.prevPosY + (entityLiving1.posY - entityLiving1.prevPosY) * (double)f2;
				double d8 = entityLiving1.prevPosZ + (entityLiving1.posZ - entityLiving1.prevPosZ) * (double)f2;
				float f10 = MathHelper.cos(-f3 * 0.017453292F - (float)Math.PI);
				float f11 = MathHelper.sin(-f3 * 0.017453292F - (float)Math.PI);
				float f12 = -f11;
				float f13 = 0.0F;
				float f14 = -f10;
				float f15 = 0.0F;
				float f16 = 1.0F;
				float f17 = 0.0F;
				sndSystem.setListenerPosition((float)d4, (float)d6, (float)d8);
				sndSystem.setListenerOrientation(f12, f13, f14, f15, f16, f17);
			}
		}
	}

	public void playStreaming(String string1, float f2, float f3, float f4, float f5, float f6) {
		if(loaded && this.options.soundVolume != 0.0F) {
			String string7 = "streaming";
			if(sndSystem.playing("streaming")) {
				sndSystem.stop("streaming");
			}

			if(string1 != null) {
				SoundPoolEntry soundPoolEntry8 = this.soundPoolStreaming.getRandomSoundFromSoundPool(string1);
				if(soundPoolEntry8 != null && f5 > 0.0F) {
					if(sndSystem.playing("BgMusic")) {
						sndSystem.stop("BgMusic");
					}

					float f9 = 16.0F;
					sndSystem.newStreamingSource(true, string7, soundPoolEntry8.soundUrl, soundPoolEntry8.soundName, false, f2, f3, f4, 2, f9 * 4.0F);
					sndSystem.setVolume(string7, 0.5F * this.options.soundVolume);
					sndSystem.play(string7);
				}

			}
		}
	}

	public void playSound(String string1, float f2, float f3, float f4, float f5, float f6) {
		if(loaded && this.options.soundVolume != 0.0F) {
			SoundPoolEntry soundPoolEntry7 = this.soundPoolSounds.getRandomSoundFromSoundPool(string1);
			if(soundPoolEntry7 != null && f5 > 0.0F) {
				this.field_587_e = (this.field_587_e + 1) % 256;
				String string8 = "sound_" + this.field_587_e;
				float f9 = 16.0F;
				if(f5 > 1.0F) {
					f9 *= f5;
				}

				sndSystem.newSource(f5 > 1.0F, string8, soundPoolEntry7.soundUrl, soundPoolEntry7.soundName, false, f2, f3, f4, 2, f9);
				sndSystem.setPitch(string8, f6);
				if(f5 > 1.0F) {
					f5 = 1.0F;
				}

				sndSystem.setVolume(string8, f5 * this.options.soundVolume);
				sndSystem.play(string8);
			}

		}
	}

	public void playSoundFX(String string1, float f2, float f3) {
		if(loaded && this.options.soundVolume != 0.0F) {
			SoundPoolEntry soundPoolEntry4 = this.soundPoolSounds.getRandomSoundFromSoundPool(string1);
			if(soundPoolEntry4 != null) {
				this.field_587_e = (this.field_587_e + 1) % 256;
				String string5 = "sound_" + this.field_587_e;
				sndSystem.newSource(false, string5, soundPoolEntry4.soundUrl, soundPoolEntry4.soundName, false, 0.0F, 0.0F, 0.0F, 0, 0.0F);
				if(f2 > 1.0F) {
					f2 = 1.0F;
				}

				f2 *= 0.25F;
				sndSystem.setPitch(string5, f3);
				sndSystem.setVolume(string5, f2 * this.options.soundVolume);
				sndSystem.play(string5);
			}

		}
	}
}
