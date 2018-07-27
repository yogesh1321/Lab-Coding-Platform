

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import net.proteanit.sql.DbUtils;
import java.awt.Font;

public class login extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JPasswordField passwordField;
	Database db = new Database();
	login frame;
	String loginname;
	String batch;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
					//frame.setUndecorated(true);
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
	String getusername()
	{
		return loginname;
	}
	
	
	public login() {
		setBounds(100, 100, 459, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
	String modes[] = {"Lab Mode","Test Mode"};
		
		JComboBox mode = new JComboBox(modes);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				boolean dbconnect = db.connect();
				if(dbconnect)
				{
					Connection con = db.getconnection();
					try {
						PreparedStatement preSta2 = con.prepareStatement("update registeration SET flag = true where username = ?;");
							preSta2.setString(1, name.getText());
							preSta2.executeUpdate();
							dispose();
						}
						
					 catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.exit(0);
			}
		});
		
		JLabel lblUsername = new JLabel("Username :");
		
		name = new JTextField();
		name.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password :");
		
		passwordField = new JPasswordField();
		
		String values[] = {"Student","Teacher"};
		
		JComboBox select = new JComboBox(values);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nam = name.getText();
				String pass = passwordField.getText();
				if(nam.length() == 0 || pass.length() == 0)
				{
					JOptionPane.showMessageDialog(frame,"Username or password can't be empty");
				}
				else
				{
			boolean dbconnect =	db.connect();
				if(dbconnect)
				{
					Connection con = db.getconnection();
					try {
						if(select.getSelectedIndex() == 0)
						{
						PreparedStatement preStatement = con.prepareStatement("SELECT 'username','password','flag' from registeration where username = ? and password = ? and flag=?;");
						PreparedStatement preSta2 = con.prepareStatement("update registeration SET flag = true where username = ?;");
						preStatement.setString(1,name.getText());
						preStatement.setString(2, passwordField.getText());
						preStatement.setBoolean(3, false);
						ResultSet result = preStatement.executeQuery();
					
						
						if(result.next())
						{
							loginname = name.getText();
							if(mode.getSelectedIndex() == 0)
							{
							first f = new first(loginname);
							f.setVisible(true);
							}
							else
							{
								
								PreparedStatement stmt = con.prepareStatement("Select batch from registeration where username = ?;");
								stmt.setString(1, name.getText().toString());
								ResultSet r = stmt.executeQuery();
								while(r.next())
								{
									batch = r.getString(1).toString();
								}
								
								PreparedStatement stmt2 = con.prepareStatement("Select username,batch,lab,testname from testdata where batch = ? and activel=?;");
								stmt2.setString(1, batch);
								stmt2.setBoolean(2, true);
								ResultSet rs = stmt2.executeQuery();
								TableModel tb = DbUtils.resultSetToTableModel(rs);
								stutestmode st = new stutestmode(tb,name.getText().toString(),batch);
								st.setVisible(true);
							//	dispose();
								
							}
							
							
							preSta2.setString(1, name.getText());
							preSta2.executeUpdate();
							dispose();
						}
						else
						{
							PreparedStatement stmt3 = con.prepareStatement("Select flag from registeration where username = ? and password = ?;");
							stmt3.setString(1, name.getText().toString());
							stmt3.setString(2, passwordField.getText().toString());
							ResultSet rs = stmt3.executeQuery();
							int count = 0;
							if(rs.next())
							{
								count++;
							}
							if(count==0)
							JOptionPane.showMessageDialog(frame,"Username or password is Invalid");
							
							else
							{
								JOptionPane.showMessageDialog(contentPane, "User already signed in");
							}
						}
				
						
						}
						
						
						
						else
						{
							PreparedStatement preStatement = con.prepareStatement("SELECT 'username' and 'password' from teacher_data where username = ? and password = ?;");
							preStatement.setString(1,name.getText());
							preStatement.setString(2, passwordField.getText());
							ResultSet result = preStatement.executeQuery();
							if(result.next())
							{
								loginname = name.getText();
								if(mode.getSelectedIndex()==0 )
								{
								Teacher t = new Teacher(loginname);
								t.setVisible(true);
								}
								else
								{
									teactestmode ttm = new teactestmode(loginname);
									ttm.setVisible(true);
									//dispose();
								}
								dispose();
							}
							else
							{
								JOptionPane.showMessageDialog(frame,"Username or password is invalid");
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				}
			}
		});
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Signup s = new Signup();
				s.setVisible(true);
				dispose();
			}
		});
		
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 19));
		
	
		
		
		
		
		
		
		
		
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(64)
							.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addGap(27)
							.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addGap(27)
							.addComponent(btnSignUp, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(102)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblWelcome)
										.addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(155)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(mode, Alignment.TRAILING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(select, Alignment.TRAILING, 0, 80, Short.MAX_VALUE))))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(31)
					.addComponent(lblWelcome)
					.addGap(67)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsername)
						.addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(select, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(mode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(44)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnLogin)
						.addComponent(btnExit)
						.addComponent(btnSignUp)))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
