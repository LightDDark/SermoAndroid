package com.sermo.sermo_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sermo.sermo_android.api.RegisterAPI;

public class RegisterViewModel extends ViewModel {
    private RegisterAPI api;
    private final LiveData<Boolean> registerStatus;
    private final LiveData<Boolean> idStatus;


    public RegisterViewModel() {
        MutableLiveData<Boolean> regStatus = new MutableLiveData<>(false);
        MutableLiveData<Boolean> idStatus = new MutableLiveData<>(false);
        this.registerStatus = regStatus;
        this.idStatus = idStatus;
        this.api = new RegisterAPI(regStatus, idStatus);
    }

    public LiveData<Boolean> getRegisterStatus() {
        return registerStatus;
    }
    public LiveData<Boolean> getIdStatus() {
        return idStatus;
    }


    public void Register(String userId, String password, String nick) {
        api.Register(userId, nick, password);
    }

    public void checkId(String userId) {
        api.checkId(userId);
    }
}
