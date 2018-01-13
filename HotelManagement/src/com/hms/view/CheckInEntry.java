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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
import javax.swing.text.JTextComponent;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;

import com.hms.controller.CheckInController;
import com.hms.model.CheckIn;
import com.hms.services.CheckInService;
import com.hms.util.BigDecimalType;
import com.hms.util.Constants;
import com.hms.util.CustomDialog;
import com.hms.util.DBConnection;
import com.hms.util.DatabaseConstants;
import com.hms.util.ExcelExporter;
import com.hms.util.SearchBoxModel;
import com.hms.util.TableDialog;
import com.hms.validators.DoubleValidator;
import com.hotelmanagement.MainPage;
import com.hotelmanagement.SFont;
import com.hotelmanagement.SetColor;

public class CheckInEntry extends JPanel implements ActionListener,FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static  JComboBox<String> text_BookingID;
	private JButton btnSave;
	private JLabel  lblBookingID;
	private JTextField text_customerID;
	private JLabel lblCustomerID;
	private JPanel panel;

	
	
	
	Object[][] dat;
	int rows=0;
	Statement st;
	ResultSet rs;
	private JLabel lblCustomerDetails_1;
		
	
	java.sql.Date startDate;
	java.sql.Date endDate;
	java.sql.Date bookingDate;
	JDatePanelImpl datePanel;
	Calendar calendar;
	JDatePanelImpl datePanel1;
	Calendar calendar1;	
	JDatePanelImpl datePanel2;
	Calendar calendar2;		
	private JLabel lblAdvance;
	private JTextField text_advance;
	
	
	
	GridBagConstraints gbc_scrollPane;
	private JScrollPane scrollPane;
	JTable table;
	DefaultTableModel tableModel;
	JLabel transExcel;
	String filePath;
	private JLabel lblCheckin;
	private CheckIn obj_checkIn;
	private CheckInController checkin_controller;
	private CheckInService checkin_service;
	private JLabel lblCheckIn;
	private JTextField text_checkInID;
	Connection con = DBConnection.getDBConnection();
	SearchBoxModel sbm_consignCom;
	public static SearchBoxModel sbm_consignCom1;
	public static JButton btnSubmit;
	private JComboBox combo_search;
	public static JLabel lblRows;
	private JLabel lblAdults;
	private JLabel lblChildren;
	public static JTextField text_adults;
	private JTextField text_children;
	public static String bookingID;
	public static JTextField text_roomNo = new JTextField();
	private JLabel lblRoomno;
	public CheckInEntry(MainPage mpg){
		
		obj_checkIn = new CheckIn();
		checkin_controller = new CheckInController(obj_checkIn);
		checkin_service = new CheckInService();
		
	
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 0, 50, 0, 100, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{24, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblRows = new JLabel("No. of Rows:");
		GridBagConstraints gbc_lblRows = new GridBagConstraints();
		gbc_lblRows.anchor = GridBagConstraints.WEST;
		gbc_lblRows.insets = new Insets(5, 5, 5, 5);
		gbc_lblRows.gridx = 2;
		gbc_lblRows.gridy = 0;
		add(lblRows, gbc_lblRows);
		
		lblCustomerDetails_1 = new JLabel("Check-In");
		GridBagConstraints gbc_lblCustomerDetails_1 = new GridBagConstraints();
		gbc_lblCustomerDetails_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblCustomerDetails_1.gridx = 3;
		gbc_lblCustomerDetails_1.gridy = 0;
		add(lblCustomerDetails_1, gbc_lblCustomerDetails_1);
		
		combo_search = new JComboBox();
		GridBagConstraints gbc_combo_search = new GridBagConstraints();
		gbc_combo_search.fill = GridBagConstraints.HORIZONTAL;
		gbc_combo_search.anchor = GridBagConstraints.SOUTH;
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
		            	checkin_service.retrieveAll(DatabaseConstants.TABLE_CHECK_IN_COLS);
		            }
		            else
		            {		            	
		            	checkin_service.retrieveCheckInTransaction(DatabaseConstants.ALL_CHECKIN_ID, ""+combo_search.getSelectedItem());
		            }
		            	
		        }
		    }
		});
		
		combo_search.setMaximumRowCount(10);
		combo_search.setEditable(true);
		sbm_consignCom = new SearchBoxModel(combo_search, DatabaseConstants.SELECT_CHECKIN_BOOKING_ID);
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
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 3, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0, 1.0};
		panel.setLayout(gbl_panel);
		
		lblCheckIn = new JLabel("Check In");
		GridBagConstraints gbc_lblCheckIn = new GridBagConstraints();
		gbc_lblCheckIn.anchor = GridBagConstraints.WEST;
		gbc_lblCheckIn.insets = new Insets(0, 0, 5, 5);
		gbc_lblCheckIn.gridx = 0;
		gbc_lblCheckIn.gridy = 2;
		//panel.add(lblCheckIn, gbc_lblCheckIn);
		
		text_checkInID = new JTextField();
		GridBagConstraints gbc_text_checkInID = new GridBagConstraints();
		gbc_text_checkInID.gridwidth = 2;
		gbc_text_checkInID.insets = new Insets(0, 0, 5, 0);
		gbc_text_checkInID.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_checkInID.gridx = 2;
		gbc_text_checkInID.gridy = 2;
