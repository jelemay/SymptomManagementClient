package com.coursera.symptommanagement.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.coursera.symptommanagement.services.ReminderAlarmService;

public class ReminderAlarmReceiver extends BroadcastReceiver {

    public ReminderAlarmReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent reminderService = new Intent(context, ReminderAlarmService.class);
        context.startService(reminderService);
    }
}
