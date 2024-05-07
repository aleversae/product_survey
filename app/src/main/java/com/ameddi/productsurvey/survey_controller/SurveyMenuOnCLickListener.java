package com.ameddi.productsurvey.survey_controller;

import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.ameddi.productsurvey.R;

import java.util.HashMap;

public class SurveyMenuOnCLickListener implements View.OnClickListener {

    private final Context context;
    private final HashMap<Integer, Runnable> actions;

    public SurveyMenuOnCLickListener(Context context) {
        this.actions = new HashMap<Integer, Runnable>();

        this.context = context;
    }

    public SurveyMenuOnCLickListener addAction(int resource, Runnable action) {
        actions.put(resource, action);
        return this;
    }

    @Override
    public void onClick(View v) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.getMenuInflater().inflate(R.menu.survey_actions, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            Toast.makeText(context, menuItem.getTitle(), Toast.LENGTH_LONG).show();
            Runnable action = actions.get(menuItem.getItemId());
            if (action != null) {
                action.run();
                return true;
            } else {
                return false;
            }
        });
        popupMenu.show();

    }

}

