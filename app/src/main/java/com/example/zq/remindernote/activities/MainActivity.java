package com.example.zq.remindernote.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.zq.remindernote.Base.App;
import com.example.zq.remindernote.R;
import com.example.zq.remindernote.adapter.HomeAdapter;
import com.example.zq.remindernote.utils.SPUtils;
import com.example.zq.remindernote.utils.SingleItemClickListener;
import com.example.zq.remindernote.widget.DividerGridItemDecoration;
import com.example.zq.remindernote.widget.XEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;
    private XEditText text;
    private Boolean isFirstUse = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mDatas = new ArrayList<String>();
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        text = (XEditText) findViewById(R.id.tv_write_note);
        mAdapter = new HomeAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        //设置recyclerview的布局管理器
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
				StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
//				StaggeredGridLayoutManager.HORIZONTAL));

        //设置item的间隔
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
//		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        initEvent();
    }

    private void initEvent() {

        text.setDrawableRightListener(new XEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {

//                Editable a  = text.getText();
                String b  = text.getText().toString();
//                mAdapter.refresh(mDatas);
                mAdapter.addData(0,b);

//                Toast.makeText(MainActivity.this,a,Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this,b,Toast.LENGTH_SHORT).show();

                isFirstUse = false;
                SPUtils.put(getApplicationContext(),"isFirst","1");
                App.aCache.put("isFirst","1");
            }
        });
        
        mRecyclerView.addOnItemTouchListener(new SingleItemClickListener(mRecyclerView, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(MainActivity.this,"dainjis",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

    }

    private void initData() {
        String str = (String) SPUtils.get(this,"isFirst","");
        mDatas = new ArrayList<String>();
        if(!TextUtils.isEmpty(str)){
            String data = App.aCache.getAsString("value");
            String [] result = data.split(",");
//            for(int i = 0;i<data.length;i++){
////                mDatas.add(String.valueOf(data[i]));
//            }
            for(String s : result){
                mDatas.add(s);
            }
        }

    }

//    protected void getData()
//    {
//        mDatas = new ArrayList<String>();
//
//        byte[] data = App.aCache.getAsBinary("value");
//        for(int i = 0;i<data.length;i++){
//            mDatas.get(i);
//        }
//
////        for (int i = 'A'; i < 'z'; i++)
////        {
////            mDatas.add("" + (char) i);
////        }
//    }

}
