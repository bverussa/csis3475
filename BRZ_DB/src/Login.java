import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Login extends JFrame 
{
	private JButton btnLogin;
	
	public Login()
	{
		this.setVisible(true);
		this.setBounds(400, 130, 540, 410);
		this.setLayout(null);
		this.setTitle("CSIS3475 Project: Database");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				login();
			}
		});
		
		Util.addComponent(this, btnLogin, 210, 110, 100, 20);
	}
	
	public static void main(String[] args) 
	{
		new Login();
		
		/*String test = Query.run("INSERT INTO tblUser (ID, Username, Password) VALUES (2, Cris, Tokoi)", "master");
		System.out.println(test);*/
	}
	
	private void login()
	{
		this.setVisible(false);
		new ClientApplication();
	}

}
