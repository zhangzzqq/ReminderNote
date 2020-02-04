package com.example.zq.remindernote.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.zq.remindernote.Base.App;
import com.example.zq.remindernote.R;
import com.example.zq.remindernote.adapter.NoteDataAdapter;
import com.example.zq.remindernote.common.Constant;
import com.example.zq.remindernote.db.MessageContent;
import com.example.zq.remindernote.enumera.WhichDay;
import com.example.zq.remindernote.utils.DateUtils;
import com.example.zq.remindernote.utils.LevelUtil;
import com.example.zq.remindernote.utils.SingleItemClickListener;
import com.example.zq.remindernote.widget.DividerGridItemDecoration;
import com.example.zq.remindernote.widget.XEditText;

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    private List<MessageContent> mList = new ArrayList();
    private SimpleDateFormat formatter;
    private Date currentDate;

    private EditText etWriteNote;
    private Spinner spinnerTitle;
    private static final String TAG = "TodayFragment";
    private String mContentLevel = "A";
    private ImageView mIvPlus;


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
        etWriteNote = (EditText) view.findViewById(R.id.et_write_note);
        spinnerTitle = (Spinner) view.findViewById(R.id.spinner_title);
        mIvPlus = (ImageView) view.findViewById(R.id.iv_plus);


        mAdapter = new NoteDataAdapter(getActivity(), mList, WhichDay.getIntDay(WhichDay.YESTERDAY.getValue()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
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

                    String strDate = message.getDailyDate();
                    //区别是哪一天的note
                    if(!TextUtils.isEmpty(strDate)){
                        try {
                            Date date = formatter.parse(strDate);
                            if (DateUtils.differentDaysByMillisecond(currentDate, date) == 1) {
                                mList.add(message);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }

                List<MessageContent> sortData = LevelUtil.sortContent(mList);
                mList.clear();
                mList.addAll(sortData);


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

        mIvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNote = etWriteNote.getText().toString();
                if (!TextUtils.isEmpty(strNote)) {
                    mAdapter.addData(mAdapter.getItemCount(), strNote, mContentLevel, WhichDay.TODADY.getValue());
                    etWriteNote.setText("");
                }

            }
        });


        spinnerTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(SpinnerTestActivity.this, "点击了" + arr[position], Toast.LENGTH_SHORT).show();

                mContentLevel = getActivity().getResources().getStringArray(R.array.level)[position];
                Log.e(TAG, "mContentLevel==" + mContentLevel);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mRecyclerView.addOnItemTouchListener(new SingleItemClickListener(mRecyclerView, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                final EditText et = new EditText(getActivity());
                final MessageContent messageContent = mList.get(position);
                String strContent = messageContent.getContent();
                et.setText(strContent);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("内容详情");
                builder.setView(et);
//                builder.setMessage(strContent);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String strNote = et.getText().toString();
                        if (!TextUtils.isEmpty(strNote)) {
                            messageContent.setContent(strNote);
                            messageContent.updateAll("contentId = ?", messageContent.getContentId());

                            mAdapter.updateData(mAdapter.getItemCount(), strNote,messageContent.getContentId(), WhichDay.TODADY.getValue());
                        }
                    }
                });
                builder.create().show();
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("任务");
                builder.setMessage("确认已经完成吗？");
                builder.setNegativeButton("未完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.setItemNoFinish(position);
                    }
                }).setPositiveButton("已完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mAdapter.setItemFinish(position);
                    }
                });

                builder.create().show();
            }
        }));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (mAdapter != null && mAdapter.getFinishMap() != null) {
//
//            HashMap map = mAdapter.getFinishMap();
//
//            App.aCache.put(Constant.yesterdaymap, map);
//
//        }

    }


}
