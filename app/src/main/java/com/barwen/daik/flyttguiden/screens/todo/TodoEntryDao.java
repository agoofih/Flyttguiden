package com.barwen.daik.flyttguiden.screens.todo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by daik on 2018-01-09.
 */

@Dao
public interface TodoEntryDao {

    @Insert
    void insert(TodoEntry... entities);

    @Query("SELECT * FROM todo_entries")
    LiveData<List<TodoEntry>> read();

    @Update
    void update(TodoEntry... entries);

    @Delete
    void delete(TodoEntry... entries);

}
