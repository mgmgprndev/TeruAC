package com.mogukun.teru.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeruData {

    public TeruData(){}

    public Player player;
    public Location to, from;
    public double deltaX, deltaY, deltaZ;
    public double lastDeltaX, lastDeltaY, lastDeltaZ;

}
