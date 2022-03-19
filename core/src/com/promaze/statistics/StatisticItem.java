package com.promaze.statistics;

import com.promaze.tools.stopwatch.TimeCycle;

public class StatisticItem {

    private final TimeCycle timeCycle;

    public StatisticItem(TimeCycle timeCycle) {
        this.timeCycle = timeCycle;
    }

    public TimeCycle getTimeCycle() {
        return timeCycle;
    }

    @Override
    public String toString() {
        return "StatisticItem{" +
                "timeCycle=" + timeCycle +
                '}';
    }
}
