package com.sermo.sermo_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sermo.sermo_android.api.LoginAPI;

public class LoginViewModel extends ViewModel implements LoginAPI.CallbackFromLoginApi {
    private LoginAPI api;
    private MutableLiveData<Boolean> loginStatus;
    private MutableLiveData<Boolean> failedLoginStatus;

    public LoginViewModel() {
        loginStatus = new MutableLiveData<>(false);
        failedLoginStatus = new MutableLiveData<>(false);
        this.api = new LoginAPI(this);
    }

    public LiveData<Boolean> getStatus() {
        return loginStatus;
    }

    public LiveData<Boolean> getFailedLoginStatus() {
        return failedLoginStatus;
    }

    public void resetFlags() {
        loginStatus.postValue(false);
        failedLoginStatus.postValue(false);
    }
    public void login(String userId, String password) {
        api.Login(userId, password);
    }

    /**
     * handle callback from login api to handle successful and unsuccessful login
     * */
    @Override
    public void onLoginCompleted(boolean status) {
        //handle successful login
        loginStatus.postValue(status);
        //handle unsuccessful login- wrong credentials
        failedLoginStatus.postValue(!status);
    }
}
