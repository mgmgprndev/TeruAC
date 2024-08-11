package com.mogukun.teru.check.checks.autoclicker;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;

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

    HashMap<UUID,Long> lastLeftAirClick = new HashMap<>();
    HashMap<UUID, ArrayList<Long>> leftClickSamples = new HashMap<>();
    HashMap<UUID,Integer> leftVL = new HashMap<>();

    @EventHandler
    public void onMove(PlayerAnimationEvent event){

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if( event.getAnimationType() == PlayerAnimationType.ARM_SWING ){
            long now = System.currentTimeMillis();
            if( lastLeftAirClick.get(uuid) != null ){
                long diff = now - lastLeftAirClick.get(uuid);

                leftClickSamples.computeIfAbsent(uuid, k -> new ArrayList<>() );

                leftClickSamples.get(uuid).add(diff);

                if ( leftClickSamples.get(uuid).size() < 50 ){
                    return;
                }

                while ( leftClickSamples.get(uuid).size() > 50 ) leftClickSamples.get(uuid).remove(0);

                double deviation = calculateDeviation(leftClickSamples.get(uuid));

                leftVL.putIfAbsent(uuid,0);

                if ( deviation < 20 ){

                    int vl = leftVL.get( uuid ) + 1;

                    leftVL.put( uuid, vl );

                    if( vl > 5 ){

                        flag(player, "vl=" + vl + " deviation=" + deviation);

                        leftVL.put(uuid,0);
                    }

                }

            }

            lastLeftAirClick.put(uuid,now);
        }

    }


    public double calculateDeviation(ArrayList<Long> samples) {
        // Calculate mean
        double sum = 0;
        for (long value : samples) {
            sum += value;
        }
        double mean = sum / samples.size();

        // Calculate standard deviation
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
