package com.yanxing.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.location.AMapLoc;
import com.amap.location.event.AMapLocListener;
import com.photo.ui.PhotoUtilsActivity;
import com.yanxing.dialog.PhotoParam;
import com.yanxing.dialog.SelectPhotoActivity;
import com.yanxing.base.BaseActivity;
import com.yanxing.sortlistviewlibrary.CityListActivity;
import com.yanxing.ui.animation.AnimationMainActivity;
import com.yanxing.ui.swipebacklayout.SwipeBackLayoutActivity;
import com.yanxing.ui.tablayout.TabLayoutPagerExampleActivity;
import com.yanxing.util.ConstantValue;
import com.yanxing.util.FileUtil;
import com.yanxing.util.PermissionUtil;
import com.yanxing.view.ConfirmDialog;
import com.yanxing.view.ListDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class MainActivity extends BaseActivity implements AMapLocListener {

    private static final int QUESTION_IMAGE_CODE = 1;
    private static final int QUESTION_SORT_LISTVIEW_CODE = 2;
    private static final int QUESTION_LOCATION = 3;
    //选择的图片名称
    private String mImageName;
    private String mCity;
    private AMapLoc mAMapLoc;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterInstanceView() {
        checkPermission();
        mAMapLoc = new AMapLoc(getApplicationContext());
        mAMapLoc.setAMapLocListener(this);
        mAMapLoc.startLocation();
    }

    /**
     * 检查并申请定位权限
     */
    public void checkPermission() {
        if (Build.VERSION.SDK_INT<23){
            return;
        }
        PermissionUtil.checkSelfPermission(this, new String[]{
                Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION
                ,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE
                ,Manifest.permission.READ_EXTERNAL_STORAGE
                ,Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_SETTINGS}, QUESTION_LOCATION);
    }

    @OnClick(value = {R.id.adapter_button, R.id.list_dialog_button, R.id.confirm_dialog_button
            , R.id.loading_dialog_button, R.id.select_image, R.id.browse_image, R.id.map
            , R.id.fresco, R.id.titleBar, R.id.tabLayoutPager
            , R.id.sortListView, R.id.greenDao, R.id.selectCity, R.id.ultra_ptr
            , R.id.amap, R.id.animation, R.id.dialog
            , R.id.expandableListViewCheck, R.id.RxJava, R.id.inputEditButton, R.id.textImage
            , R.id.select_image_dialog, R.id.downloadlibrary, R.id.surfaceView
            , R.id.textChangeImage, R.id.extendRecyclerView
            , R.id.hideTitleBottom, R.id.swipeBackLayout, R.id.design, R.id.time})
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            //通用适配器
            case R.id.adapter_button:
                intent.setClass(this, AdapterExampleActivity.class);
                startActivity(intent);
                break;
            //列表对话框
            case R.id.list_dialog_button:
                showItemDialog();
                break;
            //确定对话框
            case R.id.confirm_dialog_button:
                showConfirmDialog();
                break;
            //进度框
            case R.id.loading_dialog_button:
                showLoadingDialog(getString(R.string.load));
                break;
            //本地图片选择
            case R.id.select_image:
                long currentTime = System.currentTimeMillis();
                intent.setClass(getApplicationContext(), PhotoUtilsActivity.class);
                mImageName = String.valueOf(currentTime) + ".png";
                bundle.putString("path", FileUtil.getStoragePath() + ConstantValue.CACHE_IMAGE);
                bundle.putString("name", mImageName);
                bundle.putBoolean("cut", true);
                intent.putExtras(bundle);
                startActivityForResult(intent, QUESTION_IMAGE_CODE);
                break;
            case R.id.select_image_dialog:
                long currentTimeDialog = System.currentTimeMillis();
                intent.setClass(getApplicationContext(), SelectPhotoActivity.class);
                mImageName = currentTimeDialog + ".png";
                PhotoParam photoParam = new PhotoParam();
                photoParam.setName(mImageName);
                photoParam.setPath(FileUtil.getStoragePath() + ConstantValue.CACHE_IMAGE);
                photoParam.setCut(true);
                photoParam.setOutputX(480);
                photoParam.setOutputY(480);
                Bundle bundle1 = new Bundle();
                bundle1.putParcelable("photoParam", photoParam);
                intent.putExtras(bundle1);
                startActivityForResult(intent, QUESTION_IMAGE_CODE);
                break;
            //图片浏览
            case R.id.browse_image:
                intent.setClass(getApplicationContext(), BrowseImageExampleActivity.class);
                List<String> list = new ArrayList<String>();
                list.add("http://a0.att.hudong.com/15/08/300218769736132194086202411_950.jpg");
                list.add("http://pic15.nipic.com/20110731/8022110_162804602317_2.jpg");
                bundle.putSerializable("images", (Serializable) list);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            //百度地图
            case R.id.map:
                intent.setClass(getApplicationContext(), BaiduMapExampleActivity.class);
                startActivity(intent);
                break;
            case R.id.amap:
                showToast(mCity);
                break;
            //fresco使用
            case R.id.fresco:
                intent.setClass(getApplicationContext(), FrescoExampleActivity.class);
                startActivity(intent);
                break;
            //标题栏测试
            case R.id.titleBar:
                intent.setClass(getApplicationContext(), TitleBarExampleActivity.class);
                startActivity(intent);
                break;
            case R.id.tabLayoutPager:
                intent.setClass(getApplicationContext(), TabLayoutPagerExampleActivity.class);
                startActivity(intent);
                break;
            case R.id.extendRecyclerView:
                intent.setClass(getApplicationContext(), ExtendRecyclerViewActivity.class);
                startActivity(intent);
                break;
            //城市列表
            case R.id.sortListView:
                intent.setClass(getApplicationContext(), CityListActivity.class);
                intent.putExtra("city", getString(R.string.city_test));
                startActivityForResult(intent, QUESTION_SORT_LISTVIEW_CODE);
                break;
            case R.id.greenDao:
                intent.setClass(getApplicationContext(), GreenDaoExampleActivity.class);
                startActivity(intent);
                break;
            case R.id.selectCity:
                intent.setClass(getApplicationContext(), SelectCityActivity.class);
                intent.putExtra("currentCity", getString(R.string.city_test));
                startActivity(intent);
                break;
            case R.id.ultra_ptr:
                intent.setClass(getApplicationContext(), UltraPtrExampleActivity.class);
                startActivity(intent);
                break;
            case R.id.animation:
                intent.setClass(getApplicationContext(), AnimationMainActivity.class);
                startActivity(intent);
                break;
            case R.id.dialog:
                intent.setClass(getApplicationContext(), DialogActivity.class);
                startActivity(intent);
                break;
            case R.id.expandableListViewCheck:
                intent.setClass(getApplicationContext(), ExpandableListViewCheckActivity.class);
                startActivity(intent);
                break;
            case R.id.RxJava:
                intent.setClass(getApplicationContext(), RxJavaExampleActivity.class);
                startActivity(intent);
                break;
            case R.id.inputEditButton:
                intent.setClass(getApplicationContext(), InputEditButtonActivity.class);
                startActivity(intent);
                break;
            case R.id.textImage:
                intent.setClass(getApplicationContext(), TextImageActivity.class);
                startActivity(intent);
                break;
            case R.id.downloadlibrary:
                intent.setClass(getApplicationContext(), DownloadLibraryActivity.class);
                startActivity(intent);
                break;
            case R.id.surfaceView:
                intent.setClass(getApplicationContext(), SurfaceViewMediaPlayerActivity.class);
                startActivity(intent);
                break;
            case R.id.textChangeImage:
                intent.setClass(getApplicationContext(), TextChangeImageActivity.class);
                startActivity(intent);
                break;
            case R.id.hideTitleBottom:
                intent.setClass(getApplicationContext(), HideTitleBottomActivity.class);
                startActivity(intent);
                break;
            case R.id.swipeBackLayout:
                intent.setClass(getApplicationContext(), SwipeBackLayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.design:
                intent.setClass(getApplicationContext(), DesignActivity.class);
                startActivity(intent);
                break;
            case R.id.time:
                intent.setClass(getApplicationContext(), TimingActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * 显示确认对话框
     */
    public void showConfirmDialog() {
        final ConfirmDialog confirmDialog = new ConfirmDialog(this, getString(R.string.exit));
        confirmDialog.setConfirmButton(v -> {
            Toast.makeText(getApplicationContext(), R.string.click_confirm, Toast.LENGTH_LONG).show();
            confirmDialog.dismiss();
        });
    }

    /**
     * 显示Item对话框
     */
    public void showItemDialog() {
        final List<String> list = new ArrayList<String>();
        for (int i = 1; i < 4; i++) {
            list.add("yanxing" + i);
        }
        final ListDialog listDialog = new ListDialog(this, list);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), getString(R.string.you_select) + list.get(position), Toast.LENGTH_LONG).show();
                listDialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        mAMapLoc.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == QUESTION_IMAGE_CODE) {
                Intent intent = new Intent(getApplicationContext(), ShowImageActivity.class);
                intent.putExtra("name", mImageName);
                startActivity(intent);
            } else if (requestCode == QUESTION_SORT_LISTVIEW_CODE) {
                showToast(data.getExtras().getString("city"));
            }
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            mCity = getString(R.string.current_city_tip) + aMapLocation.getAddress();
            mAMapLoc.stopLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == QUESTION_LOCATION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                for (int i=0;i<permissions.length;i++){
                    PermissionUtil.getPermissionTip(permissions[i]);
                }
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
