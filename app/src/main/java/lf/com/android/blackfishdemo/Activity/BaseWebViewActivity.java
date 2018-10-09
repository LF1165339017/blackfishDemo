package lf.com.android.blackfishdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.util.ToastUtil;
import lf.com.android.blackfishdemo.util.WebSettingUtil;

public class BaseWebViewActivity extends FragmentActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView mIv_Back;
    @BindView(R.id.tv_title)
    TextView mTv_Title;
    @BindView(R.id.iv_share)
    ImageView mIv_share;
    @BindView(R.id.wv_base_activity_view)
    WebView mWebview;

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //5.0及以上系统才支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            //两个Flag必须要结合在一起使用，表示会让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            //将状态栏设置成透明色
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_layout);
        ButterKnife.bind(this);
        context = BaseWebViewActivity.this;
        WebSettingUtil.setSetting(mWebview);
        Intent intent = getIntent();
        mWebview.loadUrl(intent.getStringExtra("loadUrl"));
        mIv_Back.setOnClickListener(this);
        mIv_share.setOnClickListener(this);
        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    mTv_Title.setText("正在加载中....");
                }else {
                    mTv_Title.setText("加载完成");
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                mTv_Title.setText(title);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                overridePendingTransition(0, R.anim.activity_right_out);
                break;
            case R.id.iv_share:
                showBottomSheetDialog();
                break;
            default:
                break;

        }
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.view_share_bottom_dialog, null);
        dialog.setContentView(view);
        ImageView iv_close_dialog = view.findViewById(R.id.iv_close_dialog);
        ImageView iv_share_WeChat = view.findViewById(R.id.iv_weChat);
        ImageView iv_share_WeChat_friend = view.findViewById(R.id.iv_weChat_friend);
        iv_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        iv_share_WeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = ToastUtil.setMyToast(
                        context, ToastUtil.PROMPT, "分享至微信好友", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        iv_share_WeChat_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = ToastUtil.setMyToast(
                        context, ToastUtil.PROMPT, "分享至微信朋友圈", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        /**
         *   点击ProgressDialog以外的区域的时候ProgressDialog就会关闭，
         *   反之设置setCancelable(false)时，点击ProgressDialog以外的区域不会关闭ProgressDialog
         */
        dialog.setCancelable(true);

        dialog.show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(0, R.anim.activity_right_out);
        }
        return true;
    }
}
