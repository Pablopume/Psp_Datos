package ui.screens.login;

import dao.LoginDAO;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Credentials;
import ui.screens.common.BaseScreenController;

public class LoginController extends BaseScreenController {
    public PasswordField txtPassword;
    public TextField txtUserName;
    private final LoginDAO daoLogin;
    @Inject
    LoginController(LoginDAO daoLogin){
        this.daoLogin=daoLogin;
    }

    public void doLogin(ActionEvent actionEvent) {
        Credentials credentials=new Credentials(txtUserName.getText(),txtPassword.getText());
        if (daoLogin.doLogin(credentials)){
            getPrincipalController().onLoginDone(credentials);
        }
    }
}
