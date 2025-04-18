package softwareproject;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StintTimeCalculator {
    
    // Configuration constants
    private static final String TIME_FORMAT = "HH:MM:SS";
    private static final String DURATION_FORMAT = "HH:MM";
    private static final String LAP_TIME_FORMAT = "MM:SS";
    
    private final LocalTime startTime;
    private final Duration raceDuration;
    private final Duration lapTime;
    private final int driverCount;
    private final double fuelCapacity;
    private final double refuelRate;
    private final Duration pitStopTime;
    
    public StintTimeCalculator(LocalTime startTime, Duration raceDuration, Duration lapTime,
                             int driverCount, double fuelCapacity,
                             double refuelRate, Duration pitStopTime) {
        this.startTime = startTime;
        this.raceDuration = raceDuration;
        this.lapTime = lapTime;
        this.driverCount = driverCount;
        this.fuelCapacity = fuelCapacity;
        this.refuelRate = refuelRate;
        this.pitStopTime = pitStopTime;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== RACING STINT CALCULATOR ===");
        
        try {
            LocalTime startTime = promptForTime(scanner, "Race start time (" + TIME_FORMAT + "): ", TIME_FORMAT);
            Duration duration = promptForDuration(scanner, "Race duration (" + DURATION_FORMAT + "): ");
            Duration lapTime = promptForDuration(scanner, "Lap time (" + LAP_TIME_FORMAT + "): ");
            int drivers = promptForInt(scanner, "Number of drivers: ", 1, 10);
            double fuelTank = promptForDouble(scanner, "Fuel tank capacity (liters): ", 1, 200);
            double refuelSpeed = promptForDouble(scanner, "Refuel rate (liters/sec): ", 0.1, 20);
            Duration pitStop = promptForDuration(scanner, "Pit stop time (" + LAP_TIME_FORMAT + "): ");
            
            StintTimeCalculator calculator = new StintTimeCalculator(
                startTime, duration, lapTime, drivers, fuelTank, refuelSpeed, pitStop);
            
            displayResults(calculator);
            
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    public Duration calculateTotalTime() {
        // Calculate total laps
        long totalLaps = raceDuration.toSeconds() / lapTime.toSeconds();
        
        // Estimate fuel consumption (simplified)
        double fuelUsed = totalLaps * (fuelCapacity / 10); // Assuming 10 laps per tank
        
        // Calculate pit stop components
        Duration refuelTime = Duration.ofSeconds((long)(fuelUsed / refuelRate));
        Duration totalPitTime = refuelTime.plus(pitStopTime);
        
        // Add driver change time if needed
        Duration driverChangeTime = driverCount > 1 ? Duration.ofSeconds(30) : Duration.ZERO;
        
        return raceDuration.plus(totalPitTime).plus(driverChangeTime);
    }
    
    // ========== INPUT VALIDATION METHODS ==========
    private static LocalTime promptForTime(Scanner scanner, String prompt, String format) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return LocalTime.parse(input);
            } catch (DateTimeParseException e) {
                System.err.println("Invalid format! Please use " + format + " format (e.g. " + 
                    (format.equals(TIME_FORMAT) ? "14:30:00" : "01:45") + ")");
            }
        }
    }
    
    private static Duration promptForDuration(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String[] parts = scanner.nextLine().trim().split(":");
                
                if (parts.length != 2) {
                    throw new InputMismatchException("Must use HH:MM or MM:SS format");
                }
                
                long first = Long.parseLong(parts[0]);
                long second = Long.parseLong(parts[1]);
                
                if (second >= 60) {
                    throw new InputMismatchException("Minutes/seconds must be < 60");
                }
                
                return prompt.contains("HH:MM") 
                    ? Duration.ofHours(first).plusMinutes(second)
                    : Duration.ofMinutes(first).plusSeconds(second);
                
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
    
    private static int promptForInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                if (value < min || value > max) {
                    throw new InputMismatchException("Must be between " + min + " and " + max);
                }
                return value;
            } catch (Exception e) {
                System.err.println("Invalid input: " + e.getMessage());
            }
        }
    }
    
    private static double promptForDouble(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                if (value < min || value > max) {
                    throw new InputMismatchException("Must be between " + min + " and " + max);
                }
                return value;
            } catch (Exception e) {
                System.err.println("Invalid input: " + e.getMessage());
            }
        }
    }
    
    // ========== OUTPUT METHODS ==========
    private static void displayResults(StintTimeCalculator calculator) {
        try {
            Duration totalTime = calculator.calculateTotalTime();
            LocalTime endTime = calculator.startTime.plus(totalTime);
            
            System.out.println("\n=== CALCULATION RESULTS ===");
            System.out.printf("Total race time: %02d:%02d:%02d%n",
                totalTime.toHours(),
                totalTime.toMinutesPart(),
                totalTime.toSecondsPart());
            System.out.println("Projected finish time: " + endTime);
            System.out.printf("Estimated pit stops: %.1f%n",
                calculator.raceDuration.toMinutes() / 45.0); // Example estimation
            
        } catch (Exception e) {
            throw new RuntimeException("Calculation error: " + e.getMessage());
        }
    }
}