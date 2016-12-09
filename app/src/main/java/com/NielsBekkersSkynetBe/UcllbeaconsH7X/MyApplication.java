package com.NielsBekkersSkynetBe.UcllbeaconsH7X;

import android.app.Application;

import com.estimote.sdk.EstimoteSDK;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        EstimoteSDK.initialize(getApplicationContext(), "ucllbeacons-h7x", "b95b64e8338926fbe67a9050bd8bbec5");

        // uncomment to enable debug-level logging
        // it's usually only a good idea when troubleshooting issues with the Estimote SDK
//        EstimoteSDK.enableDebugLogging(true);
    }
}
