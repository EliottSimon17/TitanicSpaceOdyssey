import Data.Point;
import Landing.CelestialBody;

public class Planet {

    final static double GRAVITY_CONSTANT  = 6.67e-11;

    /**
     *
     * @param bodies : array containing all of the celestial body targeted
     * @param position : Array containing the positions of the Celestial Body each in a vector
     * @return the acceleration of each body after an instance of time
     */
    public Point[] integrateAcc(CelestialBody[] bodies, Point[] position){
        double x, y, z;          //distance between particles along axes
        double distance;                   //resultant distance between particles
        double force;

        Point[] temporaryAcceleration = new Point[bodies.length];
        //fx = -GM/d^2 * dx/d Newton's law
        //Similar to Euler
        for(int i = 0; i < bodies.length; i ++){

            CelestialBody first = bodies[i];

            temporaryAcceleration[i] = new Point(0,0,0);
            for (int j = 0; j < bodies.length; j++){
                CelestialBody second = bodies[j];
                if(first != second){
                    x = position[j].getX() - position[i].getX();
                    y = position[j].getY() - position[i].getY();
                    z = position[j].getZ() - position[i].getZ();
                    distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z ,2));
                    force = GRAVITY_CONSTANT * second.getMass() / Math.pow(distance, 2);
                    temporaryAcceleration[i].addX(x / distance * force);
                    temporaryAcceleration[i].addY(y / distance * force);
                    temporaryAcceleration[i].addZ(z / distance * force);
                }
            }
        }
        return temporaryAcceleration;
    }

    /**
     * @param velocity : Array containing the velocities of the Celestial Body each in a vector
     * @param point : Array containing all the values which will be integrated
     * @param timestep : time step chosen for the simulation in second
     * @return new velocities in a vector after performing what is similar to euler
     */
    public Point[] integrateVel(Point[] velocity, Point[] point, double timestep){
        Point[] temporaryVelocity = new Point[velocity.length];
        for(int i = 0; i< velocity.length;i++) {
            temporaryVelocity[i] = new Point(velocity[i].getX(),velocity[i].getY(),velocity[i].getZ());
            temporaryVelocity[i].addX(point[i].getX() * timestep);
            temporaryVelocity[i].addY(point[i].getY() * timestep);
            temporaryVelocity[i].addZ(point[i].getZ() * timestep);
        }
        return temporaryVelocity;
    }

    /**
     *
     * @param position : Array containing the position of the Celestial Body each in a vector
     * @param point : Array containing all the values which will be integrated
     * @param timestep : time step chosen for the simulation in second
     * @return new positions in a vector after performing what is similar to euler
     */
    public Point[] integratePosition(Point[] position, Point[] point, double timestep){
        Point[] temporaryPosition = new Point[position.length];
        for(int i = 0; i<position.length;i++) {
            temporaryPosition[i] = new Point(position[i].getX(),position[i].getY(),position[i].getZ());
            temporaryPosition[i].addX( point[i].getX()*timestep);
            temporaryPosition[i].addY( point[i].getY()*timestep);
            temporaryPosition[i].addZ( point[i].getZ()*timestep);
        }
        return temporaryPosition;
    }

    //Sets a new velocity after the iteration using a clone of the Point
    public void setVel(CelestialBody[] body, Point[] speed) {
        for(int i = 0; i< body.length;i++) {
            body[i].velocity = speed[i].samePoint();
        }
    }

    //Sets a new position after the iteration using a clone of the Point
    public void setPos(CelestialBody[] body, Point[] loc) {
        for(int i = 0; i< body.length;i++) {
            body[i].location = loc[i].samePoint();
        }
    }
}
