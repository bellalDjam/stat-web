package com.minagri.stats.core.java;

import com.minagri.stats.core.exception.Exceptions;
import lombok.SneakyThrows;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.minagri.stats.core.java.Strings.EMPTY;
import static java.time.format.DateTimeFormatter.*;

public interface Dates {
    int MINIMAL_YEAR_OF_2_DIGITS = Year.now().minusYears(95L).getValue();

    DateTimeFormatter DDMMYYHHMMSS = new DateTimeFormatterBuilder().appendPattern("ddMM").appendValueReduced(ChronoField.YEAR, 2, 2, MINIMAL_YEAR_OF_2_DIGITS).appendPattern("HHmmss").toFormatter();
    DateTimeFormatter HHMM = DateTimeFormatter.ofPattern("HHmm");
    DateTimeFormatter HHMM_COLON = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter HHMMSS = DateTimeFormatter.ofPattern("HHmmss");
    DateTimeFormatter HHMMSS_COLON = DateTimeFormatter.ofPattern("HH:mm:ss");
    DateTimeFormatter YY = new DateTimeFormatterBuilder().appendValueReduced(ChronoField.YEAR, 2, 2, MINIMAL_YEAR_OF_2_DIGITS).toFormatter();
    DateTimeFormatter YYMMDD = new DateTimeFormatterBuilder().appendValueReduced(ChronoField.YEAR, 2, 2, MINIMAL_YEAR_OF_2_DIGITS).appendPattern("MMdd").toFormatter();
    DateTimeFormatter YYYY = DateTimeFormatter.ofPattern("uuuu");
    DateTimeFormatter YYYYMM = DateTimeFormatter.ofPattern("uuuuMM");
    DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("uuuuMMdd").withResolverStyle(ResolverStyle.STRICT);
    DateTimeFormatter YYYYMMDDHHMM = DateTimeFormatter.ofPattern("uuuuMMddHHmm").withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("uuuuMMddHHmmss").withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter YYYYMMDDHHMMSSMS = DateTimeFormatter.ofPattern("uuuuMMddHHmmss.SSS").withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter YYYYMMDD_SLASHED = DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter YYYYMMDD_DASHED = DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter YYYYMMDD_DASHED_HHMM = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm").withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter YYYYMMDD_DASHED_HHMMSS = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter YYYYMMDD_DASHED_T_HHMMSS = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter YYYYMMDD_DASHED_HHMMSSSSS = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss:SSS").withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter YYYYMMDD_DASHED_HHMM_A_Z = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm a z").withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter MMYYYY = DateTimeFormatter.ofPattern("MMuuuu");
    DateTimeFormatter MMYYYY_SLASHED = DateTimeFormatter.ofPattern("MM/uuuu");
    DateTimeFormatter MMYYYY_DASHED = DateTimeFormatter.ofPattern("MM-uuuu");
    DateTimeFormatter DDMMYYYY = DateTimeFormatter.ofPattern("ddMMuuuu").withResolverStyle(ResolverStyle.STRICT);
    DateTimeFormatter DDMMYYYY_SLASHED = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    DateTimeFormatter DDMMYYYY_DASHED = DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter DDMMYYYY_DASHED_HHMMSS = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter DDMMYYYY_DASHED_HHMM = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter YYMMDDHHMMSS = new DateTimeFormatterBuilder().appendValueReduced(ChronoField.YEAR, 2, 2, MINIMAL_YEAR_OF_2_DIGITS).appendPattern("MMddHHmmss").toFormatter();
    DateTimeFormatter DDMMYY = new DateTimeFormatterBuilder().appendPattern("ddMM").appendValueReduced(ChronoField.YEAR, 2, 2, MINIMAL_YEAR_OF_2_DIGITS).toFormatter();
    DateTimeFormatter DDMM = DateTimeFormatter.ofPattern("ddMM");
    DateTimeFormatter DDMMYY_DASHED = new DateTimeFormatterBuilder().appendPattern("dd-MM-").appendValueReduced(ChronoField.YEAR, 2, 2, MINIMAL_YEAR_OF_2_DIGITS).toFormatter();
    DateTimeFormatter ISO_LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    DateTimeFormatter ISO_LOCAL_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    DateTimeFormatter ISO_LOCAL_TIME = DateTimeFormatter.ISO_LOCAL_TIME;
    DateTimeFormatter ISO_YEAR_MONTH = DateTimeFormatter.ofPattern("uuuu-MM");
    DateTimeFormatter DDD = DateTimeFormatter.ofPattern("DDD");

    static LocalDate toLocalDate(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    static LocalDate toLocalDate(Calendar cal) {
        if (cal == null) {
            return null;
        }
        return toLocalDateTime(cal).toLocalDate();
    }

    static LocalDate toLocalDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }

        return calendar.toGregorianCalendar().toZonedDateTime().toLocalDate();
    }

    static LocalDateTime toLocalDateTime(Date dateToConvert) {
        if (dateToConvert == null) {
            return null;
        }

        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    static LocalDateTime toLocalDateTime(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
    }

    static LocalDateTime toLocalDateTime(Calendar cal) {
        if (cal == null) {
            return null;
        }
        return LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone() == null ? ZoneId.systemDefault() : cal.getTimeZone().toZoneId());
    }

    static LocalDateTime toLocalDateTime(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.atStartOfDay();
    }

    static LocalDateTime toLocalDateTime(LocalTime localTime) {
        if (localTime == null) {
            return null;
        }
        return LocalDateTime.of(LocalDate.of(1970, 1, 1), localTime);
    }

    static Date toDate(LocalDate localDate) {
        return localDate == null ? null : Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    static Date toDate(LocalTime localTime) {
        return localTime == null ? null : Date.from(LocalDateTime.of(LocalDate.of(1970, 1, 1), localTime).atZone(ZoneId.systemDefault()).toInstant());
    }

    static Date toDate(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    static XMLGregorianCalendar toXMLGregorianCalendar(LocalDate localDate) {
        return toXMLGregorianCalendar(toDate(localDate));
    }

    static XMLGregorianCalendar toXMLGregorianCalendar(LocalTime localTime) {
        return toXMLGregorianCalendar(toDate(localTime));
    }

    static XMLGregorianCalendar toXMLGregorianCalendar(LocalDateTime localDateTime) {
        return toXMLGregorianCalendar(toDate(localDateTime));
    }

    @SneakyThrows
    static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }

        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
    }

    static LocalDate safeParseDate(String value, DateTimeFormatter formatter) {
        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    static String safeFormat(LocalTime value, DateTimeFormatter formatter) {
        if (value == null) {
            return EMPTY;
        }
        return formatter.format(Dates.toLocalDateTime(value));
    }

    static String safeFormat(LocalDateTime value, DateTimeFormatter formatter) {
        if (value == null) {
            return EMPTY;
        }
        return formatter.format(value);
    }

    static String safeFormat(YearMonth value, DateTimeFormatter formatter) {
        if (value == null) {
            return EMPTY;
        }
        return formatter.format(value);
    }

    static LocalDate parseDate(String value) {
        if (Strings.isBlank(value)) {
            return null;
        }

        try {
            return LocalDate.parse(value, ISO_DATE);
        } catch (Exception e) {
            //ignore
        }

        try {
            return LocalDate.parse(Strings.left(value, 8), YYYYMMDD);
        } catch (Exception e) {
            //ignore
        }

        try {
            return LocalDate.parse(Strings.left(value, 10), YYYYMMDD_DASHED);
        } catch (Exception e) {
            //ignore
        }

        try {
            return LocalDate.parse(Strings.left(value, 8), DDMMYYYY);
        } catch (Exception e) {
            //ignore
        }

        try {
            return LocalDate.parse(Strings.left(value, 10), DDMMYYYY_DASHED);
        } catch (Exception e) {
            //ignore
        }

        throw new IllegalArgumentException("unable to parse date value " + value);
    }

    static LocalDate safeParseDate(String value) {
        try {
            return parseDate(value);
        } catch (Exception e) {
            return null;
        }
    }

    static LocalDateTime parseDateTime(String value) {
        if (Strings.isBlank(value)) {
            return null;
        }

        if (!value.contains("-") && value.length() == 14) {
            try {
                return LocalDateTime.parse(value, BASIC_ISO_DATE);
            } catch (Exception e) {
                //ignore
            }
        }

        if (value.length() > 19) {
            try {
                return ZonedDateTime.parse(value, ISO_DATE_TIME).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            } catch (Exception e) {
                //ignore
            }
        }

        try {
            return LocalDateTime.parse(value, ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            //ignore
        }

        try {
            return LocalDateTime.parse(Strings.left(value, 19), YYYYMMDD_DASHED_HHMMSS);
        } catch (Exception e) {
            //ignore
        }

        try {
            return LocalDateTime.parse(Strings.left(value, 19), DDMMYYYY_DASHED_HHMMSS);
        } catch (Exception e) {
            //ignore
        }

        try {
            return LocalDateTime.parse(Strings.left(value, 16), YYYYMMDD_DASHED_HHMM);
        } catch (Exception e) {
            //ignore
        }

        throw new IllegalArgumentException("unable to parse datetime value " + value);
    }

    static LocalDateTime parseDateTime(String value, DateTimeFormatter formatter) {
        if (value == null) {
            return null;
        }
        return LocalDateTime.parse(value, formatter);
    }

    static LocalDateTime safeParseDateTime(String value, DateTimeFormatter formatter) {
        try {
            return parseDateTime(value, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    static LocalDateTime safeParseDateTime(String value) {
        try {
            return parseDateTime(value);
        } catch (Exception e) {
            return null;
        }
    }

    static LocalTime parseTime(String value) {
        if (value == null) {
            return null;
        }
        if (value.length() == 4) {
            return LocalTime.parse(value, HHMM);
        }
        if (value.length() == 5) {
            return LocalTime.parse(value, HHMM_COLON);
        }
        if (value.length() == 6) {
            return LocalTime.parse(value, HHMMSS);
        }
        if (value.length() == 8) {
            return LocalTime.parse(value, HHMMSS_COLON);
        }
        throw Exceptions.invalidValueException(value);
    }

    static LocalTime safeParseTime(String value) {
        try {
            return parseTime(value);
        } catch (Exception e) {
            return null;
        }
    }

    static YearMonth parseYearMonth(String value) {
        if (value == null) {
            return null;
        }
        if (value.matches("\\d{4}-\\d{2}")) {
            return YearMonth.parse(value);
        }
        if (value.matches("\\d{6}")) {
            return YearMonth.parse(value.substring(0, 4) + "-" + value.substring(4, 6));
        }
        throw new IllegalArgumentException("cannot parse year month value " + value);
    }

    static YearMonth safeParseYearMonth(String value) {
        try {
            return parseYearMonth(value);
        } catch (Exception e) {
            return null;
        }
    }

    static YearMonth parseYearMonth(String value, DateTimeFormatter formatter) {
        if (Strings.isBlank(value)) {
            return null;
        }
        return YearMonth.parse(value, formatter);
    }

    static YearMonth safeParseYearMonth(String value, DateTimeFormatter formatter) {
        try {
            return parseYearMonth(value, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    static String format(LocalDate date) {
        return format(date, ISO_LOCAL_DATE);
    }

    static String format(LocalDate date, DateTimeFormatter formatter) {
        if (date == null) {
            return EMPTY;
        }
        return formatter.format(date);
    }

    static String format(LocalDateTime dateTime) {
        return format(dateTime, ISO_LOCAL_DATE_TIME);
    }

    static String format(LocalDateTime dateTime, DateTimeFormatter formatter) {
        if (dateTime == null) {
            return EMPTY;
        }
        return formatter.format(dateTime);
    }

    static String format(YearMonth yearMonth) {
        return format(yearMonth, YYYYMM);
    }

    static String format(YearMonth yearMonth, DateTimeFormatter formatter) {
        if (yearMonth == null) {
            return EMPTY;
        }
        return formatter.format(yearMonth);
    }

    static boolean equalTo(LocalDate date1, LocalDate date2) {
        return Objects.equalTo(date1, date2);
    }

    static boolean notEqualTo(LocalDate date1, LocalDate date2) {
        return Objects.notEqualTo(date1, date2);
    }

    static boolean beforeOrEqualTo(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return !date1.isAfter(date2);
    }

    static boolean afterOrEqualTo(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return !date1.isBefore(date2);
    }

    static boolean beforeOrEqualToNow(LocalDate date) {
        return beforeOrEqualTo(date, LocalDate.now());
    }

    static boolean afterOrEqualToNow(LocalDate date) {
        return afterOrEqualTo(date, LocalDate.now());
    }

    static boolean beforeOrEqualTo(LocalDateTime date1, LocalDateTime date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return !date1.isAfter(date2);
    }

    static boolean afterOrEqualTo(LocalDateTime date1, LocalDateTime date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return !date1.isBefore(date2);
    }

    static boolean beforeOrEqualTo(YearMonth yearMonth1, YearMonth yearMonth2) {
        if (yearMonth1 == null || yearMonth2 == null) {
            return false;
        }
        return !yearMonth1.isAfter(yearMonth2);
    }

    static boolean afterOrEqualTo(YearMonth yearMonth1, YearMonth yearMonth2) {
        if (yearMonth1 == null || yearMonth2 == null) {
            return false;
        }
        return !yearMonth1.isBefore(yearMonth2);
    }

    static boolean beforeOrEqualToNow(YearMonth yearMonth) {
        return beforeOrEqualTo(yearMonth, YearMonth.now());
    }

    static boolean afterOrEqualToNow(YearMonth yearMonth) {
        return afterOrEqualTo(yearMonth, YearMonth.now());
    }

    static boolean isWithinPeriod(LocalDate referenceDate, LocalDate periodBegin, LocalDate periodEnd) {
        boolean validBegin = periodBegin == null || afterOrEqualTo(referenceDate, periodBegin);
        boolean validEnd = periodEnd == null || beforeOrEqualTo(referenceDate, periodEnd);
        return validBegin && validEnd;
    }

    static boolean isWithinPeriod(LocalDateTime referenceDate, LocalDateTime periodBegin, LocalDateTime periodEnd) {
        boolean validBegin = periodBegin == null || afterOrEqualTo(referenceDate, periodBegin);
        boolean validEnd = periodEnd == null || beforeOrEqualTo(referenceDate, periodEnd);
        return validBegin && validEnd;
    }

    static LocalDate toLocalDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toLocalDate();
    }

    static LocalTime toLocalTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toLocalTime();
    }

    static LocalDateTime atStartOfDay(LocalDate date) {
        return date != null ? date.atStartOfDay() : null;
    }

    static LocalDateTime atEndOfDay(LocalDate date) {
        return date != null ? date.atTime(23, 59, 59) : null;
    }

    static LocalDate atStartOfMonth(LocalDate date) {
        return date != null ? date.withDayOfMonth(1) : null;
    }

    static LocalDate atEndOfMonth(LocalDate date) {
        return date != null ? date.withDayOfMonth(1).plusMonths(1).minusDays(1) : null;
    }

    static LocalDate atStartOfMonth(YearMonth yearMonth) {
        return yearMonth != null ? yearMonth.atDay(1) : null;
    }

    static LocalDate atEndOfMonth(YearMonth yearMonth) {
        return yearMonth != null ? yearMonth.atEndOfMonth() : null;
    }

    static LocalDateTime atTime(LocalDate date, LocalTime time) {
        return date != null && time != null ? date.atTime(time) : null;
    }

    static LocalDateTime addOptionalDateAndTime(LocalDate localDate, LocalTime localTime) {
        if (localDate == null && localTime == null) {
            return null;
        }
        if (localTime == null) {
            return toLocalDateTime(localDate);
        }
        if (localDate == null) {
            return toLocalDateTime(localTime);
        }
        return LocalDateTime.of(localDate, localTime);
    }

    static int getSemester(LocalDate date) {
        return date.getMonthValue() <= 6 ? 1 : 2;
    }

    static Integer getQuarter(LocalDate date) {
        int monthMinus1 = date.getMonthValue() - 1;
        return monthMinus1 / 3 + 1;
    }

    static Integer getDaysBetween(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return null;
        }

        long daysBetween = ChronoUnit.DAYS.between(date1, date2);
        return Math.abs((int) daysBetween);
    }

    static Integer getDaysBetween(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        if (dateTime1 == null || dateTime2 == null) {
            return null;
        }

        long daysBetween = ChronoUnit.DAYS.between(dateTime1, dateTime2);
        return Math.abs((int) daysBetween);
    }

    static Integer getHoursBetween(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        if (dateTime1 == null || dateTime2 == null) {
            return null;
        }

        long hoursBetween = ChronoUnit.HOURS.between(dateTime1, dateTime2);
        return Math.abs((int) hoursBetween);
    }


    /**
     * Get all public holidays in Belgium for a specific year.
     */
    static Map<PublicHoliday, LocalDate> getPublicHolidays(int year) {
        Map<PublicHoliday, LocalDate> dates = new HashMap<>();
        for (PublicHoliday publicHoliday : PublicHoliday.values()) {
            dates.put(publicHoliday, getDateOfPublicHoliday(publicHoliday, year));
        }
        return dates;
    }

    /**
     * Get the date of a public holiday in Belgium for a specific year.
     */
    static LocalDate getDateOfPublicHoliday(PublicHoliday publicHoliday, int year) {
        switch (publicHoliday) {
            case NEW_YEAR:
                return LocalDate.of(year, 1, 1);
            case EASTER_MONDAY:
                return getEaster(year).plusDays(1L);
            case LABOUR_DAY:
                return LocalDate.of(year, 5, 1);
            case ASCENSION:
                return getEaster(year).plusDays(39L);
            case PENTECOST_MONDAY:
                return getEaster(year).plusDays(50L);
            case NATIONAL_DAY:
                return LocalDate.of(year, 7, 21);
            case ASSUMPTION:
                return LocalDate.of(year, 8, 15);
            case ALL_SAINTS_DAY:
                return LocalDate.of(year, 11, 1);
            case ARMISTICE:
                return LocalDate.of(year, 11, 11);
            case CHRISTMAS:
                return LocalDate.of(year, 12, 25);
            default:
                return null;
        }
    }

    /*
     * Compute the day of the year that Easter falls on. Step names E1 E2 etc.,
     * are direct references to Knuth, Vol 1, p 155. @exception
     * IllegalArgumentexception If the year is before 1582 (since the algorithm
     * only works on the Gregorian calendar).
     */
    static LocalDate getEaster(int year) {
        if (year <= 1582) {
            throw new IllegalArgumentException(
                    "Algorithm invalid before April 1583");
        }
        int golden, century, x, z, d, epact, n;

        golden = (year % 19) + 1; /* E1: metonic cycle */
        century = (year / 100) + 1; /* E2: e.g. 1984 was in 20th C */
        x = (3 * century / 4) - 12; /* E3: leap year correction */
        z = ((8 * century + 5) / 25) - 5; /* E3: sync with moon's orbit */
        d = (5 * year / 4) - x - 10;
        epact = (11 * golden + 20 + z - x) % 30; /* E5: epact */
        if ((epact == 25 && golden > 11) || epact == 24)
            epact++;
        n = 44 - epact;
        n += 30 * (n < 21 ? 1 : 0); /* E6: */
        n += 7 - ((d + n) % 7);
        if (n > 31) /* E7: */
            return new GregorianCalendar(year, 4 - 1, n - 31).toZonedDateTime().toLocalDate(); /* April */
        else
            return new GregorianCalendar(year, 3 - 1, n).toZonedDateTime().toLocalDate(); /* March */
    }

    static String formatDuration(Duration duration) {
        if (duration == null) {
            return null;
        }
        
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        List<String> parts = new ArrayList<>();

        // Days
        if (days > 0) {
            parts.add(days + "d");
        }
        // Hours
        if (hours > 0) {
            parts.add(hours + "h");
        }
        // Minutes
        if (minutes > 0) {
            parts.add(minutes + "m");
        }
        // Seconds (handle zero or leftover seconds)
        if (seconds > 0 || parts.isEmpty()) {
            parts.add(seconds + "s");
        }

        return String.join(" ", parts);
    }

}
