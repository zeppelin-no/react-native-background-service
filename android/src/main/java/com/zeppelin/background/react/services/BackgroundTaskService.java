package com.zeppelin.background.react.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.os.IBinder;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.LifecycleState;

import com.zeppelin.background.react.helpers.LogH;

public class BackgroundTaskService extends HeadlessJsTaskService {
  private static final String TASK_ID = "BackgroundTask";

  @Override
  protected @Nullable HeadlessJsTaskConfig getTaskConfig(Intent intent) {
    Bundle extras = intent.getExtras();

    LogH.i("HeadlessJsTaskConfig");
    LogH.i("intent-action: " + intent.getAction());
    LogH.i("intent-Uri: " + intent.getDataString());
    LogH.i("intent-type: " + intent.getType());

    if (extras != null && shouldTriggerTask()) {
      LogH.i("HeadlessJsTaskConfig - extraz, triggering task!?!?");
      try {
        return new HeadlessJsTaskConfig(
          TASK_ID,
          Arguments.fromBundle(extras),
          20000
        );
      } catch(Exception e) {
          LogH.e("error setting up headless js task");
          e.printStackTrace();
      }
    }

    stopSelf();
    return null;
  }

  private boolean shouldTriggerTask() {
    final ReactInstanceManager reactInstanceManager =
            ((ReactApplication) getApplication())
                    .getReactNativeHost()
                    .getReactInstanceManager();

    ReactContext reactContext = reactInstanceManager.getCurrentReactContext();

    LogH.breakerSmall();
    LogH.i("shouldTriggerTask");
    LogH.i("getApplication: " + (ReactApplication) getApplication());
    LogH.i("getReactNativeHost: " + ((ReactApplication) getApplication()).getReactNativeHost());
    LogH.i("getReactInstanceManager: " + reactInstanceManager);

    LogH.i("reactContext: " + reactContext);
    if (reactContext != null) {
      LogH.i("getLifecycleState: " + reactContext.getLifecycleState());
    }
    LogH.i("RESUMED: " + LifecycleState.RESUMED);
    LogH.breakerSmall();

    return reactContext != null && reactContext.getLifecycleState() != LifecycleState.RESUMED;
  }

  // TODO: we might want to revert to this when we are on RN-version with this:
  // https://github.com/facebook/react-native/pull/15929/commits/8b070c7535e357a6c96746c2e83ff65943645cd5
  // private boolean isAppInForeground() {
  //   final ReactInstanceManager reactInstanceManager =
  //           ((ReactApplication) getApplication())
  //                   .getReactNativeHost()
  //                   .getReactInstanceManager();
  //
  //   ReactContext reactContext = reactInstanceManager.getCurrentReactContext();
  //
  //   LogH.breakerSmall();
  //   LogH.i("isAppInForeground");
  //   LogH.i("getApplication: " + (ReactApplication) getApplication());
  //   LogH.i("getReactNativeHost: " + ((ReactApplication) getApplication()).getReactNativeHost());
  //   LogH.i("getReactInstanceManager: " + reactInstanceManager);
  //
  //   LogH.i("reactContext: " + reactContext);
  //   if (reactContext != null) {
  //     LogH.i("getLifecycleState: " + reactContext.getLifecycleState());
  //   }
  //   LogH.i("RESUMED: " + LifecycleState.RESUMED);
  //   LogH.breakerSmall();
  //
  //   return reactContext != null && reactContext.getLifecycleState() == LifecycleState.RESUMED;
  // }



  // @Override
  // protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
  //   // LogH.d("GETTING TASK CONFIG");
  //   Bundle extras = intent.getExtras();
  //   String jobKey = extras.getString("jobKey");
  //   int timeout = extras.getInt("timeout");
  //   return new HeadlessJsTaskConfig(jobKey, Arguments.fromBundle(extras), timeout);
  // }

  @Override
  public void onHeadlessJsTaskStart(int taskId) {
    super.onHeadlessJsTaskStart(taskId);
    LogH.i("TASK START");
  }

  @Override
  public int onStartCommand(final Intent intent, int flags, int startId) {
    LogH.i("TASK ON START COMMAND");
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onHeadlessJsTaskFinish(int taskId) {
    super.onHeadlessJsTaskFinish(taskId);
    LogH.i("TASK FINISH");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    LogH.i("TASK DESTROY");
    LogH.breakerBottom();
  }

  // @Override
  // public int onStartCommand(final Intent intent, int flags, int startId) {
  //   LogH.e("onStartCommand");

  //   if (null == intent) {
  //     LogH.e("intent was null, flags=" + flags + " bits=" + Integer.toBinaryString (flags));
  //     return START_STICKY;
  //   }
  //   if (mReactContext == null) {
  //       setReactContext(null);
  //   }
  //
  //   // LogH.d("onStartCommand");
  //
  //   if (isAppInForeground()) {
  //     // LogH.d("APP IS IN FOREGROUND");
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
  //         // LogH.d("Starting task!");
  //         startTask(taskConfig);
  //       } else {
  //         // LogH.d("Not starting task, still in bg");
  //       }
  //     }
  //   } else {
  //     // LogH.d("NOT STARTING TASK");
  //     return START_REDELIVER_INTENT;
  //   }
  //
  //   if (alwaysRunning) {
  //     if (mReactContext == null) {
  //       // LogH.d("mReactContext == null");
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
  //           // LogH.d("Creating React Context In Background");
  //             reactInstanceManager.createReactContextInBackground();
  //         }
  //       } else {
  //         // LogH.d("mReactContext != null");
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
