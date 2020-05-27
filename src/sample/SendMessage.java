package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SendMessage {

    public Pane pnMessage;
    public Button btnLogout;
    public TextArea areaMessage;
    public TextField txtfldTo;
    public Button btnSend;

    @FXML
    private void logout() {}

    public void sendMessage() {}

    public void close() {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.close();
    }
}