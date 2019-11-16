package Test;

import Data.Point;


public class NasaTest {
    /** These 3d vectors represents the position of the earth after different occurence of time
     * They are real values that can be found on
     * @see {https://ssd.jpl.nasa.gov/horizons.cgi#top}
     **/

    //2019-7-16
    public static Point earthAfterOneDay = new Point(5.928119922752549E+10, -1.400259177554518E+11, 6.218602790273726E+06);

    //2019-8-17
    public static Point earthAfterOneMonth = new Point(1.218622676800518E+11, -8.997664665707500E+10, 3.824146568093449E+06);

    //2020-7-15
    public static Point earthAfterOneYear = new Point( 5.867713629231895E+10, -1.402873822352421E+11,6.675567435540259E+06);

    //2029-8-17
    public static Point earthAfterTenYears = new Point( 1.225024318653587E+11, -8.907409881648317E+10,5.576478583671153E+06);


}


