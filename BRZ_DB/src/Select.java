import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataStructures.MyBinaryTree;
import sysObjects.ReturnValue;
import sysObjects.SelectData;
import sysObjects.Table;
import sysObjects.TableFile;
import sysObjects.Util;

public class Select
{
	private final static String PATTERN = "SELECT ([\\w\\d_.,*\\s]+) FROM ([\\w\\d_]+)([WHERE\\s[\\w\\d_.=]+]+)";
	//private final static String PATTERN = "SELECT\\s.+FROM\\s.+[WHERE\\s.*]?";
	private final static String REGEX = 
			"((?<=(SELECT\\s))[\\w\\d_.,*\\s]+(?=FROM\\s))|((?<=(FROM\\s))[\\w\\d_]+(?=\\s*))|((?<=(WHERE\\s))[\\w\\d_.=\\s]+(?=\\s*))";
	
	private static String tableName; 
    private static ArrayList<String> columns;
    private static String whereClause;
    
    private static boolean parseQuery(String query)
	{
		Pattern pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(query);
		boolean success = false;
		String line;
		whereClause = null;
		
		// there are 3 groups to match: table name, columns and where condition
		if (matcher.find())
		{
        	// get the columns to insert
    		line = matcher.group(0).replace(" ", "");
        	columns = new ArrayList<String>(Arrays.asList(line.toUpperCase().split(",")));
	        	
        	if (matcher.find())
	        {
        		// get the table name
            	tableName = matcher.group(0);
            	success = true;
            	
            	// get the where clause (optional)
	        	if (matcher.find()) whereClause = matcher.group(0).toUpperCase();
	        }
		}
		
		return success;
	}
    
    // Query for tests
	// SELECT * FROM tblUser 
    // SELECT ID, Username FROM tblUser
	public static ReturnValue run(String query, String databaseName) 
	{
		ReturnValue r = new ReturnValue();
		
		// check if the command is valid using regular expression
        if (!Util.isCommandValid(query, PATTERN)) 
        	r.msg = "Invalid query. Please check if you entered the correct command.";
        else
    	{        	
        	// extract information from query using regular expression and matcher
        	// (table name, columns, and whether it is using where clause or not)
        	if(!parseQuery(query))
        		r.msg = "Error to parse the query.";
        	else
        	{
        		Table tbl = TableFile.readTable(databaseName, tableName);
        		
        		if (tbl == null)
        			r.msg = "Error on table configuration. Please check the table file.";
        		else 
        		{
        			// if the select includes all columns (*)
            		if (columns.size() == 1 && columns.get(0).equals("*"))
            		{
            			columns = tbl.columns;
            		}
            		else
            		{
	                	// match the columns in the query with the file 
	    				if (!TableFile.columnsValid(tbl, columns))
	    				{
	    					r.msg = "Invalid column. Please check your command.";
	    					return r;
	    				}
            		}
            		
            		StringBuilder qResult = new StringBuilder();
            		
            		// get the indexes of the columns specified in the select
            		int[] selectCols = new int[columns.size()];
            		String col;
            		for (int i = 0; i < columns.size(); i++)
            		{
            			col = columns.get(i);
            			selectCols[i] = tbl.columns.indexOf(col);
            			
            			qResult.append(col);
        				qResult.append("|");
            		}
            		
            		qResult.append(System.lineSeparator());
            		
            		// if there is not where clause, just read the file and show the fields from select
            		if(whereClause == null || whereClause.equals(""))
            		{
            			ArrayList<String> res = TableFile.readTableContent(databaseName, tableName);
            			String[] values;
            			
            			for (String s : res)
            			{
            				values = s.split("\\|");
            				
            				for (int v : selectCols)
            				{
        						qResult.append(values[v]);
        						qResult.append("|");
            				}
            				
            				qResult.append(System.lineSeparator());
            			}
            		}
            		else // if there is where clause, create the binary tree with nodes of type SelectData
            		{
            			// find the column to compare in the where clause
            			String[] where = whereClause.split("=");
            			int columnCompare = tbl.columns.indexOf(where[0].trim());
            			String valueSearch = where[1];
            			
            			// do the search on the tree based on where clause
            			MyBinaryTree<SelectData> binTree = TableFile.readTableContent(databaseName, tableName, columnCompare);
            			
            			String[] fields = new String[tbl.columns.size()];
            			fields[columnCompare] = valueSearch.trim();
            			SelectData itemToSearch = new SelectData(fields, columnCompare);
            			
            			ArrayList<SelectData> itemsFound = binTree.find(itemToSearch);
            			for (SelectData selData : itemsFound)
            			{
            				for (int v : selectCols)
            				{
            					qResult.append(selData.fields[v]);
	    						qResult.append("|");
            				}
    						
    						qResult.append(System.lineSeparator());
            			}
            		}
            		
            		r.msg = qResult.toString();
        			r.success = true;
            		
            		// get the results and show only the columns specified in the select 
            		
        		}
        	}
    	}
        
		return r;
	}
}
