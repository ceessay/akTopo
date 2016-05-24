/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author mohamed
 */
public class C {

    private static MathContext mc = new MathContext(15);

    public static double Distance(Point A, Point B) {
        return roundDist(A.distance(B));
    }

    //calcul du gisement
    public static double Gisement(Point A, Point B) {

        double DX = B.x() - A.x();
        double DY = B.y() - A.y();

        double gisement
                = 2 * arctan(DX / round(((DY) + racine((DX * DX) + (DY * DY))), 15));

        gisement = (radToGrad(gisement));

        if (gisement < 0) {
            gisement += 400;
        }

        return roundAngle(gisement);
    }

//    //calcule du point rayonné
    public static Point PointRayonne(Point station, double gisement, double distance) {
        /*
         formule
         X(P)= X+ D*sin(G)						
         Y(P)=Y+D*cos(G)
         G etant convertit en grades
         */
        double sinus = 0, cosinus = 0;

        switch (S.CURRENT_UNIT.getValue()) {
            case Angle.DEG:
                sinus = sin(degToRad(gisement));
                cosinus = cos(degToRad(gisement));
                break;
            case Angle.GRAD:
                sinus = sin(gradToRad(gisement));
                cosinus = cos(gradToRad(gisement));
                break;
            case Angle.RAD:
                sinus = sin(gisement);
                cosinus = cos(gisement);
                break;
        }

        double xp = station.x() + (distance * sinus);
        double yp = station.y() + (distance * cosinus);

//        double xp = station.x() + (distance * sin(gradToRad(gisement)));
//        double yp = station.y() + (distance * cos(gradToRad(gisement)));
        Point pointRayonne = new Point(xp, yp);

        return pointRayonne;

    }

//=========================Formules pour la resolution d'un triangle====================
/*   coté-coté-cote (CCC)
     retourne la valeur d'un angle a partir de 3 cotés 
     en utilisant la lois des cosinus: relation 1
     pour trouver l'angle A passer en parametre a,b,c
     pour B==> b,a,c
     pour C==> c,a,b
     */
    public static double CCC(double a, double b, double c) {

        // (a * a + b * b - c * c) / (2 * a * b)
        double v = (a * a + b * b - c * c) / (2 * a * b);

        double result;

        if (S.CURRENT_UNIT.getValue().equals(Angle.DEG)) {
            result = radToDeg(arcos(v));
        } else if (S.CURRENT_UNIT.getValue().equals(Angle.GRAD)) {
            result = radToGrad(arcos(v));
        } else {
            result = arcos(v);
        }

        return roundAngle(result);

    }

    // verifier CCC
    public static boolean checkCCC(double a, double b, double c) {

        double val = (a * a + b * b - c * c) / (2 * a * b);

        return val >= -1 && val <= 1;

    }

    /*
     calculer la valeur d'un troisieme cote a partir d'un angle et de ses deux coté adjacents
     cote-angle-coté (CAC)
     4=rac((a²+b²)-(2ab*cosC))								
     */
    public static double CAC(double a, double b, double C) {

        if (S.CURRENT_UNIT.getValue().equals(Angle.GRAD)) {
            C = gradToRad(C);
        } else if (S.CURRENT_UNIT.getValue().equals(Angle.DEG)) {
            C = degToRad(C);
        }

        double result = Math.sqrt(a * a + b * b - 2 * a * b * cos(C));

        return roundDist(result);
    }

    /*
     calculer un angle a partir des deux autres angles connus
     */
    public static double AA(double A, double B) {
        double X;
        if (S.CURRENT_UNIT.getValue().equals(Angle.GRAD)) {
            X = Angle.MAX_GRAD_VALUE;
        } else if (S.CURRENT_UNIT.getValue().equals(Angle.DEG)) {
            X = Angle.MAX_DEG_VALUE;
        } else {
            X = Angle.MAX_RAD_VALUE;
        }

        return roundAngle(X - (A + B));
    }

    /*
     relation des sinus
     determiner un cote a partir de deux angles et de'un coté oppose au angles
     */
    public static double AACopp(double a, double A, double B) {

        if (S.CURRENT_UNIT.getValue().equals(Angle.DEG)) {
            A = degToRad(A);
            B = degToRad(B);
        } else if (S.CURRENT_UNIT.getValue().equals(Angle.GRAD)) {
            A = gradToRad(A);
            B = gradToRad(B);

        }

        double result = (a / sin(A)) * sin(B);

        return roundDist(result);
    }

