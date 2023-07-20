package xyz.k4czp3r.crafterinfo.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.k4czp3r.crafterinfo.Logger;
import xyz.k4czp3r.crafterinfo.apis.WebSocketApi;
import xyz.k4czp3r.crafterinfo.utils.SingletonUtils;

/**
 * Listener class to handle player events.
 */
public class PlayerEvents implements Listener {

  /**
   * Handle player join event.
   *
   * @param event PlayerJoinEvent
   */
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    try {
      SingletonUtils.getInstance().getInstance(WebSocketApi.class)
          .broadcast(Map.of("type", "event_status", "data",
              Map.of("playerUuid", event.getPlayer().getUniqueId().toString(), "playerName",
                  event.getPlayer().getName(), "playerStatus", "online")));
    } catch (JsonProcessingException e) {
      Logger.getInstance(null).error("Failed to broadcast player join event!");
    }
  }

  /**
   * Handle player quit event.
   *
   * @param event PlayerQuitEvent
   */
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    try {
      SingletonUtils.getInstance().getInstance(WebSocketApi.class)
          .broadcast(Map.of("type", "event_status", "data",
              Map.of("playerUuid", event.getPlayer().getUniqueId().toString(), "playerName",
                  event.getPlayer().getName(), "playerStatus", "offline")));
    } catch (JsonProcessingException e) {
      Logger.getInstance(null).error("Failed to broadcast player quit event!");
    }
  }

  /**
   * Handle player entity damage event.
   *
   * @param event EntityDamageEvent
   */
  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    if (event.getEntity() instanceof Player player) {
      double healthAfterDamage = player.getHealth() - event.getFinalDamage();

      try {
        SingletonUtils.getInstance().getInstance(WebSocketApi.class)
            .broadcast(Map.of("type", "event_dmg", "data",
                Map.of("playerUuid", player.getUniqueId().toString(), "playerName",
                    player.getName(),
                    "playerStatus", "healthChange", "playerHealth", healthAfterDamage)));
      } catch (JsonProcessingException e) {
        Logger.getInstance(null).error("Failed to broadcast player entity damage event!");
      }
    }
  }

  /**
   * Handle player entity regain health event.
   *
   * @param event EntityRegainHealthEvent
   */
  @EventHandler
  public void onEntityRegainHealth(EntityRegainHealthEvent event) {
    if (event.getEntity() instanceof Player player) {
      double healthAfterRegain = player.getHealth() + event.getAmount();

      try {
        SingletonUtils.getInstance().getInstance(WebSocketApi.class)
            .broadcast(Map.of("type", "event_dmg", "data",
                Map.of("playerUuid", player.getUniqueId().toString(), "playerName",
                    player.getName(),
                    "playerStatus", "healthChange", "playerHealth", healthAfterRegain)));
      } catch (JsonProcessingException e) {
        Logger.getInstance(null).error("Failed to broadcast entity regain health event!");
      }
    }
  }


}
