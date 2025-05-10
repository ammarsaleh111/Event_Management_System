# Event Management System

## Description

The **Event Management System (EMS)** is a desktop application developed in **Java** using **JavaFX**. It provides a role-based platform for managing events, user registrations, and event interactions. The system is designed using solid **Object-Oriented Programming (OOP)** principles, ensuring a modular, extensible, and user-friendly experience.

---

## Features

### Role-Based Access Control

- **Admin**  
  - Oversees the system, users, event categories, and venues (rooms).
  - Sub-roles: `SuperAdmin`, `RoomManager`, `CategoryManager` for fine-grained responsibilities.
  
- **Organizer**  
  - Can create, update, and delete events.
  - Manages registered attendees and tracks conceptual event earnings through a wallet system.
  
- **Attendee**  
  - Browses, registers, and pays for events using a built-in wallet.
  - Manages personal profile and registrations.

### Event Lifecycle Management

- Full **CRUD** operations for events with details such as name, description, category, venue, date, and ticket price.

### User-Friendly GUI

- Built with JavaFX.
- Distinct dashboards and views for each user role.

### Data Persistence

- Data stored in plain `.txt` files with a `|` (pipe) delimiter.
- Supports persistence across sessions.

### Wallet System

- Conceptual wallet for both attendees and organizers.
- Tracks deposits, payments, and earnings (no real transactions).

### Room & Category Management

- Admins can define and manage event **venues (rooms)** and **categories**.

---

## Technologies Used

- **Java 8+**
- **JavaFX**
- **Plain Text Files** for data persistence
- **OOP Principles:** Encapsulation, Inheritance, Polymorphism, Abstraction

---

## Screenshots

> *(Add screenshots in your repository under an `/assets` or `/screenshots` folder and embed them here.)*

- Login Screen  
- Admin Dashboard  
- Organizer Event Management View  
- Attendee Event List & Registration

---

## Setup & Installation

### Prerequisites

- **JDK 11+** (JavaFX-friendly versions recommended)
- **JavaFX SDK** (required if not bundled with JDK)
- **IDE:** IntelliJ IDEA / Eclipse / NetBeans

### Steps

1. **Clone the Repository**
    ```bash
    git clone https://github.com/ammarsaleh111/Event_Management_System.git
    cd Event_Management_System
    ```

2. **Open in IDE**
    - Import as a Java project.
    - Ensure JavaFX is correctly linked.

3. **Configure JavaFX (if needed)**
    - VM Options (for module-based setup):
      ```
      --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
      ```

4. **Create Data Directory**
    - Manually create a `data/` folder at the root.
    - This folder stores `admins.txt`, `events.txt`, etc.

5. **Run the Project**
    - Locate and run the main class, e.g., `HelloApplication.java`.

---

## Usage

1. Start the application (Welcome/Login screen).
2. On first run, the app may populate sample data for testing (e.g., default admin).
3. Log in as:
   - Admin (default)  
   - Or register a new Attendee
4. Explore your role-based dashboard and features.

---

## Project Structure

```
event-management-system/
├── data/                     # Must be created manually (stores .txt files)
├── src/
│   └── com/example/emsss/
│       ├── model/            # User, Event, etc.
│       ├── DB/               # TxtDataHandler, Database logic
│       ├── HelloApplication.java
│       ├── LoginController.java
│       └── ...
├── resources/
│   └── com/example/emsss/
│       ├── login.fxml
│       ├── style.css
│       └── ...
├── pom.xml / build.gradle    # (If using Maven or Gradle)
└── README.md
```

---

##  OOP Concepts Applied

- **Encapsulation:** Private fields, public getters/setters  
- **Inheritance:** `User` superclass with `Admin`, `Organizer`, `Attendee` subclasses  
- **Polymorphism:** Role-specific logic and overridden methods  
- **Abstraction:** Logical modeling of real-world entities (e.g., Events, Wallets)

---

## Future Enhancements

- Database Integration (e.g., SQLite, PostgreSQL)
- Password hashing and authentication improvements
- Web-based version using Spring or React
- Payment gateway integration
- Email notifications and reminders

---

## Project Contributors

Project developed by students of **Ain Shams University** under the supervision of **Prof. Mahmoud Khalil** and **Eng. Ahmed Hossam**.

### Team Members

- Ammar Ahmed Saleh ID: 24p0212  
- Aly Mohamed Aly ID: 24p0431  
- Islam Bassem Kamal ID: 24p0209  
- Mustafa Hazem ID: 24p0326  
- Seif Emad Eldin ID: 24p0386  
- Ziad Tamer Sobhy ID: 24p0433  

---

## License

This project is licensed under the **MIT License**.

```
MIT License

Copyright (c) 2025 Ammar Ahmed Saleh

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---
