

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
import javax.swing.JTextField;

public class fourth extends JFrame {

	private JPanel contentPane;
	JTextArea area;
	JTextArea textArea;
	Database db = new Database();
	Vector<String> labn = new Vector<String>();
	Vector<String> labs2 = new Vector<String>();
	String filena ;
	String dir;
	private JTextField marks;
	private JTextField totalmarks;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fourth frame = new fourth("Qwer","qe","qe","qe","qw","qw","qw");
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
	public fourth(String loginname,String code,String question,String dir,String labname,String testname,String quesn) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
				
				dispose();
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
		
		textArea.setText(code);
		
		System.out.println(question);
		
System.out.println(dir);		
		JButton btnCompile = new JButton("Compile");
		btnCompile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				/* JFileChooser fileChooser=new JFileChooser();
			        if (fileChooser.showSaveDialog(fourth.this) != JFileChooser.APPROVE_OPTION)
			                    return;
			                File file = fileChooser.getSelectedFile();*/
				//String filename = "H:\\fourth.c";
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
			                   // File file = new File("H:\\fourth.c");
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
		btnCompile.setBounds(561, 296, 97, 25);
		contentPane.add(btnCompile);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				System.out.println("Inside Run");
				//File file = new File("H:\\fourth.c");
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
                        

                        JOptionPane.showMessageDialog(fourth.this, "Compilation Error", "Error", JOptionPane.ERROR_MESSAGE);

                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});

		btnRun.setBounds(561, 334, 97, 25);
		contentPane.add(btnRun);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Question", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(32, 8, 491, 152);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(6, 18, 479, 127);
		panel_2.add(scrollPane_2);
		
		JTextArea que = new JTextArea();
		que.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 16));
		scrollPane_2.setViewportView(que);
		que.setText(question);
		
		JLabel lblEnterMarks = new JLabel("Enter Marks :");
		lblEnterMarks.setBounds(548, 432, 82, 16);
		contentPane.add(lblEnterMarks);
		
		marks = new JTextField();
		marks.setBounds(635, 429, 116, 22);
		contentPane.add(marks);
		marks.setColumns(10);
		
		JLabel lblTotalMarks = new JLabel("Total Marks :");
		lblTotalMarks.setBounds(548, 466, 82, 16);
		contentPane.add(lblTotalMarks);
		
		totalmarks = new JTextField();
		totalmarks.setBounds(635, 466, 116, 22);
		contentPane.add(totalmarks);
		totalmarks.setColumns(10);
		
		JButton submitmarks = new JButton("Submit Marks");
		submitmarks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean connect = db.connect();
				if(connect)
				{
					Connection con = db.getconnection();
					try {
						PreparedStatement stmt = con.prepareStatement("update testsubmitdata SET marks=?,totalmarks=? where username = ? AND labname = ? AND testname = ? AND questionno = ?;");
						stmt.setString(1, marks.getText().toString());
						stmt.setString(2, totalmarks.getText().toString());
						stmt.setString(3, loginname);
						stmt.setString(4, labname);
						stmt.setString(5, testname);
						stmt.setString(6, quesn);
						boolean ch = stmt.execute();
					
						if(ch)
						{
							JOptionPane.showMessageDialog(contentPane,"Marks Submitted Successfully");
						}
					
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		submitmarks.setBounds(608, 541, 111, 25);
		contentPane.add(submitmarks);
		
	
	}
}
