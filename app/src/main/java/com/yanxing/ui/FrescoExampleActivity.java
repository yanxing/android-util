package com.yanxing.ui;

import android.net.Uri;
import android.widget.GridView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * fresco使用
 * Created by lishuangxiang on 2016/1/28.
 */
public class FrescoExampleActivity extends BaseActivity {

    @BindView(R.id.gridView)
    GridView mGridView;

    private CommonAdapter<String> mCommonAdapter;
    private List<String> mList=new ArrayList<String>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_fresco_example;
    }

    @Override
    protected void afterInstanceView() {
        mList.add("http://npic7.edushi.com/cn/zixun/zh-chs/2015-12/24/9fb3600ebd60a7504bcd9acf1d6ab40b.gif");
        mList.add("http://upload.qianhuaweb.com/2015/1203/1449107626255.gif");
        mList.add("http://jiangsu.china.com.cn/uploadfile/2015/1201/1448961827412987.jpg");
        mList.add("http://img5.imgtn.bdimg.com/it/u=124877489,3743558634&fm=11&gp=0.jpg");
        mList.add("http://photocdn.sohu.com/20151203/mp46210750_1449138160100_1_th.jpeg");
        mList.add("http://img1.shenchuang.com/2015/0203/1422969395565.jpg");
        mList.add("http://www.people.com.cn/mediafile/pic/20151201/55/6912848265428588611.jpg");
        mList.add("http://moviepic.manmankan.com/yybpic/xinwen/2015/201512/02/14490288511739759.gif");
        mList.add("http://img0.imgtn.bdimg.com/it/u=503741161,2905441265&fm=21&gp=0.jpg");
        mList.add("http://easyread.ph.126.net/oqrtUVjMy0AGTI-bGY6SDg==/8796093022410112398.jpg");
        mList.add("http://pic.yesky.com/uploadImages/2015/338/58/NDGS01TWM26A.jpg");
        mList.add("http://photocdn.sohu.com/20151209/mp47317406_1449623763081_4.gif");
        mList.add("http://npic7.edushi.com/cn/zixun/zh-chs/2015-12/24/9fb3600ebd60a7504bcd9acf1d6ab40b.gif");
        mList.add("http://upload.qianhuaweb.com/2015/1203/1449107626255.gif");
        mList.add("http://jiangsu.china.com.cn/uploadfile/2015/1201/1448961827412987.jpg");
        mList.add("http://img5.imgtn.bdimg.com/it/u=124877489,3743558634&fm=11&gp=0.jpg");
        mList.add("http://photocdn.sohu.com/20151203/mp46210750_1449138160100_1_th.jpeg");
        mList.add("http://img1.shenchuang.com/2015/0203/1422969395565.jpg");
        mList.add("http://www.people.com.cn/mediafile/pic/20151201/55/6912848265428588611.jpg");
        mList.add("http://moviepic.manmankan.com/yybpic/xinwen/2015/201512/02/14490288511739759.gif");
        mCommonAdapter=new CommonAdapter<String>(mList,R.layout.gridview_adapter) {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                String url=mList.get(position);
                Uri uri = Uri.parse(url);
                SimpleDraweeView simpleDraweeView=viewHolder.findViewById(R.id.simple_drawee_view);

                if (url.contains("gif")){
                    //gif动画图自动播放
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setUri(uri)
                            .setAutoPlayAnimations(true)
                            .build();
                    simpleDraweeView.setController(controller);
                }
                //渐进式JPEG图
                else if (url.contains("jpeg")){
                    ImageRequest request = ImageRequestBuilder
                            .newBuilderWithSource(uri)
                            .setProgressiveRenderingEnabled(true)
                            .build();
                    PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                            .setImageRequest(request)
                            .setOldController(simpleDraweeView.getController())
                            .build();
                    simpleDraweeView.setController(controller);
                }else {
                    simpleDraweeView.setImageURI(uri);
                }
            }
        };
        mGridView.setAdapter(mCommonAdapter);
    }
}
