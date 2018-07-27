

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.awt.Font;

import javax.swing.JButton;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class teactestmode extends JFrame {

	private JPanel contentPane;
	JComboBox batc;
	String batch;
	Database db = new Database();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					teactestmode frame = new teactestmode("");
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
String getbatch()
{
	return batch;
}
	
	public teactestmode(String loginname) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel year = new JLabel("Year :");
		year.setFont(new Font("Tahoma", Font.PLAIN, 16));
		year.setBounds(59, 125, 43, 20);
		contentPane.add(year);
		
		String value[] = {"First","Second","Third","Fourth"};
		
		String val[] = {"Select"};
		 batc = new JComboBox(val);
		batc.setBounds(281, 125, 74, 22);
		contentPane.add(batc);
		
		
	DefaultComboBoxModel one = new DefaultComboBoxModel(new String[]{"A1","A2","A3"});
		
		DefaultComboBoxModel two = new DefaultComboBoxModel(new String[]{"B1","B2","B3"});
		
		DefaultComboBoxModel three = new DefaultComboBoxModel(new String[]{"C1","C2","C3"});
		
		DefaultComboBoxModel four = new DefaultComboBoxModel(new String[]{"D1","D2","D3"});
	
		
		JComboBox yea = new JComboBox(value);
		yea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
int selectedyear = yea.getSelectedIndex();
				
				
				if(selectedyear == 0)
				{
					batc.setModel(one);
					//batc.setBounds(281, 84, 62, 22);
					contentPane.add(batc);
				}
				else if(selectedyear == 1)
				{
					
					batc.setModel(two);
					//batc.setBounds(281, 84, 62, 22);
					contentPane.add(batc);
				}
				else if(selectedyear == 2)
				{
					batc.setModel(three);
					//batc.setBounds(281, 84, 62, 22);
					contentPane.add(batc);
				}
				else 
				{
					batc.setModel(four);
					//batc.setBounds(281, 84, 62, 22);
					contentPane.add(batc);
				}
				
				contentPane.add(batc);
			
			}
		});
		yea.setBounds(113, 125, 74, 22);
		contentPane.add(yea);
		
		JLabel lblSelectYearAnd = new JLabel("Select Year and Batch");
		lblSelectYearAnd.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblSelectYearAnd.setBounds(42, 13, 364, 49);
		contentPane.add(lblSelectYearAnd);
		
		JLabel lblBatch = new JLabel("Batch :");
		lblBatch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBatch.setBounds(220, 125, 49, 20);
		contentPane.add(lblBatch);
		
		
		
		JButton btnSubmit = new JButton("View");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				batch = batc.getSelectedItem().toString();
				boolean dbconnect =db.connect();
				if(dbconnect)
				{
					Connection con = db.getconnection();
					try {
						PreparedStatement preparedStatement  = con.prepareStatement("Select username,batch,lab,testname from testdata where batch=?;");
						preparedStatement.setString(1, batc.getSelectedItem().toString());
						ResultSet rs = preparedStatement.executeQuery(); 
						TableModel tb= DbUtils.resultSetToTableModel(rs);	
						viewtests tm = new viewtests(tb,batch,loginname);
						tm.setVisible(true);
						//dispose();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		btnSubmit.setBounds(172, 184, 97, 25);
		contentPane.add(btnSubmit);
		
		JButton btnCreateTest = new JButton("Create Test");
		btnCreateTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				test t = new test(loginname,batc.getSelectedItem().toString());
				t.setVisible(true);
			}
		});
		btnCreateTest.setBounds(42, 184, 125, 25);
		contentPane.add(btnCreateTest);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setBounds(281, 184, 97, 25);
		contentPane.add(btnExit);
		
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblWelcome.setBounds(118, 75, 69, 16);
		contentPane.add(lblWelcome);
		
		JLabel setname = new JLabel("");
		setname.setBounds(199, 75, 56, 16);
		contentPane.add(setname);
		
		setname.setText(loginname);
	}
}
