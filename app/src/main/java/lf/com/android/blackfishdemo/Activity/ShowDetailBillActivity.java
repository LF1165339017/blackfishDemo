package lf.com.android.blackfishdemo.Activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.adapter.DetailCardPagerAdapter;
import lf.com.android.blackfishdemo.listener.OnRemoveChildViewFromIndexLitener;
import lf.com.android.blackfishdemo.listener.OnSelectFinshListener;
import lf.com.android.blackfishdemo.util.DenistyUtil;
import lf.com.android.blackfishdemo.util.PickerUtil;
import lf.com.android.blackfishdemo.util.ToastUtil;

public class ShowDetailBillActivity extends BaseActivity {
    @BindView(R.id.rl_keeper_detail_bill_1)
    RelativeLayout mRelativeLayout1;
    @BindView(R.id.rl_keeper_detail_bill_2)
    RelativeLayout mRelativeLayout2;
    @BindView(R.id.tv_keeper_detail_card_title)
    TextView mTextTitle;
    @BindView(R.id.tv_detail_card_number_name)
    TextView mTextCardNumber;
    @BindView(R.id.tv_detail_card_bill_number)
    TextView mTextBillNumber;
    @BindView(R.id.tv_detail_card_pay_min)
    TextView mTextPayMin;
    @BindView(R.id.tv_detail_card_bill_lines)
    TextView mTextBillLines;
    @BindView(R.id.tv_detail_card_bill_day)
    TextView mTextBillDay;
    @BindView(R.id.tv_detail_card_pay_bill_day)
    TextView mTextPayBillDay;
    @BindView(R.id.tv_detail_card_no_li)
    TextView mTextNoLiXi;
    @BindView(R.id.iv_keeper_detail_card_back)
    ImageView mImageBack;
    @BindView(R.id.iv_detail_card_refresh)
    ImageView mImageRefresh;
    @BindView(R.id.tab_layout_detail_card)
    TabLayout mTabLayout;
    @BindView(R.id.vp_detail_card)
    ViewPager mViewPager;
    @BindView(R.id.tv_bill_detail_remind)
    TextView mTextRemind;
    @BindView(R.id.tv_bill_detail_pay_off)
    TextView mTextPayOff;
    @BindView(R.id.tv_bill_detail_sign)
    TextView mTextSign;
    @BindView(R.id.tv_bill_detail_imm_pay)
    TextView mTextImmpay;
    private Context mContext;
    private TabLayout.Tab billTab;
    private TabLayout.Tab payHistoryTab;
    private Dialog remindDialog, addRemindDialog;
    private ImageView mImageNewRemindClose, mImageAddRemindClose, mImageSelectRemindWay;
    private LinearLayout mNewRemindLayout;
    private Button mBtnCancal, mBtnDetermine;
    private TextView mTextRemindDate;
    private TextView mTextRemindTime;
    private boolean isSelectRemindWay = true;
    private int childIndex = 0;

