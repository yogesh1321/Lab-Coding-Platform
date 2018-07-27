

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

public class third extends JFrame {

	private JPanel contentPane;
	JTextArea area;
	JTextArea textArea;
	Database db = new Database();
	Vector<String> labn = new Vector<String>();
	Vector<String> labs2 = new Vector<String>();
	String filena ;
	String dir;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					third frame = new third("Qwer","q","qw","qw","qw",new DefaultComboBoxModel());
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
	public third(String loginname,String fname,String batch,String lab,String testname,DefaultComboBoxModel quemo) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 804, 956);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		btnExit.setBounds(235, 886, 76, 25);
		contentPane.add(btnExit);
	
		
	
		
		JLabel lblNewLabel = new JLabel("Name :");
		lblNewLabel.setBounds(561, 245, 56, 16);
		contentPane.add(lblNewLabel);
		
		JLabel setname = new JLabel("New label");
		setname.setBounds(608, 245, 56, 16);
		contentPane.add(setname);
		setname.setText(loginname);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Compilation Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(38, 745, 474, 135);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 18, 462, 110);
		panel.add(scrollPane);
		
		area = new JTextArea();
		scrollPane.setViewportView(area);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Enter Code", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(37, 166, 479, 572);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 18, 467, 547);
		panel_1.add(scrollPane_1);
		
		 textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		scrollPane_1.setViewportView(textArea);
		textArea.setTransferHandler(null);
		

		JComboBox question = new JComboBox();
		question.setBounds(561, 336, 117, 22);
		contentPane.add(question);
		
		question.setModel(quemo);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Question", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(36, -5, 482, 165);
		contentPane.add(panel_2);
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
					PreparedStatement stmt = con.prepareStatement("Select question from testdata where username = ? AND batch = ? and "
							+ "lab = ? and testname = ? and questionno = ?;");
					stmt.setString(1, fname);
					stmt.setString(2, batch);
					stmt.setString(3, lab);
					stmt.setString(4, testname);
					stmt.setString(5, question.getSelectedItem().toString());
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
				
				filena = setname.getText()+"$"+fname+"$"+lab+"$"+testname+"$"+ question.getSelectedItem().toString();
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
			        if (fileChooser.showSaveDialog(third.this) != JFileChooser.APPROVE_OPTION)
			                    return;
			                File file = fileChooser.getSelectedFile();*/
				//String filename = "H:\\third.c";
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
			                   // File file = new File("H:\\third.c");
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
		btnCompile.setBounds(561, 467, 97, 25);
		contentPane.add(btnCompile);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				System.out.println("Inside Run");
				//File file = new File("H:\\third.c");
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
                        

                        JOptionPane.showMessageDialog(third.this, "Compilation Error", "Error", JOptionPane.ERROR_MESSAGE);

                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});

		btnRun.setBounds(561, 523, 97, 25);
		contentPane.add(btnRun);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean dbconnect = db.connect();
				Connection con = db.getconnection();
				try {
					PreparedStatement stmt = con.prepareStatement("Insert into testsubmitdata(username,labname,testname,questionno) values(?,?,?,?);");
					stmt.setString(1, setname.getText().toString());
					stmt.setString(2, lab);
					stmt.setString(3, testname);
					stmt.setString(4, question.getSelectedItem().toString());
					stmt.execute();
					JOptionPane.showMessageDialog(contentPane, "Submitted Succesfully");
					
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
		submit.setBounds(561, 577, 97, 25);
		contentPane.add(submit);
		
		
	
	}
}
