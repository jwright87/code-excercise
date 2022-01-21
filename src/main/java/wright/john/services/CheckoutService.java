package wright.john.services;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wright.john.model.Cart;
import wright.john.model.Item;
import wright.john.promotions.Promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class CheckoutService {

    private static final Logger log = LoggerFactory.getLogger(CheckoutService.class);

    public BigDecimal checkout(Cart cart) {
        BigDecimal finalTotal = BigDecimal.ZERO;

        for (Item item : cart.getItems()) {
            log.debug("Item {} priced at  {}",item.getName(),item.getPrice());
            Promotion promotion = cart.getPromotionForItem(item);
            if (promotion == null) {
                item.setCharged(true);
                log.debug("{} charged at {}", item.getName(), item.getPrice());
            }else{
                promotion.applyPromotion();
            }
        }

        return cart.getTotal();
    }


    public String printReceipt(Cart cart) {
        if (cart.getItems().stream().filter(i -> !i.isCharged()).count() != 0) {
            cart.getItems().forEach(i -> System.out.printf("%s : charge %b%n", i, i.isCharged()));
            throw new RuntimeException("Items still not charged - unable to produce receipt.");
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("Receipt\n");
            builder.append(Strings.repeat("-", 30));
            builder.append("\n");
            cart.getItems().forEach(item -> {
                Promotion promotion = cart.getPromotionForItem(item);
                if (promotion != null) {

                }
                String hasPromotion = promotion != null ? promotion.getClass().getSimpleName() : " No Promotion";
                builder.append(String.format("%s  | %.2f |%s |\n", item.getName(), item.getPrice(), hasPromotion));
                builder.append(Strings.repeat("-", 30));
                builder.append("\n");

            });
            builder.append(String.format("Price Total: %s\n", NumberFormat.getCurrencyInstance().format(cart.getTotal())));
            builder.append(Strings.repeat("_", 30));
            builder.append("\n");
            String receipt = builder.toString();
            System.out.println(receipt);
            return receipt;
        }
    }
}

