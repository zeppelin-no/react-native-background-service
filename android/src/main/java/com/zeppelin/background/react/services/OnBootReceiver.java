package com.zeppelin.background.react.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * This will ensure that our service will setup every time the phone is booted, meaning that
 * our alarm trigger will run even after phone restart and our app hasn't been specifically run yet.
 * There is a caveeat - This will only boot on, y'know - boot. That excludes the period of time
 * between app is installed/updated and next reboot, hence, we also run {@link #triggerOnce} from
 * Variables in onCreate, to make sure that this thing is always running.
 */
public class OnBootReceiver extends BroadcastReceiver {
  private static final long INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES; // Every thirty minutes, in millis
//    private static final long INTERVAL = 1000 * 5; // Every thirty minutes, in millis
  private static boolean hasLaunched = false;
  public static final String TAG = "RCTBGService";

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.e(TAG, "#onReceive triggered - boot");
    Log.e(TAG, "#onReceive not starting on boot atm");

    // triggerOnce(context);
  }

  public static void triggerOnce(Context context) {
    Log.e(TAG, "");
    Log.e(TAG, "======== triggerOnce ========");
    Log.e(TAG, "");

    if (!hasLaunched) {
      Log.e(TAG, "triggerOnce - has not launched");
      // Log.e(TAG, "lol: " + PendingIntent.FLAG_CANCEL_CURRENT);
      // Log.e(TAG, "lol: " + PendingIntent.FLAG_IMMUTABLE);
      // Log.e(TAG, "lol: " + PendingIntent.FLAG_NO_CREATE);
      // Log.e(TAG, "lol: " + PendingIntent.FLAG_ONE_SHOT);
      // Log.e(TAG, "lol: " + PendingIntent.FLAG_UPDATE_CURRENT);

      AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

      Intent i = new Intent(context, OnAlarmReceiver.class);

      // boolean alarmUp = (PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_NO_CREATE) != null);
      //
      // if (alarmUp) {
      //   Log.d("myTag", "Alarm is already active");
      // }

      PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);

      Log.e(TAG, "triggerOnce - something osmething");

      alarmManager.setInexactRepeating(
        AlarmManager.ELAPSED_REALTIME_WAKEUP,
        SystemClock.elapsedRealtime(),
        INTERVAL,
        pendingIntent
      );
    }

    hasLaunched = true;
  }
}
