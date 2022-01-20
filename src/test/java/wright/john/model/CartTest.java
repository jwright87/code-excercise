package wright.john.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wright.john.promotions.MultiBuyPromotion;
import wright.john.promotions.PromotionFactory;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CartTest {


    private Item itemA = Item.createItem('A');
    private Item itemB = Item.createItem('B');
    private Item itemC = Item.createItem('C');
    private Item itemD = Item.createItem('D');
    private Cart cart = new Cart();

    private MultiBuyPromotion multiBuyPromotion =
            PromotionFactory.multiBuyPromotion(cart, 'B', 2, BigDecimal.valueOf(75l));

    @BeforeEach
    public void setup() {

        cart.addItems(itemA, itemB, itemC);
    }

    @Test
    public void allItemsShouldBePriced() {
        assertTrue(cart.allItemsArePriced());
    }

    @Test
    public void itemShouldHavePromotion() {

        cart.addPromotion(itemC.getSku(), PromotionFactory.percentDiscountPromotion(cart, itemC.getSku(), BigDecimal.valueOf(10)));
        assertTrue(cart.itemHasPromotion(itemC));

    }

    @Test
    public void itemShouldNotHavePromotion() {
        assertFalse(cart.itemHasPromotion(itemB));
    }

    @Test
    public void shouldAddTwoMoreItemsToCart() {
        cart.addItems(Item.createItem('D'), Item.createItem('B'));
        assertThat(cart.getItems().size()).isEqualTo(5);
    }

    @Test
    public void shouldAddPromotion() {
        cart.addItems(itemA, itemA);
        cart.addPromotion(itemA.getSku(),
                PromotionFactory.multiBuyPromotion(cart, itemA.getSku(), 3, BigDecimal.valueOf(120)));
        assertThat(cart.getPromotionForItem(itemA)).isInstanceOf(MultiBuyPromotion.class);
        assertThat(cart.getPromotionForItem(itemB)).isNull();
    }

    @Test
    public void skuShouldHavePromotion() {
        cart.addPromotion(itemB.getSku(), PromotionFactory.percentDiscountPromotion(cart, itemB.getSku(), BigDecimal.valueOf(30)));

        assertTrue(cart.itemHasPromotion(itemB));
        assertFalse(cart.itemHasPromotion(itemA));
    }


    @Test
    public void shouldPriceItemWithNoPromotion() {
        cart.priceItem(itemA);
        assertTrue(itemA.isCharged());

    }

    @Test
    public void shouldPriceItemWithPromotion() {
        cart.priceItem(itemA,BigDecimal.valueOf(25));
        assertTrue(itemA.isCharged());
    }

    @Test
    public void shouldTotalCharges() {
        cart.getItems().forEach(item -> {
            System.out.println(item.getPrice());
            cart.addToPriceTotal(item.getPrice());
            cart.setItemCharged(item);
        });
        assertThat(cart.getTotal()).isEqualTo(BigDecimal.valueOf(100));
    }

}