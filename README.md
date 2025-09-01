# TradeApp – Energy Trading Console Program

A **Java-based console tool** for managing trades in an **Energy Trading System** with **SQL Server** as the backend. The application uses **JDBC** for database connectivity and provides a simple, menu-driven interface to perform trade operations.

---

## Core Functions

* Establishes connection with SQL Server using JDBC
* Insert new trade records
* Display all stored trades
* Modify existing trade details
* Remove trades from the system
* Search trades based on **counterparty** or **commodity type**
* Interactive menu for smooth navigation

---

## System Requirements

* Java Development Kit (JDK) 11 or later
* Microsoft SQL Server (local/remote instance)
* Microsoft JDBC Driver for SQL Server → [Download here](https://learn.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server)

---

## Database Preparation

```sql
CREATE DATABASE EnergyTradingDB;
USE EnergyTradingDB;

CREATE TABLE Trades (
    TradeID INT PRIMARY KEY IDENTITY(1,1),
    TradeDate DATE NOT NULL,
    Counterparty VARCHAR(100) NOT NULL,
    Commodity VARCHAR(50) NOT NULL,
    Volume DECIMAL(10,2) NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    TradeType VARCHAR(10) CHECK (TradeType IN ('BUY','SELL'))
);
```

---

## Configuration

```java
// Windows Authentication
static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=EnergyTradingDB;encrypt=false;integratedSecurity=true;";

// SQL Authentication
static final String USER = "your_username";
static final String PASS = "your_password";
```

---

## Compile and Run

```bash
javac -cp ".;path/to/mssql-jdbc-12.x.x.jre11.jar" TradeApp.java
java -cp ".;path/to/mssql-jdbc-12.x.x.jre11.jar" TradeApp
```




![image alt](https://github.com/Shri31Shetty/Technical-Assissment/blob/main/result1.png?raw=true)
![image alt](https://github.com/Shri31Shetty/Technical-Assissment/blob/b70efa1a7a3239cc30a04aa44beadb6ccbc5799c/result2.png)
![image alt](https://github.com/Shri31Shetty/Technical-Assissment/blob/65218a0e37a06510a0f04707262b8106a3b0047e/result3.png)
![image alt](https://github.com/Shri31Shetty/Technical-Assissment/blob/655251eb1ae9509039e62b28f9a875b9f28d8841/result4.png)
![image alt](https://github.com/Shri31Shetty/Technical-Assissment/blob/116fb680a6e585d0dafc42f6789f534be4bdd526/result5.png)
![image alt](https://github.com/Shri31Shetty/Technical-Assissment/blob/1705107e67aa7b5cdee7a437f5f39ef07b604f7e/Table.png)
