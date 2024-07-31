package com.mogukun.teru.check.checks.movement.flight;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.managers.Counter;
import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.UUID;

public class FlightA extends Check {

    public FlightA() {
        cs = new CheckSetting();
        cs.setCheckName("Flight");
        cs.setCheckType("A");
        cs.setDescription("Simple Flight Check");
        cs.setExperimental(true);
    }

    HashMap<UUID, Counter> violations = new HashMap<>();

    @EventHandler
    public void onMove(TeruTickEvent e){
        if(e.getPlayer().isOnGround()){
            return;
        }

        if( e.getTo().getY() >= e.getFrom().getY() ){
            UUID uuid = e.getPlayer().getUniqueId();
            if(violations.get(uuid) == null){
                violations.put(uuid, new Counter());
            }
            violations.get(uuid).count();
            int count = violations.get(uuid).getCount();
            if( count > 20 ){
                flag(e.getPlayer());
            }
        }

    }


}
