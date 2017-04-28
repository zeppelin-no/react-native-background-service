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
import android.os.AsyncTask;
import android.app.job.JobParameters;
import android.os.Messenger;
import android.os.Message;
import android.os.Handler;
import android.os.Looper;
import android.app.job.JobInfo;
import android.content.ComponentName;

// FitKit:
import com.zeppelin.fit.react.services.FitBodyMetricsService;
import com.zeppelin.fit.react.services.FitActivitiesService;
import com.zeppelin.fit.react.services.FitStepService;
import com.zeppelin.fit.react.observers.FitStepObserver;

// rx fit:
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.patloew.rxfit.RxFit;
import rx.Completable;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Action1;

public class BackgroundReactModule extends ReactContextBaseJavaModule {
    private Context context;

    private final static String REACT_MODULE_NAME = "BackgroundService";
    public static final String TAG = "RCTBGService";

    public static final int MSG_UNCOLOUR_START = 0;
    public static final int MSG_UNCOLOUR_STOP = 1;
    public static final int MSG_SERVICE_OBJ = 2;

    private RxFit rxFit;

    public FitReactModule(ReactApplicationContext reactContext) {
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
    public void initFitKit(ReadableMap options, final Promise promise) {
        Log.i(TAG, "");
        Log.i(TAG, "");
        Log.i(TAG, "=========================================");
        Log.i(TAG, "");
        Log.i(TAG, "");
        Log.i(TAG, "initBackgroundService");
    }
}
