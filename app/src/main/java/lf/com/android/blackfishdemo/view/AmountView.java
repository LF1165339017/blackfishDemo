package lf.com.android.blackfishdemo.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnNumChangeListener;

public class AmountView extends LinearLayout implements View.OnClickListener {
    private EditText mEditText;
    private ImageView mImageReduce;
    private ImageView mImageAdd;
    private int maxNumber;
    private OnNumChangeListener listener;

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AmountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attributeSet) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_amount_layout, null);
        mEditText = view.findViewById(R.id.et_nub);
        mEditText.setOnClickListener(this);
        mEditText.setCursorVisible(false);
        mImageReduce = view.findViewById(R.id.iv_reduce);
        mImageReduce.setOnClickListener(this);
        mImageAdd = view.findViewById(R.id.iv_add);
        mImageAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int number = Integer.valueOf(mEditText.getText().toString());
        switch (v.getId()) {
            case R.id.iv_reduce:
                if (number > 1) {
                    number--;
                }
                break;
            case R.id.et_nub:
                break;
            case R.id.iv_add:
                if (number < maxNumber) {
                    number++;
                }
                break;
            default:
                break;
        }

        if (number == 1) {
            mImageReduce.setImageResource(R.drawable.icon_reduce_gray);
        } else if (number == maxNumber) {
            mImageAdd.setImageResource(R.drawable.icon_add_gray);
        } else {
            mImageReduce.setImageResource(R.drawable.icon_reduce_yellow);
            mImageAdd.setImageResource(R.drawable.icon_add_yellow);
        }

        String text = number + "";
        mEditText.setText(text);
        listener.onChange(number);
    }

    public void setOnNumChangeListener(OnNumChangeListener listener) {
        this.listener = listener;
    }
}
