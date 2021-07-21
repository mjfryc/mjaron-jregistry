package pl.mjaron.jregistry2.core;

import pl.mjaron.jregistry.ThisAndThat;

import java.util.Collection;

/**
 * Describes all nodes needed to access property value in IRegistry.
 */
public class PropertyPath {

    public static final char NODE_SEPARATOR = '.';

    public PropertyPath plus(final String node) {
        String newPath[] = pl.mjaron.jregistry.ThisAndThat.addToArray(this.pathParts, node);
        return new PropertyPath(newPath);
    }

    public PropertyPath plus(final PropertyPath other) {
        String newPath[] = ThisAndThat.addToArray(this.pathParts, other.pathParts);
        return new PropertyPath(newPath);
    }

    public class PathParseException extends RuntimeException {
        public PathParseException(String what) {
            super(what);
        }
    }

    public PropertyPath(String[] path) {
        this.pathParts = path;
        this.validatePath(path);
    }

    public PropertyPath(String path) {
        this.pathParts = this.parsePath(path);
    }

    public PropertyPath(Collection<String> collection) {
        this.pathParts = new String[collection.size()];
        collection.toArray(this.pathParts);
        this.validatePath(collection);
    }

    public String[] getPathParts() {
        return this.pathParts;
    }

    @Override
    public String toString() {
        return String.join(".", this.pathParts);
    }

    //private static final String[] EMPTY_STRING_ARRAY = {};
    private String[] parsePath(String what) {
        what = what.trim();
        if (what.isEmpty()) {
            return new String[0];
        }

        final int count = ThisAndThat.charsCount(what, NODE_SEPARATOR);
        final String[] result = new String[count + 1];

        int lastIdx = -1;
        int idx = 0;
        for (int i = 0; i < count + 1; ++i) {
            idx = what.indexOf('.', idx);
            if (idx == -1) {
                if (lastIdx + 1 == what.length()) {
                    throw new PathParseException("Empty path node. Full invalid path: [" + what + "].");
                }
                String node = what.substring(lastIdx + 1);
                result[i] = node;
                break;
            }

            if (lastIdx + 1 == idx) {
                throw new PathParseException("Empty path node. Full invalid path: [" + what + "].");
            }
            String node = what.substring(lastIdx + 1, idx);
            result[i] = node;
            lastIdx = idx;
            idx += 1;
        }
        return result;
    }

    private void validatePath(final Object source) {
        if (pathParts.length == 0) {
            throw new PathParseException("Path is empty. Full invalid path: [" + source + "].");
        }
        for (final String entry : this.pathParts) {
            if (entry.isEmpty()) {
                throw new PathParseException("Empty path node. Full invalid path: [" + source + "].");
            }
        }
    }

    private final String[] pathParts;
}
