import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JLabel;

import net.proteanit.sql.DbUtils;


public class stutestmode extends JFrame {

	private JPanel contentPane;
	private JTable table;
	int currentrow;
	Database db = new Database();
	Vector<String> quelist = new Vector<String>();
	private JTextField key;
	String check;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					stutestmode frame = new stutestmode(new TableModel() {
						
						@Override
						public void setValueAt(Object arg0, int arg1, int arg2) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void removeTableModelListener(TableModelListener arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public boolean isCellEditable(int arg0, int arg1) {
							// TODO Auto-generated method stub
							return false;
						}
						
						@Override
						public Object getValueAt(int arg0, int arg1) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public int getRowCount() {
							// TODO Auto-generated method stub
							return 0;
						}
						
						@Override
						public String getColumnName(int arg0) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public int getColumnCount() {
							// TODO Auto-generated method stub
							return 0;
						}
						
						@Override
						public Class<?> getColumnClass(int arg0) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public void addTableModelListener(TableModelListener arg0) {
							// TODO Auto-generated method stub
							
						}
					},"","");
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
	public stutestmode(TableModel tb,String username,String batch) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 635);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Tests List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(37, 20, 395, 519);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 18, 383, 494);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		table.setModel(tb);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//batch = batc.getSelectedItem().toString();
				boolean dbconnect =db.connect();
				if(dbconnect)
				{
					Connection con = db.getconnection();
					try {
						PreparedStatement preparedStatement  = con.prepareStatement("Select username,batch,lab,testname from testdata where batch=? and activel=?;");
						preparedStatement.setString(1, batch);
						preparedStatement.setBoolean(2, true);
						ResultSet rs = preparedStatement.executeQuery(); 
						TableModel tb= DbUtils.resultSetToTableModel(rs);	
					table.setModel(tb);
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		btnRefresh.setBounds(502, 113, 97, 25);
		contentPane.add(btnRefresh);
		
		table.addMouseListener(new MouseAdapter() {
			
			 public void mousePressed(MouseEvent event) {
			        // selects the row at which point the mouse is clicked
			        Point point = event.getPoint();
			        currentrow = table.rowAtPoint(point);
			        table.setRowSelectionInterval(currentrow, currentrow);
			       // view.setVisible(true);
			  
			    }
		});
		
		key = new JTextField();
		key.setBounds(513, 250, 86, 22);
		contentPane.add(key);
		key.setColumns(10);
		
		JLabel lblEnterKey = new JLabel("Enter Key :");
		lblEnterKey.setBounds(444, 253, 82, 16);
		contentPane.add(lblEnterKey);
		
		JButton submitkey = new JButton("Submit");
		submitkey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean connect = db.connect();
				if(connect)
				{
					String testnam = table.getModel().getValueAt(currentrow,3).toString();
					String fname = table.getModel().getValueAt(currentrow, 0).toString();
					String batc = table.getModel().getValueAt(currentrow, 1).toString();
					String lab = table.getModel().getValueAt(currentrow, 2).toString();
					Connection con = db.getconnection();
					try {
						PreparedStatement stmt = con.prepareStatement("Select keyval from testdata where username = ? AND batch = ? and lab = ? and testname = ?;");
						stmt.setString(1, fname);
						stmt.setString(2, batc);
						stmt.setString(3, lab);
						stmt.setString(4, testnam);
						ResultSet rs = stmt.executeQuery();
						
						while(rs.next())
						{
							check = rs.getString(1).toString();
						}
						System.out.println(check);
						System.out.println(key.getText().toString());
						if(check.equals(key.getText().toString()))
						{
							boolean connect2 = db.connect();
							if(connect2)
							{
								String testnam2 = table.getModel().getValueAt(currentrow,3).toString();
								String fname2 = table.getModel().getValueAt(currentrow, 0).toString();
								String batc2 = table.getModel().getValueAt(currentrow, 1).toString();
								String lab2 = table.getModel().getValueAt(currentrow, 2).toString();
								System.out.println(testnam2);
								System.out.println(fname2);
								System.out.println(batc2);
								System.out.println(lab2);
								
								Connection conn = db.getconnection();
								try {
									PreparedStatement stmt2 = conn.prepareStatement("Select distinct questionno from testdata where username = ? and batch = ? and lab = ? and testname = ?;");
									stmt2.setString(1, fname2);
									stmt2.setString(2, batc2);
									stmt2.setString(3, lab2);
									stmt2.setString(4, testnam2);
									ResultSet rs2 = stmt2.executeQuery();
									while(rs2.next())
									{
										quelist.add(rs2.getString(1).toString());
									}
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							
							
							
							DefaultComboBoxModel quemo = new DefaultComboBoxModel(quelist);
							
							
							third t = new third(username,fname,batc,lab,testnam,quemo);
							t.setVisible(true);
							dispose();
							
						}
						else
						{
							JOptionPane.showMessageDialog(contentPane, "Wrong Key");
							
						}
					
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				
				
				
				
				
				
				
			}
		});
		submitkey.setBounds(467, 285, 97, 25);
		contentPane.add(submitkey);
		
		submitkey.setVisible(false);
		key.setVisible(false);
		lblEnterKey.setVisible(false);
		
		JButton btnSelectTest = new JButton("Select Test");
		btnSelectTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				submitkey.setVisible(true);
				key.setVisible(true);
				lblEnterKey.setVisible(true);
				
			
			
				
				
				
				
				
				
				
			/*	
				String testnam = table.getModel().getValueAt(currentrow,3).toString();
				String fname = table.getModel().getValueAt(currentrow, 0).toString();
				String batc = table.getModel().getValueAt(currentrow, 1).toString();
				String lab = table.getModel().getValueAt(currentrow, 2).toString();
				
				boolean connect = db.connect();
				if(connect)
				{
					Connection con = db.getconnection();
					try {
						PreparedStatement stmt = con.prepareStatement("Select distinct questionno from testdata where username = ? and 	batch = ? and lab = ? and testname = ?;");
						stmt.setString(1, fname);
						stmt.setString(2, batc);
						stmt.setString(3, lab);
						stmt.setString(4, testnam);
						ResultSet rs = stmt.executeQuery();
						while(rs.next())
						{
							quelist.add(rs.getString(1).toString());
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
				
				
				DefaultComboBoxModel quemo = new DefaultComboBoxModel(quelist);
				
				
				third t = new third(username,fname,batc,lab,testnam,quemo);
				t.setVisible(true);
				dispose();*/
			}
		});
		btnSelectTest.setBounds(502, 170, 97, 25);
		contentPane.add(btnSelectTest);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean dbconnect = db.connect();
				if(dbconnect)
				{
					Connection con = db.getconnection();
					try {
						PreparedStatement preSta2 = con.prepareStatement("update registeration SET flag = false where username = ?;");
							preSta2.setString(1, username);
							preSta2.executeUpdate();
							dispose();
						}
					 catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				dispose();
			}
		});
		btnExit.setBounds(482, 393, 97, 25);
		contentPane.add(btnExit);
		
		
	}
}
