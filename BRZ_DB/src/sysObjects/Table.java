package sysObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable
{
	public String name;
	public ArrayList<String> columns;
	public ArrayList<String> types;
}
