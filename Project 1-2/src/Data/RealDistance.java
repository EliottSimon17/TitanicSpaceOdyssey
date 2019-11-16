package Data;

public class RealDistance {
    private static final double EARTH_RADIUS = 6371000;

    /** These values represents the 3d coordinates of every planet of the solar system
     *  They are all in meters and can be found on this website
     * @see {https://ssd.jpl.nasa.gov/horizons.cgi#top}
     */

    private static double sunXDistance = 0;
    private static double sunYDistance = 0;
    private static double sunZDistance = 0;

    private static double mercuryXDistance = 1.041743118363710E+10;
    private static double mercuryYDistance = -6.747721189865157E+10;
    private static double mercuryZDistance = -6.469473376363825E+09;


    private static double venusXDistance = -3.501810438615073E+09;
    private static double venusYDistance = 1.075749151195597E+11;
    private static double venusZDistance = 1.678139140435964E+09;


    private static double earthXDistance = 5.694582396564658E+10;
    private static double earthYDistance = -1.409997129837137E+11;
    private static double earthZDistance = 6.174538423918188E+06;


    private static double marsXDistance = -1.835011826887369E+11;
    private static double marsYDistance = 1.665319783422335E+11;
    private static double marsZDistance = 7.991891476964392E+09;


    private static double jupiterXDistance = -1.105722535643885E+11;
    private static double jupiterYDistance = -7.832609077326889E+11;
    private static double jupiterZDistance = 5.727373286018491E+09;

    private static double saturneXDistance = 4.421345973182874E+11;
    private static double saturneYDistance = -1.436814980588238E+12;
    private static double saturneZDistance = 7.378978343220294E+09;

    private static double uranusXDistance = 2.483943004546296E+12;
    private static double uranusYDistance = 1.624123903279656E+12;
    private static double uranusZDistance = -2.613660227683461E+10;

    private static double neptuneXDistance = 4.357312213730332E+12;
    private static double neptuneYDistance = -1.030872204409889E+12;
    private static double neptuneZDistance = -7.920279680524856E+10;

    private static double moonXDistance = 5.694584106441900E+10;
    private static double moonYDistance = -1.410044873513359E+11;
    private static double moonZDistance = 6.305181382186711E+06;

    public static double titanXDistance = 4.425657895695304E+11;
    public static double titanYDistance = -1.437827691660454E+12;
    public static double titanZDistance = 7.858057578063548E+09;


    public static Point getMercuryDistance() {
        return new Point(mercuryXDistance, mercuryYDistance, mercuryZDistance);
    }
    public static Point getVenusDistance() {
        return new Point(venusXDistance, venusYDistance, venusZDistance);
    }
    public static Point getEarthDistance(){
        return new Point(earthXDistance, earthYDistance, earthZDistance);
    }
    public static Point getMarsDistance(){
        return new Point(marsXDistance, marsYDistance, marsZDistance);
    }
    public static Point getJupiterDistance(){
        return new Point(jupiterXDistance, jupiterYDistance, jupiterZDistance);
    }
    public static Point getSaturnDistance(){
        return new Point(saturneXDistance, saturneYDistance, saturneZDistance);
    }
    public static Point getTitanDistance(){
        return new Point(titanXDistance, titanYDistance, titanZDistance);
    }
    public static Point getUranusDistance(){
        return new Point(uranusXDistance, uranusYDistance, uranusZDistance);
    }
    public static Point getNeptuneDistance(){
        return new Point(neptuneXDistance, neptuneYDistance, neptuneZDistance);
    }
    public static Point getSunDistance(){
        return new Point(sunXDistance, sunYDistance, sunZDistance);
    }

    public static Point getRocketDistance(){
        return new Point(earthXDistance + EARTH_RADIUS + 500000, earthYDistance , earthZDistance);
    }


}



