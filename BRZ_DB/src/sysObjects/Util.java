package sysObjects;
import java.awt.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

public class Util 
{
	public static String DB_MASTER = "master";
	public static String TBL_USER = "tblUser";
	public static String USER_ADMIN = "admin";
	public static String DATABASE_FOLDER = "Databases";
	
	public static void addComponent(JFrame frame, Component c, int x, int y, int w, int h) 
	{
		frame.add(c);
		c.setBounds(x, y, w, h);
	}
	
	public static boolean isCommandValid(String query, String pattern)
	{
		Pattern patternRecv = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = patternRecv.matcher(query);
        
        return matcher.matches();
	}
}
