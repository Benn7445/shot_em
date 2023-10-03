package me.quartz.shot_em;

import me.quartz.shot_em.scoreHelper.ScoreManager;
import me.quartz.shot_em.build.BuildManager;
import me.quartz.shot_em.game.GameManager;
import me.quartz.shot_em.listeners.*;
import me.quartz.shot_em.localPlayer.LocalPlayerManager;
import me.quartz.shot_em.mysql.MySQLManager;
import me.quartz.shot_em.papi.PlaceholderAPI;
import me.quartz.shot_em.worlds.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShotEm extends JavaPlugin {

    private static ShotEm instance;

    private MySQLManager mysqlManager;
    private WorldManager worldsManager;
    private LocalPlayerManager localPlayerManager;
    private GameManager gameManager;
    private BuildManager buildManager;
    private ScoreManager scoreManager;

    public static ShotEm getInstance() {
        return instance;
    }

    public MySQLManager getMysqlManager() {
        return mysqlManager;
    }

    public WorldManager getWorldsManager() {
        return worldsManager;
    }

    public LocalPlayerManager getLocalPlayerManager() {
        return localPlayerManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public BuildManager getBuildManager() {
        return buildManager;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    @Override
    public void onEnable() {
        registerShotEm();
        registerManagers();
        registerListeners();
        registerPAPI();
    }

    @Override
    public void onDisable() {
        mysqlManager.disableMySQL();
        unregisterShotEm();
        Bukkit.getServer().shutdown();
    }

    /*
    Setup ShotEm instance with config's.
    Remove all the NPCs
     */
    private void registerShotEm() {
        instance = this;
        saveDefaultConfig();
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "npc remove all");
    }

    /*
    Initialize all the managers
     */
    private void registerManagers() {
        mysqlManager = new MySQLManager();
        worldsManager = new WorldManager();
        localPlayerManager = new LocalPlayerManager();
        gameManager = new GameManager();
        buildManager = new BuildManager();
        scoreManager = new ScoreManager();
    }

    /*
    Register all the event listeners
     */
    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChangeWorldListener(), this);
        Bukkit.getPluginManager().registerEvents(new NPCDamageByEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileLaunchListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new WeatherChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }

    /*
    Register all the event listeners
     */
    private void registerPAPI() {
        if (this.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPI().register();
        }
    }

    /*
    Unload all the worlds that has been generated to play on.
    Also removing all the NPCs
     */
    private void unregisterShotEm() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            ShotEm.getInstance().getMysqlManager().saveLocalPlayer(localPlayerManager.getLocalPlayer(player));
            World world = player.getWorld();
            player.kickPlayer("");
            worldsManager.unloadWorld(world);
        }
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "npc remove all");
    }
}
