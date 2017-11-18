package sysObjects;

public class DataType 
{
	public static boolean isValid(String dataType, String value) 
	{
		if (value == null || value == "")
			return false;
		else if (value == "NULL")
			return true;
		else 
			switch (dataType.toLowerCase())
			{
				case "int":
					try
					{
						Integer.parseInt(value.trim());
						break;
					}
					catch (NumberFormatException ex)
					{
						return false;
					}
				case "text":
					break;
				case "double":
					try
					{
						Double.parseDouble(value.trim());
						break;
					}
					catch (NumberFormatException ex)
					{
						return false;
					}
				case "bit":
					if(!value.trim().equals("1") && !value.trim().equals("0"))
						return false;
					break;
			}
		
		return true;
	}
}
