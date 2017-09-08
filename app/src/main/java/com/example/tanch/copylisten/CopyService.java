package com.example.tanch.copylisten;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class CopyService extends Service {
    ClipboardManager mClipboard;
    public CopyService() {
        Log.d("复制服务","CopyService初始化");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("复制服务","onBind");
        // TODO: Return the communication channel to the service.
        return new MyBind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("复制服务","onDestroy");
    }
    int count=0;
    @Override
    public void onCreate() {
        super.onCreate();
        mClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        mClipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                // 获取剪切板的ClipData数据对象
                ClipData clip = mClipboard.getPrimaryClip();
                // 获取到数据的类型
                String[] mimeTypes = clip != null ? clip.getDescription().filterMimeTypes("*/*") : null;
                ClipData.Item item = clip.getItemAt(0);
                Toast.makeText(CopyService.this,item.getText(),Toast.LENGTH_SHORT).show();
                Log.d("监听到复制内容",item.getText()+"");

                try{
                    String content=item.getText()+"";
                    Integer s=content.indexOf("pan.baidu");
                    if (s>=0)
                    {
                        Integer end=content.indexOf(" ",s)==-1?content.length():content.indexOf(" ",s);
                        String url=content.substring(s,end);
                        url="http://"+url;
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(url);
                        intent.setData(content_url);
                        //intent.setClassName("com.tencent.mtt", "com.tencent.mtt.MainActivity");
                        startActivity(intent);
                    }
                }catch (Exception e)
                {
                    Log.d("异常",e.getMessage());
                }


//                if (content.indexOf("http")>=0)
//                {
//                    String url=content;
//                    Intent intent = new Intent();
//                    //Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//                    intent.setAction("android.intent.action.VIEW");
//                    Uri content_url = Uri.parse(url);
//                    intent.setData(content_url);
//                    intent.setClassName("com.tencent.mtt", "com.tencent.mtt.MainActivity");
//                    startActivity(intent);
//                }
            }
        });
        Log.d("复制服务","onCreate");

    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("复制服务","onStartCommand");
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyBind extends Binder {
        public void logMessage(String msg){
            Log.e("testlog",msg);
        }
    }
}
