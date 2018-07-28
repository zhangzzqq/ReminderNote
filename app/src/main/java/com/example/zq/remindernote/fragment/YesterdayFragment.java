package com.example.zq.remindernote.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zq.remindernote.R;
import com.example.zq.remindernote.adapter.NoteDataAdapter;
import com.example.zq.remindernote.db.MessageContent;
import com.example.zq.remindernote.utils.DateUtils;
import com.example.zq.remindernote.utils.SingleItemClickListener;
import com.example.zq.remindernote.widget.DividerGridItemDecoration;
import com.example.zq.remindernote.widget.XEditText;

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName:
 * Description:
 * Create by: steven
 * Date: 2018/6/29
 */
public class YesterdayFragment extends BaseFragment {

    private View view;
    private RecyclerView mRecyclerView;
    private NoteDataAdapter mAdapter;
    private XEditText mTvWriteNOte;
    private List<MessageContent> mList = new ArrayList();
    private SimpleDateFormat formatter;
    private Date currentDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_yesterday, container, false);

        initView();

        initData();


        return view;

    }

    private void initView() {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        mTvWriteNOte = (XEditText) view.findViewById(R.id.tv_write_note);
        mAdapter = new NoteDataAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void initData() {

        String currentDay = DateUtils.getCurrentDay();
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            currentDate = formatter.parse(currentDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        initMessageContent();

        clickAddNote();

    }

    private void initMessageContent() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mList.clear();
                List<MessageContent> messageContents = LitePal.findAll(MessageContent.class);
                for (MessageContent message : messageContents) {

                    String strDate = message.getContentDate();
                    try {
                        Date date = formatter.parse(strDate);
                        if(DateUtils.differentDaysByMillisecond(currentDate,date)==1){
                            mList.add(message);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });


            }
        }).start();


    }


    private void clickAddNote() {

        mTvWriteNOte.setDrawableRightListener(new XEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {

                String strNote = mTvWriteNOte.getText().toString();
                mAdapter.addData(0, strNote);

//                SPUtils.put(getActivity(), "isFirst", "1");
//                App.aCache.put("isFirst", "1");


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


}
