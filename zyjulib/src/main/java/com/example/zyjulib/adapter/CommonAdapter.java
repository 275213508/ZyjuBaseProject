package com.example.zyjulib.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 适合所有的ListView和GridView的Adapter(只适合单中Item，因为多种Item要通过数据源中对应的一个类型属性来作为判断)
 * @author 杨溢
 *
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

	private List<T> mDates;
	private Context context;
	private int itemLayoutId;
	
	/**
	 * 
	 * @param context
	 * @param mDates 数据源
	 * @param itemLayoutId item布局id
	 */
	public CommonAdapter(Context context, List<T> mDates, int itemLayoutId){
		this.context=context;
		this.mDates=mDates;
		this.itemLayoutId=itemLayoutId;
	}
	
	@Override
	public int getCount() {
		if(mDates==null){
			return 0;
		}
		return mDates.size();
	}

	@Override
	public T getItem(int position) {
		return mDates.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * 此处采用通过性ViewHolder来做
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommonViewHolder commonViewHolder=CommonViewHolder.getViewHolder(context, convertView, parent, itemLayoutId);
		//设置一个抽象方法，用于外部对getView的布局进行设置
		setView(commonViewHolder,position,getItem(position));
		return commonViewHolder.getConvertView();
	}
	
	//设置抽象方法来设置item布局中控件的样式和点击事件
	public abstract void setView(CommonViewHolder commonViewHolder,int position,T item);

}
