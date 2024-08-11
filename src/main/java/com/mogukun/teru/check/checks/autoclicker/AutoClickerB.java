package com.mogukun.teru.check.checks.autoclicker;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.managers.Counter;
import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AutoClickerB extends Check {

    public AutoClickerB() {
        cs = new CheckSetting();
        cs.setCheckName("AutoClicker");
        cs.setCheckType("B");
        cs.setDescription("Limit AutoClicker Check");
        cs.setExperimental(false);
    }

    HashMap<UUID, Counter> clickCounter = new HashMap<>();
    HashMap<UUID, Integer> vlCount = new HashMap<>();

    @EventHandler
    public void onMove(PlayerAnimationEvent event){

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if( event.getAnimationType() == PlayerAnimationType.ARM_SWING ){
            clickCounter.computeIfAbsent(uuid, k -> new Counter());

            int speed = clickCounter.get( uuid ).count();
            if ( speed > 20 ){
                vlCount.putIfAbsent(uuid,0);
                int vl = vlCount.get(uuid) + 1;
                vlCount.put(uuid, vl);

                if( vl > 5 ){
                    flag(player, "speed=" + speed );
                    vlCount.put(uuid, 0);
                }
            }

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
