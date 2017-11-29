import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import sysObjects.ReturnValue;
import sysObjects.Util;

public class ClientApplication extends JFrame 
{
	private JMenuBar menuBar; 
	
	private JMenu mQuery;
	private JMenuItem mNewQuery;
	
	private JMenu mHelp;
	private JMenuItem mHowTo;
	
	private JLabel lbl;
	private JLabel lblCurrentDB;
	private JLabel lblCurrentUser;
	private JLabel lblCurrentUserType;
	private JLabel lblHowTo;
	
	private static JTextField txtCurrentDB;
	private JTextField txtCurrentUser;
	private JTextField txtCurrentUserType;
	
	private JTextArea txtHowTo; 
	private JScrollPane scHowTo;
	private JTextArea txtQuery;
	private JScrollPane scQuery;
	private JTextArea txtResult;
	private JScrollPane scResult;
	private JButton btnRun;
	
	public static String currentDB;
	public static String userName;
	public static int userType;
	
	public ClientApplication()
	{
		ClientApplication.currentDB = Util.DB_MASTER;
		this.setVisible(true);
		this.setBounds(400, 130, 540, 440);
		this.setLayout(null);
		this.setTitle("CSIS3475 Project: Database");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		createMenu();
		
		lbl = new JLabel("Welcome! Choose an option from the menu above to start.");
		Util.addComponent(this, lbl, 80, 60, 560, 20);
		
		lblCurrentDB = new JLabel("Database: ");
		Util.addComponent(this, lblCurrentDB, 10, 340, 100, 20);
		txtCurrentDB = new JTextField(ClientApplication.currentDB);
		txtCurrentDB.setEnabled(false);
		Util.addComponent(this, txtCurrentDB, 70, 340, 100, 20);
		lblCurrentUser = new JLabel("User Name: ");
		Util.addComponent(this, lblCurrentUser, 170, 340, 100, 20);
		txtCurrentUser = new JTextField(ClientApplication.userName);
		txtCurrentUser.setEnabled(false);
		Util.addComponent(this, txtCurrentUser, 240, 340, 100, 20);
		lblCurrentUserType = new JLabel("Type:");
		Util.addComponent(this, lblCurrentUserType, 340, 340, 100, 20);
		txtCurrentUserType = new JTextField(User.getUserType(ClientApplication.userType));
		txtCurrentUserType.setEnabled(false);
		Util.addComponent(this, txtCurrentUserType, 370, 340, 100, 20);
		
		lblHowTo = new JLabel("How To:");
		Util.addComponent(this, lblHowTo, 15, 15, 100, 20);
		txtHowTo = new JTextArea();
		txtHowTo.setEditable(false);
		txtHowTo.setLineWrap(true);
		DefaultCaret caret = (DefaultCaret) txtHowTo.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		
		txtHowTo.setText("*** SPECIFICATIONS *** \n\n" +
			"Databases are folders \n" +
			"Files inside these folders are the tables \n" +
			"Files names are the tables names \n" +
			"Columns are separated by pipeline | \n" +
			"Every Table file will have the 2 first rows for the table structure.\nFor example: \n" +
			"ID | NAME | SALARY --> columns names \n" +
			"INT | TEXT | DOUBLE --> data types \n" +	
			"Default Database: MASTER \n" +
			"Default User Table on MASTER: tblUser \n" +
			"Data types: INT, TEXT, DOUBLE, BIT \n" +
			"Table User will have a user default (admin, pwd: admin) \n" +
			"User Types: 1 for administrator, 2 for user \n" +
			"Table Database will have a default database (master), which will have the \ntable User (tblUser) \n\n" +
			"*** COMMAND LINES *** \n\n" +
			"CREATE DATABASE %DBNAME% \n\n" +
			"DELETE DATABASE %DBNAME% \n\n" +
			"CONNECT %DBNAME% \n\n" +
			"SHOW %DBNAME% \n\n" +
			"CREATE TABLE %TABLENAME% (%COLUMN1% %DATATYPE1%, %COLUMN2% %DATATYPE2%) \n\n" +
			"DELETE TABLE %TABLENAME%\n\n" +
			"SELECT * FROM %TABLENAME% WHERE %COLUMN% = %TERM_TO_SEARCH% \n" +
			"INSERT INTO %TABLENAME% (%COLUMN1%, %COLUMN2%) VALUES (%VAL1%, %VAL2%)" 
		);
		scHowTo = new JScrollPane(txtHowTo);
		Util.addComponent(this, scHowTo, 15, 35, 490, 300);
		
		
		txtQuery = new JTextArea();
		scQuery = new JScrollPane(txtQuery);
		Util.addComponent(this, scQuery, 15, 15, 490, 140);
		
		txtResult = new JTextArea();
		txtResult.setEditable(false);
		scResult = new JScrollPane(txtResult);
		Util.addComponent(this, scResult, 15, 165, 490, 140);
		
		btnRun = new JButton("Run Query");
		btnRun.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String databaseName = ClientApplication.currentDB;
				
				ReturnValue r = Query.run(txtQuery.getText().trim(), databaseName);
				txtResult.setText(r.msg);
			}
		});
		
		Util.addComponent(this, btnRun, 400, 315, 100, 20);
		
		lbl.setVisible(true);
		setMainVisible(true);
		setHowToVisible(true);
	}
	
	private void setMainVisible(boolean visible)
	{
		txtQuery.setVisible(!visible);
		scQuery.setVisible(!visible);
		txtResult.setVisible(!visible);
		scResult.setVisible(!visible);
		btnRun.setVisible(!visible);
	}
	
	private void setHowToVisible(boolean visible)
	{
		lblHowTo.setVisible(!visible);
		txtHowTo.setVisible(!visible);
		scHowTo.setVisible(!visible);
	}
	
	private void createMenu()
	{
		menuBar = new JMenuBar();
		
		// Menu Query - begin 
		
		mQuery = new JMenu("Query");
		mQuery.setMnemonic(KeyEvent.VK_Q);
		menuBar.add(mQuery);
		
		mNewQuery = new JMenuItem("New Query");
		mNewQuery.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				lbl.setVisible(false);
				setMainVisible(false);
				setHowToVisible(true);
			}
		});
		mQuery.add(mNewQuery);
		
		menuBar.add(mQuery);
		
		// Menu Query - end
		
		mHelp = new JMenu("Help");
		mHelp.setMnemonic(KeyEvent.VK_Q);
		menuBar.add(mHelp);
		
		mHowTo = new JMenuItem("How To");
		mHowTo.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				lbl.setVisible(false);
				setMainVisible(true);
				setHowToVisible(false);
			}
		});
		mHelp.add(mHowTo);
		
		menuBar.add(mHelp);
		
		// Menu Help - begin
		
		// Menu Help - end
		
		// Set Menu Bar
		this.setJMenuBar(menuBar);
	}
	
	public static ReturnValue getCurrentDb()
	{
		ReturnValue r = new ReturnValue();
		r.success = true;
		
		if (ClientApplication.currentDB != null && !ClientApplication.currentDB.isEmpty())
		{
			r.msg = "You are connect to " + ClientApplication.currentDB + " database";
		}
		else
		{
			r.msg = "There is no database selected";
		}
			
		return r;
	}
	
	public static void setCurrentDb()
	{
		txtCurrentDB.setText(currentDB);
	}
	
	public static ReturnValue setCurrentDb(String query)
	{
		ReturnValue r = new ReturnValue();
		// CONNECT %DBNAME%
		ClientApplication.currentDB = query.split(" ")[1].toLowerCase();
		txtCurrentDB.setText(currentDB);
		r.success = true;
		r.msg = "Connected to " + ClientApplication.currentDB;
		return r;
	}
}
