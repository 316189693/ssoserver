package com.example.ssoserver.login.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created By willz
 * Date : 2020/3/17
 * Time: 10:48
 */
public class LoginForm {
    @NotNull
    @NotEmpty
    private String userName;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String service; // service url
     private String error; // service url
    private String tgc; // Ticket Granting Ticket, stored in the CASTGC cookie, represents a SSO session for a user
    private String st; // Service Ticket, transmitted as a GET parameter in urls, stands for the access granted by the CAS server to the CASified application for a specific user.

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTgc() {
        return tgc;
    }

    public void setTgc(String tgc) {
        this.tgc = tgc;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }
}
