import static org.junit.Assert.*;

import org.junit.Test;

/**
 * ShiftRegisterTest
 * @author dcsslg
 * Description: set of tests for a shift register implementation
 */
public class ShiftRegisterTest {
    /**
     * Returns a shift register to test.
     * @param size
     * @param tap
     * @return a new shift register
     */
    ILFShiftRegister getRegister(int size, int tap) {
        return new ShiftRegister(size, tap);
    }

    /**
     * Tests shift with simple example.
     */
    @Test
    public void testShift1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 1, 1, 0, 0, 0, 1, 1, 1, 1, 0 };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.shift());

        }
    }
    @Test
    public void testShift2() {
        ILFShiftRegister r = getRegister(9, 5);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 0, 1, 0, 1, 0, 1, 0, 0, 0, 1 };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.shift());
        }
    }
     /* Test register with size 1 with the shift method 
     */
    @Test
    public void testShift3() {
        ILFShiftRegister r = getRegister(1, 0);
        int[] seed = { 0 };
        r.setSeed(seed);
        int[] expected = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.shift());
        }

    }
    /* Test register with tap 0 to be shifted.
     */
    @Test
    public void testShift4() {
        ILFShiftRegister r = getRegister(9, 0);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 1, 1, 0, 1, 0, 1, 1, 0, 0, 1 };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.shift());
        }
    }
    /**
     * Tests generate with simple example.
     */
    @Test
    public void testGenerate1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 6, 1, 7, 2, 2, 1, 6, 6, 2, 3 };
        for (int i = 0; i < 10; i++) {
            assertEquals("GenerateTest", expected[i], r.generate(3));
        }
    }

    /**
     * Tests register of length 1.
     */
    @Test
    public void testOneLength() {
        ILFShiftRegister r = getRegister(1, 0);
        int[] seed = { 1 };
        r.setSeed(seed);
        int[] expected = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.generate(3));
        }
    }
    /* This tests when the tap is 0, whether the generate works */
    @Test
    public void testGenerateZeroTap() {
        ILFShiftRegister r = getRegister(9, 0);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 6, 5, 4, 4, 6, 7, 0, 4, 5, 7 };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.generate(3));
        }
    }
    // This tests in the case when 0 is the integer inserted into generate 
    @Test
    public void testGenerateZero() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.generate(0));
        }
    }
    /*This tests when a negative integer is put into generate, which should 
    * result in a NegativeArraySizeException as you are unable to generate -1 bits
    */
    @Test
    public void testNegativeIntGenError() {
        ILFShiftRegister r = getRegister(9, 0);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        r.generate(-1);
    }
    /**
     * Tests with erroneous seed.
     */
    @Test
    public void testSeedLengthError() {
        ILFShiftRegister r = getRegister(4, 1);
        int[] seed = { 1, 0, 0, 0, 1, 1, 0 };
        r.setSeed(seed);
        r.shift();
        r.generate(4);
    }
    //This should print out an error message stating that the seed length
    //does not match the size

    @Test
    public void testWrongTapError() {
        ILFShiftRegister r = getRegister(4, 5);
        int[] seed = { 1, 0, 0, 1 };
        r.setSeed(seed);
    }
    /* This prints out the error message that the tap is invalid.
    */

    @Test
    public void testWrongElementError() {
        ILFShiftRegister r = getRegister(4, 0);
        int[] seed = { 1, 0, 2, 4 };
        r.setSeed(seed);
    }
    /* This prints out the error message that there are elements that
    * are not 0 or 1 within the seed.
    */
}
