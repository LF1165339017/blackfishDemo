package lf.com.android.blackfishdemo.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.util.ToastUtil;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.tv_about_version)
    TextView mTextVersion;
    @BindView(R.id.tv_about_sug)
    TextView mTextSug;
    @BindView(R.id.tv_about_contact)
    TextView mTextContact;
    @BindView(R.id.tv_about_check)
    TextView mTextCheck;
    @BindView(R.id.tv_mine_about_back)
    ImageView mImageBack;

    @Override
    public int getlayoutId() {
        return R.layout.activity_about_black_fish;
    }

    @Override
    public void initView() {
        mTextSug.setOnClickListener(this);
        mTextContact.setOnClickListener(this);
        mTextCheck.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.tv_about_sug:
                startActivity(new Intent(this, SuggestionActivity.class));
                break;
            case R.id.tv_about_contact:
                Toast toast = ToastUtil.setMyToast(this, ToastUtil.PROMPT, "待开发", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.tv_about_check:
                Toast toast1 = ToastUtil.setMyToast(this, ToastUtil.PROMPT, "当前已经是最新版本", Toast.LENGTH_SHORT);
                toast1.show();
                break;
            case R.id.tv_mine_about_back:
                finish();
                break;
            default:
                break;
        }
    }
}
