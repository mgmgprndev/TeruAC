package com.mogukun.teru.check.checks.speed;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class SpeedA extends Check {

    public SpeedA() {
        cs = new CheckSetting();
        cs.setCheckName("Speed");
        cs.setCheckType("A");
        cs.setDescription("Simple Speed Check");
        cs.setExperimental(false);
    }

    HashMap<UUID, Integer> violations = new HashMap<>();

    @EventHandler
    public void onMove(TeruTickEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        double deltaXZ = e.getDeltaXZ();
        double lastDeltaXZ = e.getLastDeltaXZ();

        if( deltaXZ == 0 || lastDeltaXZ == 0 ){
            return;
        }

        double maxDeltaXZ = lastDeltaXZ * 1.8;
        maxDeltaXZ += e.getAmplifier(PotionEffectType.SPEED) * 0.1F;

        if ( deltaXZ > maxDeltaXZ ) {

            violations.putIfAbsent(uuid, 0);

            int vl = violations.get(uuid) + (int)(deltaXZ * 10);
            vl -= 1;

            if( vl > 5 ) {
                flag( player, deltaXZ + " > " + maxDeltaXZ + " vl=" + vl );
                vl = 0;
                violations.put(uuid, vl);
            }
        }
    }



}
