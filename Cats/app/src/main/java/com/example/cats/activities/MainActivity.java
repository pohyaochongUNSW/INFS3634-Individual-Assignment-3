package com.example.cats.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.cats.R;
import com.example.cats.database.Database;
import com.example.cats.database.FavouriteCatDatabase;
import com.example.cats.fragments.FavouriteRecyclerFragment;
import com.example.cats.fragments.SearchRecyclerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"Black\">Cat Search</font>"));
        Fragment fragment = new SearchRecyclerFragment();
        swapFragment(fragment);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.search) {
                    Fragment fragment = new SearchRecyclerFragment();
                    Database.catSearch.clear();
                    getSupportActionBar().setTitle(Html.fromHtml("<font color=\"Black\">Cat Search</font>"));
                    swapFragment(fragment);
                    return true;
                } else if (menuItem.getItemId() == R.id.favourite) {
                    Fragment fragment = new FavouriteRecyclerFragment();
                    Database.catSearch.clear();
                    swapFragment(fragment);
                    getSupportActionBar().setTitle(Html.fromHtml("<font color=\"Black\">Favourite List</font>"));
                    return true;
                }
                return false;
            }
        });


    }

    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_slot, fragment);
        fragmentTransaction.commit();
    }
}
