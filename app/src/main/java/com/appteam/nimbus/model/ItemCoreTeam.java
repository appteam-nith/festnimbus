package com.appteam.nimbus.model;

/**
 * Created by sukhbir on 7/3/16.
 */
public class ItemCoreTeam {
    private String name,designation;
    private int id;

    public ItemCoreTeam(String name, String designation, int id) {
        this.name = name;
        this.designation = designation;
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getdesignation() {
        return designation;
    }

    public void setdesignation(String designation) {
        this.designation = designation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
