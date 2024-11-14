import java.util.List;
import java.util.Random;

public abstract class Prey extends Animal {
    private static final Random rand = new Random();
    private int foodLevel;       // Current food level for hunger tracking
    private int maxFoodLevel;    // Maximum food level before full

    public Prey(Field field, Location location, Time time, int maxAge, int breedingAge, double breedingProbability, int maxFoodLevel) {
        super(field, location, time, maxAge, breedingAge, breedingProbability);
        this.maxFoodLevel = maxFoodLevel;
        this.foodLevel = maxFoodLevel / 2; // Start with half food level
    }

    @Override
    public void act(List<Animal> newAnimals, Weather weather) {
        if (isAlive()) {
            if (!getTime().isAsleep()) {
                forage();
            }
            age();
            if (foodLevel <= 0) {
                setDead();
            }
        }
    }

    /**
     * Attempt to eat plants in adjacent locations.
     * If a plant is found, increase the prey's food level and mark the plant as eaten.
     */
    protected void eatPlants() {
        List<Location> adjacent = getField().adjacentLocations(getLocation());
        for (Location loc : adjacent) {
            Object obj = getField().getObjectAt(loc);
            if (obj instanceof Plants) {
                Plants plant = (Plants) obj;
                if (!plant.isEaten()) {
                    plant.setEaten(); // Mark the plant as eaten
                    foodLevel = Math.min(foodLevel + 1, maxFoodLevel); // Increase food level by 1
                    System.out.println(getClass().getSimpleName() + " eats a plant and gains 1 food point.");
                    break; // Stop after eating one plant
                }
            }
        }
    }

    /**
     * Forage behavior, where prey animals look for food, such as plants.
     */
    protected void forage() {
        eatPlants(); // Attempt to eat plants
        moveRandomly(); // Move randomly if no plant was found or after eating
    }

    protected void moveRandomly() {
        Location newLocation = getField().freeAdjacentLocation(getLocation());
        if (newLocation != null) {
            setLocation(newLocation);
        }
    }

}