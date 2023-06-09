package com.example.cis2208_assignment;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cis2208_assignment.databinding.ActivityDifficultyScoresBinding;
import com.example.cis2208_assignment.ui.easy.EasyFragment;
import com.example.cis2208_assignment.ui.hard.HardFragment;
import com.example.cis2208_assignment.ui.medium.MediumFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DifficultyScores extends AppCompatActivity {

    private ActivityDifficultyScoresBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDifficultyScoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_difficulty_scores);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Hide the app bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        // Set the color of easy menu item to yellow by default
        MenuItem item = bottomNavigationView.getMenu().getItem(0);
        SpannableString spannableString = new SpannableString(item.getTitle());
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(DifficultyScores.this, R.color.yellow)), 0, spannableString.length(), 0);
        item.setTitle(spannableString);

        // Set a listener to detect when a menu item is selected
        // Handler for when a menu option is selected
        bottomNavigationView.setOnItemSelectedListener(item1 -> {

            // Loop through all menu items to reset the color to white
            int size = bottomNavigationView.getMenu().size();
            for (int i = 0; i < size; i++) {
                MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
                SpannableString spannableString1 = new SpannableString(menuItem.getTitle());
                spannableString1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(DifficultyScores.this, R.color.white)), 0, spannableString1.length(), 0);
                menuItem.setTitle(spannableString1);
            }

            // Set the color of the selected menu item to yellow
            SpannableString spannableString1 = new SpannableString(item1.getTitle());
            spannableString1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(DifficultyScores.this, R.color.yellow)), 0, spannableString1.length(), 0);
            item1.setTitle(spannableString1);

            // Switch fragment to the one chosen
            Fragment selectedFragment = null;
            switch (item1.getItemId()) {
                case R.id.navigation_easy:
                    selectedFragment = new EasyFragment();
                    break;
                case R.id.navigation_medium:
                    selectedFragment = new MediumFragment();
                    break;
                case R.id.navigation_hard:
                    selectedFragment = new HardFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_difficulty_scores, selectedFragment).commit();

            return true;
        });
    }



}