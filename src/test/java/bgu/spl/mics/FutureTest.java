package bgu.spl.mics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class FutureTest {
    private Future<Integer> f;
    @Before
    public void setUp() throws Exception {
        //creat new object new Future() and checks on it.
        f=new Future<Integer>();
    }

    @After
    public void tearDown() throws Exception {
        //java garbig colector works, no need to delete it.
    }

    @Test
    public void get() {
        f.resolve(1);
        assertEquals((Integer) 1, f.get());
    }

    @Test
    public void resolve() {
        f.resolve(1);
        assertTrue(f.isDone());
    }

    @Test
    public void isDone() {
        assertFalse(f.isDone());
        f.resolve(0);
        assertTrue(f.isDone());
    }

    @Test
    public void get1() {
        assertNull(f.get(1, TimeUnit.SECONDS));
        f.resolve(1);
        assertTrue(1 == f.get(1,TimeUnit.SECONDS));
    }
}