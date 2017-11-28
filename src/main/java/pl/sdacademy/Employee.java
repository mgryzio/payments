package pl.sdacademy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * http://mgryzio
 * 23.11.2017
 */

@Data
@AllArgsConstructor
public abstract class Employee {

    private String name;
    private String adress;
    private String bankAccountNumber;

    public abstract boolean isPaymentDay(LocalDate day);

    public abstract BigDecimal calculatePayment(LocalDate day);

    public abstract LocalDate findFirstDayOfWorkingPeriod(LocalDate paymentDay);

}
