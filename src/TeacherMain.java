

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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

public class TeacherMain extends JFrame {

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
					TeacherMain frame = new TeacherMain(new TableModel() {
						
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
					},"");
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
	public TeacherMain(TableModel tb,String batch) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
						System.out.println(batch);
						PreparedStatement preparedStatement  = con.prepareStatement("Select username,year,batch from registeration where batch=? AND flag=?;");
						preparedStatement.setString(1,batch);
						preparedStatement.setBoolean(2, true);
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
					PreparedStatement stmt= con.prepareStatement("Select * from submitdata where username = ?;");
					String nam = table.getModel().getValueAt(currentrow, 0).toString();
					System.out.println(nam);
					stmt.setString(1,nam);
					ResultSet rs = stmt.executeQuery();
					//String name = rs.getString(0);
					if(!rs.next())
					{
						JOptionPane.showMessageDialog(contentPane, "No Submissions Yet");
					}
					
					
					else{
						ResultSet rs2 = stmt.executeQuery();
					TableModel tb = DbUtils.resultSetToTableModel(rs2);
					submissions s = new submissions(tb);
					s.setVisible(true);
					}
					
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
		btnSubmissions.setVisible(false);
	table.addMouseListener(new MouseAdapter() {
		 @Override
		    public void mousePressed(MouseEvent event) {
		        // selects the row at which point the mouse is clicked
		        Point point = event.getPoint();
		        currentrow = table.rowAtPoint(point);
		        table.setRowSelectionInterval(currentrow, currentrow);
		     btnSubmissions.setVisible(true);
		  
		    }
	});
		
	}
}
