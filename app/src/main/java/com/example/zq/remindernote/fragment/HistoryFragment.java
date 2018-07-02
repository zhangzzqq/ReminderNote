package com.example.zq.remindernote.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zq.remindernote.Base.App;
import com.example.zq.remindernote.R;
import com.example.zq.remindernote.adapter.NoteDataAdapter;
import com.example.zq.remindernote.beans.MessageContent;
import com.example.zq.remindernote.utils.DateUtils;
import com.example.zq.remindernote.utils.SPUtils;
import com.example.zq.remindernote.utils.SingleItemClickListener;
import com.example.zq.remindernote.widget.DividerGridItemDecoration;
import com.example.zq.remindernote.widget.XEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:
 * Description:
 * Create by: steven
 * Date: 2018/6/29
 */
public class HistoryFragment extends BaseFragment {


    private RecyclerView mRecyclerView;
    private List<MessageContent> mDatas;
    private NoteDataAdapter mAdapter;
    private XEditText text;
    private Boolean isFirstUse = true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_yesterday, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        text = (XEditText) view.findViewById(R.id.tv_write_note);


        initView();

        return view;


    }


    private void initView() {
        mAdapter = new NoteDataAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(mAdapter);
        //设置recyclerview的布局管理器
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
//      mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
//		StaggeredGridLayoutManager.HORIZONTAL));

        //设置item的间隔
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
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
                String b = text.getText().toString();
//                mAdapter.refresh(mDatas);
                mAdapter.addData(0, b);

//                Toast.makeText(MainActivity2.this,a,Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity2.this,b,Toast.LENGTH_SHORT).show();

                isFirstUse = false;
                SPUtils.put(getActivity(), "isFirst", "1");
                App.aCache.put("isFirst", "1");
            }
        });

        mRecyclerView.addOnItemTouchListener(new SingleItemClickListener(mRecyclerView, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(getActivity(), "dainjis", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

    }

    /**
     *
     * 用数据库来保存和读取，会高效
     *
     *
     * @return
     */

    private List getData() {

        String str = (String) SPUtils.get(getActivity(), "isFirst", "");
        mDatas = new ArrayList<MessageContent>();
        if (!TextUtils.isEmpty(str)) {
            String data = App.aCache.getAsString("value");
            if (data != null) {
                String[] result = data.split(",");
//            for(int i = 0;i<data.length;i++){
//                mDatas.add(String.valueOf(data[i]));
//            }
                for (String s : result) {

//                    String currentDate =DateUtils.getCurrentTime();
                    MessageContent messageContent = new MessageContent();
                    messageContent.setContent(s);
                    messageContent.setDailyDate(null);
                    messageContent.setContentDate(n);

                    mDatas.add(messageContent);
                }
            }
        }

        return mDatas;

    }


}
