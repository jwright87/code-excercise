package wright.john;

import org.junit.jupiter.api.Test;
import wright.john.model.Item;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UtilsTest {

    @Test
    public void shouldSumBigDecimals() {
        List<Item> items =List.of(Item.createItem('A'),Item.createItem('B'),Item.createItem('C'));
        items.get(0).setPrice(BigDecimal.valueOf(12.5));

        BigDecimal result = Utils.addBigDecimalsFromItems(items);

        assertThat(result).isEqualTo(BigDecimal.valueOf(62.5));
    }

    @Test
    public void shouldGracefullyHandleNullValuesForSummingNumbers() {
        assertThat(Utils.stripDecimalZeros(null)).isNull();
    }
    @Test
    public void shouldGracefullyHandleNullValuesForTrailingZeros() {
        assertThat(Utils.stripDecimalZeros(null)).isNull();
    }

    @Test
    public void shouldStripTrailingZeros() {
        BigDecimal trailZeroDecimal = Utils.stripDecimalZeros(BigDecimal.valueOf(125.00));
        BigDecimal comparison = BigDecimal.valueOf(125);
        assertThat(trailZeroDecimal).toString().equals("125");
        assertThat(trailZeroDecimal).isEqualTo(comparison);
    }
}