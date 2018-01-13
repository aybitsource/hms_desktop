package com.hms.view;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import com.hms.controller.CustomerController;
import com.hms.exception.HMSException;
import com.hms.model.Customer;
import com.hms.util.Constants;
import com.hms.util.CustomDialog;
import com.hms.util.DatabaseConstants;
import com.hms.util.ExcelExporter;
import com.hms.util.SearchBoxModel;
import com.hms.validators.EmailValidator;
import com.hms.validators.MobileValidator;
import com.hms.validators.StringValidator;
import com.hotelmanagement.MainPage;
import com.hotelmanagement.SFont;
import com.hotelmanagement.SetColor;

public class CustomerEntry extends JPanel implements ActionListener,FocusListener {
	/**
	 * 
	 */
	static final long serialVersionUID = 1L;
	private JTextField text_customerID;
	public static String customer_ID;
	private JTextField text_firstName;
    private JLabel lblName;
    private JLabel lblCity;
	private JTextField text_city;
	private JButton btnSave;
	private JLabel  lblCustomerId;
	private JButton btnCancel_1;
	private JLabel lblLastName;
	private JLabel lblEmail;
	private JTextField text_lastName;
	private JTextField text_email;
	private JLabel lblMobileNo;
	private JTextField text_mobile;
	JTextArea text_address;
	private JPanel panel;
	private JLabel lblAdrs;
	private JLabel lblGender;
	private ButtonGroup bg;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	

	private CustomerController customer_controller;
	public static SearchBoxModel sbm_consignCom;
	java.sql.Date tdate;
	Object[][] dat;
	int rows=0;

	private JLabel lblCustomerDetails_1;
	private JLabel label;
		
