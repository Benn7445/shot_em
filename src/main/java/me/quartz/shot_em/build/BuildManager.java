package me.quartz.shot_em.build;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BuildManager {

    public Location createBotPlatform(Location location) {
        setGlassBlock(location, Material.DIAMOND_BLOCK);
        setGlassBlock(location.add(0,1,0), Material.BEACON);
        setGlassBlock(location.add(0,1,0), Material.STAINED_GLASS);
        location.subtract(0,1,0);
        Location npcLocation = new Location(location.getWorld(), location.getX() + 0.5, location.getY() + 2.5, location.getZ() + 0.5);
        setBlocksAbove(location.add(1, 0, 0), Material.DIAMOND_BLOCK, false);
        setBlocksAbove(location.add(0, 0, 1), Material.GOLD_BLOCK, true);
        setBlocksAbove(location.subtract(1, 0, 0), Material.DIAMOND_BLOCK, false);
        setBlocksAbove(location.subtract(1, 0, 0), Material.GOLD_BLOCK, true);
        setBlocksAbove(location.subtract(0, 0, 1), Material.DIAMOND_BLOCK, false);
        setBlocksAbove(location.subtract(0, 0, 1), Material.GOLD_BLOCK, true);
        setBlocksAbove(location.add(1, 0, 0), Material.DIAMOND_BLOCK, false);
        setBlocksAbove(location.add(1, 0, 0), Material.GOLD_BLOCK, true);
        location.subtract(1, 0, 0);
        location.add(0, 1, 1);
        setGlassBlock(location.add(1, 0, 0));
        setGlassBlock(location.add(0, 0, 1));
        setGlassBlock(location.subtract(1, 0, 0));
        setGlassBlock(location.subtract(1, 0, 0));
        setGlassBlock(location.subtract(0, 0, 1));
        setGlassBlock(location.subtract(0, 0, 1));
        setGlassBlock(location.add(1, 0, 0));
        setGlassBlock(location.add(1, 0, 0));
        return npcLocation;
    }

    public void clearPlatform(Location location) {
        setGlassBlock(location, Material.AIR);
        setGlassBlock(location.add(0,1,0), Material.AIR);
        setGlassBlock(location.add(0,1,0), Material.AIR);
        location.subtract(0,1,0);
        setBlocksAbove(location.add(1, 0, 0), Material.AIR, false);
        setBlocksAbove(location.add(0, 0, 1), Material.AIR, true);
        setBlocksAbove(location.subtract(1, 0, 0), Material.AIR, false);
        setBlocksAbove(location.subtract(1, 0, 0), Material.AIR, true);
        setBlocksAbove(location.subtract(0, 0, 1), Material.AIR, false);
        setBlocksAbove(location.subtract(0, 0, 1), Material.AIR, true);
        setBlocksAbove(location.add(1, 0, 0), Material.AIR, false);
        setBlocksAbove(location.add(1, 0, 0), Material.AIR, true);
        location.subtract(1, 0, 0);
        location.add(0, 1, 1);
        setGlassBlock(location.add(1, 0, 0), Material.AIR);
        setGlassBlock(location.add(0, 0, 1), Material.AIR);
        setGlassBlock(location.subtract(1, 0, 0), Material.AIR);
        setGlassBlock(location.subtract(1, 0, 0), Material.AIR);
        setGlassBlock(location.subtract(0, 0, 1), Material.AIR);
        setGlassBlock(location.subtract(0, 0, 1), Material.AIR);
        setGlassBlock(location.add(1, 0, 0), Material.AIR);
        setGlassBlock(location.add(1, 0, 0), Material.AIR);
    }

    public void setBlocksAbove(Location location, Material material, boolean up) {
        setGlassBlock(location, material);
        if(up) location = location.add(0,1,0);
        else location = location.subtract(0,1,0);
        setGlassBlock(location, material);
    }

    private void setGlassBlock(Location location, Material material) {
        Block block = location.getBlock();
        block.setType(material);
    }

    private void setGlassBlock(Location location) {
        Block block = location.getBlock();
        block.setType(Material.STAINED_GLASS);
        block.setData((byte) 5);
    }
}
