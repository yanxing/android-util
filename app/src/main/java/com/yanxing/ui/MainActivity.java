package com.yanxing.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.view.SimpleDraweeView;
import com.photo.ui.PhotoUtilsActivity;
import com.yanxing.base.BaseActivity;
import com.yanxing.model.FirstEventBus;
import com.yanxing.sortlistviewlibrary.CityListActivity;
import com.yanxing.util.ConstantValue;
import com.yanxing.util.FileUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.simple_drawee_view)
    SimpleDraweeView mSimpleDraweeView;

    private static final int QUESTION_IMAGE_CODE = 1;
    private static final int QUESTION_SORT_LISTVIEW_CODE = 2;
    //选择的图片名称
    private String mImageName;

    @Override
    @AfterViews
    protected void afterInstanceView() {
        getSwipeBackLayout().setEnableGesture(false);
        EventBus.getDefault().register(this);
    }

    @Click(value = {R.id.adapter_button, R.id.list_dialog_button, R.id.confirm_dialog_button
            , R.id.loading_dialog_button, R.id.select_image, R.id.browse_image, R.id.map
            , R.id.fresco, R.id.eventbus, R.id.titleBar, R.id.tabLayoutPager, R.id.recyclerView
            ,R.id.sortListView,R.id.greenDao})
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            //通用适配器
            case R.id.adapter_button:
                intent.setClass(this, AdapterExampleActivity_.class);
                startActivity(intent);
                break;
            //列表适配器
            case R.id.list_dialog_button:
                intent.setClass(this, ListDialogExampleActivity_.class);
                startActivity(intent);
                break;
            //确定对话框
            case R.id.confirm_dialog_button:
                intent.setClass(this, ConfirmExampleActivity_.class);
                startActivity(intent);
                break;
            //进度框
            case R.id.loading_dialog_button:
                intent.setClass(this, LoadingDialogExampleActivity_.class);
                startActivity(intent);
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
            //图片浏览
            case R.id.browse_image:
                intent.setClass(getApplicationContext(), BrowseImageExampleActivity_.class);
                List<String> list = new ArrayList<String>();
                list.add("http://www.loveq.cn/store/photo/144/546/1445460/2140998/1402789580862162351.png");
                list.add("http://a0.att.hudong.com/15/08/300218769736132194086202411_950.jpg");
                list.add("http://pic15.nipic.com/20110731/8022110_162804602317_2.jpg");
                bundle.putSerializable("images", (Serializable) list);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            //百度地图
            case R.id.map:
                intent.setClass(getApplicationContext(), BaiduMapExampleActivity_.class);
                startActivity(intent);
                break;
            //fresco使用
            case R.id.fresco:
                intent.setClass(getApplicationContext(), FrescoExampleActivity_.class);
                startActivity(intent);
                break;
            //eventBus测试
            case R.id.eventbus:
                intent.setClass(getApplicationContext(), EventBusExampleActivity_.class);
                startActivity(intent);
                break;
            //标题栏测试
            case R.id.titleBar:
                intent.setClass(getApplicationContext(), TitleBarExampleActivity_.class);
                startActivity(intent);
                break;
            case R.id.tabLayoutPager:
                intent.setClass(getApplicationContext(), TabLayoutPagerExampleActivity_.class);
                startActivity(intent);
                break;
            //RecyclerViewAdapter test
            case R.id.recyclerView:
                intent.setClass(getApplicationContext(), RecyclerViewExampleActivity_.class);
                startActivity(intent);
                break;
            //城市列表
            case R.id.sortListView:
                intent.setClass(getApplicationContext(), CityListActivity.class);
                intent.putExtra("city","上海");
                startActivityForResult(intent,QUESTION_SORT_LISTVIEW_CODE);
                break;
            case R.id.greenDao:
                intent.setClass(getApplicationContext(), GreenDaoExampleActivity_.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().post(new FirstEventBus("EventBus是一个发布 / 订阅的事件总线"));
//        EventBus.getDefault().post("EventBus是一个发布 / 订阅的事件总线");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == QUESTION_IMAGE_CODE){
                Uri uri = Uri.parse(ConstantValue.FILE_CACHE_IMAGE + mImageName);
                mSimpleDraweeView.setVisibility(View.VISIBLE);
                mSimpleDraweeView.setImageURI(uri);
            }else if (requestCode==QUESTION_SORT_LISTVIEW_CODE){
                showToast(data.getExtras().getString("city"));
            }

        }
    }

    public void onEvent(String msg) {
        showToast(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
