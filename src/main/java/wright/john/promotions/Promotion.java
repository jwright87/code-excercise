package wright.john.promotions;

import wright.john.model.Cart;

import java.math.BigDecimal;


public abstract class Promotion {

    protected Cart cart;
    protected Character sku;

     Promotion(Cart cart,Character sku) {
        this.cart = cart;
        this.sku=sku;

    }


    public abstract BigDecimal applyPromotion();

    protected BigDecimal total = new BigDecimal(0);


}
