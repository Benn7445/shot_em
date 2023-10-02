package me.quartz.shot_em.game;

import me.quartz.shot_em.ShotEm;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.*;

public class Game {

    private final UUID uuid;
    private boolean shot;
    private Location lastLocation;
    private List<NPC> npcs;
    private int score;

    public Game(Player player) {
        this.uuid = player.getUniqueId();
        this.shot = false;
        this.lastLocation = null;
        this.npcs = new ArrayList<>();
        this.score = 0;
        createTargets();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public boolean hasShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void generateNextLocation() {
        Player player = this.getPlayer();
        lastLocation = getLocation(player);
        Location npcLocation = ShotEm.getInstance().getBuildManager().createBotPlatform(this.getLastLocation());
        Bukkit.getScheduler().scheduleSyncDelayedTask(ShotEm.getInstance(), () -> {
            NPC npc = npcs.get(new Random().nextInt(npcs.size()));
            if(npc.getEntity() != null) npc.getEntity().teleport(npcLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
            else npc.spawn(npcLocation);
            npc.faceLocation(this.getPlayer().getLocation());
            LivingEntity entity = (LivingEntity) npc.getEntity();
            entity.setCustomNameVisible(true);
            npc.data().setPersistent(NPC.Metadata.NAMEPLATE_VISIBLE, entity.isCustomNameVisible());
        }, 10);
    }

    private Location getLocation(Player player) {
        Location location = new Location(player.getWorld(), 242, 51, 1348, -90, 0);
        Random r = new Random();
        location.setY(r.nextInt(52 - 48) + 48);
        if (r.nextBoolean()) location.add(r.nextInt((getDistance() + 1) - getDistance()) + getDistance(), 0, 0);
        else location.subtract(r.nextInt((getDistance() + 1) - getDistance()) + getDistance(), 0, 0);
        if (r.nextBoolean()) location.add(0, 0, r.nextInt((getDistance() + 1) - getDistance()) + getDistance());
        else location.subtract(0, 0, r.nextInt((getDistance() + 1) - getDistance()) + getDistance());
        return location;
    }

    public int getScore() {
        return score;
    }

    public void addScore() {
        this.score += 1;
    }

    public int getDistance() {
        if (score < 5) return 7;
        else if (score < 7) return 11;
        else if (score < 14) return 16;
        else if (score < 20) return 24;
        else if (score < 26) return 30;
        else if (score < 30) return 35;
        else if (score < 40) return 40;
        return 10;
    }

    public int getPointsRange() {
        Set<String> list = ShotEm.getInstance().getConfig().getConfigurationSection("points").getKeys(false);
        for(String i : list) {
            if(score < Integer.parseInt(i)) return ShotEm.getInstance().getConfig().getInt("points." + i);
        }
        return 1;
    }

    public void endGame() {
        ShotEm.getInstance().getGameManager().stopGame(this);
        for (int i = 0; i < 100; i++) getPlayer().sendMessage(" ");
        getPlayer().sendMessage(ChatColor.RED + "You've lost " + ChatColor.YELLOW + "25 points" + ChatColor.RED + " for missing!");
        getPlayer().sendMessage(ChatColor.RED + "Your score was: " + ChatColor.WHITE + score);
        Location location = lastLocation;
        location.subtract(1, 2, 0);
        location.add(0, 0, 1);
        ShotEm.getInstance().getBuildManager().clearPlatform(location);
        for (NPC npc : npcs)
            ShotEm.getInstance().getServer().dispatchCommand(ShotEm.getInstance().getServer().getConsoleSender(), "npc remove " + npc.getId());
        Bukkit.getScheduler().scheduleSyncDelayedTask(ShotEm.getInstance(), () -> ShotEm.getInstance().getGameManager().startGame(getPlayer()), 20);
    }

    private void createTargets() {
        List<String> targets = ShotEm.getInstance().getConfig().getStringList("targets");
        for (String target : targets) {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, target);
            npc.spawn(getPlayer().getLocation().subtract(0, 4,0));
            LivingEntity entity = (LivingEntity) npc.getEntity();
            entity.setCustomNameVisible(false);
            npc.data().setPersistent(NPC.Metadata.NAMEPLATE_VISIBLE, entity.isCustomNameVisible());
            npcs.add(npc);
        }
        generateNextLocation();
    }
}
