package com.sermo.sermo_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sermo.sermo_android.api.RegisterAPI;

public class RegisterViewModel extends ViewModel implements RegisterAPI.CallbackFromRegisterApi {
    private RegisterAPI api;
    private final MutableLiveData<Boolean> registerStatus;
    private final MutableLiveData<Boolean> failedRegisterStatus;


    public RegisterViewModel() {
        registerStatus = new MutableLiveData<>(false);;
        failedRegisterStatus = new MutableLiveData<>(false);;
        this.api = new RegisterAPI(this);
    }

    public LiveData<Boolean> getRegisterStatus() {
        return registerStatus;
    }
    public LiveData<Boolean> getFailedRegisterStatus() {
        return failedRegisterStatus;
    }

    public void resetFlags() {
        registerStatus.postValue(false);
        failedRegisterStatus.postValue(false);
    }

    public void register(String userId, String password, String nick) {
        api.register(userId, nick, password);
    }

    /**
     * Handle successful and unsuccessful register
     * */
    @Override
    public void onRegisterCompleted(boolean status) {
        registerStatus.postValue(status);
        failedRegisterStatus.postValue(!status);
    }
}
