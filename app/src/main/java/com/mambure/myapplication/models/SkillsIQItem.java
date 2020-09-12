package com.mambure.myapplication.models;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "SkillsIQItems", indices = {@Index(value = {"p_name", "p_country"},
        unique = true)})
public class SkillsIQItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "p_name")
    private String name;
    @ColumnInfo
    private int score;
    @ColumnInfo(name = "p_country")
    private String country;
    @ColumnInfo
    private String badgeUrl;
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
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

    public void setScore(int score) {
        this.score = score;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeUrl = badgeUrl;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof SkillsIQItem && ((SkillsIQItem) obj).name.equals(name)
                && ((SkillsIQItem) obj).score == score && ((SkillsIQItem) obj).country.equals(country);
    }
}
