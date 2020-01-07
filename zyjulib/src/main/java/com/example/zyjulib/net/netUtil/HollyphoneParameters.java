package com.example.zyjulib.net.netUtil;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A list queue for saving keys and values.
 * Using it to construct http header or get/post parameters.
 */
public class HollyphoneParameters {

	private Bundle mParameters = new Bundle();
	private List<String> mKeys = new ArrayList<String>();
	
	public void add(String key, String value){
		if(mKeys.contains(key)){
			mParameters.putString(key, value);
		}else{
			mKeys.add(key);
			mParameters.putString(key, value);
		}
	}
	
	
	public void remove(String key){
		mKeys.remove(key);
		mParameters.remove(key);
	}
	
	public void remove(int i){
		String key = mKeys.get(i);
		mParameters.remove(key);
		mKeys.remove(key);
	}
	
	
	public int getLocation(String key){
		if(mKeys.contains(key)){
			return mKeys.indexOf(key);
		}
		return -1;
	}
	
	public String getKey(int location){
		if(location >= 0 && location <mKeys.size()){
			return mKeys.get(location);
		}
		return "";
	}
	
	
	public String getValue(String key){
		return mParameters.getString(key);
	}
	
	public String getValue(int location){
		String key = mKeys.get(location);
		return mParameters.getString(key);

	}
	
	
	public int size(){
		return mKeys.size();
	}
	
	public void addAll(HollyphoneParameters parameters){
		for(int i = 0; i < parameters.size(); i++){
			add(parameters.getKey(i), parameters.getValue(i));
		}
		
	}

	public void addCommonAction(CommonAction mCommonAction){
		for(Map.Entry<String, Object> entry :mCommonAction.getParameters().entrySet()){
			add(entry.getKey(), entry.getValue().toString());
		}
	}
	
	public void clear(){
		mKeys.clear();
		mParameters.clear();
	}
	
}
