package lf.com.android.blackfishdemo.util;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

public class GetScreen {

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int Width = display.getWidth();
        return Width;
    }

    public static int getgetScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int Height = display.getHeight();
        return Height;
    }
}
