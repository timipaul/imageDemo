package com.example.administrator.imagedemo.utile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 工具类
 */
public class Utility {
    /**
     *  解决fragment 中嵌套 listView只显示一行的问题
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获取随机数
     * i 范围大小
     */
    public static int getRandom(int i){
        Random r = new Random();
        return r.nextInt(i);
    }

    public static int dpTopx(Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
    public static int pxTodp(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    /** String 转list */
    public static List<String> strongToList(String strs){
        strs = strs.replace("[", "").replace("]", "");
        String str[] = strs.split(",");
        return Arrays.asList(str);
    }

    //将网络图片转换成bitmap
    public static Bitmap getHttpBitmap(String url) {
        URL httpUrl = null;
        Bitmap bitmap = null;
        try {
            httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            InputStream in = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    //计算时间
    public static String longToDate(Date time){
        String result;
        //一小时
        int hour = 1000 * 60 * 60;
        Long day = hour * 24L;
        long nowTime =new Date().getTime();
        Long count = nowTime - time.getTime();
        if(count / hour < 1){
            result = count / 1000 * 60 + "分钟前";
        }else if(count / hour > 1 && count / day < 1){
            //表示超过一小时
            result = count / hour + "小时前";
        }else{
            SimpleDateFormat f = new SimpleDateFormat("MM月dd日");
            result = f.format(time);
        }
        return result;
    }
}
