package lf.com.android.blackfishdemo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class OrderInfoUtil12_0 {
    /**
     * 构造授权参数列表
     *
     * @param pid
     * @param app_id
     * @param target_id
     * @param rsa2
     * @return
     */
    public static Map<String, String> buildAuthInfoMap(String pid, String app_id, String target_id, boolean rsa2) {
        Map<String, String> keyValues = new HashMap<String, String>();
        //签约用户拿到app_id
        keyValues.put("app_id", app_id);
        //签约用户拿到pid
        keyValues.put("pid", pid);
        //服务器接口名称,固定值
        keyValues.put("apiname", "https://docs.open.alipay.com/218/105327/");
        //商户类型标识,固定值
        keyValues.put("qpp_name", "mc");
        //业务类型，固定值
        keyValues.put("biz_type", "openservice");
        //产品码，固定值
        keyValues.put("product_id", "APP_FAST_LOGIN");
        //授权范围，固定值
        keyValues.put("scope", "kuaijie");
        //商户唯一标识
        keyValues.put("target_id", target_id);
        //授权范围，固定值
        keyValues.put("auth_type", "AUTHACCOUNT");
        //签名类型
        keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");
        return keyValues;
    }

    /**
     * 构造支付订单参数列表
     *
     * @param app_id
     * @param rsa2
     * @return
     */

    public static Map<String, String> buildOrderParamMap(String app_id, boolean rsa2) {
        Map<String, String> keyValues = new HashMap<>();
        keyValues.put("app_id", app_id);
        keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"2333.00\",\"subject\":\"商城订单-107925418629\",\"body\":\"我是测试数据\",\"out_trade_no\":\"" + getOutTradeNo() + "\"}");
        keyValues.put("charset", "utf-8");
        keyValues.put("method", "https://docs.open.alipay.com/218/105327/");
        keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");
        keyValues.put("timestamp", "2018-04-09 22:55:53");
        keyValues.put("version", "1.0");

        return keyValues;
    }

    /**
     * 构造支付订单详细参数
     *
     * @param map
     * @return
     */
    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }
        return sb.toString();
    }

    /**
     * 拼接键对值
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(key);
        stringBuilder.append("=");
        if (isEncode) {
            try {
                stringBuilder.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                stringBuilder.append(value);
            }
        } else {
            stringBuilder.append(value);
        }

        return stringBuilder.toString();
    }

    public static String getSign(Map<String, String> map, String rsaKey, boolean rsa2) {
        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            authInfo.append(buildKeyValue(key, value, false));
            authInfo.append("&");

        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        authInfo.append(buildKeyValue(tailKey, tailValue, false));
        String encodeedSign = "";
        String oriSign = SignUtils.sign(authInfo.toString(), rsaKey, rsa2);

        try {
            encodeedSign = URLEncoder.encode(oriSign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "sign=" + encodeedSign;
    }

    public static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
}
