# Stint Time Calculator 

## 1. Graphical Abstract


## 2. Purpose of the Software 
The Stint Time Calculator is designed to accurately calculate stint durations. For endurance racing, it is very critical to have an accurate and precise stint time and stint information for drivers and the team. The software asks user to input the required data so that the software is able to precisely calculate the stint information. User also required to place a driver in each time slot. After, the information are outputted on a time sheet with all the essential information in it. 

### Development Methodology
- The development followed the Waterfall approach, ensuring the software is in good quality and without bugs. 

### Target Users & Applications
- Engineers tracking operational runtimes.
- Drivers in endurance racing
- Teams analyzing performance splits.
- Simracers 

## 3. Software Development Plan
### Development Process
- Specification: Defining project requirements and goals.
- Design: Creating the user interface.
- Implementation: Coding and integrating the core algorithms.
- Testing: Validating accuracy and performance of the software.
- Enhancement: Make updates to the software.
- Deployment: Publishing the tool for user access.

### Team Members
- Designer: Responsible for designing the UI of the software
- Developer 1: 
- Developer 2: 
- Developer 3: 
- Manager: Manage the whole process of software devlopement

### Schedule
- Month 1: Specification & Design
- Month 2: Coding & Testing
- Month 3: Enhancement & Deployment

### Algorithm
**1. Input Data Collection:**
  * Collect user inputs:
    * Start time of the race
    * Duration of the race
    * Fuel tank capacity
    * Fuel consumption rate
    * Pitlane duration
    * Tyre change time
    * Driver swap time
    * Refuel speed
    * Drivers & their corresponding lap times
    * Tyre choice

**2. Initialization:**
  * Initialize variables for tracking time, fuel, and stints.
    
**3. Stint Calculation:**
  * Calculate the number of laps each driver can complete based on fuel consumption and tank capacity.
  * Determine the start and end time for each stint.
  * Calculate the fuel required for each stint.
  * Calculate the pitstop duration based on tyre change time, driver swap time, and refuel speed.
    
**4. Output Generation:**
  * Generate a time sheet with:
    * Start & end time for each stint
    * Fuel to be added for each stint
    * Pitstop duration

### Current Status
The software is in implementation stage. The basic coding and algorithm of the software are finished. It is currently runnable in a CLI. We aim to convert the software to be usable on a GUI in the upcoming week. 

### Future Plans
- Adding AI-powered predictive analytics to return the best strategy option.
- Enhancing mobile compatibility to be usable on a phone.
