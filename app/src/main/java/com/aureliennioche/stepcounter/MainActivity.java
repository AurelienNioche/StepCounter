package com.aureliennioche.stepcounter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.aureliennioche.stepcounterplugin.Bridge;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class MainActivity extends AppCompatActivity {

    String tag = this.getClass().getSimpleName();

    private static final int POST_NOTIFICATIONS_REQUEST_CODE = 0; // Arbitrary
    private static final int ACTIVITY_RECOGNITION_REQUEST_CODE = 1; // Arbitrary

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionChecks();
    }

    public void permissionChecks() {
        // Begin by requesting the notification permission
        requestNotificationPermission();
    }

    public void allPermissionsHaveBeenGranted() {
         Log.d(tag, "Start the bridge");
         Bridge bridge = new Bridge(this);
         bridge.launchService();

         Log.d(tag, "Reading database");
         int numberOfStep = bridge.numberOfStepSinceLastBoot();
         Log.d(tag, "Last number is" + numberOfStep);
    }

    // ------------------------------------------------------------------------------------------ //

    void requestNotificationPermission() {
        Log.d(tag, "Checking the permission for notifications");
        Context context = getApplicationContext();
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            notificationPermissionHasBeenGranted();
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.POST_NOTIFICATIONS },
                    POST_NOTIFICATIONS_REQUEST_CODE);
        }
    }


    void notificationPermissionHasBeenGranted() {
        Log.d(tag,"Permission for notifications has been granted!\n" +
                "Requesting activity permission now");
        requestActivityPermission();
    }
    // ----------------------------------------------------------- //


    void requestActivityPermission() {
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(),
                Manifest.permission.ACTIVITY_RECOGNITION) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            activityPermissionHasBeenGranted();
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACTIVITY_RECOGNITION },
                    ACTIVITY_RECOGNITION_REQUEST_CODE);
        }
    }

    void activityPermissionHasBeenGranted() {
        allPermissionsHaveBeenGranted();
    }

    // ----------------------------------------------------------- //

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d(tag, "Result arrived");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case POST_NOTIFICATIONS_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    notificationPermissionHasBeenGranted();
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Log.w(tag, "User is an ass: he refused the notifications");
                }
                return;
            case ACTIVITY_RECOGNITION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    activityPermissionHasBeenGranted();
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    ActivityCompat.requestPermissions(this,
                            new String[] { Manifest.permission.ACTIVITY_RECOGNITION },
                            ACTIVITY_RECOGNITION_REQUEST_CODE);
                }
        }
    }
}