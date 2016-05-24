/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author mohamed
 */
public class Angle {

// chaines representants les diffenrentes unité utilisé 
    public final static String DEG = "deg";
    public final static String RAD = "rad";
    public final static String GRAD = "gon";
    public final static String DMS = "d.m.s.";

    public final static double MAX_DEG_VALUE = 180;
    public final static double MAX_GRAD_VALUE = 200;

    public final static double MAX_RAD_VALUE = Math.PI;

    
    public static double MAX_ANGLE_VALUE = Angle.MAX_GRAD_VALUE;

    private double deg, grad, rad;

    public Angle() {
    }

    
    public Angle(double value) {
        setValue(value);
    }

    public Angle(double value, String UNIT) {

        switch (UNIT) {
            case DEG:
                this.deg = value;
                this.rad = C.degToRad(deg);
                this.grad = C.degToGrad(deg);
                break;
            case GRAD:
                this.grad = value;
                this.deg = C.gradToDeg(grad);
                this.rad = C.gradToRad(grad);
                break;
            case RAD:
                this.rad = value;
                this.deg = C.radToDeg(rad);
                this.grad = C.radToGrad(rad);
                break;
        }

    }

    public void setValue(double value) {

        switch (S.CURRENT_UNIT.getValue()) {
            case DEG:
                this.deg = value;
                this.rad = C.degToRad(deg);
                this.grad = C.degToGrad(deg);
                break;
            case GRAD:
                this.grad = value;
                this.deg = C.gradToDeg(grad);
                this.rad = C.gradToRad(grad);
                break;
            case RAD:
                this.rad = value;
                this.deg = C.radToDeg(rad);
                this.grad = C.radToGrad(rad);
                break;
        }

    }

    public double getValue() {
        double value = 0;
        switch (S.CURRENT_UNIT.getValue()) {
            case DEG:
                value = this.deg;
                break;
            case GRAD:
                value = this.grad;
                break;
            case RAD:
                value = this.rad;
                break;
        }

        return C.roundAngle(value);
    }

    public String getStringValue() {
        double value = 0;
        switch (S.CURRENT_UNIT.getValue()) {
            case DEG:
                value = this.deg;
                break;
            case GRAD:
                value = this.grad;
                break;
            case RAD:
                value = this.rad;
                break;
        }

        return String.valueOf(C.roundAngle(value));
    }

    
    public static double getMAX_VALUE() {
        double val = 0;
        switch (S.CURRENT_UNIT.getValue()) {
            case DEG:
                val = MAX_DEG_VALUE;break;
            case GRAD:
                val = MAX_GRAD_VALUE;break;
            case RAD:
                val= MAX_RAD_VALUE;break;
        }

        return val;
    }

    public double getDeg() {
        return C.roundAngle(deg);
    }

    public double getGrad() {
        return C.roundAngle(grad);
    }

    public double getRad() {
        return C.roundAngle(rad);
    }
    
    

}
