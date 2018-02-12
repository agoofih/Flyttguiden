package com.barwen.daik.flyttguiden.screens.todo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by daik on 2018-01-09.
 */

@Database(entities = {TodoEntry.class}, version = 1, exportSchema = false)
public abstract class FlyttaguidenDatabase extends RoomDatabase {

    private static FlyttaguidenDatabase sInstance;

    public abstract TodoEntryDao getTodoEntryDao();

    public static FlyttaguidenDatabase getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context, FlyttaguidenDatabase.class, "Flyttaguiden-database.db").build();
        }

        return sInstance;
    }
}
