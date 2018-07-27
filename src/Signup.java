

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Signup extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JPasswordField passwordField;
	Signup frame;
	Database db = new Database();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Signup frame = new Signup();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Signup() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRollNo = new JLabel("Username :");
		lblRollNo.setBounds(44, 59, 69, 16);
		contentPane.add(lblRollNo);
		
		name = new JTextField();
		name.setBounds(116, 56, 116, 22);
		contentPane.add(name);
		name.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(44, 91, 69, 16);
		contentPane.add(lblPassword);
		
		JLabel year = new JLabel("Year :");
		year.setBounds(44, 124, 69, 16);
		contentPane.add(year);
		
		JLabel lblBatch = new JLabel("Batch :");
		lblBatch.setBounds(44, 153, 56, 16);
		contentPane.add(lblBatch);
		
		
		
		
	/*	JComboBox batch = new JComboBox();
		batch.setBounds(116, 150, 31, 22);
		contentPane.add(batch);*/
		
		
		
		passwordField = new JPasswordField();
		passwordField.setBounds(116, 88, 116, 22);
		contentPane.add(passwordField);
		
		JButton btnSignUp = new JButton("Sign Up");
		
		btnSignUp.setBounds(36, 198, 97, 25);
		contentPane.add(btnSignUp);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(184, 198, 97, 25);
		contentPane.add(btnExit);
		JComboBox batch ;
		String value[] = {"A1","A2","A3"};
		batch = new JComboBox(value);
		
		batch.setBounds(116, 150, 74, 22);
		contentPane.add(batch);
		
		
		DefaultComboBoxModel one = new DefaultComboBoxModel(new String[]{"A1","A2","A3"});
		
		DefaultComboBoxModel two = new DefaultComboBoxModel(new String[]{"B1","B2","B3"});
		
		DefaultComboBoxModel three = new DefaultComboBoxModel(new String[]{"C1","C2","C3"});
		
		DefaultComboBoxModel four = new DefaultComboBoxModel(new String[]{"D1","D2","D3"});
		
		String val[] = {"First","Second","Third","Fourth"};
		JComboBox yea = new JComboBox(val);
		yea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedyear = yea.getSelectedIndex();
				
				
				if(selectedyear == 0)
				{
					batch.setModel(one);
					batch.setBounds(116, 150, 74, 22);
					contentPane.add(batch);
				}
				else if(selectedyear == 1)
				{
					
					batch.setModel(two);
					batch.setBounds(116, 150, 74, 22);
					contentPane.add(batch);
				}
				else if(selectedyear == 2)
				{
					batch.setModel(three);
					batch.setBounds(116, 150, 74, 22);
					contentPane.add(batch);
				}
				else 
				{
					batch.setModel(four);
					batch.setBounds(116, 150, 74, 22);
					contentPane.add(batch);
				}
				
				contentPane.add(batch);
			}
		});
		yea.setBounds(116, 121, 74, 22);
		contentPane.add(yea);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				login l = new login();
				l.setVisible(true);
				dispose();
			}
		});
		btnLogin.setBounds(323, 198, 97, 25);
		contentPane.add(btnLogin);
			
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String nam = name.getText().toString();
				String pass = passwordField.getText().toString();
				System.out.println(nam.length());
				if(nam.length() == 0 || pass.length() == 0)
				{
					JOptionPane.showMessageDialog(frame,"Username or password is empty");
				}
				else
				{
				boolean dbconnect = db.connect();
				if(dbconnect)
						{
					Connection con = db.getconnection();
									
					try
					{
						PreparedStatement preStatement = con.prepareStatement("insert into "
								+ "registeration(username,password,year,batch,flag) values(?,?,?,?,?)");
					preStatement.setString(1,name.getText());
					preStatement.setString(2, passwordField.getText());
					preStatement.setString(3, yea.getSelectedItem().toString());
					preStatement.setString(4, batch.getSelectedItem().toString());
					preStatement.setBoolean(5, false);
                   preStatement.execute();
                   login l = new login();
                   l.setVisible(true);
                   dispose();
                   // if(check)
                    {
                    	System.out.println("Hello");
                    	JOptionPane.showMessageDialog(frame, "Successfully Registered");
                    }
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
						}
				else
				{
					try {
						db.getconnection().close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				System.out.println("Database Not Connected");	
				}
				
			}
			}
		});

	}

}
