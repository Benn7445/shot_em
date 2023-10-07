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
        helper.setTitle(ShotEm.getInstance().getConfig().getString("scoreboard-title"));
        updateConfigScore(localPlayer, game, helper);
    }

    public void updateScoreboard(Player player) {
        LocalPlayer localPlayer = ShotEm.getInstance().getLocalPlayerManager().getLocalPlayer(player);
        Game game = ShotEm.getInstance().getGameManager().getGame(player);
        if(ScoreHelper.hasScore(player)) {
            ScoreHelper helper = ScoreHelper.getByPlayer(player);
            updateConfigScore(localPlayer, game, helper);
        }
    }

    private void updateConfigScore(LocalPlayer localPlayer, Game game, ScoreHelper helper) {
        int i = ShotEm.getInstance().getConfig().getStringList("scoreboard").size();
        for(String s : ShotEm.getInstance().getConfig().getStringList("scoreboard")) {
            i = i-1;
            helper.setSlot(i, s.
                    replace("%score%", (game != null ? game.getScore() : 0) + "").
                    replace("%points%", localPlayer.getPoints() + "").
                    replace("%highscore%", localPlayer.getHighScore() + "")
            );
        }
    }
}