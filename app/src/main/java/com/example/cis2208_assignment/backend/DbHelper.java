package com.example.cis2208_assignment.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cis2208_assignment.Category;
import com.example.cis2208_assignment.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "trivia.db";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS \"categories\" " +
                "(\"categoryID\" INTEGER PRIMARY KEY, " +
                "\"categoryName\" TEXT NOT NULL, " +
                "\"icon\" TEXT NOT NULL);"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS \"questions\" (" +
                "\"questionID\" INTEGER PRIMARY KEY, " +
                "\"question\" TEXT NOT NULL, " +
                "\"difficulty\" TEXT NOT NULL, " +
                "\"answer_1\" TEXT NOT NULL, " +
                "\"answer_2\" TEXT NOT NULL, " +
                "\"answer_3\" TEXT NOT NULL, " +
                "\"correctAnswer\" TEXT NOT NULL, " +
                "\"answeredCorrectly\" INTEGER NOT NULL DEFAULT 0, " +
                "\"categoryID\" INTEGER, " +
                "FOREIGN KEY(\"categoryID\") REFERENCES \"categories\"(\"categoryID\"));"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS \"difficulties\" (" +
                "\"difficultyLevel\" INTEGER, " +
                "\"difficulty\" TEXT PRIMARY KEY);");

        db.execSQL("CREATE TABLE IF NOT EXISTS \"user\" (" +
                "\"userID\" INTEGER,"+
                "\"high_score\" INTEGER, " +
                "\"profile_pic\" TEXT);");

        String[] insertions = Insertions.insertions;
        for(int i = 0; i<insertions.length; i++){
            db.execSQL(insertions[i]);
        }


    }



    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(dropTables("questions"));
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public String dropTables(String tableName){
        return "DROP TABLE IF EXISTS " + tableName;

    }

    public List<String> getDifficulties(){
        // Gets a list of all difficulties in the db
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> difficulties = new ArrayList<String>();
        String[] projection = {"difficulty"};
        String sortOrder = "difficultyLevel ASC";
        Cursor cursor = db.query("difficulties", projection, null, null, null, null, sortOrder);
        System.out.println(cursor.getCount());
        while(cursor.moveToNext()){
            String diff = cursor.getString(cursor.getColumnIndexOrThrow("difficulty"));
            difficulties.add(diff);
        }
        return difficulties;
    }

    public List<Category> getAllCategories(){
        // Gets a list of all categories in the db
        ArrayList<Category> categories = new ArrayList<Category>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"categoryID", "categoryName", "icon"};
        Cursor cursor = db.query("categories", projection, null, null, null, null, null);

        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("categoryID"));
            String category = cursor.getString(cursor.getColumnIndexOrThrow("categoryName"));
            String icon = cursor.getString(cursor.getColumnIndexOrThrow("icon"));
            Category toAdd = new Category(id, category, icon);
            categories.add(toAdd);
        }
        List<Category> unique = categories.stream().distinct().collect(Collectors.toList());
        return unique;
    }

    public int getCategoryID(String category){
        // Gets the categoryID of a categoryName in the db
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"categoryID"};
        String selection = "categoryName = ?";
        String[] selectionArgs = {category};
        Cursor cursor = db.query("categories", projection, selection, selectionArgs, null, null, null);
        int id = 0;
        while(cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndexOrThrow("categoryID"));
        }
        return id;
    }

    public ArrayList<Question> getTenQuestions(String difficulty, int category){
        // Gets a unique list of 10 questions having a particular difficulty & category
        ArrayList<Question> questions = new ArrayList<Question>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"questionID", "question", "answer_1", "answer_2", "answer_3", "correctAnswer"};
        String selection = "difficulty = ? AND categoryID = ?";
        String[] selectionArgs = {difficulty, Integer.toString(category)};
        Cursor cursor = db.query("questions", projection, selection, selectionArgs, null, null, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("questionID"));
            String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
            String ans_1 = cursor.getString(cursor.getColumnIndexOrThrow("answer_1"));
            String ans_2 = cursor.getString(cursor.getColumnIndexOrThrow("answer_2"));
            String ans_3 = cursor.getString(cursor.getColumnIndexOrThrow("answer_3"));
            String correct = cursor.getString(cursor.getColumnIndexOrThrow("correctAnswer"));

            Question toAdd = new Question(id, question, difficulty, correct, ans_1, ans_2, ans_3);
            questions.add(toAdd);
        }
        Collections.shuffle(questions);
        ArrayList<Question> toReturn = new ArrayList<Question>();
        for(int i = 0; i<10; i++){
            toReturn.add(questions.get(i));
        }
        return toReturn;
    }

    public void setCorrectAnswered(int questionId){
        // Sets a question's correctlyAnswered field
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataToUpdate = new ContentValues();
        dataToUpdate.put("answeredCorrectly", 1);
        String selection = "questionID = ?";
        String[] selectionArgs = {Integer.toString(questionId)};
        db.update("questions", dataToUpdate, selection, selectionArgs);
    }

    public void updateProfilePicture(String base){
        // Inserts a Base64 string when a profile picture is chosen from gallery
        System.out.println(base.length());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataToUpdate = new ContentValues();
        dataToUpdate.put("profile_pic", base);
        String selection = "userID = ?";
        String[] selectionArgs = {"1"};
        db.update("user", dataToUpdate, selection, selectionArgs);
    }

    public String getProfilePicture(){
        // Obtains the Base64 profile picture
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"profile_pic"};
        Cursor cursor = db.query("user", projection, null, null, null, null, null);
        String pic = null;
        if(cursor.moveToFirst()){
            pic =  cursor.getString(cursor.getColumnIndexOrThrow("profile_pic"));
        }
        return pic;
    }


    public void updateHighScore(int score){
        // Updates user's highscore
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataToUpdate = new ContentValues();
        dataToUpdate.put("high_score", score);
        String selection = "userID = ?";
        String[] selectionArgs = {Integer.toString(1)};
        db.update("user", dataToUpdate, selection, selectionArgs);
    }

    public int getHighScore(){
        // Fetches user's highscore
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"high_score"};
        Cursor cursor = db.query("user", projection, null, null, null, null, null);
        int score = 0;
        if(cursor.moveToFirst()){
            score =  cursor.getInt(cursor.getColumnIndexOrThrow("high_score"));
        }
        return score;
    }


    public List<Category> getAllCategoryScores(){
        // Returns a list of objects to create the Category score cards
        ArrayList<Category> categories = new ArrayList<Category>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"categoryID", "categoryName", "icon"};
        Cursor cursor = db.query("categories", projection, null, null, null, null, null);

        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("categoryID"));
            String category = cursor.getString(cursor.getColumnIndexOrThrow("categoryName"));
            String icon = cursor.getString(cursor.getColumnIndexOrThrow("icon"));
            String score = this.getCategoryScore(id);
            Category toAdd = new Category(id, category, icon, score);
            categories.add(toAdd);
        }
        List<Category> unique = categories.stream().distinct().collect(Collectors.toList());
        return unique;
    }

    public String getCategoryScore(int id){
        // Returns guessed/total for a category in the db
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"questionID"};
        String selection = "answeredCorrectly = 1 AND categoryID = ?";
        String[] selectionArgs = {Integer.toString(id)};
        Cursor correct = db.query("questions", projection, selection, selectionArgs, null, null, null);

        selection = "categoryID = ?";
        Cursor total = db.query("questions", projection, selection, selectionArgs, null, null, null);

        return correct.getCount() + "/" + total.getCount();
    }

    public String getDifficultyScore(String difficulty){
        // Returns guessed/total for a difficulty in the db
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"questionID"};
        String selection = "answeredCorrectly = 1 AND difficulty = ?";
        String[] selectionArgs = {difficulty};
        Cursor correct = db.query("questions", projection, selection, selectionArgs, null, null, null);

        selection = "difficulty = ?";
        Cursor total = db.query("questions", projection, selection, selectionArgs, null, null, null);

        return correct.getCount() + "/" + total.getCount();
    }
}

