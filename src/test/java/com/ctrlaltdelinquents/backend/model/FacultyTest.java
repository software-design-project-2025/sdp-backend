package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FacultyTest {

    @Test
    void testFacultyCreation() {
        Faculty faculty = new Faculty();
        faculty.setFacultyid(1);
        faculty.setFaculty_name("Engineering");

        assertEquals(1, faculty.getFacultyid());
        assertEquals("Engineering", faculty.getFaculty_name());
    }

    @Test
    void testFacultyWithDifferentValues() {
        Faculty faculty = new Faculty();
        faculty.setFacultyid(2);
        faculty.setFaculty_name("Science");

        assertEquals(2, faculty.getFacultyid());
        assertEquals("Science", faculty.getFaculty_name());
    }

    @Test
    void testFacultyWithNullName() {
        Faculty faculty = new Faculty();
        faculty.setFacultyid(3);
        faculty.setFaculty_name(null);

        assertEquals(3, faculty.getFacultyid());
        assertNull(faculty.getFaculty_name());
    }

    @Test
    void testFacultyWithEmptyName() {
        Faculty faculty = new Faculty();
        faculty.setFacultyid(4);
        faculty.setFaculty_name("");

        assertEquals(4, faculty.getFacultyid());
        assertEquals("", faculty.getFaculty_name());
    }

    @Test
    void testFacultyBoundaryValues() {
        Faculty faculty = new Faculty();
        
        // Test with minimum integer value
        faculty.setFacultyid(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, faculty.getFacultyid());
        
        // Test with maximum integer value
        faculty.setFacultyid(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, faculty.getFacultyid());
        
        // Test with long faculty name
        String longName = "A".repeat(100);
        faculty.setFaculty_name(longName);
        assertEquals(longName, faculty.getFaculty_name());
    }
}