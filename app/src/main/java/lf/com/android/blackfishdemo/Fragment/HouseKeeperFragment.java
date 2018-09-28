package lf.com.android.blackfishdemo.Fragment;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import lf.com.android.blackfishdemo.Activity.AddBillActivity;
import lf.com.android.blackfishdemo.Activity.AddCreditBillActivity;
import lf.com.android.blackfishdemo.Activity.ShowDetailBillActivity;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnDialogPosBtnClickListener;
import lf.com.android.blackfishdemo.util.DenistyUtil;
import lf.com.android.blackfishdemo.util.SpannableStringUtil;
import lf.com.android.blackfishdemo.util.ToastUtil;

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

        mImageViewAdd.setOnClickListener(this);
        mImageShowEyes.setOnClickListener(this);
        mTextGrid1.setOnClickListener(this);
        mTextGrid2.setOnClickListener(this);
        mTextGrid3.setOnClickListener(this);
        mTextGrid4.setOnClickListener(this);
        mTextGrid.setOnClickListener(this);
        mCradLayout.setOnClickListener(this);
        mButtonAddBill.setOnClickListener(this);

        mCradLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Animation animation = setLongClickAnimation(mCradLayout);
                animation.start();
                return false;
            }
        });
    }

    @Override
    public void initdata() {


    }

    @Override
    public void lisetener(View view) {
        switch (view.getId()) {
            case R.id.iv_keeper_add:
                startActivity(new Intent(getActivity(), AddBillActivity.class));
                break;
            case R.id.tv_keeper_show_money:
                if (!isShowMoney) {
                    final DecimalFormat decimalFormat = new DecimalFormat(".00");
                    ValueAnimator animator = ValueAnimator.ofFloat(0, money);
                    animator.setDuration(1000);
                    animator.start();
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mTextMoney.setText(decimalFormat.format(animation.getAnimatedFraction()));
                            SpannableStringUtil.setRelativeSizeText(
                                    mTextMoney, 0, mTextMoney.getText().length() - 3,
                                    1.3f, mTextMoney.getText().toString());
                        }
                    });
                    isShowMoney = true;
                    mImageShowEyes.setImageResource(R.drawable.user_icon_eye_open_blue);
                } else {
                    mTextMoney.setText("******");
                    isShowMoney = false;
                    mImageShowEyes.setImageResource(R.drawable.user_icon_eye_close_blue);
                }
                break;
            case R.id.tv_keeper_grid_1:
                showAddBilDialog(R.drawable.icon_dialog_add_bill_1, "你还没有账单，快去添加吧", 0, new DialogListener());
                break;
            case R.id.tv_keeper_grid_2:
                showAddBilDialog(R.drawable.icon_dialog_add_bill_2, "免息期告诉你今天刷哪张卡最划算", 1, new DialogListener());
                break;
            case R.id.tv_keeper_grid_3:
                Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "办信用卡", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.tv_keeper_grid_4:
                Toast toast1 = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "我要贷款", Toast.LENGTH_SHORT);
                toast1.show();
                break;
            case R.id.rl_keeper_card:
                setClickAnimation(mCradLayout);
                break;
            case R.id.btn_keeper_add_bill:
                startActivity(new Intent(mContext, AddBillActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nav_housekeeper_layout;
    }


    private void showAddBilDialog(int drawableid, String text, final int type, final OnDialogPosBtnClickListener listener) {
        final Dialog dialog = new Dialog(mContext, R.style.MyDialog);
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_keeper_dialog, null);
        dialog.setContentView(view);
        ImageView imageExit = view.findViewById(R.id.iv_dialog_exit);
        ImageView imageIcon = view.findViewById(R.id.iv_dialog_icon);
        imageIcon.setImageResource(drawableid);
        TextView textTitle = view.findViewById(R.id.tv_dialog_title);
        textTitle.setText(text);
        TextView textCancel = view.findViewById(R.id.tv_dialog_cancel);
        TextView textAddBill = view.findViewById(R.id.tv_dialog_add_bill);
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        textAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnClick(type);
                dialog.dismiss();
            }
        });

        imageExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.height = DenistyUtil.dip2px(getContext(), 220);
        layoutParams.width = DenistyUtil.dip2px(getContext(), 280);
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog.show();
    }


    private Animation setLongClickAnimation(View view) {
        Animation animation = new ScaleAnimation(0.95f, 1, 0.95f, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        animation.setDuration(300);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(getActivity(), AddBillActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return animation;
    }

    private class DialogListener implements OnDialogPosBtnClickListener {

        @Override
        public void onBtnClick(int type) {
            if (type == 0) {
                startActivity(new Intent(mContext, AddBillActivity.class));
            } else if (type == 1) {
                startActivity(new Intent(mContext, AddCreditBillActivity.class));
            } else {
                return;
            }
        }
    }

    private void setClickAnimation(View view) {
        Animation animation = new ScaleAnimation(0.95f, 1, 095f, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setDuration(300);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(getActivity(), ShowDetailBillActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }
}
