

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class first extends JFrame {

	private JPanel contentPane;
	JTextArea area;
	JTextArea textArea;
	Database db = new Database();
	Vector<String> labn = new Vector<String>();
	Vector<String> labs2 = new Vector<String>();
	String filena ;
	String dir;
	private JLabel lblSelectLab;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					first frame = new first("Qwer");
					/*frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
					frame.setUndecorated(true);
					frame.setVisible(true);*/
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
	public first(String loginname) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 804, 956);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
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

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean dbconnect = db.connect();
				if(dbconnect)
				{
					Connection con = db.getconnection();
					try {
						PreparedStatement preSta2 = con.prepareStatement("update registeration SET flag = false where username = ?;");
							preSta2.setString(1, loginname);
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
	
		
	
		
		JLabel lblNewLabel = new JLabel("Name :");
		lblNewLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));
		
		JLabel setname = new JLabel("New label");
		setname.setFont(new Font("Monospaced", Font.PLAIN, 13));
		setname.setText(loginname);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Compilation Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 18, 462, 110);
		panel.add(scrollPane);
		
		area = new JTextArea();
		area.setFont(new Font("Monospaced", Font.PLAIN, 13));
		scrollPane.setViewportView(area);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Enter code", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 18, 467, 547);
		panel_1.add(scrollPane_1);
		
		 textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.BOLD, 18));
		scrollPane_1.setViewportView(textArea);
			textArea.setTransferHandler(null);
		
		JComboBox labsheet = new JComboBox();
		JComboBox lab = new JComboBox();
		
		lab.setModel(new DefaultComboBoxModel(labn));
		lab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vector<String> labs = new Vector<String>();
			
				Connection con  = db.getconnection();
				try {
					
					PreparedStatement stmt = con.prepareStatement("Select distinct labsheet from labdata where labname = ?;");
					stmt.setString(1, lab.getSelectedItem().toString());
					ResultSet rs = stmt.executeQuery();
					while(rs.next())
					{
						labs.add(rs.getString(1).toString());
						labs2.add(rs.getString(1).toString());
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch bloc
					e.printStackTrace();
				}
				labsheet.setModel(new DefaultComboBoxModel(labs));
			}
		});
		
		

		JComboBox question = new JComboBox();
		
		labsheet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Vector<String> quesno = new Vector<String>();
				Connection con = db.getconnection();
				try {
					PreparedStatement stmt = con.prepareStatement("Select questionno from labdata where labname = ? AND labsheet = ?;");
					stmt.setString(1, lab.getSelectedItem().toString());
					stmt.setString(2, labsheet.getSelectedItem().toString());
					ResultSet rs = stmt.executeQuery();
					while(rs.next())
					{
						quesno.add(rs.getString(1).toString());
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				question.setModel(new DefaultComboBoxModel(quesno));
			}
		});
		
		
	
	
		
		lblSelectLab = new JLabel("Select Lab");
		lblSelectLab.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Question", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(6, 18, 470, 140);
		panel_2.add(scrollPane_2);
		
		JTextArea que = new JTextArea();
		que.setFont(new Font("Monospaced", Font.BOLD, 16));
		scrollPane_2.setViewportView(que);
		que.setVisible(false);
		question.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String quest = new String();
				// TODO Auto-generated method stub
				Connection con = db.getconnection();
				try {
					PreparedStatement stmt = con.prepareStatement("Select question from labdata where labname = ? AND labsheet = ? and "
							+ "questionno = ?;");
					stmt.setString(1, lab.getSelectedItem().toString());
					stmt.setString(2, labsheet.getSelectedItem().toString());
					stmt.setString(3, question.getSelectedItem().toString());
					ResultSet rs = stmt.executeQuery();
					while(rs.next())
					{
						quest = rs.getString(1);
					}
					que.setText(quest);
					que.setVisible(true);	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				filena = setname.getText()+"$"+lab.getSelectedItem().toString()+"$" + labsheet.getSelectedItem().toString()+"$"+ question.getSelectedItem().toString();
				System.out.println(filena);
				dir = "H:\\"+filena+".c";	
			}
		});

