package com.hms.view;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;

import com.hms.controller.EmployeeController;
import com.hms.model.Employee;
import com.hms.util.CompareKeys;
import com.hms.util.Constants;
import com.hms.util.DatabaseConstants;
import com.hms.util.ExcelExporter;
import com.hms.util.PasswordEncrypt;
import com.hms.util.SearchBoxModel;
import com.hms.validators.EmailValidator;
import com.hms.validators.MobileValidator;
import com.hms.validators.StringValidator;
import com.hotelmanagement.MainPage;
import com.hotelmanagement.SFont;
import com.hotelmanagement.SetColor;


public class EmployeeEntry extends JPanel implements ActionListener,FocusListener, KeyListener {
	//public class Register extends JDialog implements ActionListener,FocusListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField text_customerID;
	public static String customer_ID;
	private JTextField text_firstName;
    private JLabel lblName;
    private JLabel lblUsername;
	private JTextField text_username;
	private JButton btnSave;
	private JLabel  lblCustomerId;
	private JButton btnCancel_1;
	private JLabel lblLastName;
	private JLabel lblEmail;
	private JTextField text_lastName;
	private JTextField text_email;
	private JLabel lblMobileNo;
	private JTextField text_mobile;
	JPasswordField text_password;
	private JPanel panel;
	private JLabel lblPassword;
	private JLabel lblGender;
	private ButtonGroup bg;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private JLabel lblConfirmPassword;
	private JPasswordField text_conPassword;
	private JLabel lblBirthDate;

	

	private Employee obj_employee;
	private EmployeeController employee_controller;
	
	
	java.sql.Date tdate;
	Object[][] dat;
	int rows=0;
	Statement st;
	ResultSet rs;
	private JLabel label;
		
	GridBagConstraints gbc_scrollPane;
	//private JScrollPane scrollPane;
	//JTable table;
	String filePath;
	Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel lblMsg;
	private JScrollPane scrollPane;
	public static JComboBox combo_search;
	public static JButton btnSubmit;
	public static JButton btnDelete;
	private JLabel transExcel;
	private JTable table;
	DefaultTableModel tableModel;
	public static JLabel lblRows;
	public static SearchBoxModel sbm_consignCom;
	private JDatePicker picker_birth;
	UtilDateModel birth_model = new UtilDateModel();
	DateFormat date_format;
	public EmployeeEntry(){
		//super(new JFrame(),"Login Form",true);
		//setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		//setResizable(false);
		setSize(d.width/4, d.height/2);
		setLocation(3*d.width/8, d.height/4);		
		 
		obj_employee = new Employee();
	
		bg = new ButtonGroup();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0};
		//getContentPane().setLayout(gridBagLayout);
		setLayout(gridBagLayout);
		
		lblRows = new JLabel("No. of Rows");
		GridBagConstraints gbc_lblRows = new GridBagConstraints();
		gbc_lblRows.insets = new Insets(0, 0, 5, 5);
		gbc_lblRows.gridx = 2;
		gbc_lblRows.gridy = 0;
		add(lblRows, gbc_lblRows);
		
		combo_search = new JComboBox();
		GridBagConstraints gbc_combo_search = new GridBagConstraints();
		gbc_combo_search.insets = new Insets(0, 0, 5, 5);
		gbc_combo_search.fill = GridBagConstraints.HORIZONTAL;
		gbc_combo_search.gridx = 4;
		gbc_combo_search.gridy = 0;
		combo_search.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

