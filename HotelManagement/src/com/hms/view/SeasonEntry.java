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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;

import com.hms.controller.SeasonController;
import com.hms.model.Season;
import com.hms.util.Constants;
import com.hms.util.DBConnection;
import com.hms.util.DatabaseConstants;
import com.hms.util.DateDifferenceCalculator;
import com.hms.util.ExcelExporter;
import com.hms.util.SearchBoxModel;
import com.hotelmanagement.MainPage;
import com.hotelmanagement.SFont;
import com.hotelmanagement.SetColor;

public class SeasonEntry extends JPanel implements ActionListener,FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField text_seasonID;
	private JTextField text_seasonName;
    private JLabel lblSeasonName;
	private JButton btnSave;
	private JLabel  lblSeasonID;
	private JButton btnCancel_1;
	private JLabel lblStartDate;
	private JLabel lblEndDate;
	private JComboBox <String> comboBox_couponID;
	private JLabel lblCouponID;
	private JPanel panel;
	private ButtonGroup bg;
	private SeasonController season_controller;
	
	
	
	Object[][] dat;
	int rows=0;
	Statement st;
	ResultSet rs;

	private JLabel lblCustomerDetails_1;
	private JLabel lblEnterSeasonDetails;
		
 
	
	java.sql.Date startDate;
	java.sql.Date endDate;
	
	private JScrollPane scrollPane;
	GridBagConstraints gbc_scrollPane;
	JTable table;
	DefaultTableModel tableModel;
	JLabel transExcel;
	String filePath;
	Connection con = DBConnection.getDBConnection();
	public static JButton btnSubmit;
	public static JComboBox combo_search;
	public static JLabel lblRows;
	private SearchBoxModel sbm_consign_coupon;
	public static SearchBoxModel sbm_consignCom;
	JDatePicker season_start;
	JDatePicker season_end;
	UtilDateModel start_model = new UtilDateModel();
	UtilDateModel end_model = new UtilDateModel();
	public SeasonEntry(){

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
		
		lblCustomerDetails_1 = new JLabel("Season Details");
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
		            	season_controller.retrieveAll(DatabaseConstants.TABLE_SEASON_COLS);
		            }
		            else
		            {		            	
		            	season_controller.retrieve(DatabaseConstants.TABLE_SEASON_NAME, ""+combo_search.getSelectedItem());
		            }
		            	
		        }
		    }
		});
		combo_search.setMaximumRowCount(10);
		combo_search.setEditable(true);
		sbm_consignCom = new SearchBoxModel(combo_search, DatabaseConstants.SEASON_NAME);
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
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 4;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 14, 3, 0, 20, 20, 18, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblEnterSeasonDetails = new JLabel("Season Details");
		GridBagConstraints gbc_lblEnterSeasonDetails = new GridBagConstraints();
		gbc_lblEnterSeasonDetails.fill = GridBagConstraints.BOTH;
		gbc_lblEnterSeasonDetails.gridwidth = 4;
		gbc_lblEnterSeasonDetails.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterSeasonDetails.gridx = 0;
		gbc_lblEnterSeasonDetails.gridy = 1;
		panel.add(lblEnterSeasonDetails, gbc_lblEnterSeasonDetails);
		lblSeasonID = new JLabel("Season ID");
		GridBagConstraints gbc_lblSeasonID = new GridBagConstraints();
		gbc_lblSeasonID.anchor = GridBagConstraints.WEST;
		gbc_lblSeasonID.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeasonID.gridx = 0;
		gbc_lblSeasonID.gridy = 2;
		//panel.add(lblSeasonID, gbc_lblSeasonID);
		
		text_seasonID = new JTextField();
		GridBagConstraints gbc_text_seasonID = new GridBagConstraints();
		gbc_text_seasonID.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_seasonID.gridwidth = 3;
		gbc_text_seasonID.insets = new Insets(0, 0, 5, 5);
		gbc_text_seasonID.gridx = 2;
		gbc_text_seasonID.gridy = 2;
		//panel.add(text_seasonID, gbc_text_seasonID);
		text_seasonID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		text_seasonID.setColumns(10);
		
		lblSeasonName = new JLabel("Season Name");
		GridBagConstraints gbc_lblSeasonName = new GridBagConstraints();
		gbc_lblSeasonName.anchor = GridBagConstraints.WEST;
		gbc_lblSeasonName.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeasonName.gridx = 0;
		gbc_lblSeasonName.gridy = 3;
		panel.add(lblSeasonName, gbc_lblSeasonName);
		
		text_seasonName = new JTextField();
		GridBagConstraints gbc_text_seasonName = new GridBagConstraints();
		gbc_text_seasonName.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_seasonName.gridwidth = 3;
		gbc_text_seasonName.insets = new Insets(0, 0, 5, 5);
		gbc_text_seasonName.gridx = 2;
		gbc_text_seasonName.gridy = 3;
		panel.add(text_seasonName, gbc_text_seasonName);
		text_seasonName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		text_seasonName.setColumns(10);
		
		lblStartDate = new JLabel("Start Date");
		GridBagConstraints gbc_lblStartDate = new GridBagConstraints();
		gbc_lblStartDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblStartDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartDate.gridx = 0;
		gbc_lblStartDate.gridy = 4;
		panel.add(lblStartDate, gbc_lblStartDate);
	
		final Date current_date = new Date();
		start_model.setValue(current_date);
		end_model.setValue(current_date);
		season_start = new JDatePicker(start_model, "dd-MM-yyyy");
		season_end = new JDatePicker(end_model, "dd-MM-yyyy");

		GridBagConstraints gbc_datePic = new GridBagConstraints();
		gbc_datePic.gridwidth = 4;
		gbc_datePic.fill = GridBagConstraints.BOTH;
		gbc_datePic.insets = new Insets(0, 0, 5, 0);
		gbc_datePic.gridx = 2;
		gbc_datePic.gridy = 4;
		panel.add(season_start, gbc_datePic);
		

		
		lblEndDate = new JLabel("End Date");
		GridBagConstraints gbc_lblEndDate = new GridBagConstraints();
		gbc_lblEndDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEndDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndDate.gridx = 0;
		gbc_lblEndDate.gridy = 5;
		panel.add(lblEndDate, gbc_lblEndDate);
		
		season_end = new JDatePicker(end_model, "dd-MM-yyyy");
		GridBagConstraints gbc_datePic1 = new GridBagConstraints();
		gbc_datePic1.gridwidth = 4;
		gbc_datePic1.fill = GridBagConstraints.BOTH;
		gbc_datePic1.insets = new Insets(0, 0, 5, 0);
		gbc_datePic1.gridx = 2;
		gbc_datePic1.gridy = 5;
		panel.add(season_end, gbc_datePic1);
		
		lblCouponID = new JLabel("Coupon");
		GridBagConstraints gbc_lblCouponID = new GridBagConstraints();
		gbc_lblCouponID.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCouponID.insets = new Insets(0, 0, 5, 5);
		gbc_lblCouponID.gridx = 0;
		gbc_lblCouponID.gridy = 6;
		panel.add(lblCouponID, gbc_lblCouponID);
		text_seasonName.addFocusListener(this);
		text_seasonID.addFocusListener(this);
		
		
		transExcel = new JLabel();
		GridBagConstraints gbc_lblCustomerDetails_excel = new GridBagConstraints();
		gbc_lblCustomerDetails_excel.insets = new Insets(0, 0, 5, 0);
		gbc_lblCustomerDetails_excel.anchor = GridBagConstraints.EAST;
		gbc_lblCustomerDetails_excel.gridx = 6;
		gbc_lblCustomerDetails_excel.gridy = 0;
		add(transExcel, gbc_lblCustomerDetails_excel);	

		tableModel = new DefaultTableModel(Constants.seasonEntryNames, 0);
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
	    header.setToolTipStrings(Constants.seasonEntryTipStr);
	    header.setToolTipText("Default ToolTip TEXT");
	    table.setTableHeader(header);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setFont(new Font("Tahoma",Font.PLAIN,14));
		//table.getColumn("SL NO").setMaxWidth(50);
		table.setFillsViewportHeight(true);
		
		

		scrollPane = new JScrollPane(table);
		
		 gbc_scrollPane = new GridBagConstraints();
		 gbc_scrollPane.gridwidth = 5;
		 gbc_scrollPane.insets = new Insets(0, 0, 0, 0);
		gbc_scrollPane.gridheight = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);
		
		table.setForeground(new Color(SetColor.cColor));
		setBackground(new Color(SetColor.bkColor));
		table.setBackground(new Color(SetColor.bkColor));
		table.setFont(new Font(SFont.ctFType,SFont.ctfProp, SFont.ctSize));
		
		season_controller = new SeasonController(tableModel, table);
		season_controller.retrieveAll(DatabaseConstants.TABLE_SEASON_COLS);
		
		
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
                          ExcelExporter.fillData(table, filePath,"Season Details");
                          JOptionPane.showMessageDialog(null, "Data saved at "+filePath+" successfully", "Message",JOptionPane.INFORMATION_MESSAGE);
                          Desktop.getDesktop().open(new File(filePath));
            		}        } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});
		
		comboBox_couponID = new JComboBox<String>();
		GridBagConstraints gbc_comboBox_couponID = new GridBagConstraints();
		gbc_comboBox_couponID.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_couponID.gridwidth = 3;
		gbc_comboBox_couponID.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_couponID.gridx = 2;
		gbc_comboBox_couponID.gridy = 6;
		comboBox_couponID.setEditable(true);
		
		sbm_consign_coupon = new SearchBoxModel(comboBox_couponID, DatabaseConstants.SEASONAL_COUPONS_LEFT, Constants.COUPON_TYPE_SEASONAL);
		comboBox_couponID.setModel(sbm_consign_coupon);
		comboBox_couponID.addPopupMenuListener(sbm_consign_coupon);
		comboBox_couponID.addItemListener(sbm_consign_coupon);		
		panel.add(comboBox_couponID, gbc_comboBox_couponID);
		
 
		
		
		List<String> categoryList = (ArrayList<String>)sbm_consign_coupon.db.clone();
		TableColumn  col3 = table.getColumnModel().getColumn(3);
		categoryList.remove("");
		String[] categories = categoryList.toArray(new String[categoryList.size()]);
		col3.setCellEditor(new MyComboBoxEditor(categories));
		
		btnSave = new JButton("Submit");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 2;
		gbc_btnSave.gridy = 7;
		panel.add(btnSave, gbc_btnSave);
		btnSave.setMnemonic(KeyEvent.VK_B);
		btnSave.addActionListener(this);
		
		btnCancel_1 = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel_1 = new GridBagConstraints();
		gbc_btnCancel_1.gridwidth = 2;
		gbc_btnCancel_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancel_1.fill = GridBagConstraints.BOTH;
		gbc_btnCancel_1.gridx = 3;
		gbc_btnCancel_1.gridy = 7;
		panel.add(btnCancel_1, gbc_btnCancel_1);
		btnCancel_1.setMnemonic(KeyEvent.VK_C);
		uplcColor();
		uplmtColor();
		uplbkColor();
		uplcFont(SFont.ctFType,SFont.ctfProp,SFont.ctSize);
		uplSTFont(SFont.stFType,SFont.stfProp,SFont.stSize);
		paybkColor();
		btnCancel_1.addActionListener(this);
