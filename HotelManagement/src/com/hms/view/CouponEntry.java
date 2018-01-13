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
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

import com.hms.controller.CouponController;
import com.hms.model.Coupon;
import com.hms.util.BigDecimalType;
import com.hms.util.Constants;
import com.hms.util.DatabaseConstants;
import com.hms.util.ExcelExporter;
import com.hms.util.SearchBoxModel;
import com.hms.validators.DoubleValidator;
import com.hotelmanagement.MainPage;
import com.hotelmanagement.SFont;
import com.hotelmanagement.SetColor;

public class CouponEntry extends JPanel implements ActionListener,FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField text_couponID;
	private JTextField text_couponName;
    private JLabel lblCouponName;
	private JButton btnSave;
	private JLabel  lblCouponID;
	private JButton btnCancel_1;
	private JLabel lblCouponDiscount;
	private JTextField text_discount;
	private JPanel panel;
	private ButtonGroup bg;
	private CouponController coupon_controller;
	
	
	
	java.sql.Date tdate;
	Object[][] dat;
	int rows=0;
	Statement st;
	ResultSet rs;
 
	private JLabel lblCustomerDetails_1;
	private JLabel lblCouponDetails;

	
	private JScrollPane scrollPane;
	GridBagConstraints gbc_scrollPane;
	JTable table;
	DefaultTableModel tableModel;
	JLabel transExcel;
	String filePath;
	public static JButton btnSubmit;
	public static JComboBox combo_search;
	public static JLabel lblRows;
	public static SearchBoxModel sbm_consignCom;
	private JComboBox comboBox;
	private JLabel lblType;
	public CouponEntry(){
	
		
		bg = new ButtonGroup();
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
		
		lblCustomerDetails_1 = new JLabel("Coupon Details");
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
		            	coupon_controller.retrieveAll(DatabaseConstants.TABLE_COUPON_COLS);
		            }
		            else
		            {		            	
		            	coupon_controller.retrieve(DatabaseConstants.TABLE_COUPON_NAME, ""+combo_search.getSelectedItem());
		            }
		            	
		        }
		    }
		});
		
		combo_search.setMaximumRowCount(10);
		combo_search.setEditable(true);
		sbm_consignCom = new SearchBoxModel(combo_search, DatabaseConstants.COUPON_NAME);
		combo_search.setModel(sbm_consignCom);
		combo_search.addItemListener(sbm_consignCom);
		combo_search.addPopupMenuListener(sbm_consignCom);


		add(combo_search, gbc_combo_search);
		
		btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubmit.gridx = 5;
		gbc_btnSubmit.gridy = 0;
		add(btnSubmit, gbc_btnSubmit);
		btnSubmit.addActionListener(this);
		
		transExcel = new JLabel();
		GridBagConstraints gbc_lblCustomerDetails_excel = new GridBagConstraints();
		gbc_lblCustomerDetails_excel.insets = new Insets(0, 0, 5, 0);
		gbc_lblCustomerDetails_excel.anchor = GridBagConstraints.EAST;
		gbc_lblCustomerDetails_excel.gridx = 6;
		gbc_lblCustomerDetails_excel.gridy = 0;
		add(transExcel, gbc_lblCustomerDetails_excel);		
		
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
		gbl_panel.rowHeights = new int[]{0, 14, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblCouponDetails = new JLabel("Coupon Details");
		GridBagConstraints gbc_lblCouponDetails = new GridBagConstraints();
		gbc_lblCouponDetails.fill = GridBagConstraints.BOTH;
		gbc_lblCouponDetails.gridwidth = 4;
		gbc_lblCouponDetails.insets = new Insets(0, 0, 5, 0);
		gbc_lblCouponDetails.gridx = 0;
		gbc_lblCouponDetails.gridy = 1;
		panel.add(lblCouponDetails, gbc_lblCouponDetails);
		lblCouponID = new JLabel("Coupon ID");
		GridBagConstraints gbc_lblCouponID = new GridBagConstraints();
		gbc_lblCouponID.anchor = GridBagConstraints.WEST;
		gbc_lblCouponID.insets = new Insets(0, 0, 5, 5);
		gbc_lblCouponID.gridx = 0;
		gbc_lblCouponID.gridy = 2;
		//panel.add(lblCouponID, gbc_lblCouponID);
		
		text_couponID = new JTextField();
		GridBagConstraints gbc_text_couponID = new GridBagConstraints();
		gbc_text_couponID.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_couponID.gridwidth = 2;
		gbc_text_couponID.insets = new Insets(0, 0, 5, 0);
		gbc_text_couponID.gridx = 2;
		gbc_text_couponID.gridy = 2;
		//panel.add(text_couponID, gbc_text_couponID);
		text_couponID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		text_couponID.setColumns(10);
		
		lblCouponName = new JLabel("Coupon Name");
		GridBagConstraints gbc_lblCouponName = new GridBagConstraints();
		gbc_lblCouponName.anchor = GridBagConstraints.WEST;
		gbc_lblCouponName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCouponName.gridx = 0;
		gbc_lblCouponName.gridy = 3;
		panel.add(lblCouponName, gbc_lblCouponName);
		
		text_couponName = new JTextField();
		GridBagConstraints gbc_text_couponName = new GridBagConstraints();
		gbc_text_couponName.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_couponName.gridwidth = 2;
		gbc_text_couponName.insets = new Insets(0, 0, 5, 0);
		gbc_text_couponName.gridx = 2;
		gbc_text_couponName.gridy = 3;
		panel.add(text_couponName, gbc_text_couponName);
		text_couponName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		text_couponName.setColumns(10);
		//text_couponName.setInputVerifier(new StringValidator(null, text_couponName, "Enter only text values"));
		
		lblCouponDiscount = new JLabel("Discount");
		GridBagConstraints gbc_lblCouponDiscount = new GridBagConstraints();
		gbc_lblCouponDiscount.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCouponDiscount.insets = new Insets(0, 0, 5, 5);
		gbc_lblCouponDiscount.gridx = 0;
		gbc_lblCouponDiscount.gridy = 4;
		panel.add(lblCouponDiscount, gbc_lblCouponDiscount);
		
		text_discount = new JTextField();
		GridBagConstraints gbc_text_discount = new GridBagConstraints();
		gbc_text_discount.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_discount.gridwidth = 2;
		gbc_text_discount.insets = new Insets(0, 0, 5, 0);
		gbc_text_discount.gridx = 2;
		gbc_text_discount.gridy = 4;
		panel.add(text_discount, gbc_text_discount);
		text_discount.setInputVerifier(new DoubleValidator(null, text_discount, "Enter only numeric values > 0"));		
		
		text_couponName.addFocusListener(this);
		text_couponID.addFocusListener(this);



		tableModel = new DefaultTableModel(Constants.couponColumnNames, 0);
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
	    header.setToolTipStrings(Constants.couponTipStr);
	    header.setToolTipText("Default ToolTip TEXT");
	    table.setTableHeader(header);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setFont(new Font("Tahoma",Font.PLAIN,14));
		//table.getColumn("SL NO").setMaxWidth(50);
		table.setFillsViewportHeight(true);
		TableColumn  col2 = table.getColumnModel().getColumn(2);
		String[] coupon_type = new String[] { Constants.COUPON_TYPE_GENERAL, Constants.COUPON_TYPE_SEASONAL};
		col2.setCellEditor(new MyComboBoxEditor((coupon_type)));
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
		
		coupon_controller = new CouponController(tableModel, table);
		coupon_controller.retrieveAll(DatabaseConstants.TABLE_COUPON_COLS);
		
		lblType = new JLabel("Coupon Type");
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 5;
		panel.add(lblType, gbc_lblType);
		
		comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 5;
		panel.add(comboBox, gbc_comboBox);
		comboBox.addItem(Constants.COUPON_TYPE_GENERAL);
		comboBox.addItem(Constants.COUPON_TYPE_SEASONAL);
 
		
		btnSave = new JButton("Submit");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 2;
		gbc_btnSave.gridy = 6;
		panel.add(btnSave, gbc_btnSave);
		btnSave.setMnemonic(KeyEvent.VK_B);
		btnSave.addActionListener(this);
		
		btnCancel_1 = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel_1 = new GridBagConstraints();
		gbc_btnCancel_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancel_1.fill = GridBagConstraints.BOTH;
		gbc_btnCancel_1.gridx = 3;
		gbc_btnCancel_1.gridy = 6;
		panel.add(btnCancel_1, gbc_btnCancel_1);
		btnCancel_1.setMnemonic(KeyEvent.VK_C);
		uplcColor();
		uplmtColor();
		uplbkColor();
		uplcFont(SFont.ctFType,SFont.ctfProp,SFont.ctSize);
		uplSTFont(SFont.stFType,SFont.stfProp,SFont.stSize);
		btnCancel_1.addActionListener(this);
		
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
                          ExcelExporter.fillData(table, filePath,"Coupon Details");
                          JOptionPane.showMessageDialog(null, "Data saved at "+filePath+" successfully", "Message",JOptionPane.INFORMATION_MESSAGE);
                          Desktop.getDesktop().open(new File(filePath));
            		}    } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});
		
		//text_couponID.setText(coupon_controller.generateCouponId());
		text_couponID.setEditable(false);
		
	}

	private void setClear1()
	{
		text_couponID.setText("");
		text_couponName.setText("");
		text_discount.setText("");
	
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

					if(text_couponName.getText().trim().length() == 0)
					{
						text_couponName.requestFocus(true);
						text_couponName.selectAll();
						JOptionPane.showMessageDialog(this, "Enter the coupon name", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(text_discount.getText().trim().length() == 0)
					{
						text_discount.requestFocus(true);
						text_discount.selectAll();
						JOptionPane.showMessageDialog(this, "Enter the discount > 0", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
					try{
					Coupon obj_coupon = new Coupon();
					double dicountPrice = Double.parseDouble(text_discount.getText().trim());
					obj_coupon.setCoupon_ID(coupon_controller.generateID());
					obj_coupon.setCoupon_name(text_couponName.getText().trim().toUpperCase());
					obj_coupon.setCoupon_discount(""+BigDecimalType.roundDown(dicountPrice));
					obj_coupon.setCoupon_type(""+comboBox.getSelectedItem());
					CouponController obj_controller = new CouponController(obj_coupon);
					int s = obj_controller.submitCoupon(DatabaseConstants.INSERT_COUPON);
					
					if(s>0)
					{
					JOptionPane.showMessageDialog(this,"Coupon created successfully","Success",JOptionPane.INFORMATION_MESSAGE);
					//text_couponID.requestFocus(true);
					text_couponName.requestFocus(true);
					coupon_controller.retrieveAll(DatabaseConstants.TABLE_COUPON_COLS);
					sbm_consignCom.db.add(text_couponName.getText().trim().toUpperCase());
					setClear1();
					}
					else
					{
					JOptionPane.showMessageDialog(this,"Duplicate value for coupon name","Failure",JOptionPane.ERROR_MESSAGE);
					}
					
				
					}catch(NumberFormatException ee){
						//text_couponID.requestFocus(true);
						text_couponName.requestFocus(true);
						
						JOptionPane.showMessageDialog(this,"Enter the values correctly","Failure",JOptionPane.ERROR_MESSAGE);}
					catch (Exception ee) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(this,""+ee,"Failure",JOptionPane.ERROR_MESSAGE);
					}
					}
						
				}
				else if(e.getSource()==btnSubmit)
				{
					coupon_controller.updateService(DatabaseConstants.UPDATE_COUPONS, DatabaseConstants.TABLE_COUPON_COLS, ""+combo_search.getSelectedItem());
				}
			}
 

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==text_couponID)
			text_couponID.selectAll();
		else if(arg0.getSource()==text_couponName)
			text_couponName.selectAll();
		else if(arg0.getSource()==text_discount)
			text_couponName.selectAll();		
 
 
		else {}
	}
	@Override
	public void focusLost(FocusEvent arg0) {

		if(arg0.getSource()==text_couponID)
			text_couponID.setText(text_couponID.getText().trim().toUpperCase());
		else if(arg0.getSource()==text_couponName)
			text_couponName.setText(text_couponName.getText().trim().toUpperCase());
 

		else{}
 
	}

 
 
 
	public void uplcColor()
	{
		lblCouponID.setForeground(new Color(SetColor.cColor));
		lblCouponDiscount.setForeground(new Color(SetColor.cColor));
		lblCouponName.setForeground(new Color(SetColor.cColor));
		lblRows.setForeground(new Color(SetColor.cColor));
		lblType.setForeground(new Color(SetColor.cColor));
		
	}
	public void uplmtColor()
	{
		lblCustomerDetails_1.setForeground(new Color(SetColor.mtColor));
		lblCouponDetails.setForeground(new Color(SetColor.mtColor));
	}
	public void uplbkColor()
	{
		setBackground(new Color(SetColor.bkColor));
		panel.setBackground(new Color(SetColor.bkColor));
	}
	public void uplcFont(String ctFType,int ctfProp,int ctSize)
	{
		lblCouponID.setFont(new Font(ctFType,ctfProp,ctSize));
		lblCouponDiscount.setFont(new Font(ctFType,ctfProp,ctSize));
		lblCouponName.setFont(new Font(ctFType,ctfProp,ctSize));
		btnSave.setFont(new Font(ctFType,ctfProp,ctSize));
		btnCancel_1.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRows.setFont(new Font(ctFType,ctfProp,ctSize));
		lblType.setFont(new Font(ctFType,ctfProp,ctSize));
	}
	public void uplSTFont(String stFType,int stfProp,int stSize)
	{
		lblCustomerDetails_1.setFont(new Font(stFType,stfProp,stSize));
		lblCouponDetails.setFont(new Font(stFType,stfProp,stSize));
	}
	
	
	
}
