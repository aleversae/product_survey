package com.ameddi.productsurvey.survey_controller;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ameddi.productsurvey.R;
import com.ameddi.productsurvey.model.Survey;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SurveyList extends AppCompatActivity {
    private List<Survey> surveys;

    private SurveyAdapter surveyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_list);

        SurveyAdapter surveyAdapter = new SurveyAdapter(this, new ArrayList<>());
        ListView surveysView = findViewById(R.id.surveys_view);

        surveysView.setAdapter(surveyAdapter);
        surveyAdapter.add(Survey.random());

        FloatingActionButton addButton = findViewById(R.id.survey_add);
        addButton.setOnClickListener(v -> {
            surveyAdapter.add(Survey.random());
        });
    }
}