package com.mogukun.teru.listeners;

import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.managers.TeruData;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.UUID;

public class FlyingPacketEvent extends PlayerConnection {
    public FlyingPacketEvent(MinecraftServer minecraftServer, NetworkManager networkManager, EntityPlayer entityPlayer) {
        super(minecraftServer, networkManager, entityPlayer);
    }

    HashMap<UUID, Location> lastLocation = new HashMap<>();
    HashMap<UUID, TeruData> lastTeruData = new HashMap<>();

    @Override
    public void a(PacketPlayInFlying packet) {
        super.a(packet);

        Player player = this.player.getBukkitEntity().getPlayer();

//        if(packet instanceof PacketPlayInFlying.PacketPlayInPosition || packet instanceof PacketPlayInFlying.PacketPlayInPositionLook || packet instanceof PacketPlayInFlying.PacketPlayInLook) {
//            player.sendMessage("ah fuck!");
//        }

        Location loc = player.getLocation();

        if (lastLocation.get(player.getUniqueId()) != null){
            TeruData data = new TeruData();

            data.player = player;
            data.to = loc;
            data.from = lastLocation.get(player.getUniqueId());

            data.deltaX = Math.abs(data.to.getX() - data.from.getX());
            data.deltaY = Math.abs(data.to.getY() - data.from.getY());
            data.deltaZ = Math.abs(data.to.getZ() - data.from.getZ());

            if( lastTeruData.get(player.getUniqueId()) != null ){
                TeruData oldData = lastTeruData.get(player.getUniqueId());

                data.lastDeltaX = oldData.deltaX;
                data.lastDeltaY = oldData.deltaY;
                data.lastDeltaZ = oldData.deltaZ;

                TeruTickEvent terutick = new TeruTickEvent(data);
                Bukkit.getPluginManager().callEvent( terutick );
            }

            lastTeruData.put(player.getUniqueId(), data);
        }

        lastLocation.put(player.getUniqueId(), loc);
    }
}
