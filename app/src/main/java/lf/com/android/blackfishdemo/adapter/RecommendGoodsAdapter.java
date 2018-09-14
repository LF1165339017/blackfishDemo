package lf.com.android.blackfishdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.bean.RecommendGoodsInfo;
import lf.com.android.blackfishdemo.util.SpannableStringUtil;

public class RecommendGoodsAdapter extends RecyclerView.Adapter<RecommendGoodsAdapter.MyViewHolder> {
    private Context mContext;
    private List<RecommendGoodsInfo> mGoodsInfos;
    private SpannableStringUtil mStringUtil;

    public RecommendGoodsAdapter(Context mContext, List<RecommendGoodsInfo> mGoodsInfos) {
        mStringUtil = new SpannableStringUtil();
        this.mContext = mContext;
        this.mGoodsInfos = mGoodsInfos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(
                R.layout.mall_pager_recommend_goods_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        RecommendGoodsInfo goodsInfo = mGoodsInfos.get(i);
        myViewHolder.mDraweeView.setImageURI(goodsInfo.getImageUrl());
        myViewHolder.mTextDesc.setText(goodsInfo.getDesc());
        String price = "￥" + goodsInfo.getSinglePrice() + " x" + goodsInfo.getPeriods() + "期" + "+  ￥" + goodsInfo.getTotalPrice();
        SpannableString spannableString = mStringUtil.setMallGoodsPrice(price, 0, getFirstSpaceIndex(price.toCharArray()));
        myViewHolder.mTextPrice.setText(spannableString);
        String evaluation = "好评率:" + goodsInfo.getEvaluation();
        myViewHolder.mTextEvaluation.setText(evaluation);
    }

    @Override
    public int getItemCount() {
        return mGoodsInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView mDraweeView;
        private TextView mTextDesc;
        private TextView mTextPrice;
        private TextView mTextEvaluation;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mDraweeView = itemView.findViewById(R.id.iv_mall_recommend_goods_image);
            mTextDesc = itemView.findViewById(R.id.tv_mall_recommend_goods_desc);
            mTextPrice = itemView.findViewById(R.id.tv_mall_recommend_goods_price);
            mTextEvaluation = itemView.findViewById(R.id.tv_mall_recommend_goods_evaluation);
        }
    }

    private int getFirstSpaceIndex(char[] text) {
        for (int i = 0; i < text.length; i++) {
            if (text[i] == ' ') {
                return i;
            }
        }
        return 0;
    }
}
