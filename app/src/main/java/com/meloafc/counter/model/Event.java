package com.meloafc.counter.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by alexfabiano on 03/06/2017.
 */

@IgnoreExtraProperties
public class Event {

    public enum Type {
        ACTION_SCREEN_ON("ACTION_SCREEN_ON"),
        ACTION_SCREEN_OFF("ACTION_SCREEN_OFF");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private long date;
    private Type type;

    public Event() {
    }

    public Event(long date, Type type) {
        this.date = date;
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
