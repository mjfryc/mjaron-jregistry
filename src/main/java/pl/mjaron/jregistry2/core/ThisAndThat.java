package pl.mjaron.jregistry2.core;

import java.util.Arrays;

public class ThisAndThat {

    /**
     * @param what String where looking for char occurrences.
     * @param ch Searched character.
     * @return Count of character ch in string what.
     */
    public static int charsCount(final String what, final char ch) {
        int count = 0;
        final int len = what.length();
        for (int i = 0; i < len; ++i) {
            if (what.charAt(i) == ch) {
                ++count;
            }
        }
        return count;
    }


    public static <T> T[] addElementToArray(T array[], T what)
    {
        T newArray[] = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = what;
        return newArray;
    }

}
