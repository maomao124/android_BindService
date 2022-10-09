package mao.android_bindservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service
{

    /**
     * 标签
     */
    private static final String TAG = "MyService";

    private int count;

    private volatile boolean quit;


    //定义onBinder方法所返回的对象
    private final MyBinder binder = new MyBinder();

    public class MyBinder extends Binder
    {
        public int getCount()
        {
            return count;
        }
    }


    public MyService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG, "onBind: ");
        return binder;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        Log.d(TAG, "onCreate: ");
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (!quit)
                {
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        }).start();
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.d(TAG, "onUnbind: ");
        return true;
    }


    @Override
    public void onDestroy()
    {
        this.quit = true;
    }

    @Override
    public void onRebind(Intent intent)
    {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: ");
    }
}