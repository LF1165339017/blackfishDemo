package lf.com.android.blackfishdemo.util;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

public class AdjustViewUtil {
    private static int leftType = 0;
    private static int topType = 1;
    private static int rightType = 2;
    private static int bottomType = 3;

    public void adjustTextViewPic(TextView textView, int type, int marginLeft, int marginTop, int picWidth, int picHeight) {
        Drawable[] drawables = textView.getCompoundDrawables();
        if (type == leftType) {
            Drawable drawable = drawables[0];
            drawable.setBounds(marginLeft, marginTop, picWidth, picHeight);
            textView.setCompoundDrawables(drawable, drawables[1], drawables[2], drawables[3]);
        } else if (type == topType) {
            Drawable drawable = drawables[1];
            drawable.setBounds(marginLeft, marginTop, picWidth, picHeight);
            textView.setCompoundDrawables(drawables[0], drawable, drawables[2], drawables[3]);
        } else if (type == rightType) {
            Drawable drawable = drawables[2];
            drawable.setBounds(marginLeft, marginTop, picWidth, picHeight);
            textView.setCompoundDrawables(drawables[0], drawables[1], drawable, drawables[3]);
        } else if (type == bottomType) {
            Drawable drawable = drawables[3];
            drawable.setBounds(marginLeft, marginTop, picWidth, picHeight);
            textView.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawable);
        } else {
            return;
        }
    }

}
