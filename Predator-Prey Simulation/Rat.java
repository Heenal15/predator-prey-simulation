import java.util.List;

public class Rat extends Prey {
    
    private static final int RAT_LITTER_SIZE = 300;

    public Rat(Field field, Location location, Time time) {
        super(field, location, time, 100, 2, 0.9, 50); // maxAge=8, breedingAge=2, breedingProbability=0.25
    }

    @Override
    protected Animal createOffspring(Location location) {
        return new Rat(getField(), location, getTime());
    }
    
    @Override
    protected int getLitterSize() {
        return RAT_LITTER_SIZE;
    }
}