		    @Override
		    public void keyReleased(KeyEvent event) {
		        if (event.getKeyChar() == KeyEvent.VK_ENTER) {
		            if (((JTextComponent) ((JComboBox) ((Component) event
		                    .getSource()).getParent()).getEditor()
		                    .getEditorComponent()).getText().isEmpty())
		            {
		            	employee_controller.retrieveAll(DatabaseConstants.TABLE_EMPLOYEES);
		            }
		            else
		            {		            	
		            	employee_controller.retrieve(DatabaseConstants.TABLE_EMPLOYEE_MOBILE, ""+combo_search.getSelectedItem());
		            }
		            	
		        }
		    }
		});
		combo_search.setMaximumRowCount(10);
		combo_search.setEditable(true);
		sbm_consignCom = new SearchBoxModel(combo_search, DatabaseConstants.TABLE_MOBILE);
		combo_search.setModel(sbm_consignCom);
		combo_search.addItemListener(sbm_consignCom);
		combo_search.addPopupMenuListener(sbm_consignCom);
		add(combo_search, gbc_combo_search);
		
		btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.anchor = GridBagConstraints.EAST;
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubmit.gridx = 5;
		gbc_btnSubmit.gridy = 0;
		add(btnSubmit, gbc_btnSubmit);
		
		btnDelete = new JButton("Delete");
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.anchor = GridBagConstraints.EAST;
		gbc_btnDelete.insets = new Insets(0, 0, 5, 5);
		gbc_btnDelete.gridx = 6;
		gbc_btnDelete.gridy = 0;
		add(btnDelete, gbc_btnDelete);
		
		transExcel = new JLabel("");
		GridBagConstraints gbc_lblExcel = new GridBagConstraints();
		gbc_lblExcel.anchor = GridBagConstraints.EAST;
		gbc_lblExcel.insets = new Insets(0, 0, 5, 5);
		gbc_lblExcel.gridx = 7;
		gbc_lblExcel.gridy = 0;
		add(transExcel, gbc_lblExcel);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		//getContentPane().add(panel, gbc_panel);
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 14, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		

		
		label = new JLabel("Create HMS User Accounts");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.gridwidth = 4;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		panel.add(label, gbc_label);
		lblCustomerId = new JLabel("Customer ID  ");
		GridBagConstraints gbc_lblCustomerId = new GridBagConstraints();
		gbc_lblCustomerId.anchor = GridBagConstraints.WEST;
		gbc_lblCustomerId.insets = new Insets(0, 0, 5, 5);
		gbc_lblCustomerId.gridx = 0;
		gbc_lblCustomerId.gridy = 2;
		//panel.add(lblCustomerId, gbc_lblCustomerId);
		
		text_customerID = new JTextField();
		GridBagConstraints gbc_text_customerID = new GridBagConstraints();
		gbc_text_customerID.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_customerID.gridwidth = 2;
		gbc_text_customerID.insets = new Insets(0, 0, 5, 0);
		gbc_text_customerID.gridx = 2;
		gbc_text_customerID.gridy = 2;
		//panel.add(text_customerID, gbc_text_customerID);
		text_customerID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		text_customerID.setColumns(10);
		
