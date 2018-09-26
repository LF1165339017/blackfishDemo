package lf.com.android.blackfishdemo.Activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.util.OrderInfoUtil12_0;
import lf.com.android.blackfishdemo.util.PayResult;

public class PayTypeActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.rl_lines)
    RelativeLayout mRlLines;
    @BindView(R.id.iv_cash_pay)
    ImageView mIvCashPay;
    @BindView(R.id.rl_cash_pay)
    RelativeLayout mRlCashPay;
    @BindView(R.id.iv_fenqi_pay)
    ImageView mIvFenQiPay;
    @BindView(R.id.rl_fenqi_pay)
    RelativeLayout mRlFenQiPay;
    @BindView(R.id.btn_pay)
    Button mBtnPay;

    private Context mContext;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String result = payResult.getResult();
                    String resultStatus = payResult.getResultStauts();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    private boolean isCashPay = true;
    private static final String RSA2_PRIVATE = "sdjlhdfjklhnasdjklflasdnfkljasdfnjklasndfjknasdjklnf" +
            "jklasdhnfjklkamdfl;mdsl;fl;asdkfl;ksald;fkl;asdkfl;ksadl;fkl;askdfl;kasodfkasopjfioasdjfio" +
            "jioadmf;klmas;dlfml;asdklf;kl;sdakfl;aksdfl;ksdoafjkjwfiasfjklaakljfadiosjfioj";

    private static final String APP_ID = "126498451654160165165";
    private static final String PID = "1564843218541310561541";
    private static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;

    @Override
    public int getlayoutId() {
        return R.layout.activity_pay_type_layout;
    }

    @Override
    public void initView() {
        mContext = PayTypeActivity.this;
        mIvBack.setOnClickListener(this);
        mRlLines.setOnClickListener(this);
        mIvCashPay.setOnClickListener(this);
        mRlCashPay.setOnClickListener(this);
        mIvFenQiPay.setOnClickListener(this);
        mBtnPay.setOnClickListener(this);
    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.rl_lines:
                Toast.makeText(mContext, "点击了商城额度", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_cash_pay:
                isCashPay = true;
                mBtnPay.setText("支付44.0元");
                mIvCashPay.setImageResource(R.drawable.icon_checkbox_checked);
                mIvFenQiPay.setImageResource(R.drawable.icon_checkbox_unchecked);
                break;
            case R.id.rl_fenqi_pay:
                isCashPay = false;
                mBtnPay.setText("激活分期额度");
                mIvCashPay.setImageResource(R.drawable.icon_checkbox_unchecked);
                mIvFenQiPay.setImageResource(R.drawable.icon_checkbox_checked);
                break;
            case R.id.btn_pay:
                if (isCashPay) {
                    pay();
                } else {
                    Toast.makeText(mContext, "请先激活分期", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;

        }
    }

    private void pay() {
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil12_0.buildOrderParamMap(APP_ID, rsa2);
        String orderParam = OrderInfoUtil12_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil12_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(PayTypeActivity.this);
                Map<String, String> result = payTask.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;

                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(runnable);
        payThread.start();
    }


}
