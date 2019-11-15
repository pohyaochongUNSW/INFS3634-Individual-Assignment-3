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

import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {

    private ArrayList<Cat> cats;

    public void setData(ArrayList<Cat> cats) {
        this.cats = cats;
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {
        public View v;
        public TextView catBreed;
        public ImageView catImage;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            catBreed = v.findViewById(R.id.catBreed);
            catImage = v.findViewById(R.id.catImage);
        }
    }

    @NonNull
    @Override
    public CatAdapter.CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cat, parent, false);

        CatViewHolder catViewHolder = new CatViewHolder(view);
        return catViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatAdapter.CatViewHolder holder, int position) {
        final Cat cat = cats.get(position);
        holder.catBreed.setText(cat.getBreeds().get(0).getName());
        Glide.with(holder.v.getContext()).load(cat.getImageUrl()).into(holder.catImage);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent intent = new Intent(context, CatDetailActivity.class);
                intent.putExtra("catId", cat.getBreeds().get(0).getId());
                intent.putExtra("comeFrom", "CatAdapter");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(cats == null){
            return 0;
        }
        return cats.size();
    }
}
