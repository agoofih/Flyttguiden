package com.barwen.daik.flyttguiden.screens.todo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.barwen.daik.flyttguiden.RepositoryFactory;
import com.barwen.daik.flyttguiden.TodoEntryRepository;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by daik on 2018-01-09.
 */

public class TodoViewModel extends AndroidViewModel {

    private final TodoEntryRepository todoEntryRepository;


    public TodoViewModel(@NonNull Application application) {
        super(application);

        todoEntryRepository = RepositoryFactory.getsTodoEntryRepository(application);

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
