package com.mogukun.teru.managers;

import com.mogukun.teru.Teru;
import com.mogukun.teru.commands.TeruCommand;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;


public class AuraBotSpawner {
    public void spawnNPC(Player player) {

        if ( TeruCommand.botID.get( player.getUniqueId() ) != null ){
            return;
        }

        CraftPlayer craftPlayer = (CraftPlayer) player;

        WorldServer worldServer = ((CraftWorld) player.getWorld()).getHandle();

        CraftServer craftServer = (CraftServer) Bukkit.getServer();

        UUID randomUuid = UUID.randomUUID();
        GameProfile profile = new GameProfile(randomUuid, UUID.randomUUID().toString().replace("-", "").substring(0, 8) );
        EntityPlayer npc = new EntityPlayer(craftServer.getServer(), worldServer, profile, new PlayerInteractManager(worldServer));
        TeruCommand.botID.put(player.getUniqueId(),npc.getId());
        TeruCommand.attackCount.put(npc.getId(),0);

        Location loc = player.getLocation();

        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

        PacketPlayOutPlayerInfo addEntityPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc);
        PacketPlayOutNamedEntitySpawn spawnEntityPacket = new PacketPlayOutNamedEntitySpawn(npc);

        PlayerConnection connection = craftPlayer.getHandle().playerConnection;

        connection.sendPacket(addEntityPacket);
        connection.sendPacket(spawnEntityPacket);
        connection.sendPacket(new PacketPlayOutUpdateAttributes());

        PacketPlayOutPlayerInfo removeEntityPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc);
        PacketPlayOutEntityDestroy despawnEntityPacket = new PacketPlayOutEntityDestroy(npc.getId());

        BukkitTask task;

        task = new BukkitRunnable() {

            double angle = 0;
            double radius = 2.5;

            @Override
            public void run() {
                Location playerLocation = player.getLocation();
                angle += 0.22;

                double x = playerLocation.getX() + radius * Math.cos(angle);
                double z = playerLocation.getZ() + radius * Math.sin(angle);
                double y = playerLocation.getY() + 0.6;

                npc.setLocation(x,y,z,playerLocation.getYaw(), playerLocation.getPitch());
                PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport(npc);
                craftPlayer.getHandle().playerConnection.sendPacket(teleportPacket);
            }
        }.runTaskTimer(Teru.instance, 0, 1L);

        new BukkitRunnable() {

            @Override
            public void run() {

                connection.sendPacket(despawnEntityPacket);
                connection.sendPacket(removeEntityPacket);
                connection.sendPacket(new PacketPlayOutUpdateAttributes());

                TeruCommand.botID.remove( player.getUniqueId() );

                task.cancel();
                this.cancel();

            }

        }.runTaskLater(Teru.instance, 20 * 10);
    }
}


