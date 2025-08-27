package org.main.App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application{
    public static void main(String[] args) {
    launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(ProgObyvatele.class.getResource("/FXMLDocument.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Semestralka_B Urban");
        stage.setScene(scene);
        stage.show();
    }
}
