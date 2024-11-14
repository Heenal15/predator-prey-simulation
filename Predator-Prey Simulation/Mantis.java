import java.util.List;

public class Mantis extends Prey {
    private static final int MANTIS_LITTER_SIZE = 500;

    public Mantis(Field field, Location location, Time time) {
        super(field, location, time, 100, 1, 0.7,50);
    }
    
    @Override
    protected int getLitterSize() {
        return MANTIS_LITTER_SIZE;
    }
    
    @Override
    protected Animal createOffspring(Location location) {
        return new Mantis(getField(), location, getTime());
    }

}