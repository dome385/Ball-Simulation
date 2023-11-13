package sample;


import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;


import java.lang.Math;


public class Pendel extends Line{


    public double length;
    public double mass;
    double g = 9.8;
    double deltaTime = 1/60;

    public double amplitute = Math.PI / 2;
    public double velocity;
    public double acceleration;


    public Pendel(int length){
        this.length = length;
    }

    public void run(){
        acceleration = g / length * Math.sin(amplitute);
        velocity += acceleration * deltaTime;
        amplitute += velocity * deltaTime;
    }

    public void createPendel(){
        Line test = new Line();
        Pendel p = new Pendel(100);
        test.setEndX(7.0);
        test.setStartX(-153.0);
    }







}
