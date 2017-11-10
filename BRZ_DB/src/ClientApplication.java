import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
	
	public ClientApplication()
	{
		this.setVisible(true);
		this.setBounds(400, 130, 540, 410);
		this.setLayout(null);
		this.setTitle("CSIS3475 Project: Database");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		createMenu();
		
		lbl = new JLabel("Welcome! Choose an option from the menu above to start.");
		Util.addComponent(this, lbl, 80, 60, 560, 20);
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
				new Database(1);
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
				new Database(2);
			}
		});
		mDatabase.add(mDeleteDatabase);

		menuBar.add(mDatabase);
		
		// Menu Database - end 
		
		// Menu Table - begin 
		
		mTable = new JMenu("Table");
		mTable.setMnemonic(KeyEvent.VK_T);
		menuBar.add(mTable);
		
		mCreateTable = new JMenuItem("Create");
		mCreateTable.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// Create table
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

		menuBar.add(mTable);
		
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
				// New Query
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
				// New Query
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
}
