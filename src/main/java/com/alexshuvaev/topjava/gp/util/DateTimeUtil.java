package com.alexshuvaev.topjava.gp.util;

import com.alexshuvaev.topjava.gp.util.exception.ForbiddenException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DateTimeUtil {
    /**
     * THRESHOLD_TIME need for control of Users voting time. After that threshold voting is not allowed.
     */
    public static final LocalTime THRESHOLD_TIME = LocalTime.of(23,0,0);

    public static final DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");

    public static List<LocalDate> checkAndInitStartDateEndDate(LocalDate startDate, LocalDate endDate) {
        startDate = startDate == null && endDate == null ? LocalDate.now() :
                startDate == null ? LocalDate.of(2000, 1, 1) : startDate;

        endDate = endDate == null ? LocalDate.now() : endDate;
        if (startDate.isAfter(endDate)) throw new ForbiddenException("startDate can't be after endDate.");
        return Stream.of(startDate, endDate).collect(Collectors.toList());
    }
}
