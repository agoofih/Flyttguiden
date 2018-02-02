package com.barwen.daik.flyttguiden;

import android.content.Context;

/**
 * Created by daik on 2018-01-11.
 */

public class RepositoryFactory {

    public static TodoEntryRepository sTodoEntryRepository;

    public static TodoEntryRepository getsTodoEntryRepository(Context context){
        if (sTodoEntryRepository == null){
            sTodoEntryRepository = new TodoEntryRepository(context);
        }
        return sTodoEntryRepository;
    }

}
