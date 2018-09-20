package lf.com.android.blackfishdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.bean.SimilarRecoInfo;

public class SimilarRecoAdapter extends BaseAdapter {
    private Context mContext;
    private List<SimilarRecoInfo> mSimilarRecoInfos;

    public SimilarRecoAdapter(Context mContext, List<SimilarRecoInfo> mSimilarRecoInfos) {
        mSimilarRecoInfos = new ArrayList<>();
        this.mContext = mContext;
        this.mSimilarRecoInfos = mSimilarRecoInfos;
    }

    @Override
    public int getCount() {
        return mSimilarRecoInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mSimilarRecoInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.goods_details_similar_reco, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SimilarRecoInfo similarRecoInfo = mSimilarRecoInfos.get(position);
        viewHolder.draweeView.setImageURI(similarRecoInfo.getImageUrl());
        viewHolder.mTvDesc.setText(similarRecoInfo.getDesc());
        String singlePrice = "￥" + similarRecoInfo.getSinglePrice() + " x" + similarRecoInfo.getPeriods() + " 期";
        viewHolder.mTvSinglePrice.setText(singlePrice);
        String price = "￥" + similarRecoInfo.getTotalPrice();
        viewHolder.mTvPrice.setText(price);
        return convertView;
    }


    class ViewHolder {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.iv_goods)
        SimpleDraweeView draweeView;
        @BindView(R.id.tv_desc)
        TextView mTvDesc;
        @BindView(R.id.tv_single_price)
        TextView mTvSinglePrice;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
    }


}
