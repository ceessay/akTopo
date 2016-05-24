/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.components;

import app.C;
import app.Point;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
public class GiseDistPane extends AnchorPane{

    
    @FXML
    private TextField xaField;
    @FXML
    private TextField yaField;
    @FXML
    private TextField xbField;
    @FXML
    private TextField ybField;
    @FXML
    private TextField distField;
    @FXML
    private TextField giseABField;
    @FXML
    private TextField giseBAField;
    @FXML
    private Button effacerBtn;
    

    @FXML
    private AnchorPane root;
    private Angle gisementAB;
    private Angle gisementBA;
    private FXMLLoader loader;


    @FXML
    private void handleEffacer(ActionEvent event) {
        clearAll();
    }

    public GiseDistPane() {
        loadFxml();
        setControls();
    }

    
    private void loadFxml() {
        
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/app/fxml/GiseDistView.fxml"));
        
        loader.setRoot(this);
         loader.setController(this);
         
        
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(GiseDistPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setControls() {

        giseABField.textProperty().addListener(new TextFieldListener());
        giseBAField.textProperty().addListener(new TextFieldListener());
        
        xaField.textProperty().addListener(new FieldKeyListener());
        yaField.textProperty().addListener(new FieldKeyListener());
        xbField.textProperty().addListener(new FieldKeyListener());
        ybField.textProperty().addListener(new FieldKeyListener());

        S.CURRENT_UNIT.addListener(new DefaultUnitChangeListener() );
        S.ANGLE_PRECISION.addListener(new AnglePrecisionChangeListener());
        S.LENGTH_PRECISION.addListener(new LengthPrecisionChangeListener());

        setTooltips();
        U.hackTooltip(giseABField.getTooltip());
        U.hackTooltip(giseBAField.getTooltip());

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

    private void process() {
        boolean numeric = isNumeric(xaField.getText()) && isNumeric(yaField.getText())
                && isNumeric(xbField.getText()) && isNumeric(ybField.getText());

        boolean empty = xaField.getText().isEmpty() || yaField.getText().isEmpty()
                || xbField.getText().isEmpty() || ybField.getText().isEmpty();

        if (!empty && numeric) {

            calculer();

        } else {
//            String message = "";
//            if (empty) {
//                // message += "Les champs de gauche doivent tous etre remplis\n";
//            } else if (!numeric) {
//                message += "Les valeurs entrées doivent être numeriques !";
//                U.showWarning(message, root);
//
//            }
              clearResults();
        }

        setTooltips();
    }

    private void calculer() {

        double xa = Double.valueOf(xaField.getText());
        double ya = Double.valueOf(yaField.getText());
        Point A = new Point(xa, ya);

        double xb = Double.valueOf(xbField.getText());
        double yb = Double.valueOf(ybField.getText());
        Point B = new Point(xb, yb);

        double distance = C.Distance(A, B);

        gisementAB = new Angle(C.Gisement(A, B), Angle.GRAD);
        gisementBA = new Angle(C.Gisement(B, A), Angle.GRAD);

        distField.setText(String.valueOf(distance));

        giseABField.setText(gisementAB.getStringValue());
        giseBAField.setText(gisementBA.getStringValue());
    }

    private void clearAll() {
        xaField.clear();
        yaField.clear();
        xbField.clear();
        ybField.clear();
        clearResults();

    }

    private void clearResults() {
        distField.clear();
        giseABField.clear();
        giseBAField.clear();
    }

    public void setTooltips() {
        String a = "", b = "";

        switch (S.CURRENT_UNIT.getValue()) {
            case Angle.GRAD:
                if (U.isNumeric(giseABField)) {
                    a = gisementAB.getDeg() +" "+  Angle.DEG+ "\n" + gisementAB.getRad() + " "+ Angle.RAD;
                } else {
                    a = "0 "+ Angle.DEG+"\n0 "+Angle.RAD;
                }

                if (U.isNumeric(giseBAField)) {
                    b = gisementBA.getDeg() +" "+  Angle.DEG+ "\n" + gisementBA.getRad() + " "+ Angle.RAD;
                } else {
                    b = "0 "+Angle.DEG+"\n0 "+Angle.RAD;
                }

                break;

            case Angle.DEG:
                if (U.isNumeric(giseABField)) {
                    a = gisementAB.getGrad() +" "+  Angle.GRAD+"\n" + gisementAB.getRad() + " Angle.RAD";
                } else {
                    a = "0 " + Angle.GRAD+"\n0 "+Angle.RAD;
                }

                if (U.isNumeric(giseBAField)) {
                    b = gisementBA.getGrad() + " "+Angle.GRAD+"\n" + gisementBA.getRad() + " "+Angle.RAD;
                } else {
                    b = "0 " + Angle.GRAD+"\n0 "+Angle.RAD;
                }

                break;

            case Angle.RAD:

                if (U.isNumeric(giseABField)) {
                    a = gisementAB.getDeg() +" "+  Angle.DEG+"\n" + gisementAB.getDeg() + " "+Angle.GRAD;
                } else {
                    a = "0 "+ Angle.DEG+"\n0 "+ Angle.GRAD;
                }

                if (U.isNumeric(giseBAField)) {
                    b = gisementBA.getDeg() +" "+  Angle.DEG+"\n" + gisementBA.getDeg() + " "+Angle.GRAD;
                } else {
                    b = "0 "+ Angle.DEG+"\n0 "+ Angle.GRAD;
                }

                break;
        }

        giseABField.getTooltip().setText(a);
        giseBAField.getTooltip().setText(b);

    }

    private class TextFieldListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            setTooltips();
        }
    }
    
    private class FieldKeyListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            process();
        }
    }

    private class DefaultUnitChangeListener implements ChangeListener<String> {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                clearResults();
                process();
                
            }

   
    }

    private class AnglePrecisionChangeListener implements ChangeListener<Number> {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                clearResults();
                process();

            }
     
    }

    private class LengthPrecisionChangeListener implements ChangeListener<Number> {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                clearResults();
                process();

            }       
    }
}
