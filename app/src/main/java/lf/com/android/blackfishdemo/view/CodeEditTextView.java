package lf.com.android.blackfishdemo.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 验证码控件，去掉传统EditText双击选中EditText的内容
 * 和去掉光标位置会随点击改变
 */
public class CodeEditTextView extends AppCompatEditText {
    private long lastTime = 0;

    public CodeEditTextView(Context context) {
        super(context);
    }

    public CodeEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CodeEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        this.setSelection(this.getText().length());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://判断点击事件，防止用户多次点击
                long currenTime = System.currentTimeMillis();
                if (currenTime - lastTime < 500) {
                    lastTime = currenTime;
                    return true;
                } else {
                    lastTime = currenTime;
                }
                break;


        }
        return super.onTouchEvent(event);
    }
}
