package lf.com.android.blackfishdemo.bean;

public class HomeSortItemfo {
    private String id;
    private String goodsImageUrl;

    public HomeSortItemfo(String id, String goodsImageUrl) {
        this.id = id;
        this.goodsImageUrl = goodsImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }
}
