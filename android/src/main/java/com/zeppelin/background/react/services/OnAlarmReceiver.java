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
    Log.e(TAG, "OnAlarmReceiver triggered");
    Log.e(TAG, "OnAlarmReceiver triggered" + intent.getAction());
    if (!isAppOnForeground((context))) {
      Log.e(TAG, "OnAlarmReceiver is in background!");
      // boolean hasInternet = checkInternet(context);
      Intent serviceIntent = new Intent(context, BackgroundTaskService.class);
      serviceIntent.putExtra("hasInternet", "java - testur");
      context.startService(serviceIntent);

      HeadlessJsTaskService.acquireWakeLockNow(context);

      // Lock us down, we'll need CPU for our duration
      // StepCounterService.acquireStaticLock(context);

      // Start our service
      // context.startService(new Intent(context, StepCounterService.class));
    }
  }


  private boolean isAppOnForeground(Context context) {
    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
    if (appProcesses == null) {
      return false;
    }
    final String packageName = context.getPackageName();
    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
      if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
        return true;
      }
    }
    return false;
  }
}
