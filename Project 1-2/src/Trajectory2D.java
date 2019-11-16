import Data.RealDistance;
import Data.RealMasses;
import Data.RealVelocities;
import Landing.CelestialBody;
import Landing.GUILanding;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import static Data.RealMasses.EARTH_MASS;

public class Trajectory2D extends Application {

    private static final float CANVAS_WIDTH = 1400;
    private static final float CANVAS_HEIGHT = 800;

    private static final int DRAW_CONSTANT = 1000000000;
    private int changerCount = 1;

    final static double GRAVITY_CONSTANT = 6.67e-11;

    private CelestialBody[] planet = new CelestialBody[10];
    private CelestialBody centre;

    Timeline timer;

    private NumericalEquations ode;


    private double timeStep;

    private Circle[] planet2D;

    //Starting point  x and y
    private double anchorX, anchorY;
    //Angle of x and y
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    //We will update these after drag. Using JavaFX property to bind with object
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    @Override
    public void start(Stage primaryStage){


        Group solarSystem = new Group();
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


        planet[9] = new CelestialBody("Rocket", RealDistance.getRocketDistance(), RealVelocities.getRocketVelocity(), 2.75 * Math.pow(10, 3));

        centre = planet[9];

        planet2D = new Circle[planet.length];

        for (int i = 0; i < planet2D.length; i++) {

            planet2D[i] = new Circle();

            switch (i) {
                case 0:
                    planet2D[i].setFill(Color.YELLOW);
                    planet2D[i].setRadius(15);
                    break;
                case 1:
                    planet2D[i].setFill(Color.RED);
                    planet2D[i].setRadius(4);
                    break;
                case 2:
                    planet2D[i].setFill(Color.ORANGE);
                    planet2D[i].setRadius(6);
                    break;
                case 3:
                    planet2D[i].setFill(Color.BLUE);
                    planet2D[i].setRadius(4);
                    break;
                case 4:
                    planet2D[i].setFill(Color.RED);
                    planet2D[i].setRadius(10);
                    break;
                case 5:
                    planet2D[i].setFill(Color.BLUE);
                    planet2D[i].setRadius(10);
                    break;
                case 6:
                    planet2D[i].setFill(Color.YELLOW);
                    planet2D[i].setRadius(10);
                    break;
                case 7:
                    //TITAN
                    planet2D[i].setFill(Color.WHITE);
                    planet2D[i].setRadius(0.1);
                    break;
                case 8:
                    planet2D[i].setFill(Color.BLUE);
                    planet2D[i].setRadius(10);
                    break;
                case 9:
                    planet2D[i].setFill(Color.WHITE);
                    planet2D[i].setRadius(3);
                    break;
                case 10:
                    planet2D[i].setRadius(5);
            }
            solarSystem.getChildren().add(planet2D[i]);
        }

        /*
        rock = new Circle();
        rock.setRadius(5);
        rock.setFill(Color.BLUEVIOLET);
        solarSystem.getChildren().add(rock);
        */

        planet[9].setVelX(0);
        planet[9].setVelY(-16000);

        timeStep = 2160;

        ode = new NumericalEquations(timeStep);

        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(10);
        camera.setFarClip(Integer.MAX_VALUE);
        camera.translateZProperty().set(-1000);


        Scene scene = new Scene(solarSystem, CANVAS_WIDTH, CANVAS_HEIGHT);
        scene.setCamera(camera);
        scene.setFill(Color.BLACK);

        initMouseControl(solarSystem, scene, primaryStage);


        primaryStage.setTitle("Trajectory to Titan");
        primaryStage.setScene(scene);
        primaryStage.show();

        prepareAnimation();

    }

    public void prepareAnimation() {
         timer = new Timeline(
                new KeyFrame(Duration.millis(1), e -> {
                    redraw();
                    planet[9].setVelY(hohmann() * (-1));
                    planet[9].setVelX(hohmann() - 3200);
                    ode.rungeKutta(this.planet);
                    changeAnimation();
                }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void redraw() {
        for (int i = 0; i < planet2D.length; i++) {
            planet2D[i].setTranslateX(planet[i].getXPosition() / DRAW_CONSTANT - centre.getXPosition() / DRAW_CONSTANT);
            planet2D[i].setTranslateY(planet[i].getYPosition() / DRAW_CONSTANT - centre.getYPosition() / DRAW_CONSTANT);

        }

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

            double delta = event.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() + delta);

        });
    }

    /** We use Hohmann to compute the velocity needed to reach Saturn's Orbit
     *  We need to adjust this velocity because we also use Z coordinate for the positions
     *  But we are not using a 3 dimension Graphical Interface so there is a small adjustment which has to be done
     *  All these formula's can be found here
     *  mu is the standard gravitational parameter of the earth
     * @see {https://en.wikipedia.org/wiki/Hohmann_transfer_orbit} for the formulas
     * @return delta Velocity which is the speed needed to reach saturn's orbit
     */
    public double hohmann() {
        double rEarth = 149598023000.0;

        double rSaturn = 1433530000000.0;

        double muEarth = GRAVITY_CONSTANT * EARTH_MASS;

        double majorAxis = .5 * (rEarth + rSaturn);

        double deltaV = Math.sqrt(muEarth * ((2 / rEarth) - (1 / majorAxis))) - Math.sqrt(muEarth / rEarth);

        return (deltaV * 1000);
    }

    //Switches scene
    public void changeAnimation() {
        if (changerCount == 1) {
            double distance = Math.sqrt(Math.pow(planet[9].getXPosition() - planet[6].getXPosition(), 2) + Math.pow(planet[9].getYPosition() - planet[6].getYPosition(), 2) + Math.pow(planet[9].getZPosition() - planet[6].getZPosition(), 2));
            if (distance < planet2D[6].getRadius()*DRAW_CONSTANT) {

                changerCount++;
                GUILanding landing = new GUILanding();
                Stage showLanding = new Stage();
                try {
                    landing.start(showLanding);
                    timer.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
