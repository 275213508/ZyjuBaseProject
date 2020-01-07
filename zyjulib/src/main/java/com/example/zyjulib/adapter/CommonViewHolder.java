package com.example.zyjulib.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CommonViewHolder {

	/*
	 * 定义一个容器来存储对应Item布局的控件。
	 * SparseArray相当于一个Map集合，但是比Map集合效率高，但是键只能为Integer
	 */
	SparseArray<View> mViews;
	//与ViewHolder绑定的缓存
	private View mConvertView;

	/**
	 * 初始化CommonViewHolder，创建一个与缓存绑定的ViewHolder
	 * @param context
	 * @param parent
	 * @param layoutId
	 */
	private CommonViewHolder(Context context, ViewGroup parent, int layoutId){
		mViews=new SparseArray<>();
		LayoutInflater mInflater= LayoutInflater.from(context);
		//采用这种方法加载布局,其XML中的宽高设置有效果
		mConvertView = mInflater.inflate(layoutId, parent,false);
		mConvertView.setTag(this);
	}


	/**
	 *
	 * @param context
	 * @param convertView ListView和GridView的缓存布局
	 * @param parent
	 * @param layoutId ListView和GridView的Item的布局
	 * @return 与convertView绑定的ViewHolder
	 */
	public static CommonViewHolder getViewHolder(Context context, View convertView, ViewGroup parent, int layoutId){
		/*
		 * 判断是否有缓存
		 * 1.没有的话就证明其中没有绑定ViewHolder，那么就手动创建ViewHolder，且填充布局，然后将ViewHolder与其绑定
		 * 2.有的话，就直接获取
		 */
		if(convertView==null){
			Log.i("Meet6","创建holder");
			return new CommonViewHolder(context, parent, layoutId);
		}
		return (CommonViewHolder) convertView.getTag();
	}

	/**
	 * 获取控件
	 * @param id 控件对应的id
	 * @return 返回控件
	 */
	public <T extends View>T getView(int id){
		View view = mViews.get(id);
		if(view==null){
			view=mConvertView.findViewById(id);
			if(view!=null){
				mViews.put(id, view);
			}
		}
		return (T) view;
	}

	/**
	 * 获取绑定的布局
	 * @return
	 */
	public View getConvertView(){
		return mConvertView;
	}
}
