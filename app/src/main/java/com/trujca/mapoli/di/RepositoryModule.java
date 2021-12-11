package com.trujca.mapoli.di;

import com.trujca.mapoli.data.auth.repository.AuthRepository;
import com.trujca.mapoli.data.auth.repository.FirebaseAuthRespository;
import com.trujca.mapoli.data.categories.repository.CategoriesRepository;
import com.trujca.mapoli.data.categories.repository.FirestoreCategoriesRepository;
import com.trujca.mapoli.data.favorites.repository.FavoritesRepository;
import com.trujca.mapoli.data.favorites.repository.FirestoreFavoritesRepository;
import com.trujca.mapoli.data.places.repository.FoursquarePlacesRepository;
import com.trujca.mapoli.data.places.repository.PlacesRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;

@Module
@InstallIn(ViewModelComponent.class)
public abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract AuthRepository provideAuthRepository(FirebaseAuthRespository authRespository);

    @Binds
    @ViewModelScoped
    abstract CategoriesRepository provideCategoriesRepository(FirestoreCategoriesRepository categoriesRepository);

    @Binds
    @ViewModelScoped
    abstract PlacesRepository providePlacesRepository(FoursquarePlacesRepository placesRepository);

    @Binds
    @ViewModelScoped
    abstract FavoritesRepository provideFavoritesRepository(FirestoreFavoritesRepository favoritesRepository);
}
