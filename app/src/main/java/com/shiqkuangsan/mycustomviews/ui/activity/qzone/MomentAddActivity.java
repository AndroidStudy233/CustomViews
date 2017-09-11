package com.shiqkuangsan.mycustomviews.ui.activity.qzone;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.bean.Moment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by shiqkuangsan on 2017/9/11. <p>
 * ClassName: MomentAddActivity <p>
 * Author: shiqkuangsan <p>
 * Description: 从发说说界面调过来, 操作选择图片并编辑说说的界面
 */
@ContentView(R.layout.activity_moment_add)
public class MomentAddActivity extends AppCompatActivity implements View.OnClickListener,
        EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate {

    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW = 2;
    private static final String EXTRA_MOMENT = "EXTRA_MOMENT";

    /**是否是单选「测试接口用的」*/
    @ViewInject(R.id.cb_moment_add_single_choice)
    private CheckBox mSingleChoiceCb;
    /**是否具有拍照功能「测试接口用的」*/
    @ViewInject(R.id.cb_moment_add_take_photo)
    private CheckBox mTakePhotoCb;
    /**是否显示九图控件的加号按钮「测试接口用的」*/
    @ViewInject(R.id.cb_moment_add_plus)
    private CheckBox mPlusCb;
    /**是否开启拖拽排序功能「测试接口用的」*/
    @ViewInject(R.id.cb_moment_add_sortable)
    private CheckBox mSortableCb;
    @ViewInject(R.id.et_moment_add_content)
    private EditText mContentEt;
    @ViewInject(R.id.snpl_moment_add_photos)
    private BGASortableNinePhotoLayout mPhotosSnpl;

    public static Moment getMoment(Intent intent) {
        return intent.getParcelableExtra(EXTRA_MOMENT);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        setListener();
        processLogic();
    }


    protected void setListener() {
        mSingleChoiceCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    mPhotosSnpl.setData(null);
                    mPhotosSnpl.setMaxItemCount(1);
                } else {
                    mPhotosSnpl.setMaxItemCount(9);
                }
            }
        });
        mPlusCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                mPhotosSnpl.setPlusEnable(checked);
            }
        });
        mSortableCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                mPhotosSnpl.setSortable(checked);
            }
        });

        // 设置拖拽排序控件的代理
        mPhotosSnpl.setDelegate(this);
    }

    protected void processLogic() {
        setTitle("图片选择");
        mPlusCb.setChecked(mPhotosSnpl.isPlusEnable());
        mSortableCb.setChecked(mPhotosSnpl.isSortable());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_moment_add_choice_photo) {
            choicePhotoWrapper();
        } else if (v.getId() == R.id.tv_moment_add_publish) {
            String content = mContentEt.getText().toString().trim();
            if (content.length() == 0 && mPhotosSnpl.getItemCount() == 0) {
                Toast.makeText(this, "必须填写这一刻的想法或选择照片！", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent();
            intent.putExtra(EXTRA_MOMENT, new Moment(mContentEt.getText().toString().trim(), mPhotosSnpl.getData()));
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(this, mPhotosSnpl.getMaxItemCount(), models, models, position, false), REQUEST_CODE_PHOTO_PREVIEW);
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

            startActivityForResult(BGAPhotoPickerActivity.newIntent(this, mTakePhotoCb.isChecked() ? takePhotoDir : null, mSingleChoiceCb.isChecked() ? 1 : mPhotosSnpl.getMaxItemCount(), mPhotosSnpl.getData(), true), REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
            if (mSingleChoiceCb.isChecked()) {
                mPhotosSnpl.setData(BGAPhotoPickerActivity.getSelectedImages(data));
            } else {
                mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
            }
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }
}