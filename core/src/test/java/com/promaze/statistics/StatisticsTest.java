package com.promaze.statistics;

import com.promaze.tools.stopwatch.TimeCycle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StatisticsTest {


    @Test
    public void sort(){

        Statistics statistics = new Statistics();
        statistics.addStatisticItem(new StatisticItem(new TimeCycle(1000)));
        statistics.addStatisticItem(new StatisticItem(new TimeCycle(3000)));
        statistics.addStatisticItem(new StatisticItem(new TimeCycle(2000)));

        statistics.sort();

        for (int i = 1; i < statistics.getStatisticItems().size(); i++) {
            TimeCycle previous = statistics.getStatisticItems().get(i-1).getTimeCycle();
            TimeCycle current = statistics.getStatisticItems().get(i).getTimeCycle();
            assertTrue(current.compareTo(previous) > 0);
        }

    }

    @Test
    public void sortByTimeCycle(){

        Statistics statistics = new Statistics();
        statistics.addStatisticItem(new StatisticItem(new TimeCycle(1000)));
        statistics.addStatisticItem(new StatisticItem(new TimeCycle(3000)));
        statistics.addStatisticItem(new StatisticItem(new TimeCycle(2000)));

        statistics.sortBy(SortStatisticsType.TIME_CYCLE, false);

        for (int i = 1; i < statistics.getStatisticItems().size(); i++) {
            TimeCycle previous = statistics.getStatisticItems().get(i-1).getTimeCycle();
            TimeCycle current = statistics.getStatisticItems().get(i).getTimeCycle();
            assertTrue(current.compareTo(previous) > 0);
        }
    }

    @Test
    public void sortByTimeCycleReverse(){

        Statistics statistics = new Statistics();
        statistics.addStatisticItem(new StatisticItem(new TimeCycle(1000)));
        statistics.addStatisticItem(new StatisticItem(new TimeCycle(3000)));
        statistics.addStatisticItem(new StatisticItem(new TimeCycle(2000)));

        statistics.sortBy(SortStatisticsType.TIME_CYCLE, true);

        for (int i = 1; i < statistics.getStatisticItems().size(); i++) {
            TimeCycle previous = statistics.getStatisticItems().get(i-1).getTimeCycle();
            TimeCycle current = statistics.getStatisticItems().get(i).getTimeCycle();
            assertTrue(current.compareTo(previous) < 0);
        }
    }

}
