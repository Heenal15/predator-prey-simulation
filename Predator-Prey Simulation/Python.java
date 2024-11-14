import java.util.List;

/**
 * A simple model of a Python.
 * Pythons age, move, eat rats, magpies, mantises and die.
 * 
 * @author David J. Barnes, Michael Kölling, Mustafa Gulzar and Heenal Vyas
 * @version 2024.11.14
 */

public class Python extends Predator {
    
    private static final double BASE_HUNTING_PROBABILITY = 0.15;
    private static final int PYTHON_LITTER_SIZE = 3;
    
    /**
     * Constructor for Python, setting specific values for max age, breeding age, and breeding probability.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param time The current time object for tracking day/night cycles.
     */
    public Python(Field field, Location location, Time time) {
        // Pass specific values to the Predator constructor
        super(field, location, time, 50, 10, 0.15, 20,BASE_HUNTING_PROBABILITY); // maxAge=20, breedingAge=3, breedingProbability=0.15
        setFoodLevel(10); // Set the initial food level for Python
    }

    /**
     * Create a new Python in the specified location.
     * This is called when an Python breeds.
     *
     * @param location The location for the new Python.
     * @return A new Python instance.
     */
    @Override
    protected Animal createOffspring(Location location) {
        return new Python(getField(), location, getTime()); // Creates a new Python at the specified location
    }
    
    @Override
    protected double getBaseHuntingProbability() {
        return BASE_HUNTING_PROBABILITY;
    }
    
    @Override
    protected int getLitterSize() {
        return PYTHON_LITTER_SIZE;
    }

    @Override
    protected void hunt(Weather weather, List<Animal> newAnimals) {
        adjustHuntingProbability(weather);
        // Attempt to hunt based on adjusted hunting probability
        if (Math.random() <= getHuntingProbability()) {
            Prey prey = findPrey();
            if (prey != null) {
                eat(prey);
            } else {
                moveRandomly();
            }
        } else {
            moveRandomly(); // Hunt unsuccessful, move randomly
        }
    }

    protected void adjustHuntingProbability(Weather weather) {
        if (weather.isSunny()) {
            setHuntingProbability(BASE_HUNTING_PROBABILITY + 0.05); // Increase by 5% in sunny weather
        } else if (weather.isRainy()) {
            setHuntingProbability(BASE_HUNTING_PROBABILITY - 0.1); // Decrease by 10% in rainy weather
        } else if (weather.isSnowy()) {
            setHuntingProbability(BASE_HUNTING_PROBABILITY - 0.2); // Decrease by 20% in snowy weather
        } else { // FOGGY or other
            setHuntingProbability(BASE_HUNTING_PROBABILITY); // No change
        }
    }

    /**
     * Define Python's behavior for eating prey.
     *
     * @param prey The prey to be eaten.
     */
    @Override
    protected void eat(Prey prey) {
        // Determine food value based on the type of prey
        int foodValue = 0;

        if (prey instanceof Rat) {
            foodValue = 2;
        } else if (prey instanceof Mantis) {
            foodValue = 1;
        } else if (prey instanceof Magpie) {
            foodValue = 3;
        }

        // Increase food level based on the prey's food value
        setFoodLevel(Math.min(getFoodLevel() + foodValue, getMaxFoodLevel()));

        prey.setDead(); // Mark the prey as dead
    }

}
