package com.example.relationshiptracker;

import org.testng.annotations.Test;

import java.time.LocalDate;

public class RelationshipTrackerAppTest {

    @Test
    public void testCalculateDaysBetween_SameDate() {
        RelationshipTrackerApp app = new RelationshipTrackerApp();
        LocalDate startDate = LocalDate.now();

        long days = app.calculateDaysBetween(startDate, startDate);
        assertEquals(0, days);
    }

    @Test
    public void testCalculateDaysBetween_DifferentDates() {
        RelationshipTrackerApp app = new RelationshipTrackerApp();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        long days = app.calculateDaysBetween(startDate, endDate);
        assertEquals(5, days);
    }

    @Test
    public void testCalculateDaysBetween_NullStartDate() {
        RelationshipTrackerApp app = new RelationshipTrackerApp();
        LocalDate endDate = LocalDate.now().plusDays(7);
        assertEquals(-1, app.calculateDaysBetween(null, endDate));
    }

    @Test
    public void testCalculateDaysBetween_NullEndDate() {
        RelationshipTrackerApp app = new RelationshipTrackerApp();
        LocalDate startDate = LocalDate.now();
        assertEquals(-1, app.calculateDaysBetween(startDate, null));
    }
}