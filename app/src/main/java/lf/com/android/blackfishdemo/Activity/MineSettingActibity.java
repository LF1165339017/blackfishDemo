package lf.com.android.blackfishdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;

public class MineSettingActibity extends BaseActivity {
    private Context mContext;

    @BindView(R.id.tv_setting_login_pwd)
    TextView mTextLoginPwd;
    @BindView(R.id.tv_setting_pay_pwd)
    TextView mTextPayPwd;
    @BindView(R.id.tv_setting_manger_address)
    TextView mTextMangerAddress;
    @BindView(R.id.btn_setting_exit_login)
    TextView mTextExitLogin;
    @BindView(R.id.iv_mine_setting_back)
    ImageView mIvBack;

    private String phone = "18756935216";

    @Override
    public int getlayoutId() {
        return R.layout.activity_mine_setting_layout;
    }

    @Override
    public void initView() {
        mContext = MineSettingActibity.this;
        mTextPayPwd.setOnClickListener(this);
        mTextLoginPwd.setOnClickListener(this);
        mTextMangerAddress.setOnClickListener(this);
        mTextExitLogin.setOnClickListener(this);
        mIvBack.setOnClickListener(this);

    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_setting_login_pwd:
                intent = new Intent(this, SetPwdActivity.class);
                intent.putExtra("phoneNumber", phone);
                startActivity(intent);
                break;
            case R.id.tv_setting_pay_pwd:
                Toast.makeText(mContext, "支付密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_setting_manger_address:
                skipActivity(new Intent(this, MangerAddressActivity.class));
                break;
            case R.id.btn_setting_exit_login:
                startActivity(new Intent(this, TestActivity.class));
                break;
            case R.id.iv_mine_setting_back:
                finish();
                break;
            default:
                break;
        }
    }
}
