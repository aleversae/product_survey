package com.ameddi.productsurvey.persistency;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ameddi.productsurvey.persistency.db.BaseDBOpenHelper;
import com.ameddi.productsurvey.model.Survey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainDbManager {
    private final Context context;
    private final int surveyDbVersion = 2;
    private final String SurveyDbName = "surveyDB";
    private final String allSurveys = "SELECT rowId, name, details, remoteKey FROM Survey ";

    BaseDBOpenHelper surveyDbHelper;

    public MainDbManager(Context context) {
        this.context = context;
        surveyDbHelper = new BaseDBOpenHelper(context, SurveyDbName, null, surveyDbVersion);
    }


    public List<Survey> getSurveys() {

        SQLiteDatabase readableDatabase = surveyDbHelper.getReadableDatabase();

        Cursor surveyCursor = readableDatabase.rawQuery(allSurveys, null);
        ArrayList<Survey> surveys = new ArrayList<>();

        if (surveyCursor.moveToFirst()) {
            do {
                surveys.add(readSurvey(surveyCursor));
            } while (surveyCursor.moveToNext());
        }

        return surveys;
    }

    //Expects a cursor correctly positioned in a record
    private Survey readSurvey(Cursor surveyCursor) {
        Survey survey = new Survey();
        survey.setName(surveyCursor.getString(1));
        survey.setDetails(surveyCursor.getString(2));
        survey.setRemoteKey(surveyCursor.getString(3));
        survey.setId(surveyCursor.getInt(0));
        //document
        return survey;
    }


    public boolean insert(Survey survey) {
        SQLiteDatabase writableDatabase = surveyDbHelper.getWritableDatabase();
        Object[] param = new Object[2];
        param[0] = survey.getName();
        param[1] = survey.getDetails();
        param[1] = survey.getRemoteKey();
        try {
            writableDatabase.beginTransaction();
            writableDatabase.execSQL("INSERT INTO Survey(name, details, remoteKey) values(? , ?, ? )", param);
            //document creation
            writableDatabase.endTransaction();

        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    public boolean update(Survey survey) {
        SQLiteDatabase writableDatabase = surveyDbHelper.getWritableDatabase();
        Object[] param = new Object[4];
        param[0] = survey.getName();
        param[1] = survey.getDetails();
        param[2] = survey.getRemoteKey();
        param[3] = survey.getId();
        try {
            writableDatabase.beginTransaction();
            writableDatabase.execSQL("UPDATE Survey SET name = ?, details = ?, remoteKey = ? WHERE rowid = ?", param);
            writableDatabase.endTransaction();

        } catch (SQLException e) {
            return false;
        }
        return true;
    }


    public void removeSurvey(Survey item) {
        SQLiteDatabase writableDatabase = surveyDbHelper.getWritableDatabase();
        Integer[] params = {item.getId()};
        writableDatabase.execSQL("DELETE FROM Survey WHERE rowid=?", params);

    }
}
