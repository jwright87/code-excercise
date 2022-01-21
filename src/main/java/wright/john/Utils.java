package wright.john;

import wright.john.model.Item;

import java.math.BigDecimal;
import java.util.List;

public class Utils {

    /**
     * Convenience method to sum BigDecimals.
     */
    public static BigDecimal addBigDecimalsFromItems(List<Item> numbers) {
        return numbers.stream().map(item -> item.getPrice())
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    /**
     * Remove extra trailing zeros from BigDecimals to avoid failing an equality check when the number is equivalent.
     */
    public static BigDecimal stripDecimalZeros(BigDecimal value ) {
        if ( value == null )
            return null;


        BigDecimal striped = ( value.scale() > 0 ) ? value.stripTrailingZeros() : value;
        return ( striped.scale() < 0 ) ? striped.setScale( 0 ) : striped;
    }
}
