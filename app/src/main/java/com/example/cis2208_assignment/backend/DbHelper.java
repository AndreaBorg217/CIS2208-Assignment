package com.example.cis2208_assignment.backend;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> difficulties = new ArrayList<String>();
        String[] projection = {"difficulty"};
        Cursor cursor = db.query("questions", projection, null, null, null, null, null);
        System.out.println(cursor.getCount());
        while(cursor.moveToNext()){
            String diff = cursor.getString(cursor.getColumnIndexOrThrow("difficulty"));
            difficulties.add(diff);
        }
        List<String> unique = difficulties.stream().distinct().collect(Collectors.toList());
        return unique;
    }
}