    @Override
    public int getlayoutId() {
        return R.layout.activity_show_detail_bill_layout;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
        //修改状态栏颜色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getColor(R.color.colorCardDetailHeader));
        mContext = ShowDetailBillActivity.this;
        mViewPager.setAdapter(new DetailCardPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

        billTab = mTabLayout.getTabAt(0);
        payHistoryTab = mTabLayout.getTabAt(1);
        layoutInAndOutAnim(false);
        mImageBack.setOnClickListener(this);
        mImageRefresh.setOnClickListener(this);

        mTextRemind.setOnClickListener(this);
        mTextPayOff.setOnClickListener(this);
        mTextSign.setOnClickListener(this);
        mTextImmpay.setOnClickListener(this);
    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_keeper_add_bill_back:
                exitActivity();
                break;
            case R.id.iv_detail_card_refresh:
                Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "刷新", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.tv_bill_detail_remind:
                showRemindDialog();
                break;
            case R.id.tv_bill_detail_pay_off:
                Toast toast1 = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "标记未还清", Toast.LENGTH_SHORT);
                toast1.show();
                break;
            case R.id.tv_bill_detail_sign:
                Toast toast2 = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "标记已还部分", Toast.LENGTH_SHORT);
                toast2.show();
                break;
            case R.id.tv_bill_detail_imm_pay:
                Toast toast3 = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "立即还款", Toast.LENGTH_SHORT);
                toast3.show();
                break;
            case R.id.iv_remind_dialog_close:
                addRemindDialog.dismiss();
                break;
            case R.id.tv_new_remind:
                if (mNewRemindLayout.getChildCount() < 5) {
                    showAddRemindDialog();
                } else {
                    Toast.makeText(mContext, "最多设置五条提醒", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_new_remind_close:
                remindDialog.dismiss();
                break;
            case R.id.btn_remind_cancel:
                addRemindDialog.dismiss();
                break;
            case R.id.iv_remind_select_way:
                if (isSelectRemindWay) {
                    mImageSelectRemindWay.setImageResource(R.drawable.icon_remind_way_unchecked);
                    isSelectRemindWay = false;
                } else {
                    mImageSelectRemindWay.setImageResource(R.drawable.icon_remind_way_checked);
                    isSelectRemindWay = true;
                }
                break;
            case R.id.btn_remind_determine:
                if (isSelectRemindWay) {
                    Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                    addRemindItem(new OnRemoveChildViewFromIndexLitener() {
                        @Override
                        public void onRemoveChildView(int index) {
                            mNewRemindLayout.removeView(mNewRemindLayout.getChildAt(index - 1));
                        }
                    });
                    addRemindDialog.dismiss();
                } else {
                    Toast.makeText(mContext, "请选择提醒方式", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tv_remind_data:
                selectRemindDate();
                break;
            case R.id.tv_remind_time:
                selectRemindTime();
                break;
            default:
                break;
        }
    }

    private void selectRemindTime() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                String time = hourOfDay + ":" + minute;
                mTextRemindTime.setText(time);
                mTextRemindTime.setTextColor(getResources().getColor(R.color.Black));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        timePickerDialog.show(getFragmentManager(), "timePickerDialog");
    }

    private void selectRemindDate() {
        PickerUtil pickerUtil = new PickerUtil();
        pickerUtil.showCustomPicker(this, R.array.pick_remind_date, new OnSelectFinshListener() {
            @Override
            public String onSelected(String result) {
                mTextRemindDate.setText(result);
                mTextRemindDate.setTextColor(getResources().getColor(R.color.Black));
                return null;
            }
        });
    }

    private void layoutInAndOutAnim(boolean isBack) {
        if (!isBack) {
            Animation animationTopIn = AnimationUtils.loadAnimation(this, R.anim.activity_top_in);
            animationTopIn.setFillEnabled(true);
            animationTopIn.setFillAfter(true);
            mRelativeLayout1.setAnimation(animationTopIn);
            Animation animationBottomIn = AnimationUtils.loadAnimation(this, R.anim.activity_bottom_in);
            animationBottomIn.setFillEnabled(true);
            animationBottomIn.setFillAfter(true);
            mRelativeLayout2.setAnimation(animationBottomIn);
        } else {
            Animation animationTopOut = AnimationUtils.loadAnimation(this, R.anim.activity_top_out);
            animationTopOut.setFillEnabled(true);
            animationTopOut.setFillAfter(true);
            mRelativeLayout1.setAnimation(animationTopOut);
            Animation animationBottomOut = AnimationUtils.loadAnimation(this, R.anim.activity_top_out);
            animationBottomOut.setFillEnabled(true);
            animationBottomOut.setFillAfter(true);
            mRelativeLayout2.setAnimation(animationBottomOut);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitActivity();
        }
        return false;
    }

    private void exitActivity() {
        mRelativeLayout1.clearAnimation();
        mRelativeLayout1.invalidate();
        mRelativeLayout2.clearAnimation();
        mRelativeLayout2.invalidate();
        layoutInAndOutAnim(true);
        mRelativeLayout1.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        }, 500);
    }

    private void addRemindItem(final OnRemoveChildViewFromIndexLitener litener) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_remind_item_layout, null);
        TextView textView = view.findViewById(R.id.tv_remind_item_time);
        String s = ".    " + mTextRemindDate.getText() + "   " + mTextRemindTime.getText();
        textView.setText(s);
        ImageView imageView = view.findViewById(R.id.iv_remind_item_delete);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                litener.onRemoveChildView(childIndex);
                childIndex--;
            }
        });
        mNewRemindLayout.addView(view, childIndex);
        childIndex++;
    }

    private void showRemindDialog() {
        remindDialog = new Dialog(mContext, R.style.BottomDialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_remind_bottom_dialog_layout, null);

        mImageNewRemindClose = view.findViewById(R.id.iv_new_remind_close);
        mTextRemind = view.findViewById(R.id.tv_new_remind);
        mNewRemindLayout = view.findViewById(R.id.ll_new_remind);

        mImageNewRemindClose.setOnClickListener(this);
        mTextRemind.setOnClickListener(this);

        remindDialog.setContentView(view);
        Window window = remindDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = DenistyUtil.getScreenWidth(this);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        remindDialog.show();
    }

    private void showAddRemindDialog() {
        addRemindDialog = new Dialog(mContext, R.style.BottomDialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_new_remind_dialog, null);

        mImageAddRemindClose = view.findViewById(R.id.iv_remind_dialog_close);
        mImageSelectRemindWay = view.findViewById(R.id.iv_remind_select_way);
        mBtnCancal = view.findViewById(R.id.btn_remind_cancel);
        mBtnDetermine = view.findViewById(R.id.btn_remind_determine);
        mTextRemindDate = view.findViewById(R.id.tv_remind_data);
        mTextRemindTime = view.findViewById(R.id.tv_remind_time);

        mImageAddRemindClose.setOnClickListener(this);
        mImageSelectRemindWay.setOnClickListener(this);
        mBtnCancal.setOnClickListener(this);
        mBtnDetermine.setOnClickListener(this);
        mTextRemindDate.setOnClickListener(this);
        mTextRemindTime.setOnClickListener(this);

        addRemindDialog.setContentView(view);
        Window window = addRemindDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = DenistyUtil.getScreenWidth(this);
        layoutParams.height = DenistyUtil.dip2px(mContext, 350);
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        addRemindDialog.show();
    }
}
