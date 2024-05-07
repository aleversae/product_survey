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

import java.util.List;

public class SurveyAdapter extends ArrayAdapter<Survey> {

    public SurveyAdapter(@NonNull Context context, @NonNull List<Survey> objects) {

        super(context, R.layout.survey_list_element, objects);

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
        clickListener.addAction(R.id.survey_delete, () -> this.remove(item));
        actions.setOnClickListener(clickListener);
        return element;
    }
}
