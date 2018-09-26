package lf.com.android.blackfishdemo.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import lf.com.android.blackfishdemo.Activity.AddBillActivity;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnDialogPosBtnClickListener;
import lf.com.android.blackfishdemo.util.DenistyUtil;

public class HouseKeeperFragment extends BaseFragment implements OnDialogPosBtnClickListener {
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


    @Override
    public void onBtnClick(int type) {
        if (type == 0) {

        } else if (type == 1) {

        } else {
            return;
        }
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
}
