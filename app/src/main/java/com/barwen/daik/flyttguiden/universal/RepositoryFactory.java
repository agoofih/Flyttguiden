package com.barwen.daik.flyttguiden.universal;

import android.content.Context;

/**
 * Created by daik on 2018-01-11.
 */

public class RepositoryFactory {

    public static TodoEntryRepository todoEntryRepository;

    public static  TodoEntryRepository getTodoEntryRepository(Context context){
        if (todoEntryRepository == null){
            todoEntryRepository = new TodoEntryRepository(context);
        }
        return todoEntryRepository;
    }

}
