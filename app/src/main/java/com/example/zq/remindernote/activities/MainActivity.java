package com.example.zq.remindernote.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zq.remindernote.R;
import com.example.zq.remindernote.fragment.HistoryFragment;
import com.example.zq.remindernote.fragment.TodayFragment;
import com.example.zq.remindernote.fragment.TomorrowFragment;
import com.example.zq.remindernote.fragment.YesterdayFragment;


public class MainActivity extends AppCompatActivity {
    //测试
    private FragmentTabHost tabHost;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }


    private void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        tabHost = (FragmentTabHost) findViewById(R.id.fragment_tabhost);
        //关联碎片
        tabHost.setup(this, getSupportFragmentManager(), R.id.fragment_tabcontent);
        //添加碎片
        tabHost.addTab(tabHost.newTabSpec("yesterdaytab").setIndicator(getView(0)), YesterdayFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("todaytab").setIndicator(getView(1)), TodayFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tomorrowtab").setIndicator(getView(2)), TomorrowFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tabhistory").setIndicator(getView(3)), HistoryFragment.class, null);
        //去掉间隔图片
        tabHost.getTabWidget().setDividerDrawable(null);

    }

    private void initData() {

        tabHost.setCurrentTab(1);
    }


    //得到视图 ,关联对应的tabhost
    private View getView(int position) {
        View  v = LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
        ImageView  tabImg = (ImageView) v.findViewById(R.id.tab_img);
        TextView  tabText = (TextView) v.findViewById(R.id.tab_name);

        switch (position) {
            case 0:
                tabImg.setBackgroundResource(R.drawable.bg_yesterday);
                tabText.setText(R.string.yesterday);
                break;
            case 1:
                tabImg.setBackgroundResource(R.drawable.bg_today);
                tabText.setText(R.string.today);
                break;
            case 2:
                tabImg.setBackgroundResource(R.drawable.bg_tomorrow);
                tabText.setText(R.string.tomorrow);
                break;
            case 3:
                tabImg.setBackgroundResource(R.drawable.bg_history);
                tabText.setText(R.string.history);
                break;
        }
        return v;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
} 