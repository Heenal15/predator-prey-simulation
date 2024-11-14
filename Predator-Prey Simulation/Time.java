
/**
 * Time class keeps track of time of day.
 *
 * @author Heenal Vyas and Mustafa Gulzar.
 * @version 2021.03.01.
 */
public class Time 
{
    private int hour;
    /**
     * Constructor for the time class. The starting hour is zero.
     */
    public Time()
    {
        hour = 0;
    }

    /**
     *  The following method gets the hour.
     *  @return hour
     */
    public int getHour()
    {
        return hour;
    }

    /**
     *  The following method resets the hour to zero.
     */
    public void resetHour()
    {
        hour = 0 ;
    }

    /**
     *  Following method increments hour if the hour is less than 23, similar to a 24 hour clock.
     *  Otherwise sets the hour value to zero which is the start of a new day.
     */
    public int incrementHour()
    {
        if (hour >= 23) {
            return hour = 0;
        }
        else{
            return hour++;
        }
    }

    /**
     * Checks whether the hour is between 3pm and 9pm like a 24 hour clock.
     * If the hour is between 15 and 21, the animal is asleep.
     *
     * @param  hour
     * @return true if animal is asleep   
     */
    public boolean isAsleep()
    {
        if (hour >= 15 && hour <= 21 ){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Plants will be able to grow every 20 hours.
     * @return true if it is the growing time for plants.  
     */
    public boolean isGrowingTimeForPlants()
    {
       return hour == 20;
    }

    public boolean isDayTime() {
        return hour >= 6 && hour < 18;  // Define daytime as between 6 and 18.
    }

}
