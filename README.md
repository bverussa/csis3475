# CSIS3475-071

![alt text](https://www.douglascollege.ca/-/media/9702A49D25404AAB8EB0DAB389A90B43.ashx "Douglas College Logo")

### COMMERCE AND BUSINESS ADMINISTRATION
#### CSIS 3475-071 – Data Structures and Algorithms
***Instructor:*** Stephen Chiong

**Members:**

| Name | Student ID |
| ------ | ------ |
| Bruno Verussa Porto Cardoso | 300267277 |
| Cristiane Tiemi Tokoi | 300256625 |
| Victor Luis | 300262411 |

#### Project Topic: Creating a Database

***Features***
1. Supports at least 2 users simultaneously (required)  
   a. As long as the client application is on the same network/computer, it should be able to connect to the database. Socket is optional (+1). You can simulate 2 or more users using 1 computer by running multiple client application.  
   b. Example scenario  
      i. Two users are trying to create a table on the same database. Either the table the users wanted to create has the same name or not, it should be handled appropriately.  
2. Users can create database(s), tables, and fields/columns (required)  
   a. Define your own format/structure  
   b. A database can contain 1 or more tables  
      i. A table can have 1 or more fields/columns  
      ii. It is your decision to “support” data types (such as text, int, char, float) or not  
3. Basic database features  
   a. Create database (required)  
      i. Administrator only  
      ii. It must be on the server’s terminal  
   b. Create users(required)  
      i. Administrator only  
      ii. It must be on the server’s terminal  
      iii. The user should be associated with an existing database  
   c. Create table (required)  
      i. Can be performed by the administrator and users  
   d. Query (retrieve data from a specified table) (required)  
      i. Can be performed by the administrator and users  
      ii. * (asterisks) means obtain all fields/columns from a specified table  
      iii. Use appropriate algorithm(s) to speed up data retrieval  
      iv. Filters should be allowed. You can define your own syntax. Example (if you wish to use SQL syntax) : where id = 1  
      (1) Important conditions: = , > , < , <= , >= , !=  
   e. Insert/Add new entry to a specified table (required)  
      i. Can be performed by the administrator and users  
      ii. Take into consideration if the number of values is <= the number of fields  
      iii. Use appropriate algorithm(s) and data structure(s)  
   f. Delete/Remove an entry or entries from a specified table (optional, +1)  
      i. Can be performed by the administrator and users  
      ii. Use appropriate algorithm(s) and data structure(s)  
      iii. Filters should be allowed. You can define your own syntax. Example (if you wish to use SQL syntax) : where id = 1 
      (1) Important conditions: = , > , < , <= , >= , !=  
   g. Update/Change an entry or entries to a specified table (optional, +1)  
      i. Can be performed by the administrator and users  
      ii. Use appropriate algorithm(s) and data structure(s)  
      iii. Filters should be allowed. You can define your own syntax. Example (if you wish to use SQL syntax) : where id = 1  
      (1) Important conditions: = , > , < , <= , >= , !=  

4. Client application (required)  
   a. Using GUI  
   b. Able to connect to your database server  
   c. Allow user to type and send the database commands  
      i. You will define your own set of commands to create, query, insert, update, delete  
   d. Display the results  

***Requirements and markings***

1. Video Documentation          [3 marks]  
   a. Showcase the features you implemented  
   b. How to use your application?  
   c. Test cases performed with results (at least 20)  
2. Application                  [12 marks]  
   a. User friendly, Intuitive  
   b. Well designed code. Proper use of data structures and algorithms  
   c. Working application  
3. Defense and presentation     [5 marks]  


***Dates to remember***

|| CSIS 3475 – 001 | CSIS 3475 - 71 |
| ------ | ------ | ------ | 
| Due | Nov 25 (Sat) | Nov 29 (Wed) |
| Defense | Nov 28 (Tues) | Dec 2 (Sat) |
