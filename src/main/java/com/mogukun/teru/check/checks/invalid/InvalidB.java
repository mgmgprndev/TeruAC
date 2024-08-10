package com.mogukun.teru.check.checks.invalid;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.managers.Counter;
import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class InvalidB extends Check {

    public InvalidB() {
        cs = new CheckSetting();
        cs.setCheckName("Invalid");
        cs.setCheckType("B");
        cs.setDescription("FastPlace Check");
        cs.setExperimental(true);
    }

    HashMap<UUID, Counter> violations = new HashMap<>();

    HashMap<UUID, ArrayList<Long>> samples = new HashMap<>();
    HashMap<UUID, Long> lastPlace = new HashMap<>();

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        long now = System.currentTimeMillis();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if(lastPlace.get(uuid) != null ){
            long last = lastPlace.get(uuid);

            long diff = now - last;

            if( samples.get(uuid) != null ){
                ArrayList<Long> array = samples.get(uuid);

                if(diff > 1000){
                    lastPlace.put(uuid, now);
                    return;
                }

                array.add(diff);

                if( array.size() < 15 ){
                    return;
                }

                while (array.size() > 15 ) {
                    array.remove(0);
                }

                long sum = 0;
                for (Long value : array) {
                    sum += value;
                }

                double average = (double) sum / array.size();
                samples.put(uuid, array);

                double avgDiff = Math.abs(50 - average);

                if( avgDiff < 100 && diff < 150 ){

                    if(violations.get(uuid)==null){
                        violations.put(uuid, new Counter());
                    }

                    if(violations.get(uuid).count() > 5){
                        flag(player, "avg=" + avgDiff + " diff=" + diff);
                    }
                }
            }else {
                ArrayList<Long> newArray = new ArrayList<>();
                newArray.add(diff);
                samples.put(uuid, newArray);
            }
        }


        lastPlace.put(uuid, now);
    }

}
