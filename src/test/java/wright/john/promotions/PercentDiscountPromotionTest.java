package wright.john.promotions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wright.john.Utils;
import wright.john.model.Cart;
import wright.john.model.Item;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PercentDiscountPromotionTest {


    private Cart cart = new Cart();
    private PercentDiscountPromotion promotion = PromotionFactory.percentDiscountPromotion(cart, 'A',
            BigDecimal.valueOf(50));
    private Item carrot = Item.createItem('A');

    @BeforeEach
    public void setup() {
        cart.addItems(carrot);
    }

    @Test
    public void shouldCalcPercentage() {

        BigDecimal total = promotion.calcPercentage(BigDecimal.valueOf(20), BigDecimal.valueOf(100));
        assertThat(total).isEqualTo(BigDecimal.valueOf(80));

        BigDecimal total2 = promotion.calcPercentage(BigDecimal.valueOf(15), BigDecimal.valueOf(30));
        assertThat(total2).isEqualTo(BigDecimal.valueOf(25.5));
    }

    @Test
    public void shouldApplyPercentDiscount() {
        cart.getItems().clear();
        cart.addItems(Item.createItem('D'), Item.createItem('D'));
        promotion.setSku('D');
        cart.addPromotion(promotion);
        promotion.setPercentDiscount(BigDecimal.valueOf(15));
        BigDecimal total = promotion.applyPromotion();
        assertThat(Utils.stripDecimalZeros(total)).isEqualTo(Utils.stripDecimalZeros(BigDecimal.valueOf(25.5)));
    }
}