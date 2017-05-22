package com.zeppelin.background.react.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.os.IBinder;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

public class BackgroundTaskService extends HeadlessJsTaskService {
  public static final String TAG = "RCTBGService";

  @Override
  protected @Nullable HeadlessJsTaskConfig getTaskConfig(Intent intent) {
    Bundle extras = intent.getExtras();

    Log.i(TAG, "HeadlessJsTaskConfig");

    if (extras != null) {
      Log.i(TAG, "HeadlessJsTaskConfig - extraz, triggering task!?!?");
      try {
        return new HeadlessJsTaskConfig(
          "BackgroundTask",
          Arguments.fromBundle(extras),
          5000
        );
      } catch(Exception e) {
          Log.e(TAG, "error setting up headless js task");
          e.printStackTrace();
          return null;
      }
    }
    return null;
  }


  // @Override
  // protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
  //   // Log.d(LOG_TAG, "GETTING TASK CONFIG");
  //   Bundle extras = intent.getExtras();
  //   String jobKey = extras.getString("jobKey");
  //   int timeout = extras.getInt("timeout");
  //   return new HeadlessJsTaskConfig(jobKey, Arguments.fromBundle(extras), timeout);
  // }

  @Override
  public void onHeadlessJsTaskFinish(int taskId) {
    Log.d(TAG, "TASK FINISHED");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.i(TAG, "In destroyed");
  }

  // @Override
  // public int onStartCommand(final Intent intent, int flags, int startId) {
  //   Log.e (TAG, "onStartCommand");

  //   if (null == intent) {
  //     Log.e (LOG_TAG, "intent was null, flags=" + flags + " bits=" + Integer.toBinaryString (flags));
  //     return START_STICKY;
  //   }
  //   if (mReactContext == null) {
  //       setReactContext(null);
  //   }
  //
  //   // Log.d(LOG_TAG, "onStartCommand");
  //
  //   if (isAppInForeground()) {
  //     // Log.d(LOG_TAG, "APP IS IN FOREGROUND");
  //     cancelTimer();
  //     stopSelf();
  //     return Service.START_REDELIVER_INTENT;
  //   }
  //
  //   boolean alwaysRunning = intent.getIntExtra("alwaysRunning", 0) == 1;
  //
  //   HeadlessJsTaskConfig taskConfig = getTaskConfig(intent);
  //   if (taskConfig != null) {
  //     if (mReactContext == null || !alwaysRunning) {
  //       if (!isAppInForeground()) {
  //         // Log.d(LOG_TAG, "Starting task!");
  //         startTask(taskConfig);
  //       } else {
  //         // Log.d(LOG_TAG, "Not starting task, still in bg");
  //       }
  //     }
  //   } else {
  //     // Log.d(LOG_TAG, "NOT STARTING TASK");
  //     return START_REDELIVER_INTENT;
  //   }
  //
  //   if (alwaysRunning) {
  //     if (mReactContext == null) {
  //       // Log.d(LOG_TAG, "mReactContext == null");
  //       reactInstanceManager
  //         .addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
  //           @Override
  //           public void onReactContextInitialized(ReactContext reactContext) {
  //             setReactContext(reactContext);
  //             showNotification(intent);
  //             reactInstanceManager.removeReactInstanceEventListener(this);
  //           }
  //         });
  //         if (!reactInstanceManager.hasStartedCreatingInitialContext()) {
  //           // Log.d(LOG_TAG, "Creating React Context In Background");
  //             reactInstanceManager.createReactContextInBackground();
  //         }
  //       } else {
  //         // Log.d(LOG_TAG, "mReactContext != null");
  //         showNotification(intent);
  //       }
  //       return START_STICKY;
  //   } else {
  //     return START_REDELIVER_INTENT;
  //   }
  //   return START_STICKY;
  // }

  // @Override
  // public @Nullable IBinder onBind(Intent intent) {
  //   return null;
  // }
}
