package com.trujca.mapoli.ui.settings.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.trujca.mapoli.ui.base.BaseViewModel;

import javax.inject.Inject;

import co.nedim.maildroidx.MaildroidX;
import co.nedim.maildroidx.MaildroidXType;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReportBugViewModel extends BaseViewModel {

    public MutableLiveData<String> message = new MutableLiveData<>("");

    @Inject
    public ReportBugViewModel() {
    }

    public void sendEmail(){
        executor.execute(() -> {
            new MaildroidX.Builder()
                    .smtp("smtp.mailtrap.io")
                    .smtpUsername("a1a7b80d1b1d8d")
                    .smtpPassword("bde830bbc5748a")
                    .port("2525")
                    .isStartTLSEnabled(true)
                    .type(MaildroidXType.HTML)
                    .to("kacperkluska130@gmail.com")
                    .from("kacperkluska130@gmail.com")
                    .subject("subject")
                    .body("<h1>Bug report</h1><p>" + message.getValue() + "</p>")
                    .mail();
        });
    }
}
