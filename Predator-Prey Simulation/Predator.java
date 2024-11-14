import java.util.List;

public abstract class Predator extends Animal
{
    private int foodLevel; // Current food level for hunger tracking.
    private int maxFoodLevel;
    private double huntingProbability;
    /**
     * Constructor for objects of class Predator
     */
    public Predator(Field field, Location location, Time time, int maxAge, int breedingAge, double breedingProbability, int maxFoodLevel, double huntingProbability)
    {
        super(field, location,time, maxAge, breedingAge, breedingProbability);
        this.maxFoodLevel = maxFoodLevel;
        this.foodLevel = maxFoodLevel / 2;
        this.huntingProbability = huntingProbability;
    }

    @Override
    public void act(List<Animal> newAnimals, Weather weather) {
        if (isAlive()) {
            int currentHour = getTime().getHour();
            if (!getTime().isAsleep()) {
                if (currentHour < 12) {  // Morning activity
                    hunt(weather, newAnimals);
                    breed(newAnimals);
                } else if (currentHour >= 18) {  // Evening activity
                    moveRandomly();
                } else {  // Afternoon or inactive hours
                    rest();
                }
            } else {
                rest();  // Rest during sleep time
            }
            age();
            if (foodLevel <= 0) {
                setDead();
            }
        }
    }
    
      
    protected abstract double getBaseHuntingProbability();
    
    protected double getHuntingProbability() {
        return huntingProbability;
    }

    protected void setHuntingProbability(double probability) {
        this.huntingProbability = probability;
    }

    protected int getFoodLevel() {
        return foodLevel;
    }

    protected void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    protected int getMaxFoodLevel() {
        return maxFoodLevel;
    }

    protected void hunt(Weather weather, List<Animal> newAnimals) {
        // Attempt to hunt based on adjusted hunting probability
        if (Math.random() <= getHuntingProbability()) {
            Prey prey = findPrey();
            if (prey != null) {
                eat(prey);
            } else {
                moveRandomly(); // Move randomly if no prey is found
            }
        } else {
            moveRandomly(); // Hunt unsuccessful, move randomly
        }
    }

    protected Prey findPrey() {
        // Logic to find prey within hunting range.
        // For instance, checking nearby locations in the field for a prey animal.
        List<Location> adjacent = getField().adjacentLocations(getLocation());
        for (Location loc : adjacent) {
            Object obj = getField().getObjectAt(loc); // Retrieve the object at location
            if (obj instanceof Animal) {              // Check if it's an Animal
                Animal animal = (Animal) obj;         // Cast to Animal
                if (animal instanceof Prey && animal.isAlive()) {
                    return (Prey) animal;             // Return as Prey if conditions are met
                }
            }
        }
        return null;
    }

    protected void eat(Prey prey) {
        prey.setDead(); // Kill the prey.
        foodLevel = Math.min(foodLevel + 10, maxFoodLevel); // Increase food level up to max.
    }

    protected void moveRandomly() {
        // Move to a free adjacent location if possible.
        Location newLocation = getField().freeAdjacentLocation(getLocation());
        if (newLocation != null) {
            setLocation(newLocation);
        }
    }

    protected void rest() {
        // Rest behavior for night time.
    }

}

