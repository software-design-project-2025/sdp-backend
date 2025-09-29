package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserCourseIdTest {

    private UserCourseId userCourseId;
    private final String TEST_USER_ID = "user123";
    private final String TEST_COURSE_CODE = "CS101";

    @BeforeEach
    void setUp() {
        userCourseId = new UserCourseId();
        userCourseId.setUserid(TEST_USER_ID);
        userCourseId.setCourseCode(TEST_COURSE_CODE);
    }

    @Test
    void testDefaultConstructor() {
        UserCourseId id = new UserCourseId();
        assertNotNull(id);
        assertNull(id.getUserid());
        assertNull(id.getCourseCode());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(TEST_USER_ID, userCourseId.getUserid());
        assertEquals(TEST_COURSE_CODE, userCourseId.getCourseCode());

        userCourseId.setUserid("newUser");
        userCourseId.setCourseCode("MATH201");

        assertEquals("newUser", userCourseId.getUserid());
        assertEquals("MATH201", userCourseId.getCourseCode());
    }

    @Test
    void testNullAndEmptyValues() {
        userCourseId.setUserid(null);
        userCourseId.setCourseCode("");
        
        assertNull(userCourseId.getUserid());
        assertEquals("", userCourseId.getCourseCode());
    }

    @Test
    void testEquals_SameValues() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid(TEST_USER_ID);
        id1.setCourseCode(TEST_COURSE_CODE);

        UserCourseId id2 = new UserCourseId();
        id2.setUserid(TEST_USER_ID);
        id2.setCourseCode(TEST_COURSE_CODE);

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void testEquals_DifferentUserid() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid(TEST_USER_ID);
        id1.setCourseCode(TEST_COURSE_CODE);

        UserCourseId id2 = new UserCourseId();
        id2.setUserid("differentUser");
        id2.setCourseCode(TEST_COURSE_CODE);

        assertNotEquals(id1, id2);
        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void testEquals_DifferentCourseCode() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid(TEST_USER_ID);
        id1.setCourseCode(TEST_COURSE_CODE);

        UserCourseId id2 = new UserCourseId();
        id2.setUserid(TEST_USER_ID);
        id2.setCourseCode("MATH201");

        assertNotEquals(id1, id2);
        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void testEquals_BothFieldsDifferent() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid(TEST_USER_ID);
        id1.setCourseCode(TEST_COURSE_CODE);

        UserCourseId id2 = new UserCourseId();
        id2.setUserid("differentUser");
        id2.setCourseCode("MATH201");

        assertNotEquals(id1, id2);
        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void testEquals_NullUserid() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid(null);
        id1.setCourseCode(TEST_COURSE_CODE);

        UserCourseId id2 = new UserCourseId();
        id2.setUserid(null);
        id2.setCourseCode(TEST_COURSE_CODE);

        UserCourseId id3 = new UserCourseId();
        id3.setUserid(TEST_USER_ID);
        id3.setCourseCode(TEST_COURSE_CODE);

        assertEquals(id1, id2);
        assertNotEquals(id1, id3);
    }

    @Test
    void testEquals_NullCourseCode() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid(TEST_USER_ID);
        id1.setCourseCode(null);

        UserCourseId id2 = new UserCourseId();
        id2.setUserid(TEST_USER_ID);
        id2.setCourseCode(null);

        UserCourseId id3 = new UserCourseId();
        id3.setUserid(TEST_USER_ID);
        id3.setCourseCode(TEST_COURSE_CODE);

        assertEquals(id1, id2);
        assertNotEquals(id1, id3);
    }

    @Test
    void testEquals_BothFieldsNull() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid(null);
        id1.setCourseCode(null);

        UserCourseId id2 = new UserCourseId();
        id2.setUserid(null);
        id2.setCourseCode(null);

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void testEquals_Reflexive() {
        assertEquals(userCourseId, userCourseId);
    }

    @Test
    void testEquals_Symmetric() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid(TEST_USER_ID);
        id1.setCourseCode(TEST_COURSE_CODE);

        UserCourseId id2 = new UserCourseId();
        id2.setUserid(TEST_USER_ID);
        id2.setCourseCode(TEST_COURSE_CODE);

        assertEquals(id1, id2);
        assertEquals(id2, id1);
    }

    @Test
    void testEquals_Transitive() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid(TEST_USER_ID);
        id1.setCourseCode(TEST_COURSE_CODE);

        UserCourseId id2 = new UserCourseId();
        id2.setUserid(TEST_USER_ID);
        id2.setCourseCode(TEST_COURSE_CODE);

        UserCourseId id3 = new UserCourseId();
        id3.setUserid(TEST_USER_ID);
        id3.setCourseCode(TEST_COURSE_CODE);

        assertEquals(id1, id2);
        assertEquals(id2, id3);
        assertEquals(id1, id3);
    }

    @Test
    void testEquals_NullComparison() {
        assertNotEquals(null, userCourseId);
    }

    @Test
    void testEquals_DifferentType() {
        assertNotEquals(userCourseId, "some string");
        assertNotEquals(userCourseId, new Object());
    }

    @Test
    void testSerializableImplementation() {
        assertTrue(userCourseId instanceof Serializable, 
            "UserCourseId must implement Serializable for JPA composite keys");
    }

    @Test
    void testSerialization() throws IOException, ClassNotFoundException {
        UserCourseId original = new UserCourseId();
        original.setUserid(TEST_USER_ID);
        original.setCourseCode(TEST_COURSE_CODE);

        // Serialize
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(original);
        objectOutputStream.close();

        // Deserialize
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        UserCourseId deserialized = (UserCourseId) objectInputStream.readObject();
        objectInputStream.close();

        assertNotNull(deserialized);
        assertEquals(original.getUserid(), deserialized.getUserid());
        assertEquals(original.getCourseCode(), deserialized.getCourseCode());
        assertEquals(original, deserialized);
        assertEquals(original.hashCode(), deserialized.hashCode());
    }

    @Test
    void testSerializationWithNullFields() throws IOException, ClassNotFoundException {
        UserCourseId original = new UserCourseId();
        original.setUserid(null);
        original.setCourseCode(null);

        // Serialize and deserialize
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(original);
        objectOutputStream.close();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        UserCourseId deserialized = (UserCourseId) objectInputStream.readObject();
        objectInputStream.close();

        assertNotNull(deserialized);
        assertNull(deserialized.getUserid());
        assertNull(deserialized.getCourseCode());
        assertEquals(original, deserialized);
    }

    @Test
    void testHashSetBehavior() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid(TEST_USER_ID);
        id1.setCourseCode(TEST_COURSE_CODE);

        UserCourseId id2 = new UserCourseId();
        id2.setUserid(TEST_USER_ID);
        id2.setCourseCode(TEST_COURSE_CODE);

        Set<UserCourseId> set = new HashSet<>();
        set.add(id1);
        set.add(id2);

        assertEquals(1, set.size(), "Equal objects should not duplicate in HashSet");
        assertTrue(set.contains(id1));
        assertTrue(set.contains(id2));
    }

    @Test
    void testHashMapBehavior() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid(TEST_USER_ID);
        id1.setCourseCode(TEST_COURSE_CODE);

        UserCourseId id2 = new UserCourseId();
        id2.setUserid(TEST_USER_ID);
        id2.setCourseCode(TEST_COURSE_CODE);

        Map<UserCourseId, String> map = new HashMap<>();
        map.put(id1, "First Value");
        map.put(id2, "Second Value");

        assertEquals(1, map.size(), "Equal keys should overwrite in HashMap");
        assertEquals("Second Value", map.get(id1));
        assertEquals("Second Value", map.get(id2));
        assertTrue(map.containsKey(id1));
        assertTrue(map.containsKey(id2));
    }

    @Test
    void testHashMapWithDifferentObjects() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid("user1");
        id1.setCourseCode("CS101");

        UserCourseId id2 = new UserCourseId();
        id2.setUserid("user2");
        id2.setCourseCode("CS101");

        UserCourseId id3 = new UserCourseId();
        id3.setUserid("user1");
        id3.setCourseCode("MATH201");

        Map<UserCourseId, String> map = new HashMap<>();
        map.put(id1, "User1 CS101");
        map.put(id2, "User2 CS101");
        map.put(id3, "User1 MATH201");

        assertEquals(3, map.size());
        assertEquals("User1 CS101", map.get(id1));
        assertEquals("User2 CS101", map.get(id2));
        assertEquals("User1 MATH201", map.get(id3));
    }

    @Test
    void testToStringWithNullFields() {
        UserCourseId id = new UserCourseId();
        id.setUserid(null);
        id.setCourseCode(null);
        
        String stringRepresentation = id.toString();
        assertNotNull(stringRepresentation);
        assertFalse(stringRepresentation.isEmpty());
    }

    @Test
    void testLongValues() {
        String longUserId = "A".repeat(1000);
        String longCourseCode = "B".repeat(1000);

        userCourseId.setUserid(longUserId);
        userCourseId.setCourseCode(longCourseCode);

        assertEquals(longUserId, userCourseId.getUserid());
        assertEquals(longCourseCode, userCourseId.getCourseCode());
    }

    @Test
    void testSpecialCharacters() {
        String specialUserId = "user@123#email.com";
        String specialCourseCode = "CS-101/2024&Spring";

        userCourseId.setUserid(specialUserId);
        userCourseId.setCourseCode(specialCourseCode);

        assertEquals(specialUserId, userCourseId.getUserid());
        assertEquals(specialCourseCode, userCourseId.getCourseCode());
    }

    @Test
    void testWhitespaceValues() {
        userCourseId.setUserid("  user123  ");
        userCourseId.setCourseCode("  CS101  ");

        assertEquals("  user123  ", userCourseId.getUserid());
        assertEquals("  CS101  ", userCourseId.getCourseCode());
    }
}