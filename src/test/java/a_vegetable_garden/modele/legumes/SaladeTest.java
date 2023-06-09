package a_vegetable_garden.modele.legumes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaladeTest {
    Salade salade;

    @BeforeEach
    void setUp() {
        salade = new Salade();
    }

    @Test
    @DisplayName("Salade.getVariete() should return Varietes.salade")
    void getVariete() {
        assertEquals(Varietes.SALADE, salade.getVariete());
    }

    @Test
    @DisplayName("Salade.croissance() should do something")
    void croissance() {
        // TODO
    }

    @Test
    @DisplayName("Salade.getCoinPrice() should return Salade.PRICE")
    void getCoinPrice() {
        assertEquals(Salade.PRICE, salade.getCoinPrice());
    }

}