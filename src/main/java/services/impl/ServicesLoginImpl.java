package services.impl;

import dao.LoginDAO;
import model.Credentials;
import services.ServicesLogin;

public class ServicesLoginImpl implements ServicesLogin {
    private LoginDAO dao;

    public boolean doLogin(Credentials credentials) {
        return dao.doLogin(credentials);

    }

}
