package com.mogukun.teru.listeners;

import com.mogukun.teru.check.PlayerUtil;
import com.mogukun.teru.check.checks.autoclicker.AutoClickerA;
import com.mogukun.teru.commands.TeruCommand;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.managers.PacketWrapper;
import com.mogukun.teru.managers.TeruData;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PacketListener extends PlayerConnection {
    public PacketListener(MinecraftServer minecraftServer, NetworkManager networkManager, EntityPlayer entityPlayer) {
        super(minecraftServer, networkManager, entityPlayer);
    }

    public static HashMap<UUID, Long> disableTime = new HashMap<>();
    public static HashMap<UUID, Long> lastVelocityTaken = new HashMap<>();
    HashMap<UUID, Location> lastLocation = new HashMap<>();
    HashMap<UUID, TeruData> lastTeruData = new HashMap<>();

    @Override
    public void a(PacketPlayInFlying packet) {
        super.a(packet);

        Player player = this.player.getBukkitEntity().getPlayer();

        Location loc = player.getLocation();

        if (lastLocation.get(player.getUniqueId()) != null){
            TeruData data = new TeruData();

            data.player = player;

            if(!data.player.isOnGround()){
                data.airtick++;
            }

            boolean isStandingOnBlock = PlayerUtil.isStandingOnBlock(player);

            if( !isStandingOnBlock ){
                data.serverAirTick++;
            }

            data.to = loc;
            data.from = lastLocation.get(player.getUniqueId());

            data.deltaX = Math.abs(data.to.getX() - data.from.getX());
            data.deltaY = Math.abs(data.to.getY() - data.from.getY());
            data.deltaZ = Math.abs(data.to.getZ() - data.from.getZ());

            data.deltaXZ = Math.hypot(Math.abs(data.deltaX), Math.abs(data.deltaZ));

            data.deltaYaw = Math.abs(data.to.getYaw() - data.from.getYaw());


            if( lastTeruData.get(player.getUniqueId()) != null ){
                TeruData oldData = lastTeruData.get(player.getUniqueId());

                data.airtick = oldData.airtick;
                data.serverAirTick = oldData.serverAirTick;

                if(!data.player.isOnGround()){
                    data.airtick++;
                }else {
                    data.airtick = 0;
                }

                if( !isStandingOnBlock ){
                    data.serverAirTick++;
                }else {
                    data.serverAirTick = 0;
                }

                data.lastDeltaX = oldData.deltaX;
                data.lastDeltaY = oldData.deltaY;
                data.lastDeltaZ = oldData.deltaZ;

                data.lastDeltaXZ = oldData.deltaXZ;

                data.lastDeltaYaw = oldData.deltaYaw;

                TeruTickEvent terutick = new TeruTickEvent(data);
                Bukkit.getPluginManager().callEvent( terutick );
            }

            lastTeruData.put(player.getUniqueId(), data);
        }

        lastLocation.put(player.getUniqueId(), loc);
    }



    @Override
    public void a(PacketPlayInUseEntity packet){
        super.a(packet);

        Player player = this.player.getBukkitEntity().getPlayer();
        PacketWrapper wrapper = new PacketWrapper(packet,packet.getClass());

        if( wrapper.get("a") == null || TeruCommand.botID.get(player.getUniqueId()) == null ){
            return;
        }

        int botId = TeruCommand.botID.get(player.getUniqueId());
        int entityId = wrapper.get("a");

        if ( botId == entityId ){
            TeruCommand.attackCount.put(botId,TeruCommand.attackCount.get(botId) + 1);
        }


        PlayerUtil.setDigging(player,false);
    }

    @Override
    public void a(PacketPlayInBlockDig packet){
        super.a(packet);

        if( packet.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK ) PlayerUtil.setDigging(this.player.getBukkitEntity(),true);
        if( packet.c() == PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK ) PlayerUtil.setDigging(this.player.getBukkitEntity(),false);
        if( packet.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK ) PlayerUtil.setDigging(this.player.getBukkitEntity(),false);
    }


}
