package lf.com.android.blackfishdemo.util;

import android.view.View;
import android.view.ViewTreeObserver;

import lf.com.android.blackfishdemo.listener.OnViewListener;

public class ViewUtil {

    public static void getViewWidth(final View view, final OnViewListener listener) {
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (listener != null) {
                    listener.onView(view.getWidth(), view.getHeight());
                }
            }
        });
    }
}
