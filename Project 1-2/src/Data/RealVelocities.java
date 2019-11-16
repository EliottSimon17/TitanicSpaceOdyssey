package Data;

public class RealVelocities {

    /** These values represents the 3d vector of the velocity of every planet of the solar system
     *  They are all in meters per second and can be found on this website
     * @see {https://ssd.jpl.nasa.gov/horizons.cgi#top}
     */
    private static double mercuryXVel = 3.837275299364725E+04;
    private static double mercuryYVel = 9.930895118862866E+03;
    private static double mercuryZVel = -2.708681501144652E+03;

    private static double venusXVel = -3.512135210075254E+04;
    private static double venusYVel = -1.324960464439158E+03;
    private static double venusZVel = 2.008583187309812E+03;

    private static double earthXVel = 2.712382813494149E+04;
    private static double earthYVel = 1.104275855203031E+04;
    private static double earthZVel = 4.916553979299465E-07;

    private static double marsXVel = -1.537222890618722E+04;
    private static double marsYVel = -1.587537182956243E+04;
    private static double marsZVel = 4.452484405566093E-05;

    private static double jupiterXVel = 1.279026928930304E+04;
    private static double jupiterYVel = -1.211507038929954E+03;
    private static double jupiterZVel = -2.812378654408884E-04;

    private static double saturnXVel = 8.712026528564804E+03;
    private static double saturnYVel = 2.811199863987989E+03;
    private static double saturnZVel = -3.952319960231777E-04;

    private static double uranusXVel = -3.764621202718402E+03;
    private static double uranusYVel = 5.381140040864593E+03;
    private static double uranusZVel = 6.851537335588653E-05;

    private static double neptuneXVel = 1.228023457300544E+03;
    private static double neptuneYVel = 5.321361433634341E+03;
    private static double neptuneZVel = -1.380908195345469E-04;


    private static double titanXVel = 1.393360765499658E+04;
    private static double titanYVel = 4.523548905639481E+03;
    private static double titanZVel = -1.797275350793326E+03;

    public static Point getSunVelocity() {
        return new Point(0, 0, 0);
    }
    public static Point getMercuryVelocity() {
        return new Point(mercuryXVel, mercuryYVel, mercuryZVel);
    }
    public static Point getVenusVelocity() {
        return new Point(venusXVel, venusYVel, venusZVel);
    }
    public static Point getEarthVelocity() {
        return new Point(earthXVel, earthYVel, earthZVel);
    }
    public static Point getMarsVelocity() {
        return new Point(marsXVel, marsYVel, marsZVel);
    }
    public static Point getJupiterVelocity() {
        return new Point(jupiterXVel, jupiterYVel, jupiterZVel);
    }
    public static Point getSaturnVelocity() {
        return new Point(saturnXVel, saturnYVel, saturnZVel);
    }
    public static Point getTitanVelocity() {
        return new Point(titanXVel, titanYVel, titanZVel);
    }
    public static Point getUranusVelocity() {
        return new Point(uranusXVel, uranusYVel, uranusZVel);
    }
    public static Point getNeptuneVelocity(){
        return new Point(neptuneXVel, neptuneYVel, neptuneZVel);
    }
    public static Point getRocketLaunchVel() {
        return new Point(4000, 9000, 0);
    }
    public static Point getRocketVelocity(){
        return new Point(0, 4400, -8000);
    }
}
