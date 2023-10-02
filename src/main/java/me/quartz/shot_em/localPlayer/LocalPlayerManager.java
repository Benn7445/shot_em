package me.quartz.shot_em.localPlayer;

import me.quartz.shot_em.ShotEm;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LocalPlayerManager {

    private final List<LocalPlayer> localPlayers = new ArrayList<>();

    public LocalPlayer getLocalPlayer(Player player) {
        for(LocalPlayer localPlayer : localPlayers) {
            if(localPlayer.getPlayer().getUniqueId() == player.getUniqueId()) return localPlayer;
        }
        return createNewLocalPlayer(player);
    }

    private LocalPlayer createNewLocalPlayer(Player player) {
        LocalPlayer localPlayer = ShotEm.getInstance().getMysqlManager().fetchLocalPlayer(player);
        if(localPlayer == null) localPlayer = new LocalPlayer(player);
        localPlayers.add(localPlayer);
        return localPlayer;
    }
}
