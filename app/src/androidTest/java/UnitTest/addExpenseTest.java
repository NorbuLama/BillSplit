package UnitTest;
import com.example.billsplit.addExpense;

import org.junit.Test;
import static org.junit.Assert.*;
public class addExpenseTest {
    @Test
    public void amountvalid(){
        assertTrue(addExpense.isAmountValid(234));
        assertFalse(addExpense.isAmountValid(-123));
    }
}
