package com.example.zyjulib.utile;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zyjulib.R;
import com.example.zyjulib.adapter.CommonAdapter;
import com.example.zyjulib.adapter.CommonViewHolder;
import com.example.zyjulib.interf.CommonInterface;

import java.util.List;


public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static class Builder {

        private Context context;
        /**
         * 0-内容为TextView 1-内容为ListView 2-内容为EditText
         */
        private int showType;
        private String title = "";
        private String message = "";
        private boolean isLeft;
        private String hint = "";
        private List<String> data;

        private String positive;
        private String negative;
        private CommonInterface.DialogOnClick positiveButtonClickListener;
        private CommonInterface.DialogOnClick negativeButtonClickListener;
        private CommonInterface.SelectItemListener selectItemListener;

        private boolean isLeftBlue;
        private boolean isRightBlue;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setShowType(int showType) {
            this.showType = showType;
            return this;
        }
        /**
         * 设置标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 内容为TextView和EditText使用
         *
         * @param message
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 内容为EditText使用
         *
         * @param hint
         * @return
         */
        public Builder setHint(String hint) {
            this.hint = hint;
            return this;
        }

        /**
         * 设置文本显示的时候，文字显示是否靠左显示，默认是显示在中部
         *
         * @param isLeft
         * @return
         */
        public Builder setTextShowLeft(boolean isLeft) {
            this.isLeft = isLeft;
            return this;
        }

        /**
         * 该方法是内容为ListView时候使用
         *
         * @param data 数据源
         * @return
         */
        public Builder setMessage(List<String> data) {
            this.data = data;
            return this;
        }

        /**
         * 该方法是设置左边的Button按钮 背景色为蓝色
         *
         * @param isLeftBlue
         * @return
         */
        public Builder setLeftBlue(boolean isLeftBlue) {
            this.isLeftBlue = isLeftBlue;
            return this;
        }

        /**
         * 该方法是设置右边的Button按钮 背景色为蓝色
         * @param isRightBlue
         * @return
         */
        public Builder setRightBlue(boolean isRightBlue) {
            this.isRightBlue = isRightBlue;
            return this;
        }

        /**
         * 设置显示内容为ListView的时候，点击回调使用
         *
         * @param listener
         * @return
         */
        public Builder setSelectItemListener(CommonInterface.SelectItemListener listener) {
            this.selectItemListener = listener;
            return this;
        }

        /**
         * 底部按钮
         *
         * @param positive
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positive, CommonInterface.DialogOnClick listener) {
            this.positive = positive;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * 底部按钮
         *
         * @param negative
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negative, CommonInterface.DialogOnClick listener) {
            this.negative = negative;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public CustomDialog onCreate() {
            View inflate = View.inflate(context, R.layout.dialog_custom, null);
            TextView tv_dialog_title = (TextView) inflate.findViewById(R.id.tv_dialog_title);
            final EditText et_dialog_message = (EditText) inflate.findViewById(R.id.et_dialog_message);
            ListView lv_dialog_message = (ListView) inflate.findViewById(R.id.lv_dialog_message);
            TextView bt_dialog_positive = (TextView) inflate.findViewById(R.id.bt_dialog_positive);
            TextView bt_dialog_negative = (TextView) inflate.findViewById(R.id.bt_dialog_negative);
            final CustomDialog dialog = new CustomDialog(context, R.style.Dialog_NoTitle);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);

            if (TextUtils.isEmpty(title)) {
//                tv_dialog_title.setVisibility(View.GONE);
                tv_dialog_title.setText("提示");
            } else {
                tv_dialog_title.setText(title);
            }
            if (showType == 0) {
                et_dialog_message.setFocusable(false);
                et_dialog_message.setText(message);
                if (TextUtils.isEmpty(message)) {
                    et_dialog_message.setVisibility(View.GONE);
                }
            } else if (showType == 1) {
                et_dialog_message.setVisibility(View.GONE);
                lv_dialog_message.setVisibility(View.VISIBLE);
                MyListAdapter adapter = new MyListAdapter(context, data, R.layout.item_custom_dialog);
                lv_dialog_message.setAdapter(adapter);
                //设置其最大高度
                setListViewHeightBasedOnChildren(lv_dialog_message);
                lv_dialog_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (selectItemListener != null) {
                            dialog.dismiss();
                            selectItemListener.onSelect(position);
                        }
                    }
                });
            } else if (showType == 2) {
                et_dialog_message.setFocusable(true);
                et_dialog_message.setText(message);
                et_dialog_message.setHint(hint);
            }

            if (isLeft) {
                et_dialog_message.setGravity(Gravity.LEFT);
            }
            if(isLeftBlue){
                bt_dialog_positive.setBackgroundResource(R.drawable.bg_button_click);
                bt_dialog_positive.setTextColor(Color.WHITE);
            }else{
                bt_dialog_positive.setBackgroundResource(R.drawable.bg_border_red_);
                bt_dialog_positive.setTextColor(Color.RED);
            }
            if(isRightBlue){
                bt_dialog_negative.setBackgroundResource(R.drawable.bg_button_click);
                bt_dialog_negative.setTextColor(Color.WHITE);
            }else{
                bt_dialog_negative.setBackgroundResource(R.drawable.bg_border_red_);
                bt_dialog_negative.setTextColor(Color.RED);
            }
            if (positive == null) {
                bt_dialog_positive.setVisibility(View.GONE);
            } else {
                bt_dialog_positive.setText(positive);
                bt_dialog_positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (showType == 2) {
                            positiveButtonClickListener.onClick(dialog, et_dialog_message.getText().toString());
                        } else {
                            positiveButtonClickListener.onClick(dialog, "");
                        }
                    }
                });
            }

            if (negative == null) {
                bt_dialog_negative.setVisibility(View.GONE);
            } else {
                bt_dialog_negative.setText(negative);

                bt_dialog_negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        negativeButtonClickListener.onClick(dialog, "");
                    }
                });
            }
            dialog.setContentView(inflate);
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.width = CommonUtils.getScreenWidth() / 7 * 6;
                window.setAttributes(attributes);
            }
            return dialog;
        }

        /**
         * 设置ListView的最大高度
         *
         * @param listView
         */
        private void setListViewHeightBasedOnChildren(ListView listView) {

            ListAdapter listAdapter = listView.getAdapter();

            if (listAdapter == null) {
                return;
            }

            int totalHeight = 0;

            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();

            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

            if (params.height > CommonUtils.getScreenHeight() / 4 * 3) {
                params.height = CommonUtils.getScreenHeight() / 4 * 3;
            }

            listView.setLayoutParams(params);
        }

        private class MyListAdapter extends CommonAdapter<String> {

            /**
             * @param context
             * @param mDates       数据源
             * @param itemLayoutId item布局id
             */
            public MyListAdapter(Context context, List<String> mDates, int itemLayoutId) {
                super(context, mDates, itemLayoutId);
            }

            @Override
            public void setView(CommonViewHolder commonViewHolder, int position, String item) {
                TextView tv = commonViewHolder.getView(R.id.tv_item_custom_dialog);
                tv.setText(item);
            }
        }
    }

}
