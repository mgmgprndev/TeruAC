package com.mogukun.teru.check;

import com.mogukun.teru.Teru;
import com.mogukun.teru.check.checks.movement.flight.*;

import com.mogukun.teru.check.checks.movement.invalid.InvalidA;
import com.mogukun.teru.check.checks.movement.motion.MotionB;
import com.mogukun.teru.check.checks.movement.motion.MotionA;
import com.mogukun.teru.check.checks.movement.timer.TimerA;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;

public class CheckUtil {

    public static ArrayList<Check> checks = new ArrayList<>();
    public static void setup(PluginManager pm, Teru instance){
        checks.add( new FlightA() );
        checks.add( new FlightB() );

        checks.add( new MotionA() );
        checks.add( new MotionB() );

        checks.add( new TimerA() );

        checks.add(new InvalidA());



        for(Check c : checks){
            pm.registerEvents(c, instance);
        }
    }
}
