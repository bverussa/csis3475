import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TableFile 
{
	private static final String DATABASE_FOLDER = "Databases";
	
	private static String getFullPath(String databaseName, String tableName)
	{
		String format = "%s\\%s\\%s\\%s.txt";
		
		return String.format(
				format, 
				System.getProperty("user.dir"), 
				DATABASE_FOLDER, 
				databaseName, 
				tableName);
	}
	
	public static String writeLine(String databaseName, String tableName, String value)
	{
		String filename = getFullPath(databaseName, tableName);
        
		try
		{
			FileWriter fwriter = new FileWriter(filename, true);
	    	BufferedWriter bf = new BufferedWriter(fwriter);
	    	bf.append(System.lineSeparator());
	        bf.append(value);
	        bf.close();
	        
	        return "Query executed successfully!";
		}
		catch(IOException ex)
		{
			return "Error to execute the query.";
		}
	}
	
	public static Table readTable(String databaseName, String tableName)
	{
		String filename = getFullPath(databaseName, tableName);
		
		try
		{
			FileReader freader = new FileReader(filename);
	        BufferedReader br = new BufferedReader(freader);
	        
	        int lineCount = 1;
	        
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
	            {
	            	tbl.types = new ArrayList<String>(Arrays.asList(data.split("\\|")));
	            		
	            	// loop all the lines of the file to get the last SYSID
                	while ((data = br.readLine()) != null) 
                		lineCount++;
                	
                	tbl.nextSysID = lineCount;
	            }
	        }
	        
	        br.close();
	        
	        return tbl;
		}
		catch(IOException ex)
		{
			return null;
		}
	}
	
	public static boolean columnsValid(Table tbl, ArrayList<String> columns)
	{
		for (String col : columns)
			if (!tbl.columns.contains(col.trim()))
				return false;
		
		return true;
	}
}
