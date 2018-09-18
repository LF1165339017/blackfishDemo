package lf.com.android.blackfishdemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnClassifyItemClickListener;

public class ClassifyTitleAdapter extends RecyclerView.Adapter<ClassifyTitleAdapter.TitleViewHolder> {
    private Context mContext;
    private ArrayList<String> mListTitle;
    private OnClassifyItemClickListener listener;

    public ClassifyTitleAdapter(Context mContext, ArrayList<String> mListTitle) {
        mListTitle = new ArrayList<>();
        this.mContext = mContext;
        this.mListTitle = mListTitle;
    }

    @NonNull
    @Override
    public TitleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TitleViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.view_classify_goods_title_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TitleViewHolder titleViewHolder, final int i) {
        if (i == 0) {
            titleViewHolder.mTextTitle.setTextColor(Color.parseColor("#FECD15"));
            titleViewHolder.mFrameLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        titleViewHolder.mTextTitle.setText(mListTitle.get(i));

        titleViewHolder.mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListTitle.size();
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextTitle;
        public FrameLayout mFrameLayout;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.tv_classify_goods_title_item);
            mFrameLayout = itemView.findViewById(R.id.f1_classify_layout);
        }
    }


    public void setListener(OnClassifyItemClickListener listener) {
        this.listener = listener;
    }
}
