package lf.com.android.blackfishdemo.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapUtil {

    /**
     * @param resources 加载文件的路径
     * @param piexlW    真正显示图片的宽
     * @param pieylH    真正显示图片的高
     * @return 返回bitmap
     */
    public static Bitmap Resource(Resources resources, int id, int piexlW, int pieylH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只加载图片宽高
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;//设置位深度,减少内存消耗
        //预加载
        BitmapFactory.decodeResource(resources, id, options);
        int oringinalW = options.outWidth;
        int oringinalH = options.outHeight;

        //真正加载
        options.inJustDecodeBounds = false;
        options.inSampleSize = getSimpleSize(oringinalW, oringinalH, piexlW, pieylH);

        return BitmapFactory.decodeResource(resources, id, options);
    }

    private static int getSimpleSize(int oringinalW, int oringinalH, int piexlW, int pieylH) {
        int SimpleSize = 1;//设置采样率为1，不压缩

        if (oringinalW > oringinalH && oringinalW > piexlW) {
            SimpleSize = oringinalW / piexlW;
        } else if (oringinalW < oringinalH && oringinalH > pieylH) {
            SimpleSize = oringinalH / pieylH;
        }
        if (SimpleSize <= 0) {
            SimpleSize = 1;
        }
        return SimpleSize;
    }
}
