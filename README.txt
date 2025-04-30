JFrame GPA Calculator - CS IT 2238 Practical Exam
Student Name: MICHAEL ONWUACHI 
Student Roll No: 10211100292


 Project Description:
This is a Java Swing (JFrame) GPA Calculator  It allows users to input student details (Name, ID, Courses, Grades, and Credit Hours) and calculate GPA based on the official grade point scale.

Users can choose where to save the results: either to a file or a database. For this project, only the **File saving** option is implemented.

 Features:

- Input student Name, ID, Course, Grade, and Credit Hours.
- Select grade from a dropdown (ComboBox).
- Calculate GPA based on the formula:
  
  GPA = Σ(Grade Point × Credit Hours) / Σ(Credit Hours)

- Save data to a text file (`gpa_records.txt`).
- Display "Database: Under Construction" if the database option is selected.
- Basic error handling for invalid credit hour input.

Save Option Implemented:

 File (`gpa_records.txt`)  
 Databae (Under Construction)


 How to Run the Project:

1. Make sure you have Java installed.
2. Open the terminal or command prompt.
3. Compile the Java file:
    
 javac GPACalculator

 
 Files Included:
------------------
- GPACalculator.java (Main Java code)
- gpa_records.txt (Generated file after saving)
- README.txt (This file)



