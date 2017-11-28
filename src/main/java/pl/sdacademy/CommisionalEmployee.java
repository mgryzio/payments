package pl.sdacademy;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommisionalEmployee extends Employee {

    private BigDecimal salary;
    private BigDecimal commisionRate; // percent
    private List<Invoice> invoices;

    public CommisionalEmployee(String name, String adress, String bankAccountNumber,
                               BigDecimal salary, BigDecimal commisionRate) {
        super(name, adress, bankAccountNumber);
        this.salary = salary;
        this.commisionRate = commisionRate;
        this.invoices = new ArrayList();
    }

    public void addInvoices(Invoice invoice) {
        invoices.add(invoice);
    }

    @Override
    public boolean isPaymentDay(LocalDate date) {
        return isEverySecondFriday(date) || isLastWorkingDayOfYear(date);
    }


    private boolean isLastWorkingDayOfYear(LocalDate date) {
        return date.getMonth() == Month.DECEMBER ? DateUtils.isLastWorkingDayOfMonth(date) : false;
    }

    /**
     * Metoda sprawdza, czy podany dzień jest co drugim piątkiem
     *
     * @param date
     * @return
     */
    private boolean isEverySecondFriday(LocalDate date) {
        LocalDate secondFriday = findSecondFridayOfTheYear(date);
        int secondFridayDayOfYear = secondFriday.getDayOfYear();
        int dayDayOfYear = date.getDayOfYear();
        return (dayDayOfYear - secondFridayDayOfYear) % 14 == 0;
    }

    /**
     * Metoda znajduje drugi piątek w roku
     */
    private LocalDate findSecondFridayOfTheYear(LocalDate day) {
        LocalDate date = LocalDate.of(day.getYear(), 1, 1);
        while (date.getDayOfWeek() != DayOfWeek.FRIDAY) {
            date = date.plusDays(1);
        }
        return date.plusDays(7);
    }

    @Override
    public BigDecimal calculatePayment(LocalDate date) {
        if (isPaymentDay(date)) {
            LocalDate firstDayOfWorkingPeriod = findFirstDayOfWorkingPeriod(date);
            List<Invoice> invoices = findInvoicesWithinPeriod(firstDayOfWorkingPeriod, date);
            return calculatePayment(invoices);
        } else {
            return BigDecimal.ZERO;
        }
    }

    //TODO obliczyć wypłatę na podstawie salary i sumy invoice.value pomnożonej przez commisionRate
    private BigDecimal calculatePayment(List<Invoice> invoices) {
        BigDecimal paymentFromInvoices = BigDecimal.ZERO;
        for (Invoice invoice : invoices) {
            paymentFromInvoices = paymentFromInvoices.add(invoice.getValue());
        }
        paymentFromInvoices = paymentFromInvoices.multiply(commisionRate);
        return paymentFromInvoices.add(salary);
    }

    //TODO znaleść wszystkie invoice wystawione pomiędzy firstDay i lastDay włącznie
    private List<Invoice> findInvoicesWithinPeriod(LocalDate firstDay, LocalDate lastDay) {
        return invoices.stream()
                .filter(invoice -> invoice.betweenDays(firstDay, lastDay))
                .collect(Collectors.toList());
    }

    //TODO znaleść poniedziałek sprzed dwóch tygodni dla podanego dnia (poniedziałek)
    @Override
    public LocalDate findFirstDayOfWorkingPeriod(LocalDate paymentDate) {
        if (isEverySecondFriday(paymentDate)) {
            LocalDate firstDay = paymentDate.minusDays(11);
            if (firstDay.getYear() < paymentDate.getYear()) {
                firstDay = LocalDate.of(paymentDate.getYear(), 1, 1);
            }
            return firstDay;
        } else if (isLastWorkingDayOfYear((paymentDate))){
            while (!isEverySecondFriday(paymentDate)) {
                paymentDate = paymentDate.minusDays(1);
            }
            return paymentDate.plusDays(3);
        } else {
            throw new IllegalArgumentException("Nie można wywoływać dla dnia, który nie jest dniem wypłaty");
        }
    }
}
