package com.mogukun.teru.check.checks.timer;

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

    HashMap<UUID, Long> lastFlagged = new HashMap<>();

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

                if( avgDiff > 1.5 ){ //  ( avgDiff > 0.5 && Math.abs( 50 - diff ) > 2 ) ||  <= REMOVED FOR FALSES A LOT!
                    if(violations.get(uuid) == null){
                        violations.put(uuid, new Counter());
                    }
                    int count = violations.get(uuid).count();
                    if( count > 5 ){

                        long flagTime = System.currentTimeMillis();

                        lastFlagged.computeIfAbsent(uuid, k -> flagTime - 10000);

                        if( flagTime - lastFlagged.get( uuid ) >= 1000 ){

                            double timerSpeedPercent = Math.floor( ( ( 50/ average ) * 100 ) * 1000 ) / 1000;

                            //flag(e.getPlayer(), "speed=" +  timerSpeedPercent + "% ad=" + avgDiff + " d1=" + average +  " d2=" + diff + " v=" + count );
                            flag( e.getPlayer(), "speed=" + timerSpeedPercent + "%" );

                            lastFlagged.put(uuid, flagTime);
                        }
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
