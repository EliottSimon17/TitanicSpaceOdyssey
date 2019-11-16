package Landing;

import Data.Point;

public class CelestialBody {


    private final double GRAVITY_CONSTANT = 6.673 * Math.pow(10, -11);

    private double force = 0;

    public Point location;
    public Point velocity;
    Point acceleration = new Point(0, 0, 0);
    double mass;
    double accX = 0;
    double accY = 0;
    double accZ = 0;
    String name;
    private Point kuttaPosition = null;

    /**
     * @param bodyMass : mass in kilogram of each body
     * @param location : vector in 3 dimensions that represents the coordinate of each body
     * @param velocity : vector in 3 dimensions that represents the velocity of each body
     * @param name : name of each body
     *
     */
    public CelestialBody(String name, Point location, Point velocity,
                         double bodyMass) {
        this.location = location;
        this.velocity = velocity;
        mass = bodyMass;
        this.name = name;

    }


    public double getMass() {
        return mass;
    }

    //-------------------------------------------------------------------------

    // VELOCITIES

    //-------------------------------------------------------------------------

    public void setVelX(double d) { velocity.setX(d); }

    public void setVelY(double d) {
        velocity.setY(d);
    }

    public void setVelZ(double d) {
        velocity.setZ(d);
    }

    public double getVelX() {
        return velocity.getX();
    }

    public double getVelY() {
        return velocity.getY();
    }

    public double getVelZ() {
        return velocity.getZ();
    }

    //-------------------------------------------------------------------------

    //ACCELERATIONS

    //-------------------------------------------------------------------------


    public void setAccX(double d) {
        acceleration.setX(d);
    }

    public void setAccY(double d) {
        acceleration.setY(d);
    }

    public void setAccZ(double d) {
        acceleration.setZ(d);
    }

    public double getAccX() {
        return acceleration.getX();
    }

    public double getAccY() {
        return acceleration.getY();
    }

    public double getAccZ() {
        return acceleration.getZ();
    }

    //-------------------------------------------------------------------------


    public void changeVelocity(Point vel) {

        velocity.add(vel);
    }

    public void changePosition(Point point) {
        location.add(point);
    }


    public Point getLocation() {
        return location;

    }

    public Point getVelocity() {
        return velocity;
    }


    //-------------------------------------------------------------------------

    //LOCATIONS

    //-------------------------------------------------------------------------

    public void setXPosition(double x) {
        location.setX(x);
    }

    public void setYPosition(double y) {
        location.setY(y);
    }

    public void setZPosition(double z) {
        location.setZ(z);
    }

    public double getXPosition() {
        return location.getX();
    }

    public double getYPosition() {
        return location.getY();
    }

    public double getZPosition() {
        return location.getZ();
    }


    public String getName() {
        return this.name;
    }

    /** Computes the acceleration of each body using Newton's law of gravity
     *
     * By applying this fundamental physical formula we compute the attraction
     * between each planet
     * We then increment the acceleration by multiplying this force with the
     * variation of position
     * This is similar to Euler's differential equation applied to kinematic equations
     *
     * @see {https://en.wikipedia.org/wiki/Newton%27s_law_of_universal_gravitation}
     * @param bodies represents an array containing each of the bodies in the solar system
     *
     */
    public void calculateAcceleration(CelestialBody[] bodies) {

        //Point acceleration = new Point();
        double accX = 0;
        double accY = 0;
        double accZ = 0;


        for (int i = 0; i < bodies.length; i++) {
            if (bodies[i] != this) {
                double r = (Math.pow((bodies[i].getXPosition() - getXPosition()), 2) + Math.pow((bodies[i].getYPosition() - getYPosition()), 2) + Math.pow((bodies[i].getZPosition() - getZPosition()), 2));

                r = Math.sqrt(r);

                force = GRAVITY_CONSTANT * bodies[i].getMass() / (Math.pow(r, 3));

                accX += (force * (bodies[i].getXPosition() - getXPosition()));
                accY += (force * (bodies[i].getYPosition() - getYPosition()));
                accZ += (force * (bodies[i].getZPosition() - getZPosition()));

            }
        }
        setAccX(accX);
        setAccY(accY);
        setAccZ(accZ);

    }

    /** Kinematic equations demonstrate that dx = dv * dt
     * dx being the position difference, dv the velocity difference and dt the timestep
     * @param timeStep the time step chosen for each update
     * This method allows to update the position of each celestial body
    */
     public void updateLocation(double timeStep) {

        changePosition(new Point(getVelX() * timeStep, getVelY() * timeStep, getVelZ() * timeStep));
    }

    /** Kinematic equations demonstrate that dx = a * dt
     * dv being the position difference, a the velocity difference and dt the timestep
     * @param timeStep the time step chosen for each update
     * This method allows to update the velocity of each celestial body
     */
    public void  computeVelocityStep(double timeStep) {
        changeVelocity(new Point(getAccX() * timeStep, getAccY() * timeStep, getAccZ() * timeStep));

    }

