package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a household participating in the Eco-Points Recycling Program.
 * Stores household details, recycling events, and total eco points earned.
 */
public class Household implements Serializable {

    private String id;
    private String name;
    private String address;
    private LocalDate joinDate;
    private List<RecyclingEvent> events;
    private double totalPoints;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Creates a new Household with the given details.
     * Join date is automatically set to today.
     */
    public Household(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.joinDate = LocalDate.now();
        this.events = new ArrayList<>();
        this.totalPoints = 0.0;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getJoinDate() { return joinDate.format(FORMATTER); }
    public List<RecyclingEvent> getEvents() { return events; }
    public double getTotalPoints() { return totalPoints; }

    /**
     * Adds a recycling event to this household and updates the total points balance.
     */
    public void addEvent(RecyclingEvent event) {
        this.events.add(event);
        this.totalPoints += event.getEcoPoints();
    }

    /**
     * Calculates and returns the total weight recycled across all events.
     */
    public double getTotalWeight() {
        double total = 0.0;
        for (RecyclingEvent event : events) {
            total += event.getWeight();
        }
        return total;
    }
}