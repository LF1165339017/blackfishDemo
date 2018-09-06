package lf.com.android.blackfishdemo.util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class CountDownUtil extends CountDownTimer {
    private TextView mTextView;


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    mTextView.setClickable(false);
                    String text = mTextView.getText().toString();
                    SpannableStringUtil.setText(mTextView, 0, 2,
                            Color.parseColor("#FF6666"), text);
                    break;
                case 0x02:
                    mTextView.setText("点击重新获取验证码");
                    mTextView.setClickable(true);
                    mTextView.setTextColor(Color.parseColor("#42A2F9"));
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownUtil(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        mTextView = textView;
    }

    //倒计时期间使用
    @Override
    public void onTick(long millisUntilFinished) {
        Message message = mHandler.obtainMessage(0x01);
        mHandler.sendMessage(message);
        mTextView.setText(millisUntilFinished / 1000 + "s后可以重新获取");
    }

    //倒计时完成时候使用
    @Override
    public void onFinish() {
        Message message = mHandler.obtainMessage(0x02);
        mHandler.sendMessage(message);
    }
}
