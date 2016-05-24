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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.PopOver;
import app.Angle;
import app.GUIs;
import app.S;

/**
 * FXML Controller class
 *
 * @author mohamed
 */
public final class MainPane extends AnchorPane {

    @FXML
    private ListView<String> listview;
    @FXML
    private AnchorPane content;
    @FXML
    private FlowPane container;
    @FXML
    private Button homeBtn;
    @FXML
    private Button optionBtn;
    @FXML
    private HBox topBar;
    @FXML
    private Label unitsLbl;
    private PopOver pop;
    private FXMLLoader loader;

    @FXML
    private void handleHomeBtn(ActionEvent event) {
        this.getScene().setRoot(GUIs.homePane);
    }

    @FXML
    private void handleOptionBtn(ActionEvent actionEvent) {
        pop.show(optionBtn);

    }

    ObservableList<String> menu = FXCollections.observableArrayList();

    public MainPane() {
        loadFxml();
        setControls();
        selectView(S.GD);
    }

    private void loadFxml() {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/app/fxml/MainView.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setControls() {

        //creation de la listview pricipale de gauche
        menu.addAll(S.GD, S.PR, S.Tri, S.Conv);
        listview.setItems(menu);

        listview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                setPane(newValue);
            }
        });

        pop = new PopOver(GUIs.settingsPane);
        pop.setDetachedTitle("Options");
        pop.setAutoHide(true);
        pop.setArrowSize(0);
        pop.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);


        setUnitLbl();

        S.CURRENT_UNIT.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                setUnitLbl();
            }

        });

    }

    //afficher une vue en fonction du choix dans le menu
    //selon la vue la bar des unité (topBar) est affiché
    public final void setPane(String newValue) {

        switch (newValue) {

            case S.GD:
                container.getChildren().setAll(GUIs.giseDistPane);
                topBar.setVisible(true);
                break;

            case S.PR:

                container.getChildren().setAll(GUIs.pointRayPane);
                topBar.setVisible(true);
                break;

            case S.Tri:

                container.getChildren().setAll(GUIs.trianglePane);
                topBar.setVisible(true);
                break;
            case S.Conv:
                container.getChildren().setAll(GUIs.converterPane);
                topBar.setVisible(false);
                break;
        }

    }

    public void selectView(String menu) {
        listview.getSelectionModel().select(menu);
    }

    private void setUnitLbl() {
        String lbl = S.CURRENT_UNIT.getValue();
        switch (S.CURRENT_UNIT.getValue()) {
            case Angle.GRAD:
                lbl += " (grades)";
                break;
            case Angle.RAD:
                lbl += " (radians)";
                break;
            case Angle.DEG:
                lbl += " (degrés)";
                break;
        }

        unitsLbl.setText(lbl);
    }
}
