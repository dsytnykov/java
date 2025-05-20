package org.example.useful.replaceif.polymorphism;

public class DebitCard implements Discount {
    @Override
    public double getDiscount() {
        return 100;
    }
}
