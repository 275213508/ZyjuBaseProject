package com.example.zyjulib.net.netUtil;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Random;

/**
 * 封装请求参数的(Map集合)
 */
public class CommonAction {

	public static final String Command = "Command";
	public static final String Action = "Action";
	public static final String ActionID = "ActionID";

	private String actionName;

	private String actionID;

	private HashMap<String, Object> parameters;

	public CommonAction(String actName, String actionID) {
		this.actionName = actName;
		this.actionID = actionID;
		parameters = new HashMap<>();
		parameters.put(Command, Action);
		parameters.put(Action, actionName);
		parameters.put(ActionID, actionID);
	}
	
	public CommonAction(String actName) {
		this.actionName = actName;
		long r = new Random().nextLong();
		r=(r < 0) ? (-r) : r;
		this.actionID =actName.trim()+r;
		parameters = new HashMap<>();
//		parameters.put(Command, Action);
		parameters.put(Action, actionName);
		parameters.put(ActionID, actionID);
	}

	public CommonAction(){
		parameters = new HashMap<>();
	}

	/**
	 * 添加参数
	 * @param key 键
	 * @param value 值
	 */
	public void addParameter(String key, Object value) {
		parameters.put(key, value);
	}

	public Object getParameter(String key){
		if(parameters.containsKey(key)){
			return parameters.get(key);
		}
		return "";
	}

	public String getStringParameter(String key){
		if(parameters.containsKey(key)){
			return (String) parameters.get(key);
		}
		return "";
	}



	public HashMap<String, Object> getParameters() {
		return parameters;
	}

	public String getJsonString() {
		return JSON.toJSONString(parameters);
	}

	public String getString(){
		return parameters.toString();
	}
}
