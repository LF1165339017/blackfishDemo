package lf.com.android.blackfishdemo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPhonePassword {
    public static boolean isPhonePassword(String password) {
        Pattern p = Pattern
                .compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

}
