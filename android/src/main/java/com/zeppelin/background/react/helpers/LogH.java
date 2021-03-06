package com.zeppelin.background.react.helpers;

import android.util.Log;

public class LogH {

  private static final String TAG = "RCTBGService";

  public static void breakerTop() {
    Log.i(TAG, "╔═════════════════════════════════════════════");
  }

  public static void breaker() {
    Log.i(TAG, "╠═════════════════════════════════════════════");
  }

  public static void breakerSmall() {
    Log.i(TAG, "╟─────────────────────────────────────────────");
  }

  public static void breakerBottom() {
    Log.i(TAG, "╚═════════════════════════════════════════════");
  }

  public static void empty() {
    Log.i(TAG, "");
  }

  public static void i(String message) {
    Log.i(TAG, "║ " + message);
  }

  public static void iList(String message) {
    Log.i(TAG, "╟─ " + message);
  }

  public static void d(String message) {
    Log.d(TAG, "║ " + message);
  }

  public static void e(String message) {
    Log.e(TAG, "║ " + message);
  }
}
