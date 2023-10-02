package me.quartz.shot_em.listeners;

import me.quartz.shot_em.ShotEm;
import me.quartz.shot_em.game.Game;
import me.quartz.shot_em.localPlayer.LocalPlayer;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.ai.speech.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class NPCDamageByEntityListener implements Listener {

    @EventHandler
    public void onNPCDamageByEntityEvent(NPCDamageByEntityEvent event) {
        if(event.getDamager() instanceof Arrow) {
            Arrow arrow = ((Arrow) event.getDamager());
            if(arrow.getShooter() instanceof Player) {
                Player player = ((Player) arrow.getShooter());
                Game game = ShotEm.getInstance().getGameManager().getGame(player);
                if(game != null) {
                    game.setShot(false);
                    game.addScore();
                    LocalPlayer localPlayer = ShotEm.getInstance().getLocalPlayerManager().getLocalPlayer(player);
                    localPlayer.addPoints(game.getPointsRange());
                    if(game.getScore() > localPlayer.getHighScore()) localPlayer.setHighScore(game.getScore());
                    NPC npc = event.getNPC();
                    LivingEntity entity = (LivingEntity) npc.getEntity();
                    entity.setCustomNameVisible(false);
                    npc.data().setPersistent(NPC.Metadata.NAMEPLATE_VISIBLE, entity.isCustomNameVisible());
                    npc.teleport(new Location(player.getWorld(), 242.5, 46, 1348.5), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    event.getDamager().remove();
                    Location clearLoc = game.getLastLocation();
                    clearLoc.subtract(1,2,0);
                    clearLoc.add(0, 0, 1);
                    ShotEm.getInstance().getBuildManager().clearPlatform(clearLoc);
                    game.generateNextLocation();
                    player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
                    for(int i = 0; i < 100; i++) player.sendMessage(" ");
                    player.sendMessage(ChatColor.GREEN + "You've gained " + ChatColor.AQUA + game.getPointsRange() + " point(s)" + ChatColor.GREEN + " for hitting your target!");
                    player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Target hit!" + ChatColor.YELLOW + " Score: " + game.getScore() + " Points: " + localPlayer.getPoints());
                }
            }
        }
        event.setCancelled(true);
    }
}
