package com.example.zq.remindernote.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zq.remindernote.R;
import com.example.zq.remindernote.activities.MainActivity;
import com.example.zq.remindernote.adapter.NoteDataAdapter;
import com.example.zq.remindernote.db.MessageContent;
import com.example.zq.remindernote.enumera.WhichDay;
import com.example.zq.remindernote.interfaces.SaveData;
import com.example.zq.remindernote.utils.LevelUtil;
import com.example.zq.remindernote.utils.SingleItemClickListener;
import com.example.zq.remindernote.widget.DividerGridItemDecoration;
import com.example.zq.remindernote.widget.XEditText;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

/**
 * ClassName:
 * Description:
 * Create by: steven
 * Date: 2018/6/29
 */
public class HistoryFragment extends BaseFragment {


    private View view;
    private ImageView ivAdd;
    private RecyclerView mRecyclerView;
    private NoteDataAdapter mAdapter;
    private XEditText mTvWriteNOte;
    private List<MessageContent> mList = new ArrayList();
    private SaveData saveData;
    private ImageView ivDelete;
    private Activity mActivity;
    private static final String TAG ="HistoryFragment";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        saveData = (MainActivity) activity;
        this.mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_history, container, false);

        initView();

        initData();

        return view;

    }


    private void initView() {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        mTvWriteNOte = (XEditText) view.findViewById(R.id.tv_write_note);
        ivAdd = (ImageView) view.findViewById(R.id.ivAdd);
        ivDelete = (ImageView) view.findViewById(R.id.ivDelete);

        mAdapter = new NoteDataAdapter(getActivity(), mList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
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

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存历史数据
                List list = new ArrayList();
                int i=1;
                List<MessageContent> messageContents = LitePal.findAll(MessageContent.class);
                for (MessageContent message : messageContents) {
                    String StrContent = message.getContent()
                            + "  " + message.getContentDate()
                            + "  " + message.getLevel();
                    list.add(i+". "+StrContent);
                    i++;
                }
                saveData.saveContent(list.toString());//系统默认转为String有逗号
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            deleteContent();

            }
        });
    }


    private void clickAddNote() {

        mTvWriteNOte.setDrawableRightListener(new XEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {

                String strNote = mTvWriteNOte.getText().toString();
                mAdapter.addData(0, strNote, WhichDay.HISTORY.getValue());

            }
        });


        mAdapter.setOnItemClickLitener(new NoteDataAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, final int position) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("删除");
                alertDialogBuilder.setMessage("确定删除吗？");

                alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MessageContent messageContent = mAdapter.getList().get(position);
                        mAdapter.removeData(position);
                        LitePal.deleteAll(MessageContent.class, "content=? and contentDate =?", messageContent.getContent(), messageContent.getContentDate());


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

        mRecyclerView.addOnItemTouchListener(new SingleItemClickListener(mRecyclerView, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MessageContent messageContent = mList.get(position);
                String strContent = messageContent.getContent();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("内容详情");
                builder.setMessage(strContent);
                builder.create().show();
            }

            @Override
            public void onItemLongClick(View view, int position) {


            }
        }));

    }



    private void deleteContent() {
        Activity activity=null ;
        if(mActivity!=null){
            activity = mActivity;
        }else if(getActivity()!=null){
            activity = getActivity();
        }
        if(activity==null){
            Log.e(TAG,"HistoryFragment :activity==null");
            return;
        }
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
        builder.setTitle("提示");
        builder.setMessage("确认删除所有的记录吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteContentConfirm(true);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    private void deleteContentConfirm(boolean isDelete) {
        Activity activity=null ;
        if(mActivity!=null){
            activity = mActivity;
        }else if(getActivity()!=null){
            activity = getActivity();
        }
        if(activity==null){
            Log.e(TAG,"HistoryFragment :activity==null");
            return;
        }
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
        builder.setTitle("提示");
        builder.setMessage("删除后不能恢复，是否删除？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if(mList!=null){
                    mList.clear();
                }

                if(mAdapter!=null){
                    mAdapter.notifyDataSetChanged();
                }
                LitePal.deleteAll(MessageContent.class);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
