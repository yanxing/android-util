package com.yanxing.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.swipelistviewlibrary.view.SwipeItemLayout;
import com.yanxing.ui.R;

import java.util.List;


/**
 * xRecycler适配器
 */
public class SwipeAdapter extends BaseAdapter {

	private List<String> mStrings;

	public SwipeAdapter(List<String> list) {
		this.mStrings=list;
	}

	@Override
	public int getCount() {
		return mStrings.size();
	}

	@Override
	public Object getItem(int i) {
		return mStrings.get(i);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup parent) {
		ViewHolder holder = null;
		if(contentView==null){
			holder = new ViewHolder();
			View view01 = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_content, null);
			View view02 = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_content_menu, null);
////			holder.btn = (Button)view02.findViewById(R.id.btn);
			contentView = new SwipeItemLayout(view01, view02, null, null);
			contentView.setTag(holder);
		}else{
			holder = (ViewHolder) contentView.getTag();
		}
		return contentView;
	}
	
	class ViewHolder{
		Button btn;
	}
}
