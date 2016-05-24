/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.components;

import app.C;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import app.Angle;
import app.S;
import app.U;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author mohamed
 */
public class TrianglePane extends AnchorPane {

    @FXML
    private TextField coteAField;
    @FXML
    private TextField coteBField;
    @FXML
    private TextField coteCField;
    @FXML
    private TextField angleAField;
    @FXML
    private TextField angleBField;
    @FXML
    private TextField angleCField;
    @FXML
    private TextField coteAResultField;
    @FXML
    private TextField coteBResultField;
    @FXML
    private TextField coteCResultField;
    @FXML
    private TextField angleAResultField;
    @FXML
    private TextField angleBResultField;
    @FXML
    private TextField angleCResultField;
    @FXML
    private ComboBox unitBox;
    @FXML
    ObservableList<String> unitsList = FXCollections.observableArrayList();
    @FXML
    private Label etatLabel;
    @FXML
    private Button effacerButton;
    @FXML
    private Tooltip angleATooltip;
    @FXML
    private Tooltip angleBTooltip;
    @FXML
    private Tooltip angleCTooltip;
    private FXMLLoader loader;

    @FXML
    private void handleEffacer(ActionEvent event) {
        clearAll();
    }

    private int cotes, angles;
    private String code;
    double coteA, coteB, coteC;
    private Angle angleA, angleB, angleC;

    public TrianglePane() {
        loadFxml();
        setControls();
    }

