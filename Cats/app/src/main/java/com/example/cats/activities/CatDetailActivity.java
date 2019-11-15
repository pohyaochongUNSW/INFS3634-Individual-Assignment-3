package com.example.cats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cats.R;
import com.example.cats.database.Database;
import com.example.cats.database.FavouriteCatDatabase;
import com.example.cats.models.Cat;
import com.example.cats.models.FavouriteCat;
import com.google.gson.Gson;

import java.util.List;

public class CatDetailActivity extends AppCompatActivity {
    private TextView catName;
    private TextView description;
    private ImageView catImage;
    private TextView weightImperial;
    private TextView weightMetric;
    private TextView temperament;
    private TextView origin;
    private TextView lifeSpan;
    private TextView wikipedia;
    private TextView dFriendly;
    private TextView cFriendly;
    private TextView sFriendly;
    private ImageView buttonLike;
    private Cat cat;
    private String catId = "";

    private FavouriteCatDatabase favouriteCatDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_detail);

        Intent intent = getIntent();

        favouriteCatDatabase = FavouriteCatDatabase.getInstance(getApplicationContext());

        String comeFrom = intent.getStringExtra("comeFrom");
        if(comeFrom.equals("CatAdapter")) {
            catId = intent.getStringExtra("catId");
            cat = Database.getCatById(catId);
        } else if(comeFrom.equals("FavouriteAdapter")){
            String catJson = intent.getStringExtra("catJson");
            Gson gson = new Gson();
            cat = gson.fromJson(catJson, Cat.class);
        }

        final Cat.Breed catBreed= cat.getBreeds().get(0);
        catName = findViewById(R.id.breed);
        catImage = findViewById(R.id.catImage);
        description = findViewById(R.id.contentDescription);
        weightImperial = findViewById(R.id.contentImperialWeight);
        weightMetric = findViewById(R.id.contentWeightMetric);
        temperament = findViewById(R.id.contentTemperament);
        origin = findViewById(R.id.contentOrigin);
        lifeSpan = findViewById(R.id.contentLifeSpan);
        wikipedia = findViewById(R.id.contentWikipedia);
        dFriendly = findViewById(R.id.contentDogFriendly);
        sFriendly = findViewById(R.id.contentStrangerFriendly);
        cFriendly = findViewById(R.id.contentChildFriendly);

        catName.setText(catBreed.getName());
        description.setText(catBreed.getDescription());
        weightImperial.setText(catBreed.getWeight().getImperial());
        weightMetric.setText(catBreed.getWeight().getMetric());
        temperament.setText(catBreed.getTemperament());
        origin.setText(catBreed.getOrigin());
        lifeSpan.setText(catBreed.getLifeSpan());
        wikipedia.setText(catBreed.getWikipediaLink());
        dFriendly.setText(String.valueOf(catBreed.getDogFriendly()));
        sFriendly.setText(String.valueOf(catBreed.getStrangerFriendly()));
        cFriendly.setText(String.valueOf(catBreed.getChildFriendly()));
        Glide.with(this).load(cat.getImageUrl()).into(catImage);

        buttonLike = findViewById(R.id.buttonLike);
        if(checkDuplicate(catBreed.getId())){
            buttonLike.setImageResource(R.drawable.added_like);
        }

        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDuplicate(catBreed.getId())){
                    buttonLike.setImageResource(R.drawable.like);
                    // remove cat from database
                    favouriteCatDatabase.favouriteCatDao().removeFavouriteCat(catBreed.getId());
                    Toast.makeText(getApplicationContext(),"Remove Favourite.", Toast.LENGTH_SHORT).show();
                } else {
                    // Convert cat back to json string to store in database
                    // So each time restart previously stored cat will be there
                    String jsonString = cat.toString();

                    FavouriteCat favouriteCat = new FavouriteCat(catId, jsonString);
                    favouriteCatDatabase.favouriteCatDao().insertCat(favouriteCat);
                    buttonLike.setImageResource(R.drawable.added_like);
                    Toast.makeText(getApplicationContext(),"Added Favourite.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Check cat previously added favourite or not
    public boolean checkDuplicate(String catId){
        List<FavouriteCat> favouriteCatList = favouriteCatDatabase.favouriteCatDao().getAllFavouriteCat();
        for (int i = 0; i < favouriteCatList.size(); i++) {
            Gson gson = new Gson();
            String jsonString = favouriteCatList.get(i).getCatJson();
            Cat cat = gson.fromJson(jsonString, Cat.class);
            String id = cat.getBreeds().get(0).getId();
            if(id.equals(catId)){
                return true;
            }
        }

        return false;
    }
}
