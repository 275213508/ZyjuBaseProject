package com.example.zyjulib.utile;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;

import com.example.zyjulib.interf.CommonInterface;

import java.util.List;

/**
 * dialog显示的基本工具类
 */
public class DialogUtils {
    public static DialogUtils instenc;

    private DialogUtils() {
    }

    public static DialogUtils getInstance() {
        if (instenc == null) {
            instenc = new DialogUtils();
        }
        return instenc;
    }

    /**
     * 设置文本显示，单个按钮，按钮没有功能
     */
    public void showCustomTextViewDialog(Context context, String title, String message, boolean isLeft, String positive) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setShowType(0)
                .setTitle(title)
                .setMessage(message)
                .setTextShowLeft(isLeft)
                .setPositiveButton(positive, new CommonInterface.DialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String item) {
                        dialog.dismiss();
                    }
                });
        builder.onCreate().show();
    }

    /**
     * 设置文本显示，单个按钮，按钮具有功能
     */
    public void showCustomTextViewDialog(Context context, String title, String message, boolean isLeft, String positive, final CommonInterface.PositiveListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setShowType(0)
                .setTitle(title)
                .setMessage(message)
                .setTextShowLeft(isLeft)
                .setLeftBlue(true)
                .setPositiveButton(positive, new CommonInterface.DialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String item) {
                        dialog.dismiss();
                        listener.onPositive();
                    }
                });
        builder.onCreate().show();
    }

    /**
     * 文本显示，两个按钮，左按钮具有功能
     * @param context
     * @param title
     * @param message
     * @param isLeft 是否显示左按钮
     * @param positive
     * @param negative
     * @param listener
     */
    public void showCustomTextViewDialog(Context context, String title, String message, boolean isLeft, boolean isLeftBlue, boolean isRightBlue,String positive, String negative, final CommonInterface.PositiveListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setShowType(0)
                .setTitle(title)
                .setMessage(message)
                .setTextShowLeft(isLeft)
                .setRightBlue(isRightBlue)
                .setLeftBlue(isLeftBlue)
                .setPositiveButton(positive, new CommonInterface.DialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String item) {
                        dialog.dismiss();
                        listener.onPositive();
                    }
                })
                .setNegativeButton(negative, new CommonInterface.DialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String item) {
                        dialog.dismiss();
                    }
                });
        builder.onCreate().show();
    }

    /**
     * 文本显示，两个按钮，左按钮具有功能
     */
    public void showCustomTextViewDialogToTransmit(Context context, String title, String message, boolean isLeft, String positive, String negative, final CommonInterface.PositiveListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setShowType(0)
                .setTitle(title)
                .setMessage(message)
                .setTextShowLeft(isLeft)
                .setLeftBlue(true)
                .setRightBlue(false)
                .setPositiveButton(positive, new CommonInterface.DialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String item) {
                        dialog.dismiss();
                        listener.onPositive();
                    }
                })
                .setNegativeButton(negative, new CommonInterface.DialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String item) {
                        dialog.dismiss();
                    }
                });
        builder.onCreate().show();
    }

    /**
     * 文本显示，两个按钮，右按钮具有功能
     *  @param context
     * @param title
     * @param message
     * @param isLeft      是否显示左边按钮
     * @param isLeftBlue  左边按钮是否是蓝色
     * @param isRightBlue 右边按钮是否蓝色
     * @param positive
     * @param negative
     * @param listener
     */
    public CustomDialog.Builder showCustomTextViewDialog(Context context, String title, String message, boolean isLeft, boolean isLeftBlue, boolean isRightBlue, String positive, String negative, final CommonInterface.NegativeListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setShowType(0)
                .setTitle(title)
                .setMessage(message)
                .setTextShowLeft(isLeft)
                .setRightBlue(isRightBlue)
                .setLeftBlue(isLeftBlue)
                .setPositiveButton(positive, new CommonInterface.DialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String item) {
                        dialog.dismiss();

                    }
                })
                .setNegativeButton(negative, new CommonInterface.DialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String item) {
                        dialog.dismiss();
                        listener.onNegative();
                    }
                });
        builder.onCreate().show();
        return builder;
    }

    /**
     * 文本显示，两个按钮，都具有功能
     * @param context
     * @param title
     * @param message
     * @param isLeft 是否显示左按钮
     * @param positive
     * @param negative
     * @param listener
     */
    public void showCustomTextViewDialog(Context context, String title, String message, boolean isLeft, String positive, String negative, final CommonInterface.PosAndNegtiveListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setShowType(0)
                .setTitle(title)
                .setLeftBlue(true)
                .setMessage(message)
                .setTextShowLeft(isLeft)
                .setPositiveButton(positive, new CommonInterface.DialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String item) {
                        dialog.dismiss();
                        listener.onPosittive();
                    }
                })
                .setNegativeButton(negative, new CommonInterface.DialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String item) {
                        dialog.dismiss();
                        listener.onNegative();
                    }
                });
        builder.onCreate().show();
    }

    /**
     * EditText显示，左按钮回调输入的文字
     */
    public void showCustomEditTextDialog(Context context, String title, String message, String hint, boolean isLeft, String positive, String negative, final CommonInterface.PositiveCallBackListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setShowType(2)
                .setTitle(title)
                .setMessage(message)
                .setHint(hint)
                .setTextShowLeft(isLeft)
                .setPositiveButton(positive, new CommonInterface.DialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String item) {
                        dialog.dismiss();
                        listener.onPositive(item);
                    }
                })
                .setNegativeButton(negative, new CommonInterface.DialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String item) {
                        dialog.dismiss();
                    }
                });
        builder.onCreate().show();
    }

    /**
     * EditText 显示单个输入框
     */
    public void showSingleEditTextDialog(Context context, String title, String message, String hint, final String prompty, String positive, String negative, final CommonInterface.PositiveCallBackListener listener) {
        EditTextDialog.Builder builder = new EditTextDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message, hint)
                .setPositiveButton(positive, new EditTextDialog.EditTextDialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String message0, String message1) {
                        if (!TextUtils.isEmpty(prompty) && TextUtils.isEmpty(message0)) {
                            ToastUtil.getInstance().showToast(prompty);
                        } else {
                            dialog.dismiss();
                            listener.onPositive(message0);
                        }
                    }
                })
                .setNegativeButton(negative, new EditTextDialog.EditTextDialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String message0, String message1) {
                        dialog.dismiss();
                    }
                });
        builder.onCreate().show();
    }

    /**
     * EditText 显示单个输入框
     */
    public void showDoubleEditTextDialog(Context context, String title, String message0, String message1, String hint0, String hint1, final String prompty0, final String prompty1, String positive, String negative, final CommonInterface.PositiveDoubleCallBackListener listener) {
        EditTextDialog.Builder builder = new EditTextDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message0, message1, hint0, hint1)
                .setPositiveButton(positive, new EditTextDialog.EditTextDialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String message0, String message1) {
                        if (!TextUtils.isEmpty(prompty0) && TextUtils.isEmpty(message0)) {
                            ToastUtil.getInstance().showToast(prompty0);
                            return;
                        }

                        if (!TextUtils.isEmpty(prompty1) && TextUtils.isEmpty(message1)) {
                            ToastUtil.getInstance().showToast(prompty1);
                            return;
                        }
                        dialog.dismiss();
                        listener.onDoublePositive(message0, message1);

                    }
                })
                .setNegativeButton(negative, new EditTextDialog.EditTextDialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String message0, String message1) {
                        dialog.dismiss();
                    }
                });
        builder.onCreate().show();
    }

    /**
     * ListView显示
     */
    public void showCustomListViewDialog(Context context, String title, List<String> data, String positive, CommonInterface.SelectItemListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setShowType(1)
                .setTitle(title)
                .setMessage(data)
                .setSelectItemListener(listener)
                .setPositiveButton(positive, new CommonInterface.DialogOnClick() {
                    @Override
                    public void onClick(Dialog dialog, String item) {
                        dialog.dismiss();
                    }
                });
        builder.onCreate().show();
    }

    public static boolean isShowLoginDialog = false;


}