	GridBagConstraints gbc_scrollPane;
	private JScrollPane scrollPane;
	JTable table;
	DefaultTableModel tableModel;
	JLabel transExcel;
	String filePath;
	public static JButton btnSubmit;
	private JComboBox combo_search;
	public static JLabel lblRows;
	List<JTextField> text_list;
	public CustomerEntry(){

		bg = new ButtonGroup();
		text_list = new ArrayList<JTextField>();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblRows = new JLabel("Rows");
		GridBagConstraints gbc_lblRows = new GridBagConstraints();
		gbc_lblRows.insets = new Insets(5, 5, 5, 5);
		gbc_lblRows.gridx = 2;
		gbc_lblRows.gridy = 0;
		add(lblRows, gbc_lblRows);
		
		lblCustomerDetails_1 = new JLabel("Customers");
		GridBagConstraints gbc_lblCustomerDetails_1 = new GridBagConstraints();
		gbc_lblCustomerDetails_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblCustomerDetails_1.gridx = 3;
		gbc_lblCustomerDetails_1.gridy = 0;
		add(lblCustomerDetails_1, gbc_lblCustomerDetails_1);
		
		combo_search = new JComboBox();
		GridBagConstraints gbc_combo_search = new GridBagConstraints();
		gbc_combo_search.anchor = GridBagConstraints.SOUTHEAST;
		gbc_combo_search.insets = new Insets(0, 0, 5, 5);
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
		            	customer_controller.populateCustomerList();
		            }
		            else
		            {		    
//		            	boolean b = false;
//		            	String selectedItem = ""+combo_search.getSelectedItem();
//		            	for(String item: sbm_consignCom.db)
//		            	{
//		            		if(selectedItem.equals(item))
//		            		{
//		            			b = true;
//		            			System.out.println("from true"+item);
//		            			break;
//		            		}
//		            		else
//		            		{
//		            			b = false;
//		            			System.out.println("from false"+item);
//		            		}
//		            	}
//		            	if(b)System.out.println("from true"+item);
//		            		JOptionPane.showMessageDialog(this, "select the mobile number from the list", "Error", JOptionPane.ERROR_MESSAGE);
//		            	else
		            	customer_controller.populateCustomer(""+combo_search.getSelectedItem());
		            }
		            	
		        }
		    }
		});
		
		combo_search.setMaximumRowCount(10);
		combo_search.setEditable(true);
		sbm_consignCom = new SearchBoxModel(combo_search, DatabaseConstants.CUSTOMER_MOBILE);
		combo_search.setModel(sbm_consignCom);
		combo_search.addItemListener(sbm_consignCom);
		combo_search.addPopupMenuListener(sbm_consignCom);
		add(combo_search, gbc_combo_search);
		
		btnSubmit = new JButton("Save");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubmit.gridx = 5;
		gbc_btnSubmit.gridy = 0;
		add(btnSubmit, gbc_btnSubmit);
		btnSubmit.addActionListener(this);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 4;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 14, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		

		
		label = new JLabel("Customers");
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
		text_list.add(text_firstName);
		
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
		text_list.add(text_lastName);
		
		lblGender = new JLabel("Gender");
		GridBagConstraints gbc_lblGender = new GridBagConstraints();
		gbc_lblGender.anchor = GridBagConstraints.WEST;
		gbc_lblGender.insets = new Insets(0, 0, 5, 5);
		gbc_lblGender.gridx = 0;
		gbc_lblGender.gridy = 5;
		panel.add(lblGender, gbc_lblGender);
		
		rdbtnMale = new JRadioButton("Male");
		GridBagConstraints gbc_rdbtnMale = new GridBagConstraints();
		gbc_rdbtnMale.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnMale.gridx = 2;
		gbc_rdbtnMale.gridy = 5;
		panel.add(rdbtnMale, gbc_rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		GridBagConstraints gbc_rdbtnFemale = new GridBagConstraints();
		gbc_rdbtnFemale.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnFemale.gridx = 3;
		gbc_rdbtnFemale.gridy = 5;
		panel.add(rdbtnFemale, gbc_rdbtnFemale);
		
		bg.add(rdbtnMale);
		bg.add(rdbtnFemale);
		
		lblEmail = new JLabel("E-mail");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 6;
		panel.add(lblEmail, gbc_lblEmail);
		
		text_email = new JTextField();
		GridBagConstraints gbc_text_email = new GridBagConstraints();
		gbc_text_email.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_email.gridwidth = 2;
		gbc_text_email.insets = new Insets(0, 0, 5, 0);
		gbc_text_email.gridx = 2;
		gbc_text_email.gridy = 6;
		panel.add(text_email, gbc_text_email);
		text_email.setColumns(10);
		text_email.setInputVerifier(new EmailValidator((JDialog) getParent(), text_email, "Enter correct email address"));
		
		lblMobileNo = new JLabel("Mobile No");
		GridBagConstraints gbc_lblMobileNo = new GridBagConstraints();
		gbc_lblMobileNo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMobileNo.insets = new Insets(0, 0, 5, 5);
		gbc_lblMobileNo.gridx = 0;
		gbc_lblMobileNo.gridy = 7;
		panel.add(lblMobileNo, gbc_lblMobileNo);
		
		text_mobile = new JTextField();
		GridBagConstraints gbc_text_mobile = new GridBagConstraints();
		gbc_text_mobile.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_mobile.gridwidth = 2;
		gbc_text_mobile.insets = new Insets(0, 0, 5, 0);
		gbc_text_mobile.gridx = 2;
		gbc_text_mobile.gridy = 7;
		panel.add(text_mobile, gbc_text_mobile);
		text_mobile.setColumns(10);
		text_mobile.setInputVerifier(new MobileValidator((JDialog) getParent(), text_mobile, "Enter correct mobile number"));
		text_list.add(text_mobile);
	
		
		lblCity = new JLabel("City");
		GridBagConstraints gbc_lblCity = new GridBagConstraints();
		gbc_lblCity.anchor = GridBagConstraints.WEST;
		gbc_lblCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblCity.gridx = 0;
		gbc_lblCity.gridy = 8;
		panel.add(lblCity, gbc_lblCity);
		
		text_city = new JTextField();
		GridBagConstraints gbc_text_city = new GridBagConstraints();
		gbc_text_city.fill = GridBagConstraints.BOTH;
		gbc_text_city.gridwidth = 2;
		gbc_text_city.insets = new Insets(0, 0, 5, 0);
		gbc_text_city.gridx = 2;
		gbc_text_city.gridy = 8;
		panel.add(text_city, gbc_text_city);
		text_city.setInputVerifier(new StringValidator(null, text_city, "Enter only text values"));
		text_list.add(text_city);
		
		text_address = new JTextArea(2,1);
		JScrollPane spane = new JScrollPane(text_address);
		GridBagConstraints gbc_text_address = new GridBagConstraints();
		gbc_text_address.gridheight = 2;
		gbc_text_address.gridwidth = 2;
		gbc_text_address.insets = new Insets(0, 0, 5, 0);
		gbc_text_address.fill = GridBagConstraints.BOTH;
		gbc_text_address.gridx = 2;
		gbc_text_address.gridy = 9;
		panel.add(spane, gbc_text_address);
		text_address.setLineWrap(true);
		text_address.setWrapStyleWord(true);
		
		
		lblAdrs = new JLabel("Address");
		GridBagConstraints gbc_lblAdrs = new GridBagConstraints();
		gbc_lblAdrs.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAdrs.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdrs.gridx = 0;
		gbc_lblAdrs.gridy = 9;
		panel.add(lblAdrs, gbc_lblAdrs);
		
		
		transExcel = new JLabel();
		GridBagConstraints gbc_lblCustomerDetails_excel = new GridBagConstraints();
		gbc_lblCustomerDetails_excel.insets = new Insets(0, 0, 5, 0);
		gbc_lblCustomerDetails_excel.anchor = GridBagConstraints.EAST;
		gbc_lblCustomerDetails_excel.gridx = 6;
		gbc_lblCustomerDetails_excel.gridy = 0;
		add(transExcel, gbc_lblCustomerDetails_excel);	

		tableModel = new DefaultTableModel(Constants.customerColumnNames, 0);
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
	    header.setToolTipStrings(Constants.customerTipStr);
	    header.setToolTipText("Default ToolTip TEXT");
	    table.setTableHeader(header);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setFont(new Font("Tahoma",Font.PLAIN,14));
		//table.getColumn("SL NO").setMaxWidth(50);
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		
		 gbc_scrollPane = new GridBagConstraints();
		 gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.gridheight = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);
		
		
		table.setForeground(new Color(SetColor.cColor));
		setBackground(new Color(SetColor.bkColor));
		table.setBackground(new Color(SetColor.bkColor));
		table.setFont(new Font(SFont.ctFType,SFont.ctfProp, SFont.ctSize));
		
		customer_controller = new CustomerController(tableModel, table);
		customer_controller.populateCustomerList();
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
                          ExcelExporter.fillData(table, filePath,"Customer Details");
                          JOptionPane.showMessageDialog(null, "Data saved at "+filePath+" successfully", "Message",JOptionPane.INFORMATION_MESSAGE);
                          Desktop.getDesktop().open(new File(filePath));
            		}
