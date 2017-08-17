package com.savefood.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.savefood.gui.ProductListActivity;
import com.savefood.persistency.DBHelper;
import com.savefood.persistency.ProductRepo;

import java.util.Date;


public class NotificationReceiver extends BroadcastReceiver {


    public NotificationReceiver() {
    }
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {


        DBHelper dbHelper = new DBHelper(context);
        ProductRepo productRepo = new ProductRepo(dbHelper);


        if (productRepo.hasProductsAboutToExpire()) {
            showNotification(context, intent);
        }

        scheduleNextAlarm(context);

    }

    private void showNotification(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);
    }


    private void scheduleNextAlarm(Context context) {
        NotificationBuilder builder = new NotificationBuilder(context.getApplicationContext());
        builder.scheduleNotification(new Intent(context.getApplicationContext(), ProductListActivity.class),"SaveFood", "Expiring products", "You have one or several expiring products. Click here to find out which." );
    }
}
