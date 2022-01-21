package wright.john.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wright.john.promotions.Promotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cart {

    private static final Logger log = LoggerFactory.getLogger(Cart.class);

    private List<Item> items = new ArrayList<>();
    private Map<Character, Promotion> promotionMap = new HashMap<>();

    public boolean itemHasPromotion(Item item) {
        Promotion promotion = promotionMap.get(item.getSku());
        return promotion != null;
    }

    public Promotion getPromotionForItem(Item item) {
        return promotionMap.get(item.getSku());
    }

    public void addPromotion( Promotion promotion) {
        promotionMap.put(promotion.getSku(), promotion);
    }

    public void addItems(Item... newItems) {
        this.items.addAll(List.of(newItems));
    }

    public void addItems(List<Item> newItems) {
        this.items.addAll(newItems);
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

    public BigDecimal getTotal() {
        List<BigDecimal> prices =  items.stream().map(item -> {
            return item.getPrice();
        }).collect(Collectors.toList());

        return prices.stream().reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
