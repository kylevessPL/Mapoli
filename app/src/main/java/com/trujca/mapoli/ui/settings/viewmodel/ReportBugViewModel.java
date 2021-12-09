package com.trujca.mapoli.ui.settings.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.auth.exception.UserNotLoggedInException;
import com.trujca.mapoli.data.auth.model.UserDetails;
import com.trujca.mapoli.data.auth.repository.AuthRepository;
import com.trujca.mapoli.ui.base.BaseViewModel;

import java.util.Objects;

import javax.inject.Inject;

import co.nedim.maildroidx.MaildroidX;
import co.nedim.maildroidx.MaildroidXType;
import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.SneakyThrows;

@HiltViewModel
public class ReportBugViewModel extends BaseViewModel {

    public MutableLiveData<String> message = new MutableLiveData<>("");
    public MutableLiveData<String> toastMessage = new MutableLiveData<>("");
    private AuthRepository authRepository;

    @Inject
    public ReportBugViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @SneakyThrows
    public void sendEmail(){
        String userEmail;
        UserDetails userDetails = authRepository.getCurrentUserDetails();
        userEmail = userDetails.getEmail();
        if (Objects.equals(message.getValue(), "")){
            toastMessage.setValue(String.valueOf(R.string.report_bug_failure_toast));
            return;
        }

        executor.execute(() -> {
            new MaildroidX.Builder()
                    .smtp("smtp.mailtrap.io")
                    .smtpUsername("a1a7b80d1b1d8d")
                    .smtpPassword("bde830bbc5748a")
                    .port("2525")
                    .isStartTLSEnabled(true)
                    .type(MaildroidXType.HTML)
                    .to("kacperkluska130@gmail.com")
                    .from(userEmail)
                    .subject("Bug report")
                    .body("<h1>Bug report</h1><h4>User message:<p>" + message.getValue() + "</p></h4>")
                    .mail();
        });
        toastMessage.setValue(String.valueOf(R.string.report_bug_success_toast));
    }
}
