/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import app.Angle;
import app.Angle_;
import app.S;
import app.U;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;

/**
 * 
 *
 * @author mohamed
 */
public class ConverterPane extends AnchorPane{
    @FXML
    TextField leftField;
    @FXML
    TextField rightField;
    @FXML
    private ListView<String> leftListView;
    @FXML
    private ListView<String> rightListView;
    @FXML
    private Label resultLbl;
    @FXML
    private Button effacerBtn;
    private String[] dmsTab;
    private FXMLLoader loader;

    @FXML
    private void handleEffacer() {
        rightField.clear();
        leftField.clear();
        resultLbl.setText("");
        angle.set(0, Angle.GRAD);
    }

    ObservableList<String> leftUnitsList = FXCollections.observableArrayList();
    ObservableList<String> rightUnitsList = FXCollections.observableArrayList();

    private Angle_ angle;
    private TextField inputField, resultField;
    private ListView inputListView, resultListView;

    public ConverterPane() {
        loadFxml();
        setControls();
    }
           
    private void loadFxml() {
        
        try {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/app/fxml/ConverterView.fxml"));
            
            loader.setRoot(this);
            loader.setController(this);
            
            
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ConverterPane.class.getName()).log(Level.SEVERE, null, ex);
        }
      

    }
    
    private void setControls(){
        inputField = new TextField();
        resultField = new TextField();
        inputListView = new ListView();
        resultListView = new ListView();

        angle = new Angle_();

        leftUnitsList.addAll(Angle.DEG, Angle.GRAD, Angle.RAD, Angle.DMS);
        rightUnitsList.addAll(Angle.DEG, Angle.GRAD, Angle.RAD, Angle.DMS);

        leftListView.setItems(leftUnitsList);
        rightListView.setItems(rightUnitsList);

        LeftListViewListener leftListViewListener = new LeftListViewListener();
        RightListViewListener rightListViewListener = new RightListViewListener();

        leftListView.getSelectionModel().selectedItemProperty().addListener(leftListViewListener);
        rightListView.getSelectionModel().selectedItemProperty().addListener(rightListViewListener);

        leftField.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                inputField = leftField;
                inputListView = leftListView;
                resultField = rightField;
                resultListView = rightListView;

                process();

            }
        });

        rightField.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                inputField = rightField;
                inputListView = rightListView;
                resultField = leftField;
                resultListView = leftListView;
                process();

            }
        });

        leftListView.getSelectionModel().select(0);
        rightListView.getSelectionModel().select(1);
        S.ANGLE_PRECISION.addListener(new AnglePrecisionChangeListener());
    }

    private void process() {
        //if (!U.isNumeric(inputField)) {
        if (inputField.getText().trim().isEmpty()) {
            resultField.clear();
        } else if (!isInputValid()) {
            resultField.clear();
            resultLbl.setText("Calcul impossible. Vérifez la valeur entrée.");
        } else {
            //System.out.println(newValue);

            if (inputListView.getSelectionModel().getSelectedItem().equals(Angle.DMS)) {

                if (dmsTab.length == 3) {
                    double d = Double.valueOf(dmsTab[0]);
                    double m = Double.valueOf(dmsTab[1]);
                    double s = Double.valueOf(dmsTab[2]);
                    angle.setDMS(d, m, s);
                } else if (dmsTab.length == 2) {
                    double d = Double.valueOf(dmsTab[0]);
                    double m = Double.valueOf(dmsTab[1]);
                    angle.setDMS(d, m, 0);
                } else if (dmsTab.length == 1 || dmsTab.length == 0) {
                    double d = Double.valueOf(dmsTab[0]);
                    angle.setDMS(d, 0, 0);
                }
            } else {

                angle.set(U.get(inputField), (String) inputListView.getSelectionModel().getSelectedItem());
            }

            resultField.setText(angle.getString((String) resultListView.getSelectionModel().getSelectedItem()));

            setRsesultLbl();
        }
    }

    private boolean isInputValid() {
        String string = inputField.getText().trim();

        if (string.isEmpty()) {
            return false;
        }

        if (inputListView.getSelectionModel().getSelectedItem().equals(Angle.DMS)) {

            String str;
            dmsTab = string.split(" ");

            //  System.out.println(dmsTab.length);
            if (dmsTab.length == 3 && isDsdValue()) {
                return true;
            } else if (dmsTab.length == 2 && isDsdValue()) {
                return true;
            } else if (dmsTab.length == 1 && isDsdValue()) {
                return true;
            }

            if (dmsTab.length == 0) {
                if (!U.isNumeric(string)) {
                    return false;
                }
                
                dmsTab[0] = string;
                return true;

            }

        } else {
            if (string.contains(" ")) {
                return false;
            }

            if (string.endsWith("d") || string.endsWith("D")
                    || string.endsWith("f") || string.endsWith("F")) {

                return false;
            }

            try {
                Double.parseDouble(string);
            } catch (Exception e) {
                return false;
            }

        }

        return true;
    }

    private void setRsesultLbl() {

        String unit1 = "", unit2 = "", str = "";

        switch ((String) inputListView.getSelectionModel().getSelectedItem()) {
            case Angle.GRAD:
                str += angle.getString(Angle.GRAD) + " grade(s)";
                break;
            case Angle.DEG:
                str += angle.getString(Angle.DEG) + " degré(s)";
                break;

            case Angle.RAD:
                str += angle.getString(Angle.RAD) + " radian(s)";
                break;
            default:
                str += angle.getDMString();
                break;
        }

        str += " = ";

        switch ((String) resultListView.getSelectionModel().getSelectedItem()) {
            case Angle.GRAD:
                str += angle.getString(Angle.GRAD) + " grade(s)";
                break;
            case Angle.DEG:
                str += angle.getString(Angle.DEG) + " degré(s)";
                break;
            case Angle.RAD:
                str += angle.getString(Angle.RAD) + " radians";
                break;
            default:
                str += angle.getDMString();
                break;
        }

        resultLbl.setText(str);

    }

    private boolean isDsdValue() {
        for (String s : dmsTab) {

            if (s.endsWith("d") || s.endsWith("D")
                    || s.endsWith("f") || s.endsWith("F")) {

                return false;
            }
            if (!U.isNumeric(s)) {

                return false;
            }
        }
        return true;
    }

    private class LeftListViewListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            process();
        }
    }

    private class RightListViewListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

            process();
        }

    }

    private class AnglePrecisionChangeListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            process();
        }

    }
}
