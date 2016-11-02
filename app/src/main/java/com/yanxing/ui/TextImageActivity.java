package com.yanxing.ui;

import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.yanxing.base.BaseActivity;
import com.yanxing.util.CommonUtil;
import com.yanxing.util.LogUtil;

import butterknife.BindView;

/**
 * TextView前后添加图片
 * http://blog.csdn.net/yanzi1225627/article/details/24590029
 * Created by lishuangxiang on 2016/8/25.
 */
public class TextImageActivity extends BaseActivity {

    @BindView(R.id.text)
    TextView mText;

    @BindView(R.id.icon)
    TextView mIcon;

    @BindView(R.id.content)
    TextView mContent;

    private int mwidth;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_text_image;
    }

    @Override
    protected void afterInstanceView() {
//      //前面,如果保留图片大小，用new ImageSpan（this，bitmap）
        ImageSpan start = new ImageSpan(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        String str = "TextView前后添加图片TextView前后添加图片TextView前后添加图片TextView前后" +
                "添加图片TextView前后添加图片TextView前后添加图片TextView前后添加图片TextView前后添加图片" +
                "TextView前后添加图片TextView前后添加图片TextView前后添加图片TextView前后添加图片";
        SpannableString ss = new SpannableString(str);
        ss.setSpan(start,0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mText.setText(ss);
        //后面
        ImageSpan endImageSpan = new ImageSpan(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        SpannableString end=new SpannableString(" ");
        end.setSpan(endImageSpan,0,1,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mText.append(end);


        String text="正文部分正文部分正文部分正文部分正文部分正文部分正文部分正文部分";
        mIcon.post(new Runnable() {
            @Override
            public void run() {
                mwidth=mIcon.getWidth();
                StringBuilder blank=new StringBuilder();
                int widthdp=CommonUtil.px2dp(getApplicationContext(),mwidth);
                LogUtil.d(TAG,"width="+mwidth+"  widthdp="+widthdp);//96 32
                int count=widthdp/3+(widthdp%10>=5?1:0);
                for (int i=0;i<count;i++){
                    blank.append(" ");
                }
//                blank.append("          ");//10
                blank.append(text);
                mContent.setText(blank.toString());

                //后面
                ImageSpan endImageSpan = new ImageSpan(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
                SpannableString end=new SpannableString(" ");
                end.setSpan(endImageSpan,0,1,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                mContent.append(end);
            }
        });

    }
}
