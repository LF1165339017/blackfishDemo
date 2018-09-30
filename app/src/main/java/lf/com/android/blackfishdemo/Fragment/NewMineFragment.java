package lf.com.android.blackfishdemo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import lf.com.android.blackfishdemo.Activity.AboutActivity;
import lf.com.android.blackfishdemo.Activity.MineSettingActibity;
import lf.com.android.blackfishdemo.R;

public class NewMineFragment extends BaseFragment {
    private Context mContext;
    private ImageView mImageSetting;
    private SimpleDraweeView mSimpleDraweeView;//头像
    private TextView mTextPhone, mTextPerCenter;
    private TextView mTextBankCard, mTextCoupons, mTextFav, mTextHistory, mTextHelpCenter, mTextAbout;
    private TextView mTextPay, mTextSend, mTextGet, mTextAfterSale;

    public static NewMineFragment newInstance() {
        return new NewMineFragment();
    }

    @Override
    public void initView() {
        mContext = getContext();
        mImageSetting = findView(R.id.iv_mine_settings);
        mSimpleDraweeView = findView(R.id.iv_mine_portrait);
        mTextPerCenter = findView(R.id.tv_mine_personal_center);

        mTextBankCard = findView(R.id.tv_mine_bank_card);
        mTextCoupons = findView(R.id.tv_mine_coupons);
        mTextFav = findView(R.id.tv_mine_fav);
        mTextHistory = findView(R.id.tv_mine_history);
        mTextHelpCenter = findView(R.id.tv_mine_help_center);
        mTextAbout = findView(R.id.tv_mine_about_of);

        mTextPay = findView(R.id.tv_mine_grid_pay);
        mTextSend = findView(R.id.tv_mine_grid_send_goods);
        mTextGet = findView(R.id.tv_mine_grid_get_goods);
        mTextAfterSale = findView(R.id.tv_mine_grid_after_sale);

        mImageSetting.setOnClickListener(this);
        mSimpleDraweeView.setOnClickListener(this);
        mTextPerCenter.setOnClickListener(this);

        mTextBankCard.setOnClickListener(this);
        mTextCoupons.setOnClickListener(this);
        mTextPay.setOnClickListener(this);
        mTextHistory.setOnClickListener(this);
        mTextHelpCenter.setOnClickListener(this);
        mTextAbout.setOnClickListener(this);

        mTextPay.setOnClickListener(this);
        mTextSend.setOnClickListener(this);
        mTextGet.setOnClickListener(this);
        mTextAfterSale.setOnClickListener(this);
        mSimpleDraweeView.setImageResource(R.drawable.image_mine_user);
    }

    @Override
    public void initdata() {

    }

    @Override
    public void lisetener(View view) {
        switch (view.getId()) {
            case R.id.iv_mine_settings:
                //设置
                startActivity(new Intent(getActivity(), MineSettingActibity.class));
                break;
            case R.id.iv_mine_portrait:
                //个人头像
                Toast.makeText(mContext, "点击了个人头像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_personal_center:
                //个人中心
                Toast.makeText(mContext, "点击了个人中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_grid_pay:
                //待付款
                Toast.makeText(mContext, "点击了个待付款", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_grid_send_goods:
                //待发货
                Toast.makeText(mContext, "点击了个待发货", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_grid_get_goods:
                //待收货
                Toast.makeText(mContext, "点击了个待收货", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_grid_after_sale:
                //待付款
                Toast.makeText(mContext, "点击了个待付款", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_bank_card:
                //我的银行卡
                Toast.makeText(mContext, "点击我的银行卡", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_coupons://我的优惠劵
                Toast.makeText(mContext, "点击了我的优惠劵", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_history://浏览历史
                Toast.makeText(mContext, "点击了浏览历史", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_help_center:
                //帮助中心
                Toast.makeText(mContext, "点击了帮助中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_about_of:
                //关于小黑鱼
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            default:
                break;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nav_mine;
    }


}
