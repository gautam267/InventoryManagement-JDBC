# Inventory Management System

A menu-driven Inventory Management System developed using **Java JDBC and MySQL**. This project demonstrates database connectivity and CRUD operations using Java.

## Technologies Used

- Java (JDK 25)
- MySQL 8.4
- JDBC
- MySQL Connector/J 26.10.0
- IntelliJ IDEA

## Features

1. **Insert Products:** Insert three product records using PreparedStatement and handle duplicate product IDs.
2. **Retrieve Products:** Display products by category with quantity below 20, sorted by price in descending order.
3. **Update Stock:** Increase the existing quantity of a product using its product ID.
4. **Search and Update Price:** Search for a product and update its price only if it exists and the new price is greater than zero.
5. **Inventory Value and Deletion:** Calculate total inventory value using `SUM(quantity * price)` and delete a product only if its quantity is zero.

## Database Setup

Create the database:

```sql
CREATE DATABASE retail_db;
USE retail_db;
```

Create the Product table:

```sql
CREATE TABLE Product (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100),
    category VARCHAR(50),
    quantity INT,
    price DOUBLE
);
```

## JDBC Configuration

Update the database credentials in `Main.java`:

```java
String url = "jdbc:mysql://localhost:3306/retail_db";
String user = "root";
String password = "YOUR_MYSQL_PASSWORD";
```

Add MySQL Connector/J to the project dependencies.

## How to Run

1. Install Java JDK and MySQL Server.
2. Create the database and Product table using the SQL commands above.
3. Add MySQL Connector/J to the IntelliJ project.
4. Open `src/Main.java`.
5. Configure your MySQL username and password.
6. Run the `Main` class.
7. Select an operation from the menu.

## Project Structure

```text
InventoryManagement/
│
├── src/
│   └── Main.java
│
├── .gitignore
└── README.md
```

## Author

Java OOP – MSE-2 JDBC Practical Project