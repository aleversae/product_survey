package com.ameddi.productsurvey.persistency.db;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.health.connect.datatypes.AppInfo;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.UUID;

public class SurveyDescriptionDBOpenHelper extends SQLiteOpenHelper {


    public SurveyDescriptionDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, (name == null) ? generateRandomDbName():name, factory, version);
    }

    private static String generateRandomDbName() {

        String s;

        do {
            int iteration = 0;
            s = "survey-" + UUID.randomUUID().toString();
            if (iteration++ == 20)
                break;
        } while (candidateExists(s));
        return s;
    }

    @NonNull
    private static boolean candidateExists(String s) {
        String fullPathCandidate;
        File dataDirectory = Environment.getDataDirectory();

        Log.i("cositas", Environment.getRootDirectory().toString());
        //Log.i("cositas", Environment.);
        //Log
        //Environment.getStorageDirectory();


        fullPathCandidate = dataDirectory.getAbsolutePath() + File.separator + s;
        boolean fullPathCandidateExists = new File(fullPathCandidate).exists();
        boolean fullPathCandidatePlusDbExists = new File(fullPathCandidate + ".db").exists();
        Log.i("SURVEYDB", "Candidate: " + s);
        Log.i("SURVEYDB", "Fullpath: " + fullPathCandidate + (fullPathCandidateExists ? "👍" : "👎"));
        Log.i("SURVEYDB", "Fullpath: " + fullPathCandidate + ".db" + (fullPathCandidatePlusDbExists ? "👍" : "👎"));
        return fullPathCandidateExists || fullPathCandidatePlusDbExists;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Fields(name TEXT NOT NULL, type INT NOT NULL, column_name TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