    /** This function is another way of computing the planetary motion
     * by using a 4th-Order Runge-Kutta differential method
     * This gets a more approximate value than Euler by subdividing each step with
     * 4 coefficients and then taking the "mean" value of all of them
     *
     *  @see {http://spiff.rit.edu/richmond/nbody/OrbitRungeKutta4.pdf}
     * @param bodies array containing all of the celestial bodies
     * @param dt time step
     */
    /*public void kuttaAcc(CelestialBody[] bodies, double dt){
        Point force = new Point();

        Point k1;
        Point k2;
        Point k3;
        Point k4;

        Point stepLocation;
        Point stepVelocity;

        for (int i = 0; i < bodies.length; i++) {
            CelestialBody b = bodies[i];
            if (b.equals(this))
                continue;

            Point dist = this.getLocation().subtract(b.getLocation());
            double mag = -GRAVITY_CONSTANT * b.getMass() / Math.pow(dist.magnitude(), 3);

            k1 = dist.scale(mag);

            stepVelocity = rk4Step(this.getVelocity(), k1, dt * 0.5);
            stepLocation = rk4Step(this.getLocation(), stepVelocity, dt * 0.5);

            dist = stepLocation.subtract(b.getLocation());
            mag = -GRAVITY_CONSTANT * b.getMass() / Math.pow(dist.magnitude(), 3);

            k2 = dist.scale(mag);

            stepVelocity = rk4Step(this.getVelocity(), k2, dt * 0.5);
            stepLocation = rk4Step(this.getLocation(), stepVelocity, dt * 0.5);

            dist = stepLocation.subtract(b.getLocation());
            mag = -GRAVITY_CONSTANT * b.getMass() / Math.pow(dist.magnitude(), 3);

            k3 = dist.scale(mag);

            stepVelocity = rk4Step(this.getVelocity(), k3, dt);
            stepLocation = rk4Step(this.getLocation(), stepVelocity, dt);

            dist = stepLocation.subtract(b.getLocation());
            mag = -GRAVITY_CONSTANT * b.getMass() / Math.pow(dist.magnitude(), 3);

            k4 = dist.scale(mag);

            Point stepDelta = new Point(k1);

            stepDelta.add(k2.scale(2));
            stepDelta.add(k3.scale(2));
            stepDelta.add(k4);

            force.add(stepDelta.scale(1.0 / 6.0));
        }

        this.setAccX(force.getX());
        this.setAccY(force.getY());
        this.setAccZ(force.getZ());
        }
    */
    /** This is similar to Euler's integration and is a short way of writing
     * the two steps of differentiation at the same time
     * @param pos Vector which represents the position in 3d of the body
     * @param vel Vector which represents the velocity in 3d of the body
     * @param dt time step
     * @return new Point that is the result of the Euler integration
     */
    private Point rk4Step(Point pos, Point vel, double dt) {
        Point res = new Point(pos);
        res.add(vel.scale(dt));

        return res;
    }
    /*
    public void jpp(CelestialBody[] body){
        Point[] pk1 = new Point[body.length];
        Point[] vk1 = new Point[body.length];
        Point k1 = new Point(0,0,0);

        Point[] vk2 = new Point[body.length];

        Point[] poskutta = new Point[body.length];
        Point[] velKutta = new Point[body.length];
        Point[] iteratePos = new Point[body.length];
        Point[] iterateVel = new Point[body.length];

        for(int i = 0; i< body.length; i++){

            poskutta[i] = new Point(0,0,0);
            velKutta[i] = new Point(0,0,0);
            iteratePos[i] = body[i].getLocation();
            iterateVel[i] = body[i].getVelocity();
        }

        pk1 = iteratePos;
        vk1 = iterateVel;
        k1 = eulerAcc(body, pk1);
    }

    public Point eulerAcc(CelestialBody[] bodies, Point[] point){

        //Point acceleration = new Point();
        double accX = 0;
        double accY = 0;
        double accZ = 0;


        for (int i = 0; i < bodies.length; i++) {
            if (bodies[i] != this) {
 Point dist = this.getLocation().subtract(b.getLocation());
                r = Math.sqrt(r);

                force = GRAVITY_CONSTANT * bodies[i].getMass() / (Math.pow(r, 3));

                accX += (force * (bodies[i].getXPosition() - getXPosition()));
                accY += (force * (bodies[i].getYPosition() - getYPosition()));
                accZ += (force * (bodies[i].getZPosition() - getZPosition()));

            }
        }
        return acceleration;
    }

       */

    public Point integrate(CelestialBody body,  double timeStep){

        Point firstObject = new Point(this.getXPosition(), this.getYPosition(), this.getYPosition());
        Point secondObject = new Point(body.getXPosition(), body.getYPosition(), body.getZPosition());
        firstObject.subtract(secondObject).magnitude().scale(-1);
        firstObject.scale(GRAVITY_CONSTANT * body.getMass() *timeStep);
        double r = (Math.pow((body.getXPosition() - getXPosition()), 2) + Math.pow((body.getYPosition() - getYPosition()), 2) + Math.pow((body.getZPosition() - getZPosition()), 2));
        r = Math.sqrt(r);
        firstObject.div(r*r*r);
        return firstObject;
    }
    public void rungeFourthOrder(CelestialBody body, double timeStep){

        Point ak1 = new Point(velocity);
        kuttaVelocity(body, timeStep/2);

        Point ak2 = new Point(velocity);
        ak2.add(ak1.scale(0.5));

        Point ak3 = new Point(velocity);
        ak3.add(ak2.scale(0.5));
        kuttaVelocity(body, timeStep/2);

        Point ak4 = new Point(velocity);
        ak4.add(ak3);

        Point kutta = new Point(0,0,0);
        kutta.add(ak1);
        kutta.add(ak2.scale(2));
        kutta.add(ak3.scale(2));
        kutta.add(ak4);
        kutta.scale(1/6);

        kuttaPosition = new Point(kutta);


    }
    public void rungeUpdateLocation()

    {
        location.add(kuttaPosition);
    }
    public void kuttaVelocity(CelestialBody body, double timeStep){
        Point vk = integrate(body, timeStep);
        velocity.add(vk);
    }

}


