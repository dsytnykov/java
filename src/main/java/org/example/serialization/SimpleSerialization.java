package org.example.serialization;

import java.io.*;

public class SimpleSerialization {
    public static void main(String[] args) {
        Person person = new Person("John Doe", 30, "123 Main St");

        try(ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream("person.ser"))) {
            ous.writeObject(person);
            System.out.println("Object was serialized successfully");

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person.ser"));
            Person personDeserialized = (Person) ois.readObject();
            System.out.println("Deserialized object: " + personDeserialized);
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private record Person(String name, int age, String address) implements Serializable {
    }
}
