package com.example.zyjulib.utile;

import android.text.Html;
import android.text.Spanned;

public class TextUtil {
    /**
     * 给textView加下划线
     *
     * @param str
     * @return
     */
    public Spanned getLineForText(String str) {
        return Html.fromHtml("<u>" + str + "<u/>");
    }

}
