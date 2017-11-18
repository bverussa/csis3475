import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
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
	private final static String PATTERN = "CREATE TABLE ([\\w\\d_]+) \\(([\\w\\d_,\\s]+)\\) VALUES \\(([\\w\\d_.,\\s]+)\\)";
	private final static String REGEX = "((?<=(CREATE\\sTABLE\\s))[\\w\\d_]+(?=\\s+))|((?<=\\()([\\w\\d_.,\\s]+)+(?=\\)))";
	
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
		Database.dbName = getUserInput(1, "Create Database").toLowerCase();
		
		if (!Database.dbName.isEmpty())
		{
			File database = new File("Databases/" + dbName);
			
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
			Database.dbName = null;
			ClientApplication.currentDB = Database.dbName;
			JOptionPane.showMessageDialog(null, "Please, enter a database name");
		}
	}
	
	public static ReturnValue createDB(String query)
	{
		ReturnValue r = new ReturnValue();
		// CREATE DATABASE %DBNAME%
		Database.dbName = query.split(" ")[2].toLowerCase();
		
		if (!Database.dbName.isEmpty())
		{
			File database = new File("Databases/" + dbName);
			
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
			Database.dbName = null;
			ClientApplication.currentDB = Database.dbName;
			r.success = false;
			r.msg = "Please, enter a database name";
		}
		return r;
	}
	
	public void deleteDB()
	{
		Database.dbName = getUserInput(1, "Delete Database").toLowerCase();
		
		if (!Database.dbName.isEmpty())
		{	
			if (!Database.dbName.equals("master"))
			{
				File database = new File("Databases/" + Database.dbName);
				
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
			        ClientApplication.currentDB = null;
			        JOptionPane.showMessageDialog(null, "Database " + Database.dbName + " deleted!");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Database " + Database.dbName + " does not exist!");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "You can not delete MASTER database!");
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
		// DELETE DATABASE %DBNAME%
		Database.dbName = query.split(" ")[2].toLowerCase();
		
		if (!Database.dbName.isEmpty())
		{	
			if (!Database.dbName.equals("master"))
			{
				File database = new File("Databases/" + Database.dbName);
				
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
			        ClientApplication.currentDB = null;
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
		        r.msg = "You can not delete MASTER database!";
			}
		}
		else
		{
			r.success = false;
	        r.msg = "Please, enter a database name";
		}
		return r;
	}
	
	public static ReturnValue createTable(String query)
	{	
		ReturnValue r = new ReturnValue();
		// CREATE TABLE %TABLENAME%
		Database.tableName = query.split(" ")[2].toLowerCase();
		Database.dbName = ClientApplication.currentDB;
		String dbTable = "Databases/" + Database.dbName + "/" + tableName + ".txt";
		Writer tableToDisk = null;
		
		try
		{
			tableToDisk = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(dbTable), "utf-8"));
			
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
				
				if (!Util.isCommandValid(query, PATTERN)) 
				{
					r.success = false;
		        	r.msg = "Invalid query. Please check if you entered the correct command.";
				}
		        else
		        {
		        	r.success = true;
					r.msg = "Table " + Database.tableName + " created in the database " + Database.dbName;
		        }
			}
			catch (Exception ex)
			{
				r.success = false;
				r.msg = ex.toString();
			}
		}
		return r;
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
}
