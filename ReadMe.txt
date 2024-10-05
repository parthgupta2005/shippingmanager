
# Shipping Management System

## Project Overview

The **Shipping Management System** is a Java-based application designed to optimize the packing and management of shipping containers. It was developed to solve the operational inefficiencies of a shipping company in Galveston, Texas, by automating the process of organizing and managing shipments. The system helps the company reduce costs by maximizing packing efficiency and reducing shipment frequency. The project uses Java for the core logic and user interface, MySQL for database management, and various Java libraries to streamline the packing process.

## Table of Contents
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Future Enhancements](#future-enhancements)
- [License](#license)

## Features

- **Optimized Packing Algorithm**: Automatically sorts and arranges crates in containers to maximize space usage, reducing shipping costs.
- **Order and Shipment Management**: Input, update, and track shipping orders, containers, and crates with ease.
- **Efficiency Calculation**: The system calculates packing efficiency, displaying the percentage of space used versus available space in each shipment.
- **Database Storage**: Data is stored in an SQL database, categorized into 'active' and 'archived' orders, providing structured and secure data management.
- **Export to Excel**: Users can export order details and efficiency reports into Excel spreadsheets for easy sharing and analysis.

## Installation

### Prerequisites

Ensure you have the following installed:
- **Java Development Kit (JDK 8 or higher)**
- **MySQL** (for database management)
- **Apache POI** (for Excel export functionality)

### Steps to Install

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd ShippingManagementSystem
   ```

2. **Set up the SQL database**:
   - Install MySQL on your system.
   - Run the provided SQL script to create the necessary tables for 'active' and 'archived' orders.
   - Configure the database connection in the `JavaDatabase` class.

3. **Compile and run the application**:
   ```bash
   javac Main.java
   java Main
   ```

## Running the Program

To run the program, the user must follow one of the following three methods:

1. **Command Prompt/Terminal (Manual Classpath)**:  
   Open the command prompt/terminal after navigating to the Product Directory and type the following command:  
   ```bash
   java -cp .:derby.jar:poi-3.2-FINAL.jar:poi-3.7.jar:dom4j-1.6.1.jar:xmlbeans-2.3.0.jar:poi-scratchpad-3.7.jar:poi-scratchpad-3.7-sources.jar:poi-ooxml-schemas-3.7.jar:poi-ooxml-3.7.jar:poi-examples-3.7.jar Welcome
   ```

2. **Running via JAR**:  
   Open the command prompt/terminal after navigating to the Product Directory and type:  
   ```bash
   java -jar ShippingManager.jar
   ```

3. **Running via Finder**:  
   Navigate to the Product Directory in Finder, and double-click the `ShippingManager.jar` file to run the program.

### Reinstalling the Database

To empty the database and reinstall it, follow these steps:

1. Delete the existing database.
2. In the command prompt/terminal, navigate to the Product Directory and type:
   ```bash
   java -cp .:derby.jar InstallDB
   ```

## Navigating Through the Program

Follow these steps to successfully navigate through the program:

1. After running the program, navigate to the **Menu Bar** and select **"Add Order"**. You will be prompted to add an order by selecting container types and providing a **Container ID** (an integer from 0 to 9999).
2. Once the order is added, you can insert containers by providing dimensional information, the order it belongs to, and the quantity to add.
3. After crates are added, their positions are updated, and you can view the positions in the **Display** frame.
4. You can delete or update containers, archive orders, view order efficiency, and export the information to a spreadsheet.
5. For more support, refer to the **Help** tab in the menu bar on the home page.

## Usage

- **Insert Shipment**: Input shipment data, including container ID, crate dimensions, and destination.
- **Display Orders**: View current and archived shipments, including details about crate placements and packing efficiency.
- **Update Orders**: Modify existing shipment details as needed.
- **Export Data**: Generate Excel reports summarizing shipment efficiency and container usage.

## Technologies

- **Java**: The core programming language used to build the application’s logic and GUI.
- **MySQL**: Used for database management, storing and retrieving shipment details, container IDs, and efficiency data.
- **Apache POI**: Java library used to export shipment and order details into Excel spreadsheets.
- **JDBC (Java Database Connectivity)**: Provides a connection between the Java application and the MySQL database.

## Project Structure

- **Algorithm**: The project uses a custom crate-packing algorithm that ensures optimal space utilization. It checks different orientations for crates to determine the most efficient arrangement within the container.
- **Database Integration**: SQL is used to manage and store all shipment data. Two tables are maintained: `currentTable` for active orders and `efficiencyTable` for efficiency calculations.
- **Efficiency Calculation**: The system calculates efficiency by comparing the total container volume with the volume occupied by crates. The efficiency is stored in the database and displayed for each shipment.
- **Coordinate Mapping**: Each crate is assigned specific coordinates within the container, which are updated in the database and displayed in the interface for user reference.

## Testing

To ensure the system meets the success criteria, the following tests were conducted:
- **User Acceptance Testing**: Clients were given a sample interface to test the program with mock orders. Feedback was collected and implemented.
- **Black Box Testing**: SQL queries and database functionality were tested to ensure that the system stores and retrieves data correctly.
- **White Box Testing**: The packing algorithm was tested by running sample calculations, outputting the process to verify its correctness.
- **Efficiency Testing**: Several test datasets were used to measure the system’s ability to calculate and display the packing efficiency accurately.

## Future Enhancements

- **3D Space Optimization**: Currently, the system solves the packing problem on a 2D plane. Expanding the algorithm to handle 3D space could improve packing efficiency by utilizing container height.
- **Dynamic Container Adjustment**: The system could be enhanced to dynamically adjust for changes in container sizes or the number of containers on a shipment, further improving flexibility.
- **Real-time Updates**: Integration with APIs for real-time updates on shipments and tracking information could make the system more robust.