//		panel.add(text_checkInID, gbc_text_checkInID);
//		text_checkInID.setColumns(10);
//		text_checkInID.setText(checkin_service.generateCheckInId());
//		text_checkInID.setEditable(false);
		
		lblCheckin = new JLabel("Check-In");
		GridBagConstraints gbc_lblCheckin = new GridBagConstraints();
		gbc_lblCheckin.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCheckin.gridwidth = 3;
		gbc_lblCheckin.insets = new Insets(0, 0, 5, 0);
		gbc_lblCheckin.gridx = 1;
		gbc_lblCheckin.gridy = 1;
		panel.add(lblCheckin, gbc_lblCheckin);
		
		lblBookingID = new JLabel("Booking ID");
		GridBagConstraints gbc_lblBookingID = new GridBagConstraints();
		gbc_lblBookingID.anchor = GridBagConstraints.WEST;
		gbc_lblBookingID.insets = new Insets(0, 0, 5, 5);
		gbc_lblBookingID.gridx = 1;
		gbc_lblBookingID.gridy = 2;
		panel.add(lblBookingID, gbc_lblBookingID);
		
		text_BookingID = new JComboBox<String>();
		GridBagConstraints gbc_text_BookingID = new GridBagConstraints();
		gbc_text_BookingID.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_BookingID.insets = new Insets(0, 0, 5, 5);
		gbc_text_BookingID.gridx = 2;
		gbc_text_BookingID.gridy = 2;		
		text_BookingID.setEditable(false);
		sbm_consignCom1 = new SearchBoxModel(text_BookingID, DatabaseConstants.BOOKING_ID_BOOKED, Constants.BOOKED);
		text_BookingID.setModel(sbm_consignCom1);
		text_BookingID.addItemListener(sbm_consignCom1);
		text_BookingID.addPopupMenuListener(sbm_consignCom1);
		if(bookingID!=null)
		sbm_consignCom1.cb.setSelectedItem(bookingID);
