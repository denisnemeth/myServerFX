package sample;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Login {

    public TextField txtLogin;
    public TextField txtPassword;
    public Button btnLogin;
    public Button btnSignup;
    public String token;
    public String login;

    public void checkInput() {
        String login = txtLogin.getText();
        String password = txtPassword.getText();
        if (login.isEmpty() || login.trim().isEmpty() && password.isEmpty() || password.trim().isEmpty()) btnLogin.setDisable(true);
        else btnLogin.setDisable(false);
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public void login() throws IOException {
        URL url = new URL("http://localhost:8080/login");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        String jsonString = "{\"login\": " + txtLogin.getText() + ", \"password\": " + txtPassword.getText() + "}";
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }
        int responseCode = connection.getResponseCode();
        InputStream inputStream = null;
        if (responseCode >= 200 && responseCode < 400) {
            inputStream = connection.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder responseBuilder = new StringBuilder();
            String input;
            while ((input = streamReader.readLine()) != null) responseBuilder.append(input);
            JSONObject object = new JSONObject(responseBuilder.toString());
            setToken(object.getString("token"));
            setLogin(object.getString("login"));
            openSendMessage();
            closeLogin();
        } else {
            txtLogin.setText("");
            txtPassword.setText("");
            btnLogin.setDisable(true);
        }
        connection.disconnect();
    }

    public void openSendMessage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sendMessage.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root1, 650, 500);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void closeLogin() {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }
}