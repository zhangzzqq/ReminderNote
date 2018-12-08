package com.example.zq.remindernote.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zq.remindernote.R;
import com.example.zq.remindernote.db.MessageContent;
import com.example.zq.remindernote.utils.DateUtils;
import com.example.zq.remindernote.utils.TextUtilManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stevenzhang on 2016/9/19 0019.
 *
 *  记住选择的状态
 *
 *  hahmap 来保存多选和记住多选（用到了Aache 文件缓存策略）
 *
 *  用时间详情作为key,保证内容唯一性
 *
 */
public class NoteDataAdapter extends RecyclerView.Adapter<NoteDataAdapter.MyViewHolder> {
    private List<MessageContent> mDatas;
    private LayoutInflater mInflater;
    private Activity mActivity;
    private int whichDay=-1;
    private static final String TAG = NoteDataAdapter.class.getSimpleName();

    public interface OnItemClickLitener {

        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public NoteDataAdapter(Activity context, List<MessageContent> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        this.mActivity = context;
    }

    public NoteDataAdapter(Activity context, List<MessageContent> datas, int intDay) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        this.mActivity = context;
        whichDay = intDay;
    }

    /**
     * 任务完成
     * @param position
     */
    public void setItemFinish(int position) {
        mDatas.get(position).setIsFinish(1);
        setChangeStatus(position,true);
        notifyDataSetChanged();
    }

    /**
     * 任务未完成
     * @param position
     */
    public void setItemNoFinish(int position) {
        mDatas.get(position).setIsFinish(0);
        setChangeStatus(position,false);
        notifyDataSetChanged();
    }

    /**
     *
     *  昨天 今天 明天 的新增它的日期和时间是不一样的
     *
     * @param position
     * @param input
     * @param value
     */
    public void addData(int position, String input,String value) {
        String currentDay = "";
        String  currentTime = "";
        int whichDay=0;
        //存入数据库
        switch (value){
            case "today":
                currentDay = DateUtils.getCurrentDay();
                currentTime = DateUtils.getCurrentTime();
                whichDay = 2;
                break;
            case "tomorrow":
                currentDay = DateUtils.AddOrDeleteOneDay(1);
                currentTime = DateUtils.AddOrDeleteOneCurrentTime(1);
                whichDay = 3;
                break;
            case "yesterday":
                currentDay = DateUtils.AddOrDeleteOneDay(-1);
                currentTime = DateUtils.AddOrDeleteOneCurrentTime(-1);
                whichDay = 1;
                break;
        }

        MessageContent messageContent = new MessageContent();
        messageContent.setContent(input);
        messageContent.setDailyDate(currentDay);
        messageContent.setContentDate(currentTime);
        messageContent.setContentId(currentTime.replaceAll(" ",""));//增加contentid
        messageContent.setWhichDay(whichDay);
        messageContent.save();
        mDatas.add(position, messageContent);
//        notifyDataSetChanged();
        notifyItemInserted(position);

    }

    /**
     * 更新数据库状态
     * @param position
     */
    public void setChangeStatus(int position,boolean flag){

     MessageContent messageContent =  mDatas.get(position);
        String id =  TextUtilManager.removeNullString(messageContent.getContentDate());
        if(flag){
            messageContent.setIsFinish(1);
        }else {
            messageContent.setToDefault("isFinish");
        }
      int backValue =  messageContent.updateAll("contentId = ?", id);

    }

    //创建viewholder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(
                R.layout.item_home, parent, false));
        return holder;
    }

    /**
     * 保存勾选状态信息，之前是通过map来保存 ，然后通过App.aCache来记住保存的数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Log.e(TAG,"holder.getAdapterPosition()=="+holder.getAdapterPosition());
        MessageContent messageContent = mDatas.get(position);
        if(messageContent.getIsFinish()==1){
            holder.ivFinish.setVisibility(View.VISIBLE);
        }else {
            holder.ivFinish.setVisibility(View.GONE);
        }

        holder.tv.setText(messageContent.getContent());
        holder.tvDate.setText(messageContent.getContentDate());

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        TextView tvDate;
        ImageView ivFinish;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_display_note);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            ivFinish = (ImageView) view.findViewById(R.id.ivFinish);

        }
    }

    public List<MessageContent> getList() {
        if(mDatas==null){
            mDatas = new ArrayList<>();
        }
        return mDatas;
    }


}
