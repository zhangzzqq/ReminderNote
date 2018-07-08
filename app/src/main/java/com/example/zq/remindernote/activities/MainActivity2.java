package com.example.zq.remindernote.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.zq.remindernote.Base.BaseActivity;
import com.example.zq.remindernote.R;
import com.example.zq.remindernote.adapter.NoteDataAdapter;
import com.example.zq.remindernote.db.MessageContent;
import com.example.zq.remindernote.utils.SingleItemClickListener;
import com.example.zq.remindernote.widget.DividerGridItemDecoration;
import com.example.zq.remindernote.widget.XEditText;

import java.util.List;

public class MainActivity2 extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<MessageContent> mDatas;
    private NoteDataAdapter mAdapter;
    private XEditText text;
    private Boolean isFirstUse = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        text = (XEditText) findViewById(R.id.tv_write_note);
        mAdapter = new NoteDataAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        //设置recyclerview的布局管理器
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
				StaggeredGridLayoutManager.VERTICAL));
//      mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
//		StaggeredGridLayoutManager.HORIZONTAL));

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

                String b  = text.getText().toString();
                mAdapter.addData(0,b);
                isFirstUse = false;

            }
        });

        mRecyclerView.addOnItemTouchListener(new SingleItemClickListener(mRecyclerView, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(MainActivity2.this,"dainjis",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

    }

}
