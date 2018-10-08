package lf.com.android.blackfishdemo.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import lf.com.android.blackfishdemo.Application.MyApplication;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * recycler view 滚动监听
 * 首页标题栏透明度处理
 * Created by zhangdong on 2018/1/30.
 */
public class RecyclerAlphaListener extends RecyclerView.OnScrollListener {
    private AlphaRecyclerListener mAlphaListener;
    private int mAlpha;
    private int mHeight;
    private int mPaddingTopHieght;
    private int mAbsLocY;

    public RecyclerAlphaListener(AlphaRecyclerListener listener) {
        mAlphaListener = listener;
        mPaddingTopHieght = DenistyUtil.dip2px(MyApplication.getmContext(), 75); //吸顶标题栏的高度+margin (此处数值和layout里保持一致)
        mHeight = DenistyUtil.dip2px(MyApplication.getmContext(), 150); //头部View高度 (此处数值和layout里保持一致)
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (newState == SCROLL_STATE_IDLE) {
            if (mAbsLocY <= 0) {
                return;
            } else if (mAbsLocY < mHeight / 3) {
                recyclerView.smoothScrollBy(0, mAbsLocY - mHeight);
            } else if (mAbsLocY < mHeight) {
                recyclerView.smoothScrollBy(0, mHeight - mAbsLocY - mPaddingTopHieght);
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof LinearLayoutManager) {

            int position = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            View firstChildView = recyclerView.getChildAt(0);
            mAbsLocY = -firstChildView.getTop();

            float ratio = 1;
            if (mAbsLocY >= mHeight - mPaddingTopHieght || position > 1) {
                ratio = 1;
            } else {
                ratio = (float) mAbsLocY / (mHeight - mPaddingTopHieght);
            }

            Log.i("xixi", "mAbsLocY : " + mAbsLocY + ", mHeight : " + mHeight + ", ratio : " + ratio + ", mAlpha : " + mAlpha);

            if (mAlphaListener != null) {
                mAlphaListener.onScrollAlpha(ratio);
                mAlphaListener.onScroll(recyclerView, dx, dy);
            }
        }
    }
}