    private void loadFxml() {

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/app/fxml/TriangleView.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(TrianglePane.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    private void setControls() {

        coteAField.textProperty().addListener(new TextFieldListener());
        coteBField.textProperty().addListener(new TextFieldListener());
        coteCField.textProperty().addListener(new TextFieldListener());
        angleAField.textProperty().addListener(new TextFieldListener());
        angleBField.textProperty().addListener(new TextFieldListener());
        angleCField.textProperty().addListener(new TextFieldListener());

        cotes = 0;
        angles = 0;
        code = new String();
        etatLabel.setText("Aucune valeur entrée");
        clearResults();
        setTooltips();

        angleA = new Angle();
        angleB = new Angle();
        angleC = new Angle();
        coteA = coteB = coteC = 0;

        U.hackTooltip(angleAResultField.getTooltip());
        U.hackTooltip(angleBResultField.getTooltip());
        U.hackTooltip(angleCResultField.getTooltip());

        S.CURRENT_UNIT.addListener(new DefaultUnitChangeListener());
        S.ANGLE_PRECISION.addListener(new AnglePrecisionChangeListener());
        S.LENGTH_PRECISION.addListener(new LengthPrecisionChangeListener());

    }

    /* resoudre un triangle en se basant sur un code de 3 lettres correspondant 
     aux valeurs (cotée ou angles) saisies
     */
    private int getInputs() {
        cotes = 0;
        angles = 0;
        code = "";

        if (U.isNumeric(coteAField.getText())) {
            cotes++;
            code += "a";
        }
        if (U.isNumeric(coteBField.getText())) {
            cotes++;
            code += "b";
        }
        if (U.isNumeric(coteCField.getText())) {
            cotes++;
            code += "c";
        }

        if (U.isNumeric(angleAField.getText())) {
            angles++;
            code += "A";
        }
        if (U.isNumeric(angleBField.getText())) {
            angles++;
            code += "B";

        }
        if (U.isNumeric(angleCField.getText())) {
            angles++;
            code += "C";
        }

        //System.out.println(cotes + angles + " " + code);
        return cotes + angles;
    }

    private void clearAll() {
        cotes = 0;
        angles = 0;
        code = new String();

        angleA = new Angle();
        angleB = new Angle();
        angleC = new Angle();
        coteA = coteB = coteC = 0;

        coteAField.clear();
        coteBField.clear();
        coteCField.clear();
        angleAField.clear();
        angleBField.clear();
        angleCField.clear();

        clearResults();
        setTooltips();
        etatLabel.setText("Aucune valeur entrée");

    }

    private void notifierErreurValeurs() {
        etatLabel.setText("Vous devez entrer exactement trois valeurs numeriques !");
    }

    private void notifierCas(int cas) {
        switch (cas) {
            case 0:
                etatLabel.setText("Aucune solution: verifiez les valeurs entrées.");
                clearResults();
                break;
            case 1:
                etatLabel.setText("Trois cotés connus");
                break;
            case 2:
                etatLabel.setText("Un angle et deux cotés adjacents connus");
                break;
            case 3:
                etatLabel.setText("Deux angles et un coté opposé connus");
                break;
            case 4:
                etatLabel.setText("Deux cotés et un angle opposé connus");
                break;
            case 5:
                etatLabel.setText("Un coté et deux angles adjacents connus");
                break;

        }

    }

    private void solve(String code) {

        switch (code) {

            //========================== cas avec trois coté donnés
            case "abc":

                // if (a + b <= c || b + c <= a || c + a <= b)
                if (coteA + coteB <= coteC || coteB + coteC <= coteA || coteC + coteA <= coteB) {
                    notifierCas(0);
                } else {

                    if (!C.checkCCC(coteA, coteB, coteC)) {
                        notifierCas(0);

                    } else {
                        notifierCas(1);

                        angleA.setValue(C.CCC(coteB, coteC, coteA));
                        angleB.setValue(C.CCC(coteC, coteA, coteB));
                        angleC.setValue(C.CCC(coteA, coteB, coteC));

                    }
                }
                break;

            //======================== cas un angle et desux cotés adjacents CAC    
            case "abC":

                if ((angleC.getValue()) >= Angle.MAX_ANGLE_VALUE) {
                    notifierCas(0);
                } else {

                    coteC = C.CAC(coteA, coteB, angleC.getValue());
                    angleA.setValue(C.CCC(coteB, coteC, coteA));
                    angleB.setValue(C.CCC(coteC, coteA, coteB));

                    notifierCas(2);
                }
                break;

            case "acB":

                if (angleB.getValue() >= Angle.MAX_ANGLE_VALUE) {
                    notifierCas(0);
                } else {

                    coteB = C.CAC(coteA, coteC, angleB.getValue());
                    angleA.setValue(C.CCC(coteB, coteC, coteA));
                    angleC.setValue(C.CCC(coteA, coteB, coteC));

                    notifierCas(2);
                }
                break;

            case "bcA":

                if (angleA.getValue() >= Angle.MAX_ANGLE_VALUE) {
                    notifierCas(0);
                } else {

                    coteA = C.CAC(coteB, coteC, angleA.getValue());
                    angleB.setValue(C.CCC(coteC, coteA, coteB));
                    angleC.setValue(C.CCC(coteA, coteB, coteC));

                    notifierCas(2);
                }
                break;

            //===================deux angles, un coté opposé===============
            /*
             Pour chaque cas:
             1-calculer l'angle inconnue a partir des deux angle inconues
             la somme des angle d'un trinagle = MAX_ANGLE_VALUE degres ou MAX_ANGLE_VALUE grade
             si le resultat est negatif alors le cas est impossile
             sinon on continue les calculs
                
             2-calculer un un cote en utilisant la relation des cosinus
             3-calculer le cote restant utilisant la relation des sinus
                
                
             */
            case "aAB":

                if ((C.AA(angleA.getValue(), angleB.getValue())) <= 0) {
                    notifierCas(0);
                } else {

                    angleC.setValue(C.AA(angleA.getValue(), angleB.getValue()));
                    coteB = C.AACopp(coteA, angleA.getValue(), angleB.getValue());
                    coteC = C.CAC(coteA, coteB, angleC.getValue());

                    notifierCas(3);
                }
                break;

            case "aAC":

                if ((C.AA(angleA.getValue(), angleC.getValue())) <= 0) {
                    notifierCas(0);
                } else {
                    angleB.setValue(C.AA(angleA.getValue(), angleC.getValue()));
                    coteB = C.AACopp(coteA, angleA.getValue(), angleB.getValue());
                    coteC = C.CAC(coteA, coteB, angleC.getValue());

                    notifierCas(3);
                }
                break;

            case "cAC":

                if ((C.AA(angleA.getValue(), angleC.getValue())) <= 0) {
                    notifierCas(0);
                } else {
                    angleB.setValue(C.AA(angleA.getValue(), angleC.getValue()));
                    coteA = C.AACopp(coteC, angleC.getValue(), angleA.getValue());
                    coteB = C.CAC(coteA, coteC, angleB.getValue());

                    notifierCas(3);
                }
                break;

            case "cBC":

                if ((C.AA(angleB.getValue(), angleC.getValue())) <= 0) {
                    notifierCas(0);
                } else {
                    angleA.setValue(C.AA(angleB.getValue(), angleC.getValue()));
                    coteB = C.AACopp(coteC, angleC.getValue(), angleB.getValue());
                    coteA = C.CAC(coteB, coteC, angleA.getValue());

                    notifierCas(3);
                }
                break;

            case "bAB":

                if ((C.AA(angleA.getValue(), angleB.getValue())) <= 0) {
                    notifierCas(0);
                } else {
                    angleC.setValue(C.AA(angleA.getValue(), angleB.getValue()));
                    coteA = C.AACopp(coteB, angleB.getValue(), angleA.getValue());
                    coteC = C.CAC(coteA, coteB, angleC.getValue());

                    notifierCas(3);
                }
                break;

            case "bBC":

                if ((C.AA(angleB.getValue(), angleC.getValue())) <= 0) {
                    notifierCas(0);
                } else {
                    angleA.setValue(C.AA(angleB.getValue(), angleC.getValue()));
                    coteC = C.AACopp(coteB, angleB.getValue(), angleC.getValue());
                    coteA = C.CAC(coteB, coteC, angleA.getValue());

                    notifierCas(3);
                }

                break;

            //==========cas Deux cotés, un angle opposé connus
            case "abA":

                if (angleA.getValue() >= Angle.MAX_ANGLE_VALUE) {
                    notifierCas(0);
                } else {

                    angleB.setValue(C.CCAopp(coteA, coteB, angleA.getValue()));
                    angleC.setValue(C.AA(angleA.getValue(), angleB.getValue()));
                    coteC = C.AACopp(coteB, angleB.getValue(), angleC.getValue());

                    notifierCas(4);
                }
                break;

            case "abB":
                if (angleB.getValue() >= Angle.MAX_ANGLE_VALUE) {
                    notifierCas(0);
                } else {
                    angleA.setValue(C.CCAopp(coteB, coteA, angleB.getValue()));
                    angleC.setValue(C.AA(angleA.getValue(), angleB.getValue()));
                    coteC = C.AACopp(coteB, angleB.getValue(), angleC.getValue());
                    notifierCas(4);
                }

                break;
            case "bcB":

                if (angleB.getValue() >= Angle.MAX_ANGLE_VALUE) {
                    notifierCas(0);
                } else {

                    angleC.setValue(C.CCAopp(coteB, coteC, angleB.getValue()));
                    angleA.setValue(C.AA(angleC.getValue(), angleB.getValue()));
                    coteA = C.AACopp(coteB, angleB.getValue(), angleA.getValue());

                    notifierCas(4);
                }
                break;

            case "bcC":

                if (angleC.getValue() >= Angle.MAX_ANGLE_VALUE) {
                    notifierCas(0);
                } else {

                    angleB.setValue(C.CCAopp(coteC, coteB, angleC.getValue()));
                    angleA.setValue(C.AA(angleC.getValue(), angleB.getValue()));
                    coteA = C.AACopp(coteB, angleB.getValue(), angleA.getValue());

                    notifierCas(4);
                }

                break;

            case "acC":

                if (angleC.getValue() >= Angle.MAX_ANGLE_VALUE) {
                    notifierCas(0);
                } else {

                    angleA.setValue(C.CCAopp(coteC, coteA, angleC.getValue()));
                    angleB.setValue(C.AA(angleA.getValue(), angleC.getValue()));
                    coteB = C.AACopp(coteC, angleC.getValue(), angleB.getValue());

                    notifierCas(4);
                }

                break;

            //=================un cote deux agles adjacents=========
            case "aBC":

                if (((C.AA(angleB.getValue(), angleC.getValue())) <= 0)) {
                    notifierCas(0);
                } else {
                    angleA.setValue(C.AA(angleB.getValue(), angleC.getValue()));

                    coteB = C.AACopp(coteA, angleA.getValue(), angleB.getValue());
                    coteC = C.CAC(coteA, coteB, angleC.getValue());

                    notifierCas(5);

                }

                break;
            case "bAC":

                if (((C.AA(angleA.getValue(), angleC.getValue())) <= 0)) {
                    notifierCas(0);
                } else {
                    angleB.setValue(C.AA(angleA.getValue(), angleC.getValue()));
                    coteC = C.AACopp(coteB, angleB.getValue(), angleC.getValue());
                    coteA = C.CAC(coteB, coteC, angleA.getValue());

                    notifierCas(5);

                }

                break;

            case "cAB":

                if (((C.AA(angleA.getValue(), angleB.getValue())) <= 0)) {
                    notifierCas(0);
                } else {

                    angleC.setValue(C.AA(angleA.getValue(), angleB.getValue()));
                    coteB = C.AACopp(coteC, angleC.getValue(), angleB.getValue());
                    coteA = C.CAC(coteB, coteC, angleA.getValue());

                    notifierCas(5);

                }

                break;

            default:
                notifierCas(0);
                break;
        }

    }

    //afficher les resultat des calculs dans les champs correspondant
    private void viewResults() {
        coteAResultField.setText(String.valueOf(coteA));
        coteBResultField.setText(String.valueOf(coteB));
        coteCResultField.setText(String.valueOf(coteC));
        angleAResultField.setText(angleA.getStringValue());
        angleBResultField.setText(angleB.getStringValue());;
        angleCResultField.setText(angleC.getStringValue());;
    }

    private void clearResults() {
        coteAResultField.clear();
        coteBResultField.clear();
        coteCResultField.clear();
        angleAResultField.clear();
        angleBResultField.clear();
        angleCResultField.clear();
    }

    public void setTooltips() {

        String a = "";
        String b = a;
        String c = a;
        switch (S.CURRENT_UNIT.getValue()) {
            case Angle.GRAD:
                if (!U.isEmpty(angleAResultField)) {
                    a = angleA.getDeg() + " deg\n" + angleA.getRad() + " rad";
                } else {
                    a = "0 deg\n0 rad";
                }

                if (!U.isEmpty(angleBResultField)) {
                    b = angleB.getDeg() + " deg\n" + angleB.getRad() + " rad";
                } else {
                    b = "0 deg\n0 rad";
                }

                if (!U.isEmpty(angleCResultField)) {
                    c = angleC.getDeg() + " deg\n" + angleC.getRad() + " rad";
                } else {
                    c = "0 deg\n0 rad";
                }
                break;

            case Angle.DEG:
                if (!U.isEmpty(angleAResultField)) {
                    a = angleA.getGrad() + " gon\n" + angleA.getRad() + " rad";
                } else {
                    a = "0 gon\n0 rad";
                }

                if (!U.isEmpty(angleBResultField)) {
                    b = angleB.getGrad() + " gon\n" + angleB.getRad() + " rad";
                } else {
                    b = "0 gon\n0 rad";
                }

                if (!U.isEmpty(angleCResultField)) {
                    c = angleC.getGrad() + " gon\n" + angleC.getRad() + " rad";
                } else {
                    c = "0 gon\n0 rad";
                }

                break;

            case Angle.RAD:

                if (!U.isEmpty(angleAResultField)) {
                    a = angleA.getDeg() + " deg\n" + angleA.getGrad() + " gon";
                } else {
                    a = "0 deg\n0 gon";
                }

                if (!U.isEmpty(angleBResultField)) {
                    b = angleB.getDeg() + " deg\n" + angleB.getGrad() + " gon";
                } else {
                    b = "0 deg\n0 gon";
                }

                if (!U.isEmpty(angleCResultField)) {
                    c = angleC.getDeg() + " deg\n" + angleC.getGrad() + " gon";
                } else {
                    c = "0 deg\n0 gon";
                }

                break;

        }

        angleATooltip.setText(a);
        angleBTooltip.setText(b);
        angleCTooltip.setText(c);

    }

    public void convertIfNecessary() {
        if (!U.isEmpty(angleAField)) {
            angleAField.setText(angleA.getStringValue());
        }

        if (!U.isEmpty(angleBField)) {
            angleBField.setText(angleB.getStringValue());
        }

        if (!U.isEmpty(angleCField)) {
            angleCField.setText(angleC.getStringValue());
        }

        process();
    }

    private class TextFieldListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

            coteAResultField.setText(coteAField.getText());
            coteBResultField.setText(coteBField.getText());
            coteCResultField.setText(coteCField.getText());
            angleAResultField.setText(angleAField.getText());
            angleBResultField.setText(angleBField.getText());
            angleCResultField.setText(angleCField.getText());
            process();

        }

    }

