package torpedo.gmbmxp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "givemebackmyxp.json");

    public static ModConfig config = new ModConfig();

    public static void loadConfig() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                config = GSON.fromJson(reader, ModConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            saveConfig();
        }
    }

    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ModConfig {
        public String[] _comment = {
                "Note: This is the percentage of the player total experience, not their levels.",
                "Example: A value of 0.7 means 70% of the total XP will be returned.",
                "If a player has 30 levels (which equals 1395 XP) when they die,",
                "they will drop 976 XP (70% of 1395), which is approximately 25 levels.",
                "Keep in mind that XP required per level is not linear. Higher levels need more XP."
        };
        public float multiplier = 0.7f;
    }
}