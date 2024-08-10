package com.mogukun.teru.check.checks.timer;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.managers.Counter;
import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TimerB extends Check {

    public TimerB() {
        cs = new CheckSetting();
        cs.setCheckName("Timer");
        cs.setCheckType("B");
        cs.setDescription("Balance Timer Check");
        cs.setExperimental(true);
    }

    HashMap<UUID, Long> lastPacketOn = new HashMap<>();
    HashMap<UUID, Integer> balance = new HashMap<>();

    @EventHandler
    public void onMove(TeruTickEvent e){
        long now = System.currentTimeMillis();
        UUID uuid = e.getPlayer().getUniqueId();

        if( lastPacketOn.get(uuid) != null ){

            balance.putIfAbsent(uuid, 0);

            long delta = now - lastPacketOn.get(uuid);;
            int b = balance.get(uuid);
            b += (int) delta;
            b -= 50;

            int x = Math.abs(b);

            if( x > 200 ){
                b = 0;
                flag(e.getPlayer());
            }

            balance.put(uuid, b);


        }

        lastPacketOn.put(uuid, now);
    }


}
