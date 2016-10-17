package com.example.zq.remindernote.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zq.remindernote.Base.App;
import com.example.zq.remindernote.R;

import java.util.Iterator;
import java.util.List;

/**
 * Created by stevenzhang on 2016/9/19 0019.
 */
public class HomeAdapter  extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<String> mDatas;
    private LayoutInflater mInflater;

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

    public HomeAdapter(Context context, List<String> datas)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
//        Log.d("HomeAdapter", datas.size() + "");
    }

    public void refresh(List<String> datas){
        mDatas = datas;
        notifyDataSetChanged();
   }

    public void addData(int position,String input){
        StringBuilder builder = new StringBuilder();
        mDatas.add(position,input);
        notifyDataSetChanged();
        App.aCache.remove("value");
        Iterator iterator = mDatas.iterator();
        while (iterator.hasNext()){
            builder.append(iterator.next());
            builder.append(",");
        }
        App.aCache.put("value",builder.toString());
//        notifyItemInserted(position);
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
        holder.tv.setText(mDatas.get(position));

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
//        Log.d("HomeAdapter","mDatas.size()==="+mDatas.size());
        return mDatas!=null? mDatas.size():0;


    }

    public void addData(int position)
    {
        mDatas.add(position, "Insert One");
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
        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_display_note);

        }
    }
}
