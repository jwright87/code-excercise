package wright.john.promotions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wright.john.model.Cart;
import wright.john.model.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class MultiBuyPromotion extends Promotion {
    private static final Logger log = LoggerFactory.getLogger(MultiBuyPromotion.class);

    private int bulkBuyCount;
    private BigDecimal bulkBuyCost;

    public MultiBuyPromotion(Cart cart, Character sku) {
        super(cart, sku);
    }

    void setBulkBuyCost(BigDecimal bulkBuyCost) {
        this.bulkBuyCost = bulkBuyCost;
    }

    public BigDecimal applyPromotion() {

        List<Item> items = findItemsWithPromotionSku();
        if (items.size() == bulkBuyCount) {
            log.debug("Applying bulk discount");
            applyMultiBuy(items);
        } else {
            log.debug("Unable to apply bulk discount, only %d items in cart," +
                    "%d required", items.size(), bulkBuyCount);
        }
return total;
    }


    private List<Item> findItemsWithPromotionSku() {
        List<Item> items = cart.getItems().stream().filter(p -> p.getSku() == sku)
                .collect(Collectors.toList());
        if (items.size() > bulkBuyCount) {
            return items.subList(0, bulkBuyCount);
        } else {
            return items;
        }
    }

    private void applyMultiBuy(List<Item> items) {
        items.forEach(item -> {
            item.setCharged(true);
            BigDecimal pricePerItem = bulkBuyCost.divide(BigDecimal.valueOf(bulkBuyCount),RoundingMode.HALF_UP);
            item.setPrice(pricePerItem);
            item.setPromotion(this.getClass().getSimpleName());
        });
        total = total.add(bulkBuyCost);
    }

    void setBulkBuyCount(int bulkBuyCount) {
        this.bulkBuyCount = bulkBuyCount;
    }
}
