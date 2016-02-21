package com.appteam.nimbus;

import java.io.Serializable;

/**
 * Created by sukhbir on 21/2/16.
 */
public class User implements Serializable{

    private String email;
    private String roll;
    private String phone;
    private int silverCoins,goldCoins;
    private int collegeRank;
    private String eventRank;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSilverCoins() {
        return silverCoins;
    }

    public void setSilverCoins(int silverCoins) {
        this.silverCoins = silverCoins;
    }

    public int getGoldCoins() {
        return goldCoins;
    }

    public void setGoldCoins(int goldCoins) {
        this.goldCoins = goldCoins;
    }

    public int getCollegeRank() {
        return collegeRank;
    }

    public void setCollegeRank(int collegeRank) {
        this.collegeRank = collegeRank;
    }

    public String getEventRank() {
        return eventRank;
    }

    public void setEventRank(String eventRank) {
        this.eventRank = eventRank;
    }
}
