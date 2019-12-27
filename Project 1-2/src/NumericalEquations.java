import Data.Point;
import Landing.CelestialBody;

public class NumericalEquations {

    double timeStep;
    Planet body;

    public NumericalEquations(double timeStep){
        this.timeStep = timeStep;
        body = new Planet();
    }

    public void rungeKutta(CelestialBody[] bodies){
        Point[] p1, p2, p3, p4;
        Point[] pk = new Point[bodies.length];

        Point[] v1, v2, v3, v4;
        Point[] vk = new Point[bodies.length];

        Point[] k1, k2, k3, k4;

        Point[] position = new Point[bodies.length];
        Point[] velocity = new Point[bodies.length];

        for(int i = 0 ; i < bodies.length; i++){
            pk[i] = new Point(0,0,0);
            vk[i] = new Point(0,0,0);
            position[i] = bodies[i].location;
            velocity[i] = bodies[i].velocity;
        }


        v1 = velocity;
        p1 = position;
        k1 = body.integrateAcc(bodies, p1);

        v2 = body.integrateVel(velocity, k1, timeStep *.5);
        p2 = body.integratePosition(position, v1, timeStep*.5);
        k2 = body.integrateAcc(bodies, p2);

        v3 = body.integrateVel(velocity, k2, timeStep*.5);
        p3 = body.integratePosition(position, v2, timeStep*.5);
        k3 = body.integrateAcc(bodies, p3);

        v4 = body.integrateVel(velocity, k3, timeStep);
        p4 = body.integratePosition(position, v3, timeStep);
        k4 = body.integrateAcc(bodies, p4);

        for(int i = 0; i < bodies.length; i++) {
            vk[i].x = velocity[i].x + (timeStep / 6) * (k1[i].x + 2 * k2[i].x + 2 * k3[i].x + k4[i].x);
            vk[i].y = velocity[i].y + (timeStep / 6) * (k1[i].y + 2 * k2[i].y + 2 * k3[i].y + k4[i].y);
            vk[i].z = velocity[i].z + (timeStep / 6) * (k1[i].z + 2 * k2[i].z + 2 * k3[i].z + k4[i].z);
        }
        for(int i = 0 ; i < bodies.length ; i++){
            pk[i].x = position[i].x + (timeStep/6) * (v1[i].x + 2 * v2[i].x + 2 * v3[i].x + v4[i].x);
            pk[i].y = position[i].y + (timeStep/6) * (v1[i].y + 2 * v2[i].y + 2 * v3[i].y + v4[i].y);
            pk[i].z = position[i].z + (timeStep/6) * (v1[i].z + 2 * v2[i].z + 2 * v3[i].z + v4[i].z);
        }

        body.setVel(bodies, vk);
        body.setPos(bodies, pk);

    }

    public void leapfrog(CelestialBody[] bodies){
        Point[] pos = new Point[bodies.length];
        Point[] vel = new Point[bodies.length];
        for(int i  = 0 ; i < bodies.length ; i ++){
            pos[i] = bodies[i].location.samePoint();
            vel[i] = bodies[i].velocity.samePoint();
        }
        Point[] acc = body.integrateAcc(bodies, pos);
        Point[] vClone = vel.clone();

        for(int i = 0 ; i < bodies.length ; i ++){
            vel[i] = vel[i].returnAdded(acc[i].samePoint().scale(timeStep*.5));
        }

        //Integrates the position and take a step (2nd order)
        Point[] newPosition = body.integratePosition(pos, vel, timeStep);
        Point[] acc2 = body.integrateAcc(bodies, newPosition);

        for(int i = 0 ; i < bodies.length ; i ++){
            acc[i] = acc[i].returnAdded(acc2[i]);
        }

        Point[] newVel = body.integrateVel(vClone, acc, timeStep*.5);

        body.setPos(bodies, newPosition);
        body.setVel(bodies, newVel);
    }

    public void yoshida4th(CelestialBody[] bodies){
        double w0 = -(Math.pow(2,(double)1/3)/(2-Math.pow(2,(double)1/3)));
        double w1 = 1/ (2 - (Math.pow(2,(double)1/3)));
        double c1 = w1/2;
        double c2 = (w0+w1)/2;

        Point [] p1, p2, p3, p4;
        Point [] v1, v2, v3;
        Point [] acc;
        Point[] pk = new Point[bodies.length];
        Point[] vk = new Point[bodies.length];


        for(int i = 0 ; i < bodies.length; i++){
            pk[i] = bodies[i].location.clone();
            vk[i] = bodies[i].velocity.clone();
        }

        p1 = body.integratePosition(pk, vk, timeStep*c1);
        acc = body.integrateAcc(bodies, p1);
        v1 = body.integrateVel(vk, acc, timeStep*w1);

        p2 = body.integratePosition(p1, v1, timeStep*c2);
        acc = body.integrateAcc(bodies, p2);
        v2 = body.integrateVel(v1, acc, timeStep*w0);

        p3 = body.integratePosition(p2, v2, timeStep*c2);
        acc = body.integrateAcc(bodies, p3);
        v3 = body.integrateVel(v2, acc, timeStep*w1);

        p4 = body.integratePosition(p3, v3, timeStep*c1);
        //acc = body.integrateAcc

        body.setPos(bodies, p4);
        body.setVel(bodies, v3);

    }

    public void rungekutta6(CelestialBody[] bodies){
        Point[] p1, p2, p3, p4, p5, p6;
        Point[] pk = new Point[bodies.length];

        Point[] v1, v2, v3, v4, v5, v6;
        Point[] vk = new Point[bodies.length];

        Point[] k1, k2, k3, k4, k5, k6;

        Point[] position = new Point[bodies.length];
        Point[] velocity = new Point[bodies.length];

        for(int i = 0 ; i < bodies.length; i++){
            pk[i] = new Point(0,0,0);
            vk[i] = new Point(0,0,0);
            position[i] = bodies[i].location;
            velocity[i] = bodies[i].velocity;
        }

        p1 = position;
        v1 = velocity;
        k1 = body.integrateAcc(bodies, p1);

        v2 = body.integrateVel(velocity, k1, timeStep*(1/9));
        p2 = body.integratePosition(position, v1, timeStep*(1/9));
        k2 = body.integrateAcc(bodies, p2);

        v3 = body.integrateVel(velocity, k2, timeStep*(1/6));
        p3 = body.integratePosition(position, v2, timeStep* (1/6));


    }



}
