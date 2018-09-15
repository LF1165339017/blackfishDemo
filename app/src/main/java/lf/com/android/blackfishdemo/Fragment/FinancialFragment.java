package lf.com.android.blackfishdemo.Fragment;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.bean.UrlInfoBean;
import lf.com.android.blackfishdemo.util.WebSettingUtil;

public class FinancialFragment extends BaseFragment {
    @BindView(R.id.webViews)
    WebView mWebView;
    private Context mContext;

    public static FinancialFragment newInstance() {
        return new FinancialFragment();
    }

    @Override
    public void initView() {
        mContext = getActivity();
        WebSettingUtil.setSetting(mWebView);
        mWebView.loadUrl(UrlInfoBean.financialUrl);

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void initdata() {

    }

    @Override
    public void lisetener(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nav_finacial;
    }
}
