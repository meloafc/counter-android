package com.meloafc.counter.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by alexfabiano on 03/06/2017.
 */

@IgnoreExtraProperties
public class Evento {

    public enum Type {
        ACTION_SCREEN_ON("ACTION_SCREEN_ON"),
        ACTION_SCREEN_OFF("ACTION_SCREEN_OFF");

        private String type;

        Type(String url) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public long data;
    public Type type;

    public Evento() {
    }

    public Evento(long data, Type type) {
        this.data = data;
        this.type = type;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