    public void process() {
        //verifier et recupere les valeurs saisie dans les champs
        if (U.isNumeric(angleAField)) {
            angleA.setValue(U.get(angleAField));
        } else {
            angleA.setValue(0);
        }

        if (U.isNumeric(angleBField)) {
            angleB.setValue(U.get(angleBField));
        } else {
            angleB.setValue(0);

        }

        if (U.isNumeric(angleCField)) {
            angleC.setValue(U.get(angleCField));
        } else {
            angleC.setValue(0);
        }

        if (U.isNumeric(coteAField)) {
            coteA = U.get(coteAField);
        } else {
            coteA = 0;
        }

        if (U.isNumeric(coteBField)) {
            coteB = U.get(coteBField);
        } else {
            coteB = 0;
        }

        if (U.isNumeric(coteCField)) {
            coteC = U.get(coteCField);
        } else {
            coteC = 0;
        }

        //verifer qu'il y'a strictement 3 champs remplis et resoudre
        if (getInputs() == 3) {

            solve(code);
            //verifier apres resolution qu'aucune valeur n'est vide
            if (checkResultKO()) {
                notifierCas(0);
            } else {
                //afficher les resltats
                viewResults();
            }

        } else if (getInputs() > 3) {
            notifierErreurValeurs();
            clearResults();
        } else {
            notifierErreurValeurs();
            //clearResults();
        }

        setTooltips();

    }

    private boolean checkResultKO() {
        //verifier sil y'a dev valeurs nulles apres le calcul dans ce cas on n'a pas de splution
        return (coteA == 0 || coteB == 0 || coteC
                == 0
                || angleA.getValue()
                == 0 || angleB.getValue() == 0 || angleC.getValue() == 0);
    }

    private class DefaultUnitChangeListener implements ChangeListener<String> {

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

    private class LengthPrecisionChangeListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            process();
        }

    }

}
