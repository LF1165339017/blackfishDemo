package lf.com.android.blackfishdemo.Fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.LinkedList;
import java.util.List;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.bean.BannerInfo;
import lf.com.android.blackfishdemo.bean.GridInfo;
import lf.com.android.blackfishdemo.bean.MallPagerInfo;
import lf.com.android.blackfishdemo.util.JsonUtil;
import lf.com.android.blackfishdemo.view.RecyclerViewBanner;

public class MallFragment extends BaseFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private Context mContext;

    private Toolbar mToolbar;
    private ImageView mIvMenu, mIvMsg;
    private RelativeLayout mHeaderLayout;
    private RecyclerViewBanner mBanner;
    private List<BannerInfo> mBannerInfos;

    private List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;

    private DelegateAdapter delegateAdapter;

    private SimpleDraweeView mImageGridItem;
    private TextView mTextGridItem;
    private JsonUtil mJsonUtil = new JsonUtil();
    private List<MallPagerInfo> mMallPagerInfos;
    private List<GridInfo> mGridInfos;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    public static MallFragment newInstance() {
        return new MallFragment();
    }

    @Override
    public void initView() {
        

    }

    @Override
    public void initdata() {

    }

    @Override
    public void lisetener(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nav_mall;
    }
}
