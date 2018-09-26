package lf.com.android.blackfishdemo.util;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cncoderx.wheelview.OnWheelChangedListener;
import com.cncoderx.wheelview.Wheel3DView;
import com.cncoderx.wheelview.WheelView;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnSelectFinshListener;

public class PickerUtil {
    private Dialog mDialog;
    private TextView mTextCancel, mTextDeterMine;
    private RecyclerView mRecyclerView;
    private Wheel3DView mWheel3Dview;


    public void showCustomPicker(Activity activity, int arrayId, final OnSelectFinshListener listener) {
        mDialog = new Dialog(activity, R.style.BottomDialogStyle);
        View view = LayoutInflater.from(activity).inflate(R.layout.view_custom_picker_layout, null);

        mTextCancel = view.findViewById(R.id.tv_picker_custom_cancel);
        mTextDeterMine = view.findViewById(R.id.tv_picker_custom_determine);
        mWheel3Dview = view.findViewById(R.id.wheel_view_picker);


        String[] arrayList = activity.getResources().getStringArray(arrayId);
        mWheel3Dview.setEntries(arrayList);
        mTextCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mWheel3Dview.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                listener.onSelected(view.getItem(newIndex).toString());
            }
        });

        mTextDeterMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mWheel3Dview.setOnWheelChangedListener(new OnWheelChangedListener() {
                    @Override
                    public void onChanged(WheelView view, int oldIndex, int newIndex) {
                        listener.onSelected(view.getItem(newIndex).toString());
                    }
                });
                mDialog.dismiss();
            }
        });

        mDialog.setContentView(view);
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DenistyUtil.getScreenWidth(activity);
        params.height = DenistyUtil.dip2px(activity, 300);
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        mDialog.show();

    }

}
