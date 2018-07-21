package com.bringg.elad.service.repository;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    protected Context mContext;

    public RepositoryModule(Context context){
        this.mContext = context;
    }
    @Provides Context provideContext() { return mContext; }


    @Provides
    @Singleton
    public Repository provideRepository() {
        return new Repository(mContext);
    }
}
