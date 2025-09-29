package com.ctrlaltdelinquents.backend.dto;

/**
 * Data Transfer Object (DTO) interface to hold the results of the progress statistics query.
 * Spring Data JPA will automatically map the aliased columns from the native query
 * to the getter methods defined in this interface.
 */
public interface ProgressStats {
    Integer getTotalHoursStudied();
    Integer getTopicsCompleted();
    Integer getCurrentStreakDays();
    Integer getStudySessionsAttended();
}