package lf.com.android.blackfishdemo.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.util.RequestPermissionUtil;
import lf.com.android.blackfishdemo.util.ToastUtil;

public class SuggestionActivity extends BaseActivity {
    private Context mContext;
    private static final int REQUEST_CODE = 1;
    @BindView(R.id.tv_sug_interface)
    TextView mTextSugFace;
    @BindView(R.id.tv_sug_other)
    TextView mTextOther;
    @BindView(R.id.et_sug)
    EditText mEditSug;
    @BindView(R.id.et_sug_phone_text)
    EditText mEditPhone;
    @BindView(R.id.iv_sug_add_image)
    ImageView mAddImage;
    @BindView(R.id.btn_sug_submit)
    Button mButtonSubmit;
    @BindView(R.id.iv_mine_sug_back)
    ImageView mImageBack;

    @Override
    public int getlayoutId() {
        return R.layout.activity_suggestion_layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= 23) {
            RequestPermissionUtil.reqPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, "申请必要授权用于读取内存卡", REQUEST_CODE);
        }
        mContext = SuggestionActivity.this;
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        switchType(mTextSugFace, true);
        mAddImage.setImageURI(getUriFromDrawbleRes(mContext, R.drawable.icon_sug_add_image));

        mTextSugFace.setOnClickListener(this);
        mTextOther.setOnClickListener(this);

        mAddImage.setOnClickListener(this);
        mButtonSubmit.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
    }

    @Override
    public void intitdata() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.tv_sug_interface:
                switchType(mTextSugFace, true);
                switchType(mTextOther, false);
                break;
            case R.id.tv_sug_other:
                switchType(mTextSugFace, false);
                switchType(mTextOther, true);
                break;
            case R.id.iv_sug_add_image:
                selectImage(0x01);
                break;
            case R.id.btn_sug_submit:
                if (mEditSug.getText().length() > 3) {
                    Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "已提交,谢谢反馈. ", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                    overridePendingTransition(0, R.anim.activity_top_out);
                } else {
                    Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "请填写反馈内容，不少于三个字", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.iv_mine_sug_back:
                finish();
                break;
            default:
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void switchType(TextView mTextSugFace, boolean b) {
        if (b) {
            mTextSugFace.setBackground(getDrawable(R.drawable.shape_sug_text_view_selected));
            mTextSugFace.setTextColor(getColor(R.color.Black));
        } else {
            mTextSugFace.setBackground(getDrawable(R.drawable.shape_sug_text_view_unselected));
            mTextSugFace.setTextColor(getColor(R.color.colorGray696969));
        }
    }

    public Uri getUriFromDrawbleRes(Context context, int id) {
        Resources resources = context.getResources();
        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(id) + "/"
                + resources.getResourceTypeName(id) + "/"
                + resources.getResourceEntryName(id);
        return Uri.parse(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "权限申请成功", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "权限已被拒绝", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private void selectImage(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, requestCode);
    }
}
