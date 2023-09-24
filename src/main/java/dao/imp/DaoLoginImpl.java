package dao.imp;

import common.Constants;
import dao.LoginDAO;
import model.Credentials;

public class DaoLoginImpl implements LoginDAO {

    @Override
    public boolean doLogin(Credentials credentials) {
        boolean solution = false;
        if (credentials.getUser().equals(Constants.ROOT) && credentials.getPassword().equals(Constants.ROOT1)) {
            solution = true;
        }
        return solution;

    }
}
