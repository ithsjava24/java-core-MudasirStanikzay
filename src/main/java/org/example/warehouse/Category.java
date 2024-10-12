package org.example.warehouse;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class Category {
    private final String name;  // Kategorins namn
    private static final Map<String, Category> categories = new ConcurrentHashMap<>(); // Trådsäker karta för kategorier


    private Category(String name) {
        this.name = name;
    }

    public static Category of(String name) throws IllegalArgumentException {
        validateName(name);  // Validera namnet via en hjälpmetod

        String formattedName = capitalizeFirstLetter(name);  // Kapitalisera första bokstaven

        return categories.computeIfAbsent(formattedName, Category::new); // Återanvänd befintlig eller skapa ny
    }

    public String getName() {
        return name;
    }

    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name can't be null");
        }
    }

    private static String capitalizeFirstLetter(String input) {
        String trimmed = input.trim();
        return trimmed.substring(0, 1).toUpperCase() + trimmed.substring(1).toLowerCase();
    }
}