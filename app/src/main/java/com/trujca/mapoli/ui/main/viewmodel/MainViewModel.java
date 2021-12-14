package com.trujca.mapoli.ui.main.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hadilq.liveevent.LiveEvent;
import com.trujca.mapoli.data.auth.model.UserDetails;
import com.trujca.mapoli.data.favorites.model.Favorite;
import com.trujca.mapoli.data.favorites.repository.FavoritesRepository;
import com.trujca.mapoli.data.map.model.LodzUniversityBuilding;
import com.trujca.mapoli.data.map.repository.MapRepository;
import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.ui.base.BaseViewModel;
import com.trujca.mapoli.ui.common.CurrentUserLiveData;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class MainViewModel extends BaseViewModel {

    private final MapRepository mapRepository;
    private final FavoritesRepository favoritesRepository;

    @Getter
    private final LiveData<UserDetails> currentUser = new CurrentUserLiveData();
    @Getter
    private final MutableLiveData<List<Favorite>> favorites = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<List<LodzUniversityBuilding>> universityBuildings = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> generalError = new LiveEvent<>();

    @Inject
    public MainViewModel(MapRepository mapRepository, FavoritesRepository favoritesRepository) {
        this.mapRepository = mapRepository;
        this.favoritesRepository = favoritesRepository;
        fetchUniversityBuildings();
    }

    public void fetchFavorites() {
        executor.execute(() -> favoritesRepository.getAllFavorites(new RepositoryCallback<List<Favorite>, Void>() {

            @Override
            public void onLoading(final Boolean loading) {
            }

            @Override
            public void onSuccess(final List<Favorite> model) {
                favorites.postValue(model);
            }

            @Override
            public void onError(final Void unused) {
                generalError.postValue(true);
            }
        }));
    }

    public void fetchUniversityBuildings() {
        executor.execute(() -> mapRepository.getAllBuildings(new RepositoryCallback<List<LodzUniversityBuilding>, Void>() {

            @Override
            public void onLoading(final Boolean loading) {
            }

            @Override
            public void onSuccess(final List<LodzUniversityBuilding> model) {
                universityBuildings.postValue(model);
            }

            @Override
            public void onError(final Void unused) {
                generalError.postValue(true);
            }
        }));
    }
}