    /*
     determine un angle a partir de deux coté et un angle opposé
     *lois des sinus
     */
    public static double CCAopp(double a, double b, double A) {

        if (S.CURRENT_UNIT.getValue().equals(Angle.DEG)) {
            A = degToRad(A);
        } else if (S.CURRENT_UNIT.getValue().equals(Angle.GRAD)) {
            A = gradToRad(A);
        }

        double sinus = ((b * sin(A)) / a);

        double result = radToGrad(arcsin(sinus));
        return roundAngle(result);
    }

    /*=====================================================================================
     ================== quelques methodes pour une meilleur lisibilité des formules================
     ======================================================================================*/
    private static double carre(double val) {
        return Math.pow(val, 2);
    }

    private static double racine(double val) {
        return Math.sqrt(val);
    }

    private static double sin(double val) {
        return Math.sin(val);
    }

    private static double cos(double val) {
        return Math.cos(val);
    }

    private static double tan(double val) {
        return Math.tan(val);
    }

    private static double arcos(double val) {
        return Math.acos(val);
    }

    private static double arcos(BigDecimal val) {
        return Math.acos(val.doubleValue());
    }

    private static double arcsin(double val) {
        return Math.asin(val);
    }

    private static double arctan(double val) {
        return Math.atan(val);
    }

    /*=========================================================================================
     ========================== Méthodes de conversiondes angles=============================
     =========================================================================================*/
    //  radians en degrés
    public static double radToDeg(double radian) {
        // degre = radian * 180/pi
        return Math.toDegrees(radian);
    }

    //  grades en degres
    public static double gradToDeg(double grade) {
        // degré = 180 * (grade) / 200 
        return 180 * grade / 200;
    }

    //  degrés en radians
    public static double degToRad(double degre) {
        //radian = degre * pi/180
        return Math.toRadians(degre);
    }

// grades en radians 
    public static double gradToRad(double grade) {
        double radian = Math.PI * (grade) / 200;
        return radian;
    }

    // les degrés en grad
    public static double degToGrad(double degre) {
        //grade = 200 * ( degré) / 180 
        return 200 * degre / 180;
    }

    // radians en grades
    public static double radToGrad(double radian) {
        // grade = 200 * (radian) / pi 
        return 200 * radian / Math.PI;
    }

    //degrés en degrés, minutes secondes
    public static double[] degtoDMS(double degre) {
        
        int d = (int) degre;
        double y = (degre - d) * 60; // reste X 60
        double m = (int) y;
        y = (y - m) * 60; // reste X 60
        double s = y;

        double[] dms = {d, m, s};
        return dms;

    }
    
    //degrés, minutes secondes en degrés
    public static double dmsToDeg(double d, double m, double s){
        return d + m/60 + s/3600;
    }
    
    //grad en dms
    public static double[] gradToDMS(double grad){
        
       return degtoDMS(gradToDeg(grad));
    }
    
    //dms en grad
    public static double dmsToGrad(double d, double m, double s){
       return degToGrad(dmsToDeg(d, m, s));
    }
    
    //radians en dms
    public static double[] radToDMS(double rad){
        
       return degtoDMS(radToDeg(rad));
    }
    
    //dms n radians
    public static double dmsToRad(double d, double m, double s){
       return degToRad(dmsToDeg(d, m, s));
    }
    
    /*=====================================================================================
     ====================================Arrondir les valeurs=================================
     =======================================================================================*/

    public static double round(double d, int n) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(n, RoundingMode.DOWN);
        return bd.doubleValue();
    }

    public static double roundDist(double d) {

        int x = S.LENGTH_PRECISION.getValue();

        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(x, RoundingMode.HALF_UP);
        return bd.doubleValue();

        //return (long) (d * 1e2) / 1e2;
    }

    public static double roundAngle(double d) {
        int x = S.ANGLE_PRECISION.getValue();

        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(x, RoundingMode.HALF_UP);
        return bd.doubleValue();

        //return (long) (d * 1e4) / 1e4;
    }

}