//		try
//		{
//
//			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
//			rs=stmt.executeQuery(DatabaseConstants.COUPON_NAME);
//			while(rs.next())
//			{
//				comboBox_couponID.addItem(rs.getString(1));	
//			}
//			rs.first();
//
//		}catch(Exception e){
//			Thread t=new Thread(){
//				public void run()
//				{
//					comboBox_couponID.removeAllItems();
//					JOptionPane.showMessageDialog(this, "No data found", "Error Message", JOptionPane.ERROR_MESSAGE);
//				}
//				};
//				t.start();
//		}
//		
		//text_seasonID.setText(season_controller.generateSeasonID());
		text_seasonID.setEditable(false);
	}

	private void setClear1()
	{
		text_seasonID.setText("");
		text_seasonName.setText("");		
 
	}
	public boolean checkCouponInDB(String param)
	{
		boolean b = false;
		if(param.length()>0)
		{
			for(String item : sbm_consign_coupon.db)
			{
				if(item.equals(param))
				{
					b =  true;
					break;
				}
			}
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
					Date utilStartDate = (Date) season_start.getModel().getValue();
					Date utilEndDate = (Date) season_end.getModel().getValue();
					startDate = new java.sql.Date(utilStartDate.getTime());
					endDate = new java.sql.Date(utilEndDate.getTime());
					if(text_seasonName.getText().trim().length() == 0)
					{
						text_seasonName.requestFocus(true);
						text_seasonName.selectAll();
						JOptionPane.showMessageDialog(this, "Enter season name", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(DateDifferenceCalculator.calculateDifference(startDate, endDate)<0)
					{	
						season_start.requestFocus(true);
						JOptionPane.showMessageDialog(this, "Ending date should be greater that start date", "Failure", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
							if(checkCouponInDB(""+comboBox_couponID.getSelectedItem()))						
							submitSeason();
							else 
							{
								comboBox_couponID.requestFocus(true);
								JOptionPane.showMessageDialog(this, "Select the coupon from the list", "Failure", JOptionPane.ERROR_MESSAGE);
							}
					}
				}
				else if(e.getSource()==btnSubmit)
				{
					season_controller.updateService(DatabaseConstants.UPDATE_SEASON, DatabaseConstants.TABLE_SEASON_COLS, ""+combo_search.getSelectedItem());
				}
			}

	private void submitSeason() {


		try{

		Season obj_season = new Season();
		obj_season.setSeason_ID(season_controller.generateID());
		obj_season.setSeason_name(text_seasonName.getText().trim().toUpperCase());
		obj_season.setDate_start(startDate);
		obj_season.setDate_end(endDate);
		obj_season.setCoupon_name((String)comboBox_couponID.getSelectedItem());

		SeasonController obj_controller = new SeasonController(obj_season);
		int s = obj_controller.submitService(DatabaseConstants.INSERT_SEASON);
		
		if(s>0)
		{
		JOptionPane.showMessageDialog(this,"Season created successfully","Success",JOptionPane.INFORMATION_MESSAGE);
		text_seasonName.requestFocus(true);
		season_controller.retrieveAll(DatabaseConstants.TABLE_SEASON_COLS);
		sbm_consign_coupon.db.remove(comboBox_couponID.getSelectedItem());
		sbm_consignCom.db.add(text_seasonName.getText().trim().toUpperCase());
		comboBox_couponID.setSelectedItem("");
		setClear1();
		}
		else
		{
		JOptionPane.showMessageDialog(this,"Duplicate value for season name","Failure",JOptionPane.ERROR_MESSAGE);
		}
		

		}
		catch (Exception ee) {
			// TODO Auto-generated catch blockock
			ee.printStackTrace();
			JOptionPane.showMessageDialog(this,"Check for solution","Failure",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void throwError(String msg) throws Exception 
	{
		    throw new Exception(msg);
	} 

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==text_seasonID)
			text_seasonID.selectAll();
		else if(arg0.getSource()==text_seasonName)
			text_seasonName.selectAll();
 
 
		else {}
	}
	@Override
	public void focusLost(FocusEvent arg0) {

		if(arg0.getSource()==text_seasonID)
			text_seasonID.setText(text_seasonID.getText().trim().toUpperCase());
		else if(arg0.getSource()==text_seasonName)
			text_seasonName.setText(text_seasonName.getText().trim().toUpperCase());
 

		else{}
 
	}

 
 
 
	public void uplcColor()
	{
		lblSeasonID.setForeground(new Color(SetColor.cColor));
		lblStartDate.setForeground(new Color(SetColor.cColor));
		lblEndDate.setForeground(new Color(SetColor.cColor));
		lblSeasonName.setForeground(new Color(SetColor.cColor));
		lblCouponID.setForeground(new Color(SetColor.cColor));
		lblRows.setForeground(new Color(SetColor.cColor));

		
	}
	public void paybkColor()
	{
		setBackground(new Color(SetColor.bkColor));
		table.setBackground(new Color(SetColor.bkColor));
		season_start.setBackground(new Color(SetColor.bkColor));
		season_end.setBackground(new Color(SetColor.bkColor));		
	}
	public void uplmtColor()
	{
		lblCustomerDetails_1.setForeground(new Color(SetColor.mtColor));
		lblEnterSeasonDetails.setForeground(new Color(SetColor.mtColor));
	}
	public void uplbkColor()
	{
		setBackground(new Color(SetColor.bkColor));
		panel.setBackground(new Color(SetColor.bkColor));
	}
	public void uplcFont(String ctFType,int ctfProp,int ctSize)
	{
		lblCouponID.setFont(new Font(ctFType,ctfProp,ctSize));
		lblSeasonID.setFont(new Font(ctFType,ctfProp,ctSize));
		lblStartDate.setFont(new Font(ctFType,ctfProp,ctSize));
		lblEndDate.setFont(new Font(ctFType,ctfProp,ctSize));
		lblSeasonName.setFont(new Font(ctFType,ctfProp,ctSize));
		btnSave.setFont(new Font(ctFType,ctfProp,ctSize));
		btnCancel_1.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRows.setFont(new Font(ctFType,ctfProp,ctSize));
	}
	public void uplSTFont(String stFType,int stfProp,int stSize)
	{
		lblCustomerDetails_1.setFont(new Font(stFType,stfProp,stSize));
		lblEnterSeasonDetails.setFont(new Font(stFType,stfProp,stSize));
	}
	
	
	
}
