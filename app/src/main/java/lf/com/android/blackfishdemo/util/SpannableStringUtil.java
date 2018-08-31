package lf.com.android.blackfishdemo.util;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

public class SpannableStringUtil {
    private static SpannableString mSpannableString;

    public static void setText(TextView textView, int start, int end, int color, String text) {
        mSpannableString = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        mSpannableString.setSpan(span, start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(mSpannableString);
    }

    public static SpannableString setText(String text, int start, int end, int color) {
        mSpannableString = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        mSpannableString.setSpan(span, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return mSpannableString;
    }

    public static void setRelativeSizeText(TextView textView, int start, int end,
                                           int color, float relativeSize,
                                           int relstart, int relend, String text) {
        mSpannableString = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(relativeSize);
        mSpannableString.setSpan(span, start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        mSpannableString.setSpan(relativeSize, relstart, relend, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(mSpannableString);
    }

    public static SpannableString setRelativeSizeText(String text, int start, int end,
                                                      float relativeSize,
                                                      int relstart, int relend, int color) {
        mSpannableString = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(relativeSize);
        mSpannableString.setSpan(sizeSpan, relstart, relend, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mSpannableString.setSpan(span, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return mSpannableString;
    }
}
