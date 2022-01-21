package wright.john.model;

import java.math.BigDecimal;
import java.util.Locale;

public class Item {

    private char sku;
    private String name;
    private String promotion= "No Promotion";
    private BigDecimal price;
    private boolean charged = false;

    Item(String name, char sku, BigDecimal price) {
        this.name = name;
        this.sku = sku;
        this.price = price;
    }

    public static Item createItem(char sku) {
        switch (sku) {
            case 'A':
                return new Item("Carrot", sku, BigDecimal.valueOf(50));
            case 'B':
                return new Item("Banana", sku, BigDecimal.valueOf(30));
            case 'C':
                return new Item("Apple", sku, BigDecimal.valueOf(20));
            case 'D':
                return new Item("Orange", sku, BigDecimal.valueOf(15));
            default:
                throw new RuntimeException(String.format("Sku with %d not found, unable to create item", sku));
        }
    }

    public static Item createItem(String fruit) {
        switch (fruit.toLowerCase()) {
            case "carrot":
                return new Item("Carrot", 'A', BigDecimal.valueOf(50));
            case "banana":
                return new Item("Banana", 'B', BigDecimal.valueOf(30));
            case "apple":
                return new Item("Apple", 'C', BigDecimal.valueOf(20));
            case "orange":
                return new Item("Orange", 'D', BigDecimal.valueOf(15));
            default:
                throw new RuntimeException(String.format("Fruit %s not sold here", fruit));
        }
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSku() {
        return sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isCharged() {
        return charged;
    }

    public void setCharged(boolean charged) {
        this.charged = charged;
    }
}
