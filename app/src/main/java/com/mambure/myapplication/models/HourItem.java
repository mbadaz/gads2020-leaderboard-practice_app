package com.mambure.myapplication.models;

import androidx.annotation.Nullable;

public class HourItem {
    private String name;
    private int hours;
    private String country;
    private String badgeUrl;
    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

    public String getCountry() {
        return country;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeUrl = badgeUrl;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof HourItem && ((HourItem) obj).name.equals(name) &&
                ((HourItem) obj).hours == hours && ((HourItem) obj).country.equals(country);
    }
}
