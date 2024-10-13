package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private static final Map<String, Warehouse> warehouseInstances = new HashMap<>();
    private final String name;
    private final Map<UUID, ProductRecord> productCatalog; // Byt till LinkedHashMap för att bevara ordning
    private final List<ProductRecord> modifiedProducts;

    private Warehouse() {
        this.name = "Warehouse";
        this.productCatalog = new LinkedHashMap<>(); // Använd LinkedHashMap
        this.modifiedProducts = new ArrayList<>();
    }

    private Warehouse(String name) {
        this.name = name;
        this.productCatalog = new LinkedHashMap<>(); // Använd LinkedHashMap
        this.modifiedProducts = new ArrayList<>();
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

        // Kontrollera om produkten redan finns
        if (uuid != null && productCatalog.containsKey(uuid)) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }

        // Generera ett nytt UUID om det inte tillhandahålls
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }

        // Sätt priset till 0 om det är null
        BigDecimal finalPrice = (price != null) ? price : BigDecimal.ZERO;

        ProductRecord newProduct = new ProductRecord(uuid, productName, category, finalPrice);
        productCatalog.put(uuid, newProduct);
        return newProduct;
    }

    public Optional<ProductRecord> getProductById(UUID uuid) {
        return Optional.ofNullable(productCatalog.get(uuid));
    }

    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        ProductRecord existingProduct = getProductById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Product with that id doesn't exist."));

        modifiedProducts.add(existingProduct);
        productCatalog.remove(uuid);
        addProduct(uuid, existingProduct.name(), existingProduct.category(), newPrice);
    }

    public List<ProductRecord> getChangedProducts() {
        return Collections.unmodifiableList(modifiedProducts);
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return productCatalog.values().stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
    }

    public List<ProductRecord> getProductsBy(Category category) {
        return getProductsGroupedByCategories().getOrDefault(category, Collections.emptyList());
    }
}