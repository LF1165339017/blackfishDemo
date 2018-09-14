package lf.com.android.blackfishdemo.bean;

public class MallHotClassifyGridInfo {
    private String headerImageUrl;
    private String goodsDesc;
    private String goodsPeriods;
    private String goodPrice;

    public MallHotClassifyGridInfo(String headerImageUrl, String goodsDesc, String goodsPeriods, String goodPrice) {
        this.headerImageUrl = headerImageUrl;
        this.goodsDesc = goodsDesc;
        this.goodsPeriods = goodsPeriods;
        this.goodPrice = goodPrice;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public String getGoodsPeriods() {
        return goodsPeriods;
    }

    public void setGoodsPeriods(String goodsPeriods) {
        this.goodsPeriods = goodsPeriods;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }
}
