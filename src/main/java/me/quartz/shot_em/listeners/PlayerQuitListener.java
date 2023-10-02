package me.quartz.shot_em.listeners;

import me.quartz.shot_em.ShotEm;
import me.quartz.shot_em.localPlayer.LocalPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Bukkit.getScheduler().scheduleSyncDelayedTask(ShotEm.getInstance(), () -> {
            if(world.getPlayers().isEmpty()) ShotEm.getInstance().getWorldsManager().unloadWorld(world);
        }, 5);
        LocalPlayer localPlayer = ShotEm.getInstance().getLocalPlayerManager().getLocalPlayer(player);
        ShotEm.getInstance().getMysqlManager().saveLocalPlayer(localPlayer);
    }
}
