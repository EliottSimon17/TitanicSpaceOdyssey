
import Data.*;
import Landing.CelestialBody;
import Landing.Rocket;
import Test.NasaTest;
import javafx.animation.KeyFrame;
import javafx.application.Application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.Group;

import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;



import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.animation.Timeline;

import javafx.util.Duration;



public class Main extends Application {

    //Dimensions of the Stage
    private static final float WIDTH = 1400;
    private static final float HEIGHT = 800;

    private static final int DRAW_CONSTANT = 1000000000;
    private int testCounterOne = 1;
    private int testCounterDay = 1;
    private int testCounterYear = 1;
    private int testCounterTenYear = 1;


    Group world = new Group();
    Group MoonGhost = new Group();
    Date date = new Date(2019, 07, 15);
    public static CelestialBody[] planet = new CelestialBody[9];
    private CelestialBody centre;
    private Sphere[] pl;
    private Sphere rock;
    private Rocket rocket;

    boolean doEuler=false;
    boolean doRungeKutta=false;

    private Button eButton = new Button("Euler");
    private Button rButton = new Button("Runge Kutta");
    private Label whichEquation = new Label("Which numerical equation?");


    public double timeStep = 1;



    //Starting point  x and y
    private double anchorX, anchorY;
    //Angle of x and y
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    //We will update these after drag. Using JavaFX property to bind with object
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    Label timeStepValue = showTimeStep();
    Label calendar = showDate();

    Slider slider = prepareSlider();

    NumericalEquations ode;


