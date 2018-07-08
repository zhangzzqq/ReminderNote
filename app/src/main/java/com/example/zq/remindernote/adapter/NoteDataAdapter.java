package com.example.zq.remindernote.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zq.remindernote.R;
import com.example.zq.remindernote.db.MessageContent;
import com.example.zq.remindernote.utils.DateUtils;

import java.util.List;

/**
 * Created by stevenzhang on 2016/9/19 0019.
 */
public class NoteDataAdapter extends RecyclerView.Adapter<NoteDataAdapter.MyViewHolder> {
    private List<MessageContent> mDatas;
    private LayoutInflater mInflater;
    private String currentDate;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public NoteDataAdapter(Context context, List<MessageContent> datas)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;

    }

    public void refresh(List<MessageContent> datas){
        mDatas = datas;
        notifyDataSetChanged();
   }

    public void addData(int position,String input){
       //存入数据库
        currentDate = DateUtils.getCurrentTime();
        MessageContent messageContent = new MessageContent();
        messageContent.setContent(input);
        messageContent.setDailyDate(currentDate);
        messageContent.setContentDate(currentDate);
        messageContent.save();

        //刷新列表
        mDatas.add(position,messageContent);
        notifyDataSetChanged();

    }

    //创建viewholder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(
                R.layout.item_home, parent, false));
        return holder;
    }

    //赋值
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {

        MessageContent messageContent =mDatas.get(position);
        holder.tv.setText(messageContent.getContent());
        holder.tvDate.setText(messageContent.getContentDate());

//        // 如果设置了回调，则设置点击事件
//        if (mOnItemClickLitener != null)
//        {
//            holder.itemView.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                    int pos = holder.getLayoutPosition();
//                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
//                }
//            });
//
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
//            {
//                @Override
//                public boolean onLongClick(View v)
//                {
//                    int pos = holder.getLayoutPosition();
//                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
//                    removeData(pos);
//                    return false;
//                }
//            });
//        }
    }

    @Override
    public int getItemCount()
    {
        return mDatas!=null? mDatas.size():0;


    }

    public void addData(int position)
    {
        notifyItemInserted(position);
    }


    public void removeData(int position)
    {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv;
        TextView tvDate;
        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_display_note);
            tvDate = (TextView) view.findViewById(R.id.tv_date);

        }
    }
}
