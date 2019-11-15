package com.example.cats.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cats.models.FavouriteCat;
import java.util.List;

@Dao
public interface FavouriteCatDao {

    @Query("SELECT * FROM favouritecat")
    List<FavouriteCat> getAllFavouriteCat();

    @Query("SELECT COUNT(*) FROM favouritecat")
    int countFavouriteCat();

    @Query("DELETE FROM favouritecat WHERE catId = :catId")
    void removeFavouriteCat(String catId);

    @Query(("DELETE FROM favouritecat"))
    void clearTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCat(FavouriteCat favouriteCatJson);
}
