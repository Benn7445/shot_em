package me.quartz.shot_em.listeners;

import me.quartz.shot_em.ShotEm;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        createNewWorld(player);
        hideAllPlayers(player);
        sendMessage(event);
        setInventory(player);
        ShotEm.getInstance().getGameManager().startGame(player);
        ShotEm.getInstance().getScoreManager().createScoreboard(player);
    }

    /*
    Create a new world for the user
     */
    private void createNewWorld(Player player) {
        World world = ShotEm.getInstance().getWorldsManager().copyWorld(player.getName());
        player.teleport(new Location(world, 242.5, 51, 1348.5, -90, 0));
        player.sendMessage(player.getWorld().getName());
    }

    /*
    Set the players inventory
     */
    private void setInventory(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        bow.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().setItem(0, bow);
        player.getInventory().setItem(1, new ItemStack(Material.ARROW, 1));
    }

    /*
    Send the join message to the player
     */
    private void sendMessage(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }


    /*
    Hide each player on join
     */
    private void hideAllPlayers(Player player) {
        for(Player players : Bukkit.getOnlinePlayers()) {
            player.hidePlayer(players);
            players.hidePlayer(player);
        }
    }
}
