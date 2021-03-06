package sysObjects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import dataStructures.MyBinaryTree;

public class TableFile 
{
	private static String getFullPath(String databaseName, String tableName)
	{
		String format = "%s\\%s\\%s\\%s.txt";
		
		return String.format(
				format, 
				System.getProperty("user.dir"), 
				Util.DATABASE_FOLDER, 
				databaseName, 
				tableName);
	}
	
	public static boolean writeLine(String databaseName, String tableName, String value)
	{
		String filename = getFullPath(databaseName, tableName);
        
		try
		{
			FileWriter fwriter = new FileWriter(filename, true);
	    	BufferedWriter bf = new BufferedWriter(fwriter);
	    	bf.append(System.lineSeparator());
	        bf.append(value);
	        bf.close();
	        
	        return true;
		}
		catch(IOException ex)
		{
			return false;
		}
	}
	
	public static Table readTable(String databaseName, String tableName)
	{
		String filename = getFullPath(databaseName, tableName);
		
		try
		{
			FileReader freader = new FileReader(filename);
	        BufferedReader br = new BufferedReader(freader);
	        
	        Table tbl = new Table();

	        // read the first line (columns)
	        String data = br.readLine();
	        
	        if (data == null)
	        {
	        	br.close();
	        	return null;
	        }
	        else 
	        {
	        	tbl.columns = new ArrayList<String>(Arrays.asList(data.split("\\|")));
	        	
	        	// read the second line (types)
	            data = br.readLine();
	            
	            if (data == null)
	            {
	            	br.close();
	            	return null;
	            }
	            else 
	            	tbl.types = new ArrayList<String>(Arrays.asList(data.split("\\|")));
	        }
	        
	        br.close();
	        
	        return tbl;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	public static ArrayList<String> readTableContent(String databaseName, String tableName)
	{
		ArrayList<String> results = new ArrayList<String>();
		String filename = getFullPath(databaseName, tableName);
		
		try
		{
			FileReader freader = new FileReader(filename);
	        BufferedReader br = new BufferedReader(freader);
	        
	        // read the first 2 lines (columns and types)
	        br.readLine();
	        br.readLine();
	        
	        String data;
	        
	        while ((data = br.readLine()) != null) 
	        {
				results.add(data);
			}
	        
	        br.close();
	        
	        return results;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	public static MyBinaryTree<SelectData> readTableContent(String databaseName, String tableName, int columnCompare)
	{
		MyBinaryTree<SelectData> binTree = new MyBinaryTree<SelectData>();
		String filename = getFullPath(databaseName, tableName);
		
		try
		{
			FileReader freader = new FileReader(filename);
	        BufferedReader br = new BufferedReader(freader);
	        
	        // read the first 2 lines (columns and types)
	        br.readLine();
	        br.readLine();
	        
	        String data;
	        String[] values;
	        SelectData selectData;
	        
	        while ((data = br.readLine()) != null) 
	        {
	        	values = data.split("\\|");
	        	selectData = new SelectData(values, columnCompare);
				binTree.add(selectData);
			}
	        
	        br.close();
	        
	        return binTree;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	public static boolean columnsValid(Table tbl, ArrayList<String> columns)
	{
		for (String col : columns)
			if (!tbl.columns.contains(col.trim().toUpperCase()))
				return false;
		
		return true;
	}
}
