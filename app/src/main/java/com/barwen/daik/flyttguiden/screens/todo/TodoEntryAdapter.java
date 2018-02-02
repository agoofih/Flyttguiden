package com.barwen.daik.flyttguiden.screens.todo;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daik on 2018-01-09.
 */

public class TodoEntryAdapter extends RecyclerView.Adapter<TodoViewHolder> {


    private List<TodoEntry> TodoEntryList = new ArrayList<>();

    private OnRemoveButtonClickListener mOnRemoveButtonClickListener;


    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return TodoViewHolder.newInstance(parent, mOnRemoveButtonClickListener);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        holder.bind(TodoEntryList.get(position));
    }

    @Override
    public int getItemCount() {
        return TodoEntryList.size();
    }

    public void setTodoEntryList(List<TodoEntry> todoEntryList) {
        TodoEntryList = todoEntryList;
        notifyDataSetChanged();
    }

    public void setmOnRemoveButtonClickListener(OnRemoveButtonClickListener listener){
        this.mOnRemoveButtonClickListener = listener;
    }

}
