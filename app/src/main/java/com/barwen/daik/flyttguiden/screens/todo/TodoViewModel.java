package com.barwen.daik.flyttguiden.screens.todo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.barwen.daik.flyttguiden.universal.RepositoryFactory;
import com.barwen.daik.flyttguiden.universal.TodoEntryRepository;

import java.util.List;

/**
 * Created by daik on 2018-01-09.
 */

public class TodoViewModel extends AndroidViewModel {

    private final TodoEntryRepository todoEntryRepository;


    public TodoViewModel(@NonNull Application application) {
        super(application);

        todoEntryRepository = RepositoryFactory.getTodoEntryRepository(application);

    }

    public LiveData<List<TodoEntry>> fetchTodoEntries() {
        return todoEntryRepository.getAll();
    }


    public void delete(TodoEntry entry){
        todoEntryRepository.removeEntry(entry);
    }


    public void save(TodoEntry entry) {
        todoEntryRepository.create(entry);
    }

}
