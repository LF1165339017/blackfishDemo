package lf.com.android.blackfishdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnSuperEditClickListener;
import lf.com.android.blackfishdemo.listener.OnSuperEditLayoutClickListener;
import lf.com.android.blackfishdemo.util.KeyBoardUtil;

public class SuperEditText extends RelativeLayout {
    private RelativeLayout mLayout;
    private ImageView mImageLeft, mImageRight;
    private TextView mTextView;
    private EditText mEditText;

    private int typeMode;

    private HashMap<String, String> mStringHashMap = new HashMap<>();

    private OnSuperEditClickListener mSuperListener;
    private OnSuperEditLayoutClickListener mClickLitener;

    public SuperEditText(Context context) {
        this(context, null);
    }

    public SuperEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        intView(context, attrs);
    }

    private void intView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_super_edit_text_layout, this);
        mLayout = view.findViewById(R.id.super_text_layout);
        mImageLeft = view.findViewById(R.id.iv_create_icon);
        mImageRight = view.findViewById(R.id.iv_create_to_right);
        mTextView = view.findViewById(R.id.tv_create_title);
        mEditText = view.findViewById(R.id.et_create_right);

        mLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mClickLitener) {
                    mClickLitener.onSuperEditClick(mTextView.getText().toString());
                    if (Build.VERSION.SDK_INT >= 26) {
                        if (mEditText.getFocusable() == FOCUSABLE) {
                            mEditText.requestFocus();
                            KeyBoardUtil.showKeyBoard(mEditText);
                        } else {
                            mEditText.setInputType(InputType.TYPE_NULL);
                            KeyBoardUtil.closeKeyBoard(mEditText);
                        }
                    } else {

                    }

                }
            }
        });


        mEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mSuperListener) {
                    mSuperListener.onSuperClick(mTextView.getText().toString());
                    if (Build.VERSION.SDK_INT >= 26) {
                        if (mEditText.getFocusable() == FOCUSABLE) {
                            mEditText.requestFocus();
                            KeyBoardUtil.showKeyBoard(mEditText);
                        } else {
                            mEditText.setInputType(InputType.TYPE_NULL);
                            KeyBoardUtil.closeKeyBoard(mEditText);
                        }
                    }

                }
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mStringHashMap.put(mTextView.getText().toString(), mEditText.getText().toString());
            }
        });


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SuperEditText);
        int drawableId = typedArray.getResourceId(R.styleable.SuperEditText_iconLeft, 0);
        String leftText = typedArray.getString(R.styleable.SuperEditText_titleText);
        String hintText = typedArray.getString(R.styleable.SuperEditText_hintTextColor);
        int typeMode = typedArray.getInteger(R.styleable.SuperEditText_typeMode, 0);
        int hintColor = typedArray.getColor(R.styleable.SuperEditText_hintTextColor, getResources().getColor(R.color.Black));

        /**
         * typeMode用于标识样式
         * 0-->右边为纯EditText
         * 1-->右边为纯TextView 无剪头
         * 2-->右边为TextView 有箭头
         * 3-->右边为TextView 有箭头，字体为黑色
         */
        mTextView.setText(leftText);
        mImageLeft.setImageResource(drawableId);
        mEditText.setHint(hintText);

        if (typeMode == 0) {

        } else if (typeMode == 1) {
            mEditText.setHintTextColor(hintColor);
            mEditText.setCursorVisible(false);
            mEditText.setClickable(true);
            mEditText.setFocusable(false);
            mEditText.setInputType(InputType.TYPE_NULL);
        } else if (typeMode == 2) {
            mEditText.setCursorVisible(false);
            mEditText.setClickable(true);
            mEditText.setFocusable(false);
            mEditText.setInputType(InputType.TYPE_NULL);
            mImageRight.setVisibility(VISIBLE);
        } else if (typeMode == 3) {
            mEditText.setCursorVisible(false);
            mEditText.setClickable(true);
            mEditText.setFocusable(false);
            mEditText.setInputType(InputType.TYPE_NULL);
            mImageRight.setVisibility(VISIBLE);
            mEditText.setHintTextColor(getResources().getColor(R.color.Black));
        }

        typedArray.recycle();
    }

    public HashMap<String, String> getmStringHashMap() {
        return mStringHashMap;
    }

    public void setmEditText(String text) {
        mEditText.setText(text);
    }

    public void setmSuperListener(OnSuperEditClickListener mSuperListener) {
        this.mSuperListener = mSuperListener;
    }

    public void setmClickLitener(OnSuperEditLayoutClickListener mClickLitener) {
        this.mClickLitener = mClickLitener;
    }

    public String getData() {
        if (typeMode == 0) {
            return mEditText.getText().toString();
        } else {
            return mEditText.getHint().toString();
        }
    }
}
