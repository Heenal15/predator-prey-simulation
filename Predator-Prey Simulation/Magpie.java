public class Magpie extends Prey {
    
    private static final int MAGPIE_LITTER_SIZE = 300;

    public Magpie(Field field, Location location, Time time) {
        super(field, location, time, 100, 3, 0.8, 50);
    }

    @Override
    protected Animal createOffspring(Location location) {
        return new Magpie(getField(), location, getTime());
    }
    
    @Override
    protected int getLitterSize() {
        return MAGPIE_LITTER_SIZE;
    }
  
}