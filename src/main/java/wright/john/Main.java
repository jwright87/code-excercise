package wright.john;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wright.john.model.Cart;
import wright.john.model.Item;
import wright.john.promotions.MultiBuyPromotion;
import wright.john.promotions.PercentDiscountPromotion;
import wright.john.promotions.PromotionFactory;

import java.math.BigDecimal;
import java.util.Arrays;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        Cart cart = new Cart();

        Arrays.stream(args).map(str -> str.toCharArray()).map(c -> c[0]).forEach(c -> {
            cart.addItems(Item.createItem(c));
        });

        log.info("{} items added to shoppng list", cart.getItems().size());
        cart.getItems().forEach(item -> System.out.println(item.getName()));

        MultiBuyPromotion multiBuyPromotion = PromotionFactory.multiBuyPromotion(cart, 'C', 3, BigDecimal.valueOf(40));
        PercentDiscountPromotion percentDiscountPromotion = PromotionFactory.percentDiscountPromotion(cart, 'A', BigDecimal.valueOf(15));

        cart.addPromotion(multiBuyPromotion);
        cart.addPromotion(percentDiscountPromotion);
    }
}
