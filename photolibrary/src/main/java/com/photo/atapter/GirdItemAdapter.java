package com.photo.atapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.photo.R;
import com.photo.util.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class GirdItemAdapter extends BaseAdapter {
	
	private final int VIEW_TYPE = 2;
	
	private final int TYPE_1 = 0;
	
	private final int TYPE_2 = 1;

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static List<String> mSelectedImage = new ArrayList<String>();
	/**
	 * 文件夹路径
	 */
	private String mDirPath;

	private Context context;

	private List<String> mDatas=new ArrayList<String>();//所有的图片


	public GirdItemAdapter(Context context, List<String> mDatas,String dirPath) {
		super();
		this.context = context;
		this.mDatas = mDatas;
		this.mDirPath=dirPath;
	}

	public void changeData(List<String> mDatas,String dirPath){
		this.mDatas = mDatas;
		this.mDirPath=dirPath;
		notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		return mDatas.size()+1;
	}

	@Override
	public String getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public int getItemViewType(int position) {
		if(position==0){
			return TYPE_1;
		}else{
			return TYPE_2;
		}
	}
	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE;
	}
	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		ViewHolder2 holder2 = null;
		int type = getItemViewType(position);
		if(convertView==null){
			switch (type) {
			case TYPE_1:
				convertView= LayoutInflater.from(context).inflate(R.layout.grid_item2, null);
				holder2=new ViewHolder2();
				holder2.id_item_image2=(LinearLayout)convertView.findViewById(R.id.id_item_image2);
				convertView.setTag(holder2);
				break;
			case TYPE_2:
				convertView= LayoutInflater.from(context).inflate(R.layout.grid_item_photo, null);
				holder=new ViewHolder();
				holder.id_item_image=(ImageView)convertView.findViewById(R.id.id_item_image);
//				holder.id_item_select=(ImageButton)convertView.findViewById(R.id.id_item_select);
				convertView.setTag(holder);
				break;
			}
		}else{
			switch (type) {
			case TYPE_1:
				holder2=(ViewHolder2) convertView.getTag();
				break;
			case TYPE_2:
				holder=(ViewHolder) convertView.getTag();
				break;

			}
		}
		switch (type) {
		case TYPE_1:
			holder2.id_item_image2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onPhotoSelectedListener.takePhoto();
//					onPhotoSelectedListener.photoPath(mDirPath);
				}
			});
			break;
		case TYPE_2:
//			holder.id_item_select.setBackgroundResource(R.drawable.picture_unselected);
//			holder.id_item_image.setBackgroundResource(R.drawable.pictures_no);
			ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(mDirPath + "/" + mDatas.get(position-1), holder.id_item_image);
			holder.id_item_image.setColorFilter(null);
			//设置ImageView的点击事件
			holder.id_item_image.setOnClickListener(new MyOnClickListener(holder, position));
			/**
			 * 已经选择过的图片，显示出选择过的效果
			 */
//			if (mSelectedImage.contains(mDirPath + "/" + mDatas.get(position-1))){
//				holder.id_item_select.setImageResource(R.drawable.pictures_selected);
//				holder.id_item_image.setColorFilter(Color.parseColor("#77000000"));
//			}else{
//				holder.id_item_select.setImageResource(R.drawable.picture_unselected);
//				holder.id_item_image.setColorFilter(Color.parseColor("#00000000"));
//			}
			break;
		}
		return convertView;
	}
	
	private class MyOnClickListener implements OnClickListener {
		
		ViewHolder holder;
		
		int position;
		
		MyOnClickListener(ViewHolder holder,int  position){
			this.holder=holder;
			this.position=position;
		}
		
		@Override
		public void onClick(View v) {
			// 已经选择过该图片
			mSelectedImage.clear();
//			if (mSelectedImage.contains(mDirPath + "/" + mDatas.get(position-1))){
//				mSelectedImage.remove(mDirPath + "/" + mDatas.get(position-1));
//				holder.id_item_select.setImageResource(R.drawable.picture_unselected);
//				holder.id_item_image.setColorFilter(null);
//			} else{// 未选择该图片
//				if(mSelectedImage.size()>=1){
//					return ;
//				}
				mSelectedImage.add(mDirPath + "/" + mDatas.get(position-1));
//				holder.id_item_select.setImageResource(R.drawable.pictures_selected);
//				holder.id_item_image.setColorFilter(Color.parseColor("#77000000"));
//			}
			onPhotoSelectedListener.photoClick(mSelectedImage);
			// 得到每张图片的路径
//			for (String string : mSelectedImage) {
				onPhotoSelectedListener.photoPath(mSelectedImage.get(0));
//			}
		}
	}
	class ViewHolder{
		ImageView id_item_image;
//		ImageButton id_item_select;
	}
	class ViewHolder2{
		LinearLayout id_item_image2;
	}
	public OnPhotoSelectedListener onPhotoSelectedListener;
	public void setOnPhotoSelectedListener(OnPhotoSelectedListener onPhotoSelectedListener){
		this.onPhotoSelectedListener=onPhotoSelectedListener;
	}
	public  interface OnPhotoSelectedListener{
		public void photoClick(List<String> number);

		/**
		 * 拍照
		 */
		public void takePhoto();

		/**
		 * 选择已存在照片
		 * @param path
         */
		public void photoPath(String path);
	}

}
