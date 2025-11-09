package org.example.streams.productordermodel;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductOrderTest {
    public static void main(String[] args) {

        List<Customer> customers = getCustomers();

        /*1️⃣ Find the Total Revenue Per Category
        Group products by category and compute the total revenue generated for each category.*/
        Map<String, Double> totalRevenueByCategory = customers.stream()
                .flatMap(c -> c.orders().stream())
                .flatMap(o -> o.products().stream())
                .collect(Collectors.groupingBy(Product::category,
                        Collectors.summingDouble(Product::price)));

        /*
        2️⃣ Get the Customer Who Spent the Most Money
        Identify the customer with the highest total spending across all orders.*/
        Customer customerWithHighestTotal = customers.stream()
                .collect(Collectors.groupingBy(
                        c -> c,
                        Collectors.summingDouble(c ->
                                c.orders().stream()
                                        .flatMap(o -> o.products().stream())
                                        .mapToDouble(Product::price)
                                        .sum())
                        ))
                .entrySet().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparingDouble(Map.Entry::getValue)),
                        e -> e.map(Map.Entry::getKey).orElse(null)
                ));

        /*If leave map than we can use
        Customer highestSpender = Collections.max(spendingByCustomer.entrySet(), Map.Entry.comparingByValue()).getKey();
        */

        /*
        3️⃣ Find the Most Frequently Purchased Product
        Determine which product appears the most times across all orders.*/
        Product mostPurchasedProduct = customers.stream()
                .flatMap(c -> c.orders().stream())
                .flatMap(o -> o.products().stream())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);

        /* Product mostFrequentProduct = Collections.max(productFrequency.entrySet(), Map.Entry.comparingByValue()).getKey();*/

        /*
        4️⃣ Get the Most Recent Order for Each Customer
        Find the latest order placed by each customer.*/
        Map<Customer, Order> latestOrderByCustomer = customers.stream()
                .collect(Collectors.toMap(customer -> customer,
                        c -> c.orders().stream()
                                .max(Comparator.comparing(Order::date))
                                .orElseThrow()));//should be added what to throw


        /*
        5️⃣ Identify Customers Who Ordered Products from Multiple Categories
        Find customers who have purchased products from at least two different categories.*/
        List<Customer> customersOrderedFromDifferentCategories = customers.stream()
                .collect(Collectors.toMap(
                        customer -> customer,
                        customer -> customer.orders().stream()
                                .flatMap(o -> o.products().stream())
                                .map(Product::category)
                                .distinct()
                                .count()
                ))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();

        List<Customer> customersOrderedFromDifferentCategories2 = customers.stream()
                .filter(customer -> customer.orders().stream()
                        .flatMap(o -> o.products().stream())
                        .map(Product::category)
                        .distinct()
                        .count() > 1)
                .toList();

        /*
        6️⃣ Find the Top 3 Most Expensive Orders
        Sort orders by total price and return the top 3 most expensive ones.*/
        List<Order> top3MostExpensiveOrders = customers.stream()
                .flatMap(c -> c.orders().stream())
                .sorted((o1, o2) -> {
                    double sum1 = o1.products().stream().map(Product::price).reduce(0.0, Double::sum);
                    double sum2 = o2.products().stream().map(Product::price).reduce(0.0, Double::sum);
                    return Double.compare(sum2, sum1);
                })
                //or .sorted(Comparator.comparingDouble(o -> o.getProducts().stream().mapToDouble(Product::getPrice).sum()).reversed())
                .limit(3)
                .toList();

        /*
        7️⃣ Calculate the Average Order Value Per Customer
        Compute the average amount spent per order for each customer.*/
        Map<Customer, Double> averageAmountPerOrderByCustomer = customers.stream()
                .collect(Collectors.toMap(c -> c,
                        c -> c.orders().stream()
                                .flatMap(o -> o.products().stream())
                                .mapToDouble(Product::price)
                                .sum() / c.orders().size()
                        ));

        /*
        8️⃣ List Products That Were Purchased More Than Once
        Identify products that appear in multiple orders.*/
        List<Product> purchasedMoreThanOnce = customers.stream()
                .flatMap(c -> c.orders().stream())
                .flatMap(o -> o.products().stream())
                .collect(Collectors.groupingBy(p -> p,
                        Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();

        /*
        9️⃣ Find the Month with the Highest Sales
        Group orders by month and determine which month had the highest total revenue.*/
        Map<Month, Double> revenueByMonth = customers.stream()
                .flatMap(c -> c.orders().stream())
                .collect(Collectors.groupingBy(
                        o -> o.date().getMonth(),
                        Collectors.summingDouble(o -> o.products().stream().mapToDouble(Product::price).sum())
                        ));

        /*
        🔟 Get the Oldest and Newest Product Purchased Per Customer
        For each customer, determine the oldest and newest product they have purchased (based on order date).*/
        record OldestAndNewestStats(Product oldest, Product newest){}
        Map<Customer, OldestAndNewestStats> oldestAndNewestStatsByCustomer = customers.stream()
                .collect(Collectors.toMap(c -> c,
                        c -> {
                           Optional<Order> oldest = c.orders().stream().min(Comparator.comparing(Order::date));
                           Optional<Order> newest = c.orders().stream().max(Comparator.comparing(Order::date));

                           Product oldestProduct = oldest.map(o -> o.products().get(0)).orElse(null);
                           Product newestProduct = newest.map(o -> o.products().get(0)).orElse(null);

                           return new OldestAndNewestStats(oldestProduct, newestProduct);
                        }));

        Map<Customer, OldestAndNewestStats> oldestAndNewestStatsByCustomer2 = customers.stream()
                .collect(Collectors.toMap(c -> c,
                        c -> c.orders().stream()
                                .collect(Collectors.teeing(
                                        Collectors.minBy(Comparator.comparing(Order::date)),
                                        Collectors.maxBy(Comparator.comparing(Order::date)),
                                        (min, max) ->
                                                new OldestAndNewestStats(
                                                        min.flatMap(o -> o.products().stream().findFirst()).orElse(null),
                                                        max.flatMap(o -> o.products().stream().findFirst()).orElse(null))
                                ))
                ));

        /*
        1️⃣ Find the Most Loyal Customer
        Identify the customer who has placed the highest number of orders.*/
        Customer customerWithHighestNumberOrders = customers.stream()
                .max(Comparator.comparingInt(c -> c.orders().size()))
                .orElse(null);

        /*
        2️⃣ Calculate the Total Sales Per Product
        Group products and compute the total sales revenue for each product.*/
        Map<Product, Double> totalRevenueByProduct = customers.stream()
                .flatMap(c -> c.orders().stream())
                .flatMap(o -> o.products().stream())
                .collect(Collectors.groupingBy(
                        p -> p,
                        Collectors.summingDouble(Product::price)
                        ));

        /*
        3️⃣ Get the Customer Who Bought the Most Expensive Single Item
        Find the customer who purchased the single most expensive product.*/
        Customer whoBoughtTheMostExpensiveProduct = customers.stream()
                .collect(Collectors.toMap(c->c,
                        c -> c.orders().stream()
                                .flatMap(o -> o.products().stream())
                                .max(Comparator.comparingDouble(Product::price))
                                .orElseThrow()))
                .entrySet().stream()
                .max(Comparator.comparingDouble(e -> e.getValue().price()))
                .map(Map.Entry::getKey)
                .orElse(null);

        Customer whoBoughtTheMostExpensiveProduct2 = customers.stream()
                .max(Comparator.comparingDouble(c -> c.orders().stream()
                        .flatMap(o -> o.products().stream())
                        .mapToDouble(Product::price)
                        .max()
                        .orElse(0)))
                .orElse(null);
        /*
        4️⃣ Find the Product That Generated the Highest Revenue
        Determine which product has contributed the most to total revenue.*/
        Product productBroughtTheMostRevenue = customers.stream()
                .<Product>mapMulti((c, consumer) -> c.orders().forEach(o -> o.products().forEach(consumer)))
                .collect(Collectors.groupingBy(p -> p,
                        Collectors.summingDouble(Product::price)))
                .entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);

        /*
        5️⃣ Get the Most Popular Product Category Per Customer
        Identify the category that each customer has purchased from the most.*/
        Map<Customer, String> mostPopularCategoryPerCustomer = customers.stream()
                .collect(Collectors.toMap(c -> c,
                        c -> c.orders().stream()
                                .flatMap(o -> o.products().stream())
                                .collect(Collectors.groupingBy(Product::category,
                                        Collectors.summingDouble(Product::price)))
                                .entrySet().stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElse("Unknown")));

        /*
        6️⃣ Determine Which Customer Spends the Most on Average Per Order
        Find the customer with the highest average order value.*/
        Customer customerWithHighestAveragePerOrder = customers.stream()
                .collect(Collectors.toMap(c -> c,
                        c -> c.orders().stream()
                                .flatMap(o -> o.products().stream())
                                .mapToDouble(Product::price)
                                .sum() / c.orders().size()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);


        /*
        7️⃣ Find the Longest Time Gap Between Two Orders for Each Customer
        Compute the maximum time difference between consecutive orders per customer.*/
        Map<Customer, Period> maxPeriodBetweenOrdersByCustomer = customers.stream()
                .collect(Collectors.toMap(c -> c,
                        c -> c.orders().stream().map(Order::date).collect(Collectors.teeing(
                                Collectors.minBy(LocalDate::compareTo),
                                Collectors.maxBy(LocalDate::compareTo),
                                (min, max) ->
                                        min.isPresent() && max.isPresent()
                                                ? Period.between(max.get(), min.get())
                                                : Period.ZERO
                        ))));


        /*
        8️⃣ Identify Customers Who Have Purchased at Least One Product from Every Category
        Find customers who have bought at least one product from all available categories.*/
        Set<String> availableCategories = customers.stream()
                .<String>mapMulti((c, downstream) ->
                        c.orders().forEach(o->
                                o.products().forEach(p ->
                                        downstream.accept(p.category()))))
                .collect(Collectors.toSet());
        List<Customer> purchasedOneProductFromEachCategory = customers.stream()
                .filter(c -> {
                    Set<String> categories = c.orders().stream().flatMap(o -> o.products().stream()).map(Product::category).collect(Collectors.toSet());
                    return categories.containsAll(availableCategories);
                })
                .toList();

        /*
        9️⃣ Find the Least Popular Product Category
        Identify the category that has generated the least sales in terms of revenue.*/
        String leastValuableCategory = customers.stream()
                .<Product>mapMulti((c, downstream) -> c.orders().forEach(o -> o.products().forEach(downstream)))
                .collect(Collectors.groupingBy(
                        Product::category,
                        Collectors.summingDouble(Product::price)))
                .entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");


        /*
        🔟 Get the First and Last Purchased Product for Each Customer
        Determine the first and most recent product purchased by each customer based on order dates.*/
        record ProductStats(Product firstPurchased, Product lastPurchased){};
        Map<Customer, ProductStats> productStatsByCustomer = customers.stream()
                .collect(Collectors.toMap(c -> c,
                        c -> {
                            Product first = c.orders().stream().min(Comparator.comparing(Order::date)).get().products().getFirst();
                            Product last = c.orders().stream().max(Comparator.comparing(Order::date)).get().products().getFirst();
                            return new ProductStats(first, last);
                        }));

        /* ------------multiMap---------------
        1️⃣ Flatten All Products from All Customers Using mapMulti
        Extract a flat stream of all products from the nested structure using mapMulti.*/
        List<Product> allProducts = customers.stream()
                .<Product>mapMulti((c, productConsumer) -> c.orders().forEach(o ->
                        o.products().forEach(productConsumer)))
                .toList();

        /*
        2️⃣ Filter and Flatten Only Electronics Products from All Orders
        Use mapMulti to collect only products of category "Electronics".*/
        List<Product> productsFromElectronics = customers.stream()
                .<Product>mapMulti((customer, downstream) ->
                        customer.orders().forEach(order ->
                                order.products().forEach(downstream)))
                .filter(p -> "Electronics".equals(p.category()))
                .toList();

        /*
        3️⃣ For Each Customer, Emit All Product Names They’ve Ordered (Distinct)
        Flatten products per customer and collect distinct product names.*/
        List<String> productNamesUnique = customers.stream()
                .<String>mapMulti((c, consumer) -> c.orders()
                        .forEach(o -> o.products().forEach(p -> consumer.accept(p.name()))))
                .distinct()
                .toList();


                /*
        4️⃣ Emit (CustomerName, ProductName) Pairs for All Purchased Products
        Use mapMulti to emit tuples representing which customer bought which product.*/
        record NamesPair(String customerName, String productName){}
        List<NamesPair> namesPairList = customers.stream()
                .<NamesPair>mapMulti((c, consumer) -> c.orders().forEach(o ->
                        o.products().forEach(p -> consumer.accept(new NamesPair(c.name(), p.name())))))
                .toList();

        /*
        5️⃣ Emit Only Products Over $500 with Their Order Info
        From all orders, flatten and emit products with price > 500, including order ID or date.*/
        List<String> productsWithTheirInfo = customers.stream()
                .<String>mapMulti((c, consumer) -> c.orders().forEach(o ->
                        o.products().forEach(p -> {
                            if(p.price() > 500) {
                                consumer.accept(o.orderId() + ": " + p.price());
                            }
                        })))
                .toList();

        /*
        6️⃣ Emit (CustomerName, OrderID, ProductName) Triplets Using mapMulti
        Traverse down from customer to orders to products and emit detailed information in a single flat stream.*/
        List<String> statInfoList = customers.stream()
                .<String>mapMulti((c, downstream) -> c.orders().forEach(o ->
                        o.products().forEach(p -> downstream.accept(c.name() + " " + o.orderId() + " " + p.name()))))
                .toList();

        /*
        7️⃣ For Each Product Purchased, Emit All Customers Who Bought It
        Reverse mapping: use mapMulti to emit product → customer pairs.*/
        record ProductCustomerPair(Product product, Customer customer){}
        List<ProductCustomerPair> productCustomerPairs = customers.stream()
                .<ProductCustomerPair>mapMulti((c, downstream) -> c.orders().forEach(o -> o.products().forEach(p ->
                        downstream.accept(new ProductCustomerPair(p, c)))))
                .toList();

        /*
        8️⃣ Flatten All Orders That Contain More Than 2 Products
        Emit only those orders (flattened) that have a product list size > 2.*/
        List<Order> ordersWithMoreThanTwoProducts = customers.stream()
                .<Order>mapMulti((c, downstream) -> c.orders().forEach(o -> {
                    if (o.products().size() > 2) {
                        downstream.accept(o);
                    }
                }))
                .toList();

        /*
        9️⃣ Emit a Flat Stream of All Orders That Include a "Laptop"
        Use mapMulti to filter only orders that contain a product with name "Laptop".*/
        List<Order> laptopOrders = customers.stream()
                .<Order>mapMulti((c, downstream) -> c.orders().forEach(o -> o.products().forEach(p -> {
                    if ("Laptop".equals(p.name())) {
                        downstream.accept(o);
                    }
                })))
                .distinct()
                .toList();

        /*
        🔟 Emit All Customers with at Least One Order Containing Furniture Products
        Use mapMulti to emit only customers who’ve bought something from the "Furniture" category.*/
        List<Customer> customersWithFurniture = customers.stream()
                .<Customer>mapMulti((c, downstream) ->
                        c.orders().forEach(
                                o -> o.products().forEach(p -> {
                                    if ("Furniture".equals(p.category())) {
                                        downstream.accept(c);
                                    }
                                })))
                .distinct()
                .toList();

        /*🔍 Tasks Using summaryStatistics()
        1️⃣ Get Price Summary of All Purchased Products
        Compute min, max, sum, average, and count of all product prices across all orders.*/
        DoubleSummaryStatistics dss = customers.stream()
                .flatMap(c -> c.orders().stream())
                .flatMap(o -> o.products().stream())
                .mapToDouble(Product::price)
                .summaryStatistics();
        //System.out.println(dss.getMin() + " " + dss.getMax() + " " + dss.getSum() + " " + dss.getAverage() + " " + dss.getCount());

        /*
        2️⃣ Get Order Value Summary for Each Customer
        For each customer, calculate the statistical summary (min/max/avg/sum/count) of their order total values.*/
        Map<String, DoubleSummaryStatistics> dss2 = customers.stream()
                .collect(Collectors.toMap(Customer::name ,
                c -> c.orders().stream()
                .mapToDouble(o -> o.products().stream().mapToDouble(Product::price).sum())
                .summaryStatistics()));

        /*TODO
        3️⃣ Find Summary of Product Prices Per Category
        Group products by category and compute price summary statistics for each group.*/
        /*Map<String, DoubleSummaryStatistics> statisticPerCategory = customers.stream()
                .flatMap(c -> c.orders().stream())
                .flatMap(o -> o.products().stream())
                .collect(Collectors.toMap(Product::category,
                        p -> ))*/


        /*
        4️⃣ Get Summary Statistics for the Number of Products Per Order
        For all orders, compute a summary of how many products each contains.

        5️⃣ Determine Summary Statistics for Spending Per Customer
        For all customers, calculate the statistics of their total spending (over all orders).

        6️⃣ Get Summary of Order Count Per Customer
        Count how many orders each customer has, and generate overall summary stats.

        7️⃣ Find Summary Stats for Product Price in Recent Orders (e.g. past month)
        Filter orders by recent date, then gather price stats of products purchased.

        8️⃣ Get Category-wise Summary for Products Sold in Orders Over $1000
        Only consider orders with a total value over $1000 and compute summary per product category.

        9️⃣ Compute Summary Stats for Product Prices in a Specific Category (e.g. Electronics)
        Isolate one category and analyze product price distribution.

        🔟 Get Statistics for Total Items Bought by All Customers
        Across all orders, count how many items each customer bought and compute global statistics.

*/
        /*1. Find the Customer Who Spent the Most in a Single Order
        Return the customer who made the single highest-value order.*/
        Customer maxInSingleOrder = getCustomers().stream()
                .collect(Collectors.toMap(c -> c,
                        c -> c.orders().stream()
                                .map(o -> o.products().stream()
                                        .mapToDouble(Product::price)
                                        .sum())
                                .max(Double::compareTo).orElse(0.0)))
                        .entrySet().stream().max(Comparator.comparingDouble(Map.Entry::getValue))
                        .map(Map.Entry::getKey).orElse(null);


        /*2. Get a Map of Product → List of Customers Who Bought It
        Create a Map<Product, List<Customer>> showing who has purchased each product (at least once).*/
        Map<Product, List<Customer>> productsByCustomers = new HashMap<>();
        getCustomers().stream()
                .collect(Collectors.toMap(c -> c, c -> c.orders().stream().flatMap(o -> o.products().stream()).collect(Collectors.toList())))
                .forEach((key, value) ->
                        value.forEach(p -> productsByCustomers.computeIfAbsent(p, k -> new ArrayList<>()).add(key)));

        Map<Product, List<Customer>> productsByCustomers2 = getCustomers().stream()
                .flatMap(customer -> customer.orders().stream()
                        .flatMap(order -> order.products().stream()
                                .map(product -> Map.entry(product, customer))))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(
                                Map.Entry::getValue,
                                Collectors.collectingAndThen(
                                        Collectors.toSet(),
                                        ArrayList::new))
                ));

        /*
        3. Identify Customers Who Have Bought a Product More Than Once (Any Order)
        Find all customers who have purchased the same product in more than one order.*/
        List<Customer> customersWithMultiplePurchases = getCustomers().stream()
                .filter(c -> c.orders().stream()
                        .flatMap(o -> o.products().stream())
                        .collect(Collectors.groupingBy(p -> p, Collectors.counting()))
                        .entrySet().stream()
                        .anyMatch(e -> e.getValue() > 1))
                .toList();

        /*//TODO
        4. Calculate Customer Retention Based on Order Intervals
        For each customer, calculate the number of days between their first and last order — flag those returning customers whose duration exceeds 6 months.

        5. Find the Product with the Widest Price Variance Across All Orders
        Compute the standard deviation or variance of prices for each product (if price varies between orders), and identify the product with the highest spread.

        6. Find the Customer Whose Orders Include Products from the Most Unique Categories
        Return the customer who has purchased products across the most distinct categories.

        7. Top 3 Most Frequently Bought Products Overall
        Determine the three products that appear most frequently across all orders (by quantity or occurrence).

        8. List of Inactive Customers (No Orders in the Past Year)
        Identify customers who haven’t placed an order in the last 12 months.

        9. Rank Customers by Average Spend Per Order and Return Top N
        Return the top N customers (e.g., 5) sorted by their average order value.

        10. Generate a Report of Monthly Revenue per Category
        For each month, compute total revenue per product category:
        Map<YearMonth, Map<String, Double>>
        (YearMonth → Category → Total Revenue)

        ➕ 4 Tasks Using Different Ways to Create Streams
        11. Generate a Stream of Recent N Orders Using Stream.iterate
        Use Stream.iterate() to simulate time-ordered orders and collect the latest N orders into a list.*/
        Product p1 = new Product("Laptop", "Electronics", 1200.0);
        List<Order> ordersSimulation = Stream.iterate(new Order(1, LocalDate.now(), List.of(p1)), o -> new Order(o.orderId() + 1, o.date().minusDays(1), List.of(p1)))
                .limit(5)
                .toList();


        /*
        12. Use Stream.generate() to Simulate Dummy Customers for Testing
        Generate a list of 100 random Customer objects using Stream.generate().

        13. Use Pattern.splitAsStream() to Create Product Tags
        Split product descriptions using regex and flatten to a unique list of keywords across all products.

        14. Read Orders from a File Using Files.lines() and Convert to Objects
        Use Files.lines() to read raw order data and parse into Order objects using a mapping function.*/

    }

    private static List<Customer> getCustomers() {
        Product p1 = new Product("Laptop", "Electronics", 1200.0);
        Product p2 = new Product("Phone", "Electronics", 800.0);
        Product p3 = new Product("Table", "Furniture", 150.0);
        Product p4 = new Product("Chair", "Furniture", 80.0);
        Product p5 = new Product("T-shirt", "Clothing", 20.0);
        Product p6 = new Product("Headphones", "Electronics", 100.0);
        Product p7 = new Product("Sofa", "Furniture", 500.0);
        Product p8 = new Product("Smartwatch", "Electronics", 200.0);
        Product p9 = new Product("Jeans", "Clothing", 50.0);

        Order order1 = new Order(101, LocalDate.of(2022, 3, 1), List.of(p1, p2, p6));
        Order order2 = new Order(102, LocalDate.of(2024, 4, 10), List.of(p3, p4, p7));
        Order order3 = new Order(103, LocalDate.of(2025, 3, 15), List.of(p5, p9));
        Order order4 = new Order(104, LocalDate.of(2024, 2, 20), List.of(p2, p8));
        Order order5 = new Order(105, LocalDate.of(2024, 3, 25), List.of(p1, p7, p9, p6));

        return List.of(
                new Customer(1, "Alice", List.of(order1, order3)),
                new Customer(2, "Bob", List.of(order2)),
                new Customer(3, "Charlie", List.of(order4, order5)));
    }
}
