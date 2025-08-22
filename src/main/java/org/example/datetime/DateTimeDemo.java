package org.example.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Locale;

public class DateTimeDemo {
    public static void main(String[] args) {
        // LocalDate
        LocalDate dateNow = LocalDate.now();
        LocalDate dateOf = LocalDate.of(2022, Month.JANUARY, 1);
        LocalDate dateParse = LocalDate.parse("2022-01-01");

        LocalDate newDateWithChronoUnit = dateNow.plus(1, ChronoUnit.YEARS);
        LocalDate newDatePlus = dateNow.plusYears(1).plusMonths(2).plusDays(3);

        LocalDate newOldDate = dateNow
                .minusYears(1).plusYears(1)
                .minusMonths(1).plusMonths(1);

        DayOfWeek dayOfWeek = dateNow.getDayOfWeek();
        int dayOfMonth = dateNow.getDayOfMonth();
        int dayOfYear = dateNow.getDayOfYear();
        Month month = dateNow.getMonth();
        int year = dateNow.getYear();

        boolean leapYear = dateNow.isLeapYear();
        boolean isAfter = dateNow.isAfter(dateOf);
        boolean isBefore = dateNow.isBefore(dateOf);
        boolean isEqual = dateNow.isEqual(dateOf);
        boolean isSupported = dateNow.isSupported(ChronoField.DAY_OF_WEEK);

        // LocalTime
        LocalTime time = LocalTime.now();
        LocalTime timeOf = LocalTime.of(12, 30, 45);
        LocalTime timeParse = LocalTime.parse("12:40:45");
        LocalTime newTimeWithChronoUnit = time.plus(2, ChronoUnit.HOURS);
        LocalTime newTimePlus = time.plusHours(1).plusMinutes(90).plusSeconds(90);

        LocalTime newOldTime = time
                .minusHours(1).plusHours(1)
                .minusMinutes(5).plusMinutes(5)
                .minusSeconds(10).plusSeconds(10);

        ValueRange range = time.range(ChronoField.HOUR_OF_DAY);
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
        int nano = time.getNano();

        boolean after = time.isAfter(timeOf);
        boolean before = time.isBefore(timeOf);
        boolean supported = time.isSupported(ChronoField.HOUR_OF_DAY);

        // LocalDateTime
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime dateTimeOf = LocalDateTime.of(2022, Month.JANUARY, 1, 12, 30, 45);
        LocalDateTime dateTimeParse = LocalDateTime.parse("2022-01-01T12:30:45");
        LocalDateTime newDateTimeWithChronoUnit = dateTime.plus(1, ChronoUnit.YEARS);
        LocalDateTime newDateTimePlus = dateTime.plusYears(1).plusMonths(2).plusDays(3).plusHours(1).plusWeeks(2).plusMinutes(90).plusSeconds(90);

        ValueRange rangeDateTime = dateTime.range(ChronoField.HOUR_OF_DAY);
        int hourDateTime = dateTime.getHour();
        int minuteDateTime = dateTime.getMinute();
        int secondDateTime = dateTime.getSecond();
        int nanoDateTime = dateTime.getNano();
        int dayOfMonthDateTime = dateTime.getDayOfMonth();
        int dayOfYearDateTime = dateTime.getDayOfYear();
        int monthDateTime = dateTime.getMonthValue();
        int yearDateTime = dateTime.getYear();
        int weekOfYear = dateTime.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());

        boolean equalDateTime = dateTime.isEqual(dateTimeOf);
        boolean afterDateTime = dateTime.isAfter(dateTimeOf);
        boolean beforeDateTime = dateTime.isBefore(dateTimeOf);
        boolean supportedDateTime = dateTime.isSupported(ChronoField.HOUR_OF_DAY);

        // ZonedDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        ZonedDateTime zonedDateTimeOf = ZonedDateTime.of(2022, 1, 1, 12, 30, 45, 0, ZoneId.of("Asia/Ho_Chi_Minh"));
        ZonedDateTime zonedDateTimeParse = ZonedDateTime.parse("2022-01-01T12:30:45+07:00[Asia/Ho_Chi_Minh]");




        Period period = Period.of(1, 2, 3);
        Duration duration = Duration.ofDays(2);
        TemporalAdjuster adjuster = TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    }
}
