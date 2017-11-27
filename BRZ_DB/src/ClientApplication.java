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

import sysObjects.ReturnValue;
import sysObjects.Util;

public class ClientApplication extends JFrame 
{
	private JMenuBar menuBar; 
	
	private JMenu mDatabase;
	private JMenuItem mCreateDatabase;
	private JMenuItem mDeleteDatabase;
	
	private JMenu mTable;
	private JMenuItem mCreateTable;
	private JMenuItem mUpdateTable;
	private JMenuItem mDeleteTable;
	
	private JMenu mQuery;
	private JMenuItem mNewQuery;
	
	private JMenu mUsers;
	private JMenuItem mNewUser;
	private JMenuItem mEditUser;
	private JMenuItem mDeleteUser;
	
	private JLabel lbl;
	private JLabel lblCurrentDB;
	private JLabel lblCurrentUser;
	private JLabel lblCurrentUserType;
	
	private JTextField txtCurrentDB;
	private JTextField txtCurrentUser;
	private JTextField txtCurrentUserType;
	
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
		this.currentDB = Util.DB_MASTER;
		this.setVisible(true);
		this.setBounds(400, 130, 540, 410);
		this.setLayout(null);
		this.setTitle("CSIS3475 Project: Database");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		createMenu();
		
		lbl = new JLabel("Welcome! Choose an option from the menu above to start.");
		Util.addComponent(this, lbl, 80, 60, 560, 20);
		
		lblCurrentDB = new JLabel("Database: ");
		Util.addComponent(this, lblCurrentDB, 10, 340, 100, 20);
		txtCurrentDB = new JTextField(currentDB);
		txtCurrentDB.setEnabled(false);
		Util.addComponent(this, txtCurrentDB, 70, 340, 100, 20);
		lblCurrentUser = new JLabel("User Name: ");
		Util.addComponent(this, lblCurrentUser, 170, 340, 100, 20);
		txtCurrentUser = new JTextField(userName);
		txtCurrentUser.setEnabled(false);
		Util.addComponent(this, txtCurrentUser, 240, 340, 100, 20);
		lblCurrentUserType = new JLabel("Type:");
		Util.addComponent(this, lblCurrentUserType, 340, 340, 100, 20);
		txtCurrentUserType = new JTextField(getUserType());
		txtCurrentUserType.setEnabled(false);
		Util.addComponent(this, txtCurrentUserType, 370, 340, 100, 20);
		
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
				// Query for tests
				// INSERT INTO tblUser (ID, Username, Password) VALUES (3, Cris, Tokoi)
				
				//TODO: get database name selected by the user
				//String databaseName = "master";
				String databaseName = ClientApplication.currentDB;
				
				ReturnValue r = Query.run(txtQuery.getText().trim(), databaseName);
				txtResult.setText(r.msg);
			}
		});
		
		Util.addComponent(this, btnRun, 400, 315, 100, 20);
		
		setMainVisible(true);
	}
	
	private void setMainVisible(boolean visible)
	{
		lbl.setVisible(visible);
		txtQuery.setVisible(!visible);
		scQuery.setVisible(!visible);
		txtResult.setVisible(!visible);
		scResult.setVisible(!visible);
		btnRun.setVisible(!visible);
	}
	
	private void createMenu()
	{
		menuBar = new JMenuBar();

		// Menu Database - begin 
		
		mDatabase = new JMenu("Database");
		mDatabase.setMnemonic(KeyEvent.VK_D);
		menuBar.add(mDatabase);
		
		mCreateDatabase = new JMenuItem("Create");
		mCreateDatabase.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// Create database
				Database db = new Database();
				db.createDB();
				
			}
		});
		mDatabase.add(mCreateDatabase);
		
		mDeleteDatabase = new JMenuItem("Delete");
		mDeleteDatabase.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// Delete database
				Database db = new Database();
				db.deleteDB();
			}
		});
		mDatabase.add(mDeleteDatabase);

		menuBar.add(mDatabase);
		
		// Menu Database - end 
		
		// Menu Table - begin 
		
		/*mTable = new JMenu("Table");
		mTable.setMnemonic(KeyEvent.VK_T);
		menuBar.add(mTable);
		
		mCreateTable = new JMenuItem("Create");
		mCreateTable.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// Create table
				Database db = new Database();
				db.createTable();
				
			}
		});
		mTable.add(mCreateTable);
		
		mUpdateTable = new JMenuItem("Update");
		mUpdateTable.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// Update table
			}
		});
		mTable.add(mUpdateTable);
		
		mDeleteTable = new JMenuItem("Delete");
		mDeleteTable.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// Delete table
			}
		});
		mTable.add(mDeleteTable);

		menuBar.add(mTable);*/
		
		// Menu Table - end
		
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
				setMainVisible(false);
			}
		});
		mQuery.add(mNewQuery);
		
		menuBar.add(mQuery);
		
		// Menu Query - end
		
		// Menu Users - begin 
		
		mUsers = new JMenu("Users");
		mUsers.setMnemonic(KeyEvent.VK_U);
		menuBar.add(mUsers);
		
		mNewUser = new JMenuItem("New User");
		mNewUser.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				new User();
			}
		});
		mUsers.add(mNewUser);
		
		mEditUser = new JMenuItem("Edit User");
		mEditUser.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// New Query
			}
		});
		mUsers.add(mEditUser);
		
		mDeleteUser = new JMenuItem("Delete User");
		mDeleteUser.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// New Query
			}
		});
		mUsers.add(mDeleteUser);
		
		menuBar.add(mUsers);
		
		// Menu Users - end
		
		// Set Menu Bar
		this.setJMenuBar(menuBar);
	}
	
	public static ReturnValue getCurrentDb()
	{
		ReturnValue r = new ReturnValue();
		r.success = true;
		
		if (ClientApplication.currentDB != null && !ClientApplication.currentDB.isEmpty())
		{
			r.msg = ClientApplication.currentDB;
		}
		else
		{
			r.msg = "There is no database selected";
		}
			
		return r;
	}
	
	public static ReturnValue setCurrentDb(String query)
	{
		ReturnValue r = new ReturnValue();
		// CONNECT %DBNAME%
		ClientApplication.currentDB = query.split(" ")[1].toLowerCase();;
		r.success = true;
		r.msg = "Connected to " + ClientApplication.currentDB;
		return r;
	}
	
	public static String getUserType()
	{
		String type;
		
		if (userType == 1)
		{
			type = "Admin";
		}
		else
		{
			type = "User";
		}
		return type;
	}
}
