package org.example.useful.replaceif.polymorphism;

public class PayPal implements Discount{
    @Override
    public double getDiscount() {
        return 80;
    }
}
