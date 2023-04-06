using TMPro;
using UnityEngine;
using UnityEngine.Android;

public class Controller : MonoBehaviour
{
    [SerializeField] private TextMeshProUGUI stepsText;
    [SerializeField] private TextMeshProUGUI totalStepsText;
    [SerializeField] private TextMeshProUGUI syncedDateText;

    private AndroidJavaClass unityClass;
    private AndroidJavaObject unityActivity;
    private AndroidJavaClass customClass;
    private const string PlayerPrefsTotalSteps = "totalSteps";
    private const string PackageName = "com.kdg.toast.plugin.Bridge";
    private const string UnityDefaultJavaClassName = "com.unity3d.player.UnityPlayer";
    private const string CustomClassReceiveActivityInstanceMethod = "ReceiveActivityInstance";
    private const string CustomClassStartServiceMethod = "StartService";
    private const string CustomClassStopServiceMethod = "StopService";
    private const string CustomClassGetCurrentStepsMethod = "GetCurrentSteps";
    private const string CustomClassSyncDataMethod = "SyncData";


    private void Awake()
    {
    }

    //private void Start()
    //{
    //    AndroidJNIHelper.debug = true;
    //    using (AndroidJavaClass jc = new AndroidJavaClass("com.android."))
    //    {
    //        jc.CallStatic("UnitySendMessage", "Controller", "JavaMessage", "NewMessage");
    //    }
    //}
    private void Start()
    {
        //string msg = "Battery Level: " + (GetBatteryLevel() * 100) + "%";
        // ShowToast("tamere");
        CheckPermissionsAndLaunchSensorWorker();
    }
    //method that calls our native plugin.
    public void LaunchSensorWorker()
    {
        if (Application.platform == RuntimePlatform.Android)
        {
            // Retrieve the UnityPlayer class.
            AndroidJavaClass unityPlayerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
            // Retrieve the UnityPlayerActivity object 
            AndroidJavaObject unityActivity = unityPlayerClass.GetStatic<AndroidJavaObject>("currentActivity");
            // Retrieve the "Bridge" from our native plugin.
            // ! Notice we define the complete package name.              
            AndroidJavaObject bridge = new AndroidJavaObject("com.aureliennioche.bkgstepcounterplugin.Bridge");
            // Setup the parameters we want to send to our native plugin.              
            object[] parameters = new object[2];
            parameters[0] = unityActivity;
            parameters[1] = "ta mere";
            // Call PrintString in bridge, with our parameters.
            bridge.Call("LaunchSensorWorker", parameters);
        }
    }

    public void CheckPermissionsAndLaunchSensorWorker()
    {
        if (Permission.HasUserAuthorizedPermission("android.permission.ACTIVITY_RECOGNITION"))
        {
            Debug.Log("User already gave his permission, so kind of him");
            LaunchSensorWorker();
        } else
        {
            var callbacks = new PermissionCallbacks();
            callbacks.PermissionDenied += PermissionCallbacks_PermissionDenied;
            callbacks.PermissionGranted += PermissionCallbacks_PermissionGranted;
            callbacks.PermissionDeniedAndDontAskAgain += PermissionCallbacks_PermissionDeniedAndDontAskAgain;
            Permission.RequestUserPermission("android.permission.ACTIVITY_RECOGNITION", callbacks);
        }
    }

    internal void PermissionCallbacks_PermissionDeniedAndDontAskAgain(string permissionName)
    {
        Debug.Log($"{permissionName} PermissionDeniedAndDontAskAgain");
    }

    internal void PermissionCallbacks_PermissionGranted(string permissionName)
    {
        Debug.Log($"{permissionName} PermissionCallbacks_PermissionGranted");
        LaunchSensorWorker();
    }

    internal void PermissionCallbacks_PermissionDenied(string permissionName)
    {
        Debug.Log($"{permissionName} PermissionCallbacks_PermissionDenied");
    }

    //public float GetBatteryLevel()
    //{
    //    if (Application.platform == RuntimePlatform.Android)
    //    {
    //        AndroidJavaClass unityPlayerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
    //        AndroidJavaObject unityActivity = unityPlayerClass.GetStatic<AndroidJavaObject>("currentActivity");
    //        AndroidJavaObject alert = new AndroidJavaObject("com.codemaker.mylibrary.BatteryLevelIndicator");
    //        object[] parameters = new object[1];
    //        parameters[0] = unityActivity;
    //        return alert.Call<float>("GetBatteryPct", parameters);
    //    }
    //    return -1f;
    //}

    //private void SendActivityReference(string packageName)
    //{
    //    //unityClass = new AndroidJavaClass(UnityDefaultJavaClassName);
    //    // unityActivity = unityClass.GetStatic<AndroidJavaObject>("currentActivity");
    //    customClass = new AndroidJavaClass(packageName);
    //    customClass.CallStatic(CustomClassReceiveActivityInstanceMethod, unityActivity);
    //}

    //public void StartService()
    //{
    //    customClass.CallStatic(CustomClassStartServiceMethod);
    //    GetCurrentSteps();
    //}

    //public void StopService()
    //{
    //    customClass.CallStatic(CustomClassStopServiceMethod);
    //}

    //public void GetCurrentSteps()
    //{
    //    int? stepsCount = customClass.CallStatic<int>(CustomClassGetCurrentStepsMethod);
    //    stepsText.text = stepsCount.ToString();
    //}

    //public void SyncData()
    //{
    //    var data = customClass.CallStatic<string>(CustomClassSyncDataMethod);

    //    var parsedData = data.Split('#');
    //    var dateOfSync = parsedData[0] + " - " + parsedData[1];
    //    syncedDateText.text = dateOfSync;
    //    var receivedSteps = int.Parse(parsedData[2]);
    //    var prefsSteps = PlayerPrefs.GetInt(PlayerPrefsTotalSteps, 0);
    //    var prefsStepsToSave = prefsSteps + receivedSteps;
    //    PlayerPrefs.SetInt(PlayerPrefsTotalSteps, prefsStepsToSave);
    //    totalStepsText.text = prefsStepsToSave.ToString();

    //    GetCurrentSteps();
    //}
}