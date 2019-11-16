package Landing;

import Data.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUILanding extends Application {

    private static final float WIDTH = 1400;
    private static final float HEIGHT = 800;
    private static final double TITAN_RADIUS = 25747;
    private static final double ROCKET_HEIGHT = 12000;


    private double distanceKM = 0;
    public double timeStep = 0.05;

    final int DRAW_CONSTANT = 1000;
    private CelestialBody[] planet = new CelestialBody[10];
    private Sphere[] pl;
    private Sphere rock;

    private Rocket rocket;

    Date date = new Date(2019, 07, 15);

    Group world = new Group();

    //Tracks drag starting point for x and y
    private double anchorX, anchorY;
    //Keep track of current angle for x and y
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    boolean freeze = true;
    boolean launching = false;
    //We will update these after drag. Using JavaFX property to bind with object
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    Label timeStepValue = showTimeStep();
    Label calendar = showDate();
    Label showAltitude = new Label();

    Slider slider = prepareSlider();


    public  void start(Stage primaryStage) throws Exception{

        Point rocketDistance = new Point(RealDistance.titanXDistance,RealDistance.titanYDistance - TITAN_RADIUS - ROCKET_HEIGHT - 5000, RealDistance.titanZDistance);


        //We instantiate all of the planets with their real data
        // found on https:// nasa Web horizons
        planet[0] = new CelestialBody("Sun", RealDistance.getSunDistance(), RealVelocities.getSunVelocity(), RealMasses.SUN_MASS);

        planet[1] = new CelestialBody("Mercury", RealDistance.getMercuryDistance(), RealVelocities.getMercuryVelocity(), RealMasses.MERCURY_MASS);

        planet[2] = new CelestialBody("Venus", RealDistance.getVenusDistance(), RealVelocities.getVenusVelocity(), RealMasses.VENUS_MASS);

        planet[3] = new CelestialBody("Earth", RealDistance.getEarthDistance(), RealVelocities.getEarthVelocity(), RealMasses.EARTH_MASS);

        planet[4] = new CelestialBody("Mars", RealDistance.getMarsDistance(),  RealVelocities.getMarsVelocity(), RealMasses.MARS_MASS);

        planet[5] = new CelestialBody("Jupiter", RealDistance.getJupiterDistance(), RealVelocities.getJupiterVelocity(), RealMasses.JUPITER_MASS);

        planet[6] = new CelestialBody("Saturn", RealDistance.getSaturnDistance(), RealVelocities.getSaturnVelocity(), RealMasses.SATURNE_MASS);

        planet[7] = new CelestialBody("Titan", RealDistance.getTitanDistance(),  RealVelocities.getTitanVelocity(), RealMasses.TITAN_MASS);

        planet[8] = new CelestialBody("Uranus", RealDistance.getUranusDistance(), RealVelocities.getUranusVelocity(), RealMasses.URANUS_MASS);

        planet[9] = new CelestialBody("Neptune", RealDistance.getNeptuneDistance(), RealVelocities.getNeptuneVelocity(), RealMasses.NEPTUNE_MASS);

        rocket = new Rocket("Rocket", rocketDistance , RealVelocities.getRocketVelocity(), 2.75 * Math.pow(10,4), new Point(0, 0, 0));

        PhongMaterial mercuryMaterial = new PhongMaterial();
        mercuryMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/mercury.jpeg")));

        PhongMaterial sunMaterial = new PhongMaterial();
        sunMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/sun.jpeg")));

        PhongMaterial venusMaterial = new PhongMaterial();
        venusMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/venusmap.jpeg")));

        PhongMaterial earthMaterial = new PhongMaterial();
        earthMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/earth/earth.normal.jpeg")));

        PhongMaterial marsMaterial = new PhongMaterial();
        marsMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/MarsMap.jpeg")));

        PhongMaterial jupMaterial = new PhongMaterial();
        jupMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/jupitermap.jpeg")));

        PhongMaterial saturnMaterial = new PhongMaterial();
        saturnMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/saturnmap.jpeg")));

        PhongMaterial uranusMaterial = new PhongMaterial();
        uranusMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/uranusmap.jpeg")));

        PhongMaterial neptuneMaterial = new PhongMaterial();
        neptuneMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/neptunemap.jpeg")));

        PhongMaterial titanMaterial = new PhongMaterial();
        titanMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("images/TitanMap.jpeg")));

        Sphere atmosphere = new Sphere();
        atmosphere.setTranslateX(planet[7].getXPosition());
        atmosphere.setTranslateY(planet[7].getYPosition());
        atmosphere.setTranslateZ(planet[7].getZPosition());

        pl = new Sphere[planet.length];

        for (int i = 0; i < pl.length; i++) {

            pl[i] = new Sphere();


            pl[i].setTranslateX(planet[i].getXPosition() / DRAW_CONSTANT - planet[7].getXPosition() / DRAW_CONSTANT);
            pl[i].setTranslateY(planet[i].getYPosition() / DRAW_CONSTANT - planet[7].getYPosition() / DRAW_CONSTANT);
            pl[i].setTranslateZ(planet[i].getZPosition() / DRAW_CONSTANT - planet[7].getZPosition() / DRAW_CONSTANT);


            switch (i) {
                case 0:
                    pl[i].setMaterial(sunMaterial);
                    pl[i].setRadius(30);
                    break;
                case 1:
                    pl[i].setMaterial(mercuryMaterial);
                    pl[i].setRadius(7.5);
                    break;
                case 2:
                    pl[i].setMaterial(venusMaterial);
                    pl[i].setRadius(10);
                    break;
                case 3:
                    pl[i].setMaterial(earthMaterial);
                    pl[i].setRadius(12.5);
                    break;
                case 4:
                    pl[i].setMaterial(marsMaterial);
                    pl[i].setRadius(10);
                    break;
                case 5:
                    pl[i].setMaterial(jupMaterial);
                    pl[i].setRadius(139.820);
                    break;
                case 6:
                    pl[i].setMaterial(saturnMaterial);
                    pl[i].setRadius(116.460);
                    break;
                case 7:
                    //TITAN
                    pl[i].setMaterial(titanMaterial);
                    pl[i].setRadius(25.747);
                    break;
                case 8:
                    pl[i].setMaterial(uranusMaterial);
                    pl[i].setRadius(50.724);
                    break;
                case 9:
                    pl[i].setMaterial(neptuneMaterial);
                    pl[i].setRadius(49.244);
                    break;
                case 10:
                    pl[i].setRadius(5);
            }

            world.getChildren().add(pl[i]);
        }

        //Create an object Sphere which takes the position of the rocket
        rock = new Sphere();
        rock.setTranslateX(rocket.getXPosition() / DRAW_CONSTANT - planet[7].getXPosition()/DRAW_CONSTANT);
        rock.setTranslateY(rocket.getYPosition() / DRAW_CONSTANT - planet[7].getYPosition()/DRAW_CONSTANT);
        rock.setTranslateZ(rocket.getZPosition() / DRAW_CONSTANT - planet[7].getZPosition()/DRAW_CONSTANT);
        rock.setRadius(0.4);
        world.getChildren().add(rock);

        //Loads the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/Landing/Chart.fxml"));

        //Button which is the bridge between the two scenes
        Button showData = new Button("Data");
        showData.setMaxSize(100, 200);
        showData.setTranslateX(300);
        showData.setTranslateY(200);


        //Camera allows the user to use a perspective view on the scene

        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(10);
        camera.setFarClip(Integer.MAX_VALUE);
        camera.translateZProperty().set(-1000);


        //Add all the components to the group
        Group group = new Group();
        group.getChildren().add(showData);
        group.getChildren().add(world);
        group.getChildren().add(showAltitude);
        group.getChildren().add(slider);
        group.getChildren().add(timeStepValue);
        group.getChildren().add(calendar);

        //Label which display the actual altitude of the probe
        //Altitude = Distance - Radius of titan

        showAltitude.setTranslateY(-170);
        showAltitude.setTranslateX(250);
        showAltitude.setFont(Font.font("Verdana", 20));
        showAltitude.setTextFill(Color.WHITE);


        Scene scene = new Scene(group, WIDTH, HEIGHT, true);
        scene.setCamera(camera);
        scene.setFill(Color.BLACK);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case SPACE:
                        freeze = true;
                        break;
                    case ENTER:
                        freeze = false;

                }

            }
        });

        //Creates a new Scene for the FXML file
        Scene sceneFXML = new Scene(root, 700, 700);

        //Sets the DATA button in action to display the FXML file

        //showData.setOnAction(e -> primaryStage.setScene(sceneFXML));

        initMouseControl(world, scene, primaryStage);


        primaryStage.setScene(scene);
        primaryStage.show();

        move();

    }


    public void move(){


        Timeline timer = new Timeline(
                new KeyFrame(Duration.millis(50), e -> {
                    if(!freeze) {
                        PID2 pidController = new PID2(rocket);
                        pidController.calculate();
                        timeStepValue.setText("Time Step :" + (int) timeStep + " s ");
                        showAltitude.setText("Altitude : " + (int) distanceKM + " m ");
                        timeStep = slider.getValue();
                        calendar.setText(date.getDate());
                        date.updateTime(timeStep);
                        euler();
                        redraw();
                        showDistance();
                    }
                }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }



    private void redraw() {

        for (int i = 0; i < pl.length; i++) {

            pl[i].setTranslateX(planet[i].getXPosition() / DRAW_CONSTANT - planet[7].getXPosition() / DRAW_CONSTANT);
            pl[i].setTranslateY(planet[i].getYPosition() / DRAW_CONSTANT - planet[7].getYPosition() / DRAW_CONSTANT);
            pl[i].setTranslateZ(planet[i].getZPosition() / DRAW_CONSTANT - planet[7].getZPosition() / DRAW_CONSTANT);


        }
        rock.setTranslateX(rocket.getXPosition() / DRAW_CONSTANT - planet[7].getXPosition() / DRAW_CONSTANT);
        rock.setTranslateY(rocket.getYPosition() / DRAW_CONSTANT - planet[7].getYPosition() / DRAW_CONSTANT);
        rock.setTranslateZ(rocket.getZPosition() / DRAW_CONSTANT - planet[7].getZPosition() / DRAW_CONSTANT);

    }

    //Creates a label that shows what is the current timeStep used
    private Label showTimeStep() {
        Label showtime = new Label();
        showtime.setFont(Font.font("Verdana", 20));
        showtime.setTextFill(Color.WHITE);
        showtime.setTranslateY(-210);
        showtime.setTranslateX(250);
        return showtime;
    }

    private Slider prepareSlider() {
        Slider slider = new Slider();
        slider.setMax(1);
        slider.setMin(timeStep);
        slider.setPrefWidth(300d);
        slider.setLayoutX(-150);
        slider.setLayoutY(200);
        slider.setShowTickLabels(true);

        slider.setTranslateZ(5);
        slider.setStyle("-fx-base: black");
        return slider;
    }

    //Creates a label that shows what day it is in the calendar
    private Label showDate() {
        Label showDay = new Label();
        showDay.setFont(Font.font("Verdana", 20));
        showDay.setTextFill(Color.WHITE);
        showDay.setTranslateX(250);
        showDay.setTranslateY(150);
        return showDay;
    }

    private void initMouseControl(Group group, Scene scene, Stage stage) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });

        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            //Get how much scroll was done in Y axis.
            double delta = event.getDeltaY();
            //Add it to the Z-axis location.
            group.translateZProperty().set(group.getTranslateZ() + delta);

        });

    }

    /**
     * Same as celestial body
     */
    public void euler(){
        for(int i = 0 ; i < planet.length ; i++){
            planet[i].calculateAcceleration(planet);
        }
        rocket.calculateAcceleration(planet);

        for(int i = 0 ; i < planet.length ; i++){
            planet[i].computeVelocityStep(timeStep);
        }

        rocket.computeVelocityStep(timeStep);

        for(int i = 0 ; i < planet.length ; i++){
            planet[i].updateLocation(timeStep);
        }

        rocket.updateLocation(timeStep);

    }


    /*
        Closed-Loop controller
     */
    public void showDistance(){
        double distance = Math.pow(rocket.getXPosition() - planet[7].getXPosition(),2) + Math.pow(rocket.getYPosition() - planet[7].getYPosition(),2) + Math.pow(rocket.getZPosition() - planet[7].getZPosition(),2);
        double distanceSquared = Math.abs(Math.sqrt(distance));
        double distanceSquaredTitan = distanceSquared - TITAN_RADIUS;
        distanceKM = distanceSquaredTitan/10;

        if (distanceKM < 0){
            freeze = true;
            System.out.println("Difference \nX: "+(rocket.getVelX()-planet[7].getVelX())+ "\tY: " + (rocket.getVelY()-planet[7].getVelY()));
            launching = true;
        }


        else if(distanceKM<1000){
            rocket.thruster(planet[7], 315000, true, timeStep);
        }
        else if(distanceKM<1250){
            rocket.thruster(planet[7], 200000, true, timeStep);
        }
        else if(distanceKM<1500){
            rocket.thruster(planet[7], 100000, true, timeStep);
        }


        //System.out.println(distanceKM + " " + rocket.getVelY());

    }

}