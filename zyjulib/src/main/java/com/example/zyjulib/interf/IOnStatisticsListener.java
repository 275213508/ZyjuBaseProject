package com.example.zyjulib.interf;

import android.content.Context;

import java.util.Map;

public interface IOnStatisticsListener {
    void onStatistics(Context context, String eventID, Map<String, Object> music);
}
