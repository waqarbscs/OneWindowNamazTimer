package com.OneWindowSol.waqarbscs.onewindownamaztimer.SilentService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by waqarbscs on 5/24/2016.
 */
public class silentService extends Service {


    Calendar calendar;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Start", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ArrayList<String> values = intent.getStringArrayListExtra("abc");
        String fs = values.get(0);
        String fe = values.get(1);
        String zs = values.get(2);
        String ze = values.get(3);
        String as = values.get(4);
        String ae = values.get(5);
        String ms = values.get(6);
        String me = values.get(7);
        String es = values.get(8);
        String ee = values.get(9);

        int fshour = Integer.parseInt(fs.substring(0, 2));
        int fsminute = Integer.parseInt(fs.substring(3, 5));

        int fehour = Integer.parseInt(fe.substring(0, 2));
        int feminute = Integer.parseInt(fe.substring(3, 5));

        int zshour = Integer.parseInt(zs.substring(0, 2));
        int zsminute = Integer.parseInt(zs.substring(3, 5));

        int zehour = Integer.parseInt(ze.substring(0, 2));
        int zeminute = Integer.parseInt(ze.substring(3, 5));

        int ashour = Integer.parseInt(as.substring(0, 2));
        int asminute = Integer.parseInt(as.substring(3, 5));

        int aehour = Integer.parseInt(ae.substring(0, 2));
        int aeminute = Integer.parseInt(ae.substring(3, 5));

        int mshour = Integer.parseInt(ms.substring(0, 2));
        int msminute = Integer.parseInt(ms.substring(3, 5));

        int mehour = Integer.parseInt(me.substring(0, 2));
        int meminute = Integer.parseInt(me.substring(3, 5));

        int eshour = Integer.parseInt(es.substring(0, 2));
        int esminute = Integer.parseInt(es.substring(3, 5));

        int eehour = Integer.parseInt(ee.substring(0, 2));
        int eeminute = Integer.parseInt(ee.substring(3, 5));

        Log.d("fsmin0", Integer.toString(zsminute));
        Log.d("fsmin1", Integer.toString(zeminute));
        Log.d("fsmin2", Integer.toString(fsminute));
        Log.d("fsmin3", Integer.toString(feminute));

        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if ((hour == fshour && minute == fsminute)) {
            AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else if ((hour == zshour && minute == zsminute)) {
            AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else if ((hour == ashour && minute == asminute)) {
            AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else if ((hour == mshour && minute == msminute)) {
            AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else if ((hour == eshour && minute == esminute)) {
            AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        if (hour == fehour && minute == feminute) {
            AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        } else if (hour == zehour && minute == zeminute) {
            AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        } else if (hour == aehour && minute == aeminute) {
            AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        } else if (hour == mehour && minute == meminute) {
            AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        } else if (hour == eehour && minute == eeminute) {
            AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        if((hour==fshour)&&(minute==(fsminute-15))){
            Calendar calendar1=Calendar.getInstance();
            calendar1.set(Calendar.HOUR_OF_DAY,hour);
            calendar1.set(Calendar.MINUTE, minute);
            calendar1.set(Calendar.SECOND, 0);
            Intent intentAlarm = new Intent(this, NotificaitionService.class);
            PendingIntent alarmIntent;
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmIntent= PendingIntent.getService(this,0,intentAlarm,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }else if ((hour == zshour && minute == (zsminute-15))) {
            Calendar calendar1=Calendar.getInstance();
            calendar1.set(Calendar.HOUR_OF_DAY,hour);
            calendar1.set(Calendar.MINUTE, minute);
            calendar1.set(Calendar.SECOND, 0);
            Intent intentAlarm = new Intent(this, NotificaitionService.class);
            PendingIntent alarmIntent;
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmIntent= PendingIntent.getService(this,0,intentAlarm,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        } else if ((hour == ashour && minute == (asminute-15))) {
            Calendar calendar1=Calendar.getInstance();
            calendar1.set(Calendar.HOUR_OF_DAY,hour);
            calendar1.set(Calendar.MINUTE, minute);
            calendar1.set(Calendar.SECOND, 0);
            Intent intentAlarm = new Intent(this, NotificaitionService.class);
            PendingIntent alarmIntent;
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmIntent= PendingIntent.getService(this,0,intentAlarm,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        } else if ((hour == mshour && minute == (msminute-15))) {
            Calendar calendar1=Calendar.getInstance();
            calendar1.set(Calendar.HOUR_OF_DAY,hour);
            calendar1.set(Calendar.MINUTE, minute);
            calendar1.set(Calendar.SECOND, 0);
            Intent intentAlarm = new Intent(this, NotificaitionService.class);
            PendingIntent alarmIntent;
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmIntent= PendingIntent.getService(this,0,intentAlarm,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        } else if ((hour == eshour && minute == (esminute-15))) {
            Calendar calendar1=Calendar.getInstance();
            calendar1.set(Calendar.HOUR_OF_DAY,hour);
            calendar1.set(Calendar.MINUTE, minute);
            calendar1.set(Calendar.SECOND, 0);
            Intent intentAlarm = new Intent(this, NotificaitionService.class);
            PendingIntent alarmIntent;
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Random rand = new Random(calendar1.getTimeInMillis());
            alarmIntent= PendingIntent.getService(this,0,intentAlarm,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(silentService.this, "Destroy service", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
