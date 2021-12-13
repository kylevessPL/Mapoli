package com.trujca.mapoli.di;

import static com.trujca.mapoli.BuildConfig.FOURSQUARE_TOKEN;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.trujca.mapoli.data.map.api.LodzUniversityBuildingsService;
import com.trujca.mapoli.data.map.model.LodzUniversityBuilding;
import com.trujca.mapoli.data.map.repository.LodzUniversityBuildingListDeserializer;
import com.trujca.mapoli.data.places.api.FoursquarePlacesService;
import com.trujca.mapoli.data.places.model.PlaceNearby;
import com.trujca.mapoli.data.places.repository.PlaceNearbyListDeserializer;

import org.itishka.gsonflatten.FlattenTypeAdapterFactory;

import java.lang.annotation.Retention;
import java.util.List;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class ApiModule {

    private static final String FOURSQUARE_PLACES_BASE_URL = "https://api.foursquare.com/v3/places/";
    private static final String LODZ_UNIVERSITY_BUILDINGS_BASE_URL = "https://nav.p.lodz.pl/data/buildings.json/";

    @Provides
    @Singleton
    @FoursquareHttpClient
    OkHttpClient provideFoursquareHttpClient(
            @LoggingInterceptor HttpLoggingInterceptor httpLoggingInterceptor,
            @FoursquareInterceptor Interceptor foursquareInterceptor
    ) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(foursquareInterceptor)
                .build();
    }

    @Provides
    @Singleton
    @CommonHttpClient
    OkHttpClient provideCommonHttpClient(@LoggingInterceptor HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    @LoggingInterceptor
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    @FoursquareInterceptor
    Interceptor provideFoursquareInterceptor() {
        return chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", FOURSQUARE_TOKEN)
                    .build();
            return chain.proceed(newRequest);
        };
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new FlattenTypeAdapterFactory())
                .registerTypeAdapter(TypeToken.getParameterized(List.class, PlaceNearby.class).getType(), new PlaceNearbyListDeserializer())
                .registerTypeAdapter(TypeToken.getParameterized(List.class, LodzUniversityBuilding.class).getType(), new LodzUniversityBuildingListDeserializer())
                .create();
    }

    @Provides
    @Singleton
    @FoursquarePlacesApiClient
    Retrofit provideFoursquarePlacesApiClient(@FoursquareHttpClient OkHttpClient foursquareHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .client(foursquareHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(FOURSQUARE_PLACES_BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    @LodzUniversityBuildingsApiClient
    Retrofit provideLodzUniversityBuildingsApiClient(@CommonHttpClient OkHttpClient commonHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .client(commonHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(LODZ_UNIVERSITY_BUILDINGS_BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    FoursquarePlacesService provideFoursquarePlacesApiService(Retrofit apiClient) {
        return apiClient.create(FoursquarePlacesService.class);
    }

    @Provides
    @Singleton
    LodzUniversityBuildingsService provideLodzUniversityBuildingsApiService(Retrofit apiClient) {
        return apiClient.create(LodzUniversityBuildingsService.class);
    }

    @Qualifier
    @Retention(RUNTIME)
    public @interface LoggingInterceptor {
    }

    @Qualifier
    @Retention(RUNTIME)
    public @interface FoursquareInterceptor {
    }

    @Qualifier
    @Retention(RUNTIME)
    public @interface FoursquareHttpClient {
    }

    @Qualifier
    @Retention(RUNTIME)
    public @interface CommonHttpClient {
    }

    @Qualifier
    @Retention(RUNTIME)
    public @interface FoursquarePlacesApiClient {
    }

    @Qualifier
    @Retention(RUNTIME)
    public @interface LodzUniversityBuildingsApiClient {
    }
}
