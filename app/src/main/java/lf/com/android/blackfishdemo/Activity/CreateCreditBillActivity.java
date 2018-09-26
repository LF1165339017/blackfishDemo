package lf.com.android.blackfishdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnSelectFinshListener;
import lf.com.android.blackfishdemo.listener.OnSuperEditClickListener;
import lf.com.android.blackfishdemo.listener.OnSuperEditLayoutClickListener;
import lf.com.android.blackfishdemo.util.PickerUtil;
import lf.com.android.blackfishdemo.util.ToastUtil;
import lf.com.android.blackfishdemo.view.SuperEditText;

public class CreateCreditBillActivity extends BaseActivity {
    private Context mContext;
    @BindView(R.id.tv_keeper_create_credit_bill_back)
    ImageView mImageBack;
    @BindView(R.id.super_edit_card_number)
    SuperEditText mSuperCardNumber;
    @BindView(R.id.super_edit_belong_to_bank)
    SuperEditText mSuperBank;
    @BindView(R.id.super_edit_card_type)
    SuperEditText mSuperCardType;
    @BindView(R.id.super_edit_username)
    SuperEditText mSuperUsername;
    @BindView(R.id.super_edit_lines)
    SuperEditText mSuperLines;
    @BindView(R.id.super_edit_bill)
    SuperEditText mSuperBill;
    @BindView(R.id.super_edit_bill_day)
    SuperEditText mSuperBillDay;
    @BindView(R.id.super_edit_pay_bill_day)
    SuperEditText mSuperPayBillDay;
    @BindView(R.id.btn_create_save)
    Button mButtonSave;

    private PickerUtil mPickerUtil;
    private String bankName = "";
    private String cardType = "信用卡";
    private String billDay = "15 日";
    private String payBillDay = "5 日";

    @Override
    public int getlayoutId() {
        return R.layout.activity_create_credit_bill_layout;
    }

    @Override
    public void initView() {
        mContext = CreateCreditBillActivity.this;
        mPickerUtil = new PickerUtil();
        mImageBack.setOnClickListener(this);
        mButtonSave.setOnClickListener(this);

        mSuperCardNumber.setmClickLitener(new SuperListenerLayout());
        mSuperBank.setmClickLitener(new SuperListenerLayout());
        mSuperCardType.setmClickLitener(new SuperListenerLayout());
        mSuperUsername.setmClickLitener(new SuperListenerLayout());
        mSuperLines.setmClickLitener(new SuperListenerLayout());
        mSuperBill.setmClickLitener(new SuperListenerLayout());
        mSuperPayBillDay.setmClickLitener(new SuperListenerLayout());

        mSuperBank.setmSuperListener(new OnSuperClick());
        mSuperCardType.setmSuperListener(new OnSuperClick());
        mSuperBill.setmSuperListener(new OnSuperClick());
        mSuperPayBillDay.setmSuperListener(new OnSuperClick());
    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.tv_keeper_create_credit_bill_back:
                finish();
                break;
            case R.id.btn_create_save:
                getEditText();
                break;
            default:
                break;
        }

    }

    class SuperListenerLayout implements OnSuperEditLayoutClickListener {

        @Override
        public void onSuperEditClick(String id) {
            switch (id) {
                case "卡号":
                    Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "卡号  被点击" + mSuperCardNumber.getData(), Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                case "所属银行":
                    selectBank();
                    break;
                case "卡片类型":
                    selectCardType();
                    break;
                case "用户名":
                    Toast toast1 = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "信用额度 被点击" + mSuperUsername.getData(), Toast.LENGTH_SHORT);
                    toast1.show();
                    break;
                case "信用额度":
                    Toast toast2 = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "信用额度 被点击" + mSuperLines.getData(), Toast.LENGTH_SHORT);
                    toast2.show();
                    break;
                case "账单金额":
                    Toast toast3 = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "信用额度 被点击" + mSuperBill.getData(), Toast.LENGTH_SHORT);
                    toast3.show();
                    break;
                case "账单日":
                    selectBillDay();
                    break;
                case "还款日":
                    selectPayBillDay();
                    break;
                default:
                    break;

            }
        }
    }


    class OnSuperClick implements OnSuperEditClickListener {
        @Override
        public void onSuperClick(String id) {
            switch (id) {
                case "所属银行":
                    selectBank();
                    break;
                case "卡片类型":
                    selectCardType();
                    break;
                case "账单日":
                    selectBillDay();
                    break;
                case "还款日":
                    selectPayBillDay();
                    break;
                default:
                    break;
            }
        }
    }

    private void selectPayBillDay() {
        mPickerUtil.showCustomPicker(this, R.array.BillDay, new OnSelectFinshListener() {
            @Override
            public String onSelected(String result) {
                payBillDay = result;
                mSuperBillDay.setmEditText("       " + payBillDay);
                return null;
            }
        });

    }

    private void selectBillDay() {
        mPickerUtil.showCustomPicker(this, R.array.BillDay, new OnSelectFinshListener() {
            @Override
            public String onSelected(String result) {
                billDay = result;
                mSuperBillDay.setmEditText("        " + billDay);
                return null;
            }
        });
    }

    private void selectCardType() {
        mPickerUtil.showCustomPicker(this, R.array.CardType, new OnSelectFinshListener() {
            @Override
            public String onSelected(String result) {
                cardType = result;
                mSuperCardType.setmEditText("      " + cardType);
                return null;
            }
        });
    }

    private void selectBank() {
        startActivityForResult(new Intent(mContext, SelectCardActivity.class), 0x001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x001 && resultCode == RESULT_OK) {
            bankName = data.getStringExtra("cardName");
            mSuperBank.setmEditText("      " + bankName);
        }
    }

    private void getEditText() {
        HashMap<String, String> mCardNumberMap = mSuperCardNumber.getmStringHashMap();
        String cardNumber = mCardNumberMap.get("卡号");
        HashMap<String, String> mUsernameMap = mSuperUsername.getmStringHashMap();
        String username = mUsernameMap.get("用户名");
        HashMap<String, String> mLinesMap = mSuperLines.getmStringHashMap();
        String lines = mLinesMap.get("信用额度");
        HashMap<String, String> mBillMap = mSuperBill.getmStringHashMap();
        String bills = mBillMap.get("账单金额");

        if (null == cardNumber || cardNumber.length() < 19) {
            showToast("请填写正确卡号");
        } else if (null == username || username.equals("")) {
            showToast("请填写正确的用户名");
        } else if (null == lines || lines.equals("")) {
            showToast("请填写正确信用额度");
        } else if (null == bills || bills.equals("")) {
            showToast("请填写正确账单金额");
        } else if (bankName.equals("")) {
            showToast("请选择所属银行");
        } else {
            Intent intent = new Intent(mContext, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("CardNumber", cardNumber);
            bundle.putString("BankName", bankName);
            bundle.putString("CardType", cardType);
            bundle.putString("Username", username);
            bundle.putString("Lines", lines);
            bundle.putString("Bills", bills);
            bundle.putString("BillDay", billDay);
            bundle.putString("PayBillDay", payBillDay);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            startActivity(intent);

        }
    }

    private void showToast(String msg) {
        Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
