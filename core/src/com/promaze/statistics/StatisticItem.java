package com.promaze.statistics;

import com.promaze.tools.stopwatch.TimeCycle;

public class StatisticItem {

    private final TimeCycle timeCycle;
    private int mazeSize;
    public StatisticItem(TimeCycle timeCycle, int mazeSize) {
        this.timeCycle = timeCycle;
        this.mazeSize = mazeSize;
    }

    public TimeCycle getTimeCycle() {
        return timeCycle;
    }
    public int getMazeSize(){return mazeSize;}

    @Override
    public String toString() {
        return "StatisticItem{" +
                "timeCycle=" + timeCycle +
                '}';
    }
}
