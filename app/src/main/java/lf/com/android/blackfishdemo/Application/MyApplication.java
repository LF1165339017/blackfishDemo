package lf.com.android.blackfishdemo.Application;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        MobSDK.init(this);
    }

    public static Context getmContext() {
        return mContext;
    }
}
