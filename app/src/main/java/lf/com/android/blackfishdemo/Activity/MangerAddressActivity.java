package lf.com.android.blackfishdemo.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnMangerAddressListener;
import lf.com.android.blackfishdemo.util.DenistyUtil;
import lf.com.android.blackfishdemo.util.SharePerUtil;

public class MangerAddressActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.btn_new_address)
    Button mBtnNewAddress;
    @BindView(R.id.ll_no_address)
    LinearLayout mLiNoAddress;
    @BindView(R.id.ll_address)
    LinearLayout mLiAddress;
    @BindView(R.id.btn_add_address)
    Button mBtnAddAddress;

    private static final int REQUEST_CODE = 0x01;
    private static final int RESULT_CODE = 0x02;
    private Context mContext;
    private SharePerUtil mSharePerUtil;


    @Override
    public int getlayoutId() {
        return R.layout.activity_manager_address_layout;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
        getWindow().setStatusBarColor(getColor(R.color.colorWhite));
        mContext = MangerAddressActivity.this;
        mSharePerUtil = new SharePerUtil(mContext);
        mIvBack.setOnClickListener(this);
        mBtnNewAddress.setOnClickListener(this);
    }

    @Override
    public void intitdata() {
        int count = mSharePerUtil.getInt("childCount");
        if (count > 0) {
            mLiAddress.setVerticalGravity(View.INVISIBLE);
            for (int i = 0; i < count; i++) {
                mMangerAddressListener.addAddress(mSharePerUtil.getString("name" + i),
                        mSharePerUtil.getString("phone" + i), mSharePerUtil.getString("address" + i),
                        mSharePerUtil.getBoolean("isDefault" + i), true);
            }
        }
    }

    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.btn_new_address:
                Intent intent = new Intent(this, NewAddressActivity.class);
                intent.putExtra("isDefault", !(mLiAddress.getChildCount() > 0));
                startActivityForResult(intent, REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            mLiNoAddress.setVisibility(View.INVISIBLE);
            mMangerAddressListener.addAddress(data.getStringExtra("name"), data.getStringExtra("phone"),
                    data.getStringExtra("address"), data.getBooleanExtra("isDefault", false), false);
        }
    }

    private OnMangerAddressListener mMangerAddressListener = new OnMangerAddressListener() {
        @Override
        public void addAddress(String name, String phone, String address, boolean isDefault, boolean isCreate) {
            final int childCount = mLiAddress.getChildCount();
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_address_layout, null);
            TextView textViewName = view.findViewById(R.id.tv_name);
            TextView textViewPhone = view.findViewById(R.id.tv_phone);
            TextView textViewAddress = view.findViewById(R.id.tv_address);
            AppCompatCheckBox checkBox = view.findViewById(R.id.checks);
            TextView textViewEdit = view.findViewById(R.id.tv_edit);
            TextView textViewDelete = view.findViewById(R.id.tv_delete);

            textViewName.setText(name);
            textViewPhone.setText(phone);
            textViewAddress.setText(address);
            checkBox.setChecked(isDefault);

            final Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("phone", phone);
            bundle.putString("address", address);
            bundle.putBoolean("isDefault", isDefault);
            if (isDefault) {
                checkBox.setChecked(false);
            }
            textViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMangerAddressListener.editAddress(bundle);
                }
            });
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMangerAddressListener.deleteAddress(childCount);
                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mMangerAddressListener.setDefaultAddress(childCount);
                    }
                }
            });

            mLiAddress.addView(view, childCount);

            setBtnLayout(childCount);
            mBtnAddAddress.setVisibility(View.VISIBLE);
            mBtnAddAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NewAddressActivity.class);
                    intent.putExtra("isDefault", !(mLiAddress.getChildCount() > 0));
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });

            if (!isCreate) {
                mSharePerUtil.saveString("name" + childCount, name);
                mSharePerUtil.saveString("phone" + childCount, phone);
                mSharePerUtil.saveString("address" + childCount, address);
                mSharePerUtil.saveBoolean("isDefault" + childCount, isDefault);
                mSharePerUtil.saveInt("childCount", childCount + 1);
            }
        }

        @Override
        public void editAddress(Bundle bundle) {
            Intent intent = new Intent(mContext, NewAddressActivity.class);
            intent.putExtras(bundle);
            skipActivity(intent);
        }

        @Override
        public void deleteAddress(int index) {
            mLiAddress.removeViewAt(index);
            int count = mLiAddress.getChildCount();
            if (count == 0) {
                mBtnAddAddress.setVisibility(View.INVISIBLE);
                mLiNoAddress.setVisibility(View.VISIBLE);
            }
            setBtnLayout(count - 1);


            mSharePerUtil.deleteValue("name" + index);
            mSharePerUtil.deleteValue("phone" + index);
            mSharePerUtil.deleteValue("address" + index);
            mSharePerUtil.deleteValue("isDefault" + index);
            mSharePerUtil.saveInt("childCount", mSharePerUtil.getInt("childCount") - 1);
        }

        @Override
        public void setDefaultAddress(int index) {
            updateData(index);
        }
    };

    private void updateData(int index) {
        String name = mSharePerUtil.getString("name0");
        String phone = mSharePerUtil.getString("phone0");
        String address = mSharePerUtil.getString("address0");

        mSharePerUtil.saveString("name0", mSharePerUtil.getString("name" + index));
        mSharePerUtil.saveString("phone0", mSharePerUtil.getString("phone" + index));
        mSharePerUtil.saveString("address0", mSharePerUtil.getString("address" + index));

        mSharePerUtil.saveString("name" + index, name);
        mSharePerUtil.saveString("phone" + index, phone);
        mSharePerUtil.saveString("address" + index, address);
        mSharePerUtil.saveBoolean("isDefault" + index, false);

        mLiNoAddress.setVisibility(View.INVISIBLE);
        int count = mLiAddress.getChildCount();
        mLiAddress.removeAllViews();
        for (int i = 0; i < count; i++) {
            mMangerAddressListener.addAddress(mSharePerUtil.getString("name" + i), mSharePerUtil.getString("phone" + i),
                    mSharePerUtil.getString("address" + i), mSharePerUtil.getBoolean("isDefault" + i), true);
        }
    }

    private void setBtnLayout(int childCount) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBtnAddAddress.getLayoutParams();
        layoutParams.setMargins(0, DenistyUtil.dip2px(mContext, 120 * (childCount + 1) + 50), 0, 0);
        mBtnAddAddress.setLayoutParams(layoutParams);
    }

}
