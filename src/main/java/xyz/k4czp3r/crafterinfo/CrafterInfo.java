package xyz.k4czp3r.crafterinfo;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.k4czp3r.crafterinfo.apis.WebSocketApi;
import xyz.k4czp3r.crafterinfo.events.PlayerEvents;
import xyz.k4czp3r.crafterinfo.tasks.StatsTask;
import xyz.k4czp3r.crafterinfo.utils.ConfigUtils;


public class CrafterInfo extends JavaPlugin {

  @Override
  public void onEnable() {
    Logger.getInstance(getComponentLogger());

    var config = new ConfigUtils(this);
    config.createDefaultConfig();





    Logger.getInstance(null).info("Registering player events!");
    getServer().getPluginManager().registerEvents(
            new PlayerEvents(),
            this
    );

    Logger.getInstance(null).info("Registering stats task, interval:"+ config.getStatsTaskInterval()+"s.");
    getServer()
            .getScheduler()
            .scheduleSyncRepeatingTask(
                    this,
                    new StatsTask(),
                    0,
                    20L * config.getStatsTaskInterval()
            );


    try {
      Logger.getInstance(null).info("Starting WebSocket server on port:"+config.getWebSocketPort());
      WebSocketApi.getInstance(config.getWebSocketPort()).start();
    } catch (Exception e) {
      throw new RuntimeException("Failed to start WebSocket server!", e);
    }
  }

}
