package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * TODO:
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some
 * example test cases.
 * Write your own unit tests to test against IntQueue interface with
 * specification testing method
 * using mQueue = new LinkedIntQueue();
 * 
 * 2.
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new
 * ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the
 * {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with
 * structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
        // mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        // TODO: write your own unit test

        mQueue.enqueue(1);
        assertFalse(mQueue.isEmpty());

    }

    @Test
    public void testPeekEmptyQueue() {
        // TODO: write your own unit test
        assertNull(mQueue.peek());

    }

    @Test
    public void testPeekNoEmptyQueue() {
        // TODO: write your own unit test
        mQueue.enqueue(1);
        assertEquals(1, mQueue.peek().intValue());
    }

    @Test
    public void testEnqueue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(testList.get(i), mQueue.dequeue());
            assertEquals(testList.size() - i - 1, mQueue.size());
        }

    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }

    @Test
    public void testDequeueOnEmptyQueue() {
        // The queue is initially empty, so this should return null.
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testEnsureCapacityWithWrapAround() {
        final int initialSize = 10; // Use the known initial size.

        // Step 1: Fill the queue to its initial capacity.
        for (int i = 0; i < initialSize; i++) {
            mQueue.enqueue(i);
        }

        // Step 2: Dequeue a few elements to move the head forward.
        int numberToDequeue = 3;
        for (int i = 0; i < numberToDequeue; i++) {
            mQueue.dequeue();
        }

        // Step 3: Enqueue elements to fill the queue and force a wrap around.
        for (int i = 0; i < numberToDequeue; i++) {
            mQueue.enqueue(initialSize + i);
        }

        // Step 4: Add one more element to trigger a resize.
        mQueue.enqueue(-1);

        // Now the queue should have been resized, and we need to check the order.
        for (int i = numberToDequeue; i < initialSize; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
        for (int i = 0; i < numberToDequeue; i++) {
            assertEquals(Integer.valueOf(initialSize + i), mQueue.dequeue());
        }
        assertEquals(Integer.valueOf(-1), mQueue.dequeue());
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testClear() {
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());
    }

    @Test
    public void testEnqueueAfterClear() {
        mQueue.enqueue(1);
        mQueue.clear();
        mQueue.enqueue(2);
        assertEquals(1, mQueue.size());
        assertEquals(Integer.valueOf(2), mQueue.peek());
    }

    @Test
    public void testDequeueSingleElement() {
        mQueue.enqueue(1);
        assertEquals(Integer.valueOf(1), mQueue.dequeue());
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testPeekAfterDequeue() {
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.dequeue();
        assertEquals(Integer.valueOf(2), mQueue.peek());
    }

    @Test
    public void testEnqueueWithMultipleResizing() {
        for (int i = 0; i < 100; i++) {
            mQueue.enqueue(i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testDequeueToEmptyThenEnqueue() {
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.dequeue();
        mQueue.dequeue();
        assertTrue(mQueue.isEmpty());
        mQueue.enqueue(3);
        assertFalse(mQueue.isEmpty());
        assertEquals(Integer.valueOf(3), mQueue.peek());
    }

}
