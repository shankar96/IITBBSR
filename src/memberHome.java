import java.awt.BorderLayout;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class memberHome extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTable table;
	private JTable table_1;
	private JLabel lblMembername;
	private JLabel lblBookIssueMessage;
	private JLabel lblLibid;
	private JLabel lblPassword_1;
	private JButton btnDenyIssue;
	private JButton btnAllowIssue;
	private JComboBox comboBox;
	private JLabel lblFineOnReturned;
	private JLabel lblFine;
	private JLabel lblFineToBe;
	private	JLabel lbltime_;
	private JLabel lbldate_;
	int bt_id=1;
	int ft_id=1;
	
	DefaultTableModel model;
	DefaultTableModel model1;
	
	private String memId,memName,memType,memGender,memPhoneNo,memEmail,memPassword,memCreatedBy,memCreated;
	String[] argmnts;
	
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
					System.out.println("error in clock "+e);
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

	
	
	void refresh_search()
	{
		String choice=(String)comboBox.getSelectedItem();
		String book_id,book_title,author_name,pub_name;
		int edition,copies_available,total_copies;
		//System.out.println("Hey I'm inside search");
		if(choice=="Book Title")
		{
			try {
				Statement stmt=conn.createStatement();
				String sql="select book_id,book_title,first_name||' '||last_name as author_name,pub_name,edition,copies_available,total_copies from books B natural join publisher P natural join books_by_author BA natural join author A where book_title='"+textField_9.getText()+"'";  
				ResultSet rs=stmt.executeQuery(sql);
				//System.out.println("Hey I'm inside search book_title");
				model.setRowCount(0);
				while(rs.next())
				{	
					//System.out.println("I'm in resultset!!");
					book_id=rs.getString("book_id");
					book_title=rs.getString("book_title");
					author_name=rs.getString("author_name");
					pub_name=rs.getString("pub_name");
					edition=rs.getInt("edition");
					copies_available=rs.getInt("copies_available");
					total_copies=rs.getInt("total_copies");
					//System.out.println("book_id:"+book_id+" book_title:"+book_title);	
					model.addRow(new Object[] { book_id,book_title,author_name,pub_name,edition,copies_available,total_copies });
				}
				stmt.close();
				rs.close();
						
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(choice=="Author")
		{	
			try {
				Statement stmt=conn.createStatement();
				String sql="select book_id,book_title,first_name||' '||last_name as author_name,pub_name,edition,copies_available,total_copies from books B natural join publisher P natural join books_by_author BA natural join author A where book_id in (select BA1.book_id from books_by_author BA1 where BA1.author_id in (select A2.author_id from author A2 where (A2.first_name||' '||A2.last_name)='"+textField_9.getText()+"'))";
				ResultSet rs=stmt.executeQuery(sql);
				model.setRowCount(0);
				while(rs.next())
				{	
					book_id=rs.getString("book_id");
					book_title=rs.getString("book_title");
					author_name=rs.getString("author_name");
					pub_name=rs.getString("pub_name");
					edition=rs.getInt("edition");
					copies_available=rs.getInt("copies_available");
					total_copies=rs.getInt("total_copies");
					//System.out.println("memName:"+memName);	
					model.addRow(new Object[] { book_id,book_title,author_name,pub_name,edition,copies_available,total_copies });
				}
				stmt.close();
				rs.close();
						
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(choice=="Publisher")
		{
			try {
				Statement stmt=conn.createStatement();
				String sql="select book_id,book_title,first_name||' '||last_name as author_name,pub_name,edition,copies_available,total_copies from books B natural join publisher P natural join books_by_author BA natural join author A where pub_id in (select pub_id from publisher where pub_name='"+textField_9.getText()+"')";
				ResultSet rs=stmt.executeQuery(sql);
				model.setRowCount(0);
				while(rs.next())
				{	
					book_id=rs.getString("book_id");
					book_title=rs.getString("book_title");
					author_name=rs.getString("author_name");
					pub_name=rs.getString("pub_name");
					edition=rs.getInt("edition");
					copies_available=rs.getInt("copies_available");
					total_copies=rs.getInt("total_copies");
					//System.out.println("memName:"+memName);	
					model.addRow(new Object[] { book_id,book_title,author_name,pub_name,edition,copies_available,total_copies });
				}
				stmt.close();
				rs.close();
						
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	void refresh_account_info()
	{
		model1.setRowCount(0);
		try {
			Statement stmt=conn.createStatement();
			//System.out.println("Pane opened in memberHome");
			String sql="select distinct BT1.borrow_transaction_id as btId,B1.book_id,B1.book_title,A1.first_name||' '||A1.last_name as author_name,P1.pub_name,B1.edition,(to_date(date_due,'DD-MM-YY')) as date_due from books B1,borrow_transaction BT1,author A1,books_by_author BA1,publisher P1 where BT1.book_id=B1.book_id and B1.book_id=BA1.book_id and BA1.author_id=A1.author_id and B1.pub_id=P1.pub_id and BT1.borrow_transaction_id in (select distinct BT.borrow_transaction_id from borrow_transaction BT where BT.member_id='"+memId+"' and BT.is_returned='N')";
			ResultSet rs=stmt.executeQuery(sql);
			String book_id,book_title,author_name,pub_name,btId;
			int edition,fine;
			while(rs.next())
			{
				btId=rs.getString("btId");
				book_id=rs.getString("book_id");
				book_title=rs.getString("book_title");
				author_name=rs.getString("author_name");
				pub_name=rs.getString("pub_name");
				edition=rs.getInt("edition");
				//due_date=rs.getdate("date_due");
				//if(fine<0)
					//fine=0;
				//System.out.println("memName:"+memName);	
				model1.addRow(new Object[] { btId,book_id,book_title,author_name,pub_name,edition,rs.getDate("date_due")});
			}
			stmt.close();
			rs.close();
					
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//System.out.println("hello I'm in main");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					memberHome frame = new memberHome(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection conn=null;
	private JTextField textField_10;
	private JPasswordField passwordField_2;
	/**
	 * Create the frame.
	 */
	public memberHome(String member_id) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		memId=member_id;
		conn=LibDBConnector.dbConnector();
		try {
			Statement stmt=conn.createStatement();
			//System.out.println("Pane opened in memberHome");
			String sql="select * from members where member_id='"+memId+"'";
			ResultSet rs=stmt.executeQuery(sql);
			//System.out.println("Hi I'm here.. memId="+memId);
			while(rs.next())
			{
				memId=rs.getString("member_id");
				memName=rs.getString("member_name");
				memType=rs.getString("member_type");
				memPhoneNo=rs.getString("phone_number");
				memEmail=rs.getString("email");
				memGender=rs.getString("gender");
				memPassword=rs.getString("password");
				memCreatedBy=rs.getString("created_by");
				memCreated=(String)rs.getString("created");
				//System.out.println("memID:"+memId+" memName:"+memName+" memType:"+memType);
			}
			stmt.close();
			rs.close();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setBounds(100, 100, 822, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(33, 54, 763, 371);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Profile", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name :");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel.setBounds(26, 36, 61, 18);
		panel.add(lblNewLabel);
		
		JLabel lblType = new JLabel("Type :");
		lblType.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblType.setBounds(26, 65, 46, 14);
		panel.add(lblType);
		
		JLabel lblNewLabel_3 = new JLabel("Gender :");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_3.setBounds(26, 90, 61, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number :");
		lblPhoneNumber.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblPhoneNumber.setBounds(26, 115, 112, 18);
		panel.add(lblPhoneNumber);
		
		JLabel lblCreatedBy = new JLabel("Email :");
		lblCreatedBy.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblCreatedBy.setBounds(26, 144, 93, 14);
		panel.add(lblCreatedBy);
		
		JLabel lblNewLabel_7 = new JLabel("Created :");
		lblNewLabel_7.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_7.setBounds(26, 176, 73, 14);
		panel.add(lblNewLabel_7);
		
		JLabel lblCreatedBy_1 = new JLabel("Created By :");
		lblCreatedBy_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblCreatedBy_1.setBounds(26, 201, 93, 14);
		panel.add(lblCreatedBy_1);
		
		JLabel lblMemberid = new JLabel("Member_id :");
		lblMemberid.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblMemberid.setBounds(26, 13, 93, 14);
		panel.add(lblMemberid);
		
		
		
		textField = new JTextField();
		textField.setBounds(149, 11, 283, 20);
		panel.add(textField);
		textField.setColumns(10);
		textField.setEditable(false);
		textField.setText(memId);
		
		textField_1 = new JTextField();
		textField_1.setBounds(149, 36, 283, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setEditable(false);
		textField_1.setText(memName);
		
		textField_2 = new JTextField();
		textField_2.setBounds(149, 63, 283, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setEditable(false);
		textField_2.setText(memType);
		
		textField_3 = new JTextField();
		textField_3.setBounds(149, 88, 283, 20);
		panel.add(textField_3);
		textField_3.setColumns(10);
		textField_3.setEditable(false);
		textField_3.setText(memGender);
		
		textField_4 = new JTextField();
		textField_4.setBounds(149, 115, 283, 20);
		panel.add(textField_4);
		textField_4.setColumns(10);
		textField_4.setEditable(false);
		textField_4.setText(memPhoneNo);
		
		textField_5 = new JTextField();
		textField_5.setBounds(149, 142, 283, 20);
		panel.add(textField_5);
		textField_5.setColumns(10);
		textField_5.setEditable(false);
		textField_5.setText(memEmail);
		
		textField_6 = new JTextField();
		textField_6.setBounds(149, 174, 283, 20);
		panel.add(textField_6);
		textField_6.setColumns(10);
		textField_6.setEditable(false);
		textField_6.setText(memCreated);
		
		textField_7 = new JTextField();
		textField_7.setBounds(149, 200, 283, 20);
		panel.add(textField_7);
		textField_7.setColumns(10);
		textField_7.setEditable(false);
		textField_7.setText(memCreatedBy);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblPassword.setBounds(26, 233, 73, 14);
		panel.add(lblPassword);
		
		textField_8 = new JTextField();
		textField_8.setBounds(149, 231, 283, 20);
		panel.add(textField_8);
		textField_8.setColumns(10);
		textField_8.setEditable(false);
		textField_8.setText(memPassword);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setEditable(false);
				textField_2.setEditable(false);
				textField_4.setEditable(false);
				textField_5.setEditable(false);
				textField_8.setEditable(false);
				Statement stmt;
				try {	
					stmt = conn.createStatement();
					String sql="update members set member_name='"+textField_1.getText()+"',member_type='"+textField_2.getText()+"',phone_number='"+textField_4.getText()+"',email='"+textField_5.getText()+"',password='"+textField_8.getText()+"' where member_id='"+memId+"'";                        
					stmt.executeUpdate(sql);
					stmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				lblMembername.setText(textField_1.getText());
			}
		});
		btnSave.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnSave.setBounds(26, 274, 89, 23);
		panel.add(btnSave);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField_1.setEditable(true);
				textField_2.setEditable(true);
				textField_4.setEditable(true);
				textField_5.setEditable(true);
				textField_8.setEditable(true);
			}
		});
		btnEdit.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnEdit.setBounds(303, 275, 89, 23);
		panel.add(btnEdit);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Books", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblSearchBooksBy = new JLabel("Search Books By ");
		lblSearchBooksBy.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSearchBooksBy.setBounds(31, 11, 134, 14);
		panel_1.add(lblSearchBooksBy);
		
		comboBox = new JComboBox();
		comboBox.setBounds(187, 10, 86, 20);
		panel_1.add(comboBox);
		comboBox.addItem("Book Title");
		comboBox.addItem("Author");
		comboBox.addItem("Publisher");
		
		textField_9 = new JTextField();
		textField_9.setBounds(296, 10, 307, 20);
		panel_1.add(textField_9);
		textField_9.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblBookIssueMessage.setVisible(false);
				lblLibid.setVisible(false);
				lblPassword_1.setVisible(false);
				textField_10.setEditable(false);
				textField_10.setVisible(false);
				passwordField_2.setEditable(false);
				passwordField_2.setVisible(false);
				btnAllowIssue.setVisible(false);
				btnDenyIssue.setVisible(false);
				
				lblFine.setText(null);
				lblFineOnReturned.setVisible(false);
				lblFine.setVisible(false);
				
				refresh_search();
				
			}
		});
		btnSearch.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnSearch.setBounds(613, 9, 89, 23);
		panel_1.add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 54, 647, 211);
		panel_1.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		model=new DefaultTableModel(){
		    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
		table.setModel(model);
		model.addColumn("book_id");
		model.addColumn("book_title");
		model.addColumn("author_name");
		model.addColumn("pub_name");
		model.addColumn("edition");
		model.addColumn("copies_available");
		model.addColumn("total_copies");
		//model.set

		
		JButton btnIssueSelectedBook = new JButton("Issue Selected Book");
		btnIssueSelectedBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblBookIssueMessage.setVisible(false);
				
				lblFine.setText(null);
				lblFineOnReturned.setVisible(false);
				lblFine.setVisible(false);
				
				int currow=table.getSelectedRow();
				String bookId=(String)table.getValueAt(currow,0);
				//System.out.println("book_id: "+bookId);
				try {
					Statement stmt=conn.createStatement();
					String sql="select count(*) as count_books from borrow_transaction where member_id='"+memId+"' and is_returned='N'";
					ResultSet rs=stmt.executeQuery(sql);
					//System.out.println("Hi I'm here.. memId="+memId);
					int books_issued=0;
					while(rs.next())
						books_issued=rs.getInt("count_books");
					//System.out.println("book_issued:"+books_issued);
					
					if(books_issued>=3)
					{
						lblBookIssueMessage.setText("Max Issue Limit Reached!!");
						lblBookIssueMessage.setVisible(true);
					}
					else
					{
						sql="select copies_available from books where book_id='"+bookId+"'";
						rs=stmt.executeQuery(sql);
						//System.out.println("Hi I'm here.. memId="+memId);
						int available_copies=0;
						while(rs.next())
							available_copies=rs.getInt("copies_available");
						
						if(available_copies>0)
						{
							lblLibid.setVisible(true);
							lblPassword_1.setVisible(true);
							textField_10.setEditable(true);
							textField_10.setVisible(true);
							passwordField_2.setEditable(true);
							passwordField_2.setVisible(true);
							btnAllowIssue.setVisible(true);
							btnDenyIssue.setVisible(true);
							//JOptionPane.showMessageDialog(null,"Connection Successful!!");
						}
						else
						{
							lblBookIssueMessage.setText("Sorry!! No Copies Available!");
							lblBookIssueMessage.setVisible(true);
						}
					}
					
					stmt.close();
					rs.close();
							
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		btnIssueSelectedBook.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnIssueSelectedBook.setBounds(31, 282, 184, 23);
		panel_1.add(btnIssueSelectedBook);
		
		lblBookIssueMessage = new JLabel("book issue message");
		lblBookIssueMessage.setBounds(225, 287, 167, 14);
		panel_1.add(lblBookIssueMessage);
		lblBookIssueMessage.setVisible(false);
		
		lblLibid = new JLabel("Lib_Id :");
		lblLibid.setBounds(396, 287, 46, 14);
		panel_1.add(lblLibid);
		lblLibid.setVisible(false);
		
		lblPassword_1 = new JLabel("Password :");
		lblPassword_1.setBounds(395, 312, 60, 14);
		panel_1.add(lblPassword_1);
		lblPassword_1.setVisible(false);
		
		textField_10 = new JTextField();
		textField_10.setBounds(465, 284, 176, 20);
		panel_1.add(textField_10);
		textField_10.setColumns(10);
		textField_10.setVisible(false);
		textField_10.setEditable(false);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(465, 309, 176, 20);
		panel_1.add(passwordField_2);
		passwordField_2.setColumns(10);
		passwordField_2.setVisible(false);
		passwordField_2.setEditable(false);
		
		btnAllowIssue = new JButton("Allow Issue");
		btnAllowIssue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currow=table.getSelectedRow();
				String bookId=(String)table.getValueAt(currow,0);
				//System.out.println("Hey world I'm here");
				try {
					Statement stmt=conn.createStatement();
					String sql="select count(*) as count from librarian where librarian_id='"+textField_10.getText()+"' and password='"+passwordField_2.getText()+"'";
					//System.out.println(sql);
					ResultSet rs=stmt.executeQuery(sql);
					//System.out.println("hey I'm working");
					int count=0;
					while(rs.next())
						count=rs.getInt("count");
					//System.out.println("hey I'm working too!");
					if(count==1)
					{
						sql="update books set copies_available=copies_available-1 where book_id='"+bookId+"'";
						//System.out.println(sql);
						stmt.executeUpdate(sql);
						Object obj=table.getValueAt(currow, 5);
						String str=obj.toString();
						int value=Integer.parseInt(str);
						value=value-1;
						table.setValueAt(value, currow, 5);
						lblBookIssueMessage.setText("Successfully Issued!!");
						lblBookIssueMessage.setVisible(true);
						lblLibid.setVisible(false);
						lblPassword_1.setVisible(false);
						String libId=textField_10.getText();
						textField_10.setEditable(false);
						textField_10.setVisible(false);
						passwordField_2.setEditable(false);
						passwordField_2.setVisible(false);
						btnAllowIssue.setVisible(false);
						btnDenyIssue.setVisible(false);
						// ADD to BORROW_TRANSACTION
						sql="select max(borrow_transaction_id) as max_id from borrow_transaction";
						rs=stmt.executeQuery(sql);
						int c=0;
						while(rs.next())
						{
							c=1;
							bt_id=rs.getInt("max_id");
						}
						if(c==0)
							bt_id=1;
						else
							bt_id=bt_id+1;
						//sql="select current_timestamp as curts,sysdate from dual";
						String str1;
						//rs=stmt.executeQuery(sql);
						//System.out.println("book_id:"+bookId);
						str1="insert into borrow_transaction values("+bt_id+",'"+textField_10.getText()+"','"+bookId+"','"+memId+"',current_timestamp,null,sysdate+30,'N')";
						//System.out.println("str1:"+str1);
						stmt.executeQuery(str1);
						bt_id=bt_id+1;
						textField_10.setText(null);
						passwordField_2.setText(null);
						refresh_account_info();	
						
						String arr[]=new String[2];
						arr[0]=memId;
						arr[1]=bookId;
						emailing.issue(arr);
					}
					else if(count==0)
					{
						lblBookIssueMessage.setText("ID or Password is wrong");
						lblBookIssueMessage.setVisible(true);
					}
					else if(count>1)
					{
						lblBookIssueMessage.setText("Duplicate ID or Password");
						lblBookIssueMessage.setVisible(true);
					}
					stmt.close();
					rs.close();
							
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		btnAllowIssue.setBounds(659, 283, 89, 23);
		panel_1.add(btnAllowIssue);
		btnAllowIssue.setVisible(false);
		
		btnDenyIssue = new JButton("Deny Issue");
		btnDenyIssue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblBookIssueMessage.setText("Cannot Issue!!");
				lblBookIssueMessage.setVisible(true);
				
				lblLibid.setVisible(false);
				lblPassword_1.setVisible(false);
				textField_10.setEditable(false);
				textField_10.setVisible(false);
				passwordField_2.setEditable(false);
				passwordField_2.setVisible(false);
				btnAllowIssue.setVisible(false);
				btnDenyIssue.setVisible(false);
			}
		});
		btnDenyIssue.setBounds(659, 308, 89, 23);
		panel_1.add(btnDenyIssue);
		btnDenyIssue.setVisible(false);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Account Information", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblBooksIssuedBy = new JLabel("Books Issued");
		lblBooksIssuedBy.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblBooksIssuedBy.setBounds(37, 22, 107, 14);
		panel_2.add(lblBooksIssuedBy);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(37, 43, 649, 141);
		panel_2.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		model1=new DefaultTableModel(){
		    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
		table_1.setModel(model1);
		model1.addColumn("transaction_id");
		model1.addColumn("book_id");
		model1.addColumn("book_title");
		model1.addColumn("author_name");
		model1.addColumn("pub_name");
		model1.addColumn("edition");
		model1.addColumn("due_date");
		refresh_account_info();
		
		
		
		JButton btnReturnSelectedBook = new JButton("Return Selected Book");
		btnReturnSelectedBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int currow=table_1.getSelectedRow();
				String btId=(String)table_1.getValueAt(currow,0);
				//System.out.println("btId:"+btId);
				String bookId=(String)table_1.getValueAt(currow,1);
				try {
					Statement stmt=conn.createStatement();
					//System.out.println("Pane opened in memberHome");
					String sql="update books set copies_available=copies_available+1 where book_id='"+bookId+"'";
					stmt.executeQuery(sql);
					sql="update borrow_transaction set is_returned='Y',returned_datetime=current_timestamp where borrow_transaction_id='"+btId+"'";
					//System.out.println("sql:"+sql);
					stmt.executeUpdate(sql);
					sql="select sysdate-date_due as fine from borrow_transaction where borrow_transaction_id="+btId;
					//System.out.println("sql::"+sql);
					ResultSet rs;
					rs=stmt.executeQuery(sql);
					
					//String libId="select librarian_id from borrow_transaction where borrow_transaction_id="+btId;
					
					int fine=0;
					while(rs.next())
						fine=rs.getInt("fine");
					//System.out.println("Fine:"+fine);
					if(fine<0)	
						fine=0;
					else
					{
						sql="select max(fined_transaction_id) as max_id from fined_transaction";
						rs=stmt.executeQuery(sql);
						int c=0;
						while(rs.next())
						{
							c=1;
							ft_id=rs.getInt("max_id");
						}
						if(c==0)
							ft_id=1;
						else
							ft_id=ft_id+1;
						
						sql="insert into fined_transaction values("+ft_id+",'"+memId+"',"+bt_id+","+fine+",0,sysdate)";
						stmt.executeQuery(sql);
											
					}
					
					sql="select sum(amount_added-amount_received) as total_fine from fined_transaction where member_id='"+memId+"'";
					rs=stmt.executeQuery(sql);
					//int c=0;
					int total_fine=0;
					while(rs.next())
						total_fine=rs.getInt("total_fine");
					
					lblFineToBe.setText(""+total_fine+"");
					
					
					lblFineOnReturned.setVisible(true);
					lblFine.setVisible(true);
					lblFine.setText(""+fine+"");
					
					refresh_account_info();
					refresh_search();
					
					String arr[]=new String[2];
					arr[0]=memId;
					arr[1]=bookId;
					emailing.issue(arr);
					
					stmt.close();
							
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		btnReturnSelectedBook.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnReturnSelectedBook.setBounds(37, 195, 221, 23);
		panel_2.add(btnReturnSelectedBook);
		
		JLabel lblYourTotalFine = new JLabel("Your Total Fine  :");
		lblYourTotalFine.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblYourTotalFine.setBounds(37, 252, 126, 14);
		panel_2.add(lblYourTotalFine);
		
		lblFineToBe = new JLabel("Fine to be showed");
		lblFineToBe.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblFineToBe.setBounds(173, 252, 119, 14);
		panel_2.add(lblFineToBe);
		try {
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			String sql="select sum(amount_added-amount_received) as total_fine from fined_transaction where member_id='"+memId+"'";
			//System.out.println("for total_fine :"+sql);
			rs=stmt.executeQuery(sql);
			int total_fine=0;
			//int c=0;
			while(rs.next())
				total_fine=rs.getInt("total_fine");
			lblFineToBe.setText(""+total_fine+"");
			stmt.close();
			rs.close();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lblFineOnReturned = new JLabel("Fine on Returned Book :");
		lblFineOnReturned.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFineOnReturned.setBounds(343, 200, 134, 14);
		panel_2.add(lblFineOnReturned);
		lblFineOnReturned.setVisible(false);
		
		lblFine = new JLabel("Fine");
		lblFine.setBounds(487, 200, 46, 14);
		panel_2.add(lblFine);
		lblFine.setVisible(false);
		
		JLabel lblHello = new JLabel("Hello");
		lblHello.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblHello.setBounds(33, 21, 46, 14);
		contentPane.add(lblHello);
		
		lblMembername = new JLabel("Member_name");
		lblMembername.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblMembername.setBounds(86, 21, 195, 14);
		contentPane.add(lblMembername);
		try {
			Statement stmt=conn.createStatement();
			//System.out.println("Pane opened in memberHome");
			String sql="select member_name from members where member_id='"+memId+"'";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next())
				memName=rs.getString("member_name");
				//System.out.println("memName:"+memName);	
			stmt.close();
			rs.close();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lblMembername.setText(memName);
		
		
		JButton btnSignOut = new JButton("Sign Out");
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblBookIssueMessage.setText(null);
				lblBookIssueMessage.setVisible(false);
				lblFine.setText(null);
				lblFineOnReturned.setVisible(false);
				lblFine.setVisible(false);
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
				Main obj=new Main();
				obj.frame.setVisible(true);
			}
		});
		btnSignOut.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnSignOut.setBounds(592, 20, 107, 23);
		contentPane.add(btnSignOut);
		
		lbltime_ = new JLabel("time");
		lbltime_.setBounds(307, 11, 58, 14);
		contentPane.add(lbltime_);
		
		lbldate_ = new JLabel("date");
		lbldate_.setBounds(375, 11, 75, 14);
		contentPane.add(lbldate_);
		
		clock();
	}
}
