package wright.john.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wright.john.Utils;
import wright.john.model.Cart;
import wright.john.model.Item;
import wright.john.promotions.PromotionFactory;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    private static final Logger log = LoggerFactory.getLogger(CheckoutServiceTest.class);

    private Cart cart = new Cart();

    private CheckoutService checkoutService = new CheckoutService();

    private Item carrot = Item.createItem('A');
    private Item banana = Item.createItem('B');
    private Item apple = Item.createItem('C');
    private Item apple2 = Item.createItem('C');
    private Item orange = Item.createItem('D');
    private Item orange2 = Item.createItem('D');

    @BeforeEach
    public void setup() {
        cart.addItems(carrot, banana, apple, apple2, orange, orange2);
        // 50 + 30 + 20 +20 + 15  + 15 = 150
    }

    @Test
    public void shouldCalculateCorrectPriceWithNoPromotions() {
        BigDecimal finalTotal = checkoutService.checkout(cart);

        assertThat(finalTotal).isEqualTo(BigDecimal.valueOf(150));

        cart.getItems().forEach(item -> assertTrue(item.isCharged()));
    }

    @Test
    public void shouldApplyMultiBuyPromotion() {
        cart.addPromotion(PromotionFactory.multiBuyPromotion(cart, orange.getSku(), 2,
                BigDecimal.valueOf(20)));

        BigDecimal finalTotal = checkoutService.checkout(cart);
        assertThat(finalTotal).isEqualTo(BigDecimal.valueOf(140));
        cart.getItems().forEach(item -> assertTrue(item.isCharged()));

    }

    @Test
    public void shouldApplyPercentDiscount() {
        log.debug("items in Cart: {}",cart.getItems());
        cart.addPromotion(PromotionFactory.percentDiscountPromotion(cart, orange.getSku(), BigDecimal.valueOf(25)));
        BigDecimal finalTotal = checkoutService.checkout(cart);
        log.debug("Final Total: {}, Cart Total: {}",finalTotal,cart.getTotal());
        assertThat(finalTotal).isEqualTo(cart.getTotal());
        assertEquals(Utils.stripDecimalZeros(finalTotal), BigDecimal.valueOf(142.5));
        cart.getItems().forEach(item -> assertTrue(item.isCharged()));
        checkoutService.printReceipt(cart);
    }

    @Test
    public void shouldPrintReceiptWithNoPromos() {
     BigDecimal finalTotal = checkoutService.checkout(cart);
     String receipt = checkoutService.printReceipt(cart);
     assertThat(receipt).containsOnlyOnce("Receipt");
     assertThat(receipt).containsOnlyOnce("Carrot");
     assertThat(receipt).containsOnlyOnce("Banana");
     assertThat(receipt).contains("Apple");
     assertThat(receipt).contains("Orange");
     assertThat(cart.getItems().size()).isEqualTo(6);

     assertThat(checkoutService.checkout(cart)).isEqualTo(BigDecimal.valueOf(150));
    }


}