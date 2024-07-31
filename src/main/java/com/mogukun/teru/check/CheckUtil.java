package com.mogukun.teru.check;

import com.mogukun.teru.Teru;
import com.mogukun.teru.check.checks.movement.flight.FlightA;
import com.mogukun.teru.check.checks.movement.timer.TimerA;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;

public class CheckUtil {

    public static ArrayList<Check> checks = new ArrayList<>();
    public static void setup(PluginManager pm, Teru instance){
        checks.add( new FlightA() );
        checks.add( new TimerA() );


        for(Check c : checks){
            pm.registerEvents(c, instance);
        }
    }
}
