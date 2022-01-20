package wright.john.promotions;

import wright.john.model.Cart;
import wright.john.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class MultiBuyPromotion extends Promotion {

    private int bulkBuyCount;
    private BigDecimal fixedPrice;

    public MultiBuyPromotion(Cart cart, Character sku) {
        super(cart, sku);
    }


    public BigDecimal applyPromotion() {

        List<Item> items = findItemsWithPromotionSku();

        if (items.size() < bulkBuyCount) {
            priceNonPromotionalItems(items);
            return total;
        } else {
            applyMultiBuy(items);

            List<Item> extraItems = items.subList(bulkBuyCount, items.size() - 1);
            priceNonPromotionalItems(extraItems);
        }

        return total;

    }

    public void priceNonPromotionalItems(List<Item> items) {
        items.forEach(item -> cart.priceItem(item));
    }

    private List<Item> findItemsWithPromotionSku() {
        return cart.getItems().stream().filter(p -> p.getSku() == sku)
                .collect(Collectors.toList());
    }

    private void applyMultiBuy(List<Item> items) {
        items.subList(0, bulkBuyCount).forEach(item -> {
            cart.priceItem(item, BigDecimal.valueOf(0));
            cart.addToPriceTotal(fixedPrice);
            item.setCharged(true);
        });
    }

    private void setAllItemsAsCharged(Cart cart, List<Item> items) {
        cart.getItems().stream().forEach(i -> i.setCharged(true));
    }

    public int getBulkBuyCount() {
        return bulkBuyCount;
    }

    public void setBulkBuyCount(int bulkBuyCount) {
        this.bulkBuyCount = bulkBuyCount;
    }
}
