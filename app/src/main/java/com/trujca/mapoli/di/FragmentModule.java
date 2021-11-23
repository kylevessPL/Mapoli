package com.trujca.mapoli.di;

import com.trujca.mapoli.util.GlideLoader;

import agency.tango.android.avatarview.ImageLoaderBase;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.scopes.FragmentScoped;

@Module
@InstallIn(FragmentComponent.class)
public abstract class FragmentModule {

    @Binds
    @FragmentScoped
    abstract ImageLoaderBase provideImageLoader(GlideLoader glideLoader);
}
