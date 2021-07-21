

|This is draft only!|
|---|

# mjaron-jregistry

Key-value settings for java applications.

## Tasks

* [x] String and Integer properties
* [x] Possibility to define default values if properties are not set in storage
* [x] Create type value restrictions (enumerations) 
* Make it thread-safe
* Create transactions critical section
* Create persistent storage root
* Create possibility to edit properties via web browser
    * options tree view
    * Create actions which may be triggered with html button
    * Create some options visualizers
    * Add file management

## Sample usage

```java
package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.IPropertyVisitor;
import pl.mjaron.jregistry.core.MemoryStorage;

import java.io.PrintStream;

class Registry {
    Node root = new Node(new MemoryStorage());
    public Node fish = new Node(root, "fish");
    public IntProperty FISH_AGE = new IntProperty(fish, "age").setValue(28);
    public StrProperty name = new StrProperty(fish, "what").setDefault("Fish");
    public StrProperty surname = new StrProperty(fish, "name").setValue("Jessica");
    public StrProperty myEnum = new StrProperty(fish, "enum")
            .addEnum("a")
            .addEnum("b")
            .addEnum("c")
            .setEnumOnly(true)
            .setValue("b");
    public IntProperty myDigitEnum = new IntProperty(fish, "enum-d").setEnumOnly(true).addEnum(4).addEnum(6).addEnum(8);
}


public class Sample {

    static Registry R = new Registry();

    public static void main(String[] args) {
        final PrintStream o = System.out;
        R.myDigitEnum.setValue(4);
        o.println("All properties:");
        R.root.accept(new IPropertyVisitor.PrintingVisitor());

        o.println();
        o.println("Fish age is: " + R.FISH_AGE.getValue());
    }

}

```
