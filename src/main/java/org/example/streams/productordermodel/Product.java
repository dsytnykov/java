package org.example.streams.productordermodel;

record Product(String name, String category, double price) {

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}
