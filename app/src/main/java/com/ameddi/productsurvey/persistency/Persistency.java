package com.ameddi.productsurvey.persistency;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ameddi.productsurvey.db.BaseDBOpenHelper;
import com.ameddi.productsurvey.model.Survey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Persistency {
    private final Context context;
    private final int surveyDbVersion =1;
    private final String SurveyDbName="surveyDB";
    BaseDBOpenHelper surveyDbHelper;
    private final String allSurveys="SELECT rowId, name, details FROM Survey ";

    public Persistency(Context context) {
        this.context = context;
        surveyDbHelper = new BaseDBOpenHelper(context, SurveyDbName, null, surveyDbVersion);
    }


    public List<Survey> getSurveys() {
        SQLiteDatabase readableDatabase = surveyDbHelper.getReadableDatabase();
        //readableDatabase.execSQL(allSurveys);
        String[] columns = (String[])Arrays.asList("rowid", "name", "details").toArray();

        Cursor surveyCursor = readableDatabase.rawQuery(allSurveys, null);
        ArrayList<Survey> surveys = new ArrayList<>();
        if(surveyCursor.moveToFirst()){
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
        survey.setId(surveyCursor.getInt(0));
        return survey;
    }


    public boolean insert(Survey survey) {
        SQLiteDatabase writableDatabase = surveyDbHelper.getWritableDatabase();
        Object[] param = new Object[2];
        param[0] = survey.getName();
        param[1] = survey.getDetails();


        try {
            writableDatabase.execSQL("INSERT INTO Survey(name, details) values(? , ? )" , param);
        }
        catch (SQLException e){
            return false;
        }
        return true;
    }
}
