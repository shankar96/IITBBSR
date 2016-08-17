import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.MouseAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Librarian extends JFrame {
	
	
	Connection connection=null;
	private JTable lib_info_table;
	public String lib_name;
	public static String librarian_id;
	public static String password;
	public JScrollPane lib_scrollPane;
	public JComboBox<String> u_comboBox;
	public JTextField edit_info;
	public JComboBox<String> comboBox;
	JComboBox<String> b_comboBox ;
	private JTable mb_info_t;
	private JPasswordField passwordField;
	private JTextField txtmemberid;
	private JTextField txtmembername;
	private JTextField txtmembertype;
	private JTextField txtgender;
	private JTextField txtphone;
	private JTextField txtemail;
	private JTextField txtactive;
	private JTextField txtcreated;
	private JTextField txtbooks;
	static JTabbedPane tabbedPane;
	static int tab=0;
	String a_e="";//add a edit e for members info
	String ab_e="";//add a, edit e for books info
	
	String m_enter_p="";
	JLabel save_icoe;
	JLabel cross_ico1;
	JLabel save_ico;
	JLabel error_ico;
	private JTextField txtFillAppropriateValue;
	private JTextField txtexpire;
	private JTextField txtbook_id;
	private JTextField txtbook_title;
	private JTextField txtedition;
	private JTextField txtpub_date;
	private JTextField txtaf_name;
	private JTextField txtcategory;
	private JTextField txtstate;
	private JTextField txtcatalog;
	private JTextField txtupdated;
	private JTextField txtcopies;
	private JTextField txt_created;
	private JTextField txtas_name;
	private JTextField txtrow;
	private JTextField txtmsg;
	private JTextField txtpub_name;
	private JLabel lbltime_;
	private JLabel lbldate_;
	private JTextField txtsearcht;
	private JTable table;
	int search_clk1=0;
	int load1=0;
	private JTextField txtfine;
	JLabel lblcbox;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(args.length>0){
						System.out.println(args[0]+","+args[1]+","+args[2]);
					librarian_id=args[1];
					password=args[2];
					tab=Integer.parseInt(args[0]);
					
					}
					Librarian frame = new Librarian();
					tabbedPane.setSelectedIndex(tab);
					frame.setVisible(true);
					frame.setTitle("Librarian");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the frame.
	 */
	
	
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
	
	public Librarian( )
	{
		
		//tabbedPane.setSelectedIndex(0);

		
		
		if(librarian_id==null){
			System.out.println("lib null");
		librarian_id="LB1";
		password="123";}
		
		System.out.println("present val="+librarian_id);
		connection=oracleConnection.DbConnector();
		
		set_value();
		
		Image ok = new ImageIcon(this.getClass().getResource("/ok.png")).getImage();
		Image ok1 = ok.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH ) ;
		
		Image nok = new ImageIcon(this.getClass().getResource("/nok.png")).getImage();
		Image nok1 = nok.getScaledInstance( 20, 25,  java.awt.Image.SCALE_SMOOTH ) ;
		
		setTitle("Librarian");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 508, 422);
		getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 50, 492, 333);
		getContentPane().add(tabbedPane);
		
		
		
		JPanel librarian_info = new JPanel();
		tabbedPane.addTab("profile", null, librarian_info, null);
		librarian_info.setLayout(null);
		
		lib_scrollPane = new JScrollPane();
		lib_scrollPane.setBounds(0, 39, 225, 266);
		librarian_info.add(lib_scrollPane);
		
		
		get_lib_info_table();
		
		
		
		lib_scrollPane.setViewportView(lib_info_table);
		JLabel lblLibrarian = new JLabel(librarian_id.trim()+"_info");
		lblLibrarian.setBounds(75, 11, 87, 14);
		lblLibrarian.setToolTipText("");
		lblLibrarian.setFont(new Font("Tahoma", Font.PLAIN, 14));
		librarian_info.add(lblLibrarian);
		
		lbltime_ = new JLabel("");
		lbltime_.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbltime_.setBounds(380, 0, 45, 15);
		getContentPane().add(lbltime_);
		
		lbldate_ = new JLabel("");
		lbldate_.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbldate_.setBounds(430, 0, 60, 14);
		getContentPane().add(lbldate_);
		
		clock();
		
		lblcbox = new JLabel("VAR");
		lblcbox.setBounds(246, 81, 87, 28);
		lblcbox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		librarian_info.add(lblcbox);
		
		u_comboBox = new JComboBox();
		u_comboBox.setBounds(269, 32, 208, 30);
		
		u_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 u_combo_info();
			}
		});
		u_comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		librarian_info.add(u_comboBox);
		
		
		
		edit_info = new JTextField();
		edit_info.setBounds(343, 82, 134, 30);
		edit_info.setFont(new Font("Tahoma", Font.PLAIN, 15));
		edit_info.setBackground(SystemColor.control);
		edit_info.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		librarian_info.add(edit_info);
		edit_info.setColumns(10);
		
		JLabel su_ico=new JLabel("");
		su_ico.setBounds(439, 146, 38, 30);
		
		JButton btnsave = new JButton("SAVE/UPDATE");
		btnsave.setBounds(288, 146, 145, 30);
		btnsave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String value=edit_info.getText();
				String attr=lblcbox.getText();
				System.out.println(attr+" , "+value);
				String sql="update librarian set "+attr+" = '"+value+"' where librarian_id ='"+librarian_id+"'";
				System.out.println(sql);
				
				try {
					Statement stmt1=connection.createStatement();
					int rs=stmt1.executeUpdate(sql);
					if(rs>0)
					{
						sql="update librarian set updated_by= '"+librarian_id+"', updated=sysdate where librarian_id ='"+librarian_id+"'";;
						System.out.println(sql);
						rs=connection.createStatement().executeUpdate(sql);
						if(rs>0)
						su_ico.setVisible(true);
						
					}
					stmt1.close();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnsave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnsave.setBackground(SystemColor.control);
		librarian_info.add(btnsave);
		
		JButton btnrefresh = new JButton("REFRESH");
		btnrefresh.setBounds(306, 205, 121, 30);
		btnrefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				common_refresh(0);
			}
		});
		btnrefresh.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnrefresh.setBackground(SystemColor.menu);
		librarian_info.add(btnrefresh);
		
		
		Image ico = new ImageIcon(this.getClass().getResource("/ok.png")).getImage();
		Image ico1 = ico.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH ) ;
		su_ico.setIcon(new ImageIcon(ico1));
		su_ico.setVisible(false);
		librarian_info.add(su_ico);
		
		JLabel rfsh_ico = new JLabel("");
		rfsh_ico.setBounds(437, 205, 38, 30);
		rfsh_ico.setIcon(new ImageIcon(ico1));
		rfsh_ico.setVisible(false);
		librarian_info.add(rfsh_ico);
		
		fill_combo_ubox();
		
		
		
		JButton btnRefresh = new JButton("REFRESH");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnRefresh.setVisible(true);
				if(m_enter_p.compareTo("m")==0)
				build_mb_info_table(); 
				else
					build_book_info_table();
				common_refresh(1);
			}
		});
		JPanel panel_1 = new JPanel();
		
		
		tabbedPane.addTab("members/ books", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBounds(0, 35, 487, 270);
		panel_1.add(scrollPane);
		
		mb_info_t = new JTable();
		scrollPane.setViewportView(mb_info_t);
		
		
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRefresh.setBounds(182, 0, 103, 37);
		btnRefresh.setVisible(false);
		panel_1.add(btnRefresh);
		
		JButton btnLoadBooksDetails = new JButton("LOAD BOOKS DETAILS");
		btnLoadBooksDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m_enter_p="b";
				build_book_info_table(); 
			}
		});
		btnLoadBooksDetails.setBounds(5, 0, 140, 37);
		panel_1.add(btnLoadBooksDetails);
		
		JButton btnLoadMembersDetails = new JButton("LOAD MEMBERS DETAILS");
		btnLoadMembersDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m_enter_p="m";
				build_mb_info_table(); 
			}
		});
		btnLoadMembersDetails.setBounds(320, 0, 153, 37);
		panel_1.add(btnLoadMembersDetails);
		
		JPanel edit_member = new JPanel();
		edit_member.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlShadow));
		edit_member.setForeground(SystemColor.textHighlight);
		tabbedPane.addTab("edit_member", null, edit_member, null);
		
		
		txtmemberid = new JTextField();
		txtmemberid.setBounds(349, 10, 138, 20);
		txtmemberid.setEditable(false);
		txtmemberid.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtmemberid.setForeground(SystemColor.activeCaptionText);
		txtmemberid.setBackground(SystemColor.control);
		txtmemberid.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		edit_member.setLayout(null);
		
		txtmembername = new JTextField();
		txtmembername.setBounds(349, 45, 138, 20);
		txtmembername.setEditable(false);
		txtmembername.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtmembername.setForeground(SystemColor.activeCaptionText);
		txtmembername.setBackground(SystemColor.control);
		txtmembername.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtmembername.setColumns(10);
		edit_member.add(txtmembername);
		
		txtmembertype = new JTextField();
		txtmembertype.setBounds(349, 80, 138, 20);
		txtmembertype.setEditable(false);
		txtmembertype.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtmembertype.setForeground(SystemColor.controlText);
		txtmembertype.setBackground(SystemColor.control);
		txtmembertype.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtmembertype.setColumns(10);
		edit_member.add(txtmembertype);
		
		txtgender = new JTextField();
		txtgender.setBounds(349, 115, 138, 20);
		txtgender.setEditable(false);
		txtgender.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtgender.setForeground(SystemColor.controlText);
		txtgender.setBackground(SystemColor.control);
		txtgender.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtgender.setColumns(10);
		edit_member.add(txtgender);
		
		txtphone = new JTextField();
		txtphone.setBounds(349, 140, 138, 20);
		txtphone.setEditable(false);
		txtphone.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtphone.setForeground(SystemColor.controlText);
		txtphone.setBackground(SystemColor.control);
		txtphone.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtphone.setColumns(10);
		edit_member.add(txtphone);
		
		txtemail = new JTextField();
		txtemail.setBounds(349, 175, 138, 20);
		txtemail.setEditable(false);
		txtemail.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtemail.setForeground(SystemColor.controlText);
		txtemail.setBackground(SystemColor.control);
		txtemail.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtemail.setColumns(10);
		edit_member.add(txtemail);
		
		txtactive = new JTextField();
		txtactive.setBounds(349, 210, 138, 20);
		txtactive.setEditable(false);
		txtactive.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtactive.setForeground(SystemColor.controlText);
		txtactive.setBackground(SystemColor.control);
		txtactive.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtactive.setColumns(10);
		edit_member.add(txtactive);
		
		JLabel lblcreated = new JLabel("Created_by");
		lblcreated.setBounds(252, 245, 89, 25);
		lblcreated.setFont(new Font("Tahoma", Font.PLAIN, 14));
		edit_member.add(lblcreated);
		
		txtcreated = new JTextField();
		txtcreated.setBounds(349, 245, 138, 20);
		txtcreated.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtcreated.setForeground(SystemColor.controlText);
		txtcreated.setColumns(10);
		txtcreated.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtcreated.setBackground(SystemColor.menu);
		txtcreated.setEditable(false);
		edit_member.add(txtcreated);
		txtbooks = new JTextField();
		txtbooks.setBounds(349, 280, 138, 20);
		txtbooks.setEditable(false);
		txtbooks.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtbooks.setForeground(SystemColor.controlText);
		txtbooks.setColumns(10);
		txtbooks.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtbooks.setBackground(SystemColor.menu);
		edit_member.add(txtbooks);
		
		edit_member.add(txtmemberid);
		txtmemberid.setColumns(10);
		
		JLabel lblexpiry = new JLabel("Expiry date");
		lblexpiry.setBounds(5, 275, 78, 25);
		lblexpiry.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblexpiry.setVisible(false);
		edit_member.add(lblexpiry);
		
		txtexpire = new JTextField();
		txtexpire.setBounds(84, 275, 156, 20);
		txtexpire.setForeground(Color.BLACK);
		txtexpire.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtexpire.setEditable(false);
		txtexpire.setColumns(10);
		txtexpire.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtexpire.setBackground(SystemColor.menu);
		txtexpire.setVisible(false);
		lblexpiry.setVisible(false);
		edit_member.add(txtexpire);
		
		JLabel lpassw = new JLabel("SET PASSWORD");
		lpassw.setBounds(5, 70, 103, 25);
		lpassw.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lpassw.setVisible(false);
		edit_member.add(lpassw);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(118, 70, 111, 25);
		passwordField.setEditable(false);
		passwordField.setBackground(SystemColor.control);
		passwordField.setVisible(false);
		passwordField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(128, 128, 128)));
		edit_member.add(passwordField);
		
		JLabel lblMemberid = new JLabel("member_id");
		lblMemberid.setBounds(252, 10, 78, 25);
		lblMemberid.setFont(new Font("Tahoma", Font.PLAIN, 14));
		edit_member.add(lblMemberid);
		
		
		
		JLabel lblMembername = new JLabel("member_name");
		lblMembername.setBounds(252, 45, 100, 25);
		lblMembername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		edit_member.add(lblMembername);
		
		JLabel lblMembertype = new JLabel("member_type");
		lblMembertype.setBounds(252, 80, 89, 25);
		lblMembertype.setFont(new Font("Tahoma", Font.PLAIN, 14));
		edit_member.add(lblMembertype);
		
		JLabel lblGender = new JLabel("gender");
		lblGender.setBounds(252, 115, 68, 25);
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 14));
		edit_member.add(lblGender);
		
		JLabel lblPhoneNumber = new JLabel("phone number");
		lblPhoneNumber.setBounds(252, 140, 100, 25);
		lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		edit_member.add(lblPhoneNumber);
		
		JLabel lblEmailid = new JLabel("email_id");
		lblEmailid.setBounds(252, 175, 68, 25);
		lblEmailid.setFont(new Font("Tahoma", Font.PLAIN, 14));
		edit_member.add(lblEmailid);
		
		JLabel lblActive = new JLabel("active");
		lblActive.setBounds(252, 210, 58, 25);
		lblActive.setFont(new Font("Tahoma", Font.PLAIN, 14));
		edit_member.add(lblActive);
		
		
		
		JLabel lblbooks = new JLabel("Books_Issued");
		lblbooks.setBounds(252, 280, 89, 25);
		lblbooks.setFont(new Font("Tahoma", Font.PLAIN, 14));
		edit_member.add(lblbooks);
		
		JLabel lblenterintext = new JLabel("Enter In TextField  and");
		lblenterintext.setBounds(15, 93, 181, 20);
		lblenterintext.setForeground(SystemColor.textHighlight);
		lblenterintext.setFont(new Font("Tahoma", Font.BOLD, 12));
		edit_member.add(lblenterintext);
		
		JLabel lblclicksave = new JLabel("click save/update");
		lblclicksave.setBounds(30, 108, 138, 20);
		lblclicksave.setForeground(SystemColor.textHighlight);
		lblclicksave.setFont(new Font("Tahoma", Font.BOLD, 12));
		edit_member.add(lblclicksave);
		
		JButton btnAddNewMember = new JButton("ADD NEW MEMBER");
		btnAddNewMember.setBounds(10, 130, 132, 30);
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.setBounds(30, 40, 89, 30);
		
		save_ico = new JLabel("");
		save_ico.setBounds(145, 168, 39, 27);
		save_ico.setIcon(new ImageIcon(ok1));
		save_ico.setVisible(false);
		edit_member.add(save_ico);
		
		error_ico = new JLabel("");
		error_ico.setBounds(152, 168, 25, 27);
		error_ico.setIcon(new ImageIcon(nok1));
		error_ico.setVisible(false);
		edit_member.add(error_ico);
		
		JLabel lblfine = new JLabel("total_fine");
		lblfine.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblfine.setBounds(5, 245, 78, 25);
		edit_member.add(lblfine);
		
		txtfine = new JTextField();
		txtfine.setForeground(Color.BLACK);
		txtfine.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtfine.setEditable(false);
		txtfine.setColumns(10);
		txtfine.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtfine.setBackground(SystemColor.menu);
		txtfine.setBounds(84, 249, 156, 20);
		edit_member.add(txtfine);
		
		comboBox = new JComboBox();
		comboBox.setBounds(10, 5, 132, 30);
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 a_e="";
				lblfine.setVisible(true);
				txtfine.setVisible(true);
				
				txtmemberid.setVisible(true);
				lblMemberid.setVisible(true);
				
				txtexpire.setVisible(true);
				lblexpiry.setVisible(true);
				
				txtmembername.setEditable(false);
				txtmembertype.setEditable(false);
				txtemail.setEditable(false);
				txtphone.setEditable(false);
				txtactive.setEditable(false);
				txtgender.setEditable(false);
				
				txtactive.setBackground(SystemColor.control);
				txtmembername.setBackground(SystemColor.control);
				txtmembertype.setBackground(SystemColor.control);
				txtemail.setBackground(SystemColor.control);
				txtgender.setBackground(SystemColor.control);
				txtphone.setBackground(SystemColor.control);
				
				txtcreated.setVisible(true);
				lblcreated.setVisible(true);
				txtbooks.setVisible(true);
				lblbooks.setVisible(true);
				
				passwordField.setVisible(false);
				passwordField.setText("");
				lpassw.setVisible(false);
				lblenterintext.setVisible(false);
				lblclicksave.setVisible(false);
				
				btnAddNewMember.setVisible(true);
				btnEdit.setVisible(true);
				
				error_ico.setVisible(false);
				save_ico.setVisible(false);
				txtFillAppropriateValue.setVisible(false);
				 fill_each_mem();
			}
		});
		
		edit_member.add(comboBox);
		//fill_combo_mbox();
		
		
		
		JButton btnSaveupdate = new JButton("SAVE/UPDATE");
		btnSaveupdate.setBounds(10, 165, 132, 30);
		btnSaveupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				if(a_e.compareTo("a")==0)
				{
					System.out.println("a a_e="+a_e);
					update_member_table(a_e);
				}
				else if(a_e.compareTo("e")==0)
				{
					System.out.println("e a_e="+a_e);
					update_member_table(a_e);
				}
				else if(a_e=="")
				{
					//nothing refresh
					a_e="";
					txtmemberid.setVisible(true);
					lblMemberid.setVisible(true);
					
					txtexpire.setVisible(true);
					lblexpiry.setVisible(true);
					
					txtmembername.setEditable(false);
					txtmembertype.setEditable(false);
					txtemail.setEditable(false);
					txtphone.setEditable(false);
					txtactive.setEditable(false);
					txtgender.setEditable(false);
					
					txtactive.setBackground(SystemColor.control);
					txtmembername.setBackground(SystemColor.control);
					txtmembertype.setBackground(SystemColor.control);
					txtemail.setBackground(SystemColor.control);
					txtgender.setBackground(SystemColor.control);
					txtphone.setBackground(SystemColor.control);
					
					txtcreated.setVisible(true);
					lblcreated.setVisible(true);
					txtbooks.setVisible(true);
					lblbooks.setVisible(true);
					
					passwordField.setVisible(false);
					passwordField.setText("");
					lpassw.setVisible(false);
					lblenterintext.setVisible(false);
					lblclicksave.setVisible(false);
					
					btnAddNewMember.setVisible(true);
					btnEdit.setVisible(true);
					
					error_ico.setVisible(false);
					save_ico.setVisible(false);
					txtFillAppropriateValue.setVisible(false);
					
					fill_each_mem();
				}
				
				//a_e="";
			}
		});
		edit_member.add(btnSaveupdate);
		
		
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				a_e="e";
				txtmemberid.setVisible(false);
				lblMemberid.setVisible(false);
				txtexpire.setVisible(false);
				lblexpiry.setVisible(false);
				
				txtmembername.setEditable(true);
				txtmembertype.setEditable(true);
				txtemail.setEditable(true);
				txtphone.setEditable(true);
				txtactive.setEditable(true);
				txtgender.setEditable(true);
				
				
				
				txtcreated.setVisible(false);
				lblcreated.setVisible(false);
				txtbooks.setVisible(false);
				lblbooks.setVisible(false);
				passwordField.setVisible(false);
				passwordField.setText("");
				lpassw.setVisible(false);
				
				lblfine.setVisible(false);
				txtfine.setVisible(false);
				
				lblenterintext.setVisible(true);
				lblclicksave.setVisible(true);
				
				btnAddNewMember.setVisible(false);
				btnEdit.setVisible(true);
				
				error_ico.setVisible(false);
				save_ico.setVisible(false);
				txtFillAppropriateValue.setVisible(false);
				//btnundo.
			}
		});
		edit_member.add(btnEdit);
		
		
		btnAddNewMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				a_e="a";
				txtmemberid.setVisible(false);
				lblMemberid.setVisible(false);
				txtexpire.setVisible(false);
				lblexpiry.setVisible(false);
				
				txtmembername.setEditable(true);
				txtmembername.setText("");
				txtmembertype.setEditable(true);
				txtmembertype.setText("");
				txtemail.setEditable(true);
				txtemail.setText("");
				txtphone.setEditable(true);
				txtphone.setText("");
				txtactive.setEditable(true);
				txtactive.setText("");
				txtgender.setEditable(true);
				txtgender.setText("");
				
				lblfine.setVisible(false);
				txtfine.setVisible(false);
				
				txtcreated.setVisible(false);
				lblcreated.setVisible(false);
				txtbooks.setVisible(false);
				lblbooks.setVisible(false);
				passwordField.setVisible(true);
				passwordField.setText("");
				passwordField.setEditable(true);
				lpassw.setVisible(true);
				
				lblenterintext.setVisible(true);
				lblclicksave.setVisible(true);
				
				btnAddNewMember.setVisible(true);
				btnEdit.setVisible(false);
				
				error_ico.setVisible(false);
				save_ico.setVisible(false);
				txtFillAppropriateValue.setVisible(false);
			}
		});
		edit_member.add(btnAddNewMember);
		
		JButton btnrefresh_1 = new JButton("REFRESH");
		btnrefresh_1.setBounds(145, 3, 103, 30);
		btnrefresh_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnrefresh_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				a_e="";
				txtmemberid.setVisible(true);
				lblMemberid.setVisible(true);
				txtexpire.setVisible(true);
				lblexpiry.setVisible(true);
				
				txtmembername.setEditable(false);
				txtmembertype.setEditable(false);
				txtemail.setEditable(false);
				txtphone.setEditable(false);
				txtactive.setEditable(false);
				txtgender.setEditable(false);
				
				txtactive.setBackground(SystemColor.control);
				txtmembername.setBackground(SystemColor.control);
				txtmembertype.setBackground(SystemColor.control);
				txtemail.setBackground(SystemColor.control);
				txtgender.setBackground(SystemColor.control);
				txtphone.setBackground(SystemColor.control);
				
				txtcreated.setVisible(true);
				lblcreated.setVisible(true);
				txtbooks.setVisible(true);
				lblbooks.setVisible(true);
				
				lblfine.setVisible(true);
				txtfine.setVisible(true);
				
				passwordField.setVisible(false);
				passwordField.setText("");
				lpassw.setVisible(false);
				lblenterintext.setVisible(false);
				lblclicksave.setVisible(false);
				
				btnAddNewMember.setVisible(true);
				btnEdit.setVisible(true);
				
				error_ico.setVisible(false);
				save_ico.setVisible(false);
				txtFillAppropriateValue.setVisible(false);
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				connection=oracleConnection.DbConnector();
				fill_each_mem();
				common_refresh(2);
			}
		});
		edit_member.add(btnrefresh_1);
		
		txtFillAppropriateValue = new JTextField();
		txtFillAppropriateValue.setBounds(5, 205, 209, 20);
		txtFillAppropriateValue.setEditable(false);
		txtFillAppropriateValue.setBackground(SystemColor.control);
		txtFillAppropriateValue.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlShadow));
		txtFillAppropriateValue.setVisible(false);
		edit_member.add(txtFillAppropriateValue);
		txtFillAppropriateValue.setColumns(10);
		
		
		
		
		
