package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;



public class Linie extends Line {


    public double getDeltaX() {
        return getEndX()-getStartX();
    }


    public double getDeltaY() {
        return getEndY()-getStartY();
    }
}
