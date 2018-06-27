package com.example.administrator.imagedemo;

import android.support.multidex.MultiDexApplication;

import java.util.Map;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class ShareDataApplication extends MultiDexApplication {

    private Map<Integer,int[]> dataList;

    public void setDataList(Map<Integer, int[]> dataList) {
        this.dataList = dataList;
    }

    public Map<Integer, int[]> getDataList() {
        return dataList;
    }
}
