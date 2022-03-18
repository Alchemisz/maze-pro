package com.promaze.tools.stopwatch;

public class TimeCycle implements Comparable<TimeCycle>{

    private Integer minutes;
    private Integer seconds;
    private Integer milliseconds;

    public TimeCycle(Integer milliseconds) {
        this.minutes = milliseconds / 1000 / 60;
        this.seconds = milliseconds / 1000 - (this.minutes * 60);
        this.milliseconds = milliseconds - (this.minutes * 1000 * 60) - (this.seconds * 1000);
    }

    /**
     * Porównuje dwa cykle czasu
     * @param o - inny cykl czasu
     * @return  1 - jeśli TEN (this) cykl czasu jest większy od innego (podanego jako parametr),
     *         -1 - jeśli ten cykl jest mniejszy od innego
     *          0 - jeśli cykle czasowe są równe
     */
    @Override
    public int compareTo(TimeCycle o) {
        if (this.minutes > o.getMinutes()) return 1;
        else if (this.minutes < o.getMinutes()) return -1;

        if (this.seconds > o.getSeconds()) return 1;
        else if (this.seconds < o.getSeconds()) return -1;

        if (this.milliseconds > o.getMilliseconds()) return 1;
        else if (this.milliseconds < o.getMilliseconds()) return -1;

        return 0;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Integer getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(Integer milliseconds) {
        this.milliseconds = milliseconds;
    }

    @Override
    public String toString() {
        return "TimeCycle[" +
                "minutes=" + minutes +
                ", seconds=" + seconds +
                ", milliseconds=" + milliseconds +
                ']';
    }
}
