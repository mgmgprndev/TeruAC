package com.mogukun.teru.check.checks.invalid;

import com.mogukun.teru.check.Check;
import com.mogukun.teru.check.PlayerUtil;
import com.mogukun.teru.events.TeruTickEvent;
import com.mogukun.teru.managers.Counter;
import com.mogukun.teru.settings.CheckSetting;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.UUID;

public class InvalidA extends Check {

    public InvalidA() {
        cs = new CheckSetting();
        cs.setCheckName("Invalid");
        cs.setCheckType("A");
        cs.setDescription("False Ground Check");
        cs.setExperimental(false);
    }

    HashMap<UUID, Counter> violation_a = new HashMap<>();
    HashMap<UUID, Counter> violation_b = new HashMap<>();
    HashMap<UUID, Counter> violation_c = new HashMap<>();


    @EventHandler
    public void onMove(TeruTickEvent e){

        Player player = e.getPlayer();


        if( !e.isLegInAir() ){
            return;
        }

        boolean isMathGround = e.isMathGround;


        int flagged = 0;

        if ( player.isOnGround() ){
            if ( ( e.getDeltaY() != 0 && e.getLastDeltaY() != 0 && !e.tooSmallY ) ){ //&& Math.floor( e.getTo().getY() * 10 ) / 10 != Math.floor( e.getTo().getY() ) )
                flagged = 1;
            }
        }else {
            if ( ( e.getDeltaY() == 0 && e.getLastDeltaY() == 0 ) || isMathGround ){
                flagged = 2;
            }
        }

        if ( e.getServerAirTick() > 20 && player.isOnGround() ){
            flagged = 3;
        }

        UUID uuid = player.getUniqueId();

        if ( flagged == 1 ){
            if(violation_a.get(uuid) == null){
                violation_a.put(uuid, new Counter());
            }

            violation_a.get(uuid).count();
            int count = violation_a.get(uuid).getCount();

            if( count > 5 ){
                flag(player, "Player is faked on-ground while non-ground");
            }
        }

        if ( flagged == 2 ){
            if(violation_b.get(uuid) == null){
                violation_b.put(uuid, new Counter());
            }

            violation_b.get(uuid).count();
            int count = violation_b.get(uuid).getCount();

            if( count > 5 ){
                flag(player, "Player is faked non-ground while on-ground");
            }
        }

        if ( flagged == 3 ){

            if(violation_c.get(uuid) == null){
                violation_c.put(uuid, new Counter());
            }

            int count = violation_c.get(uuid).count();

            if( count > 1 ){
                flag(player, "Player's ground state is incorrect.");
            }
        }


    }

}