System.out.println(dir);		
		JButton btnCompile = new JButton("Compile");
		btnCompile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				/* JFileChooser fileChooser=new JFileChooser();
			        if (fileChooser.showSaveDialog(first.this) != JFileChooser.APPROVE_OPTION)
			                    return;
			                File file = fileChooser.getSelectedFile();*/
				//String filename = "H:\\first.c";
				String filename = dir;
				int flag = 0;
				BufferedWriter bw = null;
				FileWriter fw = null;

				try {

					String content = "This is the content to write into file\n";

					fw = new FileWriter(filename);
					bw = new BufferedWriter(fw);
					bw.write(content);

					System.out.println("Done");

				} catch (IOException e2) {

					e2.printStackTrace();

				} finally {

					try {

						if (bw != null)
							bw.close();

						if (fw != null)
							fw.close();

					} catch (IOException ex) {

						ex.printStackTrace();

					}

				}
			
			System.out.println(flag);
			                   // File file = new File("H:\\first.c");
			 File file = new File(dir);
			                try {
			                    FileWriter out = new FileWriter(file);
			                    textArea.write(out);
			                    out.close();

			                } catch (IOException e1) {
			                    e1.printStackTrace();
			                }

			                String filepath = file.getPath();
			                System.out.println(filepath);
			                String filepath2 = filepath.substring(0, filepath.lastIndexOf(File.separator));
			             
			                System.out.println(filepath2);
			                String name = file.getName();


			                String name2 = file.getName().substring(0, file.getName().lastIndexOf("."));
			                 String folder = filepath2+"\\";
				        String exe = folder+name2+".exe";
			                 System.out.println(exe);
			          
			                ProcessBuilder pb=new ProcessBuilder();
			                  try {
			                   
			                      pb = new ProcessBuilder("cmd", "/C", "gcc " + "\"" + filepath2 + "\\" + name + "\"" + " -o \"" + name2+"\"");
			                    
			                   
			                      pb = new ProcessBuilder("cmd", "/C", "g++ " + "\"" + filepath2 + "\\" + name + "\"" + " -o \"" + name2+"\"");

			                    
			                    pb.directory(new File(filepath2));
			                    Process p = pb.start();
			                    p.waitFor();
			                    int x = p.exitValue();

			                    if (x == 0) {
			                       
			                        area.setForeground(Color.red);
			                        area.setText("            == 0 error.. Compilation Finished");
			                    } else {

			                        BufferedReader r = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			                        //BufferedWriter rm=new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));


			                        String out;
			                        area.setText("");


			                        while ((out = r.readLine()) != null)


			                        {


			                            area.setForeground(Color.RED);
			                            area.append(out + System.getProperty("line.separator"));
			                        }


			                    }


			                } catch (Exception ex) {
			                    ex.printStackTrace();
			                }
			}
		});
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				System.out.println("Inside Run");
				//File file = new File("H:\\first.c");
				 File file = new File(dir);
				String filepath = file.getPath();
                String filepath2 = filepath.substring(0, filepath.lastIndexOf(File.separator));
                System.out.println(filepath);
                System.out.println(filepath2);
                String name = file.getName();
                String name2 = file.getName().substring(0, file.getName().lastIndexOf("."));
                 String folder = filepath2+"\\";
	        String exe = folder+name2+".exe";
                 System.out.println(exe);
          
                ProcessBuilder pb=new ProcessBuilder();
                  try {
                    
                      pb = new ProcessBuilder("cmd", "/C", "gcc " + "\"" + filepath2 + "\\" + name + "\"" + " -o \"" + name2+"\"");
                    
                   
                      pb = new ProcessBuilder("cmd", "/C", "g++ " + "\"" + filepath2 + "\\" + name + "\"" + " -o \"" + name2+"\"");

                    
                    pb.directory(new File(filepath2));
                    Process p = pb.start();
                    p.waitFor();
                    int x = p.exitValue();
                    int z=p.exitValue();
                 
                    if (x == 0) {
                        
                        Runtime rt = Runtime.getRuntime();
                        try {
                            String username = System.getProperty("user.name");
                            String c = "@echo off\n" + "\"" +
                                    filepath2 + "\\" + name2 + ".exe\"\n" + "echo.\n" + "echo.\n" + "echo Process Terminated\n" +
                                    "pause\n" +
                                    "exit";


                            File dir = new File("C:\\Users\\" + username + "\\CodeEditor");
                            dir.mkdir();

                            try {
                                File file2 = new File("C:\\Users\\" + username + "\\CodeEditor" + "\\run.bat");
                                file2.createNewFile();
                                PrintWriter writer = new PrintWriter(file2);
                                writer.println(c);
                                writer.close();


                                Process p2 = Runtime.getRuntime().exec("cmd /c start run.bat", null, new File("C:\\Users\\" + username + "\\CodeEditor"));
                            } catch (Exception ex) {

                            }


                        } catch (Exception ex) {

                        }

                    } else {
                        

                        JOptionPane.showMessageDialog(first.this, "Compilation Error", "Error", JOptionPane.ERROR_MESSAGE);

                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean dbconnect = db.connect();
				Connection con = db.getconnection();
				try {
					PreparedStatement stmt = con.prepareStatement("select * from submitdata where username = ? and labname = ? and labsheet = ? and questionno = ?;");
					stmt.setString(1, setname.getText().toString());
					stmt.setString(2, lab.getSelectedItem().toString());
					stmt.setString(3, labs2.get(labsheet.getSelectedIndex()));
					stmt.setString(4, question.getSelectedItem().toString());
					ResultSet rs = stmt.executeQuery();
					if(rs.next() == true)
					{
					}
					else
					{
					PreparedStatement stmt2 = con.prepareStatement("Insert into submitdata(username,labname,labsheet,questionno) values(?,?,?,?);");
					stmt2.setString(1, setname.getText().toString());
					stmt2.setString(2, lab.getSelectedItem().toString());
					stmt2.setString(3, labs2.get(labsheet.getSelectedIndex()));
					stmt2.setString(4, question.getSelectedItem().toString());
					stmt2.execute();
				
					}
					JOptionPane.showMessageDialog(contentPane, "Submitted Succesfully");
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(31)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
					.addGap(263))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(32)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
					.addGap(45)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(47)
							.addComponent(setname, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(lblSelectLab, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
						.addComponent(lab, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(labsheet, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(question, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCompile, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
						.addComponent(submit, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
					.addGap(103))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(33)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 474, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(230)
					.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(79)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(setname)
								.addComponent(lblNewLabel))
							.addGap(13)
							.addComponent(lblSelectLab, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
							.addGap(19)
							.addComponent(lab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(31)
							.addComponent(labsheet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(28)
							.addComponent(question, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(33)
							.addComponent(btnCompile)
							.addGap(31)
							.addComponent(btnRun)
							.addGap(29)
							.addComponent(submit)))
					.addGap(7)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(btnExit))
		);
		contentPane.setLayout(gl_contentPane);
		
		
	
	}
}
