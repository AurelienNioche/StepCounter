package com.aureliennioche.bkgstepcounterplugin;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

/**
 * Created by gojuukaze on 16/8/17.
 * Email: i@ikaze.uu.me
 */
public class StepService extends Service {

    String tag = this.getClass().getSimpleName();
    // String CHANNEL_ID = "tamere";
    // private StepThread thread;
    // private PowerManager.WakeLock mWakeLock;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(tag, "service create()");
        // thread = new StepThread(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(tag, "service start()");
        return START_STICKY;
    }
//        Bridge app = (Bridge) getApplication();
//        app.setServiceRun(true);
//
////        if (intent.getBooleanExtra("isActivity", false))
////            thread.setActivity(true);
//        String s = intent.getStringExtra("restart");
//        if (s != null) {
//            Log.d("restart", s);
//        }
//        if (thread.getState() == Thread.State.NEW)
//            thread.start();
//        SharedPreferences sharedPreferences = getSharedPreferences("conf", MODE_PRIVATE);
//        boolean foreground_model = sharedPreferences.getBoolean("foreground_model", false);
//
//        if (foreground_model) {
//
//            myStartForeground();
//            mWakeLock(this);
//        } else {
//            stopForeground(STOP_FOREGROUND_DETACH);
//            if (mWakeLock != null) {
//                if (mWakeLock.isHeld())
//                    mWakeLock.release();
//                mWakeLock = null;
//            }
//        }
//
//
//        return START_STICKY;
//    }
//
//    public void myStartForeground() {
//        Log.i(tag, "Try to show a notification");
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this, CHANNEL_ID)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("tamere")
//                        .setContentText("lapute")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        Intent notificationIntent = new Intent(this, Bridge.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(Bridge.class);
//        stackBuilder.addNextIntent(notificationIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(resultPendingIntent);
//
//
//        startForeground(1, mBuilder.build());

    @Override
    public void onDestroy() {
        Log.d("service", "service stop()");
//        if (mWakeLock != null) {
//            if (mWakeLock.isHeld())
//                mWakeLock.release();
//            mWakeLock = null;
//        }
//
//        stopForeground(STOP_FOREGROUND_DETACH);
//        thread.mystop();
//        Bridge app = (Bridge) getApplication();
//
//        app.setServiceRun(false);
//        boolean temp = getSharedPreferences("conf", MODE_PRIVATE).getBoolean("switch_on", false);
//        if (temp) {
//            Log.d("restart", "auto restart");
//            Intent intent = new Intent("cn.ikaze.pedometer.start");
//            sendBroadcast(intent);
//        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    synchronized private void mWakeLock(Context context) {
//        if (mWakeLock != null) {
//            if (mWakeLock.isHeld())
//                mWakeLock.release();
//            mWakeLock = null;
//        }
//
//        PowerManager mgr = (PowerManager) context
//                .getSystemService(Context.POWER_SERVICE);
//        mWakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
//                StepService.class.getName());
//        mWakeLock.setReferenceCounted(true);
//        mWakeLock.acquire(10*60*1000L /*10 minutes*/);

//            Calendar c = Calendar.getInstance();
//            c.setTimeInMillis(System.currentTimeMillis());
//            int hour = c.get(Calendar.HOUR_OF_DAY);
//            if (hour >= 23 || hour <= 6) {
//                mWakeLock.acquire(5000);
//            } else {
//                mWakeLock.acquire(300000);
//            }
//    }


}