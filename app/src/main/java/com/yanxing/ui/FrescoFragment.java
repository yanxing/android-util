package com.yanxing.ui;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.yanxing.adapterlibrary.RecyclerViewAdapter;
import com.yanxing.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * fresco+PhotoDraweeView使用
 * Created by lishuangxiang on 2016/1/28.
 */
public class FrescoFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private RecyclerViewAdapter<String> mCommonAdapter;
    private List<String> mList=new ArrayList<String>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_fresco_example;
    }

    @Override
    protected void afterInstanceView() {
        mList.add("http://upload.qianhuaweb.com/2015/1203/1449107626255.gif");
        mList.add("http://moviepic.manmankan.com/yybpic/xinwen/2015/201512/02/14490288511739759.gif");
//        mList.add("http://npic7.edushi.com/cn/zixun/zh-chs/2015-12/24/9fb3600ebd60a7504bcd9acf1d6ab40b.gif");
//        mList.add("http://photocdn.sohu.com/20151209/mp47317406_1449623763081_4.gif");
        mCommonAdapter=new RecyclerViewAdapter<String>(mList,R.layout.gridview_adapter) {
            @Override
            public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
                String url=mDataList.get(position);
                Uri uri = Uri.parse(url);
                final PhotoDraweeView photoDraweeView= (PhotoDraweeView) holder.findViewById(R.id.simple_drawee_view);
                PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setOldController(photoDraweeView.getController())
                        .setAutoPlayAnimations(true);
                controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (imageInfo == null || photoDraweeView == null) {
                            return;
                        }
                        photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                    }
                });
                photoDraweeView.setController(controller.build());
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mCommonAdapter);
    }
}
