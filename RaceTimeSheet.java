import java.util.Scanner;

public class RaceTimeSheet {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input
        System.out.print("Enter start time of the race (HH:MM): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter duration of the race (hours): ");
        int duration = scanner.nextInt();
        System.out.print("Enter fuel tank capacity (liters): ");
        double fuelCapacity = scanner.nextDouble();
        System.out.print("Enter fuel consumption rate (liters per lap): ");
        double fuelConsumption = scanner.nextDouble();
        System.out.print("Enter pitlane duration (seconds): ");
        int pitlaneDuration = scanner.nextInt();
        System.out.print("Enter tyre change time (seconds): ");
        int tyreChangeTime = scanner.nextInt();
        System.out.print("Enter refuel speed (liters per second): ");
        double refuelSpeed = scanner.nextDouble();
        System.out.print("Enter average lap time (MM:SS): ");
        String averageLapTime = scanner.next();

        // Convert average lap time to seconds
        String[] lapTimeParts = averageLapTime.split(":");
        int lapTimeSeconds = Integer.parseInt(lapTimeParts[0]) * 60 + Integer.parseInt(lapTimeParts[1]);

        // Convert start time to minutes
        String[] startParts = startTime.split(":");
        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);
        int totalMinutes = duration * 60;
        int currentHour = startHour, currentMinute = startMinute;

        int totalLaps = (totalMinutes * 60) / lapTimeSeconds;
        int lapsPerStint = (int) Math.floor(fuelCapacity / fuelConsumption);
        int stintCount = totalLaps / lapsPerStint;
        int remainingLaps = totalLaps % lapsPerStint;
        String[] drivers = new String[stintCount + (remainingLaps > 0 ? 1 : 0)];

        System.out.println("\nGenerated Time Sheet:");
        for (int i = 0; i < stintCount; i++) {
            int fuelToAdd = (int) Math.ceil(lapsPerStint * fuelConsumption);
            int pitstopTime = Math.max(tyreChangeTime, (int) Math.ceil(refuelSpeed * lapsPerStint * fuelConsumption)) + pitlaneDuration;

            currentMinute += (lapsPerStint * lapTimeSeconds) / 60;
            while (currentMinute >= 60) {
                currentHour++;
                currentMinute -= 60;
            }

            System.out.printf("Stint %d: Stint: %02d:%02d - %02d:%02d, Driver: TBD, Laps: %d, Fuel to Add: %d liters, Pitstop Time: %d seconds\n",
                    i + 1, startHour, startMinute, currentHour, currentMinute, lapsPerStint, fuelToAdd, pitstopTime);

            startHour = currentHour;
            startMinute = currentMinute;
        }

        // Adjust last stint
        if (remainingLaps > 0) {
            int fuelToAdd = (int) Math.ceil(remainingLaps * fuelConsumption);
            int pitstopTime = Math.max(tyreChangeTime, (int) Math.ceil(refuelSpeed * remainingLaps * fuelConsumption)) + pitlaneDuration;

            currentMinute += (remainingLaps * lapTimeSeconds) / 60;
            while (currentMinute >= 60) {
                currentHour++;
                currentMinute -= 60;
            }

            System.out.printf("Last Stint: Stint: %02d:%02d - %02d:%02d, Driver: TBD, Laps: %d, Fuel to Add: %d liters, Pitstop Time: %d seconds\n",
                    startHour, startMinute, currentHour, currentMinute, remainingLaps, fuelToAdd, pitstopTime);
        }

        // Allow user to assign drivers
        scanner.nextLine(); // Consume leftover newline
        System.out.println("\nEnter the driver for each stint:");
        for (int i = 0; i < drivers.length; i++) {
            System.out.printf("Enter driver for Stint %d: ", i + 1);
            drivers[i] = scanner.nextLine();
        }

        // Display final schedule with drivers
        System.out.println("\nFinal Time Sheet with Drivers:");
        startHour = Integer.parseInt(startParts[0]);
        startMinute = Integer.parseInt(startParts[1]);
        currentHour = startHour;
        currentMinute = startMinute;

        for (int i = 0; i < stintCount; i++) {
            int fuelToAdd = (int) Math.ceil(lapsPerStint * fuelConsumption);
            int pitstopTime = Math.max(tyreChangeTime, (int) Math.ceil(refuelSpeed * lapsPerStint * fuelConsumption)) + pitlaneDuration;

            currentMinute += (lapsPerStint * lapTimeSeconds) / 60;
            while (currentMinute >= 60) {
                currentHour++;
                currentMinute -= 60;
            }

            System.out.printf("Stint %d: Stint: %02d:%02d - %02d:%02d, Driver: %s, Laps: %d, Fuel to Add: %d liters, Pitstop Time: %d seconds\n",
                    i + 1, startHour, startMinute, currentHour, currentMinute, drivers[i], lapsPerStint, fuelToAdd, pitstopTime);

            startHour = currentHour;
            startMinute = currentMinute;
        }

        if (remainingLaps > 0) {
            int fuelToAdd = (int) Math.ceil(remainingLaps * fuelConsumption);
            int pitstopTime = Math.max(tyreChangeTime, (int) Math.ceil(refuelSpeed * remainingLaps * fuelConsumption)) + pitlaneDuration;

            currentMinute += (remainingLaps * lapTimeSeconds) / 60;
            while (currentMinute >= 60) {
                currentHour++;
                currentMinute -= 60;
            }

            System.out.printf("Last Stint: Stint: %02d:%02d - %02d:%02d, Driver: %s, Laps: %d, Fuel to Add: %d liters, Pitstop Time: %d seconds\n",
                    startHour, startMinute, currentHour, currentMinute, drivers[drivers.length - 1], remainingLaps, fuelToAdd, pitstopTime);
        }

        scanner.close();
    }
}
