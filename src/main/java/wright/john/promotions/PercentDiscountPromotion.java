package wright.john.promotions;

import com.google.common.annotations.VisibleForTesting;
import wright.john.model.Cart;
import wright.john.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PercentDiscountPromotion extends Promotion {

    private BigDecimal percentDiscount;
    private BigDecimal total = BigDecimal.ZERO;

    public PercentDiscountPromotion(Cart cart, Character sku) {
        super(cart, sku);
    }


    @Override
    public BigDecimal applyPromotion() {
        List<Item> applicableItems = cart.getItems().stream()
                .filter(p -> p.getSku() == sku)
//                .filter(p -> p.isCharged())
                .collect(Collectors.toList());
        for (Item item : applicableItems) {
            if (item.isCharged()) {
                continue;
            }
            total = total.add(calcPercentage(percentDiscount, item.getPrice()));
           item.setCharged(true);
        }
        return total;

    }

    @VisibleForTesting
    BigDecimal calcPercentage(BigDecimal discount, BigDecimal amount) {

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
