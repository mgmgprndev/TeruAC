package com.mogukun.teru.check.checks.motion;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

public class MotionC extends Check {

    public MotionC() {
        cs = new CheckSetting();
        cs.setCheckName("Motion");
        cs.setCheckType("C");
        cs.setDescription("Jump Height Check");
        cs.setExperimental(true);
    }


    @EventHandler
    public void onMove(TeruTickEvent e){
        Player player = e.getPlayer();

        if( e.isMathGround && e.getDeltaY() == 0.5 ){
            return;
        }

        if( e.getTo().getY() <= e.getFrom().getY() ){
            return;
        }

        double base = 0.41999998688697815;

        double max = base * ( 1 + ( 0.5 * e.getAmplifier( PotionEffectType.JUMP ) ) );
        if( e.getDeltaY() > max ) {
            flag( player, e.getDeltaY() + " > " + max );
        }
    }

}
