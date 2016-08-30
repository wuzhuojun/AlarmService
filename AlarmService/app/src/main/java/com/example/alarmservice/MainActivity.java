package com.example.alarmservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Intent intentService;
    private MyService.AlarmBinder binder;

    @BindView(R.id.edit_second)
    EditText editText;

    @OnClick(R.id.btn_start)
    void OnClickStart()
    {
        binder.setTime(Integer.parseInt(editText.getText().toString()));
        startService(intentService);

    }

    @OnClick(R.id.btn_stop)
    void OnClickStop()
    {
    }


    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MyService.AlarmBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        intentService = new Intent(this, MyService.class);
        bindService(intentService, connection, BIND_AUTO_CREATE);
    }
}
