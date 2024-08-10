package com.mogukun.teru.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeruData {

    public TeruData(){}

    public Player player;
    public Location to, from;
    public double deltaX, deltaY, deltaZ, deltaXZ, deltaYaw;
    public double lastDeltaX, lastDeltaY, lastDeltaZ, lastDeltaXZ, lastDeltaYaw;

    public int airtick = 0;
    public int serverAirTick = 0;

}
