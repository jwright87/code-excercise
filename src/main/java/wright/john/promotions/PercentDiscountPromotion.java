package wright.john.promotions;

import wright.john.model.Cart;
import wright.john.model.Item;

import java.math.BigDecimal;
import java.util.List;

public class PercentDiscountPromotion extends Promotion {

    private BigDecimal percentDiscount;

    public PercentDiscountPromotion(Cart cart,Character sku) {
        super(cart,sku);
    }


    @Override
    public BigDecimal applyPromotion() {

        for (Item item : cart.getItems()) {
            total.add(item.getPrice().multiply(percentDiscount));
            cart.setItemCharged(item);
        }
        return total;

    }

    public BigDecimal getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(BigDecimal percentDiscount) {
        this.percentDiscount = percentDiscount;
    }
}
