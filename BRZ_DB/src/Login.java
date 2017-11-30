import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import sysObjects.ReturnValue;
import sysObjects.Util;

public class Login extends JFrame 
{
	private JLabel lblUserName, lblPassword;
	private JTextField txtUserName;
	private JPasswordField txtPassword;
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
		
		lblUserName = new JLabel("User Name: ");
		Util.addComponent(this, lblUserName, 170, 50, 100, 20);
		txtUserName = new JTextField();
		Util.addComponent(this, txtUserName, 250, 50, 120, 20);
		lblPassword = new JLabel("Password: ");
		Util.addComponent(this, lblPassword, 170, 80, 100, 20);
		txtPassword = new JPasswordField();
		txtPassword.setEchoChar('*');
		Util.addComponent(this, txtPassword, 250, 80, 120, 20);
		
		Util.addComponent(this, btnLogin, 270, 110, 80, 20);
	}
	
	public static void main(String[] args) 
	{
		new Login();
	}
	
	private void login()
	{
		ReturnValue r = User.checkLogin(txtUserName.getText().trim(), String.valueOf(txtPassword.getPassword()));
		if (r.success)
		{
			this.setVisible(false);
			new ClientApplication();
		}
		else
		{
			JOptionPane.showMessageDialog(null, r.msg, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
