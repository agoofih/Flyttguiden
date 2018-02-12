package com.barwen.daik.flyttguiden.screens.todo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.barwen.daik.flyttguiden.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daik on 2018-01-09.
 */

public class TodoViewHolder extends RecyclerView.ViewHolder {

    private TodoEntry todoEntry;

    private TextView todoName;
    private Button todoRemove;

    public TodoViewHolder(View itemView, final OnRemoveButtonClickListener onRemoveButtonClickListener) {
        super(itemView);

        todoName = itemView.findViewById(R.id.todo_name);
        todoRemove = itemView.findViewById(R.id.todo_delete_button);

        todoRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todoRemove.setVisibility(View.INVISIBLE);
                onRemoveButtonClickListener.onRemoveButtonClick(todoEntry);
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (todoRemove.getVisibility() == View.VISIBLE){
                    todoRemove.setVisibility(View.INVISIBLE);
                } else {
                    todoRemove.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void bind(final TodoEntry entry){
        todoEntry = entry;

        todoName.setText(entry.getTodo());

    }

    public static TodoViewHolder newInstance(ViewGroup parent, OnRemoveButtonClickListener onRemoveButtonClickListener){
        return new TodoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_entry_item, parent, false),
                onRemoveButtonClickListener
        );
    }

}
