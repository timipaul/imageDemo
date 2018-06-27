package com.example.administrator.imagedemo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class DBHelper extends SQLiteOpenHelper {
    //数据库版本号
    private static final int DATABASE_VERSION=4;

    //数据库名称
    private static final String DATABASE_NAME="crud.db";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String CREATE_TABLE_STUDENT="CREATE TABLE "+ Student.TABLE+"("
                +Student.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +Student.KEY_name+" TEXT, "
                +Student.KEY_age+" INTEGER, "
                +Student.KEY_email+" TEXT)";
        db.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果旧表存在，删除，所以数据将会消失
        db.execSQL("DROP TABLE IF EXISTS "+ Student.TABLE);

        //再次创建表
        onCreate(db);
    }
}
