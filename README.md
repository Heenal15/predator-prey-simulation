# Predator-Prey Simulation
This project is a predator-prey ecosystem simulation, created as part of a university assignment. The simulation models interactions between various species within a defined environment, representing a simplified ecosystem. Species include predators, prey, and plants, each with unique characteristics and behaviors. The simulation showcases population dynamics, breeding, hunting, and survival strategies under varying conditions.

## Project Overview
The simulation is based on a rectangular grid representing a habitat populated by predators (e.g., ocelots and pythons), prey (e.g., rats, mantises, and magpies), and plants as food sources for the prey. The program is designed to demonstrate population changes over time, accounting for factors like reproduction, food availability, and environmental effects.

### Key Features
- Species Diversity: The ecosystem includes multiple predator and prey species, each with unique traits. Predators hunt prey, while prey species rely on plants for sustenance.

- Breeding Mechanism: Each species has breeding conditions, including breeding probability, breeding age, and litter size, allowing the populations to grow dynamically.

- Hunting BehavioUr: Predators have specific hunting probabilities that affect their success in catching prey. Hunting success is influenced by environmental factors, such as weather.

- Weather Influence: The simulation includes variable weather conditions (e.g., sunny, foggy, rainy, snowy) that impact species behavior, such as hunting and plant growth rates.

- Plant Growth: Plants grow at defined intervals, providing a renewable food source for prey species.

- Population Tracking: Population numbers for each species are displayed in real-time, allowing users to observe ecosystem balance and changes over time.

### How the Simulation Works
- Initialization: The simulation begins by populating the field with initial counts of predators, prey, and plants. Each species is placed randomly within the grid.

- Step-by-Step Simulation: At each time step, animals perform actions such as moving, hunting, eating, breeding, and resting based on the current time and environmental conditions.

- Dynamic Weather: Weather conditions change periodically, affecting hunting success and plant growth.

- Population Dynamics: As animals hunt, reproduce, or die, the population of each species fluctuates, reflecting the balance (or imbalance) in the ecosystem.
  
#### Species List
Predators

- Ocelot: Hunts smaller animals with a high hunting probability, breeds with age and probability requirements.
- Python: A more passive predator with a lower hunting probability but similar breeding mechanics.
  
Prey

- Rat: Breeds quickly with a high probability, provides a food source for predators.
- Mantis: Produces multiple offspring at once, enhancing population resilience.
- Magpie: Another prey species that relies on plants and breeds at a moderate rate.
- Plants: Provides a renewable food source for prey. Grows at defined intervals and can be consumed by prey.
