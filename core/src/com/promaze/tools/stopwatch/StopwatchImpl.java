package com.promaze.tools.stopwatch;

import java.util.Date;

public class StopwatchImpl implements Stopwatch {

    private Long beginningTime;
    private Long endTime;
    private Long measuredTime;
    private Boolean isStarted;

    public StopwatchImpl() {
        this.isStarted = false;
    }

    @Override
    public void start() {
        this.isStarted = true;
        this.beginningTime = new Date().getTime();
    }

    @Override
    public TimeCycle stop() {

        if (!isStarted){
            throw new IllegalStateException("Stopwatch has not been started");
        }

        this.endTime = new Date().getTime();
        this.measuredTime = this.endTime - this.beginningTime;
        TimeCycle timeCycle = new TimeCycle(Integer.valueOf(String.valueOf(measuredTime)));
        return timeCycle;
    }
}
