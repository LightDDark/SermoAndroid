package com.sermo.sermo_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sermo.sermo_android.api.LoginAPI;

public class LoginViewModel extends ViewModel {
    private LoginAPI api;
    private LiveData<Boolean> loginStatus;

    public LoginViewModel() {
        MutableLiveData<Boolean> status = new MutableLiveData<>(false);
        this.loginStatus = status;
        this.api = new LoginAPI(status);
    }

    public LiveData<Boolean> getStatus() {
        return loginStatus;
    }

    public void login(String userId, String password) {
        api.Login(userId, password);
    }
}
