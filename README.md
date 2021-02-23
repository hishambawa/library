# library
JavaFX CRUD Library application

Run the following query in MySQL localhost:3306 to continue

  ```
  CREATE DATABASE library
  
  CREATE TABLE books 
  (
    id INT(11)  PRIMARY KEY NOT NULL,
    title VARCHAR(20),
    author VARCHAR(50),
    YEAR INT(11),
    pages INT(11)
  );
  ```
