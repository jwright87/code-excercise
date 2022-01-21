package wright.john.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
class ItemTest {



    @Test
    public void shouldHaveMultipleItemsOfSameSkuWithDifferentFieldValues() {

        Item item1 = Item.createItem('A');
        Item item2 = Item.createItem('A');

        item1.setCharged(true);

        assertTrue(item1.isCharged());
        assertFalse(item2.isCharged());

    }

    @Test
    public void shouldCreateAllSkus() {
        assertThat(Item.createItem('A').getName().equals("Carrot"));
        assertThat(Item.createItem('B').getName().equals("Banana"));
        assertThat(Item.createItem('C').getName().equals("Apple"));
        assertThat(Item.createItem('D').getName().equals("Orange"));
    }

    @Test
    public void shouldCreateAllByName() {
        assertThat(Item.createItem("Carrot").getName().equals('A'));
        assertThat(Item.createItem("Banana").getName().equals('B'));
        assertThat(Item.createItem("ApPle").getName().equals('C'));
        assertThat(Item.createItem("orange").getName().equals('D'));
    }





    @Test
    public void shouldThrowExceptionWhenPassedUnknownSku() {
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
                Item.createItem('E');
        });

    }
    @Test
    public void shouldThrowExceptionWhenPassedUnknownFruit() {
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
                Item.createItem("Grapefruit");
        });

    }


    public void shouldCreateItemsWithCorrectBasePrice() {
        assertThat(Item.createItem('A').getPrice()).isEqualTo(BigDecimal.valueOf(50));
        assertThat(Item.createItem('B').getPrice()).isEqualTo(BigDecimal.valueOf(30));
        assertThat(Item.createItem('C').getPrice()).isEqualTo(BigDecimal.valueOf(20));
        assertThat(Item.createItem('D').getPrice()).isEqualTo(BigDecimal.valueOf(15));
    }

}