import sysObjects.ReturnValue;

public class Query 
{
	public static ReturnValue run (String query, String databaseName)
	{
		String command = query.split(" ")[0];
		
		switch (command.toUpperCase()) 
		{
			case "CREATE":
				if (query.split(" ")[1].toUpperCase().equals("DATABASE")) // CREATE DATABASE %DBNAME%
				{
					return Database.createDB(query);
				}
				else if (query.split(" ")[1].toUpperCase().equals("TABLE")) // CREATE TABLE %TABLENAME% (%column% %dataType%)
				{
					return Database.createTable(query);
				}
			case "CONNECT": // CONNECT %DBNAME%
				return ClientApplication.setCurrentDb(query);
			case "SHOW": // SHOW %DBNAME%
				return ClientApplication.getCurrentDb();
			//case "SELECT":
				//return Select.run(query, databaseName);
			case "INSERT":
				return Insert.run(query, databaseName);
			//case "UPDATE":
				//return Update.run(query, databaseName);
			case "DELETE":
				if (query.split(" ")[1].toUpperCase().equals("DATABASE")) // DELETE DATABASE %DBNAME%
				{
					return Database.deleteDB(query);
				}
//				else if (query.split(" ")[1].toUpperCase().equals("TABLE")) // DELETE TABLE %DBNAME%
//				{
//					return Database.deleteTable(query);
//				}
			default: 
				ReturnValue r = new ReturnValue();
				r.success = false;
				r.msg = "Invalid Syntax";
				return r;
		}
	}
}
