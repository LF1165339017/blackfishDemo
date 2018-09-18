package lf.com.android.blackfishdemo.bean;

import java.util.List;

public class GoodsDetailsInfo {
    private List<String> mBannerList;
    private double totalPrice;
    private double singlePrice;
    private int periods;
    private String desc;
    private String defaultType;
    private List<OptionalTypeInfo> mOptionalTypeInfos;
    private List<SimilarRecoInfo> mSimilarRecoInfos;

    public GoodsDetailsInfo(List<String> mBannerList, double totalPrice, double singlePrice, int periods, String desc, String defaultType, List<OptionalTypeInfo> mOptionalTypeInfos, List<SimilarRecoInfo> mSimilarRecoInfos) {
        this.mBannerList = mBannerList;
        this.totalPrice = totalPrice;
        this.singlePrice = singlePrice;
        this.periods = periods;
        this.desc = desc;
        this.defaultType = defaultType;
        this.mOptionalTypeInfos = mOptionalTypeInfos;
        this.mSimilarRecoInfos = mSimilarRecoInfos;
    }

    public List<String> getmBannerList() {
        return mBannerList;
    }

    public void setmBannerList(List<String> mBannerList) {
        this.mBannerList = mBannerList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(double singlePrice) {
        this.singlePrice = singlePrice;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType;
    }

    public List<OptionalTypeInfo> getmOptionalTypeInfos() {
        return mOptionalTypeInfos;
    }

    public void setmOptionalTypeInfos(List<OptionalTypeInfo> mOptionalTypeInfos) {
        this.mOptionalTypeInfos = mOptionalTypeInfos;
    }

    public List<SimilarRecoInfo> getmSimilarRecoInfos() {
        return mSimilarRecoInfos;
    }

    public void setmSimilarRecoInfos(List<SimilarRecoInfo> mSimilarRecoInfos) {
        this.mSimilarRecoInfos = mSimilarRecoInfos;
    }
}
