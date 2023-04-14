///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    // TODO:
    private int[] shiftRegister; //int array to store the seed
    private int curr_Tap = 0; //int to store the tap of the shift register
    private int curr_Size = 0; //int to store the size of the shift register
    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        if (tap < 0 || tap >= size) {
            throw new RuntimeException("Tap invalid!");
        } else {
            shiftRegister = new int[size];
            this.curr_Tap = tap;
            this.curr_Size = size;
        }
    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description: First checks if the seed contains only 0 and 1. 
     * If seed length and size of shiftRegister matches, it then sets the
     * shift register to the specified intial seed, ordering them
     */
    @Override
    public void setSeed(int[] seed) {
        // TODO:
        if (seed.length != this.curr_Size) {
            throw new RuntimeException("Seed length does not match size!");
        } else {
            for (int i = 0; i < seed.length; i++) {
                if (seed[i] != 0 && seed[i] != 1) {
                    throw new RuntimeException("Seed must only contain 0 or 1!");
                } else {
                    shiftRegister[i] = seed[seed.length - i - 1];
                }
            }
        }
    }

    /**
     * shift
     * @return
     * Description: Does a left shift on the seed after it has been set
     */
    @Override
    public int shift() {
        // TODO:
        int toReturn = shiftRegister[0] ^ shiftRegister[curr_Size - curr_Tap - 1];
        for (int i = 0; i < curr_Size - 1; i++) {
            shiftRegister[i] = shiftRegister[i + 1];
        }
        shiftRegister[curr_Size - 1] = toReturn;
        return shiftRegister[curr_Size - 1];
    }

    /**
     * generate
     * @param k
     * @return
     * Description: It generates an int array of binary numbers after shifting it k times, then 
     * converts from binary to integer
     */
    @Override
    public int generate(int k) {
        // TODO:
        int[] binArray = new int[k];
        for (int i = 0; i < k; i++) {
            binArray[i] = this.shift();
        }
        return toDecimal(binArray);
    }
        

    /**
     * Returns the integer representation for a binary int array.
     * @param array
     * @return
     */
    private int toDecimal(int[] array) {
        // TODO:
        int decimal = 0;
        for (int i = 0; i < array.length; i++) {
            decimal = decimal + array[i] * (int) Math.pow(2, array.length - i - 1);
        }
        return decimal;
    }
}
