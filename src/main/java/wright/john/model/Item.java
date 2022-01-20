package wright.john.model;

import java.math.BigDecimal;

public class Item {

    private char sku;
    private String name;
    private BigDecimal price;
    private boolean charged = false;

    Item(String name,char sku, BigDecimal price) {
        this.name=name;
        this.sku = sku;
        this.price = price;
    }

    public static Item createItem(char sku) {
        switch (sku) {
            case 'A':
                return new Item("Carrot",sku, BigDecimal.valueOf(50));
            case 'B':
                return new Item("Banana",sku, BigDecimal.valueOf(30));
            case 'C':
                return new Item("Apple",sku, BigDecimal.valueOf(20));
            case 'D':
                return new Item("Orange",sku, BigDecimal.valueOf(15));
            default:
                throw new RuntimeException(String.format("Sku with %c not found, unable to create item",sku));
        }
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