//		List<String> listIDs = checkin_controller.retrieveBookingIDs(DatabaseConstants.BOOKING_ID_BOOKED, Constants.BOOKED);
//		for(String bookingID : listIDs)
//		{
//			text_BookingID.addItem(bookingID);
//		}

		System.out.println("sib bookingid is"+bookingID);
		text_BookingID.addActionListener(this);
		panel.add(text_BookingID, gbc_text_BookingID);
		text_BookingID.setFont(new Font("Tahoma", Font.PLAIN, 11));

		

		
		
		lblCustomerID = new JLabel("Customer ID");
		GridBagConstraints gbc_lblCustomerID = new GridBagConstraints();
		gbc_lblCustomerID.anchor = GridBagConstraints.WEST;
		gbc_lblCustomerID.insets = new Insets(0, 0, 5, 5);
		gbc_lblCustomerID.gridx = 0;
		gbc_lblCustomerID.gridy = 4;
		//panel.add(lblCustomerID, gbc_lblCustomerID);
		
		
		text_customerID = new JTextField();
		text_customerID.setColumns(10);
		GridBagConstraints gbc_text_customerID = new GridBagConstraints();
		gbc_text_customerID.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_customerID.gridwidth = 2;
		gbc_text_customerID.insets = new Insets(0, 0, 5, 0);
		gbc_text_customerID.gridx = 2;
		gbc_text_customerID.gridy = 4;
		//panel.add(text_customerID, gbc_text_customerID);
		

		
		Calendar cal2 = new GregorianCalendar();
		int tyr2 = cal2.get(Calendar.YEAR);
		int tmon2 = cal2.get(Calendar.MONTH);
		int tday2 = cal2.get(Calendar.DAY_OF_MONTH);
		SqlDateModel model2 = new SqlDateModel();
		model2.setDate(tyr2, tmon2, tday2);
		model2.setSelected(true);
		datePanel2 = new JDatePanelImpl(model2);
		Calendar cal = new GregorianCalendar();
		int tyr = cal.get(Calendar.YEAR);
		int tmon = cal.get(Calendar.MONTH);
		int tday = cal.get(Calendar.DAY_OF_MONTH);
		SqlDateModel model = new SqlDateModel();
		model.setDate(tyr, tmon, tday);
		model.setSelected(true);
		datePanel = new JDatePanelImpl(model);
		
		Calendar cal1 = new GregorianCalendar();
		int tyr1 = cal1.get(Calendar.YEAR);
		int tmon1 = cal1.get(Calendar.MONTH);
		int tday1 = cal1.get(Calendar.DAY_OF_MONTH);
		SqlDateModel model1 = new SqlDateModel();
		model1.setDate(tyr1, tmon1, tday1);
		model1.setSelected(true);
		datePanel1 = new JDatePanelImpl(model1);
		

		
		
		transExcel = new JLabel();
		transExcel.setIcon(new ImageIcon(BookingEntry.class.getResource("/images/excel.png")));
		GridBagConstraints gbc_lblCustomerDetails_excel = new GridBagConstraints();
		gbc_lblCustomerDetails_excel.insets = new Insets(0, 0, 5, 0);
		gbc_lblCustomerDetails_excel.anchor = GridBagConstraints.EAST;
		gbc_lblCustomerDetails_excel.gridx = 6;
		gbc_lblCustomerDetails_excel.gridy = 0;
		add(transExcel, gbc_lblCustomerDetails_excel);	
		
		tableModel = new DefaultTableModel(Constants.checkInEntryNames, 0);
		table = new JTable(tableModel)
		{
			public boolean isCellEditable(int row, int column){  
				if(table.getRowCount()>1)
				{
					return false;

				}
				else
				{
					if(column == 0)
						return false;
					else
					   return true;  
				}
				}

		};
		
	    ToolTipHeader header = new ToolTipHeader(table.getColumnModel());
	    header.setToolTipStrings(Constants.checkInEntryTipStr);
	    header.setToolTipText("Default ToolTip TEXT");
	    table.setTableHeader(header);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setFont(new Font("Tahoma",Font.PLAIN,14));
		//table.getColumn("SL NO").setMaxWidth(50);
		table.setFillsViewportHeight(true);
		TableDialog.tableRecordDetails(mpg, table, DatabaseConstants.SELECT_CHECKIN_BOOKING_ID);
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
 
		
		checkin_service = new CheckInService(tableModel, table);
		checkin_service.retrieveAll(DatabaseConstants.TABLE_CHECK_IN_COLS);
		
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
                          ExcelExporter.fillData(table, filePath,"Checkin Details");
                          JOptionPane.showMessageDialog(null, "Data saved at "+filePath+" successfully", "Message",JOptionPane.INFORMATION_MESSAGE);
                          Desktop.getDesktop().open(new File(filePath));
            		}
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});
		
		lblRoomno = new JLabel("RoomNo");
		GridBagConstraints gbc_lblRoomno = new GridBagConstraints();
		gbc_lblRoomno.anchor = GridBagConstraints.WEST;
		gbc_lblRoomno.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomno.gridx = 1;
		gbc_lblRoomno.gridy = 3;
		panel.add(lblRoomno, gbc_lblRoomno);
		
		
		GridBagConstraints gbc_text_roomNo = new GridBagConstraints();
		gbc_text_roomNo.insets = new Insets(0, 0, 5, 5);
		gbc_text_roomNo.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_roomNo.gridx = 2;
		gbc_text_roomNo.gridy = 3;
		panel.add(text_roomNo, gbc_text_roomNo);
		text_roomNo.setColumns(10);
		
		lblAdults = new JLabel("Adults");
		GridBagConstraints gbc_lblAdults = new GridBagConstraints();
		gbc_lblAdults.anchor = GridBagConstraints.WEST;
		gbc_lblAdults.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdults.gridx = 1;
		gbc_lblAdults.gridy = 4;
		panel.add(lblAdults, gbc_lblAdults);
		
		text_adults = new JTextField();
		GridBagConstraints gbc_text_adults = new GridBagConstraints();
		gbc_text_adults.insets = new Insets(0, 0, 5, 5);
		gbc_text_adults.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_adults.gridx = 2;
		gbc_text_adults.gridy = 4;
		panel.add(text_adults, gbc_text_adults);
		text_adults.setColumns(10);
		
		lblChildren = new JLabel("Children");
		GridBagConstraints gbc_lblChildren = new GridBagConstraints();
		gbc_lblChildren.anchor = GridBagConstraints.WEST;
		gbc_lblChildren.insets = new Insets(0, 0, 5, 5);
		gbc_lblChildren.gridx = 1;
		gbc_lblChildren.gridy = 5;
		panel.add(lblChildren, gbc_lblChildren);
		
		text_children = new JTextField();
		GridBagConstraints gbc_text_children = new GridBagConstraints();
		gbc_text_children.insets = new Insets(0, 0, 5, 5);
		gbc_text_children.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_children.gridx = 2;
		gbc_text_children.gridy = 5;
		panel.add(text_children, gbc_text_children);
		text_children.setColumns(10);
		
		text_advance = new JTextField();
		GridBagConstraints gbc_text_advance = new GridBagConstraints();
		gbc_text_advance.insets = new Insets(0, 0, 5, 5);
		gbc_text_advance.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_advance.gridx = 2;
		gbc_text_advance.gridy = 6;
		panel.add(text_advance, gbc_text_advance);
		text_advance.setColumns(10);
		text_advance.setInputVerifier(new DoubleValidator(null, text_advance, "Enter only numeric values"));
		
		

		
		lblAdvance = new JLabel("Advance Amount");
		GridBagConstraints gbc_lblAdvance = new GridBagConstraints();
		gbc_lblAdvance.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdvance.anchor = GridBagConstraints.WEST;
		gbc_lblAdvance.gridx = 1;
		gbc_lblAdvance.gridy = 6;
		panel.add(lblAdvance, gbc_lblAdvance);
		
		btnSave = new JButton("Submit");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 2;
		gbc_btnSave.gridy = 7;
		panel.add(btnSave, gbc_btnSave);
		btnSave.setMnemonic(KeyEvent.VK_B);
		btnSave.addActionListener(this);
		uplcColor();
		uplmtColor();
		uplbkColor();
		uplcFont(SFont.ctFType,SFont.ctfProp,SFont.ctSize);
		uplSTFont(SFont.stFType,SFont.stfProp,SFont.stSize);
		paybkColor();
		btnSubmit.setVisible(false);
		text_advance.addFocusListener(this);
		//text_customerID.addFocusListener(this);
		
	}
	public void setClear()
	{
//		
//		sbm_consignCom.db.removeAll();
//		sbm_consignCom = new SearchBoxModel(text_BookingID, DatabaseConstants.CHECKIN_BOOKING_ID, Constants.NULL);
//		text_BookingID.setModel(sbm_consignCom);
//		
//
//		text_BookingID.updateUI();
		sbm_consignCom1.db.remove(text_BookingID.getSelectedItem());
		sbm_consignCom1.cb.setSelectedItem("");
		text_advance.setText("");
		text_adults.setText("");
		text_children.setText("");
	}
	public boolean checkBookingInDB(String bookingID)
	{
		boolean b = false;
		if(bookingID.length()>0)
		{
			for(String item : sbm_consignCom1.db)
			{
				if(item.equals(bookingID))
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

				if(e.getSource()==btnSave)
				{		
							//startDate = (java.sql.Date) datePickerArrival.getModel().getValue();
							//endDate = (java.sql.Date) datePickerDeparture.getModel().getValue();
							//bookingDate = (java.sql.Date) datePickerBooking.getModel().getValue();
					
						if(checkBookingInDB(""+text_BookingID.getSelectedItem()))
						{
							try{
							double advance = Double.parseDouble(text_advance.getText().trim());
							if(advance > 0 && text_advance.getText().trim().length()<=0)
							{

								text_advance.requestFocus(true);
								text_advance.selectAll();
								new CustomDialog(this, "Enter the Advance Amount >0", "Error", text_advance, 75, 0, CustomDialog.ERROR_ICON);
						     
							}
							else if(text_adults.getText().trim().length()<=0)
							{
								new CustomDialog(this, "Enter the adults", "Error", text_adults, 75, 0, CustomDialog.ERROR_ICON);
								text_adults.requestFocus(true);
							}
							else if(text_children.getText().trim().length()<=0)
							{
								new CustomDialog(this, "Enter the children", "Error", text_children, 75, 0, CustomDialog.ERROR_ICON);
								text_children.requestFocus(true);
							}
							else
							{
										Timestamp timestamp = new Timestamp(System.currentTimeMillis());
										obj_checkIn.setCheckInID(checkin_service.generateCheckInId());	
										obj_checkIn.setBookingID(""+text_BookingID.getSelectedItem());
										obj_checkIn.setCheckinDate((timestamp));
										//obj_checkIn.setCustomerID(text_customerID.getText().trim().toUpperCase());
										obj_checkIn.setTotalAdults(text_adults.getText());
										obj_checkIn.setTotalChilds(text_children.getText());
										obj_checkIn.setAdvanceAmt(""+BigDecimalType.roundDown(advance));
							
					
					
										int s = checkin_controller.submitCheckIn();
										
										if(s>0)
										{
										JOptionPane.showMessageDialog(this,"Checkin Updated Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
										
										text_BookingID.requestFocus(true);
										checkin_service.retrieveAll(DatabaseConstants.TABLE_CHECK_IN_COLS);
										sbm_consignCom.db.add(""+text_BookingID.getSelectedItem());
										updateUI();
										setClear();
						 
										}
										else
										{
											JOptionPane.showMessageDialog(this,"Enter the details correctly","Failure",JOptionPane.ERROR_MESSAGE);
										}
									
								
								}
								
							}catch(NumberFormatException ee){
								text_advance.requestFocus(true);
								text_advance.selectAll();
								JOptionPane.showMessageDialog(this,"Enter the Advance Amount >0","Failure",JOptionPane.ERROR_MESSAGE);
								}
							catch (Exception ee) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(this,""+ee,"Failure",JOptionPane.ERROR_MESSAGE);
							}
							
						}	
						else
						{
							text_BookingID.requestFocus(true);
							new CustomDialog(this, "Select the Booking ID from the list", "Error", text_BookingID, 75, 0, CustomDialog.ERROR_ICON);
						}
					
				}
				else if(e.getSource()==btnSubmit)
				{
					int rowCount = tableModel.getRowCount();
					int colCount = tableModel.getColumnCount();
					int cl = 1;
						if(compareRows())
							JOptionPane.showMessageDialog(this, "There are no changes",  "Error", JOptionPane.ERROR_MESSAGE);
						else
						{
							
							Connection con = DBConnection.getDBConnection();
							try {
								PreparedStatement pst=con.prepareStatement(DatabaseConstants.UPDATE_CHECKIN);
								for(int i=0;i<rowCount;i++)
									for(int j=0;j<colCount;j++)
									{
										pst.setString(cl, ""+tableModel.getValueAt(i, j));
										if(cl==2)
										{
										SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
										java.util.Date date = sdf1.parse(""+tableModel.getValueAt(i, j));
										java.sql.Date sqlDate = new java.sql.Date(date.getTime());
										pst.setDate(cl, sqlDate);
										}
										cl++;
										
									}
								pst.setString(cl, ""+tableModel.getValueAt(0, 0));
								int s = pst.executeUpdate();
								if(s>0)
								{
									JOptionPane.showMessageDialog(this, "Record updated successfully", "Success",  JOptionPane.INFORMATION_MESSAGE);
									checkin_service.retrieveAll(DatabaseConstants.TABLE_CHECK_IN_COLS);
									combo_search.setSelectedItem("");
								}
								else
									JOptionPane.showMessageDialog(this, "Please enter the details correctly.",  "Error", JOptionPane.ERROR_MESSAGE);
										
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								JOptionPane.showMessageDialog(this, "Please enter the details correctly.",  "Error", JOptionPane.ERROR_MESSAGE);
								
							} catch (ParseException ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
							
						}

				}
				else if(e.getSource()==text_BookingID)
					{
						text_adults.requestFocus(true);
					}
			}

	public boolean compareRows(){
		int rowCount = tableModel.getRowCount();
		int colCount = tableModel.getColumnCount();
		boolean rowequals = false;
		Connection con = DBConnection.getDBConnection();
		try {
			PreparedStatement pst = con.prepareStatement(DatabaseConstants.ALL_CHECKIN_ID);
			pst.setString(1, ""+combo_search.getSelectedItem());
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
					int cl = 1;
				for(int i=0;i<rowCount;i++)
					for(int j=0;j<colCount;j++)
					{
						if(rs.getString(cl).equals(tableModel.getValueAt(i, j)))
						{
							rowequals = true;
						cl++;
						}
						else
						{
							rowequals = false;
							break;
						}
					}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return rowequals;
	}

	
	
	private static void throwError(String msg) throws Exception 
	{
		    throw new Exception(msg);
	} 

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
//		if(arg0.getSource()==text_BookingID)
//			text_BookingID.selectAll();
//		else if(arg0.getSource()==text_totalNights)
//			text_totalNights.selectAll();
//		else if(arg0.getSource()==text_totalRooms)
//			text_totalRooms.selectAll();
//		else if(arg0.getSource()==text_adults)
//			text_adults.selectAll();
//		else if(arg0.getSource()==text_childs)
//			text_childs.selectAll();
//		else if(arg0.getSource()==text_cost)
//			text_cost.selectAll();
		if(arg0.getSource()==text_customerID)
			text_customerID.selectAll();

 
 
		else {}
	}
	@Override
	public void focusLost(FocusEvent arg0) {

//		if(arg0.getSource()==text_BookingID)
//			text_BookingID.setText(text_BookingID.getText().trim().toUpperCase());
		if(arg0.getSource()==text_customerID)
			text_customerID.setText(text_customerID.getText().trim().toUpperCase());		
		


		else{}
 
	}

 
 
 
	public void uplcColor()
	{
		lblBookingID.setForeground(new Color(SetColor.cColor));
		lblCustomerID.setForeground(new Color(SetColor.cColor));
		lblAdvance.setForeground(new Color(SetColor.cColor));
		lblCheckIn.setForeground(new Color(SetColor.cColor));
		lblRows.setForeground(new Color(SetColor.cColor));
		lblChildren.setForeground(new Color(SetColor.cColor));
		lblAdults.setForeground(new Color(SetColor.cColor));
		lblRoomno.setForeground(new Color(SetColor.cColor));

		
	}
	public void paybkColor()
	{
		setBackground(new Color(SetColor.bkColor));
		table.setBackground(new Color(SetColor.bkColor));
		datePanel.setBackground(new Color(SetColor.bkColor));
		datePanel1.setBackground(new Color(SetColor.bkColor));
		datePanel2.setBackground(new Color(SetColor.bkColor));
	}
	public void uplmtColor()
	{
		lblCustomerDetails_1.setForeground(new Color(SetColor.mtColor));
		lblCheckin.setForeground(new Color(SetColor.mtColor));
	}
	public void uplbkColor()
	{
		setBackground(new Color(SetColor.bkColor));
		panel.setBackground(new Color(SetColor.bkColor));
	}
	public void uplcFont(String ctFType,int ctfProp,int ctSize)
	{
		lblCustomerID.setFont(new Font(ctFType,ctfProp,ctSize));
		lblBookingID.setFont(new Font(ctFType,ctfProp,ctSize));
		lblAdvance.setFont(new Font(ctFType,ctfProp,ctSize));
		lblCheckIn.setFont(new Font(ctFType,ctfProp,ctSize));
		lblCheckIn.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRows.setFont(new Font(ctFType,ctfProp,ctSize));
		lblChildren.setFont(new Font(ctFType,ctfProp,ctSize));
		lblAdults.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRoomno.setFont(new Font(ctFType,ctfProp,ctSize));
		
		
		btnSave.setFont(new Font(ctFType,ctfProp,ctSize));
	}
	public void uplSTFont(String stFType,int stfProp,int stSize)
	{
		lblCustomerDetails_1.setFont(new Font(stFType,stfProp,stSize));
		lblCheckin.setFont(new Font(stFType,stfProp,stSize));
	}



 

 
}
