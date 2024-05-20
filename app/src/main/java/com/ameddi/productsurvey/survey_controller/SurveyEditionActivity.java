package com.ameddi.productsurvey.survey_controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ameddi.productsurvey.R;
import com.ameddi.productsurvey.model.Survey;

public class SurveyEditionActivity extends AppCompatActivity {
    Survey survey;
    EditText name;
    EditText description;
    Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_survey_edition);

        if (!applySavedState(savedInstanceState)) {
            if (!applyInstanceState()) {
                survey = Survey.defaultValue();
            }
        }

        assert survey != null;

        //Name
        name = (EditText) findViewById(R.id.edit_survey_name);
        name.setText(survey.getName());
        name.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                EditText editText = (EditText) v;
                survey.setName(editText.getText().toString());
            }
        });

        //Description
        description = (EditText) findViewById(R.id.edit_survey_description);
        description.setText(survey.getDetails());
        description.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                EditText editText = (EditText) v;
                survey.setDetails(editText.getText().toString());
            }
        });


        btnCancel = findViewById(R.id.edit_cancel);
        btnCancel.setOnClickListener(v -> finish());

        btnSave = findViewById(R.id.edit_save);
        btnSave.setOnClickListener(v -> {
            Intent intent = new Intent(this, SurveyListActivity.class);
            if (survey != null) {
                updateSurvey();
                intent.putExtra("survey", survey);
            }
            startActivity(intent);
        });
    }

    private void updateSurvey() {
        survey.setName(name.getText().toString());
        survey.setDetails(description.getText().toString());

    }

    private boolean applyInstanceState() {
        Intent intent = getIntent();
        if (intent != null) {
            Survey survey = (Survey) intent.getParcelableExtra("survey");
            if (survey != null) {
                Toast.makeText(this, survey.toString(), Toast.LENGTH_SHORT).show();
                this.survey = survey;
                return true;
            }
        }
        return false;
    }

    private boolean applySavedState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (!savedInstanceState.isEmpty()) {
                survey = (Survey) savedInstanceState.getParcelable("survey");
                assert survey != null;

                Toast.makeText(this, "Got stored data :)", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("survey", survey);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    class Ofcl_ implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

        }
    }
}
