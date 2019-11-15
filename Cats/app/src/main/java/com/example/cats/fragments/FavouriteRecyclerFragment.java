package com.example.cats.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cats.R;
import com.example.cats.adapters.FavouriteAdapter;
import com.example.cats.database.FavouriteCatDatabase;
import com.example.cats.models.FavouriteCat;

import java.util.List;


public class FavouriteRecyclerFragment extends Fragment {

    private static RecyclerView recyclerView;
    private Button buttonClear;
    private List<FavouriteCat> favouriteCatList;
    private TextView notice;

    public FavouriteRecyclerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_recycler, container, false);

        final FavouriteCatDatabase favouriteCatDatabase = FavouriteCatDatabase.getInstance(getContext());

        recyclerView = view.findViewById(R.id.rv_favourite);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        final FavouriteAdapter favouriteAdapter = new FavouriteAdapter();
        favouriteCatList = favouriteCatDatabase.favouriteCatDao().getAllFavouriteCat();
        favouriteAdapter.setData(favouriteCatList);
        recyclerView.setAdapter(favouriteAdapter);

        notice = view.findViewById(R.id.noticeFavouriteList);
        if(favouriteCatList.size() > 0){
            notice.setVisibility(View.GONE);
        }

        buttonClear = view.findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favouriteCatDatabase.favouriteCatDao().clearTable();
                Toast.makeText(getContext(),"All Favourite was removed.", Toast.LENGTH_SHORT).show();
            }
        });

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // refresh cat list by getting current data from database
                                favouriteCatList = favouriteCatDatabase.favouriteCatDao().getAllFavouriteCat();
                                favouriteAdapter.setData(favouriteCatList);
                                if(favouriteCatList.size() > 0){
                                    notice.setVisibility(View.GONE);
                                } else {
                                    notice.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

        return view;
    }
}
