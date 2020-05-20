package sample;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    public TextField txtLogin;
    public TextField txtPassword;
    public Button btnLogin;
    public Button btnSignup;

    public void checkInput() { if (!txtLogin.getText().isEmpty() && !txtPassword.getText().isEmpty()) btnLogin.setDisable(false); }
}