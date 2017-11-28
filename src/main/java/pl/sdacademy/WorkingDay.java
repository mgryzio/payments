package pl.sdacademy;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

/**
 * Klasa przechowuje informacje o liczbie godzin przepracowanych danego dnia
 *
 */
@Data
@AllArgsConstructor

public class WorkingDay {

    private LocalDate date;
    private int hours;

    public boolean betweenDays(LocalDate firstDay, LocalDate lastDay) {
        return firstDay.compareTo(date) <= 0
                && date.compareTo(lastDay) <= 0;
    }
}
