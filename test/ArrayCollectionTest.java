import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ArrayCollectionTest {


    @Test
    public void testHasNext() {
        final Collection<String> testInstance = new ArrayCollection<>();
        testInstance.add("One");

        final Iterator<String> iter = testInstance.iterator();

        assertTrue("This is the first call to the hasNext() method in a non-empty collection. "
                + "The method should return true!", iter.hasNext());

        testInstance.remove("One");

        assertFalse("In an empty collection, the hasNext() method must return false!", iter.hasNext());
    }

    @Test
    public void testHasNextWhenNextWasCalled() {
        final Collection<String> testInstance = new ArrayCollection<>();
        testInstance.add("One");
        testInstance.add("Two");

        final Iterator<String> iter = testInstance.iterator();

        assertTrue("This is the first call to the hasNext() method in a non-empty collection. "
                + "The method should return true!", iter.hasNext());

        iter.next();

        assertTrue("This is the second call to the hasNext() method in a non-empty collection. "
                + "The method should return true!", iter.hasNext());

        iter.next();

        assertFalse("Not in an empty collection, but at the end of an iteration, the"
                + " hasNext() method should return false!", iter.hasNext());
    }

    @Test
    public void testNextOnEmptyCollection() {
        final Collection<Integer> testInstance = new ArrayCollection<>();

        final Iterator<Integer> iter = testInstance.iterator();

        try {
            iter.next();
            fail("The next() method does not throw an exception when there are no more"
                    + " elements in the collection.");
        } catch (final java.util.NoSuchElementException ignore) { }
    }

    @Test
    public void testNextNonEmptyCollection() {
        final Collection<Integer> testInstance = new ArrayCollection<>();

        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);
        testInstance.add(4);
        testInstance.add(5);

        final Iterator<Integer> iter = testInstance.iterator();
        assertEquals("The next() method returns wrong element.", (Integer) 1, iter.next());
        assertEquals("The next() method returns wrong element.", (Integer) 2, iter.next());
        assertEquals("The next() method returns wrong element.", (Integer) 3, iter.next());
        assertEquals("The next() method returns wrong element.", (Integer) 4, iter.next());
        assertEquals("The next() method returns wrong element.", (Integer) 5, iter.next());

        try {
            iter.next();
            fail("The next() method does not throw an exception when there are no more"
                    + " elements to iterate. The end of the collection.");
        } catch (final java.util.NoSuchElementException ignore) { }
    }

    @Test
    public void testRemoveBeforeNext() {
        final Collection<Integer> testInstance = new ArrayCollection<>();
        testInstance.add(2);

        final Iterator<Integer> iter = testInstance.iterator();
        try {
            iter.remove();
            fail("\n" + "The remove() method does not throw an exception when called before the "
                    + "next() method. But should do it!");
        } catch (final IllegalStateException ignore) { }
    }

    @Test
    public void testRemoveTwoTimeInTheRow() {
        final Collection<Integer> testInstance = new ArrayCollection<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);
        testInstance.add(4);
        testInstance.add(5);

        final Iterator<Integer> iter = testInstance.iterator();
        iter.next();
        iter.next();
        iter.next();
        iter.remove();
        assertEquals("The expected collection size, after remove(), is 4, "
                + "but the actual size is not.", 4, testInstance.size());
        try {
            iter.remove();
            fail("The remove() method does not throw an exception when it is "
                    + "called again (twice after next()).");
        } catch (final IllegalStateException ignore) { }

        assertEquals("The expected collection size is 4, but the actual size is not.",
                4,
                testInstance.size());
    }

    @Test
    public void testNextAfterIteratorsRemove() {
        final Collection<Integer> testInstance = new ArrayCollection<>();
        testInstance.add(1);
        testInstance.add(2);

        final Iterator<Integer> iter = testInstance.iterator();
        iter.next();
        iter.remove();
        iter.next();
        iter.remove();
        try {
            iter.next();
            fail("\n" + "The next() method does not throw an exception when there are no more "
                    + "elements in the collection.");
        } catch (final java.util.NoSuchElementException ignore) { }
    }
}
