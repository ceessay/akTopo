/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.components;

import app.GUIs;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import app.S;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * FXML Controller class
 *
 * @author mohamed
 */
public class HomeController implements Initializable {

    @FXML
    private Button gdBtn, prBtn, triBtn, convBtn;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private void handlebutton(ActionEvent event) {

       // rootPane.getChildren().removeAll();
        if (event.getSource().equals(gdBtn)) {
            GUIs.mainPane.selectView(S.GD);

        } else if (event.getSource().equals(prBtn)) {
            GUIs.mainPane.selectView(S.PR);

        } else if (event.getSource().equals(triBtn)) {
            GUIs.mainPane.selectView(S.Tri);

        } else if (event.getSource().equals(convBtn)) {
            GUIs.mainPane.selectView(S.Conv);
        }

        rootPane.getScene().setRoot(GUIs.mainPane);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

}
