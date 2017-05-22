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
  private final static String PENDING_INTENT_ACTION = "RCTBGServiceAction";
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

    if (!hasLaunched) {
      if (!this.isPendingIntentWorking()) {
        Log.i(TAG, "Adding alarm!!!!");

        AlarmManager am = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this.context, OnAlarmReceiver.class);
        i.setAction(PENDING_INTENT_ACTION);

        PendingIntent pi = PendingIntent.getBroadcast(this.context, PENDING_INTENT_ID, i, PendingIntent.FLAG_CANCEL_CURRENT);

        Log.i(TAG, "triggerOnce - setting repeating alarm");
        am.setInexactRepeating(
          AlarmManager.ELAPSED_REALTIME_WAKEUP,
          SystemClock.elapsedRealtime(),
          INTERVAL,
          pi
        );
      }
    }

    hasLaunched = true;

    promise.resolve(true);
  }

  private boolean isPendingIntentWorking() {
    Intent intent = new Intent(this.context, OnAlarmReceiver.class);
    intent.setAction(PENDING_INTENT_ACTION);
    boolean isWorking = (PendingIntent.getBroadcast(this.context, PENDING_INTENT_ID, intent, PendingIntent.FLAG_NO_CREATE) != null);
    Log.i(TAG, "alarm is " + (isWorking ? "" : "not") + " working...");
    return isWorking;
  }

  @ReactMethod
  public void cancel(ReadableMap options, final Promise promise) {
    Log.i(TAG, "");
    Log.i(TAG, "");
    Log.i(TAG, "=========================================");
    Log.i(TAG, "");
    Log.i(TAG, "");
    Log.i(TAG, "cancel");

    AlarmManager am = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(this.context, OnAlarmReceiver.class);
    intent.setAction(PENDING_INTENT_ACTION);
    PendingIntent pi = PendingIntent.getBroadcast(this.context, PENDING_INTENT_ID, intent, 0);

    am.cancel(pi);
    pi.cancel();

    promise.resolve(true);
  }
}
