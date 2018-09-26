package lf.com.android.blackfishdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.bean.BankCardsInfo;
import lf.com.android.blackfishdemo.listener.OnItemLayoutClickListener;

public class SelectBankCardAdapter extends RecyclerView.Adapter<SelectBankCardAdapter.MyHolder> {
    private Context mContext;
    private List<BankCardsInfo> mCardsInfos;
    private OnItemLayoutClickListener mClickListener;

    public SelectBankCardAdapter(Context mContext, List<BankCardsInfo> mCardsInfos) {
        this.mContext = mContext;
        this.mCardsInfos = mCardsInfos;
    }

    public void setmClickListener(OnItemLayoutClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_select_card_item_layout, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        String imageUrl = mCardsInfos.get(i).getLogoUrl();
        String cardName = mCardsInfos.get(i).getName();
        myHolder.mImageView.setImageURI(imageUrl);
        myHolder.mTextView.setText(cardName);
        myHolder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCardsInfos.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView mImageView;
        public TextView mTextView;
        public RelativeLayout mRelativeLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_select_card_icon);
            mTextView = itemView.findViewById(R.id.iv_select_card_text);
            mRelativeLayout = itemView.findViewById(R.id.rl_select_card);

        }
    }
}
