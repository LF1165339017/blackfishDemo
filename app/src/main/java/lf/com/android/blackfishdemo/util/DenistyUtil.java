package lf.com.android.blackfishdemo.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;

public class DenistyUtil {

    //dp转int
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dpValue + 0.5f);
    }

    //获取屏幕的宽
    public static int getScreenWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        return p.x;
    }

    //dp转px
    public static int dip2px(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }


    public static int sp2x(float sp) {
        return (int) (Resources.getSystem().getDisplayMetrics().scaledDensity + 0.5f);
    }

}
