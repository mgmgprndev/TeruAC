package com.mogukun.teru.events;

import com.mogukun.teru.Teru;
import com.mogukun.teru.managers.TeruData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeruTickEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private TeruData data;

    public TeruTickEvent(TeruData data){
        this.data = data;
    }

    public Player getPlayer(){
        return this.data.player;
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

    public double getLastDeltaX(){
        return this.data.lastDeltaX;
    }

    public double getLastDeltaY(){
        return this.data.lastDeltaY;
    }

    public double getLastDeltaZ(){
        return this.data.lastDeltaZ;
    }


    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
