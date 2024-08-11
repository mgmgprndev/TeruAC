package com.mogukun.teru.check.checks.autoclicker;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.check.PlayerUtil;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.settings.CheckSetting;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AutoClickerA extends Check {

    public AutoClickerA() {
        cs = new CheckSetting();
        cs.setCheckName("AutoClicker");
        cs.setCheckType("A");
        cs.setDescription("Deviation Based AutoClicker Check");
        cs.setExperimental(false);
    }

    HashMap<UUID,Long> lastArmSwing = new HashMap<>();
    HashMap<UUID, ArrayList<Long>> armSwingDelaySamples = new HashMap<>();
    HashMap<UUID,Integer> vlCount = new HashMap<>();


    @EventHandler
    public void onClick(PlayerAnimationEvent event){

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if ( event.getAnimationType() == PlayerAnimationType.ARM_SWING ){

            if(PlayerUtil.isDigging( player ) ) return;


            long now = System.currentTimeMillis();
            if( lastArmSwing.get(uuid) != null ){
                long diff = now - lastArmSwing.get(uuid);

                armSwingDelaySamples.computeIfAbsent(uuid, k -> new ArrayList<>() );

                armSwingDelaySamples.get(uuid).add(diff);

                if ( armSwingDelaySamples.get(uuid).size() < 50 ){
                    return;
                }

                while ( armSwingDelaySamples.get(uuid).size() > 50 ) armSwingDelaySamples.get(uuid).remove(0);

                double deviation = calculateDeviation(armSwingDelaySamples.get(uuid));

                vlCount.putIfAbsent(uuid,0);

                if ( deviation < 20 ){

                    int vl = vlCount.get( uuid ) + 1;

                    vlCount.put( uuid, vl );

                    if( vl > 5 ){

                        flag(player, "vl=" + vl + " deviation=" + deviation);

                        vlCount.put(uuid,0);
                    }

                }

            }

            lastArmSwing.put(uuid,now);
        }

    }




    public double calculateDeviation(ArrayList<Long> samples) {

        double sum = 0;
        for (long value : samples) {
            sum += value;
        }
        double mean = sum / samples.size();

        double squaredSum = 0;
        for (long value : samples) {
            squaredSum += Math.pow(value - mean, 2);
        }
        double stdDev = Math.sqrt(squaredSum / samples.size());

        // Define the outlier threshold (e.g., 2 standard deviations)
        double threshold = 2 * stdDev;

        // Filter out outliers
        ArrayList<Long> filteredSamples = new ArrayList<>();
        for (long value : samples) {
            if (Math.abs(value - mean) <= threshold) {
                filteredSamples.add(value);
            }
        }

        if (filteredSamples.isEmpty()) {
            return 0.0; // Avoid division by zero if all values are outliers
        }

        // Recalculate mean and standard deviation after filtering
        sum = 0;
        for (long value : filteredSamples) {
            sum += value;
        }
        double newMean = sum / filteredSamples.size();

        squaredSum = 0;
        for (long value : filteredSamples) {
            squaredSum += Math.pow(value - newMean, 2);
        }
        double newStdDev = Math.sqrt(squaredSum / filteredSamples.size());

        return newStdDev;
    }
}
