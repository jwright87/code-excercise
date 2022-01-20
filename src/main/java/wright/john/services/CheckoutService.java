package wright.john.services;

import wright.john.model.Cart;
import wright.john.model.Item;
import wright.john.promotions.Promotion;

import java.math.BigDecimal;

public class CheckoutService {


    public BigDecimal checkout(Cart cart) {
        BigDecimal finalTotal = BigDecimal.ZERO;

        for (Item item : cart.getItems()) {
             Promotion promotion = cart.getPromotionForItem(item);
            if (promotion != null) {
                finalTotal = finalTotal.add(promotion.applyPromotion());
                break;
            } else {
                finalTotal = finalTotal.add(item.getPrice());
                item.setCharged(true);
            }
        }
        return finalTotal;
    }
}

