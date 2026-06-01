# EcoPoints Recycling Tracker

A console-based Java application that tracks household recycling activities and calculates eco points. Households earn 10 points per kilogram recycled, with data persisted between sessions.

## Features

- Register households with unique IDs
- Log recycling events by material type and weight
- Earn 10 eco points per kilogram recycled
- View all registered households and their recycling history
- Generate community reports
- Persist data between sessions using Java serialization

## How to Run

**Compile:**
```
javac -d out src/Main.java src/model/Household.java src/model/RecyclingEvent.java src/service/EcoPointsRecyclingTracker.java
```

**Run:**
```
java -cp out Main
```

## Menu Options

| Option | Description |
|--------|-------------|
| 1 | Register a new household |
| 2 | Log a recycling event |
| 3 | Display all households |
| 4 | Display household recycling events |
| 5 | Generate reports |
| 6 | Save and exit |

## Project Structure

```
src/
├── model/
│   ├── Household.java
│   └── RecyclingEvent.java
├── service/
│   └── EcoPointsRecyclingTracker.java
└── Main.java
```

## Technologies

- Java SE (`java.time`, `java.util`, `java.io`)

## Concepts Practiced

- Object-Oriented Programming (OOP)
- Encapsulation
- Collections: `ArrayList` and `HashMap`
- Java Date & Time API
- File I/O and Serialization
- Exception Handling
- Console-based UI with Scanner

## Data Persistence

Household and recycling data is saved to `households.ser` on exit and automatically reloaded on the next run.
