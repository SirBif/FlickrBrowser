package com.flickrbrowser.parcelable;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bif on 31/03/14.
 */
public class SimpleLocationTest {
    private SimpleLocation locA;
    private SimpleLocation locB;
    private SimpleLocation locC;

    @Before
    public void init() {
        locA = new SimpleLocation(2.0, 6.0);
        locB = new SimpleLocation(4.0, 5.0);
        locC = new SimpleLocation(2.0, 6.0);
    }

    @Test
    public void testEqual() {
        Assert.assertTrue(locA.equals(locC));
    }

    @Test
    public void testDifferent() {
        Assert.assertFalse(locA.equals(locB));
    }

    @Test
    public void testNull() {
        Assert.assertFalse(locA.equals(null));
    }

    @Test
    public void testOtherObject() {
        Assert.assertFalse(locA.equals(new Double(1)));
    }

    @Test
    public void testReflexive() {
        Assert.assertTrue(locA.equals(locA));
    }

    @Test
    public void testSymmetric() {
        Assert.assertEquals(locA.equals(locC), locC.equals(locA));
    }

    @Test
    public void testHash() {
        Assert.assertEquals(locA.hashCode(), locC.hashCode());
        Assert.assertFalse(locA.hashCode() == locB.hashCode());
    }
}
