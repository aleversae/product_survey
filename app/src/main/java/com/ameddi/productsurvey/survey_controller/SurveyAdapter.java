package com.ameddi.productsurvey.survey_controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ameddi.productsurvey.R;
import com.ameddi.productsurvey.model.Survey;
import com.ameddi.productsurvey.persistency.MainDbManager;

import java.util.ArrayList;
import java.util.List;

public class SurveyAdapter extends ArrayAdapter<Survey> {


    MainDbManager mainDbManager = null;
    private OnEditSurveyListener onEditSurveyListener;

    public SurveyAdapter(@NonNull Context context) {
        super(context, R.layout.survey_list_element, new ArrayList<>());
        mainDbManager = new MainDbManager(context);
    }

    public void setOnEditSurveyListener(OnEditSurveyListener onEditSurveyListener) {
        this.onEditSurveyListener = onEditSurveyListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View element;
        if (convertView == null) {
            element = LayoutInflater.from(getContext()).inflate(R.layout.survey_list_element, parent, false);
        } else {
            element = convertView;
        }

        TextView name = element.findViewById(R.id.text1);
        TextView description = element.findViewById(R.id.survey_description);
        TextView actions = element.findViewById(R.id.survey_actions);
        Survey item = getItem(position);
        assert item != null;
        name.setText(item.getName());
        description.setText(item.getDetails());
        SurveyMenuOnCLickListener clickListener = new SurveyMenuOnCLickListener(getContext());
        clickListener.addAction(R.id.survey_delete, () -> {
            mainDbManager.removeSurvey(item);
            loadFromDB();
        });
        clickListener.addAction(R.id.survey_edit, () -> onEditSurveyListener.onEditSurvey(item));


        actions.setOnClickListener(clickListener);
        return element;
    }

    public void loadFromDB() {
        List<Survey> surveys = mainDbManager.getSurveys();
        clear();
        addAll(surveys);
    }

    public interface OnEditSurveyListener {
        void onEditSurvey(Survey s);
    }

}
