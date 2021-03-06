import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sysObjects.DataType;
import sysObjects.ReturnValue;
import sysObjects.Table;
import sysObjects.TableFile;
import sysObjects.Util;

public class Insert 
{
	private final static String PATTERN = "INSERT INTO ([\\w\\d_]+) \\(([\\w\\d_,\\s]+)\\) VALUES \\(([\\w\\d_.,\\s]+)\\)";
	private final static String REGEX = "((?<=(INSERT\\sINTO\\s))[\\w\\d_]+(?=\\s+))|((?<=\\()([\\w\\d_.,\\s]+)+(?=\\)))";
	
	private static String tableName; 
    private static ArrayList<String> columns;
    private static ArrayList<String> values;
	
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
        		line = matcher.group(0).replace(" ", "").toUpperCase();
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
	
	public static ReturnValue run(String query, String databaseName) 
	{
		ReturnValue r = new ReturnValue();

		// check if the command is valid using regular expression
        if (!Util.isCommandValid(query, PATTERN)) 
        	r.msg = "Invalid query. Please check if you entered the correct command.";
        else
    	{
        	// extract information from query using regular expression and matcher
        	if(!parseQuery(query))
        		r.msg = "Error to parse the query.";
        	else
        	{
	        	if ((tableName.equals("tblUser") && ClientApplication.userType == 1) || (!tableName.equals("tblUser") && ClientApplication.userType > 0))
	        	{
	        		// match the columns x values
		        	if(columns.size() != values.size())
		        		r.msg = "The number of columns does not correspond to the number of values. Please check your query.";
		        	else 
		        	{
		        		Table tbl = TableFile.readTable(databaseName, tableName);
		        		
		        		if (tbl == null)
		        			r.msg = "Error on table configuration. Please check the table file.";
		        		else 
		        		{
	                    	// match the columns in the query with the file 
	        				if (!TableFile.columnsValid(tbl, columns))
	        					r.msg = "Invalid column. Please check your command.";
	        				else 
	        				{
		                    	ArrayList<String> finalValues = new ArrayList<String>();
		                    	
		                		// fill the values of the columns not included in the query (null)
		                    	// and put the values in the same order as the columns in the file
		                    	int index;
		                    	
		                    	for (int i = 0; i < tbl.columns.size(); i++)
		                    	{
		                    		index = columns.indexOf(tbl.columns.get(i).toUpperCase());
		                    		if (index == -1) // if the column was not included in the query
		                    			finalValues.add("NULL");
		                    		else
		                    			finalValues.add(values.get(index));
		                    	}
		                    	
		                    	// match the data type in the query with the file
		                    	// create the string line with the values of the query
		                    	boolean typeValid = true;
		                    	
		                    	String writeValues = "";
		                    	for (int j = 0; j < finalValues.size(); j++) 
		                    	{
		                    		typeValid = DataType.isValid(tbl.types.get(j), finalValues.get(j));
		                    		if(!typeValid) break;
		                    		else writeValues += finalValues.get(j) + "|";
		                    	}
		                    	
		                    	if(!typeValid) 
		                    		r.msg = "Invalid data type.";
		                    	else 
		                    	{
			                		// write the line in the file
		                    		if(TableFile.writeLine(databaseName, tableName, writeValues))
		                    		{
		                    			r.msg = "Query executed successfully!";
		                    			r.success = true;
		                    		}
		                    		else
		                    			r.msg = "Error to execute the query.";
		                    	}
	        				}
		        		}
		        	}
	        	}
	        	else
	        	{
	        		r.success = false;
	        		r.msg = "Only adminstrators are allowed to create new users. \nPlease, contact the administrator";
	        	}
	        }
    	}
        
        return r;
	}
}
