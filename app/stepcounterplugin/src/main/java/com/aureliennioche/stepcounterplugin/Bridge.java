package com.aureliennioche.stepcounterplugin;

// import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
// import android.content.pm.PackageManager;
// import android.os.Build;
import android.util.Log;
// import android.Manifest;
// import android.app.Activity;
// import android.app.ActivityManager;
// import android.content.Context;
// import android.content.Intent;
// import android.content.pm.PackageManager;
// import android.os.Bundle;

// import androidx.activity.result.ActivityResultLauncher;
// import androidx.activity.result.contract.ActivityResultContracts;
// import androidx.annotation.NonNull;
// import androidx.annotation.NonNull;
// import androidx.annotation.RequiresApi;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.core.app.ActivityCompat;
// import androidx.core.content.ContextCompat;
// import androidx.core.app.ActivityCompat;
// import androidx.core.content.ContextCompat;

// @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class Bridge {

    static String tag = "Bridge";

    // Important! Method needs to be static to be able to be called by Unity
    public static void launchService(Activity mainActivity) {
        Context context = mainActivity.getApplicationContext();
        if (isServiceAlive(context, StepService.class)) {
            Log.d(tag, "Service is already running");
        } else {
            Log.d(tag, "Creating the service");
            Intent intent = new Intent(context, StepService.class);
            context.startForegroundService(intent);

            Log.d(tag, "The service is supposed to be running now");
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean isServiceAlive(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}