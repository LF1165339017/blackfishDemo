package lf.com.android.blackfishdemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnRvBannerClickListener;
import lf.com.android.blackfishdemo.listener.OnSwitchRvBannerListener;
import lf.com.android.blackfishdemo.util.LogUtil;

public class RecyclerViewBanner extends FrameLayout {
    private static final int DEFAULT_SELECT_COLOR = 0xffffffff;//选中时的颜色
    private static final int DEFAULT_UNSELECTED_COLOR = 0X50ffffff;//未来选中时的颜色
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;
    private LinearLayout mLinearLayout;
    private OnRvBannerClickListener listener;
    private OnSwitchRvBannerListener onSwitchRvBannerListener;
    private boolean isPlaying;//是否播放
    private int startX, startY, currentIndex;//从X轴开始的位置，从Y轴开始的位置
    private int mSize;//大小
    private int mSpace;//间距
    private int mInterval;//时间间隔
    private int margin;//距离外边距的距离
    private int gravity;//位置
    private boolean isShowIndicator;//是否显示指示器
    private boolean isAutoPlaying = true;//是否自动播放
    private boolean isTouched;//是否触摸
    private List<Object> mData = new ArrayList<>();
    private Drawable mSelectedDrawable;//选中时背景
    private Drawable mUnSelectedDrawable;//未选中时的背景
    private Drawable selectedSrc;//选中时的资源
    private Drawable unSelectedSrc;//未选中时的资源
    private Handler mHandler = new Handler();

    private Runnable playTask = new Runnable() {
        @Override
        public void run() {
            mRecyclerView.smoothScrollToPosition(++currentIndex);//让RecyclerView平滑到指定的位置
            if (isShowIndicator) {
                changePoint();//改变圆点指示器的显示位置
            }
            mHandler.postDelayed(this, mInterval);//设置定时器
        }
    };

    public RecyclerViewBanner(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public RecyclerViewBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RecyclerViewBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewBanner);
        mInterval = typedArray.getInt(R.styleable.RecyclerViewBanner_rvb_interval, 3000);
        isShowIndicator = typedArray.getBoolean(R.styleable.RecyclerViewBanner_rvb_showIndicator,
                true);
        isAutoPlaying = typedArray.getBoolean(R.styleable.RecyclerViewBanner_rvb_autoPlaying,
                true);
        selectedSrc = typedArray.getDrawable(R.styleable.RecyclerViewBanner_rvb_indicatorSelectedSrc);
        unSelectedSrc = typedArray.getDrawable(R.styleable.RecyclerViewBanner_rvb_indicatorUnSelectedSrc);
        mSize = typedArray.getDimensionPixelSize(R.styleable.RecyclerViewBanner_rvb_indicatorSize,
                0);
        mSpace = typedArray.getDimensionPixelSize(R.styleable.RecyclerViewBanner_rvb_indicatorSpace,
                dp2px(4));

        margin = typedArray.getDimensionPixelSize(R.styleable.RecyclerViewBanner_rvb_indicatorMargin,
                dp2px(10));
        int g = typedArray.getInt(R.styleable.RecyclerViewBanner_rvb_indicatorGravity,
                1);
        switch (g) {
            case 0:
                gravity = Gravity.START;
                break;
            case 1:
                gravity = Gravity.CENTER;
                break;
            case 2:
                gravity = Gravity.END;
                break;
            default:
                break;
        }
        if (selectedSrc == null) {
            mSelectedDrawable = gradientDrawable(DEFAULT_SELECT_COLOR);
        } else {
            if (selectedSrc instanceof ColorDrawable) {
                mSelectedDrawable = gradientDrawable(((ColorDrawable) selectedSrc).getColor());
            } else {
                mSelectedDrawable = selectedSrc;
            }
        }

        if (unSelectedSrc == null) {
            mUnSelectedDrawable = gradientDrawable(DEFAULT_UNSELECTED_COLOR);
        } else {
            if (unSelectedSrc instanceof ColorDrawable) {
                mUnSelectedDrawable = gradientDrawable(((ColorDrawable) unSelectedSrc).getColor());
            } else {
                mUnSelectedDrawable = unSelectedSrc;
            }
        }
        typedArray.recycle();//回收 TypedArray,用于后续调用时可复用之。当调用该方法后，不能再操作该变量。

