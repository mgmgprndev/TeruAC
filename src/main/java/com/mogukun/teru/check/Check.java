package com.mogukun.teru.check;

import com.mogukun.teru.commands.TeruCommand;
import com.mogukun.teru.listeners.PacketListener;
import com.mogukun.teru.settings.CheckSetting;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.UUID;

public class Check implements Listener {


    public CheckSetting cs;
    public Check(){}

    public void flag(Player p){
        flag(p, "");
    }

    public void flag(Player p, String desc){

        if ( p.getAllowFlight() ){
            return;
        }

        if ( p.isFlying() ){
            return;
        }

        if ( p.getGameMode() == GameMode.SPECTATOR ){
            return;
        }

        UUID uuid = p.getUniqueId();

        if( PacketListener.disableTime.get(uuid) == null ){
            PacketListener.disableTime.put(uuid, System.currentTimeMillis() + (1000*10));
        }

        if( System.currentTimeMillis() <= PacketListener.disableTime.get(uuid) ){
            return;
        }

        String flag = "§6§l⚡§r §c" + p.getName() + "§7 failed§c " + cs.getCheckName() + " [" + cs.getCheckType() + "]" + ( cs.isExperimental() ? " (EXP)" : "" );
        desc = desc.equalsIgnoreCase("") ? "NONE" : desc;

        int[] vls = PlayerUtil.countVL(p.getUniqueId(), cs.getCheckName(), cs.getCheckType());

        String info = "§4[§6§lTERU§r§4]\n";
        info += "§cInfo: \n§7";
        info += "§7 " + desc + "\n";
        info += "§cViolations: \n";
        info += "§7 VL: " + vls[0] + "\n";
        info += "§7 Total: " + vls[1] + "\n";
        info += "\n§cClick To Teleport!";

        TextComponent tc = new TextComponent(flag);
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(info).create()));
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + p.getName() ));

        for(Player op : Bukkit.getOnlinePlayers()){
            if(TeruCommand.isToggled(op)){
                op.spigot().sendMessage(tc);
            }
        }
    }
}
