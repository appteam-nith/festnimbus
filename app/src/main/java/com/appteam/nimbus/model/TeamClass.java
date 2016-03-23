package com.appteam.nimbus.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sukhbir on 7/3/16.
 */
public class TeamClass implements Serializable{
    private String teamname;
    private ArrayList<EventClass> events;
    private String _id;
    private String __v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public ArrayList<EventClass> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<EventClass> events) {
        this.events = events;
    }
}
