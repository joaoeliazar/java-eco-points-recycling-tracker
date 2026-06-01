package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single recycling event logged by a household.
 * Stores the material type, weight, date, and eco points earned.
 */
public class RecyclingEvent implements Serializable {

    private String materialType;
    private double weight;
    private LocalDate date;
    private double ecoPoints;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Creates a new RecyclingEvent.
     * Eco points are automatically calculated at 10 points per kilogram.
     */
    public RecyclingEvent(String materialType, double weight) {
        this.materialType = materialType;
        this.weight = weight;
        this.date = LocalDate.now();
        this.ecoPoints = weight * 10;
    }

    public String getMaterialType() { return materialType; }
    public double getWeight() { return weight; }
    public String getDate() { return date.format(FORMATTER); }
    public double getEcoPoints() { return ecoPoints; }

    /**
     * Returns a formatted string representation of this recycling event.
     */
    @Override
    public String toString() {
        return "Date: " + date.format(FORMATTER) +
                "\nMaterial Type: " + this.materialType +
                "\nWeight: " + this.weight + " kg" +
                "\nEco Points: " + this.ecoPoints;
    }
}