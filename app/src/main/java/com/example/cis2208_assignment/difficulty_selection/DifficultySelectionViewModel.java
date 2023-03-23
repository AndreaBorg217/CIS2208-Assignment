package com.example.cis2208_assignment.difficulty_selection;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cis2208_assignment.DifficultySelection;
import com.example.cis2208_assignment.backend.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class DifficultySelectionViewModel extends ViewModel {
    private MutableLiveData<List<String>> difficulties;

    public DifficultySelectionViewModel() {
        difficulties = new MutableLiveData<>();
    }

    public MutableLiveData<List<String>> getDifficulties() {
        List<String> difficultyList = new ArrayList<>();
        difficultyList.add("Easy");
        difficultyList.add("Medium");
        difficultyList.add("Hard");
        difficulties.setValue(difficultyList);
        return difficulties;
    }
}
