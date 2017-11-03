package com.yanxing.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.location.AMapLoc;
import com.amap.location.event.AMapLocListener;
import com.photo.ui.PhotoUtilsActivity;
import com.yanxing.base.BaseFragment;
import com.yanxing.dialog.PhotoParam;
import com.yanxing.dialog.SelectPhotoActivity;
import com.yanxing.sortlistviewlibrary.CityListActivity;
import com.yanxing.ui.animation.AnimationMainFragment;
import com.yanxing.ui.swipebacklayout.SwipeBackLayoutActivity;
import com.yanxing.ui.swipetoloadlayout.SwipeToLoadLayoutFragment;
import com.yanxing.ui.tablayout.TabLayoutPagerFragment;
import com.yanxing.util.ConstantValue;
import com.yanxing.util.FileUtil;
import com.yanxing.util.PermissionUtil;
import com.yanxing.view.ConfirmDialog;
import com.yanxing.view.ListDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * 菜单列表
 * Created by lishuangxiang on 2016/12/21.
 */
public class MainFragment extends BaseFragment implements AMapLocListener {

    private static final int QUESTION_IMAGE_CODE = 1;
    private static final int QUESTION_SORT_LISTVIEW_CODE = 2;
    private static final int QUESTION_LOCATION = 3;
    private static final int REQUEST_CODE_CHOOSE = 4;
    //选择的图片名称
    private String mImageName;
    private String mCity;
    private AMapLoc mAMapLoc;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void afterInstanceView() {
//        showToast("测试Tinker热更新");
        checkPermission();
        mAMapLoc = new AMapLoc(getActivity().getApplicationContext());
        mAMapLoc.setAMapLocListener(this);
        mAMapLoc.startLocation();
    }

