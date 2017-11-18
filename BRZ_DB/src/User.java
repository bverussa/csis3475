import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.PasswordAuthentication;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

public class User extends JFrame {
	
	private JLabel jlName,jlPassword,jlConfirm;
	private static JTextField tfName;
	private static JTextField tfPassword;
	private static JTextField tfConfirm;
	private static JRadioButton rbAdmin, rbUser;
	private static ButtonGroup bgGroup;
	private static JButton btnCreate;
	private static String name;
	private static String password;
	private static String user;
	
	public User() {
		// TODO Auto-generated constructor stub
		this.setVisible(true);
		this.setBounds(30,40,400,300);
		this.setLayout(null);
		
		jlName = new JLabel("Name: ");
		addMe(jlName, 10, 10, 70, 20);
		
		jlPassword = new JLabel("Password: ");
		addMe(jlPassword, 9, 35, 70, 20);
		
		jlConfirm = new JLabel("Confirm: ");
		addMe(jlConfirm, 10, 60, 70, 20);
		
		tfName = new JTextField();
		addMe(tfName, 75, 10, 150, 20);
		
		tfConfirm = new JTextField();
		addMe(tfConfirm, 75, 60, 150, 20);
		
		tfPassword = new JTextField();
		addMe(tfPassword, 75, 35, 150, 20);
		
		rbAdmin = new JRadioButton("Administrator");
		addMe(rbAdmin,230, 10, 120, 20);
		rbAdmin.setActionCommand("Admin");
		
		rbUser = new JRadioButton("User");
		addMe(rbUser,230, 30, 120, 20);
		rbUser.setActionCommand("User");
		
		btnCreate = new JButton("Create");
		addMe(btnCreate, 30, 80, 90, 20);
		
		bgGroup = new ButtonGroup();
		bgGroup.add(rbAdmin);
		bgGroup.add(rbUser);
		
		btnCreate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				validateUser();
			}
		});
	}
	
	
	private void addMe(Component c, int x, int y, int w, int h){
		add(c);
		c.setBounds(x,y,w,h);
	}
	
	public static void main(String args[]){
		new User();
		
}
	
	//|| !tfPassword.getText().trim().equals("")
	public static void validateUser(){
		
		ReturnValue r = new ReturnValue();
		
		if(!(tfName.getText()==null) ){
			name = tfName.getText();
			
			if(!(tfPassword.getText()==null) ){
				if(!(tfConfirm.getText()==null)){
					if(tfPassword.getText().equals(tfConfirm.getText())){
						password = tfPassword.getText();
						
						if(!(bgGroup.getSelection()==null)){
							user = bgGroup.getSelection().getActionCommand();
							InsertUser(name, password, user);
						}else{
							r.msg = "Invalid User Type . Please check if you have selected the user type  .";
						}
						
					}else{
						r.msg = "Password does not match . Please check if you entered correct password  .";
					}
				}else{
					r.msg = "Password does not match . Please check if you entered correct password  .";
				}
			}
				else{
				r.msg = "Invalid Password . Please check if you entered correct password  .";
			}
		}else{
			r.msg = "User or Password is no valid . Please check if you have entered correct  .";
		}	
		
	}
	
	public static void InsertUser(String name,String password, String userType){
		Insert.run("INSERT INTO tblUser values("+name+","+password+","+userType+")", "master");
		
	}

}