//		save_ico = new JLabel("");
//		save_ico.setIcon(new ImageIcon(ok1));
//		save_ico.setVisible(false);
//		save_ico.setBounds(145, 213, 40, 27);
//		edit_member.add(save_ico);
//		
//		error_ico = new JLabel("");
//		error_ico.setIcon(new ImageIcon(nok1));
//		error_ico.setVisible(false);
//		error_ico.setBounds(150, 213, 25, 27);
//		edit_member.add(error_ico);
//		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("edit_book", null, panel_4, null);
		panel_4.setLayout(null);
		
		
		save_icoe = new JLabel("");
		save_icoe.setIcon(new ImageIcon(ok1));
		save_icoe.setVisible(false);
		save_icoe.setBounds(145, 142, 39, 27);
		panel_4.add(save_icoe);
		
		cross_ico1 = new JLabel("");
		cross_ico1.setIcon(new ImageIcon(nok1));
		cross_ico1.setVisible(false);
		cross_ico1.setBounds(145, 142, 39, 27);
		panel_4.add(cross_ico1);
		
		
		JLabel b_msg2 = new JLabel("click save/update");
		b_msg2.setBounds(25, 85, 138, 20);
		b_msg2.setForeground(SystemColor.textHighlight);
		b_msg2.setFont(new Font("Tahoma", Font.BOLD, 12));
		b_msg2.setVisible(false);
		panel_4.add(b_msg2);
		
		JLabel b_msg1 = new JLabel("Enter In TextField  and");
		b_msg1.setBounds(10, 70, 181, 20);
		b_msg1.setForeground(SystemColor.textHighlight);
		b_msg1.setFont(new Font("Tahoma", Font.BOLD, 12));
		b_msg1.setVisible(false);
		panel_4.add(b_msg1);
		
		JLabel lblb_id = new JLabel("book_id");
		lblb_id.setBounds(252, 0, 78, 25);
		lblb_id.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblb_id);
		
		txtbook_id = new JTextField();
		txtbook_id.setBounds(349, 0, 138, 20);
		txtbook_id.setForeground(Color.BLACK);
		txtbook_id.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtbook_id.setEditable(false);
		txtbook_id.setColumns(10);
		txtbook_id.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtbook_id.setBackground(SystemColor.menu);
		panel_4.add(txtbook_id);
		
		JLabel lblb_name = new JLabel("book_title");
		lblb_name.setBounds(252, 35, 100, 25);
		lblb_name.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblb_name);
		
		txtbook_title = new JTextField();
		txtbook_title.setBounds(349, 35, 138, 20);
		txtbook_title.setForeground(Color.BLACK);
		txtbook_title.setFont(new Font("Tahoma", Font.BOLD, 14));
		//txtbook_title.setEditable(false);
		txtbook_title.setHorizontalAlignment(JTextField.LEFT);
		txtbook_title.setColumns(10);
		txtbook_title.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(160, 160, 160)));
		txtbook_title.setBackground(SystemColor.menu);
		panel_4.add(txtbook_title);
		
		txtedition = new JTextField();
		txtedition.setBounds(349, 71, 138, 20);
		txtedition.setForeground(Color.BLACK);
		txtedition.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtedition.setEditable(false);
		txtedition.setColumns(10);
		txtedition.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtedition.setBackground(SystemColor.menu);
		panel_4.add(txtedition);
		
		JLabel lbledition = new JLabel("edition");
		lbledition.setBounds(252, 71, 100, 25);
		lbledition.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lbledition);
		
		txtpub_date = new JTextField();
		txtpub_date.setBounds(349, 99, 138, 20);
		txtpub_date.setForeground(Color.BLACK);
		txtpub_date.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtpub_date.setEditable(false);
		txtpub_date.setColumns(10);
		txtpub_date.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtpub_date.setBackground(SystemColor.menu);
		panel_4.add(txtpub_date);
		
		JLabel lblPubdate = new JLabel("pub_date");
		lblPubdate.setBounds(252, 99, 100, 25);
		lblPubdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblPubdate);
		
		txtaf_name = new JTextField();
		txtaf_name.setBounds(330, 130, 84, 20);
		txtaf_name.setForeground(Color.BLACK);
		txtaf_name.setFont(new Font("Tahoma", Font.BOLD, 14));
		//txtaf_name.setEditable(false);
		txtaf_name.setColumns(10);
		txtaf_name.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtaf_name.setBackground(SystemColor.menu);
		panel_4.add(txtaf_name);
		
		JLabel lblAuthorfname = new JLabel("author_name");
		lblAuthorfname.setBounds(246, 130, 84, 25);
		lblAuthorfname.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblAuthorfname);
		
		txtcategory = new JTextField();
		txtcategory.setBounds(349, 161, 138, 20);
		txtcategory.setForeground(Color.BLACK);
		txtcategory.setFont(new Font("Tahoma", Font.BOLD, 14));
		//txtcategory.setEditable(false);
		txtcategory.setColumns(10);
		txtcategory.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtcategory.setBackground(SystemColor.menu);
		panel_4.add(txtcategory);
		
		JLabel lblAuthorlname = new JLabel("category");
		lblAuthorlname.setBounds(252, 161, 100, 25);
		lblAuthorlname.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblAuthorlname);
		
		txtstate = new JTextField();
		txtstate.setBounds(349, 192, 138, 20);
		txtstate.setForeground(Color.BLACK);
		txtstate.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtstate.setEditable(false);
		txtstate.setColumns(10);
		txtstate.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtstate.setBackground(SystemColor.menu);
		panel_4.add(txtstate);
		
		JLabel lblstate = new JLabel("state");
		lblstate.setBounds(252, 192, 100, 25);
		lblstate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblstate);
		
		txtcatalog = new JTextField();
		txtcatalog.setBounds(349, 223, 72, 20);
		txtcatalog.setForeground(Color.BLACK);
		txtcatalog.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtcatalog.setEditable(false);
		txtcatalog.setColumns(10);
		txtcatalog.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtcatalog.setBackground(SystemColor.menu);
		panel_4.add(txtcatalog);
		
		JLabel lblCatalogn = new JLabel("catalog/row no.");
		lblCatalogn.setBounds(252, 223, 100, 25);
		lblCatalogn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblCatalogn);
		
		txtupdated = new JTextField();
		txtupdated.setBounds(349, 280, 138, 20);
		txtupdated.setForeground(Color.BLACK);
		txtupdated.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtupdated.setEditable(false);
		txtupdated.setColumns(10);
		txtupdated.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtupdated.setBackground(SystemColor.menu);
		panel_4.add(txtupdated);
		
		JLabel lblupdated = new JLabel("updated");
		lblupdated.setBounds(252, 280, 100, 25);
		lblupdated.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblupdated);
		
		txtcopies = new JTextField();
		txtcopies.setBounds(349, 254, 138, 20);
		txtcopies.setForeground(Color.BLACK);
		txtcopies.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtcopies.setEditable(false);
		txtcopies.setColumns(10);
		txtcopies.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtcopies.setBackground(SystemColor.control);
		panel_4.add(txtcopies);
		
		JLabel lblTotalCopies = new JLabel("copies/available no.");
		lblTotalCopies.setBounds(235, 254, 117, 25);
		lblTotalCopies.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblTotalCopies);
		
		txt_created = new JTextField();
		txt_created.setBounds(90, 280, 138, 20);
		txt_created.setForeground(Color.BLACK);
		txt_created.setFont(new Font("Tahoma", Font.BOLD, 14));
		txt_created.setEditable(false);
		txt_created.setColumns(10);
		txt_created.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txt_created.setBackground(SystemColor.menu);
		panel_4.add(txt_created);
		
		JLabel lblCreated = new JLabel("created");
		lblCreated.setBounds(10, 280, 78, 25);
		lblCreated.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblCreated);
		
		txtas_name = new JTextField();
		txtas_name.setBounds(415, 130, 72, 20);
		txtas_name.setForeground(Color.BLACK);
		txtas_name.setFont(new Font("Tahoma", Font.BOLD, 14));
		//txtas_name.setEditable(false);
		txtas_name.setColumns(10);
		txtas_name.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtas_name.setBackground(SystemColor.menu);
		panel_4.add(txtas_name);
		
		txtrow = new JTextField();
		txtrow.setBounds(425, 223, 63, 20);
		txtrow.setForeground(Color.BLACK);
		txtrow.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtrow.setEditable(false);
		txtrow.setColumns(10);
		txtrow.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtrow.setBackground(SystemColor.menu);
		panel_4.add(txtrow);
		
		txtmsg = new JTextField();
		txtmsg.setForeground(new Color(255, 0, 0));
		txtmsg.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtmsg.setEditable(false);
		txtmsg.setColumns(10);
		txtmsg.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(160, 160, 160)));
		txtmsg.setBackground(SystemColor.menu);
		txtmsg.setBounds(10, 175, 181, 20);
		panel_4.add(txtmsg);
		
		JLabel lblpub_name = new JLabel("pub_name");
		lblpub_name.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblpub_name.setBounds(10, 254, 78, 25);
		panel_4.add(lblpub_name);
		
		txtpub_name  = new JTextField();
		txtpub_name.setForeground(Color.BLACK);
		txtpub_name.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtpub_name.setEditable(false);
		txtpub_name.setColumns(10);
		txtpub_name.setBorder(new MatteBorder(0, 0, 2, 0, (Color) SystemColor.controlShadow));
		txtpub_name.setBackground(SystemColor.menu);
		txtpub_name.setBounds(90, 254, 138, 20);
		panel_4.add(txtpub_name);
		
		JButton btn_edit = new JButton("EDIT");
		
		JButton btn_addnewbook = new JButton("ADD NEW BOOK");
		btn_addnewbook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save_icoe.setVisible(false);
				cross_ico1.setVisible(false);
				ab_e="a";
				btn_edit.setVisible(false);
				btn_addnewbook.setVisible(true);
				b_msg2.setVisible(true);
				b_msg1.setVisible(true);
				
				lblb_id.setVisible(false);
				lblb_name.setVisible(true);
				lbledition.setVisible(true);
				lblPubdate.setVisible(true);
				lblAuthorfname.setVisible(true);
				lblAuthorlname.setVisible(true);
				lblstate.setVisible(true);
				lblTotalCopies.setVisible(false);
				lblupdated.setVisible(false);
				lblCatalogn.setVisible(true);
				lblCreated.setVisible(false);
				
				txtpub_name.setVisible(true);
				txtpub_name.setEditable(true);
				txtpub_name.setText("");
				lblpub_name.setVisible(true);
				
				txtbook_id.setVisible(false);
				txtbook_title.setVisible(true);
				txtbook_title.setText("");
				txtbook_id.setText("");
				txtedition.setVisible(true);
				txtedition.setText("");
				txtedition.setEditable(true);
				txtpub_date.setVisible(true);
				txtpub_date.setEditable(true);
				txtpub_date.setText("");
				txtaf_name.setVisible(true);
				txtaf_name.setEditable(true);
				txtaf_name.setText("");
				txtcategory.setVisible(true);
				txtcategory.setEditable(true);
				txtcategory.setText("");
				txtstate.setVisible(true);
				txtstate.setEditable(true);
				txtstate.setText("");
				txtcatalog.setVisible(true);
				txtcatalog.setEditable(true);
				txtcatalog.setText("");
				txtupdated.setVisible(false);
				txtcopies.setVisible(false);
				txt_created.setVisible(false);
				txtas_name.setVisible(true);
				txtas_name.setEditable(true);
				txtas_name.setText("");
				txtrow.setVisible(true);
				txtrow.setEditable(true);
				txtrow.setText("");
				txtmsg.setVisible(false);
			}
		});
		btn_addnewbook.setBounds(10, 105, 132, 30);
		panel_4.add(btn_addnewbook);
		
		JButton btn_save = new JButton("SAVE/UPDATE");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				update_book_info();
				if(ab_e.compareTo("e")==0)
				{
					txtcopies.setVisible(true);
					lblTotalCopies.setVisible(true);
				}
				else if(ab_e.compareTo("a")==0)
				{
					txtcopies.setVisible(false);
					lblTotalCopies.setVisible(false);
				}
			}
		});
		btn_save.setBounds(10, 140, 132, 30);
		panel_4.add(btn_save);
		
		
		btn_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save_icoe.setVisible(false);
				cross_ico1.setVisible(false);
				ab_e="e";
				btn_addnewbook.setVisible(false);
				b_msg2.setVisible(true);
				b_msg1.setVisible(true);
				
				lblb_id.setVisible(false);
				lblb_name.setVisible(true);
				lbledition.setVisible(true);
				lblPubdate.setVisible(true);
				lblAuthorfname.setVisible(true);
				lblAuthorlname.setVisible(true);
				lblstate.setVisible(true);
				lblTotalCopies.setVisible(true);
				lblupdated.setVisible(false);
				lblCatalogn.setVisible(true);
				lblCreated.setVisible(false);
				
				txtpub_name.setVisible(false);
				lblpub_name.setVisible(false);
				
				txtbook_id.setVisible(false);
				txtbook_title.setVisible(true);
				txtedition.setVisible(true);
				txtedition.setEditable(true);
				txtpub_date.setVisible(true);
				txtpub_date.setEditable(true);
				txtaf_name.setVisible(true);
				txtaf_name.setEditable(false);
				txtcategory.setVisible(true);
				txtcategory.setEditable(true);
				txtstate.setVisible(true);
				txtstate.setEditable(true);
				txtcatalog.setVisible(true);
				txtcatalog.setEditable(true);
				txtupdated.setVisible(false);
				txtcopies.setVisible(true);
				txtcopies.setEditable(true);
				txt_created.setVisible(false);
				txtas_name.setVisible(true);
				txtas_name.setEditable(false);
				txtrow.setVisible(true);
				txtrow.setEditable(true);
				txtmsg.setVisible(false);
				
			}
		});
		btn_edit.setBounds(35, 40, 89, 30);
		panel_4.add(btn_edit);
		
		JButton btn_refresh = new JButton("REFRESH");
		btn_refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				common_refresh(3);
				btn_save.setVisible(true);btn_edit.setVisible(true);
			}
		});
		btn_refresh.setBounds(25, 200, 111, 30);
		btn_refresh.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_4.add(btn_refresh);
		
		b_comboBox = new JComboBox<String>();
		b_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save_icoe.setVisible(false);
				cross_ico1.setVisible(false);
				ab_e="";
				b_msg1.setVisible(false);
				b_msg2.setVisible(false);
				btn_addnewbook.setVisible(true);
				btn_edit.setVisible(true);
				lblb_id.setVisible(true);
				lblb_name.setVisible(true);
				lbledition.setVisible(true);
				lblPubdate.setVisible(true);
				lblAuthorfname.setVisible(true);
				lblAuthorlname.setVisible(true);
				lblstate.setVisible(true);
				lblTotalCopies.setVisible(true);
				lblupdated.setVisible(true);
				lblCatalogn.setVisible(true);
				lblCreated.setVisible(true);
				
				txtpub_name.setVisible(true);
				lblpub_name.setVisible(true);
				
				txtbook_id.setVisible(true);
				txtbook_title.setVisible(true);
				txtedition.setVisible(true);
				txtedition.setEditable(true);
				txtpub_date.setVisible(true);
				txtpub_date.setEditable(true);
				txtaf_name.setVisible(true);
				txtaf_name.setEditable(false);
				txtcategory.setVisible(true);
				txtcategory.setEditable(true);
				txtstate.setVisible(true);
				txtstate.setEditable(true);
				txtcatalog.setVisible(true);
				txtcatalog.setEditable(true);
				txtupdated.setVisible(true);
				txtcopies.setVisible(true);
				txt_created.setVisible(true);
				txtas_name.setVisible(true);
				txtas_name.setEditable(false);
				txtrow.setVisible(true);
				txtrow.setEditable(true);
				txtmsg.setVisible(false);
				
				fill_each_book();
			}
		});
		b_comboBox.setBounds(10, 5, 132, 30);
		panel_4.add(b_comboBox);
		
		
		
		/*JSpinner spinner = new JSpinner();
		spinner.setBackground(SystemColor.control);
		spinner.getToolTipText();
		spinner.setBounds(435, 249, 29, 20);
		panel_4.add(spinner);
		*/
		
		
		
		
		
		
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("transaction", null, panel_3, null);
		
		JButton btnbtransactions = new JButton("Borrowed_T");
		btnbtransactions.setBounds(80, 5, 105, 23);
		btnbtransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				build_borrowt_table(1);
			}
		});
		panel_3.setLayout(null);
		panel_3.add(btnbtransactions);
		
		JButton btnftransaction = new JButton("Fined_T");
		btnftransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				build_finedt_table();
			}
		});
		btnftransaction.setBounds(195, 5, 80, 23);
		panel_3.add(btnftransaction);
		
		JButton btnmtransaction = new JButton("My_T");
		btnmtransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				build_borrowt_table(0);
			}
		});
		btnmtransaction.setBounds(5, 5, 65, 23);
		panel_3.add(btnmtransaction);
		
		txtsearcht = new JTextField();
		txtsearcht.setBounds(321, 6, 86, 22);
		txtsearcht.setText("id/name");
		txtsearcht.setBorder(new MatteBorder(1, 1, 2, 0, (Color) new Color(153, 180, 209)));
		txtsearcht.setBackground(SystemColor.control);
		panel_3.add(txtsearcht);
		txtsearcht.setColumns(10);
		
		JButton btnsearcht = new JButton("search");
		btnsearcht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				search_member_transactions(txtsearcht.getText());
			}
		});
		btnsearcht.setBounds(405, 5, 75, 23);
		panel_3.add(btnsearcht);
		
		JScrollPane scrollPane_t = new JScrollPane();
		scrollPane_t.setBounds(0, 31, 487, 274);
		panel_3.add(scrollPane_t);
		
		table = new JTable();
		scrollPane_t.setViewportView(table);
		
		JLabel lbl_lbname = new JLabel("Hi "+lib_name);
		lbl_lbname.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_lbname.setBounds(55, 11, 110, 28);
		getContentPane().add(lbl_lbname);
		
		JLabel lbl_lbid = new JLabel(librarian_id);
		lbl_lbid.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_lbid.setBounds(464, 13, 28, 14);
		getContentPane().add(lbl_lbid);
		
		JButton btnSignout = new JButton("SignOut");
		btnSignout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Main window = new Main();
				window.frame.setVisible(true);
			}
		});
		btnSignout.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSignout.setBounds(405, 30, 80, 20);
		getContentPane().add(btnSignout);
		
		JLabel user_ico = new JLabel("");
		Image user = new ImageIcon(this.getClass().getResource("/user.png")).getImage();
		Image user1 = user.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH ) ;
		user_ico.setIcon(new ImageIcon(user1));
		user_ico.setBounds(10, 1, 40, 51);
		getContentPane().add(user_ico);
		
		
		
		tabbedPane.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	btnRefresh.setVisible(true);
				if(tabbedPane.getSelectedIndex()==1){
					build_mb_info_table(); 
					
				}
	            System.out.println("Tab: " + tabbedPane.getSelectedIndex());
	        	}
			});
		
		
		fill_combo_mbox();
		fill_b_comboBox();
	}
	
	
	
	
	
	protected void build_finedt_table() {
		// TODO Auto-generated method stub
		String sql="select * from fined_transaction order by created desc";
		
		try {
			Statement stmt=connection.createStatement();
			System.out.println(sql);
			ResultSet rs= stmt.executeQuery(sql);
			DefaultTableModel model = new DefaultTableModel(){

			    @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			};
			String [] column_li={"FTransaction_id","mem_id","BTransaction_id","amount_added","amount_received","fine","created"};
		    int [] width={80,80,90,100,130,80,130};
		    
		    
			model.setColumnIdentifiers(column_li);
			
			table.setBackground(getBackground());
			table.setMinimumSize(new Dimension(lib_scrollPane.getSize()));
			//table.setEnabled(false);
		    table.setModel(model);
		    /*table.addMouseListener(new java.awt.event.MouseAdapter() {
		        @Override
		        public void mouseClicked(java.awt.event.MouseEvent evt) {
		            int row = table.rowAtPoint(evt.getPoint());
		            int col = table.columnAtPoint(evt.getPoint());
		            if (row >= 0 && col >= 0) {
		               System.out.println(row+" "+col);

		            }
		        }
		    });*/
		    //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		    
		    
		    table.setRowHeight(40);
			
		    table.setBackground(getBackground());
		    for(int i=0;i<width.length;i++)
		    {
		    	//System.out.println(i);
		    	table.getColumnModel().getColumn(i).setPreferredWidth(width[i]);
		    }
		    table.setRowSelectionAllowed(true);
		    
			while(rs.next())
			{
				//System.out.println(rs.getString("fined_transaction_id")+rs.getString("member_id"));
				model.addRow(new Object[]{ rs.getString("fined_transaction_id"),rs.getString("member_id"),rs.getString("borrow_transaction_id"),
						rs.getString("amount_added"),rs.getString("amount_received"),rs.getInt("amount_added")-rs.getInt("amount_received"),rs.getString("created")});
				
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in mb_info");
			e.printStackTrace();
		}

		
	}


	protected void search_member_transactions(String stxt) {
		System.out.println("in search mem");
		search_clk1=0;
		// TODO Auto-generated method stub
		//System.out.println(stxt);
		try {
				Statement stmt =connection.createStatement();
				String sql;
				if(stxt.toLowerCase().contains("btech") || stxt.toLowerCase().contains("mtech") ||stxt.toLowerCase().contains("msc")||stxt.toLowerCase().contains("phd")||stxt.toLowerCase().contains("prof"))
				{
					//search by member_id
					//System.out.println("member_id=like_"+stxt);
					sql="select count(*) as count from members where member_id  like '%"+stxt+"%'";
					System.out.println(sql);
					ResultSet rs=stmt.executeQuery(sql);
					rs.next();
					if(rs.getInt("count")>1)
					{
						//table suggest
						System.out.println("count="+rs.getInt("count"));
						sql="select member_id, member_name,email,phone_number,gender from members where member_id  like '%"+stxt+"%'";
						System.out.println(sql);
						rs=stmt.executeQuery(sql);
						
						DefaultTableModel model = new DefaultTableModel(){
						    @Override
						    public boolean isCellEditable(int row, int column) {
						       //all cells false
						       return false;
						    }
						};
						String [] column_li={"member_id","member_name","phone_number","gender","email","Click to Select"};
					    int [] width={90,150,100,50,150 };
					    
					    
						model.setColumnIdentifiers(column_li);
						
						table.setBackground(getBackground());
						table.setMinimumSize(new Dimension(lib_scrollPane.getSize()));
						//table.setEnabled(false);
					    table.setModel(model);
					    
					    //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					    
					    
					    table.setRowHeight(40);
						
					    table.setBackground(getBackground());
					    for(int i=0;i<width.length;i++)
					    {
					    	//System.out.println(i);
					    	table.getColumnModel().getColumn(i).setPreferredWidth(width[i]);
					    }
					    table.setRowSelectionAllowed(true);
						while(rs.next())
						{
							model.addRow(new Object[]{ rs.getString("member_id"),rs.getString("member_name"),
									rs.getString("phone_number"),rs.getString("gender"),rs.getString("email")});
							
						}
						table.addMouseListener(new java.awt.event.MouseAdapter() {
					        @Override
					        public void mouseClicked(java.awt.event.MouseEvent evt) {
					            int row = table.rowAtPoint(evt.getPoint());
					            int col = table.columnAtPoint(evt.getPoint());
					            if (row >= 0 && col >= 0 && search_clk1==0) {
					            	search_clk1=1;
					               System.out.println("in mouse search"+row+" "+col);
					            	String m_id=(String) table.getValueAt(row, 0);
						            System.out.println("member_id="+m_id);
						            each_member_transaction(m_id);
					            }
					            
					        }
					    });
						
						
					}
					else if(rs.getInt("count")==1)
					{
						search_clk1=1;
						//call one fun
						sql="select member_id from members where member_id like '%"+stxt+"%'";
						rs=stmt.executeQuery(sql);
						rs.next();
						each_member_transaction(rs.getString("member_id"));
					}
					else
					{
						System.out.println("enter proper keyword to search");
					}
				}
				else
				{
					//search by member_name;
					System.out.println("member_name=like_"+stxt);
					sql="select count(*) as count from members where member_name  like '%"+stxt+"%'";
					System.out.println(sql);
					ResultSet rs=stmt.executeQuery(sql);
					rs.next();
					if(rs.getInt("count")>1)
					{
						//table suggest
						System.out.println("count="+rs.getInt("count"));
						sql="select member_id, member_name,email,phone_number,gender from members where member_name  like '%"+stxt+"%'";
						System.out.println(sql);
						rs=stmt.executeQuery(sql);
						
						DefaultTableModel model = new DefaultTableModel(){
						    @Override
						    public boolean isCellEditable(int row, int column) {
						       //all cells false
						       return false;
						    }
						};
						String [] column_li={"member_id","member_name","phone_number","gender","email","Click to Select"};
					    int [] width={90,150,100,50,150 };
					    
					    
						model.setColumnIdentifiers(column_li);
						
						table.setBackground(getBackground());
						table.setMinimumSize(new Dimension(lib_scrollPane.getSize()));
						//table.setEnabled(false);
					    table.setModel(model);
					    
					    //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					    
					    
					    table.setRowHeight(40);
						
					    table.setBackground(getBackground());
					    for(int i=0;i<width.length;i++)
					    {
					    	//System.out.println(i);
					    	table.getColumnModel().getColumn(i).setPreferredWidth(width[i]);
					    }
					    table.setRowSelectionAllowed(true);
						while(rs.next())
						{
							model.addRow(new Object[]{ rs.getString("member_id"),rs.getString("member_name"),
									rs.getString("phone_number"),rs.getString("gender"),rs.getString("email")});
							
						}
						table.addMouseListener(new java.awt.event.MouseAdapter() {
					        @Override
					        public void mouseClicked(java.awt.event.MouseEvent evt) {
					            int row = table.rowAtPoint(evt.getPoint());
					            int col = table.columnAtPoint(evt.getPoint());
					            if (row >= 0 && col >= 0 && search_clk1==0) {
					            	search_clk1=1;
					               System.out.println("in mouse search"+row+" "+col);
					            	String m_id=(String) table.getValueAt(row, 0);
						            System.out.println("member_id="+m_id);
						            each_member_transaction(m_id);
					            }
					            
					        }
					    });
						
					
					}
					else if(rs.getInt("count")==1)
					{
						search_clk1=1;
						//call one fun
						sql="select member_id from members where member_name like '%"+stxt+"%'";
						rs=stmt.executeQuery(sql);
						rs.next();
						each_member_transaction(rs.getString("member_id"));
					}
					else
					{
						System.out.println("enter correct keyword");
					}
					
				}
				stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void each_member_transaction(String mem_id) {
		System.out.println("in each member");
		// TODO Auto-generated method stub
		DefaultTableModel model = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		String [] column_li={"column1","column2","column3","column4","column5","column6"};
	    int [] width={150,150,150,150,150};
	    
	    
		model.setColumnIdentifiers(column_li);
		
		table.setBackground(getBackground());
		table.setMinimumSize(new Dimension(lib_scrollPane.getSize()));
		table.setEnabled(false);
	    table.setModel(model);
	    //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    
	    
	    table.setRowHeight(30);
		
	    table.setBackground(getBackground());
	    for(int i=0;i<width.length;i++)
	    {
	    	//System.out.println(i);
	    	table.getColumnModel().getColumn(i).setPreferredWidth(width[i]);
	    }
	    
		try {
				txtsearcht.setText(mem_id);
				Statement stmt=connection.createStatement();
				String sql="select * from members where member_id='"+mem_id+"'";
				System.out.println(sql);
				ResultSet rs=stmt.executeQuery(sql);
				rs.next();
				model.addRow(new Object[]{rs.getString("member_id"),rs.getString("member_name")});
				model.addRow(new Object[]{"","","Borrowed_Transaction_detail","",""});
				model.addRow(new Object[]{"borrow_transaction_id","librarian_id","book_id","borrow_date","return_date","date_due"});
				sql = "select * from borrow_transaction where member_id='"+mem_id+"' and is_returned='N'";
				System.out.println(sql);
				rs=stmt.executeQuery(sql);
				while(rs.next())
				{
					model.addRow(new Object[]{ rs.getString("borrow_transaction_id"),rs.getString("librarian_id"),rs.getString("book_id"),
							rs.getString("borrowed_datetime"),rs.getString("returned_datetime"),rs.getString("date_due") });
					
				}
				model.addRow(new Object[]{"--------------","------------","-------------------","------------","-----------","-----------"});
				model.addRow(new Object[]{"","","fined_Transaction_detail","","",""});
				sql = "select * from fined_transaction where member_id='"+mem_id+"'";
				System.out.println(sql);
				model.addRow(new Object[]{"fined_transaction_id","borrow_transaction_id","amount_added","amount_received","fine","created"});
				rs=stmt.executeQuery(sql);
				int total_fine=0;
				while(rs.next())
				{
					model.addRow(new Object[]{ rs.getString("fined_transaction_id"),rs.getString("borrow_transaction_id"),rs.getString("amount_added"),
							rs.getString("amount_received"),rs.getInt("amount_added")-rs.getInt("amount_received"),rs.getString("created") });
					total_fine+=rs.getInt("amount_added")-rs.getInt("amount_received");
					
				}
				model.addRow(new Object[]{"--------------","------------","-------------------","------------","-----------","-----------"});
				model.addRow(new Object[]{"","","","total_fine=",total_fine,""});
				stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	protected void build_borrowt_table(int k) {
		// TODO Auto-generated method stub
		String sql;
		if(k==0)
			sql="select * from borrow_transaction where librarian_id='"+librarian_id+"' order by borrowed_datetime desc";
		else
			sql="select * from borrow_transaction order by borrowed_datetime desc";
		
		try {
			Statement stmt=connection.createStatement();
			System.out.println(sql);
			ResultSet rs= stmt.executeQuery(sql);
			DefaultTableModel model = new DefaultTableModel(){

			    @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			};
			String [] column_li={"BTransaction_id","Librarian_id","BK_id","mem_id","borrowed_d","returned_d","date_due","is_returned"};
		    int [] width={80,80,90,100,130,130};
		    
		    
			model.setColumnIdentifiers(column_li);
			
			table.setBackground(getBackground());
			table.setMinimumSize(new Dimension(lib_scrollPane.getSize()));
			//table.setEnabled(false);
		    table.setModel(model);
		   /* table.addMouseListener(new java.awt.event.MouseAdapter() {
		        @Override
		        public void mouseClicked(java.awt.event.MouseEvent evt) {
		            int row = table.rowAtPoint(evt.getPoint());
		            int col = table.columnAtPoint(evt.getPoint());
		            if (row >= 0 && col >= 0) {
		               System.out.println(row+" "+col);

		            }
		        }
		    });*/
		    //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		    
		    
		    table.setRowHeight(40);
			
		    table.setBackground(getBackground());
		    for(int i=0;i<width.length;i++)
		    {
		    	//System.out.println(i);
		    	table.getColumnModel().getColumn(i).setPreferredWidth(width[i]);
		    }
		    table.setRowSelectionAllowed(true);
		    
			while(rs.next())
			{
				//System.out.println(rs.getString("borrow_transaction_id")+rs.getString("librarian_id"));
				model.addRow(new Object[]{ rs.getString("borrow_transaction_id"),rs.getString("librarian_id"),rs.getString("book_id"),
						rs.getString("member_id"),rs.getString("borrowed_datetime"),rs.getString("returned_datetime"),rs.getString("date_due"),
						rs.getString("Is_returned") });
				
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in mb_info");
			e.printStackTrace();
		}
	}
	
	
	
	protected void update_book_info() {
		// TODO Auto-generated method stub
		String book_title = txtbook_title.getText().trim();
		String edition = txtedition.getText().trim();
		String pub_date = txtpub_date.getText().trim();
		int copies_available = 0;
		int total_copies = 0;
		String state = txtstate.getText().trim();
		String catalog_number = txtcatalog.getText().trim();
		String row_number = txtrow.getText().trim();
		String updated="sysdate";
		String updated_by = librarian_id;
		
		String book_id = "";//will be generated
		String pub_id = "";//will be generated
		
		String created_by = librarian_id;
		String created="sysdate";//generated by sysdate
		String author_id = "";//if author name exist take that id else generate new
		String first_name = txtaf_name.getText().trim();
		String last_name = txtas_name.getText().trim();
		String pub_name = txtpub_name.getText().trim();
		String category_list = txtcategory.getText().trim();
		String category_id = "";//will be generated;
		save_icoe.setVisible(false);
		cross_ico1.setVisible(false);
		if(ab_e.compareTo("")!=0 && (state.compareTo("")==0 || first_name.compareTo("")==0 || row_number.compareTo("")==0||pub_name.compareTo("")==0 || edition.compareTo("")==0 || pub_date.compareTo("")==0 || book_title.compareTo("")==0 || catalog_number.compareTo("")==0 || category_list.compareTo("")==0))
		{
			save_icoe.setVisible(false);
			cross_ico1.setVisible(true);
			txtmsg.setVisible(true);
			txtmsg.setText("partially filled info..");
		}
		else
		{
			if(ab_e.compareTo("a")==0)
			{
				System.out.println("perform add");
				
				try
				{
					int op_insert=0;
					Statement stmt = connection.createStatement();
				
					//1.insert operation for author
					try
					{
						
						System.out.println("insertion in author started");
						String sqlc="select count(*) as count from author where first_name='"+first_name+"' and last_name='"+last_name+"'";
						System.out.println(sqlc);
						ResultSet rs=stmt.executeQuery(sqlc);
						rs.next();
						int count=rs.getInt("count");
						System.out.println("count="+count);
						if(count>0)
						{
							//no need to insert author as it is already exist there
							//get auhtor_id instead;
							sqlc="select author_id from author where first_name='"+first_name+"' and last_name='"+last_name+"' ";
							System.out.println(sqlc);
							rs=stmt.executeQuery(sqlc);
							rs.next();
							author_id=rs.getString("author_id");
						}
						else
						{
							//generate new author_id and insert in author
							sqlc="select count(*) as count from author";
							rs=stmt.executeQuery(sqlc);
							rs.next();
							author_id="ATHR"+(rs.getInt("count")+1);
							sqlc="insert into author values ('"+author_id+"' , '"+first_name+"' , '"+last_name+"')";
							System.out.println("=>"+sqlc);
							int rs1 = stmt.executeUpdate(sqlc);//to insert into author table;
							System.out.println("author table affected =>"+rs1);
						}
						op_insert+=1;
					}
					catch(Exception e)
					{
						System.out.println("Error in inserting into author\n \t"+e);
					}
					//2.insert operation for publisher
					try
					{
						System.out.println("insertion in publisher started");
						String sqlc="select count(*) as count from publisher where pub_name='"+pub_name+"'";
						System.out.println(sqlc);
						ResultSet rs=stmt.executeQuery(sqlc);
						rs.next();
						int count=rs.getInt("count");
						System.out.println("count of same publisher="+count);
						if(count>0)
						{
							//no need to insert in publisher as it is already exist there
							//instead get pub_id
							rs=stmt.executeQuery("select pub_id from publisher where pub_name='"+pub_name+"'");
							rs.next();
							pub_id=rs.getString("pub_id");
							System.out.println("got pub_id"+pub_id);
						}
						else
						{
							//generate ne pub_id and insert it in publisher
							sqlc="select count(*) as count from publisher";
							rs=stmt.executeQuery(sqlc);
							rs.next();
							pub_id="PB"+(rs.getInt("count")+1);
							sqlc="insert into publisher values ('"+pub_id+"' , '"+pub_name+"')";
							System.out.println("=>"+sqlc);
							int rs1 = stmt.executeUpdate(sqlc);//to insert into publisher table;
							System.out.println("publisher table affected =>"+rs1);
						}
						op_insert+=1;
					}
					catch(Exception e)
					{
						System.out.println("Error in inserting into publisher\n \t"+e);
					}
					//3.insert operation for categories
					String catlist[]=category_list.trim().split(",");
					String cat_id[]=new String[catlist.length];
					try
					{
						
						int i=0;
						for(String s:catlist)
						{
							//System.out.println(s.trim());
							System.out.println("insertion in categories started");
							String sqlc="select count(*) as count from categories where category_name='"+s.trim()+"'";
							System.out.println(sqlc);
							ResultSet rs=stmt.executeQuery(sqlc);
							rs.next();
							int count=rs.getInt("count");
							System.out.println("count="+count);
							if(count>0)
							{
								//no need to insert in categories as it is already exist there
								sqlc="select category_id from categories where category_name='"+s.trim()+"'";
								rs=stmt.executeQuery(sqlc);
								rs.next();
								cat_id[i]=rs.getString("category_id");
							}
							else
							{
								sqlc="select count(*) as count from categories";
								rs=stmt.executeQuery(sqlc);
								rs.next();
								count = rs.getInt("count")+1;
								cat_id[i]="cat"+count;
								sqlc="insert into categories values ('"+cat_id[i]+"' , '"+s.trim()+"')";
								System.out.println("=>"+sqlc);
								int rs1 = stmt.executeUpdate(sqlc);//to insert into categories table;
								System.out.println("categories table affected =>"+rs1);
							}
							//System.out.println(cat_id[i]+" - "+s.trim());
							i++;
						}
						op_insert+=1;
					}
					catch(Exception e)
					{
						System.out.println("Error in inserting into categories\n \t"+e);
					}
					//4.insert operation for books
					try
					{
						System.out.println("insertion in books started");
						String sqlc="select count(*) as count from books";
						System.out.println(sqlc);
						ResultSet rs=stmt.executeQuery(sqlc);
						rs.next();
						book_id="BK"+(rs.getInt("count")+1);
						//System.out.println("generated book_id="+book_id);
						copies_available=1;
						total_copies=1;
						sqlc="insert into books values ('"+book_id+"' , '"+pub_id+"' , '"+book_title+"' , "+edition+" , to_date('"+pub_date+"', 'dd/mm/yyyy') , "+copies_available+" , "+total_copies+" , '"+state+"' , '"+catalog_number+"' , '"+row_number+"' , '"+created_by+"' , '"+updated_by+"' , "+created+" , "+updated+")";
						System.out.println("=>"+sqlc);
						int rs1 = stmt.executeUpdate(sqlc);//to insert into books table;
						System.out.println(" books table affected =>"+rs1);
						op_insert+=1;
						
					}
					catch(Exception e)
					{
						System.out.println("Error in inserting into books\n \t"+e);
					}
					//5.insert operation for books_by_category
					try
					{
						//catlist=category_list.trim().split(",");
						//cat_id=new String[catlist.length];
						System.out.println("insertion in books_by_category started");
						for(String c:cat_id)
						{
							//System.out.println("category=_"+c+"_");
							String sqlc="select count(*) as count from books_by_category where category_id='"+c+"' and book_id = '"+book_id+"'";
							System.out.println(sqlc);
							ResultSet rs=stmt.executeQuery(sqlc);
							rs.next();
							int count=rs.getInt("count");
							System.out.println("count="+count);
							if(count>0)
							{
								//no need to insert in books_by_category as it is already exist there
								
							}
							else
							{
								sqlc="insert into books_by_category values ('"+c+"' , '"+book_id+"')";
								System.out.println("=>"+sqlc);
								int rs1 = stmt.executeUpdate(sqlc);//to insert into books_by_category table;
								System.out.println("books_ by category affected =>"+rs1);
							}
						}
						op_insert+=1;
					}
					catch(Exception e)
					{
						System.out.println("Error in inserting into books_by_category\n \t"+e);
					}
					//6.insert operation for books_by_author
					try
					{
						System.out.println("insertion in books_by_author started");
						String sqlc="select count(*) as count from books_by_author where author_id='"+author_id+"' and book_id = '"+book_id+"'";
						System.out.println(sqlc);
						ResultSet rs=stmt.executeQuery(sqlc);
						rs.next();
						int count=rs.getInt("count");
						System.out.println("count="+count);
						if(count>0)
						{
							//no need to insert in books_by_category as it is already exist there
							
						}
						else
						{
							sqlc="insert into books_by_author values ('"+author_id+"' , '"+book_id+"')";
							System.out.println("=>"+sqlc);
							int rs1 = stmt.executeUpdate(sqlc);//to insert into books_by_category table;
							System.out.println(" books_by author affected =>"+rs1);
						}
						op_insert+=1;
					}
					catch(Exception e)
					{
						System.out.println("Error in inserting into books_by_category\n \t"+e);
					}
					
					System.out.println("\tinserted Successfully");
					
					
					
					if(op_insert==6)
					{
						save_icoe.setVisible(true);
						cross_ico1.setVisible(false);
						txtmsg.setVisible(true);
						txtmsg.setText("successfully added...");
					}
					
					//*/
					stmt.close();
				}
				catch(Exception e)
				{
					System.out.println("error in making connenction in adding books "+e);
				}
			}
			else if(ab_e.compareTo("e")==0)
			{
				book_id=txtbook_id.getText();
				
				
				System.out.println("perform edit");
				txtcopies.setVisible(true);
				txtbook_id.setVisible(false);
				txtbook_title.setVisible(true);
				txtedition.setVisible(true);
				txtedition.setEditable(true);
				txtpub_date.setVisible(true);
				txtpub_date.setEditable(false);
				txtaf_name.setVisible(true);
				txtcategory.setVisible(true);
				txtcategory.setEditable(true);
				txtstate.setVisible(true);
				txtstate.setEditable(true);
				txtcatalog.setVisible(true);
				txtcatalog.setEditable(true);
				txtupdated.setVisible(false);
				txt_created.setVisible(false);
				txtas_name.setVisible(true);
				txtrow.setVisible(true);
				txtrow.setEditable(true);
				txtmsg.setVisible(false);
				
				try{
					//1.update books
					int affected=0;
					String copies[]=((String)txtcopies.getText()).trim().split("/");
					copies_available=Integer.parseInt(copies[1].trim());
					total_copies=Integer.parseInt(copies[0].trim());
					Statement stmt=connection.createStatement();
					String sql="update books set updated=sysdate , updated_by='"+updated_by+"' , copies_available="+copies_available+" , total_copies="+total_copies+" , book_title='"+book_title+"', pub_date=to_date('"+pub_date+"','dd/mm/yyyy') , edition="+edition+" , state='"+state+"' ,catalog_number='"+catalog_number+"', row_number='"+row_number+"'where book_id='"+book_id+"'";
					System.out.println(sql);
					affected=stmt.executeUpdate(sql);
					System.out.println("affected books row="+affected);
					//2.update categories;
					String cat[]=txtcategory.getText().trim().split(",");
					sql="delete from books_by_category where book_id='"+book_id+"'";
					System.out.println(sql);
					affected=stmt.executeUpdate(sql);
					System.out.println("deleted books_by_category row="+affected);
					System.out.println(txtcategory.getText()+"     l="+cat.length);
					for(String c:cat)
					{
						//insert detail
						sql="select count(*) as count from categories where category_name='"+c.trim()+"'";
						System.out.println(sql);
						ResultSet rs1 =stmt.executeQuery(sql);
						rs1.next();
						int count=rs1.getInt("count");
						System.out.println(count);
						if(count<=0)
						{
							sql="select count(*) as count from categories";
							ResultSet rs2=connection.createStatement().executeQuery(sql);
							rs2.next();
							count = rs2.getInt("count");
							System.out.println(sql);
							sql="insert into categories values('cat"+(count+1)+"' ,'"+c+"')";
							System.out.println(sql);
							affected=connection.createStatement().executeUpdate(sql);
							System.out.println(""+affected);
						}
						
						sql="select category_id from categories where category_name='"+c.trim()+"'";
						rs1=stmt.executeQuery(sql);
						rs1.next();
						
						sql="insert into books_by_category values('"+rs1.getString("category_id")+"' ,'"+txtbook_id.getText()+"')";
						System.out.println(sql);
						affected=stmt.executeUpdate(sql);
						System.out.println("affected books_by_category ="+affected);
					}
					save_icoe.setVisible(true);
					cross_ico1.setVisible(false);
					txtmsg.setVisible(true);
					txtmsg.setText("successfully edited.........");
					stmt.close();
				}
				catch(Exception e)
				{
					System.out.println("error in updating book info = "+e);
				}
			}
			else
			{
				save_icoe.setVisible(false);
				cross_ico1.setVisible(true);
				System.out.println("do add/ edit to save");
				txtmsg.setVisible(true);
				txtmsg.setText("do add/ edit to save");
			}
		}
		
	}
	protected void common_refresh(int i) {
		// TODO Auto-generated method stub
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispose();
		String []str={i+"",librarian_id,password};
		main(str);
		
		
	}
	protected void fill_b_comboBox() {
		// TODO Auto-generated method stub
		String sql="select book_id from books";
		try
		{
			Statement stmt1 = connection.createStatement();
			ResultSet rs= stmt1.executeQuery(sql);
			while(rs.next())
			{
				//System.out.println(rs.getString("book_id"));
				b_comboBox.addItem(rs.getString("book_id"));
			}
			stmt1.close();
		}
		catch(Exception e)
		{
			System.out.println("error in filling combo"+e);
		}
	}
	protected void build_book_info_table() {
		load1=1;
		String sql="select * from books";
		try {
			Statement stmt1=connection.createStatement();
			ResultSet rs= stmt1.executeQuery(sql);
			DefaultTableModel model = new DefaultTableModel(){
			    @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			};
			String [] column_li={"book_id","pub_id","book_title","edition","copies_available","total_copies","pub_date","state","catalog_number","row_number","created_by","created","updated_by","updated","author_id","author_name","pub_name","category_id","category_name"};
		    int [] width={75,80,90,100,43,140,35,62,67,120,120 };
		    
		    
			model.setColumnIdentifiers(column_li);
			
			mb_info_t.setBackground(getBackground());
			mb_info_t.setMinimumSize(new Dimension(lib_scrollPane.getSize()));
			//mb_info_t.setEnabled(false);
		    mb_info_t.setModel(model);
		    
		  //mb_info_t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		    
		    mb_info_t.setRowHeight(40);
			
		    mb_info_t.setBackground(getBackground());
		    
		    for(int i=0;i<width.length;i++)
		    {
		    	//System.out.println(i);
		    	mb_info_t.getColumnModel().getColumn(i).setPreferredWidth(width[i]);
		    }
		    mb_info_t.setRowSelectionAllowed(true);
			while(rs.next())
			{
				//System.out.println(rs.getRow()+"_"+rs.getString("book_id"));
				String book_id = rs.getString("book_id");
			    String pub_id = rs.getString("pub_id");
			    String book_title = rs.getString("book_title");
			    String edition = rs.getString("edition");
			    
			    String copies_available = rs.getString("copies_available");
			    String total_copies = rs.getString("total_copies");
			    
			    String pub_date = rs.getString("pub_date");
			    String state = rs.getString("state");
			    String catalog_number = rs.getString("catalog_number");
			    String row_number = rs.getString("row_number");
			    String created_by = rs.getString("created_by");
			    String created = rs.getString("created");
			    String updated_by = rs.getString("updated_by");
			    String updated = rs.getString("updated");
			    
			    Statement stmt2= connection.createStatement();
			    ResultSet rs1 = stmt2.executeQuery("select author_id from books_by_author where book_id = '"+book_id+"'");
			    rs1.next();
			    String author_id = rs1.getString("author_id");
			    
			    rs1 = stmt2.executeQuery("select first_name||' '||last_name as author_name from author where author_id = '"+author_id+"'");
			    rs1.next();
			    String author_name = rs1.getString("author_name");
			    
			    rs1 = stmt2.executeQuery("select pub_name from publisher where pub_id = '"+pub_id+"'");
			    rs1.next();
			    String pub_name = rs1.getString("pub_name");
			    
			    rs1 = stmt2.executeQuery("select count(*) as count from books_by_category where book_id = '"+book_id+"'");
			    rs1.next();
			    int tot=rs1.getInt("count");
			    String catlist[]=new String[tot];
			    int k=0;
			    String category_id="";
			    String category_name="";
			    rs1 = stmt2.executeQuery("select category_id from books_by_category where book_id = '"+book_id+"'");
			    while(rs1.next()){
			    	catlist[k]=rs1.getString("category_id");
			    	category_id=category_id+catlist[k]+" ,";
			    	
			    	k++;
			    }
				//System.out.println(book_id+","+pub_id+","+book_title+","+edition+","+copies_available+","+total_copies+","+pub_date+","+state+","+catalog_number+","+row_number+","+created_by+","+created+","+updated_by+","+updated+","+author_id+" , "+author_name+","+pub_name+","+category_id+","+category_name);
			    
			    for(String c:catlist){
			    	
			    	try{
			    		
			    		rs1 = stmt2.executeQuery("select category_name from categories where category_id = '"+c+"'");
					    rs1.next();
					    category_name=category_name+rs1.getString("category_name")+" ,";
					    
			    	}
			    	catch(Exception e)
			    	{
			    		
			    		System.out.println("eeror in catlist fetch "+e);
			    	}
			    	
			    }
			  //book_id,pub_id,book_title,edition,copies_available,total_copies,pub_date,state,catalog_number,row_number,created_by,created,updated_by,updated,author_name,pub_name,category_id,category_name
				//System.out.println(book_id+","+pub_id+","+book_title+","+edition+","+copies_available+","+total_copies+","+pub_date+","+state+","+catalog_number+","+row_number+","+created_by+","+created+","+updated_by+","+updated+","+author_name+","+pub_name+","+category_id+","+category_name);
			    model.addRow(new Object[]{ book_id,pub_id,book_title,edition,copies_available,total_copies,pub_date,state,catalog_number,row_number,created_by,created,updated_by,updated,author_id,author_name,pub_name,category_id,category_name });
				stmt2.close();
			}
			mb_info_t.addMouseListener(new java.awt.event.MouseAdapter() {
		        @Override
		        public void mouseClicked(java.awt.event.MouseEvent evt) {
		            int row = mb_info_t.rowAtPoint(evt.getPoint());
		            int col = mb_info_t.columnAtPoint(evt.getPoint());
		            if (row >= 0 && col >= 0 && load1==1) {
		               //System.out.println(row+" "+col);
		               //System.out.println(mb_info_t.getValueAt(row, 0) );
		               tabbedPane.setSelectedIndex(3);
		               b_comboBox.setSelectedItem(mb_info_t.getValueAt(row, 0));
		            }
		        }
		    	});
			stmt1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	protected void update_member_table(String a_e2) {
		try{
			Statement esc=connection.createStatement();
			String name=txtmembername.getText().trim();
			String typ=txtmembertype.getText().trim();
			String mail=txtemail.getText().trim();
			String phone=txtphone.getText().trim();
			String active=txtactive.getText().trim();
			String gender=txtgender.getText().trim();
			
			String sql=name+","+typ+","+mail+"," +phone+","+active+","+gender+","+librarian_id;
			
			System.out.println(sql);
			if(a_e2.compareTo("a")==0)
			{
				String pass=passwordField.getText().trim();
				if(name.compareTo("")==0 || typ.compareTo("")==0 || mail.compareTo("")==0 || phone.compareTo("")==0 || active.compareTo("")==0 || gender.compareTo("")==0 || pass.compareTo("")==0)
				{
					System.out.println("fill all field");
					error_ico.setVisible(true);
					save_ico.setVisible(false);
					txtFillAppropriateValue.setVisible(true);
					txtFillAppropriateValue.setText("enter the correct data");
				}
				else
				{
					
					int id=0;
					String year="";
					
					try {
						ResultSet rs=esc.executeQuery("select extract(year from sysdate) as year from dual");
						rs.next();
						year=rs.getString("year").trim();
						String[] s=year.split("");
						year=s[s.length-2]+s[s.length-1];
						
						sql="select count(*) as count from members where member_id like '"+year+typ.toLowerCase()+"_%'";
						rs=esc.executeQuery(sql);
						rs.next();
						id=Integer.parseInt(rs.getString("count"))+1;
						//System.out.println(id+","+year);
						sql="insert into members values('"+year+typ+id+""+"' , '"+typ+"' , '"+name+"' , '"+phone+"' , '"+gender+"' , '"+mail+"' , '"+active+"' , '"+librarian_id+"' , '"+librarian_id+"' , sysdate, sysdate,'"+pass+"')";
						System.out.println(sql);
						int aff=esc.executeUpdate(sql);
						System.out.println("affected new row in member="+aff);
						
						String expiry="";
						//select to_char(sysdate,'dd/mm/yyyy')  from dual;
						rs=esc.executeQuery("select to_char(sysdate,'dd/month/yyyy') as d from dual");
						rs.next();
						expiry=rs.getString("d");
						int duration=0;
						if(typ.toLowerCase().compareTo("btech")==0)
						{
							duration=4;
						}
						else if(typ.toLowerCase().compareTo("mtech")==0 || typ.compareTo("msc")==0 )
						{
							duration=2;
						}
						else if(typ.toLowerCase().compareTo("prof")==0 || typ.compareTo("phd")==0)
						{
							duration=5;
						}
						//02/11/2015
						String st[]=expiry.split("[/]");
						int dump=Integer.parseInt(st[2].trim())+duration;
						expiry=st[0].trim()+"/"+st[1].trim()+"/"+dump;
						sql="insert into membership values( '"+librarian_id+"' , '"+year+typ+id+"' , '"+expiry+"' )";
						System.out.println(sql);
						aff=esc.executeUpdate(sql);
						System.out.println("affected membership row="+aff);
						error_ico.setVisible(false);
						save_ico.setVisible(true);
						txtFillAppropriateValue.setVisible(true);
						txtFillAppropriateValue.setText("saved succesfully Refresh....");
						//mailer
						String mailmsg="a |"+name+"|"+year+typ+id+"|"+librarian_id+"|"+mail;
						String mailer[]=mailmsg.split("[|]");
						emailing.main(mailer);
						System.out.println("called");
						a_e="";
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else if(a_e.compareTo("e")==0)
			{
				
				sql="update members set member_name='"+name+"' , member_type='"+typ+"' ,email= '"+mail+"' , phone_number='"+phone+"' , active='"+active+"' , gender='"+gender+"' ,updated_by='"+librarian_id+"', updated= sysdate where member_id='"+(String) comboBox.getSelectedItem()+"'";
				System.out.println("here\n"+sql);
				try {
					
					int i=esc.executeUpdate(sql);
					if(i>0)
					{
						System.out.println("updated row ="+i);
						error_ico.setVisible(false);
						save_ico.setVisible(true);
						txtFillAppropriateValue.setVisible(true);
						txtFillAppropriateValue.setText("saved succesfully Refresh....");
						String mailmsg="e |"+name+"|"+(String) comboBox.getSelectedItem()+"|"+librarian_id+"|"+mail;
						String mailer[]=mailmsg.split("[|]");
						//for(String s:mailer)
							//System.out.println("mail_"+s);
						emailing.main(mailer);
						a_e="";
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			esc.close();
		}
		catch(Exception e)
		{
			System.out.println("connection prob in update mem"+e);
		}
		
		// TODO Auto-generated method stub
		
	}
	protected void fill_each_book() {
		String sql="select book_id, to_char(pub_date,'dd/mm/yyyy') as pub_date, pub_id,book_title,edition,copies_available,total_copies,state,catalog_number,row_number,created_by,updated_by,to_char(updated,'dd/mm/yyyy') as updated,to_char(created,'dd/mm/yyyy') as created from books where book_id='"+(String)b_comboBox.getSelectedItem()+"'";
		//System.out.println(sql);
		try {
			Statement stmt =connection.createStatement();
			ResultSet rs= stmt.executeQuery(sql);
			
			while(rs.next())
			{
				String book_id = rs.getString("book_id");
			    String pub_id = rs.getString("pub_id");
			    String book_title = rs.getString("book_title");
			    String edition = rs.getString("edition");
			    //String pub_name="txtpub_name";
			    String copies_available = rs.getString("copies_available");
			    String total_copies = rs.getString("total_copies");
			    
			    String pub_date = rs.getString("pub_date");
			    String state = rs.getString("state");
			    String catalog_number = rs.getString("catalog_number");
			    String row_number = rs.getString("row_number");
			    String created_by = rs.getString("created_by");
			    String created = rs.getString("created");
			    String updated_by = rs.getString("updated_by");
			    String updated = rs.getString("updated");
			    
			    Statement stmt1=connection.createStatement();
			    ResultSet rs1 = stmt1.executeQuery("select author_id from books_by_author where book_id = '"+book_id+"'");
			    
			    rs1.next();
			    String author_id = rs1.getString("author_id");
			    
			    rs1 = stmt1.executeQuery("select first_name||' '||last_name as author_name from author where author_id = '"+author_id+"'");
			    rs1.next();
			    String author_name = rs1.getString("author_name");
			    
			    rs1 = stmt1.executeQuery("select pub_name from publisher where pub_id = '"+pub_id+"'");
			    rs1.next();
			    String pub_name = rs1.getString("pub_name");
			    
			    rs1 = stmt1.executeQuery("select count(*) as count from books_by_category where book_id = '"+book_id+"'");
			    rs1.next();
			    int tot=rs1.getInt("count");
			    String catlist[]=new String[tot];
			    int k=0;
			    String category_id="";
			    String category_name="";
			    rs1 = stmt1.executeQuery("select category_id from books_by_category where book_id = '"+book_id+"'");
			    while(rs1.next()){
			    	catlist[k]=rs1.getString("category_id");
			    	category_id=category_id+catlist[k]+" ,";
			    	k++;
			    }
				//System.out.println(book_id+","+pub_id+","+book_title+","+edition+","+copies_available+","+total_copies+","+pub_date+","+state+","+catalog_number+","+row_number+","+created_by+","+created+","+updated_by+","+updated+","+author_id+" , "+author_name+","+pub_name+","+category_id+","+category_name);

			    for(String c:catlist){
			    	try{
			    		rs1 = stmt1.executeQuery("select category_name from categories where category_id = '"+c+"'");
					    rs1.next();
					    category_name+=rs1.getString("category_name")+" ,";
			    	}
			    	catch(Exception e)
			    	{
			    		System.out.println("eeror in filling book fetch "+e);
			    	}
			    	
			    }
			  //book_id,pub_id,book_title,edition,copies_available,total_copies,pub_date,state,catalog_number,row_number,created_by,created,updated_by,updated,author_name,pub_name,category_id,category_name
				//System.out.println(book_id+","+pub_id+","+book_title+","+edition+","+copies_available+","+total_copies+","+pub_date+","+state+","+catalog_number+","+row_number+","+created_by+","+created+","+updated_by+","+updated+","+author_name+","+pub_name+","+category_id+","+category_name);
				txtrow.setText(row_number);
				txtrow.setHorizontalAlignment(JTextField.CENTER);
				txtpub_name.setText(pub_name);
				txtpub_name.setHorizontalAlignment(JTextField.CENTER);
				txtbook_id.setText(book_id);
				txtbook_id.setHorizontalAlignment(JTextField.CENTER);
				txtbook_title.setText(book_title);
				txtbook_title.setHorizontalAlignment(JTextField.CENTER);
				txtcategory.setText(category_name);
				txtcategory.setHorizontalAlignment(JTextField.CENTER);
				txtstate.setText(state);
				txtstate.setHorizontalAlignment(JTextField.CENTER);
				txtcatalog.setText(catalog_number); 
				txtcatalog.setHorizontalAlignment(JTextField.CENTER);
				//String dummy[]=updated.split("\\s+");
				txtupdated.setText(updated_by+","+updated);
				txtupdated.setHorizontalAlignment(JTextField.CENTER);
				//dummy=created.split("\\s+");
				txt_created.setText(created_by+","+created);
				txt_created.setHorizontalAlignment(JTextField.CENTER);
				//dummy=pub_date.split("\\s+");
				txtpub_date.setText(pub_date);
				txtpub_date.setHorizontalAlignment(JTextField.CENTER);
				txtcopies.setText(copies_available+"/"+total_copies);
				txtcopies.setHorizontalAlignment(JTextField.CENTER);
				txtedition.setText(edition);
				txtedition.setHorizontalAlignment(JTextField.CENTER);
				txtmsg.setVisible(false);
				rs1 = stmt1.executeQuery("select first_name,last_name from author where author_id = '"+author_id+"'");
				rs1.next();
				txtaf_name.setText(rs1.getString("first_name"));
				txtas_name.setText(rs1.getString("last_name"));
				txtas_name.setHorizontalAlignment(JTextField.CENTER);
				txtaf_name.setHorizontalAlignment(JTextField.CENTER);
				
				rs1.close();
				stmt1.close();
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("error in combobox"+e);
			e.printStackTrace();
		}
		
	}
	protected void fill_each_mem() {
		String sql="select m.member_name,m.member_type,m.gender,m.phone_number,m.email,m.active,m.created_by,m.created, to_char(p.membership_expiry_date,'dd/month/yyyy') as membership_expiry_date from members m , membership p where m.member_id='"+(String) comboBox.getSelectedItem()+"' and p.member_id='"+(String) comboBox.getSelectedItem()+"'";
		
		try {
			Statement stmt =connection.createStatement();
			ResultSet rs= stmt.executeQuery(sql);
			
			rs.next();
			txtmemberid.setText((String) comboBox.getSelectedItem());
			txtmembername.setText(rs.getString("member_name"));
			txtmembertype.setText(rs.getString("member_type"));
			txtemail.setText(rs.getString("email"));
			txtphone.setText(rs.getString("phone_number"));
			txtactive.setText(rs.getString("active"));
			txtexpire.setText(rs.getString("membership_expiry_date").replaceAll("\\s+", " "));
			String str[]=rs.getString("created").split("\\s+");
			txtcreated.setText(rs.getString("created_by")+"/"+str[0]);
			txtgender.setText(rs.getString("gender"));
			sql="select count(*) as count from borrow_transaction where member_id='"+(String) comboBox.getSelectedItem()+"' and is_returned='N'";
			rs=stmt.executeQuery(sql);
			rs.next();
			txtbooks.setText(rs.getString("count"));
			sql="select (sum(amount_added)-sum(amount_received)) as fine from fined_transaction where member_id='"+(String) comboBox.getSelectedItem()+"'";
			rs=stmt.executeQuery(sql);
			rs.next();
			if(rs.getString("fine")==null)
			txtfine.setText("0");
			else
				txtfine.setText(rs.getString("fine"));
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in combobox"+e);
			e.printStackTrace();
		}
		
	}
	protected void build_mb_info_table() {
		// TODO Auto-generated method stub
		load1=0;
		String sql="select * from members";
		try {
			Statement stmt1=connection.createStatement();
			ResultSet rs= stmt1.executeQuery(sql);
			DefaultTableModel model = new DefaultTableModel(){
			    @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			};
			String [] column_li={"member_id","member_type","member_name","phone_number","gender","email","active","created_by","updated_by","created","updated"};
		    int [] width={75,80,90,100,43,140,35,62,67,120,120 };
		    
		    
			model.setColumnIdentifiers(column_li);
			
			mb_info_t.setBackground(getBackground());
			mb_info_t.setMinimumSize(new Dimension(lib_scrollPane.getSize()));
			//mb_info_t.setEnabled(false);
		    mb_info_t.setModel(model);
		    mb_info_t.addMouseListener(new java.awt.event.MouseAdapter() {
		        @Override
		        public void mouseClicked(java.awt.event.MouseEvent evt) {
		            int row = mb_info_t.rowAtPoint(evt.getPoint());
		            int col = mb_info_t.columnAtPoint(evt.getPoint());
		            if (row >= 0 && col >= 0 && load1==0) {
		            	
		                System.out.println("in mouse search"+row+" "+col);
		                
			            System.out.println(mb_info_t.getValueAt(row, 0));
			            tabbedPane.setSelectedIndex(2);
			            comboBox.setSelectedItem(mb_info_t.getValueAt(row, 0));
			           
		            }
		            
		        }
		    });
		    mb_info_t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			
		    mb_info_t.setRowHeight(40);
			
		    mb_info_t.setBackground(getBackground());
		    for(int i=0;i<width.length;i++)
		    {
		    	//System.out.println(i);
		    	mb_info_t.getColumnModel().getColumn(i).setPreferredWidth(width[i]);
		    }
		    
			while(rs.next())
			{
				model.addRow(new Object[]{ rs.getString("member_id"),rs.getString("member_type"),rs.getString("member_name"),
						rs.getString("phone_number"),rs.getString("gender"),rs.getString("email"),rs.getString("active"),
						rs.getString("created_by"),rs.getString("updated_by"),rs.getString("created"),rs.getString("updated"),
						 });
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in mb_info");
			e.printStackTrace();
		}
	}
	private void set_value()
	{
		// TODO Auto-generated method stub
		System.out.println("set"+librarian_id);
		Statement set=null;
		try {
			System.out.println("set1"+librarian_id);
			set = connection.createStatement();
			//System.out.println("set2"+librarian_id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("error in set conn"+e1);
		}
		
		try
		{
			String sql_info="select * from librarian where librarian_id='"+librarian_id+"'";
			//System.out.println(sql_info);
			
			ResultSet info=set.executeQuery(sql_info);
			while(info.next())
			{
				lib_name=info.getString("name");
			}
			info.close();
		}
		catch(Exception e)
		{
			System.out.println("error in set"+e);
		}

	}
	public void fill_combo_ubox()
	{
		String []str={"NAME","PASSWORD","ACTIVE","INACTIVE_REASON"};
		for(String s:str)
		u_comboBox.addItem(s);
	}
	
	public void fill_combo_mbox()
	{
		String sql="select member_id from members";
		try
		{
			ResultSet rs= connection.createStatement().executeQuery(sql);
			while(rs.next())
			{
				//System.out.println(rs.getString("member_id"));
				comboBox.addItem(rs.getString("member_id"));
			}
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("error in filling combo"+e);
		}
		
	}
	public void u_combo_info()
	{
		String sql="select "+(String) u_comboBox.getSelectedItem()+" from librarian where librarian_id='"+librarian_id+"'";
		 System.out.println(sql);
		 lblcbox.setText((String) u_comboBox.getSelectedItem());
		 try {
			 Statement stmt1=connection.createStatement();
			ResultSet rs= stmt1.executeQuery(sql);
			
			rs.next();
			
			edit_info.setText(rs.getString((String) u_comboBox.getSelectedItem()));
			
			stmt1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in u_comboBox");
			e.printStackTrace();
		}
		
		 if(u_comboBox.getSelectedItem().toString().toLowerCase().compareTo("password")==0)
		 {
			 lib_info_table.setValueAt(edit_info.getText(), 2, 1);
		 }
		 else
		 {
			 lib_info_table.setValueAt(pass1.replaceAll("[a-zA-Z0-9]", "*"), 2, 1);
		 }
		 
	}
	public void get_lib_info_table()
	{
		lib_info_table = new JTable();
		lib_info_table.setBackground(getBackground());
		lib_info_table.setMinimumSize(new Dimension(lib_scrollPane.getSize()));
		//lib_info_table.setEnabled(false);
		
		DefaultTableModel model = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		String [] column_li={"attributes","value"};
	    model.setColumnIdentifiers(column_li);
		lib_info_table.setModel(model);
		
		lib_info_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		
		lib_info_table.setRowHeight(20);
		
		lib_info_table.getColumnModel().getColumn(0).setPreferredWidth(100);
		lib_info_table.getColumnModel().getColumn(1).setPreferredWidth(125);
		
		try
		{
			String sql_info="select * from librarian where librarian_id='"+librarian_id+"'";
			//System.out.println(sql_info);
			ResultSet info=connection.createStatement().executeQuery(sql_info);
			info.next();
				//System.out.println(lib_name);
				model.addRow(new Object[]{"librarian_id",info.getString("librarian_id") });
				model.addRow(new Object[]{"name",info.getString("name") });
				model.addRow(new Object[]{"password",info.getString("password").replaceAll("[a-zA-Z0-9]", "*") });
				model.addRow(new Object[]{"created_by",info.getString("created_by") });
				model.addRow(new Object[]{"updated_by",info.getString("updated_by") });
				model.addRow(new Object[]{"created",info.getString("created") });
				model.addRow(new Object[]{"updated",info.getString("updated") });
				model.addRow(new Object[]{"active",info.getString("active") });
				model.addRow(new Object[]{"inactive_reason",info.getString("inactive_reason") });
				pass1=info.getString("password");
			
			lib_info_table.addMouseListener(new java.awt.event.MouseAdapter() {
		        @Override
		        public void mouseClicked(java.awt.event.MouseEvent evt) {
		            int row = lib_info_table.rowAtPoint(evt.getPoint());
		            int col = lib_info_table.columnAtPoint(evt.getPoint());
		            if (row==1 || row==2 || row==7 || row==8){
		            	
		                System.out.println("in mouse search"+row+" "+col);
			            System.out.println(lib_info_table.getValueAt(row, 0));
			            lib_info_table.setValueAt(pass1.replaceAll("[a-zA-Z0-9]", "*"), 2, 1);
			            if(row==2)
			            lib_info_table.setValueAt(pass1, 2, 1);
			            u_comboBox.setSelectedItem(lib_info_table.getValueAt(row, 0).toString().toUpperCase());
		            }
		            
		        }
		    });
			
			info.close();
		}
		
		catch(Exception e)
		{
			System.out.println("error in info"+e);
		}
		
	}
	String pass1;
}
