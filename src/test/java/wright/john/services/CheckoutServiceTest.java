package wright.john.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wright.john.Utils;
import wright.john.model.Cart;
import wright.john.model.Item;
import wright.john.promotions.MultiBuyPromotion;
import wright.john.promotions.PercentDiscountPromotion;
import wright.john.promotions.PromotionFactory;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutServiceTest {

    private static final Logger log = LoggerFactory.getLogger(CheckoutServiceTest.class);

    private Cart cart = new Cart();

    private Item extraOrange;
    private Item extraApple;
    private CheckoutService checkoutService = new CheckoutService();

    private Item carrot = Item.createItem('A');
    private Item banana = Item.createItem('B');
    private Item apple = Item.createItem('C');
    private Item apple2 = Item.createItem("Apple");
    private Item orange = Item.createItem('D');
    private Item orange2 = Item.createItem('D');

    @BeforeEach
    public void setup() {
        cart.addItems(carrot, banana, apple, apple2, orange, orange2);
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
        log.debug("items in Cart: {}", cart.getItems());
        cart.addPromotion(PromotionFactory.percentDiscountPromotion(cart, orange.getSku(), BigDecimal.valueOf(25)));
        BigDecimal finalTotal = checkoutService.checkout(cart);
        log.debug("Final Total: {}, Cart Total: {}", finalTotal, cart.getTotal());
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

    private MultiBuyPromotion multiBuyPromotion = PromotionFactory.multiBuyPromotion(cart, orange.getSku(), 2,
            BigDecimal.valueOf(20));
    private PercentDiscountPromotion discountPromotion = PromotionFactory.percentDiscountPromotion(cart, apple.getSku(),
            BigDecimal.valueOf(30));


    @Test
    public void shouldDetermineWhetherToApplyPercentDiscountPromotion() {
        setupPromotionTests(multiBuyPromotion, discountPromotion);
        assertThat(checkoutService.determineIfPromotionApplied(cart, apple, discountPromotion)).isEqualTo("PercentDiscountPromotion");
        assertThat(checkoutService.determineIfPromotionApplied(cart, extraApple, discountPromotion)).isEqualTo("PercentDiscountPromotion");
    }

    @Test
    public void shouldDetermineWhetherToApplyMultiBuyPromotion() {
        setupPromotionTests(multiBuyPromotion, discountPromotion);
        assertThat(checkoutService.determineIfPromotionApplied(cart, orange, multiBuyPromotion)).isEqualTo("MultiBuyPromotion");
        assertThat(checkoutService.determineIfPromotionApplied(cart, extraOrange, multiBuyPromotion)).isEqualTo("No Promotion");
    }

    private void setupPromotionTests(MultiBuyPromotion multiBuyPromotion, PercentDiscountPromotion discountPromotion) {

        discountPromotion.setPercentDiscount(BigDecimal.valueOf(35));
        cart.addPromotion(multiBuyPromotion);
        cart.addPromotion(discountPromotion);
        extraOrange = Item.createItem("Orange");
        extraApple = Item.createItem("Apple");
        cart.getItems().clear();
        cart.addItems(orange, orange2, extraOrange, apple, extraApple);
        multiBuyPromotion.applyPromotion();
        discountPromotion.applyPromotion();

    }

}