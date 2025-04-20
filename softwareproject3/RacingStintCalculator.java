package softwareproject3;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RacingStintCalculator {

    private enum RaceMode {
        TIME_TRIAL("Time Trial"), 
        CIRCUIT_RACE("Circuit Race");
        
        private final String displayName;
        
        RaceMode(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }

    private static final String TIME_FORMAT = "HH:MM:SS";
    private static final String DURATION_FORMAT = "HH:MM";
    private static final String LAP_TIME_FORMAT = "MM:SS";

    private final RaceMode raceMode;
    private final LocalTime startTime;
    private final Duration raceParam;
    private final Duration lapTime;
    private final int driverCount;
    private final double fuelCapacity;
    private final double refuelRate;
    private final Duration pitStopTime;
    private Duration pauseTime = Duration.ZERO;
    private Duration delayAdjustment = Duration.ZERO;

    public RacingStintCalculator(RaceMode raceMode, LocalTime startTime, Duration raceParam,
                                  Duration lapTime, int driverCount, double fuelCapacity,
                                  double refuelRate, Duration pitStopTime) {
        this.raceMode = raceMode;
        this.startTime = startTime;
        this.raceParam = raceParam;
        this.lapTime = lapTime;
        this.driverCount = driverCount;
        this.fuelCapacity = fuelCapacity;
        this.refuelRate = refuelRate;
        this.pitStopTime = pitStopTime;
    }

    public void addPause(Duration pauseDuration) {
        this.pauseTime = this.pauseTime.plus(pauseDuration);
    }

    public void adjustForDelay(Duration delayDuration) {
        this.delayAdjustment = this.delayAdjustment.plus(delayDuration);
    }

    public Duration calculateTotalTime() {
        Duration baseDuration = raceMode == RaceMode.TIME_TRIAL 
            ? calculateTimeTrialDuration() 
            : calculateCircuitRaceDuration();
        return baseDuration.plus(pauseTime).plus(delayAdjustment);
    }

    private Duration calculateTimeTrialDuration() {
        long totalLaps = raceParam.toSeconds() / lapTime.toSeconds();
        double fuelUsed = totalLaps * (fuelCapacity / 10);
        Duration refuelTime = Duration.ofSeconds((long)(fuelUsed / refuelRate));
        Duration totalPitTime = refuelTime.plus(pitStopTime);
        Duration driverChangeTime = driverCount > 1 ? Duration.ofSeconds(30) : Duration.ZERO;

        return raceParam.plus(totalPitTime).plus(driverChangeTime);
    }

    private Duration calculateCircuitRaceDuration() {
        long totalLaps = raceParam.toMinutes();
        Duration raceTime = lapTime.multipliedBy(totalLaps);
        double fuelUsed = totalLaps * (fuelCapacity / 10);
        Duration refuelTime = Duration.ofSeconds((long)(fuelUsed / refuelRate));
        Duration totalPitTime = refuelTime.plus(pitStopTime);
        Duration driverChangeTime = driverCount > 1 ? Duration.ofSeconds(30) : Duration.ZERO;

        return raceTime.plus(totalPitTime).plus(driverChangeTime);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== RACING STINT CALCULATOR ===");

        try {
            RaceMode mode = promptForRaceMode(scanner);
            LocalTime startTime = promptForTime(scanner, 
                "Race start time (" + TIME_FORMAT + "): ", TIME_FORMAT);

            String paramPrompt = mode == RaceMode.TIME_TRIAL 
                ? "Race duration (" + DURATION_FORMAT + "): "
                : "Total number of laps: ";
            Duration raceParam = mode == RaceMode.TIME_TRIAL
                ? promptForDuration(scanner, paramPrompt)
                : Duration.ofMinutes(promptForInt(scanner, paramPrompt, 1, 500));

            Duration lapTime = promptForDuration(scanner, 
                "Lap time (" + LAP_TIME_FORMAT + "): ");

            int drivers = promptForInt(scanner, 
                "Number of drivers: ", 1, 10);

            double fuelTank = promptForDouble(scanner, 
                "Fuel tank capacity (liters): ", 1, 200);

            double refuelSpeed = promptForDouble(scanner, 
                "Refuel rate (liters/sec): ", 0.1, 20);

            Duration pitStop = promptForDuration(scanner, 
                "Pit stop time (" + LAP_TIME_FORMAT + "): ");

            RacingStintCalculator calculator = new RacingStintCalculator(
                mode, startTime, raceParam, lapTime, drivers, 
                fuelTank, refuelSpeed, pitStop);

            System.out.print("Enter pause duration (MM:SS): ");
            Duration pauseDuration = promptForDuration(scanner, "Pause duration (MM:SS): ");
            calculator.addPause(pauseDuration);

            System.out.print("Enter delay adjustment duration (MM:SS): ");
            Duration delayDuration = promptForDuration(scanner, "Delay adjustment duration (MM:SS): ");
            calculator.adjustForDelay(delayDuration);

            displayResults(calculator);

        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static RaceMode promptForRaceMode(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Select race mode:");
                for (int i = 0; i < RaceMode.values().length; i++) {
                    System.out.printf("%d. %s\n", i + 1, RaceMode.values()[i].getDisplayName());
                }
                System.out.print("Your choice (1-2): ");

                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= RaceMode.values().length) {
                    return RaceMode.values()[choice - 1];
                }
                throw new InputMismatchException();
            } catch (Exception e) {
                System.err.println("Invalid selection! Please enter 1 or 2");
            }
        }
    }

    private static LocalTime promptForTime(Scanner scanner, String prompt, String format) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalTime.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.err.printf("Invalid format! Please use %s (e.g. %s)\n", 
                    format, format.equals(TIME_FORMAT) ? "14:30:00" : "01:45");
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
                    throw new InputMismatchException(
                        String.format("Must be between %d and %d", min, max));
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
                    throw new InputMismatchException(
                        String.format("Must be between %.1f and %.1f", min, max));
                }
                return value;
            } catch (Exception e) {
                System.err.println("Invalid input: " + e.getMessage());
            }
        }
    }

    private static void displayResults(RacingStintCalculator calculator) {
        try {
            Duration totalTime = calculator.calculateTotalTime();
            LocalTime endTime = calculator.startTime.plus(totalTime);

            System.out.println("\n=== CALCULATION RESULTS ===");
            System.out.println("Race mode: " + calculator.raceMode.getDisplayName());

            if (calculator.raceMode == RaceMode.CIRCUIT_RACE) {
                System.out.println("Total laps: " + calculator.raceParam.toMinutes());
            } else if (calculator.raceMode == RaceMode.TIME_TRIAL) {
                System.out.printf("Race duration: %02d:%02d:%02d\n",
                    calculator.raceParam.toHours(),
                    calculator.raceParam.toMinutesPart(),
                    calculator.raceParam.toSecondsPart());
            }

            System.out.printf("Total race time: %02d:%02d:%02d\n",
                totalTime.toHours(),
                totalTime.toMinutesPart(),
                totalTime.toSecondsPart());

            System.out.println("Projected finish time: " + endTime);

            System.out.printf("Total pause time: %02d:%02d:%02d\n",
                calculator.pauseTime.toHours(),
                calculator.pauseTime.toMinutesPart(),
                calculator.pauseTime.toSecondsPart());

            System.out.printf("Total delay adjustment: %02d:%02d:%02d\n",
                calculator.delayAdjustment.toHours(),
                calculator.delayAdjustment.toMinutesPart(),
                calculator.delayAdjustment.toSecondsPart());

            double estimatedPitStops = calculator.raceMode == RaceMode.TIME_TRIAL
                ? (double) calculator.raceParam.toSeconds() / (45 * 60)
                : calculator.raceParam.toMinutes() * calculator.lapTime.toMinutes() / 45.0;

            System.out.printf("Estimated pit stops: %.1f\n", estimatedPitStops);
        } catch (Exception e) {
            throw new RuntimeException("Calculation error: " + e.getMessage());
        }
    }
}
