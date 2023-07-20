package xyz.k4czp3r.crafterinfo.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.k4czp3r.crafterinfo.models.ConfigEntry;

/**
 * Config utilities used to access config values.
 */
public class ConfigUtils {
  
  /**
   * Creates default config values.
   */
  public static void createDefaultConfig(JavaPlugin plugin) {
    FileConfiguration config = plugin.getConfig();
    for (ConfigEntry configEntry : ConfigEntry.values()) {
      switch (configEntry) {
        case WEBSOCKET_PORT -> config.addDefault(configEntry.name(), 8888);
        case STATS_TASK_INTERVAL -> config.addDefault(configEntry.name(), 20);
        default -> throw new RuntimeException("Unknown config entry: " + configEntry.name());
      }
    }

    config.options().copyDefaults(true);
    plugin.saveConfig();
  }

  public static int getWebSocketPort(JavaPlugin plugin) {
    return plugin.getConfig().getInt(ConfigEntry.WEBSOCKET_PORT.name());
  }

  public static int getStatsTaskInterval(JavaPlugin plugin) {
    return plugin.getConfig().getInt(ConfigEntry.STATS_TASK_INTERVAL.name());
  }
}
