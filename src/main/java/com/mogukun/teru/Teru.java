package com.mogukun.teru;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.check.CheckUtil;
import com.mogukun.teru.check.PlayerUtil;
import com.mogukun.teru.commands.TeruCommand;
import com.mogukun.teru.listeners.PlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Teru extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("teru").setExecutor(new TeruCommand());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerEvent(), this);
        pm.registerEvents(new Check(), this);
        CheckUtil.setup(pm, this);

        for(Player p : Bukkit.getOnlinePlayers()){
            PlayerUtil.addPlayer(p);
        }
    }

    @Override
    public void onDisable() {

    }


}
