package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private AnchorPane scene;
    @FXML
    private Circle circle;
    @FXML
    private AnchorPane scene1;
    @FXML
    Line linie;
    @FXML
    Button start;
    @FXML
    Button stop;
    @FXML
    Button restart;
    @FXML
    TextField xgeschw;
    @FXML
    TextField ygeschw;
    @FXML
    TextField xpos;
    @FXML
    TextField ypos;
    @FXML
    TextField wind2;
    @FXML
    ToggleButton gtoggle;
    @FXML
    TextField endeX;
    @FXML
    TextField endeY;
    @FXML
    TextField ausgabe;
    @FXML
    TextField winkel;
    @FXML
    Circle circle1;
    @FXML
    Line linie1;
    @FXML
    Line pendelLinie;
    @FXML
    Circle pendelGewicht;
    @FXML
    Line pendelStop;
    @FXML
    Line stopper;
    @FXML
    CheckBox colormode;





    private double deltaTime = 1/(double)60;


    //Ball 1
    public double xKoord = 20;
    public double yKoord = 260;
    public double StartGeschwindigkeitX = 6;
    public double StartGeschwindigkeitY = 1.5;
    public double xGeschw;
    public double yGeschw;
    //Ball 2
    public double xKoord2 = 560;
    public double yKoord2 = 429;
    public double xGeschw2;
    public double yGeschw2;
    public double pendelEnde = 775;
    public double geradesRollen = 0;

    private double wind;
    private double gravitation;
    private double masse = 1;
    private double masse2 = 1;
    private boolean kollision1 = false;
    private boolean kollision2 = false;
    Vektor vek = new Vektor(0,0);
    double betragSenkrecht;
    boolean ballball = true;
    Vektor hilfsvektor1 = new Vektor(0,0);
    boolean rollen = false;
    boolean pendel = false;
    boolean endrollen = false;

    double beschleunigung;
    double velocity ;
    double winkel1 = -0.5 * Math.PI; // 90 Grad;


    void rollen1(double deltaTime, double winkel){

        double reibung = 0.0008;
        //Schwerkraft
        double schwerkraft = gravitation;
        //Hangabtriebsbeschleunigung
        double aH = schwerkraft * Math.sin((Math.toRadians(winkel)));
        //Normalbeschleunigung
        double aN = schwerkraft * Math.cos((Math.toRadians(winkel)));
        //Reibung = Reibungszahl * Normale
        double aR = reibung * aN;
        //Vektor hangabtrieb = new Vektor(geschwindigkeit.getX() * aH, geschwindigkeit.getY() * aH);
        //Vektor reibungg = new Vektor(geschwindigkeit.getX() * aR, geschwindigkeit.getY() * aR);


        //Reibung in x-Richtung
        double xR = aR * Math.cos((Math.toRadians(winkel)));
        //Reibung in y-Richtung
        double yR = aR * Math.sin((Math.toRadians(winkel)));
        //Hangabtrieb in x-Richtung
        double xH = aH * Math.cos((Math.toRadians(winkel)));
        //Hangabtrieb in y-Richtung
        double yH = aH * Math.sin((Math.toRadians(winkel)));


            xGeschw2 = StartGeschwindigkeitX + ((xH - xR) * deltaTime);
            yGeschw2 = StartGeschwindigkeitY + ((yH - yR) * deltaTime);

            xKoord2 = xKoord2 + (xGeschw2 * deltaTime + (0.5 * xH * Math.pow(deltaTime, 2) - 0.5 * xR * Math.pow(deltaTime, 2)));
            yKoord2 = yKoord2 + (yGeschw2 * deltaTime + (0.5 * yH * Math.pow(deltaTime, 2) - 0.5 * yR * Math.pow(deltaTime, 2)));




    }


    public double ballAbstand(Circle circle, Circle circle1){
        double nx = circle1.getLayoutX() - circle.getLayoutX();
        double ny = circle1.getLayoutY() - circle.getLayoutY();
        double abstand = Math.sqrt(Math.pow(nx, 2) + Math.pow(ny, 2));
        //System.out.print("abstand" + abstand);
        return abstand;

    }


    public void ballballkollision(Circle circle, Circle circle1){
        double nx = circle1.getLayoutX() - circle.getLayoutX();
        double ny = circle1.getLayoutY() - circle.getLayoutY();
        double abstand = Math.sqrt(Math.pow(nx, 2) + Math.pow(ny, 2));
        double nx1 = nx/abstand;
        double ny1 = ny/abstand;
        Vektor normale = new Vektor(nx1, ny1);


        //Geschwindigkeitsvektoren in senkrecht und parallel mit Orthogonaler Zerlegung
       //Ball 1
        Vektor senkrecht = hilfsvektor.malK(normale, skalarprodukt(getVek(), normale) / skalarprodukt(normale, getVek()));
        Vektor parallel = hilfsvektor.subtraktion(getVek(), senkrecht);
        //Ball 2
        Vektor senkrecht1 = hilfsvektor1.malK(normale, skalarprodukt(getVek2(), normale) / skalarprodukt(normale,normale));
        Vektor parallel1 = hilfsvektor1.subtraktion(getVek2(), senkrecht1);


        //v1` = 2 * (m1 * v1 + m2 * v2) / (m1 + m2) - v1
        Vektor neuerVektor = hilfsvektor.subtraktion(hilfsvektor.malK(hilfsvektor.malK(hilfsvektor.addition(hilfsvektor.malK(parallel, masse), hilfsvektor.malK(parallel1, masse2)), 1 / (masse + masse2)), 2), parallel);
        //v2` = 2 * (m1 * v1 + m2 * v2) / (m1 + m2) - v2
        Vektor neuerVektor1 = hilfsvektor.subtraktion(hilfsvektor.malK(hilfsvektor.malK(hilfsvektor.addition(hilfsvektor.malK(parallel, masse), hilfsvektor.malK(parallel1, masse2)), 1 / (masse + masse2)), 2), parallel1);




        Vektor v1 = hilfsvektor1.addition(senkrecht, neuerVektor);
        Vektor v2 = hilfsvektor1.addition(senkrecht1, neuerVektor1);
        setVek(v1);
        setVek2(v2);


    }






    public double skalarprodukt(Vektor a, Vektor b) {
        return b.getX()*a.getX()+b.getY()*a.getY();
    }

    //Point2D beschleunigung = new Point2D(wind, gravitation);
    //Point2D a = new Point2D(0,9.84);
    Vektor geschwindigkeit = new Vektor(xGeschw,yGeschw);
    Vektor hilfsvektor = new Vektor(0,0);


    public Vektor getVek() {
        vek.setX(xGeschw);
        vek.setY(yGeschw);
        return vek;
    }

    public void setVek(Vektor vek) {
        this.vek = vek;
        xGeschw= vek.getX();
        yGeschw= vek.getY();
    }

    public Vektor getVek2() {
        vek.setX(xGeschw2);
        vek.setY(yGeschw2);
        return vek;
    }

    public void setVek2(Vektor vek) {
        this.vek = vek;
        xGeschw2= vek.getX();
        yGeschw2= vek.getY();
    }


    void geradenKollision(Line tester){
        double deltaX = tester.getEndX() - tester.getStartX();
        double deltaY = tester.getEndY() - tester.getStartY();
        Vektor linienNormale;
        linienNormale = new Vektor(deltaY, deltaX * -1);

        //Orthogonale Zerlegung
        Vektor senkrecht = hilfsvektor.malK(linienNormale, skalarprodukt(getVek(), linienNormale) / skalarprodukt(linienNormale, linienNormale));
        Vektor parallel = hilfsvektor.subtraktion(getVek(), senkrecht);
        betragSenkrecht = Math.sqrt(Math.pow(senkrecht.getX(), 2) + Math.pow(senkrecht.getY(), 2));

        setVek(hilfsvektor.subtraktion(parallel, senkrecht));
        setVek(new Vektor(getVek().getX() * 0.7, getVek().getY() * 0.7));
        //System.out.println("betrag senkrecht" + betragSenkrecht);


    }




    void neuePos (double t, double g){
        //s0 + v0 * t
        xKoord = xKoord + xGeschw * t + 0.5 * wind * Math.pow(t, 2); // wind
        //s0 + v0 * t + 0.5 * a * t^2
        yKoord = yKoord + yGeschw * t + 0.5 * gravitation * Math.pow(t, 2); //konstante beschleunigung
    }

    void neuePos2 (double t, double g){
        //s0 + v0 * t
        xKoord2 = xKoord2 + xGeschw2 * t + 0.5 * wind * Math.pow(t, 2); // wind
        //s0 + v0 * t + 0.5 * a * t^2
        yKoord2 = yKoord2 + yGeschw2 * t + 0.5 * gravitation * Math.pow(t, 2); //konstante beschleunigung
    }

    //v0 + a * t
    void neueGeschw (double t, double g, double wind){
        xGeschw = xGeschw + wind * t; //wind
        yGeschw = yGeschw + gravitation * t; // gravitation

    }

    void neueGeschw2 (double t, double g, double wind){
        xGeschw2 = xGeschw2 + wind * t; //wind
        yGeschw2 = yGeschw2 + gravitation * t; // gravitation

    }

    public static double textinDouble(TextField textField){
        String text = textField.getText();
        return Double.parseDouble(text);
    }

    void rollen(double deltaTime, double winkel){

        double reibung = 0.0005;
        //Schwerkraft
        double schwerkraft = gravitation;
        //Hangabtriebsbeschleunigung
        double aH = schwerkraft * Math.sin((Math.toRadians(winkel)));
        //Normalbeschleunigung
        double aN = schwerkraft * Math.cos((Math.toRadians(winkel)));
        //Reibung = Reibungszahl * Normale
        double aR = reibung * aN;
        //Vektor hangabtrieb = new Vektor(geschwindigkeit.getX() * aH, geschwindigkeit.getY() * aH);
        //Vektor reibungg = new Vektor(geschwindigkeit.getX() * aR, geschwindigkeit.getY() * aR);


        //Reibung in x-Richtung
        double xR = aR * Math.cos((Math.toRadians(winkel)));
        //Reibung in y-Richtung
        double yR = aR * Math.sin((Math.toRadians(winkel)));
        //Hangabtrieb in x-Richtung
        double xH = aH * Math.cos((Math.toRadians(winkel)));
        //Hangabtrieb in y-Richtung
        double yH = aH * Math.sin((Math.toRadians(winkel)));

        //Bei gerader Fläche wirkt nur Reibung
        if(winkel == 0) {
            xGeschw = xGeschw - xR * deltaTime;
            yGeschw = geradesRollen;
            xKoord = xKoord + (xGeschw * deltaTime + (0.5 * Math.pow(deltaTime, 2) - 0.5 * xR * Math.pow(deltaTime, 2)));
            yKoord = yKoord + (yGeschw*deltaTime+(0.5*Math.pow(deltaTime,2) - 0.5*yR*Math.pow(deltaTime,2)));
        }
        //Bergab Rollen -> Reibung wirkt entgegen der Hangabtriebskraft
        else {

            xGeschw = xGeschw + ((xH - xR) * deltaTime);
            yGeschw = yGeschw + ((yH - yR) * deltaTime);
            xKoord = xKoord + (xGeschw * deltaTime + (0.5 * xH * Math.pow(deltaTime, 2) - 0.5 * xR * Math.pow(deltaTime, 2)));
            yKoord = yKoord + (yGeschw * deltaTime + (0.5 * yH * Math.pow(deltaTime, 2) - 0.5 * yR * Math.pow(deltaTime, 2)));
            Vektor v2 = new Vektor(xGeschw, yGeschw);
        }



    }

    public double abstandKugeln(){
        double nx = circle1.getLayoutX() - circle.getLayoutX();
        double ny = circle1.getLayoutY() - circle.getLayoutY();
        double abstand = Math.sqrt(Math.pow(nx, 2) + Math.pow(ny, 2));
        //System.out.println("Kugel Abstand:" + abstand);
        return abstand;
    }

    public double geradenWinkel(){

        double deltaX = linie.getEndX() - linie.getStartX();
        double deltaY = linie.getEndY() - linie.getStartY();
        double steigung = deltaY/deltaX;
        double winkel = Math.toDegrees(Math.atan(steigung));
        return winkel;
    }
    public double geradenWinkel1(){

        double deltaX = stopper.getEndX() - stopper.getStartX();
        double deltaY = stopper.getEndY() - stopper.getStartY();
        double steigung = deltaY/deltaX;
        double winkel = Math.toDegrees(Math.atan(steigung));
        return winkel;
    }

    public double pendelLinieBall(){
        double deltaX = pendelLinie.getEndX() - circle1.getLayoutX();
        double deltaY = pendelLinie.getEndY() - circle1.getLayoutY();
        double abstand = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        //System.out.println("pendelline abstand:" + abstand);
        return abstand;
    }

    public void pendel(){

        double deltaX = pendelLinie.getEndX() - pendelLinie.getStartX();
        double deltaY = pendelLinie.getEndY() - pendelLinie.getStartY();
        Vektor start = new Vektor(pendelLinie.getEndX(), pendelLinie.getEndY());
        double länge = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        beschleunigung = gravitation / länge * Math.sin(winkel1);
        velocity += beschleunigung * deltaTime;
        winkel1 += velocity * deltaTime;


        Vektor ende = new Vektor(start.getX() + Math.sin(winkel1) * länge, start.getY() - Math.cos(winkel1) * länge);

        pendelLinie.setStartX(ende.getX());
        pendelLinie.setStartY(ende.getY());

        System.out.println(ende.getY());
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        pendel = true;
                    }
                },//4350
                15130
        );

        if(pendel == true){
            velocity = 0;
        }




        /*
        double pendelGewicht = 7;
        double beschleunigung = pendelGewicht *  - gravitation / länge * Math.sin(winkel);
        double geschwindigkeit =+ beschleunigung;


        Rotate rotation = new Rotate();
        rotation.pivotXProperty().bind(pendelLinie.endXProperty());
        rotation.pivotYProperty().bind(pendelLinie.endYProperty());



        pendelLinie.getTransforms().add(rotation);

        rotation.setAngle(geschwindigkeit);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        pendel = true;
                    }
                },
                2300
        );

        if(pendel == true){
            rotation.setAngle(0);
        }

         */

    }


    public double trifftBalllLinie(Line tester, Circle ball){
        //Vektor aus Linie
        double deltaX = tester.getEndX() - tester.getStartX();
        double deltaY = tester.getEndY() - tester.getStartY();
        //Steigung
        double s = deltaY / deltaX;
        double ax = 1;
        double ay = s;
        //Länge des Normalenvektors
        double ba = Math.sqrt(Math.pow(ax, 2) + Math.pow(ay, 2));
        //Vektor von Linie zu Ball
        double qx = ball.getLayoutX() - tester.getStartX();
        double qy = ball.getLayoutY() - tester.getStartY();
        //Formel Geradengleichung
        double vx = ay * qx - ax * qy;
        double vy = ax * qy - ay * qx;
        double bv = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));

        //d = | a - b * n | / | n |
        double abstand = (bv / ba);
        //System.out.println("abstand: " + abstand);
        return abstand;


    }

    //Hessesche Normalform
    public double abstandLinie(Line tester){
        double deltaX = tester.getEndX() - tester.getStartX();
        double deltaY = tester.getEndY() - tester.getStartY();
        double normaleX = -deltaY;
        double normaleY = deltaX;
        double nlänge = Math.sqrt(Math.pow(normaleX, 2) + Math.pow(normaleY, 2));
        double normierteNormaleX = normaleX/nlänge;
        double normierteNormaleY = normaleY/nlänge;
        double verbindungX = circle1.getLayoutX() - tester.getStartX();
        double verbindungY = circle1.getLayoutY() - tester.getStartY();
        double abstand = Math.abs(verbindungX * normierteNormaleX + verbindungY * normierteNormaleY);
        //System.out.println("neuer abstandaaaaa:" + abstand);
        return abstand;

    }







    //1 Frame evey 10 millis, which means 100 FPS
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(6), new EventHandler<ActionEvent>() {


        @Override
        public void handle(ActionEvent actionEvent) {



                Bounds bounds = scene1.getBoundsInLocal();
                boolean rightBorder = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius() - 0.1);
                boolean leftBorder = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius() + 0.1);
                boolean bottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius() - 0.1);
                boolean topBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius() + 0.1);
            boolean rightBorder2 = circle1.getLayoutX() >= (bounds.getMaxX() - circle1.getRadius() - 0.1);
            boolean leftBorder2 = circle1.getLayoutX() <= (bounds.getMinX() + circle1.getRadius() + 0.1);
            boolean bottomBorder2 = circle1.getLayoutY() >= (bounds.getMaxY() - circle1.getRadius() - 0.1);
            boolean topBorder2 = circle1.getLayoutY() <= (bounds.getMinY() + circle1.getRadius() + 0.1);


                if (rightBorder || leftBorder) {
                    xGeschw *= -0.9;

                }
                if (bottomBorder || topBorder) {
                    yGeschw *= -0.9;

                }
            if (rightBorder2 || leftBorder2) {
                xGeschw2 *= -0.9;

            }
            if (bottomBorder2 || topBorder2) {
                yGeschw2 *= -0.9;
            }




            //Ball-Ball Kollision
            if (ballAbstand(circle, circle1) <= circle.getRadius() * 2 ){
               ballballkollision(circle, circle1);
               //rollen = true;

            }



            if(trifftBalllLinie(linie, circle) <= circle.getRadius() + 13 && kollision2 == false && circle.getLayoutX() <= linie.getEndX()){
                    geradenKollision(linie);
                    kollision2 = true;
                    System.out.println("KOLLISION");
                }
                else if(trifftBalllLinie(linie, circle) >= circle.getRadius() + 15 && kollision2 == true) {
                kollision2 = false;
                System.out.println("SPRUNG");
            }
                else if(trifftBalllLinie(linie, circle) <= circle.getRadius() + 15 && circle.getLayoutX() < linie.getEndX()) {
                rollen(deltaTime, geradenWinkel());
                System.out.println("ROLLEN");
            }
            else if(trifftBalllLinie(linie1, circle) <= circle.getRadius() + 15 &&betragSenkrecht <= 20 && betragSenkrecht != 0 && rollen == false){
                rollen(deltaTime, 0);
                System.out.println("Rollen - Nur Reibung wirkt");
            }
            else if(rollen == true){
                geradenKollision(linie1);
            }

            else{
                neuePos(deltaTime, gravitation);
                neueGeschw(deltaTime, gravitation, wind);
                System.out.println("fallen");
           }

            if(trifftBalllLinie(linie1, circle) <= circle.getRadius() + 15 && kollision1 == false && circle.getLayoutX() <= linie1.getEndX()){
                geradenKollision(linie1);
                kollision1 = true;
                System.out.println("KOLLISION1");
            }
            else if(trifftBalllLinie(linie1, circle) >= circle.getRadius() + 15 && kollision1 == true) {
                kollision1 = false;
                System.out.println("SPRUNG1");
            }

                neuePos2(deltaTime, gravitation);
                neueGeschw2(deltaTime, gravitation, wind);
                yGeschw2 = geradesRollen;

                if(circle1.getLayoutX() >= pendelEnde){
               pendel();
              xGeschw2 = geradesRollen;
           }
                if (pendel == true && endrollen == false){
                    rollen1(deltaTime, geradenWinkel1());
                    System.out.println("Zweite Kugel ROLLT");

                }
                if(circle1.getLayoutX() >= 920){
                    endrollen = true;
                    xGeschw2 = 0;
                    yGeschw2 = 0;
                }




            ausgabe.setText("X-Geschwindigkeit :" + xGeschw + "   " + "Y-Geschwindigkeit :" + yGeschw + "   " + "X-Position : " + xKoord + "   " + "Y-Position: " + yKoord + "");
            winkel.setText(geradenWinkel() + "°");
            circle.setLayoutX(xKoord);
            circle.setLayoutY(yKoord);
            circle1.setLayoutX(xKoord2);
            circle1.setLayoutY(yKoord2);




            if(gtoggle.isSelected() == true){
                gravitation = 0;
            } else {
                gravitation = 9.81;
            }




        }



    }));



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colormode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                circle.setFill(Color.RED);
                circle1.setFill(Color.RED);
            }
        });

        endeX.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                linie.setEndX(textinDouble(endeX));
            }
        });

        endeY.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                linie.setEndY(textinDouble(endeY));
            }
        });

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
            }
        });

        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                xKoord = 20;
                yKoord = 260;
                xGeschw = 0;
                yGeschw = 0;
                timeline.playFromStart();

            }
        });

        wind2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                wind = textinDouble(wind2);
            }
        });

        stop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                timeline.stop();
            }
        });

        xgeschw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                xGeschw = textinDouble(xgeschw);
            }
        });
        ygeschw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                yGeschw = textinDouble(ygeschw);
            }
        });
        xpos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                xKoord = textinDouble(xpos);
            }
        });

        ypos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                yKoord = textinDouble(ypos);
            }
        });



    }
}
