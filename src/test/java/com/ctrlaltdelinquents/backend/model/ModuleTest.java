package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ModuleTest {

    @Test
    void testModuleCreation() {
        Module module = new Module();
        module.setCourseCode("CS101");
        module.setCourseName("Introduction to Computer Science");
        module.setFacultyId(1);

        assertEquals("CS101", module.getCourseCode());
        assertEquals("Introduction to Computer Science", module.getCourseName());
        assertEquals(1, module.getFacultyId());
    }

    @Test
    void testModuleWithDifferentCourseCodes() {
        Module module1 = new Module();
        module1.setCourseCode("MATH201");
        module1.setCourseName("Advanced Mathematics");
        module1.setFacultyId(2);

        Module module2 = new Module();
        module2.setCourseCode("PHY301");
        module2.setCourseName("Physics");
        module2.setFacultyId(3);

        assertEquals("MATH201", module1.getCourseCode());
        assertEquals("Advanced Mathematics", module1.getCourseName());
        assertEquals(2, module1.getFacultyId());
        
        assertEquals("PHY301", module2.getCourseCode());
        assertEquals("Physics", module2.getCourseName());
        assertEquals(3, module2.getFacultyId());
    }

    @Test
    void testModuleWithNullAndEmptyValues() {
        Module module = new Module();
        module.setCourseCode(null);
        module.setCourseName("");
        module.setFacultyId(0);

        assertNull(module.getCourseCode());
        assertEquals("", module.getCourseName());
        assertEquals(0, module.getFacultyId());
    }

    @Test
    void testModuleCourseCodeFormat() {
        Module module = new Module();
        
        // Test with alphanumeric course code
        module.setCourseCode("CS-101-A");
        assertEquals("CS-101-A", module.getCourseCode());
        
        // Test with numeric course code
        module.setCourseCode("12345");
        assertEquals("12345", module.getCourseCode());
        
        // Test with special characters
        module.setCourseCode("CS@2024");
        assertEquals("CS@2024", module.getCourseCode());
    }

    @Test
    void testModuleFacultyAssociation() {
        Module module = new Module();
        module.setCourseCode("ENG401");
        module.setCourseName("Engineering Design");
        module.setFacultyId(10);

        // Test that faculty ID can be used to associate with Faculty entity
        assertEquals(10, module.getFacultyId());
        
        // Verify all fields are properly set
        assertEquals("ENG401", module.getCourseCode());
        assertEquals("Engineering Design", module.getCourseName());
    }
}