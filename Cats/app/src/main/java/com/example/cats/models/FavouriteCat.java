package com.example.cats.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Favourite Cat Database model
@Entity
public class FavouriteCat {

    @PrimaryKey
    @NonNull
    private String catId;

    @ColumnInfo(name = "cat_json")
    private String catJson;

    public FavouriteCat(String catId, String catJson) {
        this.catId = catId;
        this.catJson = catJson;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatJson() {
        return catJson;
    }

    public void setCatJson(String catJson) {
        this.catJson = catJson;
    }
}
