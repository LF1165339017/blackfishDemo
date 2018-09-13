package lf.com.android.blackfishdemo.bean;

import java.util.List;

public class MallGoodsInfo {
    private String headImageUrl;
    private List<MallGoodsItemInfo> mMallGoodsItemInfos;

    public MallGoodsInfo(String headImageUrl, List<MallGoodsItemInfo> mMallGoodsItemInfos) {
        this.headImageUrl = headImageUrl;
        this.mMallGoodsItemInfos = mMallGoodsItemInfos;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public List<MallGoodsItemInfo> getmMallGoodsItemInfos() {
        return mMallGoodsItemInfos;
    }

    public void setmMallGoodsItemInfos(List<MallGoodsItemInfo> mMallGoodsItemInfos) {
        this.mMallGoodsItemInfos = mMallGoodsItemInfos;
    }
}
