package com.mogukun.teru.check.checks.movement.motion;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.managers.Counter;
import com.mogukun.teru.settings.CheckSetting;
import net.minecraft.server.v1_8_R3.Blocks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.UUID;

public class MotionA extends Check {

    public MotionA() {
        cs = new CheckSetting();
        cs.setCheckName("Motion");
        cs.setCheckType("A");
        cs.setDescription("Too Tiny Vertical Speed Check");
        cs.setExperimental(true);
    }

    HashMap<UUID, Counter> violations = new HashMap<>();


    @EventHandler
    public void onMove(TeruTickEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if ( !e.isLegInAir() ){
            return;
        }

        double accelY = Math.abs(e.getDeltaY() - e.getLastDeltaY());
        accelY = Math.floor(accelY * 1000) / 1000;


        if( accelY != 0 && accelY < 0.05 ) {

            if(violations.get(uuid) == null){
                violations.put(uuid, new Counter());
            }

            violations.get(uuid).count();
            int count = violations.get(uuid).getCount();

            if( count > 5 ){
                flag(player, "ay=" + accelY );
            }

        }

    }


}
