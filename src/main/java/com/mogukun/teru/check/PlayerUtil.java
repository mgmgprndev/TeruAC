package com.mogukun.teru.check;

import com.mogukun.teru.listeners.FlyingPacketEvent;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class PlayerUtil {


    public static void addPlayer(Player p){
        CraftPlayer craftPlayer = (CraftPlayer) p;
        EntityPlayer entityPlayer = craftPlayer.getHandle();

        NetworkManager networkManager = entityPlayer.playerConnection.networkManager;
        MinecraftServer minecraftServer = ((CraftServer) craftPlayer.getServer()).getServer();

        entityPlayer.playerConnection = new FlyingPacketEvent(minecraftServer, networkManager, entityPlayer);
    }

    public static void disableFor(Player player){
        disableFor(player, 10);
    }


    public static void disableFor(Player player, long sec){
        long mill = sec * 1000;
        FlyingPacketEvent.disableTime.put(player.getUniqueId(), System.currentTimeMillis() + mill);
    }



    public static boolean isStandingOnBlock(Player player) {
        Vector location = player.getLocation().toVector();
        double feetY = location.getY() - 0.5;

        Block blockBelow = player.getWorld().getBlockAt(player.getLocation().subtract(0, 0.5, 0));

        if (blockBelow.getType() != Material.AIR) {
            return true;
        }

        double blockMinX = blockBelow.getX() - 1.5;
        double blockMaxX = blockBelow.getX() + 1.5;
        double blockMinY = blockBelow.getY() - 0.5;
        double blockMaxY = blockBelow.getY() + 0.5;
        double blockMinZ = blockBelow.getZ() - 1.5;
        double blockMaxZ = blockBelow.getZ() + 1.5;

        return location.getX() >= blockMinX && location.getX() <= blockMaxX &&
                location.getZ() >= blockMinZ && location.getZ() <= blockMaxZ &&
                feetY >= blockMinY && feetY <= blockMaxY;
    }

}
