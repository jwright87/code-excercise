package wright.john.promotions;

import wright.john.model.Cart;
import wright.john.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


public abstract class Promotion {

    protected Cart cart;
    protected Character sku;

     Promotion(Cart cart,Character sku) {
        this.cart = cart;
        this.sku=sku;

    }

    public Character getSku() {
        return sku;
    }

    public void setSku(Character sku) {
        this.sku = sku;
    }

    public abstract BigDecimal applyPromotion();

    protected BigDecimal total = new BigDecimal(0);


}
