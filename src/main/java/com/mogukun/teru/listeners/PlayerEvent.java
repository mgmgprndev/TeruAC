package com.mogukun.teru.listeners;

import com.mogukun.teru.check.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

import java.util.HashMap;

public class PlayerEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        PlayerUtil.addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        PacketListener.disableTime.remove(event.getPlayer().getUniqueId());
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
