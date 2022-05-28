package com.promaze.statistics;

import com.promaze.tools.stopwatch.TimeCycle;

public class StatisticItem {

    private final TimeCycle timeCycle;
    private int mazeSize;
    private String name;
    private int length;

    public StatisticItem(TimeCycle timeCycle, int mazeSize) {
        this.timeCycle = timeCycle;
        this.mazeSize = mazeSize;
    }

    public StatisticItem(TimeCycle timeCycle, int mazeSize, String name, int length) {
        this.timeCycle = timeCycle;
        this.mazeSize = mazeSize;
        this.name = name.substring(0,3);
        this.length = length;
    }

    public TimeCycle getTimeCycle() {
        return timeCycle;
    }
    public int getMazeSize(){return mazeSize;}

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "StatisticItem{" +
                "timeCycle=" + timeCycle +
                '}';
    }
}
