package com.example.zyjulib.utile;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zyjulib.R;
import com.example.zyjulib.view.ClearEditText;


public class EditTextDialog extends Dialog {

    public EditTextDialog(Context context) {
        super(context, R.style.Dialog_NoTitle);
    }

    public interface EditTextDialogOnClick {
        void onClick(Dialog dialog, String message0, String message1);
    }


    public static class Builder {

        private Context context;
        /**
         * 显示类型 0-显示一个,1-显示2个
         */
        private int showType = 0;
        private String title = "";
        private String message0 = "";
        private String hint0 = "";
        private String message1 = "";
        private String hint1 = "";

        private String positive;
        private String negative;
        private EditTextDialogOnClick positiveButtonClickListener;
        private EditTextDialogOnClick negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置标题
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置EditText内容
         */
        public Builder setMessage(String message0, String hint0) {
            this.message0 = message0;
            this.hint0 = hint0;
            return this;
        }

        /**
         * 设置EditText内容
         */
        public Builder setMessage(String message0, String message1, String hint0, String hint1) {
            this.message0 = message0;
            this.hint0 = hint0;
            this.message1 = message1;
            this.hint1 = hint1;
            this.showType = 1;
            return this;
        }


        /**
         * 底部按钮
         */
        public Builder setPositiveButton(String positive, EditTextDialogOnClick listener) {
            this.positive = positive;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * 底部按钮
         */
        public Builder setNegativeButton(String negative, EditTextDialogOnClick listener) {
            this.negative = negative;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public EditTextDialog onCreate() {
            View inflate = View.inflate(context, R.layout.dialog_edittext, null);
            TextView tv_title = inflate.findViewById(R.id.tv_title_editTextDialog);
            LinearLayout ll1 = inflate.findViewById(R.id.ll1_editTextDialog);
            final ClearEditText et0 = inflate.findViewById(R.id.et0_editTextDialog);
            final ClearEditText et1 = inflate.findViewById(R.id.et1_editTextDialog);
            Button bt0 = inflate.findViewById(R.id.bt0_editTextDialog);
            Button bt1 = inflate.findViewById(R.id.bt1_editTextDialog);

            final EditTextDialog dialog = new EditTextDialog(context);
            dialog.setContentView(inflate);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.width = CommonUtils.getScreenWidth()/7*5;
                window.setAttributes(attributes);
            }

            tv_title.setText(title);

            if (showType == 0) {
                et0.setText(message0);
                et0.setHint(hint0);
                et0.setSelection(message0.length());
                ll1.setVisibility(View.GONE);
            } else if (showType == 1) {
                et0.setText(message0);
                et0.setHint(hint0);
                et1.setText(message1);
                et1.setHint(hint1);
                et0.setSelection(message0.length());
                et1.setSelection(message1.length());
            }

            if (positive == null) {
                bt0.setVisibility(View.GONE);
            } else {
                bt0.setText(positive);
                bt0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        positiveButtonClickListener.onClick(dialog, et0.getText().toString(), et1.getText().toString());
                    }
                });
            }

            if (negative == null) {
                bt1.setVisibility(View.GONE);
            } else {
                bt1.setText(negative);
                bt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        negativeButtonClickListener.onClick(dialog, et0.getText().toString(), et1.getText().toString());
                    }
                });
            }
            return dialog;
        }
    }

}
