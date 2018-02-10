package com.barwen.daik.flyttguiden.universal;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.barwen.daik.flyttguiden.screens.todo.FlyttaguidenDatabase;
import com.barwen.daik.flyttguiden.screens.todo.TodoEntry;
import com.barwen.daik.flyttguiden.screens.todo.TodoEntryDao;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by daik on 2018-01-11.
 */

public class TodoEntryRepository {

    private final TodoEntryDao todoEntryDao;

    private Executor IO = Executors.newSingleThreadExecutor();

    public TodoEntryRepository(Context context) {

        FlyttaguidenDatabase database = FlyttaguidenDatabase.getsInstance(context);

        todoEntryDao = database.getTodoEntryDao();
    }

    public LiveData<List<TodoEntry>> getAll(){
        return todoEntryDao.read();
    }

    public void create(final TodoEntry entry) {
        IO.execute(new Runnable() {
            @Override
            public void run() {
                todoEntryDao.insert(entry);
            }
        });
    }

    public void removeEntry(final TodoEntry entry) {
        IO.execute(new Runnable() {
            @Override
            public void run() {
                todoEntryDao.delete(entry);
            }
        });
    }
}
