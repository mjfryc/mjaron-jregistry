package pl.mjaron.jregistry.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PropertyPathTest {

    @Test
    void twoNodes() {
        PropertyPath path = new PropertyPath("foo.bar");
        String[] parts = path.getPathParts();
        assertArrayEquals(new String[]{"foo", "bar"}, parts);
    }

    @Test
    void singleNode() {
        PropertyPath path = new PropertyPath("foobar");
        String[] parts = path.getPathParts();
        assertArrayEquals(new String[]{"foobar"}, parts);
    }

    @Test
    void isEmpty() {
        assertThrows(PropertyPath.PathParseException.class, () -> {
            PropertyPath path = new PropertyPath(" ");
            path.getPathParts();
        });
    }

    @Test
    void startsWithSeparator() {
        assertThrows(PropertyPath.PathParseException.class, () -> {
            PropertyPath path = new PropertyPath(".foo");
            path.getPathParts();
        });
    }

    @Test
    void endsWithSeparator() {
        assertThrows(PropertyPath.PathParseException.class, () -> {
            PropertyPath path = new PropertyPath("foo.");
            path.getPathParts();
        });
    }

    @Test
    void justSeparator() {
        assertThrows(PropertyPath.PathParseException.class, () -> {
            PropertyPath path = new PropertyPath(".");
            path.getPathParts();
        });
    }

    @Test
    void collectionTest() {
        ArrayList<String> array = new ArrayList<String>();
        array.add("foo");
        array.add("bar");
        PropertyPath path = new PropertyPath(array);
        String[] parts = path.getPathParts();
        assertArrayEquals(new String[]{"foo", "bar"}, parts);
    }
}