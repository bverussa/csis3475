
public class Query 
{
	public static String run (String query, String databaseName)
	{
		String command = query.split(" ")[0];
		
		switch (command.toUpperCase()) 
		{
			//case "SELECT":
				//return Select.run(query, databaseName);
			case "INSERT":
				return Insert.run(query, databaseName);
			//case "UPDATE":
				//return Update.run(query, databaseName);
			//case "DELETE":
				//return Delete.run(query, databaseName);
			default: 
				return "Invalid command";
		}
	}
}
