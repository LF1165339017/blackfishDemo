package lf.com.android.blackfishdemo.Fragment;

import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private boolean isVisible = false;
    private boolean isInitView = false;
    private boolean isFirstLoad = true;
    private View contentView;
    private SparseArray<View> mViews;

    public abstract void initView();

    public abstract void initdata();

    public abstract void lisetener(View view);

    public abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViews = new SparseArray<>();
        contentView = inflater.inflate(getLayoutId(), container, false);
        initView();
        isInitView = true;
        lazyLoad();
        return contentView;
    }

    @Override
    public void onClick(View v) {
        lisetener(v);
    }

    //懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }

    }

    private void lazyLoad() {
        if (!isFirstLoad || !isVisible || !isInitView) {
            //如果不是第一次加载、不是可见的、不是初始化View，则不加载数据
            return;
        }
        initdata();
        isFirstLoad = false;
    }


    public <E extends View> E findView(int ViewId) {
        if (contentView != null) {
            E view = (E) mViews.get(ViewId);
            if (view == null) {
                view = contentView.findViewById(ViewId);
                mViews.put(ViewId, view);
            }
            return view;
        }
        return null;
    }

}
