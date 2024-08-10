package com.mogukun.teru.check.checks.flight;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.managers.Counter;
import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.UUID;

public class FlightB extends Check {

    public FlightB() {
        cs = new CheckSetting();
        cs.setCheckName("Flight");
        cs.setCheckType("B");
        cs.setDescription("Simple Glide Check");
        cs.setExperimental(false);
    }

    HashMap<UUID, Counter> violations = new HashMap<>();

    @EventHandler
    public void onMove(TeruTickEvent e){

        if ( !e.isLegInAir() ){
            return;
        }

        if( e.getServerAirTick() < 6 ){
            return;
        }

        if( e.getTo().getY() < e.getFrom().getY() && e.getDeltaY() <= e.getLastDeltaY() ){
            UUID uuid = e.getPlayer().getUniqueId();
            if(violations.get(uuid) == null){
                violations.put(uuid, new Counter());
            }
            int count = violations.get(uuid).count();
            if( count > 5 ){
                flag(e.getPlayer());
            }
        }
    }


}
