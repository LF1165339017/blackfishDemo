package lf.com.android.blackfishdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import lf.com.android.blackfishdemo.R;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private Unbinder mUnbinder;

    public abstract int getlayoutId();

    public abstract void initView();

    public abstract void intitdata();

    public abstract void ClickListener(View view);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getlayoutId());
        mUnbinder = ButterKnife.bind(this);
        initView();
        intitdata();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    @Override
    public void onClick(View v) {
        ClickListener(v);
    }

    public void skipActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.activity_right_in, 0);
    }

    public void finishActivity() {
        finish();
        overridePendingTransition(0, R.anim.activity_right_out);
    }
}
