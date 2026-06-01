package javaapplication1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.HibernateUtil;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Gestion des Produits");
        stage.show();
    }

    @Override
    public void stop() {
        HibernateUtil.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}