package com.example.alarmservice;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;

/**
 * Created by Administrator on 2016/8/29.
 */
public class MyService extends Service {

    private AlarmManager manager;
    private PendingIntent pendingIntent;
    private AlarmBinder binder = new AlarmBinder();
    private int time;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.setAction("ALARM_ACTION");
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + time * 1000, pendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentInfo("补充内容");
        builder.setContentText("主内容区");
        builder.setContentTitle("通知标题");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("新消息");
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pending = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pending);
        Notification notification = builder.build();

        NotificationManagerCompat.from(this).notify(R.string.you_have_news_messages, notification);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = new Notification();
        notification.icon = R.drawable.ic_launcher;
        notification.contentView = new RemoteViews(getPackageName(), R.drawable.ic_launcher);
        notification.contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        startForeground(1, notification);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2, notification);

    }

    public class AlarmBinder extends Binder {
        void setTime(int i) {
            time = i;
        }
    }
}
