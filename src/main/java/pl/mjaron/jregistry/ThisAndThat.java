package pl.mjaron.jregistry;

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

    public static <T> T[] addToArray(final T array[], final T what)
    {
        T newArray[] = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = what;
        return newArray;
    }

    public static <T> T[] addToArray(final T array[], final T other[])
    {
        T newArray[] = Arrays.copyOf(array, array.length + other.length);
        for (int i = 0; i < other.length; ++i) {
            newArray[array.length + i] = other[i];
        }
        return newArray;
    }

}
