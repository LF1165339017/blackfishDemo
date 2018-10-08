package lf.com.android.blackfishdemo.util;

import android.support.v7.widget.RecyclerView;

/**
 * 透明度改变监听器
 * Created by zhangdong on 2018/1/30.
 */

public interface AlphaRecyclerListener {
    void onScrollAlpha(float alpha);

    void onScroll(RecyclerView recyclerView, int dx, int dy);
}
