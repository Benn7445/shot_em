package me.quartz.shot_em.listeners;

import me.quartz.shot_em.ShotEm;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(event.getTo().getY() < 50)
            player.teleport(new Location(player.getWorld(), 242.5, 51, 1348.5, -90, 0));
    }
}