    /**
     * 申请定位权限
     */
    public void checkPermission() {
        PermissionUtil.requestPermission(this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_SETTINGS}, QUESTION_LOCATION);
    }

    @OnClick(R.id.adapter_button)
    public void onClickCommonAdapter() {
        replace(new CommonAdapterFragment());
    }

    @OnClick(R.id.time)
    public void onClickTime() {
        replace(new TimingFragment());
    }

    @OnClick(R.id.design)
    public void onClickDesign() {
        replace(new DesignFragment());
    }

    @OnClick(R.id.textImage)
    public void onClickTextAddImageBeforeLast() {
        replace(new TextAddImageBeforeLastFragment());
    }

    @OnClick(R.id.textConversionBitmap)
    public void onClickTextConversionBitmap() {
        replace(new TextConversionBitmapFragment());
    }

    @OnClick(R.id.hideTitleBottom)
    public void onClickHideTitleBottom() {
        replace(new HideTitleBottomFragment());
    }

    @OnClick(R.id.downloadlibrary)
    public void onClickDownload() {
        replace(new DownloadLibraryFragment());
    }

    @OnClick(R.id.RxJava)
    public void onClickRxJava() {
        replace(new RxJavaFragment());
    }

    @OnClick(R.id.expandableListViewCheckbox)
    public void onClickExpandableLVCH() {
        replace(new ExpandableListViewCheckBoxFragment());
    }

    @OnClick(R.id.ultra_ptr)
    public void onClickUltra_ptr() {
        replace(new UltraPtrFragment());
    }

    @OnClick(R.id.greenDao)
    public void onClickGreenDao() {
        replace(new GreenDaoFragment());
    }

    @OnClick(R.id.fresco)
    public void onClickFresco() {
        replace(new FrescoFragment());
    }

    @OnClick(R.id.animation)
    public void onClickAnimation() {
        replace(new AnimationMainFragment());
    }

    @OnClick(R.id.luban)
    public void onClick(){
        replace(new LubanFragment());
    }

    @OnClick(R.id.tabLayoutPager)
    public void onClickTabLayoutPager() {
        replace(new TabLayoutPagerFragment());
    }

    @OnClick(R.id.selectCity)
    public void onClickSelectCity() {
        SelectCityFragment selectCityFragment = new SelectCityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("currentCity", getString(R.string.city_test));
        selectCityFragment.setArguments(bundle);
        replace(selectCityFragment);
    }

    @OnClick(R.id.titleBar)
    public void onClickTitleBar() {
        replace(new TitleBarFragment());
    }

    @OnClick(R.id.map)
    public void onClickMap() {
        replace(new BaiduMapFragment());
    }

    @OnClick(R.id.extendRecyclerView)
    public void onClickExtendRecyclerView() {
        replace(new ExtendRecyclerViewFragment());
    }

    @OnClick(R.id.browse_image)
    public void onClickBrowseImage() {
        List<String> list = new ArrayList<String>();
        Bundle bundle = new Bundle();
        list.add("http://wx4.sinaimg.cn/thumbnail/61e7f4aaly1fbgnxq7bh7j20c8138gpn.jpg");
        list.add("http://wx4.sinaimg.cn/thumbnail/61e7f4aaly1fbgo4v08ftj20pg0wa468.jpg");
        list.add("http://wx4.sinaimg.cn/thumbnail/61e7f4aaly1fbgo5cc9pbj20c80eeta5.jpg");
        bundle.putSerializable("imageUrl", (Serializable) list);
        Intent intent = new Intent(getActivity(), BrowseImageActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.MPAndroidChart)
    public void onMPAndroidChart(){
        replace(new MPAndroidChartFragment());
    }

    @OnClick(value = {R.id.list_dialog_button, R.id.confirm_dialog_button
            , R.id.loading_dialog_button, R.id.select_image, R.id.titleBar
            , R.id.sortListView, R.id.amap, R.id.dialog, R.id.inputEditButton
            , R.id.select_image_dialog, R.id.surfaceView, R.id.swipeBackLayout
            ,R.id.swipe_to_load_layout,R.id.tableView,R.id.navigationTop,R.id.matisse})
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.list_dialog_button:
                showItemDialog();
                break;
            case R.id.confirm_dialog_button:
                showConfirmDialog();
                break;
            case R.id.loading_dialog_button:
                showLoadingDialog(getString(R.string.load));
                break;
            case R.id.select_image:
                long currentTime = System.currentTimeMillis();
                intent.setClass(getActivity(), PhotoUtilsActivity.class);
                mImageName = String.valueOf(currentTime) + ".png ";
                bundle.putString("path", FileUtil.getStoragePath() + ConstantValue.CACHE_IMAGE);
                bundle.putString("name", mImageName);
                bundle.putBoolean("cut", true);
                intent.putExtras(bundle);
                startActivityForResult(intent, QUESTION_IMAGE_CODE);
                break;
            case R.id.select_image_dialog:
                long currentTimeDialog = System.currentTimeMillis();
                intent.setClass(getActivity(), SelectPhotoActivity.class);
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
            case R.id.amap:
                showToast(mCity);
                break;
            //城市列表
            case R.id.sortListView:
                intent.setClass(getActivity(), CityListActivity.class);
                intent.putExtra("city", getString(R.string.city_test));
                startActivityForResult(intent, QUESTION_SORT_LISTVIEW_CODE);
                break;
            case R.id.dialog:
                intent.setClass(getActivity(), DialogActivity.class);
                startActivity(intent);
                break;
            case R.id.inputEditButton:
                intent.setClass(getActivity(), InputEditButtonActivity.class);
                startActivity(intent);
                break;
            case R.id.surfaceView:
                intent.setClass(getActivity(), SurfaceViewMediaPlayerActivity.class);
                startActivity(intent);
                break;
            case R.id.swipeBackLayout:
                intent.setClass(getActivity(), SwipeBackLayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.swipe_to_load_layout:
                replace(new SwipeToLoadLayoutFragment());
                break;
            case R.id.tableView:
                replace(new TableViewFragment());
                break;
            case R.id.navigationTop:
                replace(new NavigationTopFragment());
                break;
            case R.id.matisse:
                Matisse.from(getActivity())
                        .choose(MimeType.allOf())
                        .countable(true)
                        .maxSelectable(9)
//                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
                default:

        }
    }

    /**
     * 替换新的Fragment
     */
    private void replace(Fragment fragment) {
        final String tag = fragment.getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(R.id.main_content, fragment, tag)
                .commit();
    }

    /**
     * 显示确认对话框
     */
    public void showConfirmDialog() {
        final ConfirmDialog confirmDialog = new ConfirmDialog(getActivity(), getString(R.string.exit));
        confirmDialog.setConfirmButton(v -> {
            Toast.makeText(getActivity(), R.string.click_confirm, Toast.LENGTH_LONG).show();
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
        final ListDialog listDialog = new ListDialog(getActivity(), list);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), getString(R.string.you_select) + list.get(position), Toast.LENGTH_LONG).show();
                listDialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroy() {
        mAMapLoc.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FragmentActivity.RESULT_OK) {
            if (requestCode == QUESTION_IMAGE_CODE) {
                Intent intent = new Intent(getActivity(), ShowImageActivity.class);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        if (requestCode == QUESTION_LOCATION) {
            for (int i=0;i<grantResults.length;i++){
                if (grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                    for (String permission : permissions) {
                        PermissionUtil.getPermissionTip(permission);
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
