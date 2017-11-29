package sysObjects;

public class SelectData implements Comparable<SelectData>
{
	public String[] fields;
	private int col;

	public SelectData(String[] fields, int columnCompare)
	{
		this.fields = fields;
		this.col = columnCompare;
	}
	
	@Override
	public int compareTo(SelectData selectData)
	{
		return fields[col].compareTo(selectData.fields[col]);
	}
}
