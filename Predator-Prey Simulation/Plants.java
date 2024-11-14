import java.util.Random;
import java.util.List;

/**
 * Plant classes which some animals can eat.
 *
 * @author Mustafa Gulzar and Heenal Vyas
 * @version 2024.11.14
 */
public class Plants
{
    // Whether the plant is eaten or not.
    private boolean eaten;
    // The plant's field.
    private Field field;
    // The plant's position in the field.
    private Location location;
    //randomizer variable
    private static final Random rand = Randomizer.getRandom();
    /**
     * Constructor for objects of class Plants
     */
    public Plants(Field field, Location location)
    {
        eaten = false;
        this.field = field;
        setLocation(location);
    }

    /**
     * Place the plant at the new location in the given field.
     * @param newLocation The plant's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Check whether the plant is eaten or not.
     * @return true if the plant is eaten.
     */
    protected boolean isEaten()
    {
        return eaten;
    }

    /**
     * Return the plant's field.
     * @return The plant's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * Indicate that the plant is eaten.
     * It is removed from the field.
     */
    protected void setEaten()
    {
        eaten = true;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the plant's location.
     * @return The plant's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     *  To grow a new plant (seedling) in the next free adjacent location.
     *  @param list of plants
     */
    public void growNewPlant(List<Plants> newPlants){

        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());

        if (free.size()>0){
            Location loc = free.remove(rand.nextInt(free.size()));
            Plants seedling = new Plants(field, loc);
            newPlants.add(seedling);
        }

    }

    /**
     * The following method indicates what happens when a plant is eaten
     */
    public void grow(Weather weather, List<Plants> newPlants)
    {
        if(weather.isRainy() || weather.isSunny()) {
            growNewPlant(newPlants);            
        }
        else{
            setEaten();
        }
}
}
