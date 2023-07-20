package xyz.k4czp3r.crafterinfo;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.k4czp3r.crafterinfo.apis.WebSocketApi;
import xyz.k4czp3r.crafterinfo.events.PlayerEvents;
import xyz.k4czp3r.crafterinfo.tasks.StatsTask;
import xyz.k4czp3r.crafterinfo.utils.ConfigUtils;
import xyz.k4czp3r.crafterinfo.utils.SingletonUtils;


/**
 * Main plugin class.
 */
public class CrafterInfo extends JavaPlugin {

  @Override
  public void onEnable() {
    Logger.getInstance(getComponentLogger());

    ConfigUtils.createDefaultConfig(this);
    Logger.getInstance(null).info("Registering player events!");
    getServer().getPluginManager().registerEvents(
        new PlayerEvents(),
        this
    );

    Logger.getInstance(null)
        .info("Registering stats task, interval: " + ConfigUtils.getStatsTaskInterval(this) + "s.");
    getServer()
        .getScheduler()
        .scheduleSyncRepeatingTask(
            this,
            new StatsTask(),
            0,
            20L * ConfigUtils.getStatsTaskInterval(this)
        );

    try {

      Logger.getInstance(null)
          .info("Starting WebSocket server on port: " + ConfigUtils.getWebSocketPort(this));
      SingletonUtils.getInstance()
          .registerInstance(new WebSocketApi(ConfigUtils.getWebSocketPort(this)));

      SingletonUtils.getInstance().getInstance(WebSocketApi.class).start();
    } catch (Exception e) {
      throw new RuntimeException("Failed to start WebSocket server!", e);
    }
  }

}
