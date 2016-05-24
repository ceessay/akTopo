/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import app.components.ConverterPane;
import app.components.GiseDistPane;
import app.components.MainPane;
import app.components.PointRayPane;
import app.components.SettingsPane;
import app.components.TrianglePane;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author ceessay
 */
public class GUIs {

    public static Stage mainStage = Main_.getStage();
    public static ConverterPane converterPane;
    public static GiseDistPane giseDistPane;
    public static PointRayPane pointRayPane;
    public static TrianglePane trianglePane;
    public static SettingsPane settingsPane;
    public static MainPane mainPane;
    public static AnchorPane homePane;
    private static FXMLLoader loader;

   
    public static void load() {
       /*chargememnt des different composants graphiques
    convertisseur,
    gisement et disance
    point rayonné
    triangle
    paramatres
    vue principal (mainPane)
    ecran d'accueil
        
    */  
        converterPane = new ConverterPane();
        giseDistPane = new GiseDistPane();
        pointRayPane = new PointRayPane();
        trianglePane = new TrianglePane();
        settingsPane = new SettingsPane();

        mainPane = new MainPane();

        loader = new FXMLLoader();
        loader.setLocation(Main_.class.getResource("fxml/HomeView.fxml"));
        try {
            homePane = (AnchorPane) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Main_.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
