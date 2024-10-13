package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;

public class Warehouse {
    private static final Map<String, Warehouse> warehouseInstances = new HashMap<>();
    private final String name;
    private final Map<UUID, ProductRecord> productCatalog;

    private Warehouse() {
        this.name = "Warehouse";
        this.productCatalog = new LinkedHashMap<>();
    }

    private Warehouse(String name) {
        this.name = name;
        this.productCatalog = new LinkedHashMap<>();
    }

    public static Warehouse getInstance() {
        return new Warehouse();
    }

    public static Warehouse getInstance(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Warehouse name can't be null or empty");
        }
        return warehouseInstances.computeIfAbsent(name, Warehouse::new);
    }

    public String getName() {
        return name;
    }

    public boolean isEmpty() {
        return productCatalog.isEmpty();
    }

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(new ArrayList<>(productCatalog.values()));
    }

    public ProductRecord addProduct(UUID uuid, String productName, Category category, BigDecimal price) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }

        if (uuid == null) {
            uuid = UUID.randomUUID();
        }

        BigDecimal finalPrice = (price != null) ? price : BigDecimal.ZERO;
        ProductRecord newProduct = new ProductRecord(uuid, productName, category, finalPrice);
        productCatalog.put(uuid, newProduct);
        return newProduct;
    }
}