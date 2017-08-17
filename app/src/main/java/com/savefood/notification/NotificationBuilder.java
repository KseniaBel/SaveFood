package com.savefood.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.savefood.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NotificationBuilder {

    public static final String ALARM_TIME_FORMAT = "kk:mm";
    public static final String ALARM_TIME = "13:01";

    private Context context;

    public NotificationBuilder(Context context) {
        this.context = context;
    }

    /** Create an intent that will be fired when the user clicks the notification.
     * The intent needs to be packaged into a {@link android.app.PendingIntent} so that the
     * notification service can fire it on our behalf.
     */
    private Notification buildNotification(Intent intentToFire, String title, String text, String subText) {

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentToFire, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.expired_products_list);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setSubText(subText);

        return builder.build();
    }

    public void scheduleNotification(Notification notification) {

        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Date scheduledFor = null;

        try {
            scheduledFor = new SimpleDateFormat(ALARM_TIME_FORMAT).parse(ALARM_TIME);
            Calendar scheduledForCalendar = Calendar.getInstance();
            scheduledForCalendar.setTime(scheduledFor);

            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.HOUR_OF_DAY, scheduledForCalendar.get(Calendar.HOUR_OF_DAY));
            calendar1.set(Calendar.MINUTE, scheduledForCalendar.get(Calendar.MINUTE));
            scheduledFor = calendar1.getTime();
        } catch (ParseException e) {
        }

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        long futureInMillis = scheduledFor.getTime();

        if (futureInMillis < Calendar.getInstance().getTimeInMillis()) {
            futureInMillis += AlarmManager.INTERVAL_DAY;
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);

    }


    public void scheduleNotification ( Intent intentToFire, String title, String text, String subText ) {
        Notification notification = buildNotification(intentToFire, title, text, subText);
        scheduleNotification(notification);
    }
}
