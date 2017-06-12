package com.meloafc.counter.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by alexfabiano on 03/06/2017.
 */

@IgnoreExtraProperties
public class Event {

    private Long initialDate;
    private Long finalDate;
    private Long duration;

    public Event() {
    }

    public Event(Long initialDate) {
        this.initialDate = initialDate;
    }

    public Event(Long initialDate, Long finalDate) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        calculateDuration();
    }

    public void calculateDuration() {
        if(initialDate != null && finalDate != null) {
            this.duration = finalDate - initialDate;
        }
    }

    @Exclude
    public boolean isValid() {
        if(initialDate == null) {
            return false;
        }

        if(finalDate == null) {
            return false;
        }

        if(duration == null || duration < 0L) {
            return false;
        }

        return true;
    }

    public Long getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Long initialDate) {
        this.initialDate = initialDate;
    }

    public Long getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Long finalDate) {
        this.finalDate = finalDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
