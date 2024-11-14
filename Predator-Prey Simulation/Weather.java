import java.util.Random;

public class Weather {
    // Enum for different weather conditions
    public enum Condition {
        SUNNY, RAINY, FOGGY, SNOWY
    }
    
    private Condition currentCondition;
    private static final Random rand = new Random();

    /**
     * Constructor for Weather.
     * Initializes with a random weather condition.
     */
    public Weather() {
        changeWeather(); // Start with a random weather condition
    }

    /**
     * Get the current weather condition.
     * @return The current weather condition.
     */
    public Condition getCurrentCondition() {
        return currentCondition;
    }

    /**
     * Change to a new random weather condition.
     */
    public void changeWeather() {
        // Randomly change the weather condition
        int pick = rand.nextInt(Condition.values().length);
        currentCondition = Condition.values()[pick];
        System.out.println("Weather changed to: " + currentCondition);
    }

    /**
     * Determine if it is raining.
     * @return true if the weather is RAINY.
     */
    public boolean isRainy() {
        return currentCondition == Condition.RAINY;
    }

    /**
     * Determine if it is foggy.
     * @return true if the weather is FOGGY.
     */
    public boolean isFoggy() {
        return currentCondition == Condition.FOGGY;
    }
    
    public boolean isSunny() {
        return currentCondition == Condition.SUNNY;
    }
    
    public boolean isSnowy() {
        return currentCondition == Condition.SNOWY;
    }

    // Add other condition-specific methods as needed (e.g., isSunny, isSnowy)
}