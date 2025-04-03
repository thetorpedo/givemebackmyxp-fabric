package torpedo.gmbmxp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class GiveMeBackMyXP implements ModInitializer {
	public static final String MOD_ID = "gmbmxp";

	@Override
	public void onInitialize() {
		ConfigManager.loadConfig(); // Carregar configurações do arquivo

		ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
			if (entity instanceof PlayerEntity player) {
				ServerWorld world = (ServerWorld) player.getWorld();

				if (!world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
					float multiplier = ConfigManager.config.multiplier;
					int xp = (int) (multiplier * getTotalXP(player));

					ExperienceOrbEntity xpOrb = new ExperienceOrbEntity(world, player.getX(), player.getY(), player.getZ(), xp);
					world.spawnEntity(xpOrb);
				}
			}
		});
	}

	private int getTotalXP(PlayerEntity player) {
		int level= player.experienceLevel;
		int xp = 0;
		int nextLevel = 0;
		int progressXp = 0;

		// Calculating current experience points
		if (level < 17){
			xp = (level * level) + (6 * level);
		} else if (level < 32){
			xp = (int) (2.5 * (level * level) - (40.5 * level) + 360);
		} else {
			xp = (int) (4.5 * (level * level) - (162.5 * level) + 2220);
		}

		// Calculating xp progress to next level
		if (level < 16){
			nextLevel = (2 * level) + 7;
		} else if (level < 31){
			nextLevel = (5 * level) - 38;
		} else {
			nextLevel = (9 * level) - 158;
		}
		progressXp = (int) (player.experienceProgress * nextLevel);

		return xp + progressXp;
	}


}