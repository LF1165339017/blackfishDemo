package lf.com.android.blackfishdemo.splash;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import lf.com.android.blackfishdemo.HomeActivity;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnViewListener;
import lf.com.android.blackfishdemo.util.BitmapUtil;
import lf.com.android.blackfishdemo.util.ViewUtil;

public class WelcomeActivity extends AppCompatActivity {
    private SharedPreferences.Editor mEditor;
    private boolean isFirstload;
    private Bitmap mBitmap;
    private ImageView mImageView;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0x01:
                    //利用bitmap加载图片
                    setBitmap();
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_layout);

        mImageView = findViewById(R.id.iv_welcome_imageView);

        //利用异步使用bitmap去加载图片
        Message msg = mHandler.obtainMessage(0x01);
        mHandler.sendMessage(msg);
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        isFirstload = preferences.getBoolean("isFirstload", true);
        mEditor = preferences.edit();
        isFirstload();
    }

    //判断是否是第一次登录
    private void isFirstload() {

        if (isFirstload) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WelcomeActivity.this,
                            WelcomeSplashActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK));
                    overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
                }
            }, 1500);
            mEditor.putBoolean("isFirstload", false);
            mEditor.apply();
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WelcomeActivity.this,
                            HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK));
                    overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
                }
            }, 1500);
        }

    }

    //利用bitmap加载图片
    private void setBitmap() {
        ViewUtil.getViewWidth(mImageView, new OnViewListener() {
            @Override
            public void onView(int width, int height) {
                mBitmap = BitmapUtil.Resource(getResources(), R.drawable.host_splash, width, height);
                mImageView.setImageBitmap(mBitmap);
            }
        });
    }
}
