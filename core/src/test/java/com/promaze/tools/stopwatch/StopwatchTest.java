package com.promaze.tools.stopwatch;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StopwatchTest{

    @Test
    public void measureTime(){
        Stopwatch stopwatch = new StopwatchImpl();
        stopwatch.start();

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TimeCycle timeCycle = stopwatch.stop();
        System.out.println(timeCycle);
        assertTrue(timeCycle.getMilliseconds() > 30 && timeCycle.getMilliseconds() < 70);
    }

}
