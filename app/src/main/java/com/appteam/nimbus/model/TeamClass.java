package com.appteam.nimbus.model;

import java.util.ArrayList;

/**
 * Created by sukhbir on 7/3/16.
 */
public class TeamClass {
    private String teamname;
    private ArrayList<EventClass> events;

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
