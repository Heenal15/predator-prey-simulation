import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's sex: 0 for male, 1 for female.
    private int sex,age,maxAge, foodLevel;
    //randomizer variable
    private static final Random rand = Randomizer.getRandom();
    //The time of day
    private Time time;
    private int breedingAge; // Minimum age required for breeding
    private double breedingProbability; // Probability of breeding successfully
    private static final int DEFAULT_LITTER_SIZE = 1;

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location, Time time, int maxAge, int breedingAge, double breedingProbability)
    {
        this.alive = true;
        this.field = field;
        this.time = time;
        //time= new Time();
        setLocation(location);
        this.sex = rand.nextInt(2);
        this.age = 0;
        this.maxAge = maxAge;
        this.breedingAge = breedingAge;
        this.breedingProbability = breedingProbability;
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals, Weather weather);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
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
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }

    protected int getSex(){
        return sex;
    }

    protected Time getTime(){
        return time;
    }

    protected void age() {
        age++;
        if (age > maxAge) {
            setDead();  // The animal dies if it surpasses its lifespan.
        }
    }

    // Getter for age, if needed
    protected int getAge() {
        return age;
    }

    protected int getMaxAge(){
        return maxAge;
    }

    protected int getBreedingAge(){
        return breedingAge;
    }

    protected double getBreedingProbability(){
        return breedingProbability;
    }

    /**
     * Attempt to breed if conditions are met.
     * Adds new animals to the list if breeding is successful.
     */
    protected void breed(List<Animal> newAnimals) {
        if (canBreed()) {
            Animal mate = findMate();
            int litterSize = getLitterSize();
            for (int i = 0; i < litterSize; i++) {
                if (mate != null) {
                    Location birthLocation = getField().freeAdjacentLocation(getLocation());
                    if (birthLocation != null) {
                        Animal offspring = createOffspring(birthLocation);
                        newAnimals.add(offspring);
                        System.out.println(this.getClass().getSimpleName() + " has bred a new offspring.");
                    }
                    else{
                        break;
                    }
                }
            }
        }
    }
    
     protected int getLitterSize() {
        return DEFAULT_LITTER_SIZE; // Default to 1 offspring per breeding
    }

    protected boolean canBreed() {
        return getAge() >= getBreedingAge() && rand.nextDouble() <= getBreedingProbability();
    }

    /**
     * Find a mate of the same species and opposite gender in adjacent locations.
     * @return A suitable mate if found, otherwise null.
     */
    protected Animal findMate() {
        List<Location> adjacent = getField().adjacentLocations(getLocation());
        for (Location loc : adjacent) {
            Object obj = getField().getObjectAt(loc);
            if (obj instanceof Animal && obj.getClass() == this.getClass()) {
                Animal potentialMate = (Animal) obj;
                if (potentialMate.getSex() != this.getSex() && potentialMate.isAlive() && potentialMate.getAge() >= breedingAge) {
                    return potentialMate;
                }
            }
        }
        return null;
    }

    /**
     * Create a new offspring. This method should be overridden in subclasses.
     * @param location The location of the offspring.
     * @return A new Animal offspring.
     */
    protected abstract Animal createOffspring(Location location);

}
