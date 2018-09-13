package lf.com.android.blackfishdemo.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lf.com.android.blackfishdemo.bean.HomeSortInfo;
import lf.com.android.blackfishdemo.bean.HomeSortItemfo;

public class JsonUtil {
    private static final int HOME_COODS_INFO = 0;
    private static final int BANK_CARD_INFO = 1;
    private static final int CLASSIFY_GOODS_INFO = 2;
    private static final int MALL_GOODS_INFO = 3;
    private static final int GOODS_DETAILS_INFO = 4;

    public List getDataFromJson(String json, int type) {
        List<HomeSortInfo> homeSortInfos = new ArrayList<>();
        List<HomeSortItemfo> homeSortItemfos = new ArrayList<>();
        JSONObject mJsonObject;
        JSONArray mJsonArray;
        if (type == HOME_COODS_INFO) {
            //首页的商品信息
            try {
                mJsonObject = new JSONObject(json);
                mJsonArray = mJsonObject.getJSONArray("home_sort");
                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) mJsonArray.get(i);
                    String title = jsonObject.getString("title");
                    String sortImageUrl = jsonObject.getString("sortImageUrl");
                    JSONArray jsonArray = jsonObject.getJSONArray("goods");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(j);
                        String id = jsonObject1.getString("id");
                        String goodsImageUrl = jsonObject1.getString("goodsImageUrl");
                        homeSortItemfos.add(new HomeSortItemfo(id, goodsImageUrl));
                    }
                    homeSortInfos.add(new HomeSortInfo(title, sortImageUrl, homeSortItemfos));
                }
                return homeSortInfos;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
