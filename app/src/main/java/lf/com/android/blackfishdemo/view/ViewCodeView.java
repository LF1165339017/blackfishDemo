package lf.com.android.blackfishdemo.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.InputCompleterListener;
import lf.com.android.blackfishdemo.util.LogUtil;

public class ViewCodeView extends RelativeLayout {
    private EditText mEditText;
    private TextView[] mTextViews;
    private View[] mViews;
    private static int Max = 6;
    private String inputContext;
    private InputCompleterListener listener;

    public ViewCodeView(Context context) {
        super(context);
        initView(context);
    }

    public ViewCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.fragment_ssmdk, this);
        mTextViews = new TextView[Max];
        mViews = new View[Max];
        mTextViews[0] = findViewById(R.id.tv_code1);
        mTextViews[1] = findViewById(R.id.tv_code2);
        mTextViews[2] = findViewById(R.id.tv_code3);
        mTextViews[3] = findViewById(R.id.tv_code4);
        mTextViews[4] = findViewById(R.id.tv_code5);
        mTextViews[5] = findViewById(R.id.tv_code6);
        mViews[0] = findViewById(R.id.view1);
        mViews[1] = findViewById(R.id.view2);
        mViews[2] = findViewById(R.id.view3);
        mViews[3] = findViewById(R.id.view4);
        mViews[4] = findViewById(R.id.view5);
        mViews[5] = findViewById(R.id.view6);
        mEditText = findViewById(R.id.et_code_text);
        mEditText.setCursorVisible(false);
        setEditTextLitener();
    }

    private void setEditTextLitener() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputContext = mEditText.getText().toString();
                if (listener != null) {
                    if (inputContext.length() >= Max) {
                        listener.inputComplete();
                    } else {
                        listener.invalidContent();
                    }
                }

                for (int i = 0; i < Max; i++) {
                    if (i < inputContext.length()) {
                        mTextViews[i].setText(String.valueOf(inputContext.charAt(i)));
                    } else {
                        mTextViews[i].setText("");
                    }
                }

            }
        });
    }

    public void setListener(InputCompleterListener listener) {
        this.listener = listener;
    }

    public String getInputContext() {
        return inputContext;
    }

    public void setEditContentEmpty() {
        mEditText.setText("");
    }


}
