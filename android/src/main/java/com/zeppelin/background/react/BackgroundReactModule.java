package com.zeppelin.background.react;

import android.content.Context;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Promise;

import android.util.Log;

// date related:
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.sql.Timestamp;
import java.util.TimeZone;

// background service:
import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.SystemClock;

import com.zeppelin.background.react.service.OnAlarmReceiver;

public class BackgroundReactModule extends ReactContextBaseJavaModule {
  private Context context;

  private final static String REACT_MODULE_NAME = "BackgroundService";
  private final static int PENDING_INTENT_ID = 987;
  public static final String TAG = "RCTBGService";
  private static final long INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

  private static boolean hasLaunched = false;

  public BackgroundReactModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.context = reactContext;
  }

  /**
   * @return the name of this module. This will be the name used to {@code require()} this module
   * from javascript.
   */
  @Override
  public String getName() {
    return REACT_MODULE_NAME;
  }

  @ReactMethod
  public void register(ReadableMap options, final Promise promise) {
    Log.i(TAG, "");
    Log.i(TAG, "");
    Log.i(TAG, "=========================================");
    Log.i(TAG, "");
    Log.i(TAG, "");
    Log.i(TAG, "register");

    Log.e(TAG, "");
    Log.e(TAG, "======== triggerOnce ========");
    Log.e(TAG, "");

    if (!hasLaunched) {
      Log.e(TAG, "triggerOnce - has not launched");


      Intent i = new Intent(this.context, OnAlarmReceiver.class);

      PendingIntent pi = PendingIntent.getBroadcast(this.context, PENDING_INTENT_ID, i, PendingIntent.FLAG_NO_CREATE);

      if (pi != null) {
        Log.d(TAG, "Alarm is already active");
        pi.cancel();
      }

      // PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, i, 0);

      Log.e(TAG, "triggerOnce - something osmething");

      AlarmManager am = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);

      am.setInexactRepeating(
        AlarmManager.ELAPSED_REALTIME_WAKEUP,
        SystemClock.elapsedRealtime(),
        INTERVAL,
        pi
      );
    }

    hasLaunched = true;

    promise.resolve(true);
  }

  @ReactMethod
  public void cancel(ReadableMap options, final Promise promise) {
    Log.i(TAG, "");
    Log.i(TAG, "");
    Log.i(TAG, "=========================================");
    Log.i(TAG, "");
    Log.i(TAG, "");
    Log.i(TAG, "cancel");

    Intent intent = new Intent(this.context, OnAlarmReceiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, intent, 0);
    AlarmManager alarmManager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);

    alarmManager.cancel(pendingIntent);

    promise.resolve(true);
  }
}
