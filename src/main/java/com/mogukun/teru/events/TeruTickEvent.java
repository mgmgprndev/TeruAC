package com.mogukun.teru.events;

import com.mogukun.teru.Teru;
import com.mogukun.teru.check.PlayerUtil;
import com.mogukun.teru.listeners.PacketListener;
import com.mogukun.teru.managers.TeruData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class TeruTickEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private TeruData data;
    private boolean legInAir, isStandingOnBlock;

    private boolean aboveBlock, belowBlock, inWater;
    public boolean tooSmallY;

    public boolean isMathGround;

    public HashMap<PotionEffectType, Integer> amplifiers;

    public long lastVelocityTaken;

    public TeruTickEvent(TeruData data){
        this.data = data;
        Material material = data.player.getLocation().getBlock().getType();
        this.legInAir = material == Material.AIR;

        this.inWater = material == Material.WATER || data.player.getEyeLocation().getBlock().getType() == Material.WATER
                || material == Material.STATIONARY_WATER || data.player.getEyeLocation().getBlock().getType() == Material.STATIONARY_WATER
                || material == Material.LAVA || data.player.getEyeLocation().getBlock().getType() == Material.LAVA
                || material == Material.STATIONARY_LAVA || data.player.getEyeLocation().getBlock().getType() == Material.STATIONARY_LAVA;

        this.aboveBlock = data.player.getEyeLocation().add(0,1,0).getBlock().getType() != Material.AIR;
        this.belowBlock = data.player.getEyeLocation().subtract(0,1,0).getBlock().getType() != Material.AIR;

        this.isStandingOnBlock = PlayerUtil.isStandingOnBlock(data.player);

        this.tooSmallY = Math.floor( getTo().getY() * 10 ) / 10 == Math.floor( getTo().getY() );

        this.amplifiers = PlayerUtil.getAmplifier(getPlayer());

        this.isMathGround = getTo().getY() % (1/64D) < 1E-4; // Credit: DerRedstoner's CheatGuard

        PacketListener.lastVelocityTaken.putIfAbsent(getPlayer().getUniqueId(), 0L);
        this.lastVelocityTaken = PacketListener.lastVelocityTaken.get(getPlayer().getUniqueId());
    }

    @Deprecated
    public TeruData getData() {
        return data;
    }

    public Player getPlayer(){
        return this.data.player;
    }

    public int getAmplifier(PotionEffectType type){
        return this.amplifiers.get(type);
    }


    public boolean isLegInAir(){
        return legInAir;
    }

    public boolean isAboveBlock(){
        return this.aboveBlock;
    }

    public boolean isBelowBlock(){
        return this.belowBlock;
    }

    public boolean isInWater(){
        return this.inWater;
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
