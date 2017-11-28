package pl.sdacademy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CommisionalEmployeeTest {

    CommisionalEmployee commisionalEmployee;
    public static final BigDecimal SALARY = new BigDecimal(100);
    public static final BigDecimal INVOICE_VALUE = new BigDecimal(100);
    public static final BigDecimal COMMISION_RATE = new BigDecimal(0.25);

    @BeforeEach
    void init() {
        commisionalEmployee = new CommisionalEmployee("Name", "Adress",
                "number", SALARY, COMMISION_RATE);
    }

    @Test
    void isPaymentDay() {
        assertTrue(commisionalEmployee.isPaymentDay(LocalDate.of(2017, 1, 13)),
                "Should return true, because it's second friday in a year");
        assertTrue(commisionalEmployee.isPaymentDay(LocalDate.of(2017,12,29)),
                "Should return true, because it's last working day in a year");
        assertFalse(commisionalEmployee.isPaymentDay(LocalDate.of(2017,1,11)),
                "It's not the second friday, so it's false");
    }

    @Test
    void calculatePayment() {
        //jedna faktura z przed 6.11
        Invoice invoice = new Invoice(LocalDate.of(2017, 11, 1), INVOICE_VALUE);
        commisionalEmployee.addInvoices(invoice);
        //jedna faktura pomiędzy 6.11 a 17.11
        invoice = new Invoice(LocalDate.of(2017, 11, 6), INVOICE_VALUE);
        commisionalEmployee.addInvoices(invoice);
        //jedna faktura po 17.11
        invoice = new Invoice(LocalDate.of(2017, 11, 24), INVOICE_VALUE);
        commisionalEmployee.addInvoices(invoice);

        BigDecimal payment = commisionalEmployee.calculatePayment(LocalDate.of(2017, 11, 17));
        assertTrue(payment.compareTo(SALARY.add(INVOICE_VALUE.multiply(COMMISION_RATE))) == 0);
    }

    @Test
    void findFirstDayOfWorkingPeriod() {
        //should return 01.01.2016 for given 08.01.2016 (without going back to 2015)
        assertEquals(commisionalEmployee.findFirstDayOfWorkingPeriod(LocalDate.of(2016,1,8)),
                LocalDate.of(2016,1,1));

        //should return 06.11.2017 for given 17.11.2016, from monday to friday
        assertEquals(commisionalEmployee.findFirstDayOfWorkingPeriod(LocalDate.of(2017,11,17)),
                LocalDate.of(2017,11,6));

        //should return 26.12.2016 for given 30.12.2016, from monday to last working day
        assertEquals(commisionalEmployee.findFirstDayOfWorkingPeriod(LocalDate.of(2016,12,30)),
                LocalDate.of(2016,12,26));

        assertThrows(IllegalArgumentException.class,
                () -> commisionalEmployee.findFirstDayOfWorkingPeriod(LocalDate.of(2017, 1, 12)));
    }

}
