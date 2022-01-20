package wright.john.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wright.john.promotions.Promotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private static final Logger log = LoggerFactory.getLogger(Cart.class);

    private List<Item> items = new ArrayList<>();
    private BigDecimal total = new BigDecimal(0);

    private Map<Character, Promotion> promotionMap = new HashMap<>();

    public boolean itemHasPromotion(Item item) {
        Promotion promotion = promotionMap.get(item.getSku());
        return promotion != null;
    }

    public Promotion getPromotionForItem(Item item) {
        return promotionMap.get(item.getSku());
    }

    public void addPromotion(char sku, Promotion promotion) {
        promotionMap.put(sku, promotion);
    }

    public void addItems(Item... newItems) {
        this.items.addAll(List.of(newItems));
    }

    public List<Item> getItems() {
        return items;
    }

    public void priceItem(Item item) {
        item.setPrice(item.getPrice());
        item.setCharged(true);
    }
    public void priceItem(Item item, BigDecimal price) {
        item.setPrice(price);
        item.setCharged(true);
    }

    public boolean allItemsArePriced() {
        return items.stream().filter(p -> !p.getPrice().equals(new BigDecimal(0))).count() == items.size();

    }

    public void addToPriceTotal(BigDecimal amount) {
       total = total.add(amount);
    }

    public void setItemCharged(Item item) {
        item.setCharged(true);
    }

    public BigDecimal getTotal() {
        return total;
    }
}
