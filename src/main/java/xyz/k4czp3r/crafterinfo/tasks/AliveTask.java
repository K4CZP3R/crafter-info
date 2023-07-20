package xyz.k4czp3r.crafterinfo.tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import xyz.k4czp3r.crafterinfo.Logger;
import xyz.k4czp3r.crafterinfo.apis.WebSocketApi;
import xyz.k4czp3r.crafterinfo.utils.SingletonUtils;

/**
 * Repeatable task to broadcast alive message using WebSocket.
 */
public class AliveTask implements Runnable {

  @Override
  public void run() {
    try {
      SingletonUtils.getInstance().getInstance(WebSocketApi.class)
          .broadcast(Map.of("type", "alive", "data", "alive"));
    } catch (JsonProcessingException e) {
      Logger.getInstance(null).error("Failed to broadcast alive!");
    }
  }
}
