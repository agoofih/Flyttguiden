package com.barwen.daik.flyttguiden.screens.todo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.barwen.daik.flyttguiden.R;

import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private TodoViewModel viewModel;
    private EditText mAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(246, 179, 78));
            window.setNavigationBarColor(Color.rgb(246, 179, 78));
        }

        viewModel = ViewModelProviders.of(this).get(TodoViewModel.class);


        final RecyclerView todoList = findViewById(R.id.todo_list);
        todoList.setLayoutManager(new LinearLayoutManager(this));

        final TodoEntryAdapter adapter = new TodoEntryAdapter();

        adapter.setmOnRemoveButtonClickListener(new OnRemoveButtonClickListener() {
            @Override
            public void onRemoveButtonClick(TodoEntry entry) {
                viewModel.delete(entry);
            }
        });

        todoList.setAdapter(adapter);

        viewModel.fetchTodoEntries().observe(this, new Observer<List<TodoEntry>>() {
            @Override
            public void onChanged(@Nullable final List<TodoEntry> todoEntries) {
                adapter.setTodoEntryList(todoEntries);
            }
        });

        FloatingActionButton create = findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(TodoActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_todo, null);

                mAddItem = (EditText) mView.findViewById(R.id.popup_editText);

                Button closeButton = (Button) mView.findViewById(R.id.close_button);
                Button addButton = (Button) mView.findViewById(R.id.button_add);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!mAddItem.getText().toString().isEmpty()) {
                            Toast.makeText(TodoActivity.this,
                                    "Yupp",
                                    Toast.LENGTH_LONG).show();

                            TodoEntry entry = new TodoEntry();
                            entry.setTodo(mAddItem.getText().toString());

                            viewModel.save(entry);
                            dialog.cancel();

                        } else {
                            Toast.makeText(TodoActivity.this,
                                    "Nada?",
                                    Toast.LENGTH_LONG).show();
                        }


                    }
                });


            }


        });

    }

}
