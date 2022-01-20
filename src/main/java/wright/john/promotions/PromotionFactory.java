package wright.john.promotions;

import wright.john.model.Cart;

import java.math.BigDecimal;

public abstract class PromotionFactory {

    public static PercentDiscountPromotion percentDiscountPromotion(Cart cart,Character sku,BigDecimal percentDiscount) {
        PercentDiscountPromotion promotion=  new PercentDiscountPromotion(cart,sku);
        promotion.setPercentDiscount(percentDiscount);
        return promotion;

    }

    public static MultiBuyPromotion multiBuyPromotion(Cart cart, Character sku, int bulBuyCount,BigDecimal bulkBuyCost) {
        MultiBuyPromotion promotion = new MultiBuyPromotion(cart,sku);
        promotion.setBulkBuyCount(bulBuyCount);
        promotion.setBulkBuyCost(bulkBuyCost);
        return promotion;
    }
}
