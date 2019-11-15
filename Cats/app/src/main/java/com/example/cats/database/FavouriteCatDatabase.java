package com.example.cats.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cats.models.FavouriteCat;

@Database(entities = {FavouriteCat.class}, version = 1)
public abstract class FavouriteCatDatabase extends RoomDatabase{

    public abstract FavouriteCatDao favouriteCatDao();

    private static FavouriteCatDatabase instance;
    public static FavouriteCatDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, FavouriteCatDatabase.class,
                    "FavouriteCatDb").allowMainThreadQueries().build();
        }
        return instance;
    }
}
