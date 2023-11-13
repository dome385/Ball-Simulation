package sample;

import java.util.Vector;

public class Vektor extends Vector {
    double x;
    double y;
    double z;

    public Vektor(double x, double y){
        this.x = x;
        this.y = y;
        z = 0;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vektor malK(Vektor vektor,double k) {
        double u = k * vektor.getX();
        double v = k * vektor.getY();
        return new Vektor(u,v);

    }
    public Vektor addition(Vektor a, Vektor b) {
        double vektorX = a.getX() + b.getX();
        double vektorY = a.getY() + b.getY();
        return new Vektor(vektorX,vektorY);
    }

    public Vektor subtraktion(Vektor a, Vektor b) {
        double X = a.getX() - b.getX();
        double Y = a.getY() - b.getY();
        return new Vektor(X,Y);
    }

}
