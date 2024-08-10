package com.mogukun.teru.commands;

import com.mogukun.teru.Teru;
import com.mogukun.teru.managers.AuraBotSpawner;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TeruCommand implements CommandExecutor {

    public static HashMap<UUID, Boolean> hasToggled = new HashMap<>();

    public static boolean isToggled(Player p){
        return hasToggled.getOrDefault(p.getUniqueId(), false);
    }

    public static ConcurrentHashMap<UUID, Integer> botID = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, Integer> attackCount = new ConcurrentHashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if ( commandSender instanceof Player &&  commandSender.hasPermission("teru.use") ){
            Player p = (Player) commandSender;
            if( args.length == 0 ){
                p.sendMessage("§6§l⚡§r§e /teru <alerts/§caurabot§a>" );
            }else if ( args.length == 1 ){
                if( args[0].equalsIgnoreCase("alerts") ){
                    UUID uuid = p.getUniqueId();
                    if( hasToggled.get( uuid ) == null ){
                        hasToggled.put( uuid, true );
                        p.sendMessage("§6§l⚡§r §e Alert is §aEnabled");
                    }else {
                        hasToggled.put( uuid, !hasToggled.get( uuid ) );
                        if(hasToggled.get( uuid )){
                            p.sendMessage("§6§l⚡§r§e Alert is §aEnabled");
                        }else {
                            p.sendMessage("§6§l⚡§r§e Alert is §cDisabled");
                        }
                    }
                }else if ( args[0].equalsIgnoreCase("aurabot") ) {
                    p.sendMessage("§6§l⚡§r§c /teru aurabot <PlayerName> is correct usage.");
                } else {
                    p.sendMessage("§6§l⚡§r§c Error, please check usage.");
                }
            }else if( args.length == 2 ) {
                if ( args[0].equalsIgnoreCase("aurabot") ) {

                    Player target = Bukkit.getPlayer(args[1]);
                    if(target == null){
                        p.sendMessage("§6§l⚡§r§c Error, Aurabot can only spawned for online players.");
                        return true;
                    }

                    if( botID.get(target.getUniqueId()) == null ){
                        new AuraBotSpawner().spawnNPC(target);
                        int botId = botID.get(target.getUniqueId());

                        p.sendMessage("§6§l⚡§r§e Bot is spawned.");

                        new BukkitRunnable() {
                            int i = 0;

                            @Override
                            public void run() {
                                int x = TeruCommand.attackCount.get(botId);

                                if( i % 5 == 0 ) p.sendMessage("§6§l⚡§r§e Attack Count: " + x );

                                if(i++ > 20){

                                    if( x > 100 ){
                                        p.sendMessage("§6§l⚡§r§e Checking Done. They hit the bot for §c" + x + "§e times. It seems cheating.");
                                    }else if( x > 50 ){
                                        p.sendMessage("§6§l⚡§r§e Checking Done. They hit the bot for §6" + x + "§e times. It seems suspicious.");
                                    }else {
                                        p.sendMessage("§6§l⚡§r§e Checking Done. They hit the bot for " + x + "§e times. It seems legit.");
                                    }

                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Teru.instance, 0 , 10);

                    }else {
                        p.sendMessage("§6§l⚡§r§e Bot already spawned for this player, please wait for despawn.");
                    }
                }else {
                    p.sendMessage("§6§l⚡§r§c Error, please check usage.");
                }
            }
        }else {
            commandSender.sendMessage("§6§l⚡§r§c This command is not executable!");
        }
        return true;
    }

}
