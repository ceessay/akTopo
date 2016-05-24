/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author mohamed
 */
public class S {
    
    public static String APP_NAME ="Askar-Topo (Beta)";

   //cette propriété definit l'unité par defaut utilisée pour les calculs sur les angles
   public static StringProperty CURRENT_UNIT = new SimpleStringProperty(Angle.GRAD);
   
   //definit la precision des resultat des calculs sur les angles
   public static IntegerProperty ANGLE_PRECISION = new SimpleIntegerProperty(4);
   
   //definit la precision des resultat des calculs sur les longueurs
   public static IntegerProperty LENGTH_PRECISION = new SimpleIntegerProperty(2);

    
    //chaine utilise dans les boutons d'accueil et dans le menu laterale
    public final static String GD = "Gisement et distance";
    public final static String PR = "Point Rayonné";
    public final static String Tri = "Résolution d'un triangle";
    public final static String Conv = "Convertisseur d'angles";


}