//		text_customerID.setText(cs.generateCustomerId());
//		text_customerID.setEditable(false);
		
		lblName = new JLabel("First Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 3;
		panel.add(lblName, gbc_lblName);
		
		text_firstName = new JTextField();
		GridBagConstraints gbc_text_firstName = new GridBagConstraints();
		gbc_text_firstName.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_firstName.gridwidth = 2;
		gbc_text_firstName.insets = new Insets(0, 0, 5, 0);
		gbc_text_firstName.gridx = 2;
		gbc_text_firstName.gridy = 3;
		panel.add(text_firstName, gbc_text_firstName);
		text_firstName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		text_firstName.setColumns(10);
		text_firstName.setInputVerifier(new StringValidator(null, text_firstName, "Enter only text values"));		
		lblLastName = new JLabel("Last Name");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 0;
		gbc_lblLastName.gridy = 4;
		panel.add(lblLastName, gbc_lblLastName);
		
		text_lastName = new JTextField();
		GridBagConstraints gbc_text_lastName = new GridBagConstraints();
		gbc_text_lastName.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_lastName.gridwidth = 2;
		gbc_text_lastName.insets = new Insets(0, 0, 5, 0);
		gbc_text_lastName.gridx = 2;
		gbc_text_lastName.gridy = 4;
		panel.add(text_lastName, gbc_text_lastName);
		text_lastName.setColumns(10);
		text_lastName.setInputVerifier(new StringValidator(null, text_lastName, "Enter only text values"));
		
		lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 5;
		panel.add(lblUsername, gbc_lblUsername);
		
		text_username = new JTextField();
		GridBagConstraints gbc_text_username = new GridBagConstraints();
		gbc_text_username.fill = GridBagConstraints.BOTH;
		gbc_text_username.gridwidth = 2;
		gbc_text_username.insets = new Insets(0, 0, 5, 0);
		gbc_text_username.gridx = 2;
		gbc_text_username.gridy = 5;
		panel.add(text_username, gbc_text_username);
		lblPassword = new JLabel("Password");
		
		lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 6;
		panel.add(lblPassword, gbc_lblPassword);
		
		
		text_password = new JPasswordField();
		GridBagConstraints gbc_text_password = new GridBagConstraints();
		gbc_text_password.gridwidth = 2;
		gbc_text_password.insets = new Insets(0, 0, 5, 0);
		gbc_text_password.fill = GridBagConstraints.BOTH;
		gbc_text_password.gridx = 2;
		gbc_text_password.gridy = 6;
		panel.add(text_password, gbc_text_password);
		text_password.addKeyListener(this);
		text_password.addFocusListener(this);
		

		
		lblConfirmPassword = new JLabel("Confirm Password");
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 0;
		gbc_lblConfirmPassword.gridy = 7;
		panel.add(lblConfirmPassword, gbc_lblConfirmPassword);
		
		
		text_conPassword = new JPasswordField();
		GridBagConstraints gbc_text_conPassword = new GridBagConstraints();
		gbc_text_conPassword.gridwidth = 2;
		gbc_text_conPassword.insets = new Insets(0, 0, 5, 0);
		gbc_text_conPassword.fill = GridBagConstraints.BOTH;
		gbc_text_conPassword.gridx = 2;
		gbc_text_conPassword.gridy = 7;
		panel.add(text_conPassword, gbc_text_conPassword);
		text_conPassword.addFocusListener(this);
		

		lblMsg = new JLabel("These passwords don't match. Try again?");
		GridBagConstraints gbc_lblMsg = new GridBagConstraints();
		gbc_lblMsg.gridwidth = 4;
		gbc_lblMsg.insets = new Insets(0, 0, 5, 0);
		gbc_lblMsg.gridx = 0;
		gbc_lblMsg.gridy = 8;
		panel.add(lblMsg, gbc_lblMsg);
		lblMsg.setVisible(false);
		lblMsg.setForeground(Color.red);
		
		lblBirthDate = new JLabel("Birthday");
		GridBagConstraints gbc_lblBirthDate = new GridBagConstraints();
		gbc_lblBirthDate.anchor = GridBagConstraints.WEST;
		gbc_lblBirthDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblBirthDate.gridx = 0;
		gbc_lblBirthDate.gridy = 9;
		panel.add(lblBirthDate, gbc_lblBirthDate);
		
		

		date_format = new SimpleDateFormat("dd-MM-yyyy");
		Date current_date = new Date();
		birth_model.setValue(current_date);
		picker_birth = new JDatePicker(birth_model, "dd-MM-yyyy");	

		
		GridBagConstraints gbc_datePickerBirth = new GridBagConstraints();
		gbc_datePickerBirth.gridwidth = 2;
		gbc_datePickerBirth.fill = GridBagConstraints.BOTH;
		gbc_datePickerBirth.insets = new Insets(0, 0, 5, 0);
		gbc_datePickerBirth.gridx = 2;
		gbc_datePickerBirth.gridy = 9;
		panel.add(picker_birth, gbc_datePickerBirth);
		
		lblGender = new JLabel("Gender");
		GridBagConstraints gbc_lblGender = new GridBagConstraints();
		gbc_lblGender.anchor = GridBagConstraints.WEST;
		gbc_lblGender.insets = new Insets(0, 0, 5, 5);
		gbc_lblGender.gridx = 0;
		gbc_lblGender.gridy = 10;
		panel.add(lblGender, gbc_lblGender);
		
		rdbtnMale = new JRadioButton("Male");
		GridBagConstraints gbc_rdbtnMale = new GridBagConstraints();
		gbc_rdbtnMale.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnMale.gridx = 2;
		gbc_rdbtnMale.gridy = 10;
		panel.add(rdbtnMale, gbc_rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		GridBagConstraints gbc_rdbtnFemale = new GridBagConstraints();
		gbc_rdbtnFemale.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnFemale.gridx = 3;
		gbc_rdbtnFemale.gridy = 10;
		panel.add(rdbtnFemale, gbc_rdbtnFemale);
		
		bg.add(rdbtnMale);
		bg.add(rdbtnFemale);
		
		lblEmail = new JLabel("E-mail");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 11;
		panel.add(lblEmail, gbc_lblEmail);
		
		text_email = new JTextField();
		GridBagConstraints gbc_text_email = new GridBagConstraints();
		gbc_text_email.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_email.gridwidth = 2;
		gbc_text_email.insets = new Insets(0, 0, 5, 0);
		gbc_text_email.gridx = 2;
		gbc_text_email.gridy = 11;
		panel.add(text_email, gbc_text_email);
		text_email.setColumns(10);
		text_email.setInputVerifier(new EmailValidator(null, text_email, "Enter correct email address"));
		
		lblMobileNo = new JLabel("Mobile No");
		GridBagConstraints gbc_lblMobileNo = new GridBagConstraints();
		gbc_lblMobileNo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMobileNo.insets = new Insets(0, 0, 5, 5);
		gbc_lblMobileNo.gridx = 0;
		gbc_lblMobileNo.gridy = 12;
		panel.add(lblMobileNo, gbc_lblMobileNo);
		
		text_mobile = new JTextField();
		GridBagConstraints gbc_text_mobile = new GridBagConstraints();
		gbc_text_mobile.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_mobile.gridwidth = 2;
		gbc_text_mobile.insets = new Insets(0, 0, 5, 0);
		gbc_text_mobile.gridx = 2;
		gbc_text_mobile.gridy = 12;
		panel.add(text_mobile, gbc_text_mobile);
		text_mobile.setColumns(10);
		text_mobile.setInputVerifier(new MobileValidator(null, text_mobile, "Enter correct mobile number"));
		

		
		btnSave = new JButton("Register");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 2;
		gbc_btnSave.gridy = 13;
		panel.add(btnSave, gbc_btnSave);
		btnSave.setMnemonic(KeyEvent.VK_B);
		btnSave.addActionListener(this);
		
		btnCancel_1 = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel_1 = new GridBagConstraints();
		gbc_btnCancel_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancel_1.fill = GridBagConstraints.BOTH;
		gbc_btnCancel_1.gridx = 3;
		gbc_btnCancel_1.gridy = 13;
		panel.add(btnCancel_1, gbc_btnCancel_1);
		btnCancel_1.setMnemonic(KeyEvent.VK_C);
		uplcColor();
		uplmtColor();
		uplbkColor();
		uplcFont(SFont.ctFType,SFont.ctfProp,SFont.ctSize);
		uplSTFont(SFont.stFType,SFont.stfProp,SFont.stSize);
		
		
		btnCancel_1.addActionListener(this);
		text_mobile.addFocusListener(this);
		text_email.addFocusListener(this);
		text_lastName.addFocusListener(this);
		text_firstName.addFocusListener(this);
		//text_customerID.addFocusListener(this);
		text_username.addFocusListener(this);
		text_conPassword.addFocusListener(this);
		

		
		table = new JTable();
		tableModel = new DefaultTableModel(Constants.employeeEntryNames, 0);
		table = new JTable(tableModel)
		{
			public boolean isCellEditable(int row, int column){  
				if(table.getRowCount()>1)
				{
					return false;

				}
				else
				{
					   return true;  
				}
 
				  }  
		};
		
	    ToolTipHeader header = new ToolTipHeader(table.getColumnModel());
	    header.setToolTipStrings(Constants.employeeEntryTipStr);
	    header.setToolTipText("Default ToolTip TEXT");
	    table.setTableHeader(header);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setFont(new Font("Tahoma",Font.PLAIN,14));
		//table.getColumn("SL NO").setMaxWidth(50);
		table.setFillsViewportHeight(true);
		table.setForeground(new Color(SetColor.cColor));
		setBackground(new Color(SetColor.bkColor));
		table.setBackground(new Color(SetColor.bkColor));
		table.setFont(new Font(SFont.ctFType,SFont.ctfProp, SFont.ctSize));
		
		
		scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 3;
		gbc_scrollPane_1.gridwidth = 6;
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 1;
		add(scrollPane, gbc_scrollPane_1);
		//scrollPane.setColumnHeaderView(table);
		//setUndecorated(true);
		employee_controller = new EmployeeController(tableModel, table);
		employee_controller.retrieveAll(DatabaseConstants.TABLE_EMPLOYEES);
		
		
		transExcel.setIcon(new ImageIcon(MainPage.class.getResource("/images/excel.png")));
		transExcel.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e)
			{
	
			
                try {
                	JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            		jfc.setDialogTitle("Choose a directory to save your file: ");
            		//jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            		jfc.setAcceptAllFileFilterUsed(false);
            		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File", "xls");
            		jfc.addChoosableFileFilter(filter);
            		
            		int returnValue = jfc.showSaveDialog(null);
            		if (returnValue == JFileChooser.APPROVE_OPTION) {
            			 File file = jfc.getSelectedFile();
            		      if (file == null) {
            		        return;
            		      }
            		      if (!file.getName().toLowerCase().endsWith(".xls")) {
            		        file = new File(file.getParentFile(), file.getName() + ".xls");
            		      }
            		      filePath=file.getAbsolutePath();
                          ExcelExporter.fillData(table, filePath,"Employee Details");
                          JOptionPane.showMessageDialog(null, "Data saved at "+filePath+" successfully", "Message",JOptionPane.INFORMATION_MESSAGE);
                          Desktop.getDesktop().open(new File(filePath));
            		}
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});
		btnSubmit.addActionListener(this);
		btnDelete.addActionListener(this);
		setVisible(true);
	}

	private void setClear1()
	{
		//text_customerID.setText("");
		text_firstName.setText("");
		text_lastName.setText("");
		text_email.setText("");
		text_mobile.setText("");
		text_password.setText("");
		text_username.setText("");
		text_conPassword.setText("");
		bg.clearSelection();
		
 
	}

 
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnCancel_1)
		{				
				//dispose();
				//new Login();
			setClear1();
		}

				if(e.getSource()==btnSave)
				{			
					if(text_firstName.getText().trim().length() == 0)
					{
						text_firstName.requestFocus(true);
						JOptionPane.showMessageDialog(this, "Enter the First Name", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(text_lastName.getText().trim().length() == 0)
					{
						text_lastName.requestFocus(true);
						JOptionPane.showMessageDialog(this, "Enter the Last Name", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(text_mobile.getText().trim().length() != 10)
					{
						text_mobile.requestFocus(true);
						JOptionPane.showMessageDialog(this, "Enter the Mobile Number Properly", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(text_password.getText().trim().length() == 0)
					{
						text_password.requestFocus(true);
						JOptionPane.showMessageDialog(this, "Enter the password", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					else
					{	
					String gender;
					if(rdbtnMale.isSelected())
						gender = "MALE";
					else if(rdbtnFemale.isSelected())
						gender = "FEMALE";
					else 
						gender = "NOT MENTIONED";
					Date util_birth_date =  (Date) picker_birth.getModel().getValue();
					java.sql.Date birth_date = new java.sql.Date(util_birth_date.getTime());
					try{
					obj_employee.setFirst_name(text_firstName.getText().trim().toUpperCase());
					obj_employee.setLast_name(text_lastName.getText().trim().toUpperCase());
					obj_employee.setUserName(text_username.getText().trim().toUpperCase());
					String computedHash = PasswordEncrypt.encrypt(text_password.getText());
					obj_employee.setPassword(computedHash);
					obj_employee.setEmail(text_email.getText().trim());
					obj_employee.setMobile(text_mobile.getText().trim());
					obj_employee.setBirthDate(birth_date);
					obj_employee.setGender(gender);		
					obj_employee.setRole(Constants.USER);
					EmployeeController emp_controller = new EmployeeController(obj_employee);
					int s = emp_controller.submitCustomer();
				

					
					if(s>0)
					{
						JOptionPane.showMessageDialog(this,"Employee created successfully","Success",JOptionPane.INFORMATION_MESSAGE);
						text_firstName.requestFocus(true);
						employee_controller.retrieveAll(DatabaseConstants.TABLE_EMPLOYEES);
						sbm_consignCom.db.add(text_mobile.getText().trim().toUpperCase());
						setClear1();
					
					}
					else
					{
					JOptionPane.showMessageDialog(this,"Don't enter duplicate values for username, mobile number, email","Failure",JOptionPane.ERROR_MESSAGE);
					}
					
				
					}catch(NumberFormatException ee){
						//text_customerID.requestFocus(true);
						text_firstName.requestFocus(true);
						JOptionPane.showMessageDialog(this,"Enter the values correctly","Failure",JOptionPane.ERROR_MESSAGE);}
					catch (Exception ee) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(this,""+ee,"Failure",JOptionPane.ERROR_MESSAGE);
					}
					}

				 
					
					
				}
				
				else if(e.getSource()==btnSubmit)
				{
					employee_controller.updateService(DatabaseConstants.UPDATE_EMPLOYEE, DatabaseConstants.TABLE_EMPLOYEES, ""+combo_search.getSelectedItem());
				}
				else if(e.getSource()==btnDelete)
				{
					int answer = JOptionPane.showConfirmDialog(new JFrame(), "Do you wish to delete the record with mobile number "+combo_search.getSelectedItem()+" ?");
					if (answer == JOptionPane.YES_OPTION)
					{		
						int s = employee_controller.deleteService(DatabaseConstants.DELETE_EMPLOYEE, ""+combo_search.getSelectedItem());
						if(s> 0)
						{
							JOptionPane.showMessageDialog(this, "Emplyee deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
							sbm_consignCom.db.remove(EmployeeEntry.combo_search.getSelectedItem());
							combo_search.setSelectedItem("");
							employee_controller.retrieveAll(DatabaseConstants.TABLE_EMPLOYEES);
						}
						
						
					}
				}
				
			}
	
 

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
//		if(arg0.getSource()==text_customerID)
//			text_customerID.selectAll();
		if(arg0.getSource()==text_firstName)
			text_firstName.selectAll();
		else if(arg0.getSource()==text_email)
			text_email.selectAll();
		else if(arg0.getSource()==text_mobile)
			text_mobile.selectAll();
		else if(arg0.getSource()==text_username)
			text_username.selectAll();
		else if(arg0.getSource()==text_password)
			text_password.selectAll();
		else if(arg0.getSource()==text_conPassword)
			text_conPassword.selectAll();
		else {}
	}
	@Override
	public void focusLost(FocusEvent arg0) {

//		if(arg0.getSource()==text_customerID)
//			text_customerID.setText(text_customerID.getText().trim().toUpperCase());
		if(arg0.getSource()==text_firstName)
			text_firstName.setText(text_firstName.getText().trim().toUpperCase());
		else if(arg0.getSource()==text_lastName)
			text_lastName.setText(text_lastName.getText().trim().toUpperCase());
	
		else if(arg0.getSource()==text_conPassword)
		{
			String s1 = text_password.getText();
			String s2 = text_conPassword.getText();
			if(!s1.equals(s2))
			{
				lblMsg.setVisible(true);
				
			}
			else
			{
				lblMsg.setVisible(false);
				
			}
				
		
			
		}
		else{}
 
	}

 
 
 
	public void uplcColor()
	{
		lblCustomerId.setForeground(new Color(SetColor.cColor));
		lblLastName.setForeground(new Color(SetColor.cColor));
		lblEmail.setForeground(new Color(SetColor.cColor));
		lblName.setForeground(new Color(SetColor.cColor));
		lblUsername.setForeground(new Color(SetColor.cColor));
		lblMobileNo.setForeground(new Color(SetColor.cColor));
		lblPassword.setForeground(new Color(SetColor.cColor));
		lblGender.setForeground(new Color(SetColor.cColor));
		rdbtnMale.setForeground(new Color(SetColor.cColor));
		rdbtnFemale.setForeground(new Color(SetColor.cColor));
		lblConfirmPassword.setForeground(new Color(SetColor.cColor));
		lblBirthDate.setForeground(new Color(SetColor.cColor));
		lblRows.setForeground(new Color(SetColor.cColor));
		
	}
	public void uplmtColor()
	{
		label.setForeground(new Color(SetColor.mtColor));
	}
	public void uplbkColor()
	{
		//getContentPane().setBackground(new Color(SetColor.bkColor));
		setBackground(new Color(SetColor.bkColor));
		panel.setBackground(new Color(SetColor.bkColor));
	}
	public void uplcFont(String ctFType,int ctfProp,int ctSize)
	{
		lblMobileNo.setFont(new Font(ctFType,ctfProp,ctSize));
		lblCustomerId.setFont(new Font(ctFType,ctfProp,ctSize));
		lblLastName.setFont(new Font(ctFType,ctfProp,ctSize));
		lblEmail.setFont(new Font(ctFType,ctfProp,ctSize));
		lblName.setFont(new Font(ctFType,ctfProp,ctSize));
		lblUsername.setFont(new Font(ctFType,ctfProp,ctSize));
		btnSave.setFont(new Font(ctFType,ctfProp,ctSize));
		btnCancel_1.setFont(new Font(ctFType,ctfProp,ctSize));
		lblPassword.setFont(new Font(ctFType,ctfProp,ctSize));
		lblGender.setFont(new Font(ctFType,ctfProp,ctSize));
		lblConfirmPassword.setFont(new Font(ctFType,ctfProp,ctSize));
		lblBirthDate.setFont(new Font(ctFType,ctfProp,ctSize));
		lblMsg.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRows.setFont(new Font(ctFType,ctfProp,ctSize));
		
	}
	public void uplSTFont(String stFType,int stfProp,int stSize)
	{
		label.setFont(new Font(stFType,stfProp,stSize));
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==text_password )
		lblMsg.setVisible(false);
	}
	

}
