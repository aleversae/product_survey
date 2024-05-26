package com.ameddi.productsurvey.survey_controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ameddi.productsurvey.R;
import com.ameddi.productsurvey.model.Survey;
import com.ameddi.productsurvey.persistency.Persistency;

public class SurveyEditionActivity extends AppCompatActivity {
    Survey survey;
    EditText name;
    EditText description;
    Persistency persistency = null;
    boolean isNewSurvey = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey_edition);
        if (!applyInstanceState()) {
            if (!applySavedState(savedInstanceState)) {
                survey = Survey.defaultValue();
            }
        }

        assert survey != null;

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle((isNewSurvey) ? R.string.new_survey : R.string.edit_survey);
            //supportActionBar.hide();
        }
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

        //TODO:Fields
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.survey_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.opt_save) {
            boolean success;
            updateSurvey();
            if (persistency == null) {
                persistency = new Persistency(this);
            }
            if (isNewSurvey) {
                success = persistency.insert(survey);
            } else {
                success = persistency.update(survey);
            }
            if (success) {
                finish();
            } else {
                Toast.makeText(this, R.string.edit_survey_unable_to_insert, Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }

        if (itemId == R.id.opt_cancel) {
            finish();
        }
        return true;
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
                isNewSurvey = false;
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
}
