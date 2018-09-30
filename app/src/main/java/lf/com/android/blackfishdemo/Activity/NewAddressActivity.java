package lf.com.android.blackfishdemo.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.util.ToastUtil;

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
    private CityPickerView mPickerView = new CityPickerView();
    private Context mContext;

    @Override
    public int getlayoutId() {
        return R.layout.activity_new_address_layout;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
        getWindow().setStatusBarColor(getColor(R.color.colorWhite));
        isDefault = getIntent().getBooleanExtra("isDefault", false);
        if (isDefault) {
            mCheckBox.setChecked(true);
            mCheckBox.setClickable(false);
        }
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPickerView.setConfig(cityConfig);
        mPickerView.init(this);
        mContext = NewAddressActivity.this;

        mIvBack.setOnClickListener(this);
        mTvArea.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);

    }

    @Override
    public void intitdata() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString("name");
            String phone = bundle.getString("phone");
            String address = "所在地区: " + bundle.getString("address");
            isDefault = bundle.getBoolean("isDefault");
            mEtName.setText(name);
            mEtPhone.setText(phone);
            mEtAddress.setText(address);
            mCheckBox.setChecked(isDefault);
        }

    }

    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.tv_area:
                mPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        mArea = " " + province.getName() + " " + city.getName() + " " + district.getName() + "   ";
                        String text = "所在地区: " + mArea;
                        mTvArea.setText(text);
                    }
                });
                mPickerView.showCityPicker();
                break;
            case R.id.btn_save:
                String name = mEtName.getText().toString();
                String phone = mEtPhone.getText().toString();
                String address = mArea + mEtAddress.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(mArea)) {
                    Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "保存", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    bundle.putString("phone", phone);
                    bundle.putString("address", address);
                    bundle.putBoolean("isDefault", mCheckBox.isChecked());
                    intent.putExtras(bundle);
                    setResult(0x02, intent);
                    finishActivity();
                } else {
                    Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "请填写正确信息", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            default:
                break;
        }
    }
}
