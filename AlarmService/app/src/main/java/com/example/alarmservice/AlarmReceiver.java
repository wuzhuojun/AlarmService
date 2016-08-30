package com.example.alarmservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2016/8/29.
 */
public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v("hehe", "receiver");
        context.startService(new Intent(context, MyService.class));
    }
}
