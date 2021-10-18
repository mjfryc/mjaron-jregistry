package pl.mjaron.jregistry.core;

import java.io.*;
import java.util.List;

public class BlobIO {

    private final IBlobStorage s;
    private final PropertyPath path;
    ICriticalSection cs;
    final String format;

    public BlobIO(final IBlobStorage storage, IProperty property, ICriticalSection section, final String format) {
        this.s = storage;
        this.path = property.getPath();
        this.cs = section;
        this.format = format;
    }

    /**
     * Tells whether given property has any value set in persistent storage.
     *
     * @return True if given property is stored in persistent storage.
     */
    public boolean hasValue() {
        return cs.withLock(() -> s.hasValue(path));
    }

    /**
     * Removes value of this property. It is no longer stored in persistent storage.
     * Post condition: hasValue() returns false.
     */
    public void cleanValue() {
        cs.withLock(() -> {
            s.cleanValue(path);
            return null;
        });
    }

    /**
     * All properties are stored as blob.
     * Provides raw value data stored as blob.
     *
     * @return Text value of property.
     */
    public InputStream getInputStream() {
        return cs.withLock(() -> s.getValue(path));
    }

    public byte[] readRawData() {
        return cs.withLock(() -> {
            InputStream in = null;
            try {
                in = s.getValue(path);
                return in.readAllBytes();
            }
            catch (final IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to read raw data.", e);
            }
        });
    }

    /**
     * All properties are stored as text.
     * Allows to set raw value data stored as text.
     *
     * @param value Value stored as blob.
     */
    public void setFromInputStream(final InputStream value) {
        cs.withLock(() -> {
            s.setValue(path, value);
            return null;
        });
    }

    public void storeRawData(final byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        this.setFromInputStream(in);
    }

}
