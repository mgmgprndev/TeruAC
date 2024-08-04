package com.mogukun.teru.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TeruCommand implements CommandExecutor {

    public static HashMap<UUID, Boolean> hasToggled = new HashMap<>();

    public static boolean isToggled(Player p){
        return hasToggled.getOrDefault(p.getUniqueId(), false);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if ( commandSender instanceof Player &&  commandSender.hasPermission("teru.use") ){
            Player p = (Player) commandSender;
            if( args.length == 0 ){
                p.sendMessage("§eTERU >> /teru <alerts/§caurabot§a>" );
            }else if ( args.length == 1 ){
                if( args[0].equalsIgnoreCase("alerts") ){
                    UUID uuid = p.getUniqueId();
                    if( hasToggled.get( uuid ) == null ){
                        hasToggled.put( uuid, true );
                        p.sendMessage("§eTERU >> Alert is §aEnabled");
                    }else {
                        hasToggled.put( uuid, !hasToggled.get( uuid ) );
                        if(hasToggled.get( uuid )){
                            p.sendMessage("§eTERU >> Alert is §aEnabled");
                        }else {
                            p.sendMessage("§eTERU >> Alert is §cDisabled");
                        }
                    }
                }else {
                    p.sendMessage("§eTERU >>§c Error, please check usage.");
                }
            }
        }else {
            commandSender.sendMessage("§eTERU >>§c This command is not executable!");
        }
        return true;
    }

}
