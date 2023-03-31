package com.example.cis2208_assignment.category_scores;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cis2208_assignment.Category;
import com.example.cis2208_assignment.backend.DbHelper;

import java.util.List;

public class CategoryScoreViewModel extends AndroidViewModel {
    private MutableLiveData<List<Category>> scores;
    private DbHelper helper;

    public CategoryScoreViewModel(Application app) {
        super(app);
        helper = new DbHelper(app.getApplicationContext());
        scores = new MutableLiveData<>();
    }

    public MutableLiveData<List<Category>> getCategoryScores() {
        List<Category> scoreList = helper.getAllCategoryScores();
        scores.setValue(scoreList);
        return scores;
    }
}
