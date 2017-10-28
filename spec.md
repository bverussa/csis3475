October 28, 2017

* Databases are folders
* Files inside these folders are the tables
* Files names are the tables names
* Columns are separated by pipeline |
* Every Table file will have the 2 first rows for the table structure. For example: 
ID | name | salary  --> columns names
int | text | double --> data types
* Data types: int, text, double, bit
* Table User will have a user default (admin, pwd: admin)
* Table Database will have a database default (master), which will have the table User and Permissions
* Every table will have an "internal ID" to control the lines. This will be a column with a pre-defined name (SYSID), which cannot be used by the user
* In the login, the user must select the database to connect
