package com.mogukun.teru.events;

import com.mogukun.teru.Teru;
import com.mogukun.teru.check.PlayerUtil;
import com.mogukun.teru.managers.TeruData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeruTickEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private TeruData data;
    private boolean legInAir, isStandingOnBlock;

    public TeruTickEvent(TeruData data){
        this.data = data;
        Material material = data.player.getLocation().getBlock().getType();
        this.legInAir = material == Material.AIR;
        this.isStandingOnBlock = PlayerUtil.isStandingOnBlock(data.player);
        //this.legInAir = data.player.getLocation().getBlock().getType().isBlock();
        // material == Material.AIR || material == Material.GRASS || material == Material.LONG_GRASS || material == Material.REDSTONE_WIRE;;
    }

    @Deprecated
    public TeruData getData() {
        return data;
    }

    public Player getPlayer(){
        return this.data.player;
    }


    public boolean isLegInAir(){
        return legInAir;
    }

    public boolean isStandingOnBlock() {
        return isStandingOnBlock;
    }

    public Location getTo(){
        return this.data.to;
    }

    public Location getFrom(){
        return this.data.from;
    }

    public double getDeltaX(){
        return this.data.deltaX;
    }

    public double getDeltaY(){
        return this.data.deltaY;
    }

    public double getDeltaZ(){
        return this.data.deltaZ;
    }

    public double getDeltaXZ(){
        return this.data.deltaXZ;
    }

    public double getLastDeltaX(){
        return this.data.lastDeltaX;
    }

    public double getLastDeltaY(){
        return this.data.lastDeltaY;
    }

    public double getLastDeltaZ(){
        return this.data.lastDeltaZ;
    }

    public double getLastDeltaXZ(){
        return this.data.lastDeltaXZ;
    }

    public int getAirTick(){
        return this.data.airtick;
    }

    public int getServerAirTick(){
        return this.data.serverAirTick;
    }


    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
