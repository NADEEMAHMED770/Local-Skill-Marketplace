# 🛠️ Local Skill Marketplace

A fully functional, object-oriented desktop application built with **Java Swing** and **MySQL**. This application serves as a hyper-local digital directory, connecting community members who need everyday services (like tutoring, repairs, or design) with local talent.

## 🚀 Features
* **Secure Authentication:** User registration and login system featuring SHA-256 password hashing.
* **Smart Matching Algorithm:** An NLP-style recommendation engine that scans community requests and instantly matches them with relevant skill providers based on keyword analysis.
* **Database Integration:** Full CRUD (Create, Read, Update, Delete) operations interacting with a local MySQL database.
* **Dynamic GUI:** Modern, multi-screen interface built with Java Swing and updated with system-native Look & Feel.
* **Data Science Ready:** Export feature that writes the current database state directly into a `.csv` dataset for future machine learning analysis.

## 💻 Tech Stack
* **Language:** Java (JDK 17+)
* **GUI Framework:** Java Swing
* **Database:** MySQL
* **Driver:** JDBC (mysql-connector-j)

## 📸 Application Gallery

### Authentication & Dashboard
<p align="center">
  <img src="images/LOGIN.png" width="400" alt="Login Screen">
  <img src="images/CREATE ACCOUNT.png" width="400" alt="Create Account">
</p>
<p align="center">
  <img src="images/DESHBOARD.png" width="800" alt="Main Dashboard">
</p>

### Managing the Marketplace
<p align="center">
  <img src="images/REGISTER NEW MEMBER.png" width="400" alt="Register Member">
  <img src="images/ADD NEW SKILL.png" width="400" alt="Add Skill">
</p>
<p align="center">
  <img src="images/BROWSE SKILL.png" width="800" alt="Browse Skills">
</p>

### Smart Matcher in Action
<p align="center">
  <img src="images/POST SERVICE REQUEST.png" width="400" alt="Post Request">
</p>
<p align="center">
  <img src="images/VIEW SERVICE REQUESTS WITH SMART SEARCH.png" width="800" alt="Smart Search Algorithm">
</p>

## ⚙️ How to Run This Project
1. Clone the repository to your local machine.
2. Open MySQL Workbench and run the `sql/setup.sql` file to generate the tables and constraints.
3. Ensure the `mysql-connector-j-9.7.0.jar` is in the `lib` folder.
4. Compile the project using: `javac -cp "lib/mysql-connector-j-9.7.0.jar" -d out src/*.java`
5. Run the application using: `java -cp "out;lib/mysql-connector-j-9.7.0.jar" Main`

---
*Developed as an academic project for the BSAI program, demonstrating advanced Object-Oriented Programming, Database Management, and File Handling logic.*