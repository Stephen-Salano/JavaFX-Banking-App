# MazeBank - JavaFX Banking Application

![Java Version](https://img.shields.io/badge/Java-21-orange)
![JavaFX Version](https://img.shields.io/badge/JavaFX-21-blue)
![License](https://img.shields.io/badge/License-MIT-green)

A modern, feature-rich banking application built with JavaFX that provides both client and administrative banking functionalities. MazeBank offers a sophisticated user interface, secure transaction processing, and comprehensive account management features.

## 🌟 Features

- **Dual Interface System**
    - Client portal for everyday banking operations
    - Administrative dashboard for bank management

- **Account Management**
    - Support for multiple account types (Checking & Savings)
    - Real-time balance tracking
    - Transaction history
    - Fund transfers between accounts

- **Security Features**
    - Secure authentication system
    - Role-based access control
    - Transaction validation
    - Database security measures

- **Modern UI/UX**
    - Intuitive JavaFX interface
    - Responsive design
    - Professional styling with CSS
    - FontAwesome icons integration

## 📋 Prerequisites

Before you begin, ensure you have met the following requirements:
- Java Development Kit (JDK) 21 or later
- Maven 3.6.3 or later
- SQLite 3.x
- Git (for version control)

## 🚀 Getting Started

### Clone the Repository

```bash
git clone https://github.com/Stephen-Salano/JavaFX-Banking-App.git
cd JavaFX-Banking-App
```

### Create a New Branch

```bash
git checkout -b feature/your-feature-name
```

### Install Dependencies

```bash
mvn clean install
```

### Run the Application

```bash
mvn javafx:run
```

## 📁 Project Structure

```
JavaFX-Banking-App/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── devs/
│       │           └── mazebank/
│       │               ├── Controllers/
│       │               │   ├── AdminControllers/
│       │               │   ├── Client/
│       │               │   └── LoginController.java
│       │               ├── Models/
│       │               │   ├── Account.java
│       │               │   ├── CheckingAccount.java
│       │               │   ├── SavingsAccount.java
│       │               │   ├── Client.java
│       │               │   ├── DatabaseDriver.java
│       │               │   ├── Model.java
│       │               │   └── Transaction.java
│       │               └── Views/
│       └── resources/
│           └── com/
│               └── devs/
│                   └── mazebank/
│                       └── Styles/
├── pom.xml
├── mazebank.db
└── README.md
```

## 🔧 Configuration

### Database Setup

The application uses SQLite for data persistence. The database file (`mazebank.db`) is automatically created when you first run the application.

### Application Properties

Key configurations can be modified in the following files:
- Database configuration: `DatabaseDriver.java`
- UI configuration: CSS files in the `Styles` directory

## 💻 Code Examples

### Connecting to the Database

```java
// DatabaseDriver.java
public Connection getConnection() {
    try {
        return DriverManager.getConnection("jdbc:sqlite:mazebank.db");
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}
```

### Creating a New Account

```java
// Account.java
public Account(String owner, String accountNumber, double balance) {
    this.owner = owner;
    this.accountNumber = accountNumber;
    this.balance = balance;
}
```

## 🤝 Contributing

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Commit Message Format

```
type(scope): subject

[optional body]

[optional footer]
```

Types:
- feat: New feature
- fix: Bug fix
- docs: Documentation changes
- style: Code style changes
- refactor: Code refactoring
- test: Adding tests
- chore: Maintenance

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details

## ✨ Author

**Stephen Salano**
- GitHub: [@Stephen-Salano](https://github.com/Stephen-Salano)

## 🔍 Additional Information

### Technology Stack
- Java (75.7%)
- CSS (24.3%)
- JavaFX for GUI
- SQLite for database
- Maven for dependency management
- FontAwesomeFX for icons

### Key Dependencies
```xml
<dependencies>
    <!-- JavaFX -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21</version>
    </dependency>
    
    <!-- FontAwesome -->
    <dependency>
        <groupId>de.jensd</groupId>
        <artifactId>fontawesomefx-fontawesome</artifactId>
        <version>4.7.0-9.1.2</version>
    </dependency>
    
    <!-- SQLite JDBC -->
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.49.0.0</version>
    </dependency>
</dependencies>
```

## 📊 Project Status

The project is currently under active development. New features and improvements are being added regularly.

## 🐛 Known Issues

If you find any bugs or have feature requests, please create an issue in the GitHub repository.

## 📞 Support

For support, please open an issue in the GitHub repository or contact the project maintainer.

---
Last Updated: 2025-03-14