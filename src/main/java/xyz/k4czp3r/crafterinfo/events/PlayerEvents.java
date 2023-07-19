package xyz.k4czp3r.crafterinfo.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.k4czp3r.crafterinfo.apis.WebSocketApi;

import java.util.Map;

public class PlayerEvents implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        try {
            WebSocketApi.getInstance(-1).broadcast(Map.of("playerUuid", event.getPlayer().getUniqueId().toString(), "playerName", event.getPlayer().getName(), "playerStatus", "online"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        try {
            WebSocketApi.getInstance(-1).broadcast(Map.of("playerUuid", event.getPlayer().getUniqueId().toString(), "playerName", event.getPlayer().getName(), "playerStatus", "offline"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            double healthAfterDamage = player.getHealth() - event.getFinalDamage();
            System.out.println("Player's health after damage: " + healthAfterDamage);

            try {
                WebSocketApi.getInstance(-1).broadcast(Map.of(
                        "playerUuid", player.getUniqueId().toString(),
                        "playerName", player.getName(),
                        "playerStatus", "healthChange",
                        "playerHealth", healthAfterDamage
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player player) {
            double healthAfterRegain = player.getHealth() + event.getAmount();
            System.out.println("Player's health after regain: " + healthAfterRegain);

            try {
                WebSocketApi.getInstance(-1).broadcast(Map.of(
                        "playerUuid", player.getUniqueId().toString(),
                        "playerName", player.getName(),
                        "playerStatus", "healthChange",
                        "playerHealth", healthAfterRegain
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
