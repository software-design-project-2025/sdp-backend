package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DegreeTest {

    @Test
    void testDegreeCreation() {
        Degree degree = new Degree();
        degree.setDegreeid(1);
        degree.setDegree_name("Computer Science");
        degree.setDegree_type("Bachelor");
        degree.setFacultyid(10);

        assertEquals(1, degree.getDegreeid());
        assertEquals("Computer Science", degree.getDegree_name());
        assertEquals("Bachelor", degree.getDegree_type());
        assertEquals(10, degree.getFacultyid());
    }

    @Test
    void testDegreeWithDifferentTypes() {
        Degree degree = new Degree();
        degree.setDegreeid(2);
        degree.setDegree_name("Electrical Engineering");
        degree.setDegree_type("Master");
        degree.setFacultyid(20);

        assertEquals(2, degree.getDegreeid());
        assertEquals("Electrical Engineering", degree.getDegree_name());
        assertEquals("Master", degree.getDegree_type());
        assertEquals(20, degree.getFacultyid());
    }

    @Test
    void testDegreeWithNullAndEmptyValues() {
        Degree degree = new Degree();
        degree.setDegreeid(3);
        degree.setDegree_name(null);
        degree.setDegree_type("");
        degree.setFacultyid(30);

        assertEquals(3, degree.getDegreeid());
        assertNull(degree.getDegree_name());
        assertEquals("", degree.getDegree_type());
        assertEquals(30, degree.getFacultyid());
    }

    @Test
    void testDegreeEquality() {
        Degree degree1 = new Degree();
        degree1.setDegreeid(1);
        degree1.setDegree_name("Physics");
        degree1.setDegree_type("PhD");
        degree1.setFacultyid(40);

        Degree degree2 = new Degree();
        degree2.setDegreeid(1);
        degree2.setDegree_name("Physics");
        degree2.setDegree_type("PhD");
        degree2.setFacultyid(40);

        assertEquals(degree1.getDegreeid(), degree2.getDegreeid());
        assertEquals(degree1.getDegree_name(), degree2.getDegree_name());
        assertEquals(degree1.getDegree_type(), degree2.getDegree_type());
        assertEquals(degree1.getFacultyid(), degree2.getFacultyid());
    }
}