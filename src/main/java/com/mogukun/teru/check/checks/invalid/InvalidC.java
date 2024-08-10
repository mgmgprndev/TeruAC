package com.mogukun.teru.check.checks.invalid;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.managers.Counter;
import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import java.util.HashMap;
import java.util.UUID;

public class InvalidC extends Check {

    public InvalidC() {
        cs = new CheckSetting();
        cs.setCheckName("Invalid");
        cs.setCheckType("C");
        cs.setDescription("Keep Sprint Check");
        cs.setExperimental(false);
    }

    HashMap<UUID, Counter> violations = new HashMap<>();


    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event){
        if ( event.getDamager() instanceof Player ) {
            Player player = ((Player) event.getDamager()).getPlayer();
            if ( player.isSprinting() ) {
                UUID uuid = player.getUniqueId();

                violations.computeIfAbsent(uuid, k -> new Counter());

                int vl = violations.get( uuid ).count();

                if ( vl > 3 ){
                    flag(player);
                }
            }
        }
    }


}
