package com.ameddi.productsurvey.survey_controller;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.ameddi.productsurvey.model.Field;

public class FieldAdapter extends ArrayAdapter<Field> {
    public FieldAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }



}
