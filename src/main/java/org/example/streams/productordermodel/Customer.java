package org.example.streams.productordermodel;

import java.util.List;

record Customer(int id, String name, List<Order> orders) {

    @Override
    public String toString() {
        return "Customer: " + name;
    }
}
