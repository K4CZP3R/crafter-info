package xyz.k4czp3r.crafterinfo.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import xyz.k4czp3r.crafterinfo.Logger;
import xyz.k4czp3r.crafterinfo.apis.WebSocketApi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StatsTask implements Runnable {

    static class StatsUtils {
        public static List<Statistic> COMMON_STATISTICS = Arrays.asList(
                Statistic.DEATHS,
                Statistic.WALK_ONE_CM,
                Statistic.SPRINT_ONE_CM,
                Statistic.TIME_SINCE_REST,
                Statistic.TIME_SINCE_DEATH,
                Statistic.PLAY_ONE_MINUTE
        );
    }

    private HashMap<String, Integer> getStatistics(Player player) {
        HashMap<String, Integer> stats = new HashMap<>();
        for(Statistic statistic : StatsUtils.COMMON_STATISTICS) {
            stats.put(statistic.toString(), player.getStatistic(statistic));
        }
        return stats;
    }
    @Override
    public void run() {

        HashMap<String, Map> allStats = new HashMap<>();

        for(Player player : Bukkit.getOnlinePlayers()) {
            HashMap<String, Integer> stats = getStatistics(player);
            allStats.put(player.getUniqueId().toString(), Map.of("playerName", player.getName(),  "playerStats", stats));

        }

        if(allStats.isEmpty()) {
            return;
        }

        try {
            WebSocketApi.getInstance(-1).broadcast(Map.of("type","stats","data",allStats));
        } catch (Exception e) {
            Logger.getInstance(null).error("Failed to broadcast stats!");
        }
    }
}
