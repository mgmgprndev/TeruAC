package com.mogukun.teru.check;

import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Check implements Listener {


    public CheckSetting cs;
    public Check(){}

    public void flag(Player p){
        Bukkit.broadcastMessage("§eTERU >>§c " + p.getName() + "§e is using§c " + cs.getCheckName() + " " + cs.getCheckType() );
    }

    public void flag(Player p, String desc){
        Bukkit.broadcastMessage("§eTERU >>§c " + p.getName() + "§e is using§c " + cs.getCheckName() + " " + cs.getCheckType() + " §eDesc: " + desc );
    }

}
