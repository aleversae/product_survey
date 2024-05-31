package com.ameddi.productsurvey.survey_controller;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.EnvironmentCompat;

import com.ameddi.productsurvey.R;
import com.ameddi.productsurvey.persistency.db.BaseDBOpenHelper;
import com.ameddi.productsurvey.model.Survey;
import com.ameddi.productsurvey.persistency.db.SurveyDescriptionDBOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseKt;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;

public class SurveyListActivity extends AppCompatActivity {
    BaseDBOpenHelper dbHelper;
    private ArrayList<Survey> surveys;
    private SurveyAdapter surveyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey_list);

        surveyAdapter = new SurveyAdapter(this);
        ListView surveysView = findViewById(R.id.surveys_view);
        surveysView.setAdapter(surveyAdapter);

        Survey[] surveys_ = null;
        if (savedInstanceState != null) {
            if (!savedInstanceState.isEmpty()) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    surveys_ = savedInstanceState.getParcelableArray("surveys", Survey.class);
                } else {
                    surveys_ = (Survey[]) savedInstanceState.getParcelableArray("surveys");
                }
                assert surveys_ != null;
            }
        }

        if (surveys_ != null) {
            surveyAdapter.addAll(surveys_);
        } else {
            surveyAdapter.loadFromDB();
        }

        surveyAdapter.setOnEditSurveyListener(s -> {
            Intent intent = new Intent(this, SurveyEditionActivity.class);
            if (s != null) {
                intent.putExtra("survey", s);
            }
            startActivity(intent);
        });
        FloatingActionButton addButton = findViewById(R.id.survey_add);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SurveyEditionActivity.class);
            startActivity(intent);
        });

        FloatingActionButton experimentoButton = findViewById(R.id.experimento);
        experimentoButton.setOnClickListener(v -> {
            SurveyDescriptionDBOpenHelper surveyDescriptionDBOpenHelper = new SurveyDescriptionDBOpenHelper(this, null, null, 1);

            SQLiteDatabase writableDatabase = surveyDescriptionDBOpenHelper.getWritableDatabase();
            File databasePath = this.getDatabasePath(surveyDescriptionDBOpenHelper.getDatabaseName());
            Log.i("tema", databasePath + ((databasePath.exists())?"👍":"👎"));

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        surveyAdapter.loadFromDB();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int count = surveyAdapter.getCount();
        Survey[] surveys = new Survey[count];
        for (int i = 0; i < count; i++) {
            surveys[i] = surveyAdapter.getItem(i);
        }
        outState.putParcelableArray("surveys", surveys);
    }


}