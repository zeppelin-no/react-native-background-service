package com.zeppelin.background.react.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.app.ActivityManager;

import java.util.List;

import com.facebook.react.HeadlessJsTaskService;

public class OnAlarmReceiver extends BroadcastReceiver {
  public static final String TAG = "RCTBGService";

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.i(TAG, "======= OnAlarmReceiver triggered =======");
    Log.i(TAG, "OnAlarmReceiver action: " + intent.getAction());
    if (!isAppOnForeground((context))) {
      Log.i(TAG, "OnAlarmReceiver is in background!");
      // boolean hasInternet = checkInternet(context);
      Intent serviceIntent = new Intent(context, BackgroundTaskService.class);
      serviceIntent.putExtra("hasInternet", "java - testur");

      // Start our service
      context.startService(serviceIntent);

      // Lock us down, we'll need CPU for our duration
      HeadlessJsTaskService.acquireWakeLockNow(context);
    }
  }


  private boolean isAppOnForeground(Context context) {
    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
    if (appProcesses == null) {
      Log.i(TAG, "should trigger headlesstask");
      return false;
    }
    final String packageName = context.getPackageName();
    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
      Log.i(TAG, "appProcess.importance: " + appProcess.importance);

      if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
        Log.i(TAG, "should NOT trigger headlesstask");
        return true;
      }
    }
    Log.i(TAG, "should trigger headlesstask");
    return false;
  }
}
