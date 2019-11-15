package com.example.cats.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cats.R;
import com.example.cats.activities.CatDetailActivity;
import com.example.cats.models.Cat;
import com.example.cats.models.FavouriteCat;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private ArrayList<FavouriteCat> favouriteCats = new ArrayList<>();

    public void setData(List<FavouriteCat> favouriteCats) {
        this.favouriteCats.clear();
        this.favouriteCats.addAll(favouriteCats);
        notifyDataSetChanged();
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder {
        public View v;
        public TextView catBreed;
        public ImageView catImage;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            catBreed = v.findViewById(R.id.catBreed);
            catImage = v.findViewById(R.id.catImage);
        }
    }

    @NonNull
    @Override
    public FavouriteAdapter.FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cat, parent, false);

        FavouriteAdapter.FavouriteViewHolder favouriteViewHolder = new FavouriteAdapter.FavouriteViewHolder(view);
        return favouriteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.FavouriteViewHolder holder, int position) {
        Gson gson = new Gson();
        final String catJson = favouriteCats.get(position).getCatJson();
        final Cat cat = gson.fromJson(catJson, Cat.class);
        holder.catBreed.setText(cat.getBreeds().get(0).getName());
        Glide.with(holder.v.getContext()).load(cat.getImageUrl()).into(holder.catImage);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent intent = new Intent(context, CatDetailActivity.class);
                intent.putExtra("catJson", catJson);
                intent.putExtra("comeFrom", "FavouriteAdapter");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(favouriteCats == null){
            return 0;
        }
        return favouriteCats.size();
    }
}
