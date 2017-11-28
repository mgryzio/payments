package pl.sdacademy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MonthlyEmployeeTest {

    private static final BigDecimal SALARY = new BigDecimal(100);
    private MonthlyEmployee monthlyEmployee;

    @BeforeEach
    void init() {
        monthlyEmployee = new MonthlyEmployee("name", "adress", "number", SALARY);
    }

    @Test
    void shouldReturnTrueWhenItsPaymentDay() {
        assertTrue(monthlyEmployee.isPaymentDay(LocalDate.of(2017, 11, 30)), "Should return true");
        assertFalse(monthlyEmployee.isPaymentDay(LocalDate.of(2017, 4, 30)), "Should return false");
        assertTrue(monthlyEmployee.isPaymentDay(LocalDate.of(2017, 4, 28)), "Should return true");
    }

    @Test
    void shouldCalculatePayment() {
        assertTrue(monthlyEmployee.calculatePayment(LocalDate.of(2017,11,27))
                .compareTo(BigDecimal.ZERO) == 0);
        assertTrue(monthlyEmployee.calculatePayment(LocalDate.of(2017,11,30))
                .compareTo(SALARY) == 0);
    }

    @Test
    void findFirstDayOfWorkingPeriod() {
        assertEquals(monthlyEmployee.findFirstDayOfWorkingPeriod(LocalDate.of(2017, 11, 27)),
                LocalDate.of(2017, 11, 1));
    }
}
