

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.xml.crypto.Data;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class testsubmissions extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Database db = new Database();
	String filename;
	String dire;
	String ques;
	String code;
	String question;
	int currentrow;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testsubmissions frame = new testsubmissions(new TableModel() {
						
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
	public testsubmissions(TableModel tb,String loginname,String batch) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 663, 617);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		JButton view = new JButton("View Code");
		view.setVisible(false);
		view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			String nam = table.getModel().getValueAt(currentrow, 0).toString();
			String fname = loginname;
			String labname = table.getModel().getValueAt(currentrow, 1).toString();
			String testname = table.getModel().getValueAt(currentrow, 2).toString();
			String quesn = table.getModel().getValueAt(currentrow, 3).toString();
			filename = nam + "$" + fname +"$"+labname+ "$" + testname + "$"+quesn;
			dire = "H:\\"+filename+".c";	
			
			String labn2 = table.getModel().getValueAt(currentrow, 1).toString();
			
			
			boolean conn = db.connect();
			if(conn)
			{
				Connection con = db.getconnection();
			try{
				PreparedStatement stmt = con.prepareStatement("Select question from testdata where username = ? AND batch = ? AND lab=? and "
						+ "testname=? and questionno=?;");
				stmt.setString(1, fname);
				stmt.setString(2, batch);
				stmt.setString(3, labname);
				stmt.setString(4, testname);
				stmt.setString(5, quesn);
		System.out.println(fname + " " +batch+ " " +labname + " " + testname + " " + quesn);
				
				ResultSet rs = stmt.executeQuery();
				while(rs.next())
				{
				question = rs.getString(1).toString();	
				}
				
			System.out.println(question);
				
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
			File file = new File(dire);
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = "",content="";
				 while ((line = reader.readLine()) != null) {
			            content += line + System.lineSeparator();
			        }
			        reader.close();				
			      //  System.out.println(question);
			        fourth s = new fourth(nam, content, question, dire,labname,testname,quesn);
			        s.setVisible(true);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Submissions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 18, 429, 415);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(tb);
		
		table.addMouseListener(new MouseAdapter() {
			  public void mousePressed(MouseEvent event) {
			        // selects the row at which point the mouse is clicked
			        Point point = event.getPoint();
			        currentrow = table.rowAtPoint(point);
			        table.setRowSelectionInterval(currentrow, currentrow);
			        view.setVisible(true);
			  
			    }
		});
		
		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean connection = db.connect();
				Connection con = db.getconnection();
				try {
					PreparedStatement stmt = con.prepareStatement("Select * from testsubmitdata;");
					String nam = table.getModel().getValueAt(currentrow, 0).toString();
					System.out.println(nam);
					
					ResultSet rs = stmt.executeQuery();
					TableModel tb = DbUtils.resultSetToTableModel(rs);
					table.setModel(tb);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(55)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 441, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addComponent(view))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addComponent(refresh))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(249)
					.addComponent(exit))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(82)
							.addComponent(refresh)
							.addGap(66)
							.addComponent(view)))
					.addGap(34)
					.addComponent(exit))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
