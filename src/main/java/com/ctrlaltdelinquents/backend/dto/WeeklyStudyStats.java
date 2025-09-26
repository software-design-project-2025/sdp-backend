package com.ctrlaltdelinquents.backend.dto;

/**
 * DTO Interface for projecting the results of the weekly study hours query.
 * Spring Data JPA will map the snake_case aliases from the native query
 * to these camelCase getter methods.
 */
public interface WeeklyStudyStats {
    String getWeekLabel();  // Note: weekLabel, not week_label
    Integer getHoursStudied(); // Note: hoursStudied, not hours_studied
}

