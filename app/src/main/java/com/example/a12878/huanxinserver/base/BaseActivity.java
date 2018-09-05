package com.example.a12878.huanxinserver.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by 12878 on 2018/7/9.
 */

public class BaseActivity extends AppCompatActivity {

    private MyApplication myApplication;
    private BaseActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (myApplication == null){
            myApplication = (MyApplication) getApplication();
        }
        mContext = this;
        addActivity();
    }

    private void addActivity() {
        myApplication.add_Activity(mContext);//调用MyApplication的添加activity方法
    }

    private void removeActivity(){
        myApplication.remove_Activity(mContext);//调用MyApplication的删除activity方法
    }

    private void removeAllActivity(){
        myApplication.remove_AllActivity();//调用MyApplication的删除全部activity方法
    }

    public void showToast(String text){
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View mDecorView = getWindow().getDecorView();

        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }
}
