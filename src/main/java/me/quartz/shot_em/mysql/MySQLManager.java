package me.quartz.shot_em.mysql;

import me.quartz.shot_em.ShotEm;
import me.quartz.shot_em.localPlayer.LocalPlayer;
import org.bukkit.entity.Player;

import java.sql.*;

public class MySQLManager {

    final String username = ShotEm.getInstance().getConfig().getString("db.username");
    final String password = ShotEm.getInstance().getConfig().getString("db.password");
    final String url = "jdbc:mysql://" +
            ShotEm.getInstance().getConfig().getString("db.host") + ":" +
            ShotEm.getInstance().getConfig().getInt("db.port") + "/" +
            ShotEm.getInstance().getConfig().getString("db.database");

    static Connection connection;

    public MySQLManager() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            createDatabaseTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disableMySQL() {
        try {
            if (connection!=null && !connection.isClosed()) {
                connection.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public LocalPlayer fetchLocalPlayer(Player player) {
        String sql = "SELECT * FROM localPlayers WHERE uuid=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, player.getUniqueId().toString());
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                LocalPlayer localPlayer = new LocalPlayer(
                        player,
                        results.getInt("points"),
                        results.getInt("highScore"),
                        results.getInt("timePlayed")
                );
                return localPlayer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void saveLocalPlayer(LocalPlayer player) {
        String sql = "INSERT INTO localPlayers(uuid, points, highScore, timePlayed) VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, player.getPlayer().getUniqueId().toString());
            stmt.setInt(2, player.getPoints());
            stmt.setInt(3, player.getHighScore());
            stmt.setInt(4, player.getTimePlayed());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDatabaseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS localPlayers(uuid varchar(64), points INT, highScore INT, timePlayed INT);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
