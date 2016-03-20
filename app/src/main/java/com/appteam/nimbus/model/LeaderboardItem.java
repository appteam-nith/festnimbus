package com.appteam.nimbus.model;


import android.os.Parcel;
import android.os.Parcelable;

public class LeaderboardItem implements Parcelable {
    public String email, silver_coins, gold_coins, rollno;
    public String events_register[];
    public int mobile;

    public LeaderboardItem(String email, String silver_coins, String gold_coins, String rollno, String[] events_register, int mobile) {
        this.email = email;
        this.silver_coins = silver_coins;
        this.gold_coins = gold_coins;
        this.rollno = rollno;
        this.events_register = events_register;
        this.mobile = mobile;
    }

    protected LeaderboardItem(Parcel in) {
        email=in.readString();
        silver_coins=in.readString();
        gold_coins=in.readString();
        rollno=in.readString();
        in.readStringArray(events_register);
        mobile=in.readInt();
    }

    public static final Creator<LeaderboardItem> CREATOR = new Creator<LeaderboardItem>() {
        @Override
        public LeaderboardItem createFromParcel(Parcel in) {
            return new LeaderboardItem(in);
        }

        @Override
        public LeaderboardItem[] newArray(int size) {
            return new LeaderboardItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(silver_coins);
        parcel.writeString(gold_coins);
        parcel.writeString(rollno);
        parcel.writeStringArray(events_register);
        parcel.writeInt(mobile);
    }
}
