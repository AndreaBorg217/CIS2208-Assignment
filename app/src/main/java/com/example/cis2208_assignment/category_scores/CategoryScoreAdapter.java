package com.example.cis2208_assignment.category_scores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis2208_assignment.Category;
import com.example.cis2208_assignment.GameActivity;
import com.example.cis2208_assignment.R;

import org.w3c.dom.Text;

import java.util.List;

public class CategoryScoreAdapter extends RecyclerView.Adapter<CategoryScoreAdapter.ViewHolder>{
    private List<Category> scores;

    public CategoryScoreAdapter(List<Category> scores){
        this.scores = scores;
    }

    @NonNull
    @Override
    public CategoryScoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.category_score_card, parent, false);
        return new CategoryScoreAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryScoreAdapter.ViewHolder holder, int position) {
        Category c = scores.get(position);
        TextView name = holder.category_name;
        TextView score = holder.category_score;
        name.setText(c.categoryName);
        score.setText(c.categoryScore);

        ImageView pic = holder.category_icon;
        Context context = pic.getContext();
        int id = context.getResources().getIdentifier(c.categoryIcon, "drawable", context.getPackageName());
        pic.setImageResource(id);
    }

    public int getItemCount() {
        return this.scores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView category_name;
        public ImageView category_icon;
        public TextView category_score;

        public ViewHolder(final View itemView) {
            super(itemView);
            category_name = (TextView) itemView.findViewById(R.id.category_score_name);
            category_icon = (ImageView) itemView.findViewById(R.id.category_score_icon);
            category_score = (TextView) itemView.findViewById(R.id.category_score);
        }
    }
}
