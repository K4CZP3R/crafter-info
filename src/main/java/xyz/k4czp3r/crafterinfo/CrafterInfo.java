package xyz.k4czp3r.crafterinfo;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.k4czp3r.crafterinfo.apis.WebSocketApi;
import xyz.k4czp3r.crafterinfo.events.PlayerEvents;
import xyz.k4czp3r.crafterinfo.tasks.StatsTask;


public class CrafterInfo extends JavaPlugin {

  @Override
  public void onEnable() {
    Logger.getInstance(getComponentLogger());


    saveDefaultConfig();




    Logger.getInstance(null).info("Registering player events!");
    getServer().getPluginManager().registerEvents(
            new PlayerEvents(),
            this
    );

    Logger.getInstance(null).info("Registering stats task!");
    getServer()
            .getScheduler()
            .scheduleSyncRepeatingTask(
                    this,
                    new StatsTask(),
                    0,
                    20L * 10
            );


    try {
      WebSocketApi.getInstance(4444).start();
    } catch (Exception e) {
      throw new RuntimeException("Failed to start WebSocket server!", e);
    }
  }

}
