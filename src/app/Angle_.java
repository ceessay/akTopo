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
public class Angle_ {

    private double deg, grad, rad;
    private double d, m, s; //utilisés pour l'unité degre, miniute, seconde

    public Angle_() {
        this.deg = this.grad = this.rad = 0;
        this.d = this.m = this.s = 0;
    }

    public void set(double value, String unit) {
        //  System.out.println("unit "+unit);
        switch (unit) {
            case Angle.DEG:
                this.deg = value;
                this.rad = C.degToRad(deg);
                this.grad = C.degToGrad(deg);
                setDMS();
                break;
            case Angle.GRAD:
                this.grad = value;
                this.deg = C.gradToDeg(grad);
                this.rad = C.gradToRad(grad);
                setDMS();
                break;
            case Angle.RAD:
                this.rad = value;
                this.deg = C.radToDeg(rad);
                this.grad = C.radToGrad(rad);
                setDMS();
                break;
        }
    }

    public double get(String unit) {

        double value = 0;
        switch (unit) {
            case Angle.DEG:
                value = this.deg;
                break;

            case Angle.GRAD:
                value = this.grad;
                break;

            case Angle.RAD:
                value = this.rad;
                break;
        }

        // System.out.println("affiche "+value);
        return C.roundAngle(value);
    }

    public double[] getDms() {
        double[] dms = {d, m, s};
        return dms;
    }

    public String getString(String unit) {

        String value = null;
        switch (unit) {
            case Angle.DEG:
                value = String.valueOf(C.roundAngle(this.deg));
                break;

            case Angle.GRAD:
                value = String.valueOf(C.roundAngle(this.grad));
                break;

            case Angle.RAD:
                value = String.valueOf(C.roundAngle(this.rad));
                break;
            case Angle.DMS:
                value = C.roundAngle(this.d) + " " + C.roundAngle(this.m) + " " + C.roundAngle(this.s);
                break;
        }
        // System.out.println("affiche "+value);
        return value;
    }

    //=====methodes  pour la gestion de l'unite degré, minute, seconde=====
    private void setDMS() {
        double[] dms = C.degtoDMS(deg);
        this.d = dms[0];
        this.m = dms[1];
        this.s = dms[2];
    }

    public void setDMS(double d, double m, double s) {
        this.d = d;
        this.m = m;
        this.s = s;
        this.deg = C.dmsToDeg(d, m, s);
        this.grad = C.degToGrad(this.deg);
        this.rad = C.degToRad(this.deg);
    }

    public String getDMString() {
        return C.roundAngle(this.d) + "° " + C.roundAngle(this.m) + "' " + C.roundAngle(this.s)+"\"";
    }
}
