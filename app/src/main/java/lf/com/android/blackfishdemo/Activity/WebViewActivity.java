package lf.com.android.blackfishdemo.Activity;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.webView_share)
    ImageView mIv_share;
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    public int getlayoutId() {
        return R.layout.web_view_layout;
    }

    @Override
    public void initView() {
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
        mWebView.getSettings().setJavaScriptEnabled(true);//支持JavaScript脚本
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    mTvTitle.setText("正在加载中");
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                mTvTitle.setText(title);
            }
        });

        mIvBack.setOnClickListener(this);
        mIv_share.setOnClickListener(this);
    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                overridePendingTransition(0, R.anim.activity_right_out);
                break;
            case R.id.ivSearch:
                ShowBottomDialog();
            default:
                break;
        }
    }

    private void ShowBottomDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.view_bottom_show_dialog, null);

    }
}
