package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductRecord {
    private final UUID uuid;
    private final String name;
    private final Category category;
    private final BigDecimal price;

    public ProductRecord (UUID uuid, String name, Category category, BigDecimal price) {
        if (uuid == null) {
            this.uuid = UUID.randomUUID(); // Tilldela ett nytt UUID om det är null
        } else {
            this.uuid = uuid;
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }

        this.name = name;
        this.category = category;
        this.price = price != null ? price : BigDecimal.ZERO; // Sätt priset till 0 om det är null
    }

    // Getter för UUID
    public UUID uuid() {
        return uuid;
    }

    // Getter för namn
    public String name() {
        return name;
    }

    // Getter för kategori
    public Category category() {
        return category;
    }

    // Getter för pris
    public BigDecimal price() {
        return price;
    }
}