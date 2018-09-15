package lf.com.android.blackfishdemo.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnDialogPosBtnClickListener;
import lf.com.android.blackfishdemo.util.JsonUtil;

public class HouseKeeperFragment extends BaseFragment {
    private ImageView mImageViewAdd, mImageShowEyes;
    private TextView mTextMoney, mTextGrid1, mTextGrid2, mTextGrid3, mTextGrid4, mTextGrid;
    private RelativeLayout mCradLayout;
    private Button mButtonAddBill;
    private Context mContext;

    private boolean isShowMoney = false;
    private float money = 2333.33f;

    public static HouseKeeperFragment newInstance() {
        return new HouseKeeperFragment();
    }

    @Override
    public void initView() {
        mContext = getActivity();
        mTextMoney = findView(R.id.tv_keeper_monkey);
        mImageViewAdd = findView(R.id.iv_keeper_add);
        mImageShowEyes = findView(R.id.tv_keeper_show_money);
        mTextGrid1 = findView(R.id.tv_keeper_grid_1);
        mTextGrid2 = findView(R.id.tv_keeper_grid_2);
        mTextGrid3 = findView(R.id.tv_keeper_grid_3);
        mTextGrid4 = findView(R.id.tv_keeper_grid_4);
        mTextGrid = findView(R.id.tv_keeper_gift);
        mCradLayout = findView(R.id.rl_keeper_card);
        mButtonAddBill = findView(R.id.btn_keeper_add_bill);

    }

    @Override
    public void initdata() {
        mImageViewAdd.setOnClickListener(this);
        mImageShowEyes.setOnClickListener(this);
        mTextGrid1.setOnClickListener(this);
        mTextGrid2.setOnClickListener(this);
        mTextGrid3.setOnClickListener(this);
        mTextGrid4.setOnClickListener(this);
        mTextGrid.setOnClickListener(this);
        mCradLayout.setOnClickListener(this);
        mButtonAddBill.setOnClickListener(this);


    }

    @Override
    public void lisetener(View view) {
        switch (view.getId()) {
            case R.id.iv_keeper_add:
                break;
            case R.id.tv_keeper_show_money:
                break;
            case R.id.tv_keeper_grid_1:
                break;
            case R.id.tv_keeper_grid_2:
                break;
            case R.id.tv_keeper_grid_3:
                break;
            case R.id.tv_keeper_grid_4:
                break;
            case R.id.tv_keeper_gift:
                break;
            case R.id.rl_keeper_card:
                break;
            case R.id.btn_keeper_add_bill:
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nav_housekeeper;
    }


    private void showAddBilDialog(int id, String text, int type, OnDialogPosBtnClickListener listener) {
        Dialog dialog = new Dialog(mContext, R.style.MyDialog);

    }
}
