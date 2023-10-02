package me.quartz.shot_em.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
        for(Player players : player.getWorld().getPlayers()) {
            players.sendMessage(event.getFormat()
                    .replace("%1$s", player.getName())
                    .replace("%2$s", event.getMessage()));
        }
    }
}
