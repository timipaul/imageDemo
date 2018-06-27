package com.example.administrator.imagedemo.pin;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.imagedemo.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 使用网络图片
 * 合并图片
 * 保存图片
 */
public class UseNetworkImageActivity extends Activity{
    
    private static final String TAG = "UseNetworkImageActivity";
    private ImageView mImage;
    private ImageView mImage2;
    private ImageView mImage3;
    private ImageView mImage4;
    private Button mButton;
    private Button mButton2;
    private Button mButtonShow;
    Bitmap bit = null;
    Bitmap bit2 = null;
    Bitmap bit3 = null;
    private int imageWidth;
    private int imageHeight;

    //需要移动的位置
    private int imageX = 0;
    private int imageY = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_network_image);
        mImage = (ImageView) findViewById(R.id.network_image);
        mImage2 = (ImageView) findViewById(R.id.network_image2);
        mImage3 = (ImageView) findViewById(R.id.image1);
        mImage4 = (ImageView) findViewById(R.id.image2);
        mButton = (Button) findViewById(R.id.but_img);
        mButton2 = (Button) findViewById(R.id.update_image);
        mButtonShow = (Button) findViewById(R.id.show_image);
        imageWidth = 200;
        imageHeight = 200;

        new Thread(new Runnable() {
            @Override
            public void run() {
                bit = netPicToBmp("http://p872ue3rt.bkt.clouddn.com/image/bhsj0001.jpg");
                //bit2 = netPicToBmp("http://p872ue3rt.bkt.clouddn.com/0000001.jpg");
                bit = zoomImg(bit,imageWidth,imageHeight);
                Drawable d = getResources().getDrawable(R.drawable.annular_label_bg);
                BitmapDrawable bd = (BitmapDrawable) d;
                bit2 =  bd.getBitmap();

                bit3 = combineBitmap(bit,bit2);
                showResponse(bit3);
            }
        }).start();

        mImage4.setOnTouchListener(new PicOnTouchListener());


        mButtonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示
                Bitmap image1 = ((BitmapDrawable)mImage3.getDrawable()).getBitmap();
                Bitmap image2 = ((BitmapDrawable)mImage4.getDrawable()).getBitmap();
                Bitmap image3 = combineBitmap(image1,image2);
                showResponse(image3);

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存
                imageWidth = 800;
                imageHeight = 800;
                bit3 = zoomImg(bit3,imageWidth,imageHeight);
                mImage2.setImageBitmap(bit3);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        imageWidth = imageWidth + 10;
                        imageHeight = imageHeight + 10;
                        bit = zoomImg(bit,imageWidth,imageHeight);
                        bit3 = combineBitmap(bit,bit2);
                        showResponse(bit3);
                    }
                }).start();

            }
        });
    }

    //OnTouch监听器
    private class PicOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event){

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:  //起始位置

                    break;
                case MotionEvent.ACTION_MOVE:   //实时位置
                    imageX = (int)event.getRawX() - v.getWidth() / 2;
                    imageY = (int)event.getRawY() - v.getHeight();
                    moveViewWithFinger(v,imageX,imageY);
                    break;
                case MotionEvent.ACTION_UP:     //结束位置

                    break;
            }
            return true;
        }
    }

    private void moveViewWithFinger(View view, int rawX, int rawY) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        // 0.05解决一个像素差bug
        params.leftMargin =  rawX - (int) (rawX*0.05);
        params.topMargin =  rawY + (int) (rawY*0.05);
        view.setLayoutParams(params);

    }


    private void showResponse(final Bitmap bit){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行 UI 操作， 将结果显示到界面上
                mImage2.setImageBitmap(bit);
            }
        });
    }


    //获取图片
    public static Bitmap netPicToBmp(String src) {
        try {
            Log.d("FileUtil", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            //设置固定大小
            //需要的大小
            float newWidth = 400f;
            float newHeigth = 400f;

            //图片大小
            int width = myBitmap.getWidth();
            int height = myBitmap.getHeight();



            //缩放比例
            float scaleWidth =  newWidth / width;
            float scaleHeigth = newHeigth / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeigth);


            Bitmap bitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);
            return bitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    /**
     * 图片组合
     * @param background
     * @param foreground
     * @return
     */
    public Bitmap combineBitmap(Bitmap background, Bitmap foreground) {
        if (background == null) {
            return null;
        }
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();

        Bitmap newmap = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newmap);

        canvas.drawBitmap(background, 0, 0, null);

        canvas.drawBitmap(foreground, imageX, imageY, null);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return newmap;
    }




    /**
     * 把两个位图覆盖合成为一个位图，左右拼接
     * @param leftBitmap
     * @param rightBitmap
     * @param isBaseMax 是否以宽度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    public static Bitmap mergeBitmap_LR(Bitmap leftBitmap, Bitmap rightBitmap, boolean isBaseMax) {

        if (leftBitmap == null || leftBitmap.isRecycled()
                || rightBitmap == null || rightBitmap.isRecycled()) {
            return null;
        }
        int height = 0; // 拼接后的高度，按照参数取大或取小
        if (isBaseMax) {
            height = leftBitmap.getHeight() > rightBitmap.getHeight() ? leftBitmap.getHeight() : rightBitmap.getHeight();
        } else {
            height = leftBitmap.getHeight() < rightBitmap.getHeight() ? leftBitmap.getHeight() : rightBitmap.getHeight();
        }

        // 缩放之后的bitmap
        Bitmap tempBitmapL = leftBitmap;
        Bitmap tempBitmapR = rightBitmap;

        if (leftBitmap.getHeight() != height) {
            tempBitmapL = Bitmap.createScaledBitmap(leftBitmap, (int)(leftBitmap.getWidth()*1f/leftBitmap.getHeight()*height), height, false);
        } else if (rightBitmap.getHeight() != height) {
            tempBitmapR = Bitmap.createScaledBitmap(rightBitmap, (int)(rightBitmap.getWidth()*1f/rightBitmap.getHeight()*height), height, false);
        }

        // 拼接后的宽度
        int width = tempBitmapL.getWidth() + tempBitmapR.getWidth();

        // 定义输出的bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // 缩放后两个bitmap需要绘制的参数
        Rect leftRect = new Rect(0, 0, tempBitmapL.getWidth(), tempBitmapL.getHeight());
        Rect rightRect  = new Rect(0, 0, tempBitmapR.getWidth(), tempBitmapR.getHeight());

        // 右边图需要绘制的位置，往右边偏移左边图的宽度，高度是相同的
        Rect rightRectT  = new Rect(tempBitmapL.getWidth(), 0, width, height);

        canvas.drawBitmap(tempBitmapL, leftRect, leftRect, null);
        canvas.drawBitmap(tempBitmapR, rightRect, rightRectT, null);
        return bitmap;
    }

    /**
     * 把两个位图覆盖合成为一个位图，上下拼接
     * @param
     * @param
     * @param isBaseMax 是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    public static Bitmap mergeBitmap_TB(Bitmap topBitmap, Bitmap bottomBitmap, boolean isBaseMax) {

        if (topBitmap == null || topBitmap.isRecycled()
                || bottomBitmap == null || bottomBitmap.isRecycled()) {
            return null;
        }
        int width = 0;
        if (isBaseMax) {
            width = topBitmap.getWidth() > bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        } else {
            width = topBitmap.getWidth() < bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        }
        Bitmap tempBitmapT = topBitmap;
        Bitmap tempBitmapB = bottomBitmap;

        if (topBitmap.getWidth() != width) {
            tempBitmapT = Bitmap.createScaledBitmap(topBitmap, width, (int)(topBitmap.getHeight()*1f/topBitmap.getWidth()*width), false);
        } else if (bottomBitmap.getWidth() != width) {
            tempBitmapB = Bitmap.createScaledBitmap(bottomBitmap, width, (int)(bottomBitmap.getHeight()*1f/bottomBitmap.getWidth()*width), false);
        }

        int height = tempBitmapT.getHeight() + tempBitmapB.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Rect topRect = new Rect(0, 0, tempBitmapT.getWidth(), tempBitmapT.getHeight());
        Rect bottomRect  = new Rect(0, 0, tempBitmapB.getWidth(), tempBitmapB.getHeight());

        Rect bottomRectT  = new Rect(0, tempBitmapT.getHeight(), width, height);

        canvas.drawBitmap(tempBitmapT, topRect, topRect, null);
        canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null);
        return bitmap;
    }


    // 等比缩放图片
    public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }



}
