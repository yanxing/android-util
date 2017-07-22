package com.yanxing.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanxing.ui.R;

/**
 * 对话框
 * Created by 李双祥 on 2017/5/24.
 */
public class LoadDialog extends DialogFragment {

    private TextView mTip;
    public static final String TAG="com.dianmei.view.LoadDialog";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.loading_dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.loading_dialog, container);
        mTip= (TextView) view.findViewById(R.id.tip);
        Bundle bundle=getArguments();
        if (bundle!=null){
            String tip=bundle.getString("tip");
            if (tip!=null){
                mTip.setText(tip);
            }
        }
        getDialog().setCanceledOnTouchOutside(false);
        return view;
    }
}
