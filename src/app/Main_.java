/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author ceessay
 */
public class Main_ extends Application {

    private static Stage stage;
    Scene mainScene;

    @Override

    public void start(Stage primaryStage) {
        GUIs.load();//chargement des differentscomposants graphiques (panels)

        stage = primaryStage;
        mainScene = new Scene(GUIs.homePane);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle(S.APP_NAME);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        primaryStage.getIcons().add(new Image("app/img/tripod.png"));
        primaryStage.show();

    }

    public static Stage getStage() {
        return stage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
       
    }

}
