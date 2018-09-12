package lf.com.android.blackfishdemo.util;

import java.io.IOException;

import lf.com.android.blackfishdemo.listener.OnNetResultListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpUtil {
    private static OkHttpUtil okHttpUtil = null;
    private OkHttpClient mHttpClient;
    private Request mRequest;

    public OkHttpUtil() {

    }

    public static OkHttpUtil getInstance() {
        if (okHttpUtil == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpUtil == null) {
                    okHttpUtil = new OkHttpUtil();
                }
            }
        }
        return okHttpUtil;
    }

    public void startGet(String url, final OnNetResultListener listener) {
        mHttpClient = new OkHttpClient();
        mRequest = new Request.Builder().url(url).build();
        mHttpClient.newCall(mRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.OnFailureListener(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.OnSuccessListener(response.body().string());
            }
        });
    }

    public void startPost(String url, String phone, String username, String password, String data,
                          final OnNetResultListener listener) {
        mHttpClient = new OkHttpClient();
        final RequestBody requestBody = new FormBody.Builder()
                .add("phone", phone)
                .add("username", username)
                .add("password", password)
                .add("regdate", data)
                .build();

        mRequest = new Request.Builder().url(url).post(requestBody).build();
        mHttpClient.newCall(mRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.OnFailureListener(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.OnSuccessListener(response.body().string());
            }
        });
    }

}
