package lf.com.android.blackfishdemo.bean;

import java.io.Serializable;
import java.util.List;

public class HomeSortInfo implements Serializable {
    private String title;//标题
    private String sortImageUrl;//分类图片Url
    private List<HomeSortItemfo> mItemfos;//分类商品信息

    public HomeSortInfo(String title, String sortImageUrl, List<HomeSortItemfo> mItemfos) {
        this.title = title;
        this.sortImageUrl = sortImageUrl;
        this.mItemfos = mItemfos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSortImageUrl() {
        return sortImageUrl;
    }

    public void setSortImageUrl(String sortImageUrl) {
        this.sortImageUrl = sortImageUrl;
    }

    public List<HomeSortItemfo> getmItemfos() {
        return mItemfos;
    }

    public void setmItemfos(List<HomeSortItemfo> mItemfos) {
        this.mItemfos = mItemfos;
    }
}

