package me.quartz.shot_em.listeners;
import me.quartz.shot_em.ShotEm;
import me.quartz.shot_em.game.Game;
import me.quartz.shot_em.localPlayer.LocalPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunchListener implements Listener {

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        if(event.getEntity().getShooter() instanceof Player) {
            if (event.getEntity() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getEntity();
                arrow.setBounce(false);
                Player player = (Player) event.getEntity().getShooter();
                Game game = ShotEm.getInstance().getGameManager().getGame(player);
                if (game != null) {
                    if (!game.hasShot()) {
                        game.setShot(true);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(ShotEm.getInstance(), () -> {
                            if (game.hasShot()) {
                                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
                                game.endGame();
                                LocalPlayer localPlayer = ShotEm.getInstance().getLocalPlayerManager().getLocalPlayer(player);
                                localPlayer.removePoints();
                            }
                        }, 20);
                    } else event.setCancelled(true);
                }
            }
        }
    }
}
