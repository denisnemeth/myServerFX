package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SendMessage {

    public Pane pnMessage;
    public Button btnLogout;
    public TextArea areaMessage;
    public TextField txtfldTo;
    public Button btnSend;
    public String token;
    public String login;

    @FXML
    private void logout() {
        try {
            URL url = new URL("http://localhost:8080/logout");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", getToken());
            String jsonString = "{\"login\": " + getLogin() + "}";
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
                closeSendMessage();
                openLogin();
            }
            connection.disconnect();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public String getToken() { return token; }

    public String getLogin() { return login; }

    public void sendMessage() {
        try {
            URL url = new URL("http://localhost:8080/message/new");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", getToken());
            String jsonString = "{\"from\": " + getLogin() + ",\"to\": " + txtfldTo.getText() + ", \"message\": " + areaMessage.getText() + "}";
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
            }
            connection.disconnect();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void openLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root2 = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root2, 350, 200);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void closeSendMessage() {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.close();
    }
}