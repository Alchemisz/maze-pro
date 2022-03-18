package com.promaze.tools.stopwatch;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeCycleTest {

    @Test
    public void TimeCycleCreatingTest(){

        TimeCycle timeCycle = new TimeCycle(65100);

        assertEquals(timeCycle.getMinutes(), Integer.valueOf(1));
        assertEquals(timeCycle.getSeconds(), Integer.valueOf(5));
        assertEquals(timeCycle.getMilliseconds(), Integer.valueOf(100));
    }

    @Test
    public void compareGreaterThan(){
        TimeCycle timeCycle1 = new TimeCycle(1250);
        TimeCycle timeCycle2 = new TimeCycle(950);

        int result = timeCycle1.compareTo(timeCycle2);
        assertEquals(Integer.valueOf(result), Integer.valueOf(1));
    }

    @Test
    public void compareLowerThan(){
        TimeCycle timeCycle1 = new TimeCycle(730);
        TimeCycle timeCycle2 = new TimeCycle(991);

        int result = timeCycle1.compareTo(timeCycle2);
        assertEquals(Integer.valueOf(result), Integer.valueOf(-1));
    }

    @Test
    public void compareEqualTo(){
        TimeCycle timeCycle1 = new TimeCycle(730);
        TimeCycle timeCycle2 = new TimeCycle(730);

        int result = timeCycle1.compareTo(timeCycle2);
        assertEquals(Integer.valueOf(result), Integer.valueOf(0));
    }
}
