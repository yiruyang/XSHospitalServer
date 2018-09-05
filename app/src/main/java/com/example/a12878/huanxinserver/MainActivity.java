package com.example.a12878.huanxinserver;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.a12878.huanxinserver.adapter.MainActivityAdapter;
import com.example.a12878.huanxinserver.base.BaseActivity;
import com.example.a12878.huanxinserver.base.User;
import com.example.a12878.huanxinserver.util.HXUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    public static final String TAG = "MainActivity";

    public static Context mContext;
    private MainActivityAdapter mainActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        init();
    }

    private void init() {
        ButterKnife.bind(this);
        mContext = getApplicationContext();
        HXUtil.init(mContext);
        mainActivityAdapter = new MainActivityAdapter(mContext);
        setOnClick();
    }

    private void setOnClick() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 9));//宫格布局
        recyclerView.setAdapter(mainActivityAdapter);
    }
}
