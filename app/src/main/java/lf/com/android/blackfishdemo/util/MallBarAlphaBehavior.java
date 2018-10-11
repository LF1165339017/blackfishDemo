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
        endOffset = child.getHeight();//返回子视图的高度
        offset += dyConsumed;//叠加在Y轴上消耗的距离
        RelativeLayout relativeLayout = (RelativeLayout) child.getChildAt(0);
        ImageView imageMenu = (ImageView) relativeLayout.getChildAt(0);//获取指定视图
        ImageView imageMsg = (ImageView) relativeLayout.getChildAt(2);//获取指定视图

        if (offset <= startOffset) {//如果Y轴上消耗的距离<=0的话，就将子view透明度设置为0
            child.getBackground().setAlpha(0);
        } else if (offset > startOffset && offset < endOffset) {//如果滑动的距离大于0，并且滑动范围在子view视图里面
            imageMsg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_home_header_msg_white));
            imageMenu.setImageDrawable(mContext.getResources().getDrawable(R.drawable.stages_icon_classify_white));
            float precent = (float) (offset - startOffset) / endOffset;
            int alpha = Math.round(precent * 255);
            child.getBackground().setAlpha(alpha);

            imageMenu.getDrawable().setAlpha(1 - alpha);
            imageMsg.getDrawable().setAlpha(1 - alpha);

        } else if (offset >= endOffset) {//滑动距离超过了子veiw的范围
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