    public void start(Stage primaryStage) {

        //-------------------------------------------------------------------------

        // First Stage

        //-------------------------------------------------------------------------

        eButton.setPrefSize(150, 50);
        whichEquation.setFont(new Font(35));

        VBox v = new VBox();

        HBox labelBox = new HBox();
        labelBox.getChildren().add(whichEquation);
        labelBox.setAlignment(Pos.CENTER);

        HBox equationChooser = new HBox();
        equationChooser.setAlignment(Pos.CENTER);
        equationChooser.setSpacing(50);
        equationChooser.getChildren().addAll(eButton, rButton);

        v.setAlignment(Pos.CENTER);
        v.setSpacing(20);
        v.getChildren().addAll(labelBox, equationChooser);

        Scene chooseEquation = new Scene(v, 500, 250);
        chooseEquation.getStylesheets().add("styleChoser.css");

        //-------------------------------------------------------------------------

        // Second Stage

        //-------------------------------------------------------------------------

        /** Instantiate all the planets in an array with their real data
         * We start the simulation the 15th of July 2019
         * @see {https://ssd.jpl.nasa.gov/horizons.cgi#top}
         */
        planet[0] = new CelestialBody("Sun", RealDistance.getSunDistance(), RealVelocities.getSunVelocity(), RealMasses.SUN_MASS);

        planet[1] = new CelestialBody("Mercury", RealDistance.getMercuryDistance(), RealVelocities.getMercuryVelocity(), RealMasses.MERCURY_MASS);

        planet[2] = new CelestialBody("Venus", RealDistance.getVenusDistance(), RealVelocities.getVenusVelocity(), RealMasses.VENUS_MASS);

        planet[3] = new CelestialBody("Earth", RealDistance.getEarthDistance(), RealVelocities.getEarthVelocity(), RealMasses.EARTH_MASS);

        planet[4] = new CelestialBody("Mars", RealDistance.getMarsDistance(),  RealVelocities.getMarsVelocity(), RealMasses.MARS_MASS);

        planet[5] = new CelestialBody("Jupiter", RealDistance.getJupiterDistance(), RealVelocities.getJupiterVelocity(), RealMasses.JUPITER_MASS);

        planet[6] = new CelestialBody("Saturn", RealDistance.getSaturnDistance(), RealVelocities.getSaturnVelocity(), RealMasses.SATURNE_MASS);

        planet[7] = new CelestialBody("Uranus", RealDistance.getUranusDistance(), RealVelocities.getUranusVelocity(), RealMasses.URANUS_MASS);

        planet[8] = new CelestialBody("Neptune", RealDistance.getNeptuneDistance(), RealVelocities.getNeptuneVelocity(), RealMasses.NEPTUNE_MASS);

        rocket = new Rocket("Rocket", RealDistance.getRocketDistance(), RealVelocities.getRocketLaunchVel(), 2.75 * Math.pow(10,3), new Point(0, 0, 0));

        centre = planet[0];

        ode = new NumericalEquations(timeStep);


        /**Use a method to wrap the spheres with an image
         * @see {http://planetpixelemporium.com/sun.html} for the jpg files
        */
        PhongMaterial mercuryMaterial = new PhongMaterial();
        mercuryMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("Landing/images/mercury.jpeg")));

        PhongMaterial sunMaterial = new PhongMaterial();
        sunMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("Landing/images/sun.jpeg")));

        PhongMaterial venusMaterial = new PhongMaterial();
        venusMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("Landing/images/venusmap.jpeg")));

        PhongMaterial earthMaterial = new PhongMaterial();
        earthMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("Landing/images/earth/earth.normal.jpeg")));

        PhongMaterial marsMaterial = new PhongMaterial();
        marsMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("Landing/images/MarsMap.jpeg")));

        PhongMaterial jupMaterial = new PhongMaterial();
        jupMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("Landing/images/jupitermap.jpeg")));

        PhongMaterial saturnMaterial = new PhongMaterial();
        saturnMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("Landing/images/saturnmap.jpeg")));

        PhongMaterial uranusMaterial = new PhongMaterial();
        uranusMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("Landing/images/uranusmap.jpeg")));

        PhongMaterial neptuneMaterial = new PhongMaterial();
        neptuneMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("Landing/images/neptunemap.jpeg")));

        pl = new Sphere[planet.length];

        /** this for loop iterates through all of the planets
         * and create a sphere for each of them at their initial positions
         * these positions are unique but will be changed in the animation method
         */
        for (int i = 0; i < pl.length; i++) {

            pl[i] = new Sphere();

            switch (i) {
                case 0:
                    pl[i].setMaterial(sunMaterial);
                    pl[i].setRadius(15);
                    break;
                case 1:
                    pl[i].setMaterial(mercuryMaterial);
                    pl[i].setRadius(4);
                    break;
                case 2:
                    pl[i].setMaterial(venusMaterial);
                    pl[i].setRadius(6);
                    break;
                case 3:
                    pl[i].setMaterial(earthMaterial);
                    pl[i].setRadius(4);
                    break;
                case 4:
                    pl[i].setMaterial(marsMaterial);
                    pl[i].setRadius(10);
                    break;
                case 5:
                    pl[i].setMaterial(jupMaterial);
                    pl[i].setRadius(10);
                    break;
                case 6:
                    pl[i].setMaterial(saturnMaterial);
                    pl[i].setRadius(10);
                    break;
                case 7:
                    //TITAN
                    pl[i].setMaterial(uranusMaterial);
                    pl[i].setRadius(0.1);
                    break;
                case 8:
                    pl[i].setMaterial(uranusMaterial);
                    pl[i].setRadius(10);
                    break;
                case 9:
                    pl[i].setMaterial(neptuneMaterial);
                    pl[i].setRadius(10);
                    break;
                case 10:
                    pl[i].setRadius(5);
            }

            world.getChildren().add(pl[i]);
        }

        rock = new Sphere();
        rock.setRadius(1);
        world.getChildren().add(rock);

        /**
         * Camera Class is a java class which allows the user to create 3d interface
         * We set the far clip to Integer.Max_Value so we can zoom out a lot
         */
        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(10);
        camera.setFarClip(Integer.MAX_VALUE);
        camera.translateZProperty().set(-1000);

        Button sunCentre = new Button("Sun");
        sunCentre.setMaxSize(100, 200);
        sunCentre.setTranslateX(300);
        sunCentre.setTranslateY(-50);
        sunCentre.setPrefWidth(75);

        Button earthCentre = new Button("Earth");
        earthCentre.setMaxSize(100, 200);
        earthCentre.setTranslateX(300);
        earthCentre.setTranslateY(-150);
        earthCentre.setPrefWidth(75);

        Button saturnCentre = new Button("Saturn");
        saturnCentre.setMaxSize(100, 200);
        saturnCentre.setTranslateX(300);
        saturnCentre.setTranslateY(-100);
        saturnCentre.setPrefWidth(75);

        ButtonBase rocketCentre = new Button("Rocket");
        rocketCentre.setMaxSize(100, 200);
        rocketCentre.setTranslateX(300);
        rocketCentre.setTranslateY(-100);
        rocketCentre.setPrefWidth(75);



        /**
         * root is the pane which contains all of the elements of the simulation
         */
        Group root = new Group();
        root.getChildren().addAll(world, slider, timeStepValue, calendar, earthCentre, saturnCentre, rocketCentre, sunCentre);



        Scene scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setCamera(camera);
        scene.setFill(Color.BLACK);

        initMouseControl(world, scene, primaryStage);


        eButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                primaryStage.setScene(scene);
                doEuler = true;
            }
        });

        rButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                primaryStage.setScene(scene);
                doRungeKutta = true;
            }
        });

        earthCentre.setOnAction(e -> centre = planet[3]);

        sunCentre.setOnAction(e -> centre = planet[0]);

        saturnCentre.setOnAction(e -> centre = planet[6]);

        rocketCentre.setOnAction(e -> centre = rocket);


        primaryStage.setTitle("Solar System");
        primaryStage.setScene(chooseEquation);
        primaryStage.show();


        prepareAnimation();

    }

    public void prepareAnimation() {

        /**TimeLine is an object which allow to user to repeat
         * an action as many time as he wants.
         * The method Keyframe specify the duration of each cycle
         * We iterate through all the planets, update their positions first and then re draw them
         * @timeStep shows how long each loop represents in real life
         */

        //System.out.println("Time Step : " + timeStep);


        Timeline timer = new Timeline(
                new KeyFrame(Duration.millis(0.1), e -> {
                    if(doEuler || doRungeKutta) {
                        redraw();
                        time();
                        if(doEuler){ euler(); }
                        else{ ode.rungeKutta(this.planet);
                        test(); }
                        seeDate();
                    }

                }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();


    }

    // Provides a test case for the positions of the planets
    // on the 17th of August
    // Compares our values to NASA's data

    public void testOneMonth() {

        double abs_error = Math.abs(Math.abs(NasaTest.earthAfterOneMonth.x)-planet[3].getXPosition());
        System.out.println(" One Month: " + "\n" + " absolute error :" + abs_error);
        double rel_error = abs_error/NasaTest.earthAfterOneMonth.x;
        System.out.println(" percentage error : " + rel_error*100);

    }

    public void testOneDay(){

        double abs_error = Math.abs(Math.abs(NasaTest.earthAfterOneDay.x)-Math.abs(planet[3].getXPosition()));
        System.out.println(" One Day: " + "\n" + " absolute error :" + abs_error);
        double rel_error = abs_error/Math.abs(NasaTest.earthAfterOneDay.x);
        System.out.println(" percentage error : " + rel_error*100);

    }

    public void testOneYear(){

        double abs_error = Math.abs(NasaTest.earthAfterOneYear.x-planet[3].getXPosition());
        System.out.println(" One Year: " + "\n" + " absolute error :" + abs_error);
        double rel_error = abs_error/Math.abs(NasaTest.earthAfterOneYear.x);
        System.out.println(" absolute error : " + rel_error*100);

    }

    public void testTenYear(){

        double abs_error = Math.abs(NasaTest.earthAfterTenYears.x-planet[3].getXPosition());
        System.out.println(" Ten Year: " + "\n" + " absolute error :" + abs_error);
        double rel_error = abs_error/NasaTest.earthAfterTenYears.x;
        System.out.println(" absolute error : " + rel_error*100);


    }


    //Slider for changing the time step of the simulation
    private Slider prepareSlider() {
        Slider slider = new Slider();
        slider.setMax(50000);
        slider.setMin(timeStep);
        slider.setPrefWidth(300d);
        slider.setLayoutX(-150);
        slider.setLayoutY(200);
        slider.setShowTickLabels(true);

        slider.setTranslateZ(5);
        slider.setStyle("-fx-base: black");
        return slider;
    }

    //Creates a label that shows what is the current timeStep used
    private Label showTimeStep() {
        Label showtime = new Label();
        showtime.setFont(Font.font("Verdana", 20));
        showtime.setTextFill(Color.WHITE);
        showtime.setTranslateY(-210);
        showtime.setTranslateX(280);
        return showtime;
    }

    //Creates a label that shows what day it is in the calendar
    private Label showDate() {
        Label showDay = new Label();
        showDay.setFont(Font.font("Verdana", 20));
        showDay.setTextFill(Color.WHITE);
        showDay.setTranslateX(300);
        showDay.setTranslateY(150);
        return showDay;
    }

    /** Moves the sphere objects as their SCALED positions
     *  It is possible to change the body that is the center by subtracting
     *  all of the positions with its position
     */
    private void redraw() {

        for (int i = 0; i < pl.length; i++) {
            pl[i].setTranslateX(planet[i].getXPosition() / DRAW_CONSTANT - centre.getXPosition() / DRAW_CONSTANT);
            pl[i].setTranslateY(planet[i].getYPosition() / DRAW_CONSTANT - centre.getYPosition() / DRAW_CONSTANT);
            pl[i].setTranslateZ(planet[i].getZPosition() / DRAW_CONSTANT - centre.getZPosition() / DRAW_CONSTANT);

        }

    }

    /**Method which allow the user to either control the camera upon the scene and the stage
     * or zoom
     * Copied from @see {https://www.youtube.com/watch?v=yinIKzg7duc&t=306s}
     */
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

            double delta = event.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() + delta);

        });
    }

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

    public void time(){
        timeStep = slider.getValue();
        timeStepValue.setText("Time Step :" + (int) timeStep + " s ");

    }
    public void seeDate(){
        date.updateTime(timeStep);
        calendar.setText(date.getDate());
    }

    public void test(){
        if(date.getDate().equals("16/7/2019") && testCounterDay == 1){ testOneDay(); testCounterDay++;}
        if(date.getDate().equals("17/8/2019") && testCounterOne == 1){ testOneMonth(); testCounterOne++;}
        if(date.getDate().equals("15/7/2020") && testCounterYear == 1){ testOneYear(); testCounterYear++;}
        if(date.getDate().equals("17/8/2029") && testCounterTenYear == 1){ testTenYear(); testCounterTenYear++;}
    }

}