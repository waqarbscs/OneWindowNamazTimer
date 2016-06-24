package com.OneWindowSol.waqarbscs.onewindownamaztimer.SilentService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.OneWindowSol.waqarbscs.onewindownamaztimer.R;
import com.OneWindowSol.waqarbscs.onewindownamaztimer.SilentMobile;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by waqarbscs on 6/1/2016.
 */
public class NotificaitionService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent resultIntent = new Intent(this,SilentMobile.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        Calendar cal=Calendar.getInstance();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setCategory(Notification.CATEGORY_PROMO)
                        .setContentTitle("Namaz Alert")
                        .setSmallIcon(R.drawable.masjidicon)
                        .setContentText("Namaz Performed in 15 minutes.")
                        .setVisibility(Notification.VISIBILITY_PRIVATE)
                        .setAutoCancel(true)
                        .setWhen((new Date()).getTime())
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        mBuilder.setContentIntent(resultPendingIntent);

        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        return super.onStartCommand(intent, flags, startId);
    }
}
