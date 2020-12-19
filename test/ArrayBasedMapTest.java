
import java.util.*;
import java.util.Map.Entry;
import static org.junit.Assert.*;
import org.junit.Test;






public class ArrayBasedMapTest {

    protected final boolean supportsPut;
    protected final boolean supportsRemove;
    protected final boolean supportsClear;
    protected final boolean allowsNullKeys;
    protected final boolean allowsNullValues;
    protected final boolean supportsIteratorRemove;

    /**
     * Creates a new, empty instance of the class under test.
     *
     * @return a new, empty map instance.
     * @throws UnsupportedOperationException if it's not possible to make an
     *          empty instance of the class under test.
     */
    protected Map<String, Student> makeEmptyMap() {
        return new ArrayBasedMap<>();
    }

    /**
     * Creates a new, non-empty instance of the class under test.
     *
     * @return a new, non-empty map instance.
     * @throws UnsupportedOperationException if it's not possible to make a
     *          non-empty instance of the class under test.
     */
    protected Map<String, Student> makePopulatedMap() {
        return new ArrayBasedMap<String, Student>() { {
            for (int i = 0; i < 1_000; i++) {
                put(String.valueOf(i), new Student("Student " + i));
            }
        }};
    }

    /**
     * Creates a new key that is not expected to be found
     * in {@link #makePopulatedMap()}.
     *
     * @return a key.
     * @throws UnsupportedOperationException if it's not possible to make a key
     *          that will not be found in the map.
     */
    protected String getKeyNotInPopulatedMap() {
        return "keyNotInMap";
    }

    /**
     * Creates a new value that is not expected to be found
     * in {@link #makePopulatedMap()}.
     *
     * @return a value.
     * @throws UnsupportedOperationException if it's not possible to make a value
     *          that will not be found in the map.
     */
    protected  Student getValueNotInPopulatedMap() {
        return new Student("notInMap");
    }

    /**
     * Constructor that assigns {@code supportsIteratorRemove} the same value as
     * {@code supportsRemove}.
     */
    public ArrayBasedMapTest() {
        this(false, false, true, true, true, true);
    }

    /**
     * Constructor with an explicit {@code supportsIteratorRemove} parameter.
     */
    protected ArrayBasedMapTest(
            boolean allowsNullKeys,
            boolean allowsNullValues,
            boolean supportsPut,
            boolean supportsRemove,
            boolean supportsClear,
            boolean supportsIteratorRemove) {
        this.supportsPut = supportsPut;
        this.supportsRemove = supportsRemove;
        this.supportsClear = supportsClear;
        this.allowsNullKeys = allowsNullKeys;
        this.allowsNullValues = allowsNullValues;
        this.supportsIteratorRemove = supportsIteratorRemove;
    }

    /**
     * Used by tests that require a map, but don't care whether it's
     * populated or not.
     *
     * @return a new map instance.
     */
    protected Map<String, Student> makeEitherMap() {
        try {
            return makePopulatedMap();
        } catch (UnsupportedOperationException e) {
            return makeEmptyMap();
        }
    }

