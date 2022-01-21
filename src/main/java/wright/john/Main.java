package wright.john;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wright.john.model.Cart;
import wright.john.model.Item;
import wright.john.promotions.MultiBuyPromotion;
import wright.john.promotions.PercentDiscountPromotion;
import wright.john.promotions.PromotionFactory;
import wright.john.services.CheckoutService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private CheckoutService checkoutService = new CheckoutService();
    private Cart cart = new Cart();
    private MultiBuyPromotion multiBuyPromotion;
    private PercentDiscountPromotion percentDiscountPromotion;
private List<Item> items;
    public static void main(String[] args) {
        new Main().processPayment(args);
    }

    public void processPayment(String[] args) {
        createFruitItemsFromProgramInput(args);

        addAndPrintItemsAddedToCart(items);

        initialisePromotions();

        addPromotionsToCart();

        checkoutItems();
    }

    private void createFruitItemsFromProgramInput(String[] args) {
        items = Arrays.stream(args).map(fruit -> Item.createItem(fruit)).collect(Collectors.toList());
    }

    private void checkoutItems() {
        checkoutService.checkout(cart);
        checkoutService.printReceipt(cart);
    }

    private void addPromotionsToCart() {
        cart.addPromotion(multiBuyPromotion);
        cart.addPromotion(percentDiscountPromotion);
    }

    private void initialisePromotions() {
        multiBuyPromotion = PromotionFactory.multiBuyPromotion(cart, 'C', 3, BigDecimal.valueOf(40));
        percentDiscountPromotion = PromotionFactory.percentDiscountPromotion(cart, 'A', BigDecimal.valueOf(15));
    }

    private void addAndPrintItemsAddedToCart(List<Item> items) {
        cart.addItems(items);
        log.info("{} items added to shopping list", cart.getItems().size());
    }
}
