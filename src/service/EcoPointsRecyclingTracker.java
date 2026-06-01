package service;

import model.Household;
import model.RecyclingEvent;

import java.io.*;
import java.util.*;

/**
 * Main service class for the Eco-Points Recycling Tracker.
 * Handles all user interactions, data management, and report generation.
 */
public class EcoPointsRecyclingTracker {

    private static Scanner scanner = new Scanner(System.in);

    /** Stores all registered households mapped by their unique ID. */
    private static Map<String, Household> households = new HashMap<>();

    /**
     * Loads saved data, then runs the main menu loop until the user exits.
     */
    public void run() {
        loadHouseholdsFromFile();

        boolean running = true;
        while (running) {
            System.out.println("\n=== Eco-Points Recycling Tracker ===");
            System.out.println("1. Register Household");
            System.out.println("2. Log Recycling Event");
            System.out.println("3. Display Households");
            System.out.println("4. Display Household Recycling Events");
            System.out.println("5. Generate Reports");
            System.out.println("6. Save and Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": registerHousehold(); break;
                case "2": logRecyclingEvent(); break;
                case "3": displayHouseholds(); break;
                case "4": displayHouseholdEvents(); break;
                case "5": generateReports(); break;
                case "6":
                    saveHouseholdsToFile();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Prompts the user to enter household details and registers a new household.
     * Rejects duplicate IDs.
     */
    private static void registerHousehold() {
        System.out.print("Enter household ID: ");
        String id = scanner.nextLine().trim();

        if (households.containsKey(id)) {
            System.out.println("Error: Household ID already exists.");
            return;
        }

        System.out.print("Enter household name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter household address: ");
        String address = scanner.nextLine().trim();

        Household household = new Household(id, name, address);
        households.put(id, household);

        System.out.println("Household registered successfully on " + household.getJoinDate());
    }

    /**
     * Prompts the user to log a recycling event for an existing household.
     * Validates that the weight is a positive number.
     */
    private static void logRecyclingEvent() {
        System.out.print("Enter household ID: ");
        String id = scanner.nextLine().trim();

        Household household = households.get(id);

        if (household == null) {
            System.out.println("Error: Household ID not found.");
            return;
        }

        System.out.print("Enter material type (plastic/glass/metal/paper): ");
        String material = scanner.nextLine().trim();

        double weight = 0.0;

        while (true) {
            try {
                System.out.print("Enter weight in kilograms: ");
                weight = Double.parseDouble(scanner.nextLine());

                if (weight <= 0) throw new IllegalArgumentException();

                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid weight. Must be a positive number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid weight. Must be a positive number.");
            }
        }

        RecyclingEvent event = new RecyclingEvent(material, weight);
        household.addEvent(event);

        System.out.println("Recycling event logged! Points earned: " + event.getEcoPoints());
    }

    /**
     * Displays all registered households with their details.
     */
    private static void displayHouseholds() {
        if (households.isEmpty()) {
            System.out.println("No households registered.");
            return;
        }

        System.out.println("\nRegistered Households:");
        for (Household h : households.values()) {
            System.out.println("ID: " + h.getId() +
                    ", Name: " + h.getName() +
                    ", Address: " + h.getAddress() +
                    ", Joined: " + h.getJoinDate());
        }
    }

    /**
     * Displays all recycling events for a specific household,
     * including total weight recycled and total eco points earned.
     */
    private static void displayHouseholdEvents() {
        System.out.print("Enter household ID: ");
        String id = scanner.nextLine().trim();

        Household household = households.get(id);

        if (household == null) {
            System.out.println("Household not found.");
            return;
        }

        System.out.println("\nRecycling Events for " + household.getName() + ":");

        if (household.getEvents().isEmpty()) {
            System.out.println("No events logged.");
        } else {
            for (RecyclingEvent e : household.getEvents()) {
                System.out.println(e);
            }
            System.out.println("Total Weight: " + household.getTotalWeight() + " kg");
            System.out.println("Total Points: " + household.getTotalPoints() + " pts");
        }
    }

    /**
     * Generates a summary report showing the household with the highest eco points
     * and the total recycling weight across all households.
     */
    private static void generateReports() {
        if (households.isEmpty()) {
            System.out.println("No households registered.");
            return;
        }

        Household top = null;
        for (Household h : households.values()) {
            if (top == null || h.getTotalPoints() > top.getTotalPoints()) {
                top = h;
            }
        }

        System.out.println("\nHousehold with Highest Points:");
        System.out.println("ID: " + top.getId() +
                ", Name: " + top.getName() +
                ", Points: " + top.getTotalPoints());

        double totalWeight = 0.0;
        for (Household h : households.values()) {
            totalWeight += h.getTotalWeight();
        }

        System.out.println("Total Community Recycling Weight: " + totalWeight + " kg");
    }

    /**
     * Serializes and saves the households map to a file named households.ser.
     */
    private static void saveHouseholdsToFile() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("households.ser")
            );
            out.writeObject(households);
            out.close();
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Deserializes and loads the households map from households.ser if it exists.
     * Starts with empty data if no save file is found.
     */
    @SuppressWarnings("unchecked")
    private static void loadHouseholdsFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("households.ser"))) {
            households = (Map<String, Household>) in.readObject();
            System.out.println("Household data loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}