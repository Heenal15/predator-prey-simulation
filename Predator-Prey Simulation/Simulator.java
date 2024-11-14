import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing ocelots, pythons, rats, magpies and mantises.
 * 
 * @author David J. Barnes, Michael Kölling, Mustafa Gulzar and Heenal Vyas
 * @version 2024.11.14
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that an animal will be created in any given grid position.
    private static final double PYTHON_CREATION_PROBABILITY = 0.05;
    private static final double OCELOT_CREATION_PROBABILITY = 0.05;
    private static final double RAT_CREATION_PROBABILITY = 0.05;
    private static final double MANTIS_CREATION_PROBABILITY = 0.05;
    private static final double MAGPIE_CREATION_PROBABILITY = 0.05;
    // The probability that a plant will be created in any given grid position.
    private static final double PLANT_CREATION_PROBABILITY = 0.05;

    private Weather weather;

    private static final int WEATHER_CHANGE_INTERVAL = 20;

    // List of animals in the field.
    private List<Animal> animals;
    //List of plants in the field.
    private List<Plants> plantList;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    //The time of day.
    private Time time;
    private Random rand = new Random();

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        animals = new ArrayList<>();
        field = new Field(depth, width);
        plantList = new ArrayList<>();
        weather = new Weather();

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Rat.class, Color.ORANGE);
        view.setColor(Python.class, Color.BLUE);
        view.setColor(Ocelot.class, Color.YELLOW);
        view.setColor(Mantis.class, Color.RED);
        view.setColor(Magpie.class, Color.PINK);
        view.setColor(Plants.class, Color.GREEN);

        time = new Time();
        //The default start time.
        view.setTime("00:00");

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * 
     */
    public void runLongSimulation()
    {
        simulate(1000); // 4000 steps changed to 100 steps
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            //delay(60);   // uncomment this to run more slowly
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * animal and plant.
     */
    public void simulateOneStep() {
        step++;
        time.incrementHour();
        view.setTime(time.getHour() + ":00");
        List<Animal> newAnimals = new ArrayList<>();
        List<Plants> newPlants = new ArrayList<>();

        if (step % WEATHER_CHANGE_INTERVAL == 0) {
            weather.changeWeather();
        }

        // Let all animals act
        for (Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            if (!time.isAsleep()) {
                animal.act(newAnimals, weather);
            }
            if (!animal.isAlive()) {
                it.remove();
            }
        }

        // Let all plants grow
        for (Iterator<Plants> it = plantList.iterator(); it.hasNext(); ) {
            Plants plant = it.next();
            if (time.isGrowingTimeForPlants()) {
                plant.grow(weather, newPlants);
            }
            if (plant.isEaten()) {
                it.remove();
            }
        }

        // Add newly born animals and plants to the main lists
        animals.addAll(newAnimals);
        plantList.addAll(newPlants);

        // Display current status
        view.showStatus(step, field);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        plantList.clear();
        populate();
        time = new Time();
        time.resetHour();
        view.setTime("00:00");

        // Show the starting state in the view.
        view.showStatus(step, field);
    }

    private void populate() {
        field.clear();

        int fieldSize = field.getDepth() * field.getWidth();

        // Set target counts for each species (adjust these based on field size and desired population)
        int targetPythons = fieldSize / 70;
        int targetOcelots = fieldSize / 70; 
        int targetRats = fieldSize / 5;    
        int targetMantis = fieldSize / 5;  
        int targetMagpies = fieldSize / 5; 
        int targetPlants = fieldSize / 5;  // Example: 10% of field for Plants

        // Populate each species with a specific target count
        addAnimals(Python.class, targetPythons);
        addAnimals(Ocelot.class, targetOcelots);
        addAnimals(Rat.class, targetRats);
        addAnimals(Mantis.class, targetMantis);
        addAnimals(Magpie.class, targetMagpies);
        addPlants(targetPlants);

    }

    private void addAnimals(Class<? extends Animal> animalClass, int targetCount) {
        int added = 0;
        while (added < targetCount) {
            int row = rand.nextInt(field.getDepth());
            int col = rand.nextInt(field.getWidth());
            Location location = new Location(row, col);
            if (field.getObjectAt(location) == null) { // Check if the location is free
                Animal animal;
                if (animalClass == Python.class) {
                    animal = new Python(field, location, time);
                } else if (animalClass == Ocelot.class) {
                    animal = new Ocelot(field, location, time);
                } else if (animalClass == Rat.class) {
                    animal = new Rat(field, location, time);
                } else if (animalClass == Mantis.class) {
                    animal = new Mantis(field, location, time);
                } else if (animalClass == Magpie.class) {
                    animal = new Magpie(field, location, time);
                } else {
                    throw new IllegalArgumentException("Unknown animal class: " + animalClass);
                }
                animals.add(animal);
                field.place(animal, location);
                added++;
            }
        }
    }

    private void addPlants(int targetCount) {
        int added = 0;
        while (added < targetCount) {
            int row = rand.nextInt(field.getDepth());
            int col = rand.nextInt(field.getWidth());
            Location location = new Location(row, col);
            if (field.getObjectAt(location) == null) { // Check if the location is free
                Plants plant = new Plants(field, location);
                plantList.add(plant);
                field.place(plant, location);
                added++;
            }
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }

    public static void main(String[] args) {
        // Set up the simulator with default field size
        Simulator simulator = new Simulator(DEFAULT_DEPTH, DEFAULT_WIDTH);
        simulator.runLongSimulation();

    }

}
