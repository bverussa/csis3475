
public class Query 
{
	public static ReturnValue run (String query, String databaseName)
	{
		String command = query.split(" ")[0];
		
		switch (command.toUpperCase()) 
		{
			case "CREATE":
				return Database.createDB(query);
			//case "CONNECT":
			case "SHOW":
				return ClientApplication.getCurrentDb();
			//case "SELECT":
				//return Select.run(query, databaseName);
			case "INSERT":
				return Insert.run(query, databaseName);
			//case "UPDATE":
				//return Update.run(query, databaseName);
			case "DELETE":
				if (query.split(" ")[1].toUpperCase().equals("TABLE"))
				{
					return Database.deleteDB(query);
				}
//				else
//				{
//					return Delete.run(query, databaseName);
//				}
			default: 
				return new ReturnValue();
		}
	}
}
