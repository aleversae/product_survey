package com.ameddi.productsurvey.survey_controller;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ameddi.productsurvey.R;
import com.ameddi.productsurvey.db.BaseDBOpenHelper;
import com.ameddi.productsurvey.model.Survey;
import com.ameddi.productsurvey.persistency.Persistency;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SurveyListActivity extends AppCompatActivity {
    private ArrayList<Survey> surveys;

    private SurveyAdapter surveyAdapter;
    BaseDBOpenHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseDBOpenHelper dbHelper = new BaseDBOpenHelper(this, "Surveys", null, 1);



        setContentView(R.layout.activity_survey_list);

        surveyAdapter = new SurveyAdapter(this, new ArrayList<>());
        surveyAdapter.addEditClick(gotoEdit());
        ListView surveysView = findViewById(R.id.surveys_view);

        surveysView.setAdapter(surveyAdapter);

        if (savedInstanceState != null) {
            if (!savedInstanceState.isEmpty()) {
                Survey[] surveys_ ;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    surveys_ = savedInstanceState.getParcelableArray("surveys", Survey.class);
                }
                else
                {
                    surveys_ =(Survey[]) savedInstanceState.getParcelableArray("surveys");
                }
                assert surveys_ != null;

                surveyAdapter.addAll(surveys_);
            }
        }
        Persistency persistency = new Persistency(this);
        List<Survey> surveys1 = persistency.getSurveys();
        surveyAdapter.addAll(surveys1);
        FloatingActionButton addButton = findViewById(R.id.survey_add);
        addButton.setOnClickListener(v->gotoEdit().apply(null).run());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Persistency persistency = new Persistency(this);
        List<Survey> surveys1 = persistency.getSurveys();
        surveyAdapter.clear();
        surveyAdapter.addAll(surveys1);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int count = surveyAdapter.getCount();
        Survey[] surveys = new Survey[count];
        for (int i = 0; i < count; i++) {
            surveys[i]= surveyAdapter.getItem(i);
        }
        outState.putParcelableArray("surveys", surveys);
    }
    private Function<Survey, Runnable> gotoEdit(){
        Context context = this;
        return survey->new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, SurveyEditionActivity.class);
                if(survey != null){
                    intent.putExtra("survey", survey);
                }
                startActivity(intent);
            }
        };
    }


}