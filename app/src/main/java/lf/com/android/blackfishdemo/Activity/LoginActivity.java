package lf.com.android.blackfishdemo.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import lf.com.android.blackfishdemo.Activity.BaseActivity;
import lf.com.android.blackfishdemo.Fragment.MyRegisteredFragment;
import lf.com.android.blackfishdemo.R;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.btn_registered)
    Button mBtn_registered;
    @BindView(R.id.btn_login)
    Button mBtn_login;
    @BindView(R.id.user_icon_qq)
    ImageView mIv_icon_qq;
    @BindView(R.id.user_icon_wechat_login)
    ImageView mIv_icon_Wechat;
    @Override
    public int getlayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        //5.0及以上系统才支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            //两个Flag必须要结合在一起使用，表示会让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //将状态栏设置成透明色
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //将actionBar隐藏
            actionBar.hide();
        }

        mBtn_login.setOnClickListener(this);
        mBtn_registered.setOnClickListener(this);
        mIv_icon_qq.setOnClickListener(this);
        mIv_icon_Wechat.setOnClickListener(this);
    }

    @Override
    public void intitdata() {
    }

    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                break;

            case R.id.btn_registered:
                startActivity(new Intent(LoginActivity.this, RegisteredActivity.class));
                overridePendingTransition(R.anim.activity_bottom_in, 0);
                break;
            default:
                break;
        }
    }

}
