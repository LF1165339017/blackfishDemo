package lf.com.android.blackfishdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;

public class GeneralVLayoutAdapter extends DelegateAdapter.Adapter<GeneralVLayoutAdapter.MainViewHolder> {
    private Context mContext;
    private LayoutHelper helper;
    private VirtualLayoutManager.LayoutParams params;
    private int mCount = 0;

    public GeneralVLayoutAdapter(Context context, LayoutHelper helper,
                                 VirtualLayoutManager.LayoutParams params, int count) {
        mContext = context;
        this.helper = helper;
        this.params = params;
        mCount = count;
    }

    public GeneralVLayoutAdapter(Context context, LayoutHelper helper, int count) {
        this(context, helper, null, count);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return helper;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
        if (params != null) {
            mainViewHolder.itemView.setLayoutParams(new VirtualLayoutManager.LayoutParams(params));
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
