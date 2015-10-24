package com.ayoprez.deilyquote;

import android.support.multidex.MultiDexApplication;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by AyoPrez on 11/07/15.
 */
public class MainApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/RockoFLF.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
//        LeakCanary.install(this);
    }

}
