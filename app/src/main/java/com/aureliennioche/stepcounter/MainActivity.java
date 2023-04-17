package com.aureliennioche.stepcounter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Observable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aureliennioche.stepcounterplugin.Bridge;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class MainActivity extends AppCompatActivity {

    String tag = this.getClass().getSimpleName();

    private static final int POST_NOTIFICATIONS_REQUEST_CODE = 0; // Arbitrary
    private static final int ACTIVITY_RECOGNITION_REQUEST_CODE = 1; // Arbitrary

    private NameViewModel model;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionChecks();


        TextView nameTextView = findViewById(R.id.central_text);

        // Get the ViewModel.
        model = new ViewModelProvider(this).get(NameViewModel.class);

        // Create the observer which updates the UI.
        // Update the UI, in this case, a TextView.
        final Observer<String> nameObserver = nameTextView::setText;

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.getCurrentName().observe(this, nameObserver);

        Bridge bridge = new Bridge(this);

        // Create the Handler object (on the main thread by default)
        Handler handler = new Handler();
// Define the code block to be executed
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {

                int nStep = bridge.numberOfStepSinceLastBoot();
                model.getCurrentName().setValue(String.valueOf(nStep));

                // Do something here on the main thread
                Log.d("Handlers", "Called on main thread");
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 3000);
            }
        };
// Start the initial runnable task by posting through the handler
        handler.post(runnableCode);
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