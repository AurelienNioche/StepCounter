package com.aureliennioche.stepcounterplugin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class StepService extends Service implements SensorEventListener {
    private static final int ONGOING_NOTIFICATION_ID = 1234;
    private static final String CHANNEL_ID = "tamere";
    SensorManager sensorManager;
    String tag = this.getClass().getSimpleName();

    @Override
    public void onCreate() {}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(tag, "Service starting");
        // Toast.makeText(this, "Service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        // Message msg = serviceHandler.obtainMessage();
        // msg.arg1 = startId;
        // serviceHandler.sendMessage(msg);

        createNotificationChannel();

        // If the notification supports a direct reply action, use
        // PendingIntent.FLAG_MUTABLE instead.
        Intent notificationIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            notificationIntent = new Intent(this, Bridge.class);
        }
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE);

        Notification notification =
                new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle(getText(R.string.notification_title))
                        .setContentText(getText(R.string.notification_message))
                        .setSmallIcon(R.drawable.ic_foot)
                        .setContentIntent(pendingIntent)
                         // .setTicker(getText(R.string.ticker_text))
                        .build();

        initSensorManager();

        // Notification ID cannot be 0.
        startForeground(ONGOING_NOTIFICATION_ID, notification);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(tag, "Service destroyed");
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();

        sensorManager.unregisterListener(this);
    }

    private void createNotificationChannel() {


        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system. You can't change the importance
        // or other notification behaviors after this.
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d(tag, "onSensorChanged: "+sensorEvent.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    public void initSensorManager(){
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor!=null){
            sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
        } else {
            Log.e(tag, "Sensor not found");
        }
        Log.d(tag, "Sensor manager initialized");
    }
}