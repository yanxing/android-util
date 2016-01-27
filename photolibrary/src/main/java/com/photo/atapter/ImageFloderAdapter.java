package com.photo.atapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.photo.R;
import com.photo.model.ImageFloder;
import com.photo.util.ImageLoader;

import java.util.List;

/**
 * 相册列表类
 */
public class ImageFloderAdapter extends BaseAdapter {
	
	private Context context;
	private List<ImageFloder> list;
	
	public ImageFloderAdapter(Context context, List<ImageFloder> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void changeData(List<ImageFloder> list){
		this.list=list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public ImageFloder getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView= LayoutInflater.from(context).inflate(R.layout.photo_file_list_item, null);
			holder=new ViewHolder();
			holder.dirItemIcon=(ImageView)convertView.findViewById(R.id.id_dir_choose);
			holder.dirItemImage=(ImageView)convertView.findViewById(R.id.id_dir_item_image);
			holder.dirItemName=(TextView)convertView.findViewById(R.id.id_dir_item_name);
			holder.dirItemNum=(TextView)convertView.findViewById(R.id.id_dir_item_count);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(list.get(position).getFirstImagePath(), holder.dirItemImage);
		holder.dirItemName.setText(list.get(position).getName());
		holder.dirItemNum.setText(list.get(position).getCount()+"张");
		if(list.get(position).isSelected())
			holder.dirItemIcon.setVisibility(View.VISIBLE);
		else
			holder.dirItemIcon.setVisibility(View.INVISIBLE);
		return convertView;
	}

	private static class ViewHolder{
		ImageView dirItemImage;
		TextView dirItemName;
		TextView dirItemNum;
		ImageView dirItemIcon;
	}
	


}