//            		else
//            		{
//            			System.out.println("from cancel bro pls solve");
//            		}
       			//FileDialog fd=new FileDialog(new JFrame(),"Save",FileDialog.SAVE);
     			//fd.setVisible(true);
     			
//                
//     			if(!filePath.equals("nullnull"))
//     			{
//  
//     			}
     			
     			
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});
 
		
		btnSave = new JButton("Submit");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 2;
		gbc_btnSave.gridy = 12;
		panel.add(btnSave, gbc_btnSave);
		btnSave.setMnemonic(KeyEvent.VK_B);
		btnSave.addActionListener(this);
		
		btnCancel_1 = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel_1 = new GridBagConstraints();
		gbc_btnCancel_1.fill = GridBagConstraints.BOTH;
		gbc_btnCancel_1.gridx = 3;
		gbc_btnCancel_1.gridy = 12;
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
		text_city.addFocusListener(this);
		text_address.addFocusListener(this);

		
	}

	private void setClear1()
	{
		//text_customerID.setText("");
		text_firstName.setText("");
		text_lastName.setText("");
		text_email.setText("");
		text_mobile.setText("");
		text_address.setText("");
		text_city.setText("");
		bg.clearSelection();
		
 
	}

	public boolean checkTextFields(List<JTextField> textList)
	{ 
		boolean b = false;
		for(JTextField text: textList)
		{
			text.getText().trim();
			if(text.getText().length() == 0)
				b = true; 
		}
		return b;		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnCancel_1)
		{
 
			setClear1();
		}

				if(e.getSource()==btnSave)
				{			
					if(text_firstName.getText().trim().length() == 0)
					{
						text_firstName.requestFocus(true);
						new CustomDialog(this, "Enter the First Name", "Error", text_firstName, 75, 0, CustomDialog.ERROR_ICON);
						
					}
					else if(text_lastName.getText().trim().length() == 0)
					{
						text_lastName.requestFocus(true);
						new CustomDialog(this, "Enter the Last Name", "Error", text_lastName, 75, 0, CustomDialog.ERROR_ICON);
					}
					else if(text_mobile.getText().trim().length() != 10)
					{
						text_mobile.requestFocus(true);
						new CustomDialog(this, "Enter the Mobile Number Properly", "Error", text_mobile, 75, 0, CustomDialog.ERROR_ICON);
					}
					else if(text_city.getText().trim().length() == 0)
					{
						text_city.requestFocus(true);
						new CustomDialog(this, "Enter the city", "Error", text_city, 75, 0, CustomDialog.ERROR_ICON);
					}
					else
					{
					
					String gender;
					if(rdbtnMale.isSelected())
						gender = "MALE";
					else 
						gender = "FEMALE";
					try{
					Customer customer = new Customer();
					customer.setFirst_name(text_firstName.getText().trim().toUpperCase());
					customer.setLast_name(text_lastName.getText().trim().toUpperCase());
					customer.setGender(gender);
					customer.setCity(text_city.getText().trim().toUpperCase());
					customer.setPhone_number(text_mobile.getText().trim());
					customer.setEmail(text_email.getText().trim());
					customer.setAddress(text_address.getText());
					
					Boolean isCustomerAdditionSuccessful = customer_controller.addCustomer(customer);
				

					
					if(isCustomerAdditionSuccessful)
					{
						sbm_consignCom.db.add(customer.getPhone_number());
						JOptionPane.showMessageDialog(this,"Customer Details Updated Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
						customer_controller.populateCustomerList();
					
						int answer = JOptionPane.showConfirmDialog(this, "Do you wish to continue with booking", null, JOptionPane.YES_NO_OPTION);
						if (answer == JOptionPane.YES_OPTION)
						{
							MainPage.tabbedPane.setSelectedIndex(2);
							SingleLeftPanel.text_mobile.setSelectedItem(text_mobile.getText().trim());
						}					
				
	
						updateUI();
						setClear1();
					
					}
					else
					{
					JOptionPane.showMessageDialog(this,"Customer with mobile number already exists","Failure",JOptionPane.ERROR_MESSAGE);
					text_mobile.requestFocus(true);
					text_mobile.selectAll();
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
						Customer customerFromTableModel = customer_controller.getCustomerFromModel(tableModel);
						Customer customerFromCache = customer_controller.getCustomerById(customerFromTableModel.getCustomerID());
						
						if(customer_controller.isCustomerDataModified(customerFromTableModel,customerFromCache)){	
							Boolean isCustomerUpdateSuccessful;
							try {
								isCustomerUpdateSuccessful = customer_controller.updateCustomer(customerFromTableModel);
								if(isCustomerUpdateSuccessful)
								{
									sbm_consignCom.db.remove(combo_search.getSelectedItem());
									sbm_consignCom.db.add(""+tableModel.getValueAt(0, 5));
									JOptionPane.showMessageDialog(this, "Record updated successfully", "Success",  JOptionPane.INFORMATION_MESSAGE);
									customer_controller.populateCustomerList();
									combo_search.setSelectedItem("");
								}
								else
									JOptionPane.showMessageDialog(this, "Please enter the details correctly.",  "Error", JOptionPane.ERROR_MESSAGE);

							} catch (HMSException e1) {
								JOptionPane.showMessageDialog(this, "Customer Update Operation Failed.Please try again",  "Error", JOptionPane.ERROR_MESSAGE);
							}
							
						}else{						
							JOptionPane.showMessageDialog(this, "There are no changes",  "Error", JOptionPane.ERROR_MESSAGE);
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
		else if(arg0.getSource()==text_city)
			text_city.selectAll();
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
	
		else if(arg0.getSource()==text_city)
		{
			text_city.setText(text_city.getText().trim().toUpperCase());
		}
		else if(arg0.getSource()==text_address)
		{
			text_address.setText(text_address.getText().trim().toUpperCase());
		}
		else{}
 
	}

 
 
 
	public void uplcColor()
	{
		lblCustomerId.setForeground(new Color(SetColor.cColor));
		lblLastName.setForeground(new Color(SetColor.cColor));
		lblEmail.setForeground(new Color(SetColor.cColor));
		lblName.setForeground(new Color(SetColor.cColor));
		lblCity.setForeground(new Color(SetColor.cColor));
		lblMobileNo.setForeground(new Color(SetColor.cColor));
		lblAdrs.setForeground(new Color(SetColor.cColor));
		lblGender.setForeground(new Color(SetColor.cColor));
		rdbtnMale.setForeground(new Color(SetColor.cColor));
		rdbtnFemale.setForeground(new Color(SetColor.cColor));
		lblRows.setForeground(new Color(SetColor.cColor));
		
	}
	public void uplmtColor()
	{
		lblCustomerDetails_1.setForeground(new Color(SetColor.mtColor));
		label.setForeground(new Color(SetColor.mtColor));
	}
	public void uplbkColor()
	{
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
		lblCity.setFont(new Font(ctFType,ctfProp,ctSize));
		btnSave.setFont(new Font(ctFType,ctfProp,ctSize));
		btnCancel_1.setFont(new Font(ctFType,ctfProp,ctSize));
		lblAdrs.setFont(new Font(ctFType,ctfProp,ctSize));
		lblGender.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRows.setFont(new Font(ctFType,ctfProp,ctSize));
	}
	public void uplSTFont(String stFType,int stfProp,int stSize)
	{
		lblCustomerDetails_1.setFont(new Font(stFType,stfProp,stSize));
		label.setFont(new Font(stFType,stfProp,stSize));
	}
	

}
