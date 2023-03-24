package com.example.cis2208_assignment.difficulty_selection;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cis2208_assignment.DifficultySelection;
import com.example.cis2208_assignment.backend.DbHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DifficultySelectionViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> difficulties;
    private DbHelper helper;

    public DifficultySelectionViewModel(Application app) {
        super(app);
        helper = new DbHelper(app.getApplicationContext());
        difficulties = new MutableLiveData<>();
    }

    public MutableLiveData<List<String>> getDifficulties() {
        List<String> difficultyList = helper.getDifficulties();
        difficulties.setValue(difficultyList);
        return difficulties;
    }
}
