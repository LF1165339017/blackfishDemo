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

public class RecyclerViewBanner extends FrameLayout {
    private static final int DEFAULT_SELECT_COLOR = 0xffffffff;
    private static final int DEFAULT_UNSELECTED_COLOR = 0X50ffffff;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;
    private LinearLayout mLinearLayout;
    private OnRvBannerClickListener listener;
    private OnSwitchRvBannerListener onSwitchRvBannerListener;
    private boolean isPlaying;
    private int startX, startY, currentIndex;
    private int mSize;
    private int mSpace;
    private int mInterval;
    private int margin;
    private int gravity;
    private boolean isShowIndicator;
    private boolean isAutoPlaying;
    private boolean isTouched;
    private List<Object> mData = new ArrayList<>();
    private Drawable mSelectedDrawable;
    private Drawable mUnSelectedDrawable;
    private Drawable selectedSrc;
    private Drawable unSelectedSrc;
    private Handler mHandler = new Handler();

    private Runnable playTask = new Runnable() {
        @Override
        public void run() {
            mRecyclerView.smoothScrollToPosition(++currentIndex);
            if (isShowIndicator) {
                switchIndicator();
            }
            mHandler.postDelayed(this, mInterval);
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
        int g = typedArray.getDimensionPixelSize(R.styleable.RecyclerViewBanner_rvb_indicatorGravity,
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
        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);
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
                            switchIndicator();
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

    //手指触摸事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                //阻止父层View拦截事件
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
                break;
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
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        if (visibility == GONE || visibility == INVISIBLE) {
            // 停止轮播
            setPlaying(false);
        } else if (visibility == VISIBLE) {
            // 开始轮播
            setPlaying(true);
        }
        super.onVisibilityChanged(changedView, visibility);
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

    private synchronized void setPlaying(boolean playing) {
        if (isAutoPlaying) {
            if (!isPlaying && playing && adapter != null && adapter.getItemCount() > 2) {

            } else if (isPlaying && !playing) {

            }
        }
    }

    public void setRvBannerData(List data) {
        setPlaying(false);
        mData.clear();
        if (null != data) {
            mData.addAll(data);
        }
        if (mData.size() > 1) {
            currentIndex = mData.size();
            adapter.notifyDataSetChanged();
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

    private void switchIndicator() {
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
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }
    }

    public void setListener(OnRvBannerClickListener listener) {
        this.listener = listener;
    }

    public void setOnSwitchRvBannerListener(OnSwitchRvBannerListener onSwitchRvBannerListener) {
        this.onSwitchRvBannerListener = onSwitchRvBannerListener;
    }


    private class PagerSnapHelper extends LinearSnapHelper {

        @Override
        public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
            //获取到将要滚动的位置targetPos
            int targetPos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
            //找到与之最近的view
            View currentView = findSnapView(layoutManager);
            if (targetPos != RecyclerView.NOT_FOCUSABLE && null != currentView) {
                //获取最近View的位置currentPos
                int currentPos = layoutManager.getPosition(currentView);
                int first = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
                int last = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                //如果滑动的位置<最近view的位置，最近的view的位置对齐最后一个可见view的位置
                //如果滑动的位置>最近view的位置，最近的view的位置对齐第一个可见的View的位置
                currentPos = targetPos < currentPos ? last : (targetPos > currentPos ? first : currentPos);
                //如果滑动的位置<最后一个可见view的位置，将要滑动的位置对齐第一个view
                //如果滑动的位置>第一个可见view的位置，将要滑动的位置对齐最后一个view
                targetPos = targetPos < currentPos ? currentPos - 1 : (
                        targetPos > currentPos ? currentPos + 1 : currentPos);
            }
            return targetPos;
        }

    }

}
