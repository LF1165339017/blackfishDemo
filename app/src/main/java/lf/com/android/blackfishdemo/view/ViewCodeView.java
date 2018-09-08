package lf.com.android.blackfishdemo.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    private Handler mHandler;

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

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 0x01:
                        mViews[0].setBackgroundColor(Color.parseColor("#FFCD15"));
                        mViews[1].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[2].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[3].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[4].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[5].setBackgroundColor(Color.parseColor("#707061"));
                        break;
                    case 0x02:
                        mViews[0].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[1].setBackgroundColor(Color.parseColor("#FFCD15"));
                        mViews[2].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[3].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[4].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[5].setBackgroundColor(Color.parseColor("#707061"));
                        break;
                    case 0x03:
                        mViews[0].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[1].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[2].setBackgroundColor(Color.parseColor("#FFCD15"));
                        mViews[3].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[4].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[5].setBackgroundColor(Color.parseColor("#707061"));
                        break;
                    case 0x04:
                        mViews[0].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[1].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[2].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[3].setBackgroundColor(Color.parseColor("#FFCD15"));
                        mViews[4].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[5].setBackgroundColor(Color.parseColor("#707061"));
                        break;
                    case 0x05:
                        mViews[0].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[1].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[2].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[3].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[4].setBackgroundColor(Color.parseColor("#FFCD15"));
                        mViews[5].setBackgroundColor(Color.parseColor("#707061"));
                        break;
                    case 0x06:
                        mViews[0].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[1].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[2].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[3].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[4].setBackgroundColor(Color.parseColor("#707061"));
                        mViews[5].setBackgroundColor(Color.parseColor("#FFCD15"));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
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
                        listener.inputComplete(mEditText);

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

                ChangeType(s.length());
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


    private void ChangeType(int length) {
        if (length < Max + 1) {
            switch (length) {
                case 1:
                    Message message1 = mHandler.obtainMessage(0x01);
                    mHandler.sendMessage(message1);
                    break;
                case 2:
                    Message message2 = mHandler.obtainMessage(0x02);
                    mHandler.sendMessage(message2);
                    break;
                case 3:
                    Message message3 = mHandler.obtainMessage(0x03);
                    mHandler.sendMessage(message3);
                    break;
                case 4:
                    Message message4 = mHandler.obtainMessage(0x04);
                    mHandler.sendMessage(message4);
                    break;
                case 5:
                    Message message5 = mHandler.obtainMessage(0x05);
                    mHandler.sendMessage(message5);
                    break;
                case 6:
                    Message message6 = mHandler.obtainMessage(0x06);
                    mHandler.sendMessage(message6);
                    break;
                default:
                    break;
            }
        }

    }

    public EditText getmEditText() {
        return mEditText;
    }
}
