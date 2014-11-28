package com.coursera.symptommanagement.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.activities.patient.PatientCheckInActivity;

public class ReminderAlarmService extends Service {

    public static final String SERVICE_NAME = "Reminder Alarm Service: ";

    private NotificationManager mManager;


    public ReminderAlarmService() {}


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Log.d(SERVICE_NAME, "Entered onStartCommand()");

        Intent reminderIntent = new Intent(this.getApplicationContext(), PatientCheckInActivity.class);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                                                .setSmallIcon(R.drawable.ic_launcher)
                                                .setContentTitle("Check In Reminder!")
                                                .setContentText("Please complete your Check In.");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(PatientCheckInActivity.class);
        stackBuilder.addNextIntent(reminderIntent);

        PendingIntent reminderPendingIntent = stackBuilder.getPendingIntent(0,
                                                    PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(reminderPendingIntent);

        mManager = (NotificationManager) this.getApplicationContext()
                .getSystemService(NOTIFICATION_SERVICE);

        mManager.notify(startId, mBuilder.build());

        return startId;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
