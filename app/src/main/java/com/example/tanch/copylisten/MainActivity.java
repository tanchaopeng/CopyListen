package com.example.tanch.copylisten;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //启动服务
    protected void StartService(View view)
    {
        Intent startIntent = new Intent(this, CopyService.class);
        startService(startIntent);
        Toast.makeText(MainActivity.this, "启动服务 - "+new Date().getTime(), Toast.LENGTH_SHORT).show();
    }

    //停止服务
    protected void StopService(View view)
    {
        Intent stopIntent = new Intent(this, CopyService.class);
        stopService(stopIntent);
        Toast.makeText(MainActivity.this, "停止服务 - "+new Date().getTime(), Toast.LENGTH_SHORT).show();
    }
}
