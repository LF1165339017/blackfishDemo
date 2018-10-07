package lf.com.android.blackfishdemo.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lf.com.android.blackfishdemo.bean.BankCardsInfo;
import lf.com.android.blackfishdemo.bean.BannerInfo;
import lf.com.android.blackfishdemo.bean.ClassifyGoodsInfo;
import lf.com.android.blackfishdemo.bean.ClassifyGridInfo;
import lf.com.android.blackfishdemo.bean.GoodsDetailsInfo;
import lf.com.android.blackfishdemo.bean.GridInfo;
import lf.com.android.blackfishdemo.bean.HomeSortInfo;
import lf.com.android.blackfishdemo.bean.HomeSortItemfo;
import lf.com.android.blackfishdemo.bean.MallGoodsInfo;
import lf.com.android.blackfishdemo.bean.MallGoodsItemInfo;
import lf.com.android.blackfishdemo.bean.MallPagerInfo;
import lf.com.android.blackfishdemo.bean.OptionalTypeInfo;
import lf.com.android.blackfishdemo.bean.RecommendGoodsInfo;
import lf.com.android.blackfishdemo.bean.SimilarRecoInfo;

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
        try {
            if (type == HOME_COODS_INFO) {
                //首页的商品信息
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
                LogUtil.d("LF1234", "homeSortInfos" + homeSortInfos);
                return homeSortInfos;
            } else if (type == BANK_CARD_INFO) {
                List<BankCardsInfo> bankCardsInfos = new ArrayList<>();
                mJsonArray = new JSONArray(json);
                mJsonObject = (JSONObject) mJsonArray.get(0);
                JSONArray jsonArray = mJsonObject.getJSONArray("bank_list");
                JSONObject jsonObject = null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = (JSONObject) jsonArray.get(i);
                    String abbr = jsonObject.getString("abbr");
                    String name = jsonObject.getString("name");
                    String logo_url = jsonObject.getString("logo_uri");
                    bankCardsInfos.add(new BankCardsInfo(logo_url, name, abbr));
                }
                return bankCardsInfos;

            } else if (type == CLASSIFY_GOODS_INFO) {
                List<ClassifyGoodsInfo> classifyGoodsInfos = new ArrayList<>();
                mJsonObject = new JSONObject(json);
                mJsonArray = mJsonObject.getJSONArray("classifyTitle");
                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) mJsonArray.get(i);
                    String title = jsonObject.getString("title");
                    String headerImageUrl = jsonObject.getString("headerImageUrl");
                    String subtitle1 = jsonObject.getString("subTitle1");
                    String subtitle2 = jsonObject.getString("suTitle");
                    JSONArray jsonArray = ((JSONObject) mJsonArray.get(i)).getJSONArray("gridImageUrls1");

                    List<ClassifyGridInfo> mGridInfos1 = new ArrayList<>();
                    List<ClassifyGridInfo> mGridInfos2 = new ArrayList<>();

                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(j);
                        int id = jsonObject1.getInt("id");
                        String desc = jsonObject1.getString("desc");
                        String imageUrl = jsonObject1.getString("iamgeUrl");
                        mGridInfos1.add(new ClassifyGridInfo(id, desc, imageUrl));
                    }
                    JSONArray jsonArray1 = ((JSONObject) mJsonArray.get(i)).getJSONArray("gridImageUrls2");
                    for (int j = 0; j < jsonArray1.length(); j++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray1.get(j);
                        int id = jsonObject1.getInt("id");
                        String desc = jsonObject1.getString("desc");
                        String imageUrl = jsonObject1.getString("iamgeUrl");
                        mGridInfos2.add(new ClassifyGridInfo(id, desc, imageUrl));
                    }
                    classifyGoodsInfos.add(new ClassifyGoodsInfo(title, headerImageUrl, subtitle1, subtitle2, mGridInfos1, mGridInfos2));
                }
                return classifyGoodsInfos;
            } else if (type == MALL_GOODS_INFO) {
                List<MallPagerInfo> mallPagerInfos = new ArrayList<>();
                List<BannerInfo> bannerInfos = new ArrayList<>();
                List<GridInfo> gridInfos = new ArrayList<>();
                List<BannerInfo> goodsInfos = new ArrayList<>();
                List<MallGoodsInfo> mallGoodsInfos = new ArrayList<>();
                mJsonObject = new JSONObject(json);

                String singleImageUrl = mJsonObject.getString("single_image");
                mJsonArray = mJsonObject.getJSONArray("banners");
                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) mJsonArray.get(i);
                    bannerInfos.add(new BannerInfo(jsonObject.getString("banner_url")));
                }
                JSONArray jsonArrayGrid = mJsonObject.getJSONArray("classifyGridItems");
                for (int i = 0; i < jsonArrayGrid.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArrayGrid.get(i);
                    gridInfos.add(new GridInfo(
                            jsonObject.getString("desc"),
                            jsonObject.getString("grid_url")));
                }

                JSONArray jsonArrayGoods = mJsonObject.getJSONArray("four_goods_image");
                for (int i = 0; i < jsonArrayGoods.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArrayGoods.get(i);
                    goodsInfos.add(new BannerInfo(jsonObject.getString("four_image_url")));
                }
                JSONArray jsonArrayHotSorts = mJsonObject.getJSONArray("hotSort");
                for (int i = 0; i < jsonArrayHotSorts.length(); i++) {
                    List<MallGoodsItemInfo> mallGoodsItemInfos = new ArrayList<>();
                    JSONObject jsonObject = (JSONObject) jsonArrayHotSorts.get(i);
                    String headerImageUrl = jsonObject.getString("headerBigImage");
                    JSONArray jsonArray = jsonObject.getJSONArray("threeGoods");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(j);
                        mallGoodsItemInfos.add(new MallGoodsItemInfo(
                                jsonObject1.getString("goodsItemImage"),
                                jsonObject1.getString("desc"),
                                jsonObject1.getDouble("singePrice"),
                                jsonObject1.getInt("numPeriods"),
                                jsonObject1.getDouble("price")));
                    }
                    mallGoodsInfos.add(new MallGoodsInfo(headerImageUrl, mallGoodsItemInfos));
                }
                JSONArray jsonArrayReco = mJsonObject.getJSONArray("recommends_goods");
                List<RecommendGoodsInfo> recommendGoodsInfoList = new ArrayList<>();
                for (int i = 0; i < jsonArrayReco.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArrayReco.get(i);
                    recommendGoodsInfoList.add(new RecommendGoodsInfo(
                            jsonObject.getString("imageUrl"),
                            jsonObject.getString("desc"),
                            jsonObject.getDouble("singlePrice"),
                            jsonObject.getInt("periods"),
                            jsonObject.getDouble("totalPrice"),
                            jsonObject.getString("rate")));
                }
                mallPagerInfos.add(new MallPagerInfo(
                        bannerInfos,
                        gridInfos,
                        singleImageUrl,
                        goodsInfos,
                        mallGoodsInfos,
                        recommendGoodsInfoList));
                return mallPagerInfos;
            } else if (type == GOODS_DETAILS_INFO) {
                List<GoodsDetailsInfo> detailsInfos = new ArrayList<>();
                List<String> bannerList = new ArrayList<>();
                mJsonObject = new JSONObject(json);
                mJsonArray = mJsonObject.getJSONArray("bannerUrls");
                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) mJsonArray.get(i);
                    String bannerUrl = jsonObject.getString("imageUrl");
                    bannerList.add(bannerUrl);
                }
                double totalPrice = mJsonObject.getDouble("totalPrice");
                double singlePrice = mJsonObject.getDouble("singlePrice");
                int periods = mJsonObject.getInt("periods");
                String desc = mJsonObject.getString("desc");
                String defaultType = mJsonObject.getString("defaultType");
                List<OptionalTypeInfo> optionalTypeInfos = new ArrayList<>();
                List<SimilarRecoInfo> similarRecoInfos = new ArrayList<>();
                JSONArray jsonArrayOptional = mJsonObject.getJSONArray("optionalType");
                for (int i = 0; i < jsonArrayOptional.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArrayOptional.get(i);
                    optionalTypeInfos.add(new OptionalTypeInfo(jsonObject.getString("type"), jsonObject.getDouble("totalPrice"), jsonObject.getDouble("singlePrice")));
                }
                JSONArray jsonArraySimilar = mJsonObject.getJSONArray("similarRecommend");
                for (int i = 0; i < jsonArraySimilar.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArraySimilar.get(i);
                    similarRecoInfos.add(new SimilarRecoInfo(jsonObject.getString("imageUrl"), jsonObject.getString("desc"), jsonObject.getDouble("totalPrice"), jsonObject.getDouble("singlePrice"), jsonObject.getInt("periods")));
                }
                detailsInfos.add(new GoodsDetailsInfo(bannerList, totalPrice, singlePrice, periods, desc, defaultType, optionalTypeInfos, similarRecoInfos));
                return detailsInfos;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}