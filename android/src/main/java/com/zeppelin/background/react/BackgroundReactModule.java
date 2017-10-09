package com.zeppelin.background.react;

import android.content.Context;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.Promise;

// import android.util.Log;

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
import com.zeppelin.background.react.helpers.LogH;

public class BackgroundReactModule extends ReactContextBaseJavaModule {
  private Context context;

  private final static String REACT_MODULE_NAME = "BackgroundService";
  private final static int PENDING_INTENT_ID = 987;
  private final static String PENDING_INTENT_ACTION = "RCTBGServiceAction";

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
    LogH.empty();
    LogH.empty();
    LogH.breakerTop();
    LogH.i("register");

    if (!hasLaunched) {
      LogH.i("has not local launch");

      if (!this.isPendingIntentWorking()) {
        this.cancelAlaram();

        long INTERVAL = getIntervalFromOptions(options);

        LogH.i("Adding alarm!!!! with interval: " + INTERVAL);

        AlarmManager am = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this.context, OnAlarmReceiver.class);
        i.setAction(PENDING_INTENT_ACTION);

        PendingIntent pi = PendingIntent.getBroadcast(this.context, PENDING_INTENT_ID, i, PendingIntent.FLAG_CANCEL_CURRENT);

        LogH.i("triggerOnce - setting repeating alarm");
        am.setInexactRepeating(
          AlarmManager.ELAPSED_REALTIME_WAKEUP,
          SystemClock.elapsedRealtime(),
          INTERVAL,
          pi
        );
      }
    }

    hasLaunched = true;

    LogH.breakerBottom();

    promise.resolve(true);
  }

  private long getIntervalFromOptions(ReadableMap options) {
    if (options != null && options.hasKey("period") && !options.isNull("period")) {
      String period = options.getString("period");

      switch (period) {
        case "INTERVAL_DAY":
          return AlarmManager.INTERVAL_DAY;
        case "INTERVAL_HALF_DAY":
          return AlarmManager.INTERVAL_HALF_DAY;
        case "INTERVAL_HALF_HOUR":
          return AlarmManager.INTERVAL_HALF_HOUR;
        case "INTERVAL_FIFTEEN_MINUTES":
          return AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        case "MANUAL":
          if (options.hasKey("interval") && !options.isNull("interval")) {
            if (options.getType("interval") == ReadableType.Number) {
              return (long) options.getInt("interval");
            }
          }
        case "INTERVAL_HOUR":
        default:
          return AlarmManager.INTERVAL_HOUR;
      }
    }
    return AlarmManager.INTERVAL_HOUR;
  }

  private boolean isPendingIntentWorking() {
    Intent intent = new Intent(this.context, OnAlarmReceiver.class);
    intent.setAction(PENDING_INTENT_ACTION);
    boolean isWorking = (PendingIntent.getBroadcast(this.context, PENDING_INTENT_ID, intent, PendingIntent.FLAG_NO_CREATE) != null);
    LogH.i("alarm is" + (isWorking ? "" : " NOT") + " running...");
    return isWorking;
  }

  private void cancelAlaram() {
    LogH.i("cancel alarm");
    AlarmManager am = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(this.context, OnAlarmReceiver.class);
    intent.setAction(PENDING_INTENT_ACTION);
    PendingIntent pi = PendingIntent.getBroadcast(this.context, PENDING_INTENT_ID, intent, 0);

    am.cancel(pi);
    pi.cancel();
  }

  @ReactMethod
  public void cancel(ReadableMap options, final Promise promise) {
    LogH.i("");
    LogH.i("");
    LogH.breaker();
    LogH.i("");
    LogH.i("");
    LogH.i("cancel");
    LogH.breaker();

    this.cancelAlaram();

    promise.resolve(true);
  }
}
