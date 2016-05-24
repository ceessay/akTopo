/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.components;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import app.Angle;
import app.GUIs;
import app.S;

/**
 * FXML Controller class
 *
 * @author mohamed
 */
public class SettingsPane extends AnchorPane {

    @FXML
    private ComboBox<String> unitBox;
    @FXML
    private ComboBox<Integer> anglePrecisionBox;
    @FXML
    private ComboBox<Integer> lenghtPrecisionBox;
    @FXML
    private Button aboutBtn;

    @FXML
    ObservableList<String> unitsList = FXCollections.observableArrayList();
    ObservableList<Integer> valuesList = FXCollections.observableArrayList();
    private Stage aboutStage;

    @FXML
    private void handleAboutBtn(ActionEvent event) {
        aboutStage.show();
    }
    private FXMLLoader loader;
    private AnchorPane pane;

    public SettingsPane() {
        loadFxml();
        setControls();
    }

    private void loadFxml() {

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/app/fxml/SettingsView.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SettingsPane.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setControls() {

        //loage about window
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/app/fxml/AboutView.fxml"));
        try {
            pane = (AnchorPane) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SettingsPane.class.getName()).log(Level.SEVERE, null, ex);
        }

        prepareAboutStage();

        unitsList.addAll(Angle.GRAD, Angle.DEG, Angle.RAD);
        valuesList.addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        unitBox.setItems(unitsList);
        unitBox.getSelectionModel().select(0);
        unitBox.getSelectionModel().selectedItemProperty().addListener(new unitBoxChangeListener());

        anglePrecisionBox.setItems(valuesList);
        anglePrecisionBox.getSelectionModel().select(3);
        anglePrecisionBox.getSelectionModel().selectedItemProperty().addListener(new AnglePrecisionBoxListener());

        lenghtPrecisionBox.setItems(valuesList);
        lenghtPrecisionBox.getSelectionModel().select(1);
        lenghtPrecisionBox.getSelectionModel().selectedItemProperty().addListener(new LengthPrecisionBoxListener());

    }

    private void prepareAboutStage() {
        aboutStage = new Stage();
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.initOwner(GUIs.mainStage);

        Scene scene = new Scene(pane);
        aboutStage.setScene(scene);
        aboutStage.setTitle(S.APP_NAME + " - A propos");
//        aboutStage.setWidth(600);
//        aboutStage.setHeight(525);
        aboutStage.setResizable(false);
    }

    private class unitBoxChangeListener implements ChangeListener<String> {

        public unitBoxChangeListener() {
        }

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            S.CURRENT_UNIT.setValue(newValue);
            Angle.MAX_ANGLE_VALUE = Angle.getMAX_VALUE();
        }

    }

    private class AnglePrecisionBoxListener implements ChangeListener<Integer> {

        public AnglePrecisionBoxListener() {
        }

        @Override
        public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
            S.ANGLE_PRECISION.set(newValue);
        }

    }

    private static class LengthPrecisionBoxListener implements ChangeListener<Integer> {

        public LengthPrecisionBoxListener() {
        }

        @Override
        public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
            S.LENGTH_PRECISION.setValue(newValue);
        }
    }

}
