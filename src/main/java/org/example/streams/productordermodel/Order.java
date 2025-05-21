package org.example.streams.productordermodel;

import java.time.LocalDate;
import java.util.List;

record Order(int orderId, LocalDate date, List<Product> products) {

    @Override
    public String toString() {
        return "Order " + orderId + " on " + date;
    }
}

