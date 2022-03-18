package com.promaze.statistics;

import java.util.*;

public class Statistics {

    List<StatisticItem> statisticItems;

    public Statistics() {
        this.statisticItems = new ArrayList<>();
    }

    public void addStatisticItem(StatisticItem item){
        this.statisticItems.add(item);
    }

    /**
     * Domyślnie sortuje rosnąco czyli od najmniejszego czasu do największego
     */
    public void sort(){
        Collections.sort(this.statisticItems, getTimeCycleComparator(false));
    }

    public void sortBy(final SortStatisticsType type, final Boolean reverseOrderSort){
        if (type == SortStatisticsType.TIME_CYCLE){
            Collections.sort(this.statisticItems, getTimeCycleComparator(reverseOrderSort));
        }
    }

    private Comparator<StatisticItem> getTimeCycleComparator(final Boolean reverseOrderSort){
        return new Comparator<StatisticItem>() {
            @Override
            public int compare(StatisticItem o1, StatisticItem o2) {
                return o1.getTimeCycle().compareTo(o2.getTimeCycle()) * (reverseOrderSort ? -1 : 1);
            }
        };
    }

    public List<StatisticItem> getStatisticItems() {
        return this.statisticItems;
    }
}
