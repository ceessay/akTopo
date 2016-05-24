/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.components;

import app.C;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import app.Point;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import app.Angle;
import app.S;
import app.U;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;

/**
 * FXML Controller class
 *
 * @author mohamed
 */
public class PointRayPane extends AnchorPane {

    @FXML
    private TextField xsField;
    @FXML
    private TextField ysField;
    @FXML
    private TextField distField;
    @FXML
    private TextField giseField;
    @FXML
    private Tooltip giseTooltip;
    @FXML
    private TextField xpField;
    @FXML
    private TextField ypField;
    @FXML
    private Button effacerBtn;
    @FXML
    private AnchorPane root;
    private FXMLLoader loader;

    @FXML
    private void handleEffacer(ActionEvent event) {
        effacer();
    }

    public static KeyCode keyCode;
    private double MAX_ANGLE_VALUE = Angle.MAX_GRAD_VALUE;
    private Double distance;
    private Angle gisement;

    public PointRayPane() {
        loadFxml();
        setControls();
    }

    
    
    private void loadFxml() {
        
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/app/fxml/PointRayView.fxml"));
        
        loader.setRoot(this);
        loader.setController(this);
         
        
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PointRayPane.class.getName()).log(Level.SEVERE, null, ex);
        }
       

    }
    
    private void setControls() {
        gisement = new Angle();

        U.hackTooltip(giseField.getTooltip());
        setTooltips();

        //ecouter les entrée dans les champs de textes
        giseField.textProperty().addListener(new FieldChangeListener());
        xsField.setOnKeyReleased(new FieldKeyListener());
        ysField.setOnKeyReleased(new FieldKeyListener());
        distField.setOnKeyReleased(new FieldKeyListener());

        //listerner de changement d'unité d'angle
        S.CURRENT_UNIT.addListener(new DefaultUnitChangeListener());

        //listener de changement de precision des angles
        S.ANGLE_PRECISION.addListener(new AnglePrecisionChangeListener());

        //listerner de chagement de précision des longueurs (distance)
        S.LENGTH_PRECISION.addListener(new LengthPrecisionChangeListener());
    }

    private boolean verifierChamps() {

        if (xsField.getText().isEmpty() || ysField.getText().isEmpty()
                || distField.getText().isEmpty()
                || giseField.getText().isEmpty()) {

            return false;
        }
        return true;

    }

    private Boolean isNumeric(String txt) {
        if (txt.endsWith("f") || txt.endsWith("F") || txt.endsWith("d") || txt.endsWith("D")) {
            return false;
        }

        try {
            Double.parseDouble(txt);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private void calculer() {

        double xs = Double.valueOf(xsField.getText());
        double ys = Double.valueOf(ysField.getText());
        Point station = new Point(xs, ys);

        distance = Double.valueOf(distField.getText());

        // gisement = new Angle(U.get(giseField), S.CURRENT_UNIT.getValue());
        Point pointr = C.PointRayonne(station, gisement.getValue(), distance);

        xpField.setText(String.valueOf(pointr.x()));
        ypField.setText(String.valueOf(pointr.y()));
    }

    private void effacer() {
        xsField.clear();
        ysField.clear();
        distField.clear();
        giseField.clear();
        distField.clear();
        xpField.clear();
        ypField.clear();
    }

    private boolean isEmpty(TextField field) {
        return field.getText().isEmpty();
    }

    private void process() {
//   verifier si tous les champs (valeurs) necessaires sont saisies
//  et declencher le calcul le cas echéant 

        boolean numeric = isNumeric(xsField.getText()) && isNumeric(ysField.getText())
                && isNumeric(distField.getText()) && isNumeric(giseField.getText());

        boolean empty = !verifierChamps();

        if (!empty && numeric) {
            calculer();
        } else {
            xpField.clear();
            ypField.clear();

//            String message = "";
//            if (empty) {
//                message += "Les champs de gauche doivent tous etre remplis\n";
//            } else if (!numeric) {
//                message += "Les valeurs entrées doivent être numeriques !";
//                if (keyCode != KeyCode.BACK_SPACE) {
//                     U.showWarning(message, root);
//                }
//            }
        }

        setTooltips();
    }

    public void setTooltips() {

        String a = "";

        switch (S.CURRENT_UNIT.getValue()) {
            case Angle.GRAD:
                if (!isEmpty(giseField)) {
                    a = gisement.getDeg() + " deg\n" + gisement.getRad() + " rad";
                } else {
                    a = "0 deg\n0 rad";
                }

                break;

            case Angle.DEG:
                if (!isEmpty(giseField)) {
                    a = gisement.getGrad() + " gon\n" + gisement.getRad() + " rad";
                } else {
                    a = "0 gon\n0 rad";
                }

                break;

            case Angle.RAD:

                if (!isEmpty(giseField)) {
                    a = gisement.getDeg() + " deg\n" + gisement.getGrad() + " grad";
                } else {
                    a = "0 deg\n0 gon";
                }

                break;

        }

        giseField.getTooltip().setText(a);

    }

    private class FieldChangeListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (U.isNumeric(giseField)) {
                gisement.setValue(U.get(giseField));
            } else {
                gisement.setValue(0);
            }
            process();
        }

    }

    private class FieldKeyListener implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            keyCode = event.getCode();
            if (U.isNumeric(giseField)) {
                gisement.setValue(U.get(giseField));
            } else {
                gisement.setValue(0);
            }
            process();
        }

    }

    private class DefaultUnitChangeListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (U.isNumeric(giseField)) {
                gisement.setValue(U.get(giseField));
            } else {
                gisement.setValue(0);
            }
            process();
        }
    }

    private class AnglePrecisionChangeListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

            process();

        }
    }

    private class LengthPrecisionChangeListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

            process();
        }
    }

}
