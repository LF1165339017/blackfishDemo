package lf.com.android.blackfishdemo.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import java.util.jar.Attributes;

public class ToolbarAlphaBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    private int offset = 0;
    private int startOffset = 0;
    private int endOffset = 0;
    private Context mContext;


    public ToolbarAlphaBehavior(Context context, Attributes attrs) {
        super(context, (AttributeSet) attrs);
        this.mContext = context;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }
}
