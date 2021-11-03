package com.trujca.mapoli.data.util;

public interface RepositoryCallback<T> {

    void onSuccess(T model);

    void onError(String msg);
}
