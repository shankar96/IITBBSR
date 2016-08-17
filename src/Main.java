import java.awt.EventQueue;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class Main {

	static JFrame frame;
	
	public void clock()
	{
		//System.out.println("clock called");
		Thread clock=new Thread()
		{
			public void run()
			{
				try
				{
					while(true){
					Calendar cal =new GregorianCalendar();
					String day=""+cal.get(Calendar.DAY_OF_MONTH);
					String month=""+cal.get(Calendar.MONTH);
					String year=""+cal.get(Calendar.YEAR);
					//System.out.println(month);
					
					String second=""+cal.get(Calendar.SECOND);
					String minute=""+cal.get(Calendar.MINUTE);
					String hour=""+cal.get(Calendar.HOUR);
					
					if(ten(day))
						day=0+day;
					if(ten(month))
						month=0+month;
					if(ten(second))
						second=0+second;
					if(ten(minute))
						minute=0+minute;
					if(ten(hour))
						hour=0+hour;
					lbltime_.setText(hour+":"+minute+":"+second);
					lbldate_.setText(day+"/"+month+"/"+year);
					sleep(1000);}
				}
				catch(Exception e)
				{
					System.out.println("eeror in clock "+e);
				}
			}

			private boolean ten(String check) {
				if(Integer.parseInt(check)<10)
				{
					return true;
				}
				return false;
			}
			
		};
		clock.start();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
					window.frame.setTitle("Library Database");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	Connection connection=null;
	private final JLabel lblLibraryDatabase = new JLabel("Library Database");
	private JTextField txtm;
	private JPasswordField mpasswordField;
	private JTextField txtl;
	private JPasswordField lpasswordField;
	private JTextField lmsg;
	private JTextField mmsg;
	private	JLabel lbltime_;
	private JLabel lbldate_;
	
	public Main() {
		initialize();
		connection=LibDBConnector.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 100, 508, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//clock();
		
		Image ok = new ImageIcon(this.getClass().getResource("/ok.png")).getImage();
		Image ok1 = ok.getScaledInstance( 20, 25,  java.awt.Image.SCALE_SMOOTH ) ;
		
		Image nok = new ImageIcon(this.getClass().getResource("/nok.png")).getImage();
		Image nok1 = nok.getScaledInstance( 20, 25,  java.awt.Image.SCALE_SMOOTH ) ;
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 54, 492, 329);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.control);
		tabbedPane.addTab("Member", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblMemberid = new JLabel("Member_id");
		lblMemberid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMemberid.setBounds(184, 27, 86, 22);
		panel_2.add(lblMemberid);
		
		txtm = new JTextField();
		txtm.setBackground(SystemColor.control);
		txtm.setBounds(299, 30, 125, 20);
		txtm.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		
		panel_2.add(txtm);
		txtm.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(184, 60, 71, 22);
		panel_2.add(lblPassword);
		
		mpasswordField = new JPasswordField();
		mpasswordField.setBackground(SystemColor.control);
		mpasswordField.setBounds(299, 61, 125, 20);
		mpasswordField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_2.add(mpasswordField);
		
		JLabel lblsuccessm = new JLabel("");
		JLabel lblunsuccessm = new JLabel("");
		
		JButton mlogin = new JButton("Log In");
		mlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String member_id=txtm.getText();
				String password=mpasswordField.getText();
				//System.out.println(librarian_id+" , "+password);
				try {
					Statement stmt=connection.createStatement();
					String sql="select count(*) as count from members where member_id='"+member_id+"' and password= '"+password+"'";
					ResultSet rs=stmt.executeQuery(sql);
					rs.next();
					int i=rs.getInt("count");
					if(i==1)
					{
						lblsuccessm.setVisible(true);
						lblunsuccessm.setVisible(false);
						mmsg.setText("successfully logging in.....");
						//JOptionPane.showMessageDialog(null,"Successfully Logged in");
						frame.dispose();
						stmt.close();
						rs.close();
						connection.close();
						memberHome obj=new memberHome(member_id);
						obj.setVisible(true);
					}
					else if(i<=0)
					{
						lblsuccessm.setVisible(false);
						lblunsuccessm.setVisible(true);
						//JOptionPane.showMessageDialog(null,"your login credential is not correct");
						mmsg.setText("your login credential is not correct");
					}
					else
					{
						lblsuccessm.setVisible(false);
						lblunsuccessm.setVisible(true);
						//JOptionPane.showMessageDialog(null,"Multiple record having same id and pass");
						mmsg.setText("Multiple record having same id and pass");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		mlogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mlogin.setBounds(232, 108, 92, 29);
		panel_2.add(mlogin);
		
		JLabel micon = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/IT_Library_Icon.png")).getImage();
		Image newimg = img.getScaledInstance( 170, 165,  java.awt.Image.SCALE_SMOOTH ) ;
		micon.setIcon(new ImageIcon(newimg));
		micon.setBounds(0, 11, 178, 157);
		panel_2.add(micon);
		
		lblsuccessm.setIcon(new ImageIcon(ok1));
		lblsuccessm.setVisible(false);
		lblsuccessm.setBounds(334, 108, 20, 29);
		panel_2.add(lblsuccessm);
		
		lblunsuccessm.setIcon(new ImageIcon(nok1));
		lblunsuccessm.setVisible(false);
		lblunsuccessm.setBounds(334, 108, 20, 29);
		panel_2.add(lblunsuccessm);
		
		mmsg = new JTextField();
		mmsg.setBorder(null);
		mmsg.setBackground(SystemColor.control);
		mmsg.setBounds(188, 148, 206, 20);
		panel_2.add(mmsg);
		mmsg.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setForeground(new Color(0, 102, 102));
		tabbedPane.addTab("Librarian", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(null);
		panel_3.setLayout(null);
		panel_3.setBounds(0, 0, 487, 301);
		panel_1.add(panel_3);
		
		JLabel lblLibrarianid = new JLabel("Librarian_id");
		lblLibrarianid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLibrarianid.setBounds(184, 27, 86, 22);
		panel_3.add(lblLibrarianid);
		
		txtl = new JTextField();
		txtl.setColumns(10);
		txtl.setBackground(SystemColor.menu);
		txtl.setBounds(299, 30, 125, 20);
		txtl.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_3.add(txtl);
		
		JLabel label_1 = new JLabel("Password");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_1.setBounds(184, 60, 71, 22);
		panel_3.add(label_1);
		
		lpasswordField = new JPasswordField();
		lpasswordField.setBackground(SystemColor.menu);
		lpasswordField.setBounds(299, 61, 125, 20);
		lpasswordField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_3.add(lpasswordField);
		
		JLabel lblsuccessl = new JLabel("");
		JLabel lblunsuccessl = new JLabel("");
		lmsg = new JTextField();
		
		JButton llogin = new JButton("LogIn");
		llogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String librarian_id=txtl.getText();
				String password=lpasswordField.getText();
				//System.out.println(librarian_id+" , "+password);
				try {
					Statement stmt=connection.createStatement();
					String sql="select count(*) as count from librarian where librarian_id='"+librarian_id+"' and password= '"+password+"'";
					ResultSet rs=stmt.executeQuery(sql);
					rs.next();
					int i=rs.getInt("count");
					if(i==1)
					{
						
						lblsuccessl.setVisible(true);
						lblunsuccessl.setVisible(false);
						lmsg.setText("successfully logging in.....");
						
						frame.dispose();
						//Librarian newl= new Librarian();
						String[] arg={"0",librarian_id,password};
						connection.close();
						Librarian.main(arg);
						//newl.setVisible(true);
						//JOptionPane.showMessageDialog(null,"Successfully Logged in");
					}
					else if(i<=0)
					{
						lblsuccessl.setVisible(false);
						lblunsuccessl.setVisible(true);
						//JOptionPane.showMessageDialog(null,"your login credential is not correct");
						lmsg.setText("your login credential is not correct");
					}
					else
					{
						lblsuccessl.setVisible(false);
						lblunsuccessl.setVisible(true);
						//JOptionPane.showMessageDialog(null,"Multiple record having same id and pass");
						lmsg.setText("Multiple record having same id and pass");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		llogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		llogin.setBounds(232, 108, 92, 29);
		panel_3.add(llogin);
		
		JLabel licon = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/IT_Library_Icon.png")).getImage();
		Image newimg2 = img2.getScaledInstance( 170, 165,  java.awt.Image.SCALE_SMOOTH ) ;
		licon.setIcon(new ImageIcon(newimg2));
		licon.setBounds(0, 11, 178, 157);
		panel_3.add(licon);
		
		
		lblsuccessl.setIcon(new ImageIcon(ok1));
		lblsuccessl.setBounds(334, 108, 20, 29);
		lblsuccessl.setVisible(false);
		panel_3.add(lblsuccessl);
		
		lblunsuccessl.setIcon(new ImageIcon(nok1));
		lblunsuccessl.setBounds(334, 108, 20, 29);
		lblunsuccessl.setVisible(false);
		panel_3.add(lblunsuccessl);
		
		
		lmsg.setBackground(SystemColor.control);
		lmsg.setBorder(null);
		lmsg.setBounds(188, 148, 299, 20);
		panel_3.add(lmsg);
		lmsg.setColumns(10);
		Image img5 = new ImageIcon(this.getClass().getResource("/ok.png")).getImage();
		Image newimg5 = img5.getScaledInstance( 20, 25,  java.awt.Image.SCALE_SMOOTH ) ;
		Image img4 = new ImageIcon(this.getClass().getResource("/IT_Library_Icon.png")).getImage();
		Image newimg4 = img4.getScaledInstance( 170, 165,  java.awt.Image.SCALE_SMOOTH ) ;
		lblLibraryDatabase.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblLibraryDatabase.setBounds(156, 11, 155, 31);
		frame.getContentPane().add(lblLibraryDatabase);
		
		lbltime_ = new JLabel("time");
		lbltime_.setBounds(349, 11, 59, 14);
		frame.getContentPane().add(lbltime_);
		
		lbldate_ = new JLabel("date");
		lbldate_.setBounds(407, 11, 75, 14);
		frame.getContentPane().add(lbldate_);
		clock();
	}
}
