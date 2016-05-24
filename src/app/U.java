/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.lang.reflect.Field;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author mohamed
 */
public class U {

    public static boolean isNumeric(String string) {

        if (string.trim().isEmpty()) {
            return false;
        } else {
            try {
                Double.parseDouble(string);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }

    public static boolean isNumeric(TextField field) {
        String string = field.getText();

        if(string.contains(" "))
            return false;
        if (string.isEmpty()) {
            return false;
        } else {
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

    public static double get(TextField textField) {
        return Double.parseDouble(textField.getText().trim());
    }

    public static boolean isEmpty(TextField field) {
        return field.getText().isEmpty();
    }

    public static void showWaringDialog(String message) {

        Dialogs.create()
                .owner(Main_.getStage())
                .title("")
                //.masthead("Look, a Warning Dialog")
                .message(message).showWarning();

    }

    public static void showWarning(String message, AnchorPane owner) {

        Notifications.create()
                //.title("Title Text")
                .owner(owner)
                .text(message)
                .hideAfter(new Duration(2500))
                .position(Pos.CENTER)
                .showWarning();
    }

    public static void hackTooltip(Tooltip tooltip) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);
            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);
            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(10)));
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
