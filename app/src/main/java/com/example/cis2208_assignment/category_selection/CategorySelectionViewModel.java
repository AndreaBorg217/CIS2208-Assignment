package com.example.cis2208_assignment.category_selection;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cis2208_assignment.Category;
import com.example.cis2208_assignment.backend.DbHelper;

import java.util.List;

public class CategorySelectionViewModel extends AndroidViewModel {
    private MutableLiveData<List<Category>> categories;
    private DbHelper helper;

    public CategorySelectionViewModel(Application app) {
        super(app);
        helper = new DbHelper(app.getApplicationContext());
        categories = new MutableLiveData<>();
    }

    public MutableLiveData<List<Category>> getDifficulties() {
        List<Category> categoryList = helper.getAllCategories();
        categories.setValue(categoryList);
        return categories;
    }

}
