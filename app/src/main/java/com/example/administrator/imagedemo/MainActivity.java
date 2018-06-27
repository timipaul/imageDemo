package com.example.administrator.imagedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.imagedemo.canvas.CanvasDemo;
import com.example.administrator.imagedemo.data.FileIoActivity;
import com.example.administrator.imagedemo.data.SharedPreferencesActivity;
import com.example.administrator.imagedemo.data.SqlLiteInsertActivity;
import com.example.administrator.imagedemo.draw.DrawActivity;
import com.example.administrator.imagedemo.pin.BitmapActivity;
import com.example.administrator.imagedemo.pin.CustomCartoonActivity;
import com.example.administrator.imagedemo.pin.GestureActivity;
import com.example.administrator.imagedemo.pin.GestureZoonActivity;
import com.example.administrator.imagedemo.pin.MatrixActivity;
import com.example.administrator.imagedemo.pin.PinBallActivity;
import com.example.administrator.imagedemo.pin.ShaderImageActivity;
import com.example.administrator.imagedemo.pin.ShearImageActvity;
import com.example.administrator.imagedemo.pin.UseNetworkImageActivity;
import com.example.administrator.imagedemo.update.ImageUpdateActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        Class clazz = null;
        switch (view.getId()) {
            case R.id.use_image:
                //跳转到使用图片界面
                clazz = UseImageActivity.class;
                break;
            case R.id.canvas_image:
                //跳转到绘制图片
                clazz = CanvasDemo.class;
                break;
            case R.id.draw_image:
                //手动绘制画板
                clazz = DrawActivity.class;
                break;
            case R.id.pin_ball:
                clazz = PinBallActivity.class;
                //弹球游戏
                break;
            case R.id.matrix:
                clazz = MatrixActivity.class;
                //图片变换
                break;
            case R.id.bitmap:
                //背景移动
                clazz = BitmapActivity.class;
                break;
            case R.id.shader_image:
                clazz = ShaderImageActivity.class;
                break;
            case R.id.custom_cartoon:
                //自定义补间动画
                clazz = CustomCartoonActivity.class;
                break;
            case R.id.use_network_image:
                //使用网络图片移动操作
                clazz = UseNetworkImageActivity.class;
                break;
            case R.id.gesture_zoon:
                //改变图片大小   不好用
                clazz = GestureZoonActivity.class;
                break;
            case R.id.shared_preferences:
                //共享数据
                clazz = SharedPreferencesActivity.class;
                break;
            case R.id.file_io:
                //文件存储
                clazz = FileIoActivity.class;
                break;
            case R.id.sql_lite:
                //操作数据库
                clazz = SqlLiteInsertActivity.class;
                break;
            case R.id.image_update:
                //图片上传
                clazz = ImageUpdateActivity.class;
                break;
            case R.id.shear_image:
                //剪切图片
                clazz = ShearImageActvity.class;
                break;
            case R.id.gesture_text:
                clazz = GestureActivity.class;
                break;
        }
        intent = new Intent(this,clazz);
        startActivity(intent);
    }
}