        mRecyclerView = new RecyclerView(context);
        mLinearLayout = new LinearLayout(context);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);//辅助Recycler进行滚动对齐
        adapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int first = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    int last = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (first == last && currentIndex != last) {
                        currentIndex = last;
                        if (isShowIndicator && isTouched) {
                            isTouched = false;
                            changePoint();
                        }

                    }
                }
            }
        });
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mLinearLayout.setGravity(Gravity.CENTER);
        LayoutParams vpLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        LayoutParams linearLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        linearLayoutParams.gravity = Gravity.BOTTOM | gravity;
        linearLayoutParams.setMargins(margin, margin, margin, margin);

        addView(mRecyclerView, vpLayoutParams);
        addView(mLinearLayout, linearLayoutParams);
    }

    /**
     * 设置间隔时间
     *
     * @param mInterval
     */
    public void setmInterval(int mInterval) {
        this.mInterval = mInterval;
    }

    /**
     * 设置是否指示器导航点
     *
     * @param showIndicator
     */
    public void setShowIndicator(boolean showIndicator) {
        isShowIndicator = showIndicator;
    }

    /**
     * 设置是否禁止滚动播放
     *
     * @param autoPlaying
     */
    public void setAutoPlaying(boolean autoPlaying) {
        isAutoPlaying = autoPlaying;
    }

    /**
     * 设置是否自动播放（上锁）
     *
     * @param playing 开始播放
     */
    private synchronized void setPlaying(boolean playing) {
        if (isAutoPlaying) {
            if (!isPlaying && playing && adapter != null && adapter.getItemCount() > 2) {
                mHandler.postDelayed(playTask, mInterval);
                isPlaying = true;
            } else if (isPlaying && !playing) {
                mHandler.removeCallbacksAndMessages(null);
                isPlaying = false;
            }
        }
    }

    //手指触摸事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //手动触摸的时候，停止自动播放，根据手势变换
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                int disX = moveX - startX;
                int disY = moveY - startY;
                boolean hasMoved = 2 * Math.abs(disX) > Math.abs(disY);
                getParent().requestDisallowInterceptTouchEvent(hasMoved);
                if (hasMoved) {
                    setPlaying(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!isPlaying) {
                    isTouched = true;
                    setPlaying(true);
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setPlaying(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setPlaying(false);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility == GONE || visibility == INVISIBLE) {
            // 停止轮播
            setPlaying(false);
        } else if (visibility == VISIBLE) {
            // 开始轮播
            setPlaying(true);
        }
        super.onWindowVisibilityChanged(visibility);
    }


    //创建默认指示器
    private GradientDrawable gradientDrawable(int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setSize(dp2px(6), dp2px(6));
        gradientDrawable.setCornerRadius(dp2px(6));
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }


    /**
     * 指示器整体由数据列表容量数量的AppCompatImageView均匀分布在一个横向的LinearLayout中构成
     * 使用AppCompatImageView的好处是在Fragment中也使用Compat相关属性
     */
    private void createIndicators() {
        mLinearLayout.removeAllViews();//先动态移除所有的View
        for (int i = 0; i < mData.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams indicatorsParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            indicatorsParams.leftMargin = mSpace / 2;
            indicatorsParams.rightMargin = mSpace / 2;
            if (mSize >= dp2px(4)) {
                indicatorsParams.width = indicatorsParams.height = mSize;
            } else {
                imageView.setMinimumHeight(dp2px(2));
                imageView.setMinimumWidth(dp2px(2));
            }
            imageView.setImageDrawable(i == 0 ? mSelectedDrawable : mUnSelectedDrawable);
            mLinearLayout.addView(imageView, indicatorsParams);
        }
    }

    //将int值转化为dp值
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }


    /**
     * 设置轮播数据
     *
     * @param data
     */
    public void setRvBannerData(List data) {
        setPlaying(false);
        mData.clear();
        if (null != data) {
            mData.addAll(data);
        }
        if (mData.size() > 1) {
            adapter.notifyDataSetChanged();
            // 将起始点设为最靠近的 MAX_VALUE/2 的，且为mData.size()整数倍的位置
            currentIndex = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mData.size();
            mRecyclerView.scrollToPosition(currentIndex);
            if (isShowIndicator) {
                createIndicators();
            }
            setPlaying(true);
        } else {
            currentIndex = 0;
            adapter.notifyDataSetChanged();
        }

    }

    private void changePoint() {
        if (mLinearLayout != null && mLinearLayout.getChildCount() > 0) {
            for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
                ((ImageView) mLinearLayout.getChildAt(i)).setImageDrawable(
                        i == currentIndex % mData.size() ? mSelectedDrawable : mUnSelectedDrawable);
            }
        }
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(viewGroup.getContext());
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            simpleDraweeView.setLayoutParams(layoutParams);
            simpleDraweeView.setId(R.id.rvb_banner_imageView_id);
            simpleDraweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            simpleDraweeView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.OnClick(currentIndex % mData.size());
                    }
                }
            });

            return new RecyclerView.ViewHolder(simpleDraweeView) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            SimpleDraweeView draweeView = viewHolder.itemView.findViewById(R.id.rvb_banner_imageView_id);
            if (onSwitchRvBannerListener != null) {
                onSwitchRvBannerListener.switchBanner(i % mData.size(), draweeView);
            }

        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size() < 2 ? mData.size() : Integer.MAX_VALUE;
        }
    }

    public void setListener(OnRvBannerClickListener listener) {
        this.listener = listener;
    }

    public void setOnSwitchRvBannerListener(OnSwitchRvBannerListener onSwitchRvBannerListener) {
        this.onSwitchRvBannerListener = onSwitchRvBannerListener;
    }


    private class PagerSnapHelper extends LinearSnapHelper {
        /**
         * 通过当前滑动速度，计算最终定位的position
         *
         * @param layoutManager
         * @param velocityX
         * @param velocityY
         * @return
         */

        @Override
        public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
            //获取到将要滚动的位置targetPos
            int targetPos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
            //找到与之最近的view
            View currentView = findSnapView(layoutManager);
            if (targetPos != RecyclerView.NO_POSITION && null != currentView) {
                //获取最近View的位置currentPos
                int currentPos = layoutManager.getPosition(currentView);
                int first = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
                int last = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                //如果滑动的位置<最近view的位置，最近的view的位置对齐最后一个可见view的位置
                //如果滑动的位置>最近view的位置，最近的view的位置对齐第一个可见的View的位置
                currentPos = targetPos < currentPos ? last : (targetPos > currentPos ?
                        first : currentPos);
                //如果滑动的位置<最后一个可见view的位置，将要滑动的位置对齐第一个view
                //如果滑动的位置>第一个可见view的位置，将要滑动的位置对齐最后一个view
                targetPos = targetPos < currentPos ? currentPos - 1 :
                        (targetPos > currentPos ? currentPos + 1 : currentPos);
            }
            return targetPos;
        }

    }

}
