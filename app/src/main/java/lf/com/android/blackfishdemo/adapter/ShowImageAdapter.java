package lf.com.android.blackfishdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import lf.com.android.blackfishdemo.listener.OnImageClickListener;

public class ShowImageAdapter extends PagerAdapter {
    private Context context;
    private String[] mStrings;
    private OnImageClickListener mOnImageClickListener;

    public ShowImageAdapter(Context context, String[] mStrings) {
        this.context = context;
        this.mStrings = mStrings;
    }

    @Override
    public int getCount() {
        return mStrings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        SimpleDraweeView draweeView = new SimpleDraweeView(context);
        draweeView.setImageURI(mStrings[position]);
        draweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(draweeView);
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnImageClickListener.onImageClick();
            }
        });
        return draweeView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setmOnImageClickListener(OnImageClickListener mOnImageClickListener) {
        this.mOnImageClickListener = mOnImageClickListener;
    }
}
