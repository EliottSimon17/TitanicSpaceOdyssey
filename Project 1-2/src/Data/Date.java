package Data;

public class Date {
    private int year;
    private int month;
    private int day;
    private int secondsPassed=0;
    final private int SECONDSINDAY = 86400;
    private int leap = 0;
    final private int[] DAYS_IN_MONTH = {31,28+leap, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     *
     * @param year the year the simulation happens
     * @param month the month the simulation happens
     * @param day the day the simulation happens
     */
    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
        if(year%4==0)
            leap = 1;
    }

    //update the time for each second since we are working with seconds in the main
    //method
    public void updateTime(double seconds){
        secondsPassed+=seconds;
        if(secondsPassed>SECONDSINDAY){
            day+=secondsPassed/SECONDSINDAY;
            secondsPassed %= SECONDSINDAY;
        }
        int daysThisMonth = DAYS_IN_MONTH[month-1];
        if(day>daysThisMonth){
            month+=day/daysThisMonth;
            day%=daysThisMonth;

        }
        if(month>DAYS_IN_MONTH.length){
            year+=month/DAYS_IN_MONTH.length;
            month%=DAYS_IN_MONTH.length;
            leap = 0;
            if(year%4==0){
                leap = 1;
            }
        }
    }

    /**
     *
     * @return a string containing the date of the simulation
     * example : 17/08/2000 is the 17th of august of 2000
     */
    public String getDate(){
        return(String.format("%d/%d/%d", day, month, year));
    }
}
