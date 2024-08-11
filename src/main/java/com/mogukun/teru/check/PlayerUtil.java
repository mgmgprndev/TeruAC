package com.mogukun.teru.check;

import com.mogukun.teru.listeners.PacketListener;
import com.mogukun.teru.managers.ViolationData;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerUtil {


    public static void addPlayer(Player p){
        CraftPlayer craftPlayer = (CraftPlayer) p;
        EntityPlayer entityPlayer = craftPlayer.getHandle();

        NetworkManager networkManager = entityPlayer.playerConnection.networkManager;
        MinecraftServer minecraftServer = ((CraftServer) craftPlayer.getServer()).getServer();

        entityPlayer.playerConnection = new PacketListener(minecraftServer, networkManager, entityPlayer);
    }

    public static void disableFor(Player player){
        disableFor(player, 10);
    }


    public static void disableFor(Player player, long sec){
        long mill = sec * 1000;
        PacketListener.disableTime.put(player.getUniqueId(), System.currentTimeMillis() + mill);
    }



    public static boolean isStandingOnBlock(Player player) {
        Location playerLocation = player.getLocation();
        Vector playerPos = playerLocation.toVector();

        double checkY = 0.5;

        double feetY = playerPos.getY() - checkY;

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int zOffset = -1; zOffset <= 1; zOffset++) {
                for(int yOffset = 0; yOffset <= 1; yOffset++ ){

                    Block block = player.getWorld().getBlockAt(
                            playerLocation.getBlockX() + xOffset,
                            playerLocation.getBlockY() - yOffset,
                            playerLocation.getBlockZ() + zOffset
                    );

                    double blockMinX = block.getX() - 1.5;
                    double blockMaxX = block.getX() + 1.5;
                    double blockMinY = block.getY() - checkY;
                    double blockMaxY = block.getY() + checkY;
                    double blockMinZ = block.getZ() - 1.5;
                    double blockMaxZ = block.getZ() + 1.5;

                    boolean isColliding = playerPos.getX() >= blockMinX && playerPos.getX() <= blockMaxX &&
                            playerPos.getZ() >= blockMinZ && playerPos.getZ() <= blockMaxZ &&
                            feetY >= blockMinY && feetY <= blockMaxY;

                    if (isColliding && block.getType() != Material.AIR) {
                        return true;
                    }

                }
            }
        }

        return false;
    }


    public static HashMap<UUID,ArrayList<ViolationData>> vl = new HashMap<>();

    public static int[] countVL(UUID uuid, String check, String type){
        vl.computeIfAbsent(uuid, k -> new ArrayList<>());

        ArrayList<ViolationData> array = vl.get(uuid);

        ViolationData data = new ViolationData(check, type);

        array.add(data);

        int i = 0;
        for( ViolationData d : array ){
            if( d.check == check && d.type == type  ){
                i++;
            }
        }

        vl.put(uuid, array);

        return new int[]{ i, array.size() };
    }

    public static HashMap<PotionEffectType, Integer> getAmplifier(Player player){
        HashMap<PotionEffectType, Integer> amplifiers = new HashMap<>();
        for( PotionEffectType effectType : PotionEffectType.values() ) amplifiers.put(effectType,0);
        for( PotionEffect effect : player.getActivePotionEffects()) amplifiers.put(effect.getType(), effect.getAmplifier());
        return amplifiers;
    }

    public static HashMap<UUID, Boolean> isDigging = new HashMap<>();


    public static boolean isDigging(Player player) {
        UUID uuid = player.getUniqueId();

        isDigging.putIfAbsent(uuid, false);

        return isDigging.get(uuid);
    }

    public static void setDigging(Player player, boolean v) {
        UUID uuid = player.getUniqueId();
        isDigging.put(uuid, v);
    }

}
