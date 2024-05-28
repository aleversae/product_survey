package com.ameddi.productsurvey.persistency.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseDBOpenHelper extends SQLiteOpenHelper {
    String surveyTableSQL = "CREATE TABLE Survey (name TEXT NOT NULL, details TEXT NOT NULL, document TEXT ) ";

    public BaseDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Survey table

        db.execSQL(surveyTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
