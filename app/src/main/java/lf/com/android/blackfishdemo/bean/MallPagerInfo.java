package lf.com.android.blackfishdemo.bean;

import java.util.List;

public class MallPagerInfo {
    private List<BannerInfo> mBannerInfos;
    private List<GridInfo> mClassifyInfo;
    private String singleImageUrl;
    private List<BannerInfo> mGridGoodsInfos;
    private List<MallGoodsInfo> mMallGoodsInfos;
    private List<RecommendGoodsInfo> mRecommendGoodsInfos;

    public MallPagerInfo(List<BannerInfo> mBannerInfos, List<GridInfo> mClassifyInfo, String singleImageUrl, List<BannerInfo> mGridGoodsInfos, List<MallGoodsInfo> mMallGoodsInfos, List<RecommendGoodsInfo> mRecommendGoodsInfos) {
        this.mBannerInfos = mBannerInfos;
        this.mClassifyInfo = mClassifyInfo;
        this.singleImageUrl = singleImageUrl;
        this.mGridGoodsInfos = mGridGoodsInfos;
        this.mMallGoodsInfos = mMallGoodsInfos;
        this.mRecommendGoodsInfos = mRecommendGoodsInfos;
    }

    public List<BannerInfo> getmBannerInfos() {
        return mBannerInfos;
    }

    public void setmBannerInfos(List<BannerInfo> mBannerInfos) {
        this.mBannerInfos = mBannerInfos;
    }

    public List<GridInfo> getmClassifyInfo() {
        return mClassifyInfo;
    }

    public void setmClassifyInfo(List<GridInfo> mClassifyInfo) {
        this.mClassifyInfo = mClassifyInfo;
    }

    public String getSingleImageUrl() {
        return singleImageUrl;
    }

    public void setSingleImageUrl(String singleImageUrl) {
        this.singleImageUrl = singleImageUrl;
    }

    public List<BannerInfo> getmGridGoodsInfos() {
        return mGridGoodsInfos;
    }

    public void setmGridGoodsInfos(List<BannerInfo> mGridGoodsInfos) {
        this.mGridGoodsInfos = mGridGoodsInfos;
    }

    public List<MallGoodsInfo> getmMallGoodsInfos() {
        return mMallGoodsInfos;
    }

    public void setmMallGoodsInfos(List<MallGoodsInfo> mMallGoodsInfos) {
        this.mMallGoodsInfos = mMallGoodsInfos;
    }

    public List<RecommendGoodsInfo> getmRecommendGoodsInfos() {
        return mRecommendGoodsInfos;
    }

    public void setmRecommendGoodsInfos(List<RecommendGoodsInfo> mRecommendGoodsInfos) {
        this.mRecommendGoodsInfos = mRecommendGoodsInfos;
    }
}
