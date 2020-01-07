package com.example.zyjulib.utile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;

/**
 * Created by GCS on 2017-03-22.
 */

public class AnimationUtil {

    private static AnimationUtil instenc;

    private AnimationUtil() {
    }
    public static AnimationUtil getInstance(){
        if(instenc==null){
            instenc = new AnimationUtil();
        }
        return instenc;
    }
    @NonNull
    public ObjectAnimator getObjectAnimator(View mShowEdt, String set, float v, float v2, int time) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mShowEdt, set, v, v2);
        animator.setDuration(time);
        animator.start();
        return animator;
    }
    @NonNull
    public ObjectAnimator getObjectAnimators(View mShowEdt, String set, float v, float v2,int time) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mShowEdt, set,v,v2);
        animator.setDuration(time);
        animator.setRepeatCount(3);
        animator.start();
        return animator;
    }

    public ObjectAnimator startRotation(View view, float v) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", v);
        objectAnimator.setDuration(300);
        objectAnimator.start();
        return objectAnimator;
    }
    public ObjectAnimator startRotation(View view, float v,int duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", v);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
        return objectAnimator;
    }

    /**
     * 显示编辑区
     */
    public void showEditInfo(View view, float translationY, float endtranslationY) {
        getObjectAnimator(view, "translationY", translationY, endtranslationY, 300);
    }

    /**
     * 弹跳动画
     *
     * @param imageView
     */
    public void beginTransAtranslationYnimation(final ImageView... imageView) {
        for (int i = 0; i < imageView.length; i++) {
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageView[i], "translationY", 600, -100);
            ObjectAnimator objectAnimatorY2 = ObjectAnimator.ofFloat(imageView[i], "translationY", -100, 80);
            ObjectAnimator objectAnimatorY3 = ObjectAnimator.ofFloat(imageView[i], "translationY", 80, -60);
            ObjectAnimator objectAnimatorY4 = ObjectAnimator.ofFloat(imageView[i], "translationY", -60, 40);
            ObjectAnimator objectAnimatorY5 = ObjectAnimator.ofFloat(imageView[i], "translationY", 40, -30);
            ObjectAnimator objectAnimatorY6 = ObjectAnimator.ofFloat(imageView[i], "translationY", -30, 0);
//        ObjectAnimator objectAnimatorY7 = ObjectAnimator.ofFloat(imageView, "translationY", 10, 0);
//        objectAnimatorY.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                imageView.setVisibility(View.INVISIBLE);
//            }
//        });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(150);
            animatorSet.playSequentially(objectAnimatorY, objectAnimatorY2, objectAnimatorY3, objectAnimatorY4, objectAnimatorY5, objectAnimatorY6);
//            animatorSet.play(objectAnimatorX).with(objectAnimatorY);
            if (i != 0) {
                animatorSet.setStartDelay(50);
                animatorSet.start();
            } else {
                animatorSet.start();
            }
        }
    }

    /**
     * 心跳动画
     * @param imageView
     */
    public void PantAnimation(View imageView){
        ObjectAnimator objectAnimatorY1 = ObjectAnimator.ofFloat(imageView, "scaleX", 1, 1.5f,1);//缩放
        ObjectAnimator objectAnimatorY2 = ObjectAnimator.ofFloat(imageView, "scaleY", 1, 1.5f,1);//缩放
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorY1, objectAnimatorY2);
        animatorSet.setDuration(300);
        animatorSet.start();
    }

    /**
     * 选择按钮抖动动画
     *
     * @param imageView
     */
    @SuppressLint("ObjectAnimatorBinding")
    public void TableSelectAnimation(final ImageView imageView) {

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageView, "rotation", 0,-25,20,-16);//旋转
        ObjectAnimator objectAnimatorY1 = ObjectAnimator.ofFloat(imageView, "scaleX", 1, 1.5f,1);//缩放
        ObjectAnimator objectAnimatorY2 = ObjectAnimator.ofFloat(imageView, "scaleY", 1, 1.5f,1);//缩放
        ObjectAnimator objectAnimatorY3 = ObjectAnimator.ofFloat(imageView, "alpha", 0, 1);//透明度
        final ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageView, "rotation", -12,12,-6,0);//旋转
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator1,objectAnimatorY1, objectAnimatorY2, objectAnimatorY3);
        animatorSet.setDuration(200);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        animatorSet.start();
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                objectAnimatorY.start();
            }
        },animatorSet.getDuration());

    }

    /**
     * 揭露动画
     * @param view 要揭露的view
     * @param x 起始圆心x坐标
     * @param y 起始圆心Y坐标
     * @param r 半径
     */
    private void StartAnim(View view,int x,int y,int r) {
        int cicular_R = 3000;
        Animator animator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animator = ViewAnimationUtils.createCircularReveal(view, x, y, 0, r);
        }
        animator.setDuration(1000);
        animator.start();
    }
}
