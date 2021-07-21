package pl.mjaron.jregistry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThisAndThatTest {

    @Test
    void charsCount() {
        assertEquals(0, ThisAndThat.charsCount("foobar", '.'));
        assertEquals(1, ThisAndThat.charsCount("foo.bar", '.'));
        assertEquals(2, ThisAndThat.charsCount(".foo.bar", '.'));
        assertEquals(3, ThisAndThat.charsCount(".foo.bar.", '.'));
        assertEquals(4, ThisAndThat.charsCount(".foo...", '.'));
    }
}