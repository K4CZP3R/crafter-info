package xyz.k4czp3r.crafterinfo.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.k4czp3r.crafterinfo.models.ConfigEntry;

public class ConfigUtils {
    private JavaPlugin javaPlugin;

    public ConfigUtils(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public void createDefaultConfig() {
        FileConfiguration config = javaPlugin.getConfig();
        for(ConfigEntry configEntry : ConfigEntry.values()) {
            switch (configEntry){
                case WEBSOCKET_PORT:
                    config.addDefault(configEntry.name(), 8888);
                    break;
                case STATS_TASK_INTERVAL:
                    config.addDefault(configEntry.name(), 20);
                    break;
            }
        }

        config.options().copyDefaults(true);
        javaPlugin.saveConfig();
    }

    public int getWebSocketPort() {
        return javaPlugin.getConfig().getInt(ConfigEntry.WEBSOCKET_PORT.name());
    }

    public int getStatsTaskInterval() {
        return javaPlugin.getConfig().getInt(ConfigEntry.STATS_TASK_INTERVAL.name());
    }
}
