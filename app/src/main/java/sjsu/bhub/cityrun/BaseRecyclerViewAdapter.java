package com.iindicar.indicar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeseul on 2018-04-13.
 *
 *      public BaseRecyclerViewAdapter(Context context);
 *      public BaseRecyclerViewAdapter(Context context, List<T> itemList);
 *      public Context getContext();
 *      public int getItemCount();
 *      public T getItem(int position);
 *      public void updateItems(List<T> items);
 *      public void addItems(List<T> items);
 *      public void addItems(int position, List<T> items);
 *      public void addItem(T item);
 *      public void addItem(int position, T item);
 *      public void clearItems();
 *      public void removeItem(int position);
 *      public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position);
 *      protected abstract void onBindView(H holder, int position);
 *      public void setOnItemClickListener(OnItemClickListener onItemClickListener);
 *      public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener);
 *      public interface OnItemClickListener {
 *
 *              void onItemClick(View view, int position);
 *      }
 *      public interface OnItemLongClickListener {
 *
 *              void onItemLongClick(View view, int position);
 *      }
 *
 *
 */

public abstract class BaseRecyclerViewAdapter<T, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> itemList;
    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;
    protected Context context;

    public BaseRecyclerViewAdapter(Context context){
        this.context = context;
    }

    public BaseRecyclerViewAdapter(Context context, List<T> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if(this.itemList == null) {
            return 0;
        }

        return this.itemList.size();
    }

    public T getItem(int position){
        if(this.itemList == null){
            return null;
        }

        return this.itemList.get(position);
    }

    public List<T> getItemList(){
        if(this.itemList == null){
            return null;
        }

        return this.itemList;
    }

    /**
     * item list 전체 수정
     * */
    public void updateItems(List<T> items){
        if(this.itemList == null){
            itemList = new ArrayList<>();
        }
        this.itemList.clear();
        this.itemList.addAll(items);

        notifyDataSetChanged();
    }
    /**
     * item 수정
     * */
    public void updateItem(int position, T item){
        if(this.itemList == null){
            return;
        }
        if(position > this.itemList.size()){
            return;
        }
        this.itemList.remove(position);
        this.itemList.add(position, item);

        notifyItemChanged(position);
    }
    /**
     * 맨 처음 item list 초기화 또는 ,
     * item list 마지막 position 뒤에 items 추가
     * */
    public void addItems(List<T> items){
        if (this.itemList == null) {
            this.itemList = items;
            notifyDataSetChanged();
        } else {
            int position = this.itemList.size();
            this.itemList.addAll(items);
            notifyItemRangeInserted(position, items.size());
        }
    }

    /**
     * position 위치에 items 추가
     * */
    public void addItems(int position, List<T> items){
        if(this.itemList == null){
            this.itemList = new ArrayList<>();
        }
        if(position > this.itemList.size()){
            return;
        }
        this.itemList.addAll(position, items);
        notifyItemRangeInserted(position, items.size());
    }

    /**
     * item list 마지막 position 뒤에 item 추가
     * */
    public void addItem(T item){
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
            itemList.add(item);
            notifyDataSetChanged();
        } else {
            int position = this.itemList.size();
            this.itemList.add(item);
            notifyItemInserted(position);
        }
    }

    /**
     * position 위치에 item 추가
     * */
    public void addItem(int position, T item){
        if(this.itemList == null){
            return;
        }
        if(position > this.itemList.size()){
            return;
        }
        this.itemList.add(position, item);
        notifyItemInserted(position);
    }
    /**
     * item list 전체 삭제
     * */
    public void clearItems(){
        if(itemList != null){
            itemList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * position 위치의 item 삭제
     * */
    public void removeItem(int position) {
        if (this.itemList != null && position < this.itemList.size()) {
            this.itemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.itemView, position);
                }

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if(onItemLongClickListener != null){
                    onItemLongClickListener.onItemLongClick(holder.itemView, position);
                }

                return false;
            }
        });

        onBindView((H) holder, position);
    }

    protected abstract void onBindView(H holder, int position);

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener{

        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{

        void onItemLongClick(View view, int position);
    }

}
