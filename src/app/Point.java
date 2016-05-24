/*
 * Definition d'un point dans le plan
 */
package app;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author mohamed
 */
public class Point {

//    private  BigDecimal x;    // abscice
//    private  BigDecimal y;    // ordonnée
//
//    public Point(BigDecimal x, BigDecimal y) {
//        this.x = C_.round(x, 3);
//        this.y = C_.round(y, 3);
//    }
//
//    
//     public Point(String x, String y) {
//        this.x = C_.round(new BigDecimal(x), 3);
//        this.y = C_.round(new BigDecimal(y), 3);
//    }
//    
//    // getters
//    public BigDecimal x() {
//        return x;
//    }
//
//    public BigDecimal y() {
//        return y;
//    }
//
//    public void setX(BigDecimal x) {
//        this.x = C_.round(x, 3);
//    }
//
//    public void setY(BigDecimal y) {
//        this.y = C_.round(y, 3);
//    }
//    
//
//
//    // distance entre deux point
//    public BigDecimal distance(Point p) {
//        BigDecimal dx = this.x.subtract(p.x);
//        BigDecimal dy = this.y.subtract(p.y);
//        return BigDecimalMath.sqrt(dx.multiply(dx).add( dy.multiply(dy)));
//    }
//
//    
//    public String toString() {
//        return "(" + x + ", " + y + ")";
//    }
//    
    private double x;    // abscice
    private double y;    // ordonnée

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // getters
    public double x() {
      return C.roundDist(x)  ;
    }

    public double y() {
        return C.roundDist(y);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double distance(Point p) {
        double dx = this.x - p.x;
        double dy = this.y - p.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

//    public static double round(double d) {
//        BigDecimal bd = new BigDecimal(d);
//        bd = bd.setScale(3, RoundingMode.HALF_EVEN);
//        return bd.doubleValue();
//    }

}
