package com.trujca.mapoli.di;

import com.trujca.mapoli.data.places.repository.FoursquarePlacesRepository;
import com.trujca.mapoli.data.places.repository.PlacesRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public abstract class RepositoryModule {

    @Binds
    abstract PlacesRepository providePlacesRepository(FoursquarePlacesRepository placesRepository);
}
