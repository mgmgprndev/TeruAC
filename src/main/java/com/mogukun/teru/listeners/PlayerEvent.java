package com.mogukun.teru.listeners;

import com.mogukun.teru.check.PlayerUtil;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

public class PlayerEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        PlayerUtil.addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        FlyingPacketEvent.disableTime.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        PlayerUtil.disableFor(event.getPlayer());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        PlayerUtil.disableFor(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){
        if ( event.getEntity() instanceof Player ){
            PlayerUtil.disableFor(((Player) event.getEntity()).getPlayer(), 2);
        }
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent event){
        PlayerUtil.disableFor(event.getPlayer());
    }

}
