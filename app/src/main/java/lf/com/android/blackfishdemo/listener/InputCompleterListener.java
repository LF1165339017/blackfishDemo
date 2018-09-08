package lf.com.android.blackfishdemo.listener;

import android.widget.EditText;

public interface InputCompleterListener {
    void inputComplete(EditText view);

    void invalidContent();
}
