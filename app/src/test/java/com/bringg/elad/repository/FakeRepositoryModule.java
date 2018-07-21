package com.bringg.elad.repository;

import android.content.Context;

import com.bringg.elad.service.repository.Repository;
import com.bringg.elad.service.repository.RepositoryModule;

public class FakeRepositoryModule extends RepositoryModule {

    public FakeRepositoryModule(Context context) {
        super(context);
    }

    @Override
    public Repository provideRepository() {
        return new FakeRepository(mContext);
    }
}
