package com.sermo.sermo_android.IO;

public class RegisterReq extends LoginReq{
    String name;

    public RegisterReq(String id, String password, String name) {
        super(id, password);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
