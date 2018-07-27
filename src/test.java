import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;


public class test extends JFrame {

	private JPanel quessele;
	private JTextField testname;
	private JTextField key2;
	int count = 1;
	Database db = new Database();
	Vector<String> labn;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test("q","qw");
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
	public test(String loginname,String batch) {
		boolean connect = db.connect();
		if(connect)
		{
			Connection con = db.getconnection();
			try {
				PreparedStatement stmt = con.prepareStatement("Select distinct labname from labdata;");
				ResultSet rs = stmt.executeQuery();
				labn = new Vector<String>();
				while(rs.next())
				{
					labn.add(rs.getString(1).toString());
				}
				for(int i=0;i<labn.size();i++)
				{
					System.out.println(labn.get(i));
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 840, 469);
		quessele = new JPanel();
		quessele.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(quessele);
		
		JLabel testnamelbl = new JLabel("Test Name :");
		testnamelbl.setBounds(19, 93, 84, 19);
		testnamelbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		testname = new JTextField();
		testname.setBounds(112, 92, 116, 22);
		testname.setColumns(10);
		
		JLabel lblEnterTestDetails = new JLabel("Enter Test Details");
		lblEnterTestDetails.setBounds(231, 13, 311, 49);
		lblEnterTestDetails.setFont(new Font("Tahoma", Font.PLAIN, 40));
		
		JComboBox lab = new JComboBox();
		lab.setBounds(628, 92, 124, 22);
		lab.setModel(new DefaultComboBoxModel(labn));
		
		JLabel lblSelectLab = new JLabel("Select Lab :");
		lblSelectLab.setBounds(540, 93, 76, 19);
		lblSelectLab.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblTestKey = new JLabel("Test Key :");
		lblTestKey.setBounds(284, 126, 70, 19);
		lblTestKey.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		key2 = new JTextField();
		key2.setBounds(377, 125, 116, 22);
		key2.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Question", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(11, 178, 793, 181);
		quessele.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 18, 781, 156);
		panel.add(scrollPane);
		
		JTextArea question = new JTextArea();
		question.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane.setViewportView(question);
		key2.setText("");
		
		JButton submit = new JButton("Add Question");
		submit.setBounds(188, 372, 124, 25);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String name = loginname;
			String testnam = testname.getText().toString();
			String questionno = "Question " + Integer.toString(count);
			String questionval = question.getText().toString();
			
		
			
			
			count++;
			if(testnam.isEmpty() || questionval.isEmpty())
			{
				if(testnam.isEmpty())
				JOptionPane.showMessageDialog(quessele, "Test name cannot be empty");
				
				else
				{
					JOptionPane.showMessageDialog(quessele, "Question cannot be empty");
				}
			}
			else
			{
				boolean connect = db.connect();
				if(connect)
				{
					Connection con = db.getconnection();
					try {
						PreparedStatement stmt = con.prepareStatement("Insert into testdata(username,batch,lab,testname,questionno,question,keyval,activel) values(?,?,?,?,?,?,?,?);");
						stmt.setString(1, loginname);
						stmt.setString(2, batch);
						stmt.setString(3, lab.getSelectedItem().toString());
						stmt.setString(4, testname.getText().toString());
						stmt.setString(5, questionno);
						stmt.setString(6, questionval);
						stmt.setString(7, key2.getText().toString());
						stmt.setBoolean(8, false);
						stmt.execute();
						JOptionPane.showMessageDialog(quessele,"Question Submitted");
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
			
			}
			
		});
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exit.setBounds(540, 372, 64, 25);
		quessele.setLayout(null);
		
		
		quessele.add(lblEnterTestDetails);
		quessele.add(testnamelbl);
		quessele.add(testname);
		quessele.add(lblSelectLab);
		quessele.add(lab);
		quessele.add(submit);
		quessele.add(exit);
		quessele.add(lblTestKey);
		quessele.add(key2);
	}
}
