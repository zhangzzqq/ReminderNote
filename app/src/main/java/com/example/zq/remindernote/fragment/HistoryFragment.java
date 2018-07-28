package com.example.zq.remindernote.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zq.remindernote.R;
import com.example.zq.remindernote.adapter.NoteDataAdapter;
import com.example.zq.remindernote.db.MessageContent;
import com.example.zq.remindernote.widget.DividerGridItemDecoration;
import com.example.zq.remindernote.widget.XEditText;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:
 * Description:
 * Create by: steven
 * Date: 2018/6/29
 */
public class HistoryFragment extends BaseFragment {


    private View view;
    private RecyclerView mRecyclerView;
    private NoteDataAdapter mAdapter;
    private XEditText mTvWriteNOte;
    private List<MessageContent> mList = new ArrayList();




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
                    mList.add(message);
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


        mAdapter.setOnItemClickLitener(new NoteDataAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, final int position) {

                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("删除");
                alertDialogBuilder.setMessage("确定删除吗？");

                alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MessageContent messageContent=  mAdapter.getList().get(position);
                        mAdapter.removeData(position);
                        LitePal.deleteAll(MessageContent.class,"content=? and contentDate =?",messageContent.getContent(),messageContent.getContentDate());


                    }
                });

                alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialogBuilder.create().show();


            }
        });

//        mRecyclerView.addOnItemTouchListener(new SingleItemClickListener(mRecyclerView, new SingleItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//
//            }
//        }));

    }





}
