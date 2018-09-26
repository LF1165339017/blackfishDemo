package lf.com.android.blackfishdemo.util;

import android.text.TextUtils;

import java.util.Map;

public class PayResult {
    private String resultStauts;
    private String result;
    private String memo;

    public PayResult(Map<String, String> rawResult) {
        if (rawResult == null) {
            return;
        }
        for (String key : rawResult.keySet()) {
            if (TextUtils.equals(key, "resultStatus")) {
                resultStauts = rawResult.get(key);
            } else if (TextUtils.equals(key, "result")) {
                result = rawResult.get(key);
            } else if (TextUtils.equals(key, "memo")) {
                memo = rawResult.get(key);
            }
        }
    }

    @Override
    public String toString() {
        return "resultStatus = {" + resultStauts + "};memo={" + memo + "};result ={" + result + "}";
    }

    public String getResultStauts() {
        return resultStauts;
    }

    public String getResult() {
        return result;
    }

    public String getMemo() {
        return memo;
    }
}
