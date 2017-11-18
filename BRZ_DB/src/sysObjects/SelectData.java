package sysObjects;

public class SelectData
{
	private String tableName;
	private String[] fields;
	//private int[] selectedFieldsIds;
	
	public SelectData(String tableName, String databaseName)
	{
		this.tableName = tableName;
		
		Table tbl = TableFile.readTable(databaseName, tableName);
		this.fields = (String[]) tbl.columns.toArray();
	}
}
