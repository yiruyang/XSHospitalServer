package com.example.a12878.huanxinserver.base;

import android.app.Activity;
import android.app.Application;

import com.hyphenate.EMMessageListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 12878 on 2018/7/9.
 */

public class MyApplication extends Application {

    private List<Activity> mList;//用于存放所有已经启动的活动

    @Override
    public void onCreate() {
        super.onCreate();
        mList = new ArrayList<Activity>();

    }

    /**
     * 添加单个activity
     * @param activity
     */
    public void add_Activity(Activity activity){
        if (!mList.contains(activity)){
            mList.add(activity);//把当前的activity添加到集合中
        }
    }

    /**
     * 销毁单个activity
     * @param activity
     */
    public void remove_Activity(Activity activity){
        if (mList.contains(activity)){
            mList.remove(activity);//从集合中移除activity
            activity.finish();//销毁活动
        }
    }


    /**
     * 销毁所有的活动
     */
    public void remove_AllActivity(){
        for (Activity activity:mList){
            activity.finish();
        }
    }

}