package lf.com.android.blackfishdemo.Activity;

import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;

public class NewAddressActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.et_address)
    EditText mEtAddress;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    @BindView(R.id.app_checkBox)
    AppCompatCheckBox mCheckBox;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    private String mArea;
    private boolean isDefault;

    @Override
    public int getlayoutId() {
        return R.layout.activity_new_address_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {

    }
}
