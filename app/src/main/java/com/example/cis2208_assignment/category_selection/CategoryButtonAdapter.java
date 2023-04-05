package com.example.cis2208_assignment.category_selection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis2208_assignment.Category;
import com.example.cis2208_assignment.GameActivity;
import com.example.cis2208_assignment.R;

import java.util.List;

public class CategoryButtonAdapter extends RecyclerView.Adapter<CategoryButtonAdapter.ViewHolder>{
    private List<Category> categories;

    public CategoryButtonAdapter(List<Category> categories){
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryButtonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.category_button, parent, false);
        return new CategoryButtonAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // For every category in the db, we create a button with its name and icon

        Category c = categories.get(position);
        TextView name = holder.name;
        name.setText(c.categoryName);
        ImageView icon = holder.icon;

        Context context = icon.getContext();
        int id = context.getResources().getIdentifier(c.categoryIcon, "drawable", context.getPackageName());
        Drawable pic = context.getResources().getDrawable(id);
        icon.setImageDrawable(pic);

    }


    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView icon;

        public ViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.category_name);
            icon = (ImageView) itemView.findViewById(R.id.category_icon);
            CardView card = (CardView) itemView.findViewById(R.id.category_button);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent fetch = ((Activity) v.getContext()).getIntent();
                    // We fetch the difficulty from the previous activity
                    String difficulty = fetch.getStringExtra("DIFFICULTY");
                    Intent intent = new Intent(v.getContext(), GameActivity.class);
                    String cat = name.getText().toString();
                    // We pass the Game Screen the category chosen in this activity &
                    // the difficulty chosen in the previous
                    intent.putExtra("CATEGORY", cat);
                    intent.putExtra("DIFFICULTY", difficulty);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
