package Data;

public class Point {
    public double x=0;
    public double y=0;
    public double z=0;

    public Point(){
    }
    public Point(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(Point p){
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }
    public Point(double x){
        this.x = x;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getZ(){
        return z;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setZ(double z) {
        this.z = z;
    }
    public void changeX(double change){
        x+=change;
    }
    public void changeY(double change){
        y+=change;
    }
    public void changeZ(double change){
        z+=change;
    }

    /**
     * @param point : A point to be added to another point
     * @return point + point
     */
    public void add(Point point){
        this.x+=point.getX();
        this.y+=point.getY();
        this.z+=point.getZ();
    }
    public double getDistance(Point point){
        return Math.cbrt(Math.abs(x-point.getX()) + Math.abs(y-point.getY()) + Math.abs(z-point.getZ()));
    }

    /**
     * @return copy of first Point
     */
    public Point samePoint(){
        Point pt = new Point(this.x, this.y, this.z);
        return pt;
    }

    /**
     *
     * @return point which is equal to the square root of the sum of x, y and z squared
     */

    public Point magnitude () {
        return new Point(Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2)));
    }

    /**
     @param scalingFactor = value by which we want to multiply our point class
     @return Point that has all his coordinates multiplied by the scaling factor
     */
    public Point scale (double scalingFactor) {
        return new Point(this.x * scalingFactor, this.y * scalingFactor, this.z * scalingFactor);
    }

    /**
     @param b = Point
     @return the subtraction of Point a and b
     */
    public Point subtract (Point b) {
        return new Point(this.x - b.x, this.y - b.y, this.z - b.z);
    }

    /**
     @param dividingFactor = double value
     @return the quotient of every coordinate of Point A divided by dividingFactor
     */
    public Point div(double dividingFactor) {
        return new Point(this.x / dividingFactor, this.y /dividingFactor, this.z /dividingFactor);
    }

    //Increment x by a
    public void addX(double a) {
        this.x += a;
    }

    // Increment Y by a
    public void addY(double a) {
        this.y += a;
    }

    // Increment Z by a
    public void addZ(double a) {
        this.z += a;
    }
}
