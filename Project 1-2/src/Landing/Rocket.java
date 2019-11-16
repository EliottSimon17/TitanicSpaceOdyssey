package Landing;

import Data.Point;

public class Rocket extends CelestialBody {

    private Point force;
    private double accelerateFor=0;

    /**
     * @param name the name of the rocket
     * @param location the vector in 3-dimension which represents the position of the rocket
     * @param velocity the vector in 3-dimension which represents the velocity of the rocket
     * @param bodyMass the mass of the rocket
     * @param force
     */
    public Rocket(String name, Point location, Point velocity,
                  double bodyMass, Point force){
        super(name, location, velocity, bodyMass);
        this.force = force;

    }

    //-------------------------------------------------------------------------

    //Accelerations

    //-------------------------------------------------------------------------

    public double getAccX(){
        return acceleration.getX()+force.getX()/mass;
    }
    public double getAccY(){
        return acceleration.getY()+force.getY()/mass;
    }
    public double getAccZ(){
        return acceleration.getZ()+force.getZ()/mass;
    }

    public void setAcceleration(Point acc){
        this.acceleration=acc;
    }

    public void setForce(Point newForce){
        this.force = newForce;
    }

    /**
     *
     * @param planet Array of the celestial bodies in the solar system
     * @param strength Strength of the force of the thrust applied
     * @param away boolean determining direction the force is applied
     * @param timeStep is the time step chosen
     */
    public void thruster(CelestialBody planet, double strength, boolean away, double timeStep) {
        accelerateFor = 500;

        double r = location.getDistance(planet.getLocation());

        double appliedForce = timeStep*strength/r;

        if(away)
            appliedForce=-appliedForce;


        setForce(new Point(appliedForce*(planet.getXPosition() - getXPosition()),appliedForce*(planet.getYPosition() - getYPosition()), appliedForce*(planet.getZPosition() - getZPosition())));
    }

    public void computeVelocityStep(double timeStep){
        accelerateFor-= timeStep;
        changeVelocity(new Point(getAccX() * timeStep,getAccY() * timeStep, getAccZ() * timeStep ));

        if(accelerateFor<=0)
            setForce(new Point(0,0,0));

        //Rocket will keep on accelerating for more seconds than expected if the timeStep is high
    }

}