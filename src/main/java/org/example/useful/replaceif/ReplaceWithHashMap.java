package org.example.useful.replaceif;

import org.example.useful.replaceif.product.Pasta;
import org.example.useful.replaceif.product.Pizza;
import org.example.useful.replaceif.product.Product;
import org.example.useful.replaceif.product.Soup;

import java.util.HashMap;
import java.util.Map;

public class ReplaceWithHashMap {
    //In Spring, we can add for each product @Service("Soup") etc. and add to map @Resource
    //It will inject a map with names as keys and products as values - Map<String, Product>
    private static final Map<String, Product> map = new HashMap<>();

    public ReplaceWithHashMap() {
        map.put("Pizza", new Pizza());
        map.put("Soup", new Soup());
        map.put("Pasta", new Pasta());
    }

    public Product getProduct(String type) {
        return map.get(type);
    }
}
