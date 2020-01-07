package com.example.zyjulib.utile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.core.widget.NestedScrollView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongyu on 8/8/2016.
 */
public class ScreenShot {

    private static ScreenShot instance;

    public static ScreenShot getInstance() {
        if (instance == null) {
            instance = new ScreenShot();
        }
        return instance;
    }

    // 获取指定Activity的截屏，保存到png文件
    public Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        System.out.println(statusBarHeight);

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    // 保存到sdcard
    public void savePic(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        Log.d("TAG", "savePic() returned: ");
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                Log.d("TAG", "savePic() returned:    " + b.getHeight());
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 程序入口 截取当前屏幕
    public void shootLoacleView(Activity a, String picpath) {
        savePic(takeScreenShot(a), picpath);
    }

    /**
     * 保存bitmap到SD卡
     *
     * @param mBitmap 图片对像
     *                return 生成压缩图片后的图片路径
     */
    @SuppressLint("SdCardPath")
    public String saveMyBitmap(Bitmap mBitmap) {
        String bitName = System.currentTimeMillis() + "";
        File f = new File(CommonUtils.getPicturePath() + bitName + ".png");

        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println("在保存图片时出错：" + e.toString());
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        } catch (Exception e) {
            return "create_bitmap_error";
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f.getAbsolutePath();
    }

    /**
     * 截取WebView的屏幕，
     * <p>
     * webView.capturePicture()方法在android 4.4（19）版本被废弃，
     * 不兼容 5.0+ 系统
     * 官方建议通过onDraw(Canvas)截屏
     * <p>
     * 在5.0及以上版本，Android为了降低WebView的内存消耗，对WebView进行了优化：可视区域内的HTML页面进行渲染，不可视区域的HTML显示为白屏，待需要显示时再进行渲染。
     * <p>
     * 解决办法是在设置布局文件的setContentView()方法前调用WebView.enableSlowWholeDocumentDraw()，其作用是禁止Android对WebView进行优化。
     * <p>
     * //系统版本不小于5.0
     * if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
     * WebView.enableSlowWholeDocumentDraw();
     * }
     *
     * @param webView
     * @return
     */
    private Bitmap captureWebView(WebView webView) {
        Picture snapShot = webView.capturePicture();
        Bitmap bitmap = Bitmap.createBitmap(snapShot.getWidth(),
                snapShot.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        snapShot.draw(canvas);
        return bitmap;
    }

    /**
     * 截取LinearLayout
     **/
    public Bitmap getLinearLayoutBitmap(LinearLayout linearLayout) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            h += linearLayout.getChildAt(i).getHeight();
            linearLayout.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(linearLayout.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        linearLayout.draw(canvas);
        return bitmap;
    }

    /**
     * http://stackoverflow.com/questions/12742343/android-get-screenshot-of-all-listview-items
     */
    public Bitmap shotListView(ListView listview) {

        ListAdapter adapter = listview.getAdapter();
        int itemscount = adapter.getCount();
        int allitemsheight = 0;
        List<Bitmap> bmps = new ArrayList<Bitmap>();

        for (int i = 0; i < itemscount; i++) {
            View childView = adapter.getView(i, null, listview);

            childView.measure(
                    View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
            childView.setDrawingCacheEnabled(true);
            childView.buildDrawingCache();

            bmps.add(childView.getDrawingCache());
            allitemsheight += childView.getMeasuredHeight();
            listview.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        int w = listview.getMeasuredWidth();
        Bitmap bigbitmap = Bitmap.createBitmap(w, allitemsheight, Bitmap.Config.ARGB_8888);
        Canvas bigcanvas = new Canvas(bigbitmap);

        Paint paint = new Paint();
        int iHeight = 0;

        for (int i = 0; i < bmps.size(); i++) {
            Bitmap bmp = bmps.get(i);
            bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
            iHeight += bmp.getHeight();

            bmp.recycle();
            bmp = null;
        }
        return bigbitmap;
    }

    /**
     * 截取scrollview的屏幕
     **/
    public Bitmap getScrollViewBitmap(NestedScrollView scrollView) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
//        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
//                Bitmap.Config.ARGB_8888);
//        final Canvas canvas = new Canvas(bitmap);
//        scrollView.draw(canvas);

        bitmap = createBitmapSafely((scrollView.getWidth()),
                h, Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null) {
            synchronized (this) {
                Canvas canvas = new Canvas();
                canvas.setBitmap(bitmap);
                canvas.save();
                canvas.drawColor(Color.WHITE); // 防止 View 上面有些区域空白导致最终 Bitmap 上有些区域变黑
//                canvas.scale(scale, scale);
                scrollView.draw(canvas);
                canvas.restore();
                canvas.setBitmap(null);
            }
        }
        return bitmap;
    }
        /**
         * 安全的创建bitmap。
         * 如果新建 Bitmap 时产生了 OOM，可以主动进行一次 GC - System.gc()，然后再次尝试创建。
         *
         * @param width      Bitmap 宽度。
         * @param height     Bitmap 高度。
         * @param config     传入一个 Bitmap.Config。
         * @param retryCount 创建 Bitmap 时产生 OOM 后，主动重试的次数。
         * @return 返回创建的 Bitmap。
         */
        public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
            try {
                return Bitmap.createBitmap(width, height, config);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                if (retryCount > 0) {
                    System.gc();
                    return createBitmapSafely(width, height, config, retryCount - 1);
                }
                return null;
            }
        }
}
