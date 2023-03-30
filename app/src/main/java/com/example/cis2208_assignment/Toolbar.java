package com.example.cis2208_assignment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.cis2208_assignment.backend.DbHelper;

public class Toolbar extends Fragment {

    private ImageView back;
    private ImageView profile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toolbar, container, false);

        // Find the buttons in the layout
        back =  (ImageView) view.findViewById(R.id.back_button);
        profile = (ImageView) view.findViewById(R.id.profile_button);
        CardView card = (CardView) view.findViewById(R.id.profile_container);
        DbHelper helper = new DbHelper(view.getContext());

        String base = helper.getProfilePicture();
        if(base == null){
            profile.setImageResource(R.drawable.profile);
            card.setCardBackgroundColor(Color.TRANSPARENT);
        }
        else {
            byte[] decodedString = Base64.decode(base, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profile.setImageBitmap(bitmap);
            card.setCardBackgroundColor(Color.WHITE);

        }

        // Set click listeners for the buttons
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity a  = (Activity) v.getContext();
                a.onBackPressed();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Activity) v.getContext(), ProfileActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        return view;
    }
}
