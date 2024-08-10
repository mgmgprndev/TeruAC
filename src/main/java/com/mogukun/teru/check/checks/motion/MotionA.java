package com.mogukun.teru.check.checks.motion;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class MotionA extends Check {

    public MotionA() {
        cs = new CheckSetting();
        cs.setCheckName("Motion");
        cs.setCheckType("A");
        cs.setDescription("Step Hack Check");
        cs.setExperimental(false);
    }


    @EventHandler
    public void onMove(TeruTickEvent e){

        Player player = e.getPlayer();

        if( !e.isLegInAir() ){
            return;
        }

        if ( player.isOnGround() && e.getDeltaY() > 0.5 && e.getLastDeltaY() == 0 ) {
            flag(player, "dy=" + e.getDeltaY());
        }


    }

}
