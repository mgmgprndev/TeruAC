package com.mogukun.teru.check;

import com.mogukun.teru.listeners.FlyingPacketEvent;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerUtil {


    public static void addPlayer(Player p){
        CraftPlayer craftPlayer = (CraftPlayer) p;
        EntityPlayer entityPlayer = craftPlayer.getHandle();

        NetworkManager networkManager = entityPlayer.playerConnection.networkManager;
        MinecraftServer minecraftServer = ((CraftServer) craftPlayer.getServer()).getServer();

        entityPlayer.playerConnection = new FlyingPacketEvent(minecraftServer, networkManager, entityPlayer);
    }

}
