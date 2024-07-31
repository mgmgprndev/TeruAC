package com.mogukun.teru.check.checks.movement.timer;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.managers.Counter;
import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TimerA extends Check {

    public TimerA() {
        cs = new CheckSetting();
        cs.setCheckName("Timer");
        cs.setCheckType("A");
        cs.setDescription("Simple Timer Check");
        cs.setExperimental(true);
    }

    HashMap<UUID, Long> lastPacketOn = new HashMap<>();
    HashMap<UUID, ArrayList<Long>> samples = new HashMap<>();
    HashMap<UUID, Counter> violations = new HashMap<>();

    @EventHandler
    public void onMove(TeruTickEvent e){
        long now = System.currentTimeMillis();

        UUID uuid = e.getPlayer().getUniqueId();

        if( lastPacketOn.get(uuid) != null ){
            long diff = now - lastPacketOn.get(uuid);

            if( samples.get(uuid) != null ){
                ArrayList<Long> array = samples.get(uuid);
                array.add(diff);

                if( array.size() < 50 ){
                    return;
                }

                while (array.size() > 50) {
                    array.remove(0);
                }

                long sum = 0;
                for (Long value : array) {
                    sum += value;
                }
                double average = (double) sum / array.size();
                samples.put(uuid, array);

                double avgDiff = Math.abs(50 - average);

                if(avgDiff > 5 ){
                    if(violations.get(uuid) == null){
                        violations.put(uuid, new Counter());
                    }
                    violations.get(uuid).count();
                    int count = violations.get(uuid).getCount();
                    if( count > 5 ){
                        flag(e.getPlayer(), "i=" + avgDiff + " v=" + count );
                    }
                }

            }else {
                ArrayList<Long> newArray = new ArrayList<>();
                newArray.add(diff);
                samples.put(uuid, newArray);
            }


        }

        lastPacketOn.put(uuid, now);
    }


}
