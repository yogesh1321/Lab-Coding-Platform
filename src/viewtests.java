

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class viewtests extends JFrame {

	private JPanel contentPane;
	private JTable table;
	int currentrow;

	/**
	 * Launch the application.
	 */
	Database db = new Database();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewtests frame = new viewtests(new TableModel() {
						
						@Override
						public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void removeTableModelListener(TableModelListener l) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public boolean isCellEditable(int rowIndex, int columnIndex) {
							// TODO Auto-generated method stub
							return false;
						}
						
						@Override
						public Object getValueAt(int rowIndex, int columnIndex) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public int getRowCount() {
							// TODO Auto-generated method stub
							return 0;
						}
						
						@Override
						public String getColumnName(int columnIndex) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public int getColumnCount() {
							// TODO Auto-generated method stub
							return 0;
						}
						
						@Override
						public Class<?> getColumnClass(int columnIndex) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public void addTableModelListener(TableModelListener l) {
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
	public viewtests(TableModel tb,String batch,String loginname) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 585, 577);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(49, 53, 286, 429);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		table.setModel(tb);
		
		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean dbconnect =db.connect();
				if(dbconnect)
				{
					Connection con = db.getconnection();
					try {
						//Teacher t = new Teacher();
						PreparedStatement preparedStatement  = con.prepareStatement("Select username,batch,lab,testname from testdata where batch=?;");
						preparedStatement.setString(1,batch);
						ResultSet rs = preparedStatement.executeQuery(); 
						table.setModel(DbUtils.resultSetToTableModel(rs));
						
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
			}
		});
		refresh.setBounds(423, 63, 97, 25);
		contentPane.add(refresh);
		
		JPopupMenu menu = new JPopupMenu();
		JMenuItem submission = new JMenuItem("Submissions");
		menu.add(submission);
		table.setComponentPopupMenu(menu);
		
		JButton btnSubmissions = new JButton("Submissions");
		btnSubmissions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean conn = db.connect();
				Connection con = db.getconnection();
				try {
					PreparedStatement stmt= con.prepareStatement("Select * from testsubmitdata");
					String nam = table.getModel().getValueAt(currentrow, 0).toString();
					System.out.println(nam);
					
					ResultSet rs = stmt.executeQuery();
					TableModel tb = DbUtils.resultSetToTableModel(rs);
					testsubmissions s = new testsubmissions(tb,loginname,batch);
					s.setVisible(true);
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSubmissions.setBounds(412, 112, 115, 25);
		contentPane.add(btnSubmissions);
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		exit.setBounds(423, 177, 97, 25);
		contentPane.add(exit);
		
		JButton active = new JButton("Active");
		active.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Boolean connect = db.connect();
				Connection con = db.getconnection();
				try {
					PreparedStatement stmt = con.prepareStatement("Update testdata set activel = ? where username = ? and batch = ? and lab = ? and testname = ?;");
					stmt.setBoolean(1, true);
					stmt.setString(2, table.getModel().getValueAt(currentrow, 0).toString());
					stmt.setString(3, table.getModel().getValueAt(currentrow, 1).toString());
					stmt.setString(4, table.getModel().getValueAt(currentrow, 2).toString());
					stmt.setString(5, table.getModel().getValueAt(currentrow, 3).toString());
					stmt.execute();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
			}
		});
		active.setBounds(423, 234, 97, 25);
		contentPane.add(active);
		
		JButton disable = new JButton("Disable");
		disable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Boolean connect = db.connect();
				Connection con = db.getconnection();
				try {
					PreparedStatement stmt = con.prepareStatement("Update testdata set activel = ? where username = ? and batch = ? and lab = ? and testname = ?;");
					stmt.setBoolean(1, false);
					stmt.setString(2, table.getModel().getValueAt(currentrow, 0).toString());
					stmt.setString(3, table.getModel().getValueAt(currentrow, 1).toString());
					stmt.setString(4, table.getModel().getValueAt(currentrow, 2).toString());
					stmt.setString(5, table.getModel().getValueAt(currentrow, 3).toString());
					stmt.execute();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		disable.setBounds(423, 282, 97, 25);
		contentPane.add(disable);
		btnSubmissions.setVisible(false);
		active.setVisible(false);
		disable.setVisible(false);
	table.addMouseListener(new MouseAdapter() {
		 @Override
		    public void mousePressed(MouseEvent event) {
		        // selects the row at which point the mouse is clicked
		        Point point = event.getPoint();
		        currentrow = table.rowAtPoint(point);
		        table.setRowSelectionInterval(currentrow, currentrow);
		     btnSubmissions.setVisible(true);
		     active.setVisible(true);
		     disable.setVisible(true);
		    }
	});
		
	}
}
