package me.quartz.shot_em.game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private final List<Game> games = new ArrayList<>();


    public Game getGame(Player player) {
        for(Game game : games) {
            if(game.getPlayer() == player) return game;
        }
        return null;
    }

    public void stopGame(Game game) {
        games.remove(game);
    }

    public void startGame(Player player) {
        if(getGame(player) == null) {
            Game game = new Game(player);
            games.add(game);
            for (int i = 0; i < 100; i++) player.sendMessage(" ");
            player.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "A new round has been started.");
            player.sendMessage(" ");
            player.sendTitle(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Good Luck!", "");
        }
    }
}
