package com.alia.sisdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SisdiaryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "sisdiary";
    private static final int DB_VERSION = 1;

    SisdiaryDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SUBJECT (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "DAY TEXT, "
                + "HOMEWORK TEXT);");
        insertSubject(db, "Математика", "ПН", "домашка");
        insertSubject(db, "Фіз-ра", "ПН","домашка");
        insertSubject(db, "Укр. мова", "ПН","домашка");
        insertSubject(db, "Укр. мова", "ПН","домашка");
        insertSubject(db, "Біологія", "ПН", "домашка");
        insertSubject(db, "Біологія", "ПН", "домашка");
        insertSubject(db, "Біологія", "ВТ", "домашка");
        insertSubject(db, "Англ. мова", "ВТ", "домашка");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertSubject(SQLiteDatabase db, String name, String day, String homework){
        ContentValues subjectValues = new ContentValues();
        subjectValues.put("NAME", name);
        subjectValues.put("DAY", day);
        subjectValues.put("HOMEWORK", homework);
        db.insert("SUBJECT", null, subjectValues);
    }
}
