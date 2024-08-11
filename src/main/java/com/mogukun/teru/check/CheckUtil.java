package com.mogukun.teru.check;

import com.mogukun.teru.Teru;
import com.mogukun.teru.check.checks.autoclicker.AutoClickerA;
import com.mogukun.teru.check.checks.autoclicker.AutoClickerB;
import com.mogukun.teru.check.checks.flight.*;

import com.mogukun.teru.check.checks.invalid.InvalidA;
import com.mogukun.teru.check.checks.invalid.InvalidB;
import com.mogukun.teru.check.checks.invalid.InvalidC;
import com.mogukun.teru.check.checks.motion.MotionA;
import com.mogukun.teru.check.checks.motion.MotionC;
import com.mogukun.teru.check.checks.speed.SpeedA;
import com.mogukun.teru.check.checks.motion.MotionB;
import com.mogukun.teru.check.checks.timer.TimerA;
import com.mogukun.teru.check.checks.timer.TimerB;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;

public class CheckUtil {

    public static ArrayList<Check> checks = new ArrayList<>();
    public static void setup(PluginManager pm, Teru instance){
        /* Flight Checks */

        checks.add( new FlightA() );
        checks.add( new FlightB() );


        /* Motion Checks */
        checks.add( new MotionA() );
        checks.add( new MotionB() );
        checks.add( new MotionC() );

        /* Speed Checks */
        checks.add( new SpeedA() );

        /* Timer Checks */
        checks.add( new TimerA() );
        checks.add( new TimerB() );

        /* Invalid Checks */
        checks.add( new InvalidA() );
        checks.add( new InvalidB() );
        checks.add( new InvalidC() );


        checks.add( new AutoClickerA() );
        checks.add( new AutoClickerB() );

        for(Check c : checks){
            pm.registerEvents(c, instance);
        }
    }
}
