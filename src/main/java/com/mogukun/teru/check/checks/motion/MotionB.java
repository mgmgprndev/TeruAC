package com.mogukun.teru.check.checks.motion;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class MotionB extends Check {

    public MotionB() {
        cs = new CheckSetting();
        cs.setCheckName("Motion");
        cs.setCheckType("B");
        cs.setDescription("Horizontal Weird Behavior Check");
        cs.setExperimental(false);
    }


    /*
     This check is from MrNik's tutorial. (Thanks Nik <3)
     https://youtu.be/-SiqszHE9rQ
    */

    @EventHandler
    public void onMove(TeruTickEvent e){

        Player player = e.getPlayer();
        double d_yaw = e.getData().deltaYaw;
        double d_xz = e.getDeltaXZ();
        double accelXZ = Math.abs(d_xz - e.getLastDeltaXZ()) * 100;

        if(d_yaw > 1.5F && d_xz > .15D && accelXZ < 1.0E-5){
            flag( player, "y=" + d_yaw + " a=" + accelXZ + " xz=" + d_xz );
        }
    }

}
