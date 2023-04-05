package modele.legumes;

public class Salade extends Legume {

    public Salade() {
        super();
        temperatureMin = 10;
        temperatureMax = 35;
    }

    public static final float PRICE = 2f;
    @Override
    public Varietes getVariete() {
        return Varietes.SALADE;
    }

    @Override
    protected float getCoinBaseValue() {
        return PRICE;
    }

    @Override
    protected void croissance() {
        updateCroissance(0.8, 0.5, 0.2);
    }
}
