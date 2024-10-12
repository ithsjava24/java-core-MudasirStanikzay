package org.example.warehouse;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class Category {
    private final String name;  // Kategorins namn
    private static final Map<String, Category> categories = new ConcurrentHashMap<>(); // Trådsäker karta för kategorier

    // Privat konstruktor för att hindra direkt instansiering
    private Category(String name) {
        this.name = name;
    }

    // Statiskt factory-metod för att skapa eller återanvända kategorier
    public static Category of(String name) throws IllegalArgumentException {
        validateName(name);  // Validera namnet via en hjälpmetod

        String formattedName = capitalizeFirstLetter(name);  // Kapitalisera första bokstaven

        return categories.computeIfAbsent(formattedName, Category::new); // Återanvänd befintlig eller skapa ny
    }

    // Returnerar kategorins namn
    public String getName() {
        return name;
    }

    // Hjälpmetod för att validera namn
    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name can't be null");
        }
    }

    // Hjälpmetod för att kapitalisera första bokstaven i ett namn
    private static String capitalizeFirstLetter(String input) {
        String trimmed = input.trim();  // Trimma onödiga mellanslag
        return trimmed.substring(0, 1).toUpperCase() + trimmed.substring(1).toLowerCase();
    }
}