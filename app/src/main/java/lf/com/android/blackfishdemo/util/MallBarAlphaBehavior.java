package lf.com.android.blackfishdemo.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import lf.com.android.blackfishdemo.R;

public class MallBarAlphaBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    private int offset = 0;
    private int startOffset = 0;
    private int endOffset = 0;
    private Context mContext;

    public MallBarAlphaBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        startOffset = 0;
        endOffset = child.getHeight();
        offset += dyConsumed;

        RelativeLayout relativeLayout = (RelativeLayout) child.getChildAt(0);
        ImageView imageMenu = (ImageView) relativeLayout.getChildAt(0);
        ImageView imageMsg = (ImageView) relativeLayout.getChildAt(2);

        if (offset <= startOffset) {
            child.getBackground().setAlpha(0);
        } else if (offset > startOffset && offset < endOffset) {
            imageMsg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_home_header_msg_white));
            imageMenu.setImageDrawable(mContext.getResources().getDrawable(R.drawable.stages_icon_classify_white));
            float precent = (float) (offset - startOffset) / endOffset;
            int alpha = Math.round(precent * 255);
            child.getBackground().setAlpha(alpha);

            imageMenu.getDrawable().setAlpha(1 - alpha);
            imageMsg.getDrawable().setAlpha(1 - alpha);

        } else if (offset >= endOffset) {
            child.getBackground().setAlpha(255);
            imageMsg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_home_header_msg_black));
            imageMenu.setImageDrawable(mContext.getResources().getDrawable(R.drawable.stages_icon_classify_gray));
            imageMenu.setVisibility(View.VISIBLE);
            imageMenu.setAlpha(1f);
            imageMsg.setVisibility(View.VISIBLE);
            imageMsg.setAlpha(1f);
        }

    }
}
