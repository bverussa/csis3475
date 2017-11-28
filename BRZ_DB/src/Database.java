import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import sysObjects.ReturnValue;
import sysObjects.Util;

public class Database
{	
	private static String dbName;
	private static String tableName;
	private static ArrayList<String> columns;
    private static ArrayList<String> dataType;
	private final static String PATTERN = "(CREATE TABLE) ([\\w\\d_]+) [\\(]((([\\w\\d_]+) (INT|TEXT|DOUBLE|BIT)([\\,]?))+|(([\\w\\d_]+) (INT|TEXT|DOUBLE|BIT))+)[\\)]";
	
	private String getUserInput(int type, String msg)
	{
		String label = "";
		
		switch(type)
		{
			case 1: // Database
				label = "Database Name:";
				break;
			case 2: // Table
				label = "Table Name:";
				break;
		}
		
		JTextField userInput = new JTextField();
		final JComponent[] input = new JComponent[] 
		{
			new JLabel(label), userInput
		};
		
		int result = JOptionPane.showConfirmDialog(null, input, msg, JOptionPane.PLAIN_MESSAGE);
		
		if (result == JOptionPane.OK_OPTION)
		{
			return userInput.getText();
		}
		else
		{
			return null;
		}
	}
	
	public void createDB()
	{
		Database.dbName = getUserInput(1, "Create Database").toLowerCase().trim();
		
		if (!Database.dbName.isEmpty())
		{
			File database = new File(Util.DATABASE_FOLDER + "/" + dbName);
			
			if (!database.exists())
			{
				boolean check = false;
				
				try
				{
					database.mkdirs();
					check = true;
				}
				catch(SecurityException ex)
				{
					JOptionPane.showMessageDialog(null, ex);
				}
				if (check)
				{
					ClientApplication.currentDB = Database.dbName;
					ClientApplication.setCurrentDb();
					JOptionPane.showMessageDialog(null, "Database " + dbName + " created!");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Database " + dbName + " already exist!");
				ClientApplication.currentDB = Database.dbName;
			}
		}
		else
		{
			Database.dbName = Util.DB_MASTER;
			ClientApplication.currentDB = Database.dbName;
			JOptionPane.showMessageDialog(null, "Please, enter a database name");
		}
	}
	
	public static ReturnValue createDB(String query)
	{
		ReturnValue r = new ReturnValue();
		
		if (ClientApplication.userType == 1)
		{
			// CREATE DATABASE %DBNAME%
			Database.dbName = query.split(" ")[2].toLowerCase();
			
			if (!Database.dbName.isEmpty())
			{
				File database = new File(Util.DATABASE_FOLDER + "/" + dbName);
				
				if (!database.exists())
				{
					boolean check = false;
					
					try
					{
						database.mkdirs();
						check = true;
					}
					catch(SecurityException ex)
					{
						r.success = false;
						r.msg = ex.toString();
					}
					
					if (check)
					{
						ClientApplication.currentDB = Database.dbName;
						ClientApplication.setCurrentDb();
						r.success = true;
						r.msg = "Database " + dbName + " created!";
					}
				}
				else
				{
					ClientApplication.currentDB = Database.dbName;
					r.success = false;
					r.msg =  "Database " + dbName + " already exist!";
				}
			}
			else
			{
				Database.dbName = Util.DB_MASTER;
				ClientApplication.currentDB = Database.dbName;
				r.success = false;
				r.msg = "Please, enter a database name";
			}
		}
		else
		{
			r.success = false;
			r.msg = "Only adminstrators are allowed to create new database. \nPlease, contact the administrator";
		}
		return r;
	}
	
	public void deleteDB()
	{
		Database.dbName = getUserInput(1, "Delete Database").toLowerCase();
		
		if (!Database.dbName.isEmpty())
		{	
			if (!Database.dbName.equals(Util.DB_MASTER))
			{
				File database = new File(Util.DATABASE_FOLDER + "/" + Database.dbName);
				
				if (database.exists())
				{
					File[] tables = database.listFiles();
			        if (tables != null)
			        {
			            for (int i = 0; i < tables.length; i++)
			            {
			                tables[i].delete();
			            }
			        }
			        
			        database.delete();
			        ClientApplication.currentDB = Util.DB_MASTER;
			        ClientApplication.setCurrentDb();
			        JOptionPane.showMessageDialog(null, "Database " + Database.dbName + " deleted!");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Database " + Database.dbName + " does not exist!");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "You can not delete " + Util.DB_MASTER + " database!");
				this.deleteDB();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Please, enter a database name");
			this.deleteDB();
		}
	}
	
	public static ReturnValue deleteDB(String query)
	{
		ReturnValue r = new ReturnValue();
		
		if (ClientApplication.userType == 1)
		{
			// DELETE DATABASE %DBNAME%
			Database.dbName = query.split(" ")[2].toLowerCase();
			
			if (!Database.dbName.isEmpty())
			{	
				if (!Database.dbName.equals(Util.DB_MASTER))
				{
					File database = new File(Util.DATABASE_FOLDER + "/" + Database.dbName);
					
					if (database.exists())
					{
						File[] tables = database.listFiles();
				        if (tables != null)
				        {
				            for (int i = 0; i < tables.length; i++)
				            {
				                tables[i].delete();
				            }
				        }
				        
				        database.delete();
				        ClientApplication.currentDB = Util.DB_MASTER;
				        ClientApplication.setCurrentDb();
				        r.success = true;
				        r.msg = "Database " + Database.dbName + " deleted!";
				        
					}
					else
					{
						r.success = false;
				        r.msg = "Database " + Database.dbName + " does not exist!";
					}
				}
				else
				{
					r.success = false;
			        r.msg = "You can not delete " + Util.DB_MASTER + " database!";
				}
			}
			else
			{
				r.success = false;
		        r.msg = "Please, enter a database name";
			}
		}
		else
		{
			r.success = false;
			r.msg = "Only adminstrators are allowed to delete a database. \nPlease, contact the administrator";
		}
		return r;
	}
	
	public static ReturnValue createTable(String query)
	{	
		ReturnValue r = new ReturnValue();
		// COMMAND: create table %tableName% (%columnName% %dataType%, %columnName% %dataType%, ...)
		// Data Types: int | text | double | bit
		Database.tableName = query.split(" ")[2].toLowerCase();
		Database.dbName = ClientApplication.currentDB;
		String dbTable = Util.DATABASE_FOLDER + "/" + Database.dbName + "/" + tableName + ".txt";
		Writer tableToDisk = null;
		
		try
		{
			if (!Util.isCommandValid(query, PATTERN)) 
			{
				r.success = false;
	        	r.msg = "Invalid query. Please check if you entered the correct command.";
			}
	        else
	        {
	        	if(!parseQuery(query))
	        		r.msg = "Error to parse the query.";
	        	else
	        	{
	        		try
	        		{
	        			tableToDisk = new BufferedWriter(
		    					new OutputStreamWriter(
		    							new FileOutputStream(dbTable), "utf-8"));
		        		
		        		// match the columns x dataType
			        	if(columns.size() != dataType.size())
			        		r.msg = "The number of columns does not correspond to the number of dataTypes. Please check your query.";
			        	else 
			        	{
	        			   	ArrayList<String> finalValues = new ArrayList<String>();
	                    	
	                		int index;
	                    	String columnsUpdated = "SYSID(0)|";
	                    	for (int i = 0; i < columns.size(); i++)
	                    	{
	                    		index = columns.indexOf(columns.get(i));
	                    		if (index == -1) // if the column was not included in the query
	                    			finalValues.add("NULL");
	                    		else
	                    			finalValues.add(dataType.get(index).toUpperCase());
	                    		
	                    		columnsUpdated += columns.get(i) + "|";
	                    	}
	                    	
	                    	// match the data type in the query with the file
	                    	// create the string line with the values of the query
	                    	boolean typeValid = true;
	                    	String writeValues = "INT|";
	                    	for (int j = 0; j < finalValues.size(); j++) 
	                    	{
	                    			writeValues += finalValues.get(j) + "|";
	                    	}
	                    	
	                    	if(!typeValid) 
	                    		r.msg = "Invalid data type.";
	                    	else 
	                    	{
		                		// write the line in the file
	                    		tableToDisk.write(columnsUpdated);
	                    		tableToDisk.append("\n");
	                    		tableToDisk.append(writeValues); ;
	                    		
	                    		r.success = true;
	                    		r.msg = "Table " + Database.tableName + " created in the database " + Database.dbName;
	                    		
	                    	}
			        	}
	        		}
	        		catch (IOException ex)
	        		{
	        			r.success = false;
	        			r.msg = ex.toString();
	        		}
	        		finally
	        		{
	        			try
	        			{
	        				tableToDisk.close();
	        			}
	        			catch (Exception ex)
	        			{
	        				r.success = false;
	        				r.msg = ex.toString();
	        			}
	        		}
	        	}
	        }
		}
		catch (Exception ex)
		{
			r.success = false;
			r.msg = ex.toString();
		}
		return r;
	}
	
	public static ReturnValue deleteTable(String query)
	{	
		ReturnValue r = new ReturnValue();
		// COMMAND: delete table %tableName%
		Database.tableName = query.split(" ")[2].toLowerCase();
		Database.dbName = ClientApplication.currentDB;
		String dbTable = Util.DATABASE_FOLDER + "/" + Database.dbName + "/" + tableName + ".txt";
		
		if (!Database.tableName.equals(Util.TBL_USER.toLowerCase()))
		{
			try
			{
				File table = new File(dbTable);
				
				if (Files.deleteIfExists(table.toPath()))
				{
					r.success = true;
					r.msg = "Table " + Database.tableName + " deleted";	
				}
				else
				{
					r.success = false;
					r.msg = "Table " + Database.tableName + " does not exist";	
				}
			}
			catch (Exception ex)
			{
				r.success = false;
				r.msg = ex.toString();
			}
		}
		else
		{
			r.success = false;
			r.msg = "You can not delete " + Util.TBL_USER + " table!";
		}
		return r;
	}
	
	private static boolean parseQuery(String query)
	{
		Pattern patternRecv = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE);
		Matcher matcher = patternRecv.matcher(query);
		boolean success = false;
		String line;
		
		// there are 3 groups to match: table name, columns and dataType
		if (matcher.find())
		{
			// get the table name
        	tableName = matcher.group(2);
        	
        	line = matcher.group(3);
        	String[] groups = line.split(",");
        	ArrayList<String> elem = new ArrayList<String>();
        	
        	for (String str : groups)
        	{
        		for (String e : str.split("\\s+"))
        		{
        			elem.add(e);
        		}
        	}
        	
        	// get the columns to insert
        	columns = new ArrayList<String>();
        	
        	for (int i = 0; i < elem.size(); i+= 2) 
        	{
        		String col = elem.get(i).replace(" ", "");
        		columns.add(col); 
        	}
	        	
	        // get the dataType
	        dataType = new ArrayList<String>();
	        
	        for (int i = 1; i < elem.size(); i+= 2) 
        	{
        		String dt = elem.get(i).replace(" ", "");
        		dataType.add(dt); 
        	}
	        
		    success = true;
		}
		
		return success;
	}
}
