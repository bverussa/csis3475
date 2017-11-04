import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;

public class Insert 
{
	private final static String PATTERN = "INSERT INTO ([\\w\\d_]+) \\(([\\w\\d_,\\s]+)\\) VALUES \\(([\\w\\d_,\\s]+)\\)";
	private final static String REGEX = "((?<=(INSERT\\sINTO\\s))[\\w\\d_]+(?=\\s+))|((?<=\\()([\\w\\d_,\\s]+)+(?=\\)))";
	
	private static String tableName; 
    private static ArrayList<String> columns;
    private static ArrayList<String> values;
    
	private static boolean isCommandValid(String query)
	{
		Pattern pattern = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);
        
        return matcher.matches();
	}
	
	private static boolean parseQuery(String query)
	{
		Pattern pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(query);
		boolean success = false;
		String line;
		
		// there are 3 groups to match: table name, columns and values
		if (matcher.find())
		{
			// get the table name
        	tableName = matcher.group(0);
        	
        	// get the columns to insert
        	if (matcher.find())
	        {
        		line = matcher.group(0).replace(" ", "");
	        	columns = new ArrayList<String>(Arrays.asList(line.split(",")));
	        	
	        	// get the values
	        	if (matcher.find())
		        {
	        		line = matcher.group(0).replace(" ", "");
		        	values = new ArrayList<String>(Arrays.asList(line.split(",")));
		        	success = true;
		        }
	        }
		}
		
		return success;
	}
	
	public static String run(String query, String databaseName) 
	{
		String returnMsg = "";
		
		// check if the command is valid using regular expression
        if (!isCommandValid(query)) 
        	returnMsg = "Invalid query. Please check if you entered the correct command.";
        else
    	{
        	// extract information from query using regular expression and matcher
        	if(!parseQuery(query))
        		returnMsg = "Error to parse the query.";
        	else
        	{
	        	// match the columns x values
	        	if(columns.size() != values.size())
	        		returnMsg = "The number of columns does not correspond to the number of values. Please check your query.";
	        	else 
	        	{
	        		Table tbl = TableFile.readTable(databaseName, tableName);
	        		
	        		if (tbl == null)
	        			returnMsg = "Error on table configuration. Please check the table file.";
	        		else 
	        		{
                    	// match the columns in the query with the file 
        				if (!TableFile.columnsValid(tbl, columns))
        					returnMsg = "Invalid column. Please check your command.";
        				else 
        				{
	                    	ArrayList<String> finalValues = new ArrayList<String>();
	                    	
	                		// fill the values of the columns not included in the query (null)
	                    	// and put the values in the same order as the columns in the file
	                    	int index;
	                    	for (int i = 1; i < tbl.columns.size(); i++)
	                    	{
	                    		index = columns.indexOf(tbl.columns.get(i));
	                    		if (index == -1) // if the column was not included in the query
	                    			finalValues.add("NULL");
	                    		else
	                    			finalValues.add(values.get(index));
	                    	}
	                    	
	                    	// TODO: match the data type in the query with the file
	                    	
	                    	
	                    	// create the string line with the values of the query
	                    	String writeValues = tbl.nextSysID + "|";
	                    	for (String val : finalValues)
	                    		writeValues += val + "|";
	                    	
	                		// write the line in the file
	                    	returnMsg = TableFile.writeLine(databaseName, tableName, writeValues);
        				}
	        		}
	        	}
	        }
    	}
        
        return returnMsg;
	}
}
