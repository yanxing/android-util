package com.yanxing.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.view.SimpleDraweeView;
import com.photo.ui.PhotoUtilsActivity;
import com.yanxing.base.BaseActivity;
import com.yanxing.util.ConstantValue;
import com.yanxing.util.FileUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity{

    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.simple_drawee_view)
    SimpleDraweeView simpleDraweeView;

    private static final int QUESTION_IMAGE_CODE = 1;
    //选择的图片名称
    private  String mImageName;

    @Override
    @AfterViews
    protected void afterInstanceView() {
        setSupportActionBar(toolbar);
    }

    @Click(value = {R.id.adapter_button,R.id.list_dialog_button,R.id.confirm_dialog_button
            ,R.id.loading_dialog_button,R.id.select_image,R.id.browse_image})
    public void onClick(View v) {
        Intent intent=new Intent();
        Bundle bundle = new Bundle();
        switch (v.getId()){
            //通用适配器
            case R.id.adapter_button:
                intent.setClass(this,AdapterExampleActivity.class);
                startActivity(intent);
                break;
            //列表适配器
            case R.id.list_dialog_button:
                intent.setClass(this,ListDialogExampleActivity.class);
                startActivity(intent);
                break;
            //确定对话框
            case R.id.confirm_dialog_button:
                intent.setClass(this,ConfirmExampleActivity.class);
                startActivity(intent);
                break;
            //进度框
            case R.id.loading_dialog_button:
                intent.setClass(this,LoadingDialogExampleActivity_.class);
                startActivity(intent);
                break;
            //本地图片选择
            case R.id.select_image:
                long currentTime=System.currentTimeMillis();
                intent.setClass(getApplicationContext(),PhotoUtilsActivity.class);
                mImageName = String.valueOf(currentTime) + ".png";
                bundle.putString("path", FileUtil.getStoragePath() + ConstantValue.CACHE_IMAGE);
                bundle.putString("name", mImageName);
                bundle.putBoolean("cut", true);
                intent.putExtras(bundle);
                startActivityForResult(intent, QUESTION_IMAGE_CODE);
                break;
            //图片浏览
            case R.id.browse_image:
                intent.setClass(getApplicationContext(),BrowseImageActivity_.class);
                List<String> list=new ArrayList<String>();
                list.add("http://www.loveq.cn/store/photo/144/546/1445460/2140998/1402789580862162351.png");
                list.add("http://a0.att.hudong.com/15/08/300218769736132194086202411_950.jpg");
                list.add("http://pic15.nipic.com/20110731/8022110_162804602317_2.jpg");
                bundle.putSerializable("images", (Serializable) list);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==QUESTION_IMAGE_CODE&&resultCode==RESULT_OK){
            Uri uri = Uri.parse(ConstantValue.FILE_CACHE_IMAGE+mImageName);
            simpleDraweeView.setVisibility(View.VISIBLE);
            simpleDraweeView.setImageURI(uri);
        }
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
