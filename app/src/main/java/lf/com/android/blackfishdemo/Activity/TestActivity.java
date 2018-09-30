package lf.com.android.blackfishdemo.Activity;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.view.TagsLayout;

public class TestActivity extends BaseActivity {
    private Context mContext;
    @BindView(R.id.tags_layout)
    TagsLayout mTagsLayout;

    @Override
    public int getlayoutId() {
        return R.layout.activity_test_layout;
    }

    @Override
    public void initView() {
        mContext = TestActivity.this;
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        String[] strings = {"你好,很高兴遇见你", "你好,很高兴看见你", "你好,看见你很高兴", "你好"};
        for (int i = 0; i < strings.length; i++) {
            TextView textView = new TextView(mContext);
            textView.setText(strings[i]);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundColor(getResources().getColor(R.color.colorBlue1296db));
            mTagsLayout.addView(textView, lp);
        }

    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {

    }
}
