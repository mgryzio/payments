package pl.sdacademy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Invoice {

    private LocalDate date;
    private BigDecimal value;

    public boolean betweenDays(LocalDate firstDay, LocalDate lastDay) {
        return firstDay.compareTo(date) <= 0
                && date.compareTo(lastDay) <= 0;
    }

}
