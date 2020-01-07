package com.example.zyjulib.utile;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import com.example.zyjulib.interf.IVisible;


public class CostemRelativelayout extends RelativeLayout {
    private IVisible iVisible;

    public CostemRelativelayout(Context context) {
        super(context);
    }

    public CostemRelativelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CostemRelativelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CostemRelativelayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if(iVisible!=null)
        iVisible.getVisibility(visibility);
    }
    public void setVisibilityListenner(IVisible iVisible){
        this.iVisible = iVisible;
    }

}
