package wright.john.promotions;

import com.google.common.annotations.VisibleForTesting;
import wright.john.model.Cart;
import wright.john.model.Item;

import java.math.BigDecimal;

public class PercentDiscountPromotion extends Promotion {

    private BigDecimal percentDiscount;

    public PercentDiscountPromotion(Cart cart,Character sku) {
        super(cart,sku);
    }


    @Override
    public BigDecimal applyPromotion() {

        for (Item item : cart.getItems()) {
            total = total.add(calcPercentage(percentDiscount,item.getPrice()));
            cart.setItemCharged(item);
        }
        return total;

    }

    @VisibleForTesting
     BigDecimal calcPercentage(BigDecimal discount,BigDecimal amount) {

        return amount.subtract(amount.multiply(discount).
                divide(BigDecimal.valueOf(100)));
    }

    public BigDecimal getPercentDiscount() {
        return percentDiscount;
    }

     void setPercentDiscount(BigDecimal percentDiscount) {
        this.percentDiscount = percentDiscount;
    }
}
