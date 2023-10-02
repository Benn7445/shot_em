package me.quartz.shot_em.scoreHelper;

import me.quartz.shot_em.ShotEm;
import me.quartz.shot_em.game.Game;
import me.quartz.shot_em.localPlayer.LocalPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreManager {

    public ScoreManager() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) updateScoreboard(player);
            }
        }.runTaskTimer(ShotEm.getInstance(), 20L, 20L);
    }

    public void createScoreboard(Player player) {
        LocalPlayer localPlayer = ShotEm.getInstance().getLocalPlayerManager().getLocalPlayer(player);
        Game game = ShotEm.getInstance().getGameManager().getGame(player);
        ScoreHelper helper = ScoreHelper.createScore(player);
        helper.setTitle("&a&lShotEm");
        helper.setSlot(6, "&6&lScore");
        helper.setSlot(5, (game != null ? game.getScore() : 0) + "");
        helper.setSlot(4, " ");
        helper.setSlot(3, "&6&lPersonal");
        helper.setSlot(2, "Points: " + ChatColor.YELLOW + localPlayer.getPoints());
        helper.setSlot(1, "Best Score: " + ChatColor.YELLOW + localPlayer.getHighScore());
    }

    public void updateScoreboard(Player player) {
        LocalPlayer localPlayer = ShotEm.getInstance().getLocalPlayerManager().getLocalPlayer(player);
        Game game = ShotEm.getInstance().getGameManager().getGame(player);
        if(ScoreHelper.hasScore(player)) {
            ScoreHelper helper = ScoreHelper.getByPlayer(player);
            helper.setSlot(5, (game != null ? game.getScore() : 0) + "");
            helper.setSlot(2, "Points: " + ChatColor.YELLOW + localPlayer.getPoints());
            helper.setSlot(1, "Best Score: " + ChatColor.YELLOW + localPlayer.getHighScore());
        }
    }
}