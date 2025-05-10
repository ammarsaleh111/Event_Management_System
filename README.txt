# Event Management System

## Description

The Event Management System (EMS) is a desktop application developed using Java and JavaFX. It provides a platform for managing events, user registrations, and interactions across different user roles: Administrators, Organizers, and Attendees. The system is built upon Object-Oriented Programming (OOP) principles to ensure a modular, maintainable, and user-friendly solution.

## Features

*   **Role-Based Access Control:**
    *   **Admin:** Manages users, oversees all events, and administers event rooms and categories. Includes sub-roles like SuperAdmin, RoomManager, and CategoryManager for granular control.
    *   **Organizer:** Creates, updates, and deletes events; views registered attendees for their events; manages a conceptual wallet for event earnings.
    *   **Attendee:** Browses available events, registers for events, and manages their profile and conceptual wallet for payments.
*   **Comprehensive Event Lifecycle Management:** Full CRUD (Create, Read, Update, Delete) operations for events, including details like name, description, date, venue, category, and price.
*   **User-Friendly GUI:** Intuitive graphical user interface built with JavaFX, featuring distinct dashboards and views tailored to each user role.
*   **Data Persistence:** All critical application data (users, events, rooms, categories) is saved to and loaded from plain `.txt` files, ensuring data retention across sessions.
*   **Wallet System:** A simplified wallet feature for attendees to manage their balance and for organizers to track income (conceptual, no real transactions).
*   **Room and Category Management:** Admins can define and manage event venues (rooms) and event classifications (categories).

## Technologies Used

*   **Java:** Core programming language (Designed with Java 8+ features in mind, compatible with JDK 11+ for JavaFX).
*   **JavaFX:** Framework for the Graphical User Interface (GUI).
*   **Object-Oriented Programming (OOP):** Principles like Encapsulation, Inheritance, Polymorphism, and Abstraction are applied throughout the design.
*   **Plain Text Files (`.txt`):** Used for data persistence with a custom pipe-delimited (`|`) format.

## Screenshots

*(Placeholder: You should add screenshots of your application here. For example:)*
*   *Login Screen*
*   *Admin Dashboard*
*   *Organizer Dashboard (Event Creation View)*
*   *Attendee Dashboard (Event Listing View)*

## Setup and Installation

### Prerequisites

*   **Java Development Kit (JDK):** Version 11 or newer is recommended (as it typically includes JavaFX or makes it easier to integrate).
*   **JavaFX SDK:** Ensure JavaFX libraries are available. If using JDK 11+, you might need to include JavaFX as separate modules or dependencies.
*   **IDE (Recommended):** An Integrated Development Environment like IntelliJ IDEA, Eclipse, or NetBeans will simplify project management and running the application.

### Steps

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/yourusername/event-management-system.git
    cd event-management-system
    ```
    (Replace `yourusername/event-management-system.git` with your actual repository URL.)
    Alternatively, download the source code as a ZIP file and extract it.

2.  **Open in IDE:**
    *   Import the project into your preferred Java IDE.

3.  **Configure JavaFX:**
    *   If your IDE doesn't automatically configure JavaFX, you may need to add the JavaFX SDK libraries to your project's build path or module path.
    *   If using Maven or Gradle, ensure JavaFX dependencies are correctly specified in your `pom.xml` or `build.gradle` file.
    *   For module-based projects (Java 9+), you might need to configure VM options to add JavaFX modules, for example:
        `--module-path /path/to/javafx-sdk-xx/lib --add-modules javafx.controls,javafx.fxml`

4.  **Create `data` Directory:**
    *   In the root directory of the project (the same level as your `src` folder), manually create a directory named `data`.
    *   This directory will be used to store the `.txt` files for data persistence (e.g., `admins.txt`, `events.txt`).

5.  **Compile and Run:**
    *   Locate the main application class (e.g., `com.example.emsss.HelloApplication` or similar, based on your project structure).
    *   Compile and run this class from your IDE.

## Usage

1.  Once the application is running, you will be presented with the Welcome/Login screen.
2.  **First Run:** If the `data` directory is empty (or the `.txt` files within it are empty/missing), the application will start with no pre-existing data. The system is designed to add initial dummy data (e.g., a default admin, sample categories) if the data files are empty. This allows for immediate testing.
3.  **Login/Register:**
    *   You can log in using credentials for existing users (e.g., the dummy admin if created on first run).
    *   New users can register as an Attendee through the registration form.
4.  Navigate through the application using the role-specific dashboards and menus.

## File Structure (Overview)

```
event-management-system/
├── data/                     # Stores .txt data files (admins.txt, events.txt, etc.) - MUST BE CREATED MANUALLY
├── src/
│   └── com/example/emsss/    # Main package for source code
│       ├── model/            # Entity classes (User.java, Event.java, etc.)
│       ├── DB/               # Database.java, TxtDataHandler.java
│       ├── HelloApplication.java # Main application class (or similar)
│       ├── LoginController.java  # Example controller
│       └── ...               # Other controller and utility classes
├── resources/
│   └── com/example/emsss/    # FXML and CSS files (or similar path)
│       ├── login.fxml        # Example FXML file
│       ├── style.css         # Example CSS file
│       └── ...
├── pom.xml                   # If using Maven (or build.gradle for Gradle)
└── README.md
```

## Data Persistence

*   Data is stored locally in plain text (`.txt`) files located in the `data/` directory at the project root.
*   Each entity type (Admin, Event, Room, etc.) has its own file.
*   Objects are stored one per line, with fields separated by a pipe (`|`) delimiter.
*   Data is loaded into memory when the application starts and saved back to the files when the application is closed gracefully.

## OOP Principles Applied

The project demonstrates the use of key Object-Oriented Programming principles:

*   **Encapsulation:** Data fields are private, accessed via public getters and setters.
*   **Inheritance:** A base `User` class is extended by `Admin`, `Organizer`, and `Attendee` classes.
*   **Polymorphism:** (Conceptual) Role-specific actions can be handled via overridden methods.
*   **Abstraction:** Classes model real-world entities, hiding complex implementation details.

## Future Enhancements

*   Integration with a relational database (e.g., SQLite, PostgreSQL) for improved data management.
*   Enhanced security features, especially for password storage (e.g., hashing).
*   Development of a web-based interface.
*   Online payment gateway integration.
*   Automated notifications and reminders.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

*(Please adapt this section if you have specific contribution guidelines.)*

## License

This project can be licensed under the MIT License. You can add a `LICENSE.md` file with the MIT License text if you choose this license.

```text
MIT License

Copyright (c) [Year] [Your Name/Organization]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
