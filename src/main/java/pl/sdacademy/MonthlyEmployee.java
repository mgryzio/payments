package pl.sdacademy;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MonthlyEmployee extends Employee {

    private BigDecimal salary;


    public MonthlyEmployee(String name, String adress, String bankAccountNumber, BigDecimal salary) {
        super(name, adress, bankAccountNumber);
        this.salary = salary;
    }

    @Override
    public boolean isPaymentDay(LocalDate day) {
        return DateUtils.isLastWorkingDayOfMonth(day);
    }

    @Override
    public BigDecimal calculatePayment(LocalDate day) {
        return isPaymentDay(day) ? salary : BigDecimal.ZERO;
    }

    @Override
    public LocalDate findFirstDayOfWorkingPeriod(LocalDate paymentDay) {
        return LocalDate.of(paymentDay.getYear(), paymentDay.getMonth(), 1);
    }
}
