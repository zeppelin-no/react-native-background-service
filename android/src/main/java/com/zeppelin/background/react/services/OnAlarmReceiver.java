package com.zeppelin.background.react.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.app.ActivityManager;

import java.util.List;

import com.facebook.react.HeadlessJsTaskService;

import com.zeppelin.background.react.helpers.LogH;

public class OnAlarmReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    LogH.empty();
    LogH.empty();
    LogH.breakerTop();
    LogH.i("OnAlarmReceiver triggered");
    LogH.i("OnAlarmReceiver action: " + intent.getAction());
    if (!isAppOnForeground((context))) {
      LogH.i("OnAlarmReceiver is in background!");
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
      LogH.i("should trigger headlesstask");
      return false;
    }
    final String packageName = context.getPackageName();
    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
      LogH.i("appProcess.importance: " + appProcess.importance);

      if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
        LogH.i("should NOT trigger headlesstask");
        return true;
      }
    }
    LogH.i("should trigger headlesstask");
    return false;
  }
}
