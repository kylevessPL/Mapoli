package com.trujca.mapoli.ui.settings.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.hadilq.liveevent.LiveEvent;
import com.trujca.mapoli.BuildConfig;
import com.trujca.mapoli.data.auth.repository.AuthRepository;
import com.trujca.mapoli.ui.base.BaseViewModel;
import com.trujca.mapoli.ui.common.CurrentUserLiveData;

import javax.inject.Inject;

import co.nedim.maildroidx.MaildroidX;
import co.nedim.maildroidx.MaildroidXType;
import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.SneakyThrows;

@HiltViewModel
public class SettingsViewModel extends BaseViewModel {

    private final AuthRepository authRepository;

    public CurrentUserLiveData currentUserLiveData = new CurrentUserLiveData();
    public MutableLiveData<String> message = new MutableLiveData<>();
    public MutableLiveData<Boolean> mailSent = new LiveEvent<>();
    public MutableLiveData<Boolean> closeReportBugDialog = new LiveEvent<>();

    @Inject
    public SettingsViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @SneakyThrows
    public void sendEmail() {
        closeReportBugDialog.setValue(true);
        String userEmail = authRepository.getCurrentUserDetails().getEmail();
        executor.execute(() -> new MaildroidX.Builder()
                .smtp(BuildConfig.SMPT)
                .smtpUsername(BuildConfig.SMPT_USER_NAME)
                .smtpPassword(BuildConfig.SMPT_USER_PASSWORD)
                .port(BuildConfig.SMPT_PORT)
                .isStartTLSEnabled(true)
                .type(MaildroidXType.HTML)
                .to(BuildConfig.SMPT_EMAIL_TO)
                .from(userEmail)
                .subject("Bug report")
                .body("<h1>Bug report</h1><h4>User message:<p>" + message.getValue() + "</p></h4>")
                .mail()
        );
        mailSent.setValue(true);
    }
}
