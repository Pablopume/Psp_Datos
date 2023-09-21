package dao.imp;

import dao.LoginDAO;
import model.Credentials;

public class DaoLoginImpl implements LoginDAO {

    @Override
    public boolean doLogin(Credentials credentials) {
        boolean solution=false;
        if (credentials.getUser().equals("root") && credentials.getPassword().equals("root")){
            solution=true;
        }
        return solution;

}
}
