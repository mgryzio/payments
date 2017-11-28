package pl.sdacademy;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class HourlyEmployee extends Employee {

    private static final int REGULAR_HOURS = 8;
    private static final BigDecimal OVERTIME_RATE = new BigDecimal("1.5");

    private BigDecimal hourlyRate;
    private List<WorkingDay> workingDays;

    public HourlyEmployee(String name, String adress, String bankAccountNumber, BigDecimal hourlyRate) {
        super(name, adress, bankAccountNumber);
        this.hourlyRate = hourlyRate;
        workingDays = new LinkedList<>();
    }

    public void addWorkingDay(WorkingDay workingDay) {
        workingDays.add(workingDay);
    }

    @Override
    public boolean isPaymentDay(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.FRIDAY);
    }

    @Override
    public BigDecimal calculatePayment(LocalDate date) {
        if (!isPaymentDay(date)) {
            return BigDecimal.ZERO;
        } else {
            LocalDate firsDayOfWeek = findFirstDayOfWorkingPeriod(date);
            List<WorkingDay> workingDays = findWorkingDays(firsDayOfWeek, date);
            return calculatePayment(workingDays);
        }
    }

    @Override
    public LocalDate findFirstDayOfWorkingPeriod(LocalDate paymentDay) {
        return paymentDay.minusDays(4);
    }

    private BigDecimal calculatePayment(List<WorkingDay> workingDays) {
        BigDecimal payment = BigDecimal.ZERO;

        for (WorkingDay workingDay : workingDays) {
            payment = payment.add(calculatePayment(workingDay));
        }
        return payment;
    }

    private BigDecimal calculatePayment(WorkingDay workingDay) {
        if (workingDay.getHours() <= REGULAR_HOURS) {
            return hourlyRate.multiply(new BigDecimal(workingDay.getHours()));
        } else {
            BigDecimal regularPayment
                    = hourlyRate.multiply(new BigDecimal(REGULAR_HOURS));
            BigDecimal overtimeRate
                    = hourlyRate.multiply(OVERTIME_RATE);
            BigDecimal overtimePayment
                    = overtimeRate.multiply(new BigDecimal(workingDay.getHours() - REGULAR_HOURS));
            return regularPayment.add(overtimePayment);
        }
    }

    private List<WorkingDay> findWorkingDays(LocalDate firstDayOfWeek, LocalDate lastDay) {
/*        List<WorkingDay> foundedDays = new ArrayList<>();

        for (WorkingDay workingDay : workingDays) {
            LocalDate workingDate = workingDay.getDate();
            if (firstDayOfWeek.compareTo(workingDate) <= 0
                    && workingDate.compareTo(lastDay) <= 0) {
                foundedDays.add(workingDay);
            }
        }
        return foundedDays;
*/
        return workingDays.stream()
                .filter(workingDay -> workingDay.betweenDays(firstDayOfWeek, lastDay))
                .collect(Collectors.toList());
    }

}
