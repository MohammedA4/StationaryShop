# # Stationery Shop Management System (Java)

A console-based Java application designed to manage suppliers and orders for a stationery shop. The system demonstrates object-oriented programming, file handling, and basic data management.

---

## 🚀 Overview

This application allows users to:

* Add and manage suppliers
* Create and manage orders
* View stored data
* Delete suppliers and associated orders

All data is stored locally using text files.

---

## ✨ Features

### 🏢 Supplier Management

* Add new suppliers
* View all suppliers
* Delete suppliers

---

### 📦 Order Management

* Create new orders
* Assign orders to suppliers
* View all orders

---

### 🔄 Data Persistence

* Data is stored in:

  * `orders.txt`
  * `suppliers.txt`
* Automatically loaded on startup
* Automatically saved on exit

---

### ✅ Input Validation

* Prevents empty inputs
* Ensures valid numbers for quantity
* Validates date format (dd/mm/yyyy)
* Ensures dates are after a defined cutoff

---

## 🛠️ Technologies Used

* Java
* Object-Oriented Programming (OOP)
* File I/O (BufferedReader, FileWriter)

---

## 📂 Project Structure

```bash id="p6z1jr"
Part1/
│
├── src/
│   ├── MainApp.java
│   ├── StationaryShopSystem.java
│   ├── DataManager.java
│   ├── Order.java
│   └── Supplier.java
│
├── orders.txt
├── suppliers.txt
├── Part1.iml
```

---

## ▶️ How to Run

1. Navigate to the `src` folder:

```bash id="h3f7kw"
cd Part1/src
```

2. Compile the program:

```bash id="2h2r0c"
javac *.java
```

3. Run the application:

```bash id="g0h6sq"
java MainApp
```

---

## 🖥️ How It Works

The system runs in the console and displays a menu:

* Add Supplier
* Add Order
* Display Suppliers
* Display Orders
* Delete Supplier
* Exit

Users interact by entering numbers and input values.

---

## 🎯 Purpose

This project was developed as part of university coursework to demonstrate:

* Java programming skills
* System design and structure
* Data handling without a database

---

## 📌 Notes

* This is a console-based application (not JavaFX GUI)
* Data is stored locally, no database is used
* Suitable for learning and demonstration purposes

---

## 👤 Author

Mohammed Abir

---

## 📄 License

This project is for educational purposes.
