package org.example.useful.replaceif.polymorphism;

public class ReplaceWithPolymorphism {
    public static void main(String[] args) {
        Discount discount = new PayPal();
        System.out.println(discount.getDiscount());
    }

}
