package org.example.generics;

/**  PECS Principle - Producer Extends Consumer Super
    <li>For containers that frequently read data out, use the upper bound Extends.</li>
    <li>For containers that often insert data in, use the lower bound Super.</li>
 */
public class GenericsDemo {

    public static void main(String[] args) {
        Plate<? super Meat> p = new Plate<>(new Meat());

        p.set(new Meat());
        p.set(new Beef());

        // The read data can only be stored in the Object class.
        //Beef newMeat3 = p.get();   // Error
        //Meat newMeat1 = p.get();   // Error
        Object newMeat2 = p.get();

        Plate<? extends Food> foodPlate = new Plate<>(new Food());

        //foodPlate.set(new Fruit()); //Error
        //foodPlate.set(new Apple()); //Error

        Object food = foodPlate.get();
        Food newFood = foodPlate.get();
    }

    static class Food {}

    static class Fruit extends Food {}
    static class Meat extends Food {}

    static class Beef extends Meat {}

    static class Plate<T> {

        private T item;

        public Plate(T t) { item = t; }

        public void set(T t) { item = t; }

        public T get() { return item; }

    }
}
