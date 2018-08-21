package lf.com.android.blackfishdemo.util;

import android.util.Log;

public class LogUtil {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;


    public static void loglevel(String TAG, String msg, int level) {
        if (level < NOTHING) {
            switch (level) {
                case VERBOSE:
                    Log.v(TAG, msg);
                    break;
                case DEBUG:
                    Log.d(TAG, msg);
                    break;
                case INFO:
                    Log.i(TAG, msg);
                    break;
                case WARN:
                    Log.w(TAG, msg);
                    break;
                case ERROR:
                    Log.e(TAG, msg);
                    break;
                default:
                    break;
            }
        } else {
            return;
        }
    }


}
