package com.example.zyjulib.net.netUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NetEncode {

    public static String encodeUrl(HollyphoneParameters parameters) {
        if (parameters == null || parameters.size() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        try {
            for (int loc = 0; loc < parameters.size(); loc++) {
                if (!parameters.getValue(loc).equals("\"\"")) {
                    sb.append(parameters.getKey(loc) + "=" + URLEncoder.encode(parameters.getValue(loc), "UTF-8"));
//			sb.append(parameters.getKey(loc) + "=" + parameters.getValue(loc));
                    sb.append("&");
                } else {
                    sb.append(parameters.getKey(loc) + "=" + parameters.getValue(loc));
//			sb.append(parameters.getKey(loc) + "=" + parameters.getValue(loc));
                    sb.append("&");
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}