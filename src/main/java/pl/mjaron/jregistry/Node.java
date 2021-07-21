package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.PropertyNode;

public class Node extends PropertyNode {

    public Node node(final String name) {
        return this.add(name, new Node());
    }

    public IntProperty integer(final String name) {
        return this.add(name, new IntProperty());
    }

    public StringProperty string(final String name) {
        return this.add(name, new StringProperty());
    }

}
