package lf.com.android.blackfishdemo.util;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import lf.com.android.blackfishdemo.R;

public class ToastUtil {

    private static final String PROMPT = "提示";
    private static final String WARING = "警告";
    private static final String ERROR = "错误";
    private static View view;
    private static Toast mToast;
    private static TextView mTv_toastPrompt, mTv_toastMessage;

    /**
     * 使用自定义Toast控件并居中显示
     *
     * @param mContext
     * @param toastPrompt  提示类别
     * @param toastMessage 提示信息
     * @param duration
     * @return
     */
    public static Toast setMyToast(Context mContext, String toastPrompt,
                                   String toastMessage, int duration) {
        mToast = new Toast(mContext);
        view = getView(mContext);
        mTv_toastPrompt = view.findViewById(R.id.toast_message);
        mTv_toastMessage = view.findViewById(R.id.toast_message_hint);
        switch (toastPrompt) {
            case PROMPT:
                mTv_toastPrompt.setTextColor(Color.parseColor("#707061"));
                break;
            case WARING:
                mTv_toastPrompt.setTextColor(Color.parseColor("#FF5A34"));
                break;
            case ERROR:
                mTv_toastPrompt.setTextColor(Color.parseColor("#EF1F1F"));
                break;
            default:
                break;
        }
        mTv_toastMessage.setText(toastMessage);
        mToast.setView(view);
        mToast.setDuration(duration);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        return mToast;
    }

    public static Toast setToastNormal(Context mContext, String text, int duration) {
        mToast = new Toast(mContext);
        mToast.setText(text);
        mToast.setDuration(duration);
        return mToast;
    }

    private static View getView(Context mContext) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.toast_view_layout, null);
        return view;
    }


}
