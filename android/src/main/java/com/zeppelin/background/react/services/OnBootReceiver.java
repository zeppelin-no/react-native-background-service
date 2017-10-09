package com.zeppelin.background.react.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.zeppelin.background.react.helpers.LogH;

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

  @Override
  public void onReceive(Context context, Intent intent) {
    LogH.breaker();
    LogH.i("#onReceive triggered - boot");
    LogH.i("#onReceive not starting on boot atm");

    // triggerOnce(context);
  }

  public static void triggerOnce(Context context) {
    LogH.breaker();
    LogH.i("triggerOnce");
    LogH.breaker();

    if (!hasLaunched) {
      LogH.i("triggerOnce - has not launched");

      AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

      Intent i = new Intent(context, OnAlarmReceiver.class);

      // boolean alarmUp = (PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_NO_CREATE) != null);
      //
      // if (alarmUp) {
      //   LogH.d("myTag", "Alarm is already active");
      // }

      PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);

      LogH.i("triggerOnce - something osmething");

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
