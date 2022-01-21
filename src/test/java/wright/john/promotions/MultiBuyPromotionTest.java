package wright.john.promotions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wright.john.model.Cart;
import wright.john.model.Item;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MultiBuyPromotionTest {

    private Cart cart = new Cart();
    private MultiBuyPromotion promotion = PromotionFactory.multiBuyPromotion(cart,'B',3,
            BigDecimal.valueOf(65));

    private Item orange = Item.createItem('B');
    private Item orange2 = Item.createItem('B');
    private Item orange3 = Item.createItem('B');
    private Item orange4 = Item.createItem('B');
    private Item carrot = Item.createItem('D');

    @BeforeEach
    public void setup() {
        cart.addItems(orange,orange2,orange3,orange4,carrot);
    }

    @Test
    public void shouldApplyMultiBuyDiscount() {
        BigDecimal total = promotion.applyPromotion();
        assertThat(total).isEqualTo(BigDecimal.valueOf(65));

        assertTrue(orange.isCharged());
        assertTrue(orange2.isCharged());
        assertTrue(orange3.isCharged());
        assertFalse(orange4.isCharged());
        assertFalse(carrot.isCharged());
    }

    @Test
    public void shouldNotApplyBulkDiscount() {
        cart.getItems().clear();
        cart.addItems(orange,orange2,carrot);
        BigDecimal total = promotion.applyPromotion();
        assertThat(total).isEqualTo(BigDecimal.valueOf(0));
        assertFalse(orange.isCharged());
        assertFalse(orange2.isCharged());
        assertFalse(carrot.isCharged());

    }
}