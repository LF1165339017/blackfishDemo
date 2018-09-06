package lf.com.android.blackfishdemo.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

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
            case MotionEvent.ACTION_DOWN:
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
