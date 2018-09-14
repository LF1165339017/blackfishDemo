package lf.com.android.blackfishdemo.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.bean.MallHotClassifyGridInfo;
import lf.com.android.blackfishdemo.listener.OnViewItemClickListener;
import lf.com.android.blackfishdemo.util.SpannableStringUtil;

public class MallHotClassifyGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<MallHotClassifyGridInfo> mMallHotClassifyGridInfos;
    private SpannableStringUtil mStringUtil = new SpannableStringUtil();

    public MallHotClassifyGridAdapter(Context mContext, List<MallHotClassifyGridInfo> mMallHotClassifyGridInfos) {
        this.mContext = mContext;
        this.mMallHotClassifyGridInfos = mMallHotClassifyGridInfos;
    }

    @Override
    public int getCount() {
        return mMallHotClassifyGridInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mMallHotClassifyGridInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.mall_pager_hot_classify_grid_item_layout, parent, false);
            myViewHolder.headerImage = convertView.findViewById(R.id.iv_hot_classify_goods_image);
            myViewHolder.mTextDesc = convertView.findViewById(R.id.tv_hot_classify_goods_desc);
            myViewHolder.mTextPeriods = convertView.findViewById(R.id.tv_hot_classify_goods_periods);
            myViewHolder.mTextPrice = convertView.findViewById(R.id.tv_hot_classify_goods_price);
            myViewHolder.mLinearLayout = convertView.findViewById(R.id.ll_goods_item);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        myViewHolder.headerImage.setImageURI(mMallHotClassifyGridInfos.get(position).getHeaderImageUrl());
        myViewHolder.mTextDesc.setText(mMallHotClassifyGridInfos.get(position).getGoodsDesc());
        myViewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnViewItemClickListener.onItemClick("HotGoodsItem");
            }
        });
        String string = mMallHotClassifyGridInfos.get(position).getGoodsPeriods();
        int spaceIndex = getFirstSpaceIndex(string.toCharArray());
        SpannableString spannableString = mStringUtil.setMallGoodsPrice(string, 0, spaceIndex);
        myViewHolder.mTextPeriods.setText(spannableString);
        myViewHolder.mTextPrice.setText(mMallHotClassifyGridInfos.get(position).getGoodPrice());

        return convertView;
    }

    private class MyViewHolder {
        private LinearLayout mLinearLayout;
        private SimpleDraweeView headerImage;
        private TextView mTextDesc;
        private TextView mTextPeriods;
        private TextView mTextPrice;
    }

    private int getFirstSpaceIndex(char[] text) {
        for (int i = 0; i < text.length; i++) {
            if (text[i] == ' ') {
                return i;
            }
        }
        return 0;
    }

    private OnViewItemClickListener mOnViewItemClickListener;

    public void setmOnViewItemClickListener(OnViewItemClickListener mOnViewItemClickListener) {
        this.mOnViewItemClickListener = mOnViewItemClickListener;
    }
}
