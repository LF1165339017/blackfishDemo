package lf.com.android.blackfishdemo.AlertDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.Ondialoglistener;
import lf.com.android.blackfishdemo.util.SpannableStringUtil;

public class MyAlertDialogFragment extends DialogFragment implements View.OnClickListener {
    private Context mContext;
    private TextView mTv_dialog_message, mTv_losePassword, mTv_Reset;
    private Ondialoglistener listener;
    private View view;
    private Dialog mDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        view = LayoutInflater.from(mContext).inflate(R.layout.passworderrordialog, null);
        mDialog = new Dialog(mContext, R.style.PassWordDialog);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(500, 350);
        mDialog.addContentView(view, layoutParams);
        intitView();
        initData();
        return mDialog;
    }

    private void initData() {
        SpannableStringUtil.setText(mTv_dialog_message, 0, 4,
                R.color.colorRedEF1F1F, mTv_dialog_message.getText().toString());
    }

    private void intitView() {
        mTv_dialog_message = view.findViewById(R.id.dialog_messageTitle);
        mTv_losePassword = view.findViewById(R.id.Tv_lose_password);
        mTv_Reset = view.findViewById(R.id.Tv_Reset);

        mTv_dialog_message.setOnClickListener(this);
        mTv_losePassword.setOnClickListener(this);
        mTv_Reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_messageTitle:
                break;
            case R.id.Tv_Reset:
                dismiss();
                break;
            case R.id.tv_user_losePassword:
                listener.OnClick(mTv_losePassword);
                break;
            default:
                break;

        }
    }

    public void setOnLosePassword(Ondialoglistener listener) {
        this.listener = listener;
    }
}
