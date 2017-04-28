package com.zeppelin.background.react.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

public class BackgroundTaskService extends HeadlessJsTaskService {
  public static final String TAG = "RCTBGService";

  @Override
  protected @Nullable HeadlessJsTaskConfig getTaskConfig(Intent intent) {
    Bundle extras = intent.getExtras();

    Log.e(TAG, "HeadlessJsTaskConfig");

    if (extras != null) {
      Log.e(TAG, "HeadlessJsTaskConfig - extraz, triggering task!?!?");

      return new HeadlessJsTaskConfig(
        "BackgroundTask",
        Arguments.fromBundle(extras),
        5000
      );
    }
    return null;
  }
}
