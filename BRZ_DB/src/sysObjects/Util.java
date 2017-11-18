package sysObjects;
import java.awt.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

public class Util 
{
	public static void addComponent(JFrame frame, Component c, int x, int y, int w, int h) 
	{
		frame.add(c);
		c.setBounds(x, y, w, h);
	}
	
	public static boolean isCommandValid(String query, String pattern)
	{
		Pattern patternRec = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = patternRec.matcher(query);
        
        return matcher.matches();
	}
}
