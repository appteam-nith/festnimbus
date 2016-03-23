package com.appteam.nimbus.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sukhbir on 7/3/16.
 */
public class EventClass implements Serializable{
    private String id;
    private String name;
    private String short_des;
    private String long_des;
    private String contact;
    private String timeline;
    private String __v;
    private ArrayList<String> rules;
    private String url;
    private String Dname;
    private String teamname;

    public String getLong_des() {
        return long_des;
    }

    public void setLong_des(String long_des) {
        this.long_des = long_des;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public ArrayList getRules() {
        return rules;
    }

    public void setRules(ArrayList<String> rules) {
        this.rules = rules;
    }

    public String getDname() {
        return Dname;
    }

    public void setDname(String dname) {
        Dname = dname;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

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
