package wright.john.promotions;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wright.john.Utils;
import wright.john.model.Cart;
import wright.john.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PercentDiscountPromotion extends Promotion {

    private static final Logger log = LoggerFactory.getLogger(PercentDiscountPromotion.class);

    private BigDecimal percentDiscount;

    public PercentDiscountPromotion(Cart cart, Character sku) {
        super(cart, sku);
    }


    @Override
    public BigDecimal applyPromotion() {
        log.debug("Items in Cart: {}",cart.getItems().size());

        List<Item> applicableItems = applicableItems(cart);
        if (applicableItems.isEmpty()) {
            log.debug("No items eligible for promotion");
            return total;
        }
        log.debug("{} {} items applicatble for discount.",applicableItems.size(),applicableItems.get(0).getName());
        for (Item item : applicableItems) {
            BigDecimal discountPrice = calcPercentage(percentDiscount, item.getPrice());
           item.setCharged(true);
           item.setPrice(discountPrice);
        }
        return Utils.addBigDecimalsFromItems(applicableItems);
//                .stream().map(item -> item.getPrice())//TODO remove;
//                .reduce(BigDecimal.ZERO,BigDecimal::add);

    }

    public  List<Item> applicableItems(Cart cart) {
        return cart.getItems().stream().filter(p -> cart.itemHasPromotion(p))
                .filter(p ->  cart.getPromotionForItem(p) instanceof PercentDiscountPromotion)
                .filter(p -> p.getSku() == sku)
                .filter(p -> !p.isCharged())
                .collect(Collectors.toList());
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
