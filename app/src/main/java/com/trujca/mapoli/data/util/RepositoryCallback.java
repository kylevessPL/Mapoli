package com.trujca.mapoli.data.util;

public interface RepositoryCallback<Model, Error> {

    void onLoading(Boolean loading);

    void onSuccess(Model model);

    void onError(Error error);
}
