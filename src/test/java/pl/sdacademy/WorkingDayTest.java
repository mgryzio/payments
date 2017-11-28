package pl.sdacademy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WorkingDayTest {
    @Test
    @DisplayName("should return true when day is between")
    void shouldReturnTrueWhenDayIsBetween() {
        LocalDate startDate = LocalDate.of(2017, 11, 23);
        LocalDate betweenDate = LocalDate.of(2017, 11, 24);
        LocalDate endDate = LocalDate.of(2017, 11, 25);
        WorkingDay workingDay = new WorkingDay(betweenDate, 10);
        assertTrue(workingDay.betweenDays(startDate, endDate));
    }

    @Test
    @DisplayName("should return false when day is out of range")
    void shouldReturnFalseWhenDayIsNotBetween() {
        LocalDate startDate = LocalDate.of(2017, 11, 23);
        LocalDate betweenDate = LocalDate.of(2017, 11, 22);
        LocalDate endDate = LocalDate.of(2017, 11, 25);
        WorkingDay workingDay = new WorkingDay(betweenDate, 10);
        assertFalse(workingDay.betweenDays(startDate, endDate));
    }

}