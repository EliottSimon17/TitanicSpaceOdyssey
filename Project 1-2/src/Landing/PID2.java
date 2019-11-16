package Landing;

public class PID2 {

    private double kp;
    private double ki;
    private double kd;

    private double errorX;
    private double errorY;
    private double previousErrorX;
    private double previousErrorY;

    private double integralX;
    private double integralY;

    private double derivativeX;
    private double derivativeY;

    private boolean conditionX;
    private boolean conditionY;

    private float targetValue;

    private Rocket rocket;


    public PID2(Rocket rocket) {

        this.rocket = rocket;
        kp = 0.7285;
        ki = 0.45;
        kd = 0.1335;
        conditionX = true;
        conditionY = true;

        targetValue = 0;

    }

    public void calculate() {

        previousErrorX = targetValue-rocket.getVelX();
        previousErrorY = targetValue - rocket.getVelY();
        while (conditionX && conditionY) {
            errorX = rocket.getVelX()-targetValue;
            integralX += errorX;

            //System.out.println("Error X: " + errorX);

            errorY = rocket.getVelY()-targetValue;
            integralY += errorY;

            //System.out.println("Error Y: " + errorY);


            if (errorX == 0) {
                integralX = 0;
                conditionX = false;
            } else if (Math.abs(errorX) > 40) {
                integralX = 0;
                conditionX = false;
            }


            if (errorY == 0) {
                integralY = 0;
                conditionY = false;
            } else if (Math.abs(errorY) > 40) {
                integralY = 0;
                conditionY = false;
            }

            derivativeX = (errorX - previousErrorX);

            derivativeY = (errorY - previousErrorY);

            double vel = kp * errorX + ki * integralX + kd * derivativeX;
            rocket.setVelX(vel);
            //System.out.println("X: " + rocket.getVelX());

            double velY = kp * errorY + ki * integralY + kd * derivativeY;
            rocket.setVelY(velY);
            //System.out.println("Y: " + rocket.getVelY());

            previousErrorX = errorX;
            previousErrorY = errorY;
        }
    }
}
