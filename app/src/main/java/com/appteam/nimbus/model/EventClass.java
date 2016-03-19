package com.appteam.nimbus.model;

/**
 * Created by sukhbir on 7/3/16.
 */
public class EventClass {
    private String id;
    private String name;
    private String short_des;
    private String url;

    public EventClass(){
    }

    public EventClass(String id, String name, String short_des, String url) {
        this.id = id;
        this.name = name;
        this.short_des = short_des;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_des() {
        return short_des;
    }

    public void setShort_des(String short_des) {
        this.short_des = short_des;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
