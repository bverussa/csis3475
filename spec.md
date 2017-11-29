October 28, 2017

* Databases are folders
* Files inside these folders are the tables
* Files names are the tables names
* Columns are separated by pipeline |
* Every Table file will have the 2 first rows for the table structure. For example: 
    * ID | name | salary  --> columns names
    * int | text | double --> data types
* Data types: int, text, double, bit
* Table User will have a user default (admin, pwd: admin)
* User Types: 1 for administrator, 2 for user
* Table Database will have a database default (master), which will have the table User (tblUser)
* In the login, the user must select the database to connect
* When implement delete, think about the SYSID logic

### INSERT

* Command should be: 
    ##### INSERT INTO [*table name*] ([*columns*]) VALUES ([*values*])
* The columns and values are separated by comma ","
* It is possible to list only the columns to insert values in the [*columns*] part. The columns not specified will be inserted as *NULL*
* The values for the data type bit are 0 or 1
* The decimal separator for double is dot "."
* We did not implemented *PRIMARY KEY* neither *FOREIGN KEY* at this time
* We did not implemented *auto identity* neither *unique* fields at this time
* Some scenarios to test: 
   - Insert all columns correctly
   - Insert only some columns and check if the others will be included as NULL
   - Try to insert columns with wrong names
   - Try to insert values with the wrong data type
   - Try to insert columns with a different order than the table 


### Ideas / Next steps

* Create column sizes as 3rd line of the file
* When the program opens for the first time, give the option to choose the folder to create the databases. Store this path in a file and create the basic structure before show the login screen (create database master, user admin). This option will appear only if the path is not chosen. Otherwise there will be an option in the menu to change the path. 
* Improve the insert class to include more options to run the query (e.g. *INSERT INTO [table name] VALUES ([values])* >> without specifying the columns)
