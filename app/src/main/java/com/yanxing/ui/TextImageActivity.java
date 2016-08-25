package com.yanxing.ui;

import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.yanxing.base.BaseActivity;

import butterknife.BindView;

/**
 * TextView前后添加图片
 * http://blog.csdn.net/yanzi1225627/article/details/24590029
 * Created by lishuangxiang on 2016/8/25.
 */
public class TextImageActivity extends BaseActivity {

    @BindView(R.id.text)
    TextView mText;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_text_image;
    }

    @Override
    protected void afterInstanceView() {
//      //前面,如果保留图片大小，用new ImageSpan（this，bitmap）
        ImageSpan start = new ImageSpan(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        String str = " TextView前后添加图片TextView前后添加图片TextView前后添加图片TextView前后" +
                "添加图片TextView前后添加图片TextView前后添加图片TextView前后添加图片TextView前后添加图片" +
                "TextView前后添加图片TextView前后添加图片TextView前后添加图片TextView前后添加图片 ";
        SpannableString ss = new SpannableString(str);
        ss.setSpan(start,0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mText.setText(ss);
        //后面
        ImageSpan endImageSpan = new ImageSpan(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        SpannableString end=new SpannableString(" ");
        end.setSpan(endImageSpan,0,1,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mText.append(end);
    }
}