    protected final boolean supportsValuesHashCode(Map<String, Student> map) {
        // get the first non-null value
        Collection<Student> values = map.values();
        for (Student value : values) {
            if (value != null) {
                try {
                    value.hashCode();
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
        }
        return true;
    }

    /**
     * Checks all the properties that should always hold of a map. Also calls
     * {@link #assertMoreInvariants} to check invariants that are peculiar to
     * specific implementations.
     *
     * @see #assertMoreInvariants
     * @param map the map to check.
     */
    protected final void assertInvariants(Map<String, Student> map) {
        Set<String> keySet = map.keySet();
        Collection<Student> valueCollection = map.values();
        Set<Map.Entry<String, Student>> entrySet = map.entrySet();

        assertEquals(map.size() == 0, map.isEmpty());
        assertEquals(map.size(), keySet.size());
        assertEquals(keySet.size() == 0, keySet.isEmpty());
        assertEquals(!keySet.isEmpty(), keySet.iterator().hasNext());

        int expectedKeySetHash = 0;
        for (String key : keySet) {
            Student value = map.get(key);
            expectedKeySetHash += key != null ? key.hashCode() : 0;
            assertTrue(map.containsKey(key));
            assertTrue(map.containsValue(value));
            assertTrue(valueCollection.contains(value));
            assertTrue(valueCollection.containsAll(Collections.singleton(value)));
            assertTrue(allowsNullKeys || (key != null));
        }
        assertEquals(expectedKeySetHash, keySet.hashCode());

        assertEquals(map.size(), valueCollection.size());
        assertEquals(valueCollection.size() == 0, valueCollection.isEmpty());
        assertEquals(
                !valueCollection.isEmpty(), valueCollection.iterator().hasNext());
        for (Student value : valueCollection) {
            assertTrue(map.containsValue(value));
            assertTrue(allowsNullValues || (value != null));
        }

        assertEquals(map.size(), entrySet.size());
        assertEquals(entrySet.size() == 0, entrySet.isEmpty());
        assertEquals(!entrySet.isEmpty(), entrySet.iterator().hasNext());
        assertFalse(entrySet.contains("foo"));

        boolean supportsValuesHashCode = supportsValuesHashCode(map);
        if (supportsValuesHashCode) {
            int expectedEntrySetHash = 0;
            for (Entry<String, Student> entry : entrySet) {
                assertTrue(map.containsKey(entry.getKey()));
                assertTrue(map.containsValue(entry.getValue()));
                int expectedHash =
                        (entry.getKey() == null ? 0 : entry.getKey().hashCode()) ^
                                (entry.getValue() == null ? 0 : entry.getValue().hashCode());
                assertEquals(expectedHash, entry.hashCode());
                expectedEntrySetHash += expectedHash;
            }
            assertEquals(expectedEntrySetHash, entrySet.hashCode());
        }

        Object[] entrySetToArray1 = entrySet.toArray();
        assertEquals(map.size(), entrySetToArray1.length);
        assertTrue(Arrays.asList(entrySetToArray1).containsAll(entrySet));

        Entry<?, ?>[] entrySetToArray2 = new Entry<?, ?>[map.size() + 2];
        entrySetToArray2[map.size()] = mapEntry("foo", 1);
        assertSame(entrySetToArray2, entrySet.toArray(entrySetToArray2));
        assertNull(entrySetToArray2[map.size()]);
        assertTrue(Arrays.asList(entrySetToArray2).containsAll(entrySet));

        Object[] valuesToArray1 = valueCollection.toArray();
        assertEquals(map.size(), valuesToArray1.length);
        assertTrue(Arrays.asList(valuesToArray1).containsAll(valueCollection));

        Object[] valuesToArray2 = new Object[map.size() + 2];
        valuesToArray2[map.size()] = "foo";
        assertSame(valuesToArray2, valueCollection.toArray(valuesToArray2));
        assertNull(valuesToArray2[map.size()]);
        assertTrue(Arrays.asList(valuesToArray2).containsAll(valueCollection));

        assertMoreInvariants(map);
    }

    /**
     * Override this to check invariants which should hold true for a particular
     * implementation, but which are not generally applicable to every instance
     * of Map.
     *
     * @param map the map whose additional invariants to check.
     */
    protected void assertMoreInvariants(Map<String, Student> map) {
    }


    @Test
    public void testGetOrDefaultInEmptyMap() {
        final Map<String, Student> map;

        map = makeEmptyMap();

        assertEquals("Not found", map.getOrDefault(getKeyNotInPopulatedMap(),
                new Student("Not found")).getUuid());

        assertInvariants(map);
    }

    @Test
    public void testGetOrDefault() {
        final Map<String, Student> map;

        map = makePopulatedMap();

        assertEquals("Not found", map.getOrDefault(getKeyNotInPopulatedMap(),
                new Student("Not found")).getUuid());
        assertEquals("Student 0", map.getOrDefault("0", new Student("Not found")).getUuid());
        assertEquals("Student 999", map.getOrDefault("999", new Student("Not found")).getUuid());

        assertInvariants(map);
    }

    private static <String, Student> Entry<String, Student> mapEntry(String key, Student value) {
        return Collections.singletonMap(key, value).entrySet().iterator().next();
    }
}