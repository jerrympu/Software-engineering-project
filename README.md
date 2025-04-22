# Stint Time Calculator Documentation

## 1. Graphical Abstract
(photo of the UI)
Demo Video: (link here)

## 2. Purpose of the Software
The Stint Time Calculator is designed to accurately calculate stint durations for endurance racing. Accurate stint information is critical for drivers and teams to manage race strategies effectively. The software requires user input to calculate stint information precisely and outputs a detailed time sheet with all essential information.

### Development Method
The development followed the Waterfall approach.

### Reason
- Clarity and Structure:
The Waterfall model provides a clear and structured approach to software development. Each phase has specific deliverables and milestones, making it easier to manage and track progress.

- Defined Requirements:
In the early stages of the project, the requirements were well-defined and unlikely to change significantly. The Waterfall approach is ideal for projects with stable requirements, as it allows for thorough planning and design before implementation begins.

- Ease of Management:
The sequential nature of the Waterfall model simplifies project management. Each phase must be completed before the next begins, reducing the complexity of managing overlapping tasks and dependencies.

- Quality Assurance:
By conducting thorough testing after the implementation phase, the Waterfall model ensures that the software is rigorously validated before deployment. This helps to identify and resolve issues early, resulting in a more reliable and stable product.

### Target Users & Applications
- **Engineers & drivers**: Planning the best strategy for drivers with changing conditions for and during the race.
- **Simracers**: Enhancing virtual racing strategies.

## 3. Software Development Plan
### Development Process
1. **Specification**: Defining project requirements and goals.
2. **Design**: Creating the user interface.
3. **Implementation**: Coding and integrating the core algorithms.
4. **Testing**: Validating the accuracy and performance of the software.
5. **Enhancement**: Making updates to the software based on feedback.
6. **Deployment**: Publishing the tool for user access.

### Team Members
- **Designer**: Responsible for designing the UI of the software.
- **Developer 1**: Focuses on backend development.
- **Developer 2**: Handles frontend development.
- **Developer 3**: Integrates algorithms and ensures functionality.
- **Manager**: Manages the entire software development process.

### Schedule
- **Month 1**: Specification & Design
  - Define requirements and goals.
  - Design the user interface.
- **Month 2-3**: Coding & Testing
  - Implement core algorithms.
  - Conduct unit and integration testing.
- **Month 4**: Enhancement & Deployment
  - Make necessary updates.
  - Deploy the software for user access.

### Algorithm
**1. Input Data Collection:**
  - Collect user inputs:
    - Start time of the race
    - Duration of the race
    - Fuel tank capacity
    - Fuel consumption rate
    - Pitlane duration
    - Tyre change time
    - Refuel speed
    - Lap times

**2. Initialization:**
  - Initialize variables for tracking time, fuel, and stints.

**3. Stint Calculation:**
  - Calculate the number of laps driver can complete based on fuel consumption and tank capacity.
  - Determine the start and end time for each stint.
  - Calculate the fuel required for each stint.
  - Calculate the pitstop duration based on tyre change time and refuel speed.

**4. Output Generation:**
  - Generate a time sheet with:
    - Start & end time for each stint
    - Fuel to be added for each stint
    - Pitstop duration
    - Allow user to assign drivers to each time slot

### Current Status
The software is currently in the coding & testing stage. The basic coding and algorithm are complete, and it is currently runnable in a CLI. The next step is to convert the software to a GUI within the upcoming week.

### Future Plans
- **Cloud implementation**: Allow multiple users to work on one schedule at the same time.
- **AI-Powered Predictive Analytics**: Integrate AI to suggest optimal strategies based on historical data and real-time inputs.
- **Mobile Compatibility**: Enhance the software to be usable on mobile devices, providing flexibility for users on the go.

