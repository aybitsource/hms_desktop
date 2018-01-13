package com.hms.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import com.hms.controller.CheckOutController;
import com.hms.model.ReportDetails;
import com.hms.services.CheckOutService;
import com.hms.util.CheckoutSearchModel;
import com.hms.util.Constants;
import com.hms.util.DBConnection;
import com.hms.util.DatabaseConstants;
import com.hms.util.DateDifferenceCalculator;
import com.hms.util.ExcelExporter;
import com.hms.util.SearchCouponModel;
import com.hms.util.TableDialog;
import com.hms.validators.DoubleValidator;
import com.hotelmanagement.MainPage;
import com.hotelmanagement.SFont;
import com.hotelmanagement.SetColor;

public class BookingCheckout extends JPanel implements ActionListener, FocusListener{

	public static JComboBox text_bookingID;
	private JTextField text_customer_name;
	private JTextField text_mobile;
	private JTextField text_booking_date;
	private JTextField text_checkin_date;
	private JTextField text_checkout_date;
	private JTextField text_room_no;
	private JTextField text_room_type;
	private JTextField text_days;
	private JTextField text_room_cost;
	private JTextField text_facilities_cost;
	public static JTextField text_miscellaneous;
	public static String bookingID;
	//private JTextField text_net_total;
	//private JTextField text_gst;
	//private JTextField text_gross_total;
	public static JTextField text_discount;
	private JTextField text_advance;
	//private JTextField text_balance;
	
	private JLabel lblBookingDetails;
	private JLabel lblBookingId;
	private JLabel lblRoomCost;
	private JLabel lblCustomerName;
	private JLabel lblFacilitiesCost;
	private JLabel lblMobile;
	private JLabel lblMiscellaneous;
	private JLabel lblBookingDate;
	//private JLabel lblNetTotal;
	private JLabel lblCheckinDate;
	//private JLabel lblGst;
	private JLabel lblCheckoutDate;
	//private JLabel lblGrossTotal;
	private JLabel lblRoomNo;
	private JLabel lblDiscount;
	private JLabel lblRoomType;
	private JLabel lblAdvance;
	private JLabel lblNoOfDays;
	//private JLabel lblBalance;
	JButton okButton;
	JButton cancelButton;
	JButton btnPrint;
	public static boolean tglbtnTaxValue = false;
	public static String rdbtnValue = null;
	public CheckOutController checkOut_controller;
	MiscellaneousServices obj_ms = new MiscellaneousServices();
	int days = 0;
	double room_cost = 0;
	double facilities_cost = 0;
	public static double extra_person = 0;
	public static double miscellaneous_cost = 0;
	
	//double total_room_cost = 0;
	public static double booking_cost = 0;	
	
	double booking_cgst = 0;
	double booking_sgst = 0;
	double service_cgst = 0;
	double service_sgst = 0;


	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	public ReportDetails rptDet = new ReportDetails();
	private JLabel lblPaymentmode;
	private JRadioButton rdbtnCash;
	private JRadioButton rdbtnCard;
	private JToggleButton tglbtnTax;
	ButtonGroup bg;
	public static JButton btnAdd;
	public static JTextField text_extrabed;
	private JLabel lblExtraPerson;
	private JScrollPane scrollPane;
	public static JTable table;
	public static DefaultTableModel tableModel;
	String filePath;
	private JLabel transExcel;
	public CheckOutService checkOut_service;
	private JComboBox combo_search;
	public static JButton btnSubmit;
	CheckoutSearchModel sbm_consignCom;
	private JPanel panel;
	public static JLabel lblRows;
	public static JComboBox combo_coupon;
	private JLabel lblCoupon;
	public static JTextField text_extrapersons;
	private JLabel lblExtraPersons;
	private SearchCouponModel scm;
	//CheckOutService chs = new CheckOutService();
	//public BookingCheckout(String bookingID, CheckOutService chs) {
	MainPage mpg;
	public BookingCheckout(MainPage mpg) {
		//super(new JFrame(),"Check-out Form",true);
		this.mpg = mpg;
		//this.chs = chs;

		
		bg = new ButtonGroup();
//		
//		getContentPane().setBackground(Color.WHITE);
//		setBackground(Color.WHITE);
//		setSize(3*d.width/4, 3*d.height/4);
//		setLocation(d.width/8, d.height/8);
//		getContentPane().setLayout(new BorderLayout());
//		setBorder(new EmptyBorder(5, 5, 5, 5));
//		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gbl_panel);
		{
			combo_search = new JComboBox();
			GridBagConstraints gbc_combo_search = new GridBagConstraints();
			gbc_combo_search.anchor = GridBagConstraints.SOUTHEAST;
			gbc_combo_search.insets = new Insets(0, 0, 5, 5);
			gbc_combo_search.gridx = 7;
			gbc_combo_search.gridy = 0;
			combo_search.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

			    @Override
			    public void keyReleased(KeyEvent event) {
			        if (event.getKeyChar() == KeyEvent.VK_ENTER) {
			            if (((JTextComponent) ((JComboBox) ((Component) event
			                    .getSource()).getParent()).getEditor()
			                    .getEditorComponent()).getText().isEmpty())
			            {
			            	checkOut_service.retrieveCheckOutDetails();
			            }
			            else
			            {		            

			            	checkOut_service.retrieveCheckOutTransaction(DatabaseConstants.ALL_CHECKOUT_ID, ""+combo_search.getSelectedItem());
			            }
			            	
			        }
			    }
			});
			combo_search.setMaximumRowCount(10);
			combo_search.setEditable(true);
			
			if(MainPage.user_role.equals(Constants.ADMIN))
			sbm_consignCom = new CheckoutSearchModel(combo_search, DatabaseConstants.SELECT_CHECKOUT_BOOKING_ID);
			else
			sbm_consignCom = new CheckoutSearchModel(combo_search, DatabaseConstants.SELECT_CHECKOUT_BOOKING_ID_USER);
			
			combo_search.setModel(sbm_consignCom);
			combo_search.addItemListener(sbm_consignCom);
			combo_search.addPopupMenuListener(sbm_consignCom);
			{
				lblRows = new JLabel("lblRows");
				GridBagConstraints gbc_lblRows = new GridBagConstraints();
				gbc_lblRows.insets = new Insets(0, 0, 5, 5);
				gbc_lblRows.anchor = GridBagConstraints.WEST;
				gbc_lblRows.gridx = 6;
				gbc_lblRows.gridy = 0;
				add(lblRows, gbc_lblRows);
			}

			add(combo_search, gbc_combo_search);
		}
		{
			btnSubmit = new JButton("Submit");
			GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
			gbc_btnSubmit.insets = new Insets(0, 0, 5, 5);
			gbc_btnSubmit.gridx = 8;
			gbc_btnSubmit.gridy = 0;
			add(btnSubmit, gbc_btnSubmit);
			btnSubmit.setVisible(false);
		}
		{
			transExcel = new JLabel();
			transExcel.setIcon(new ImageIcon(BookingCheckout.class.getResource("/images/excel.png")));
			GridBagConstraints gbc_transExcel = new GridBagConstraints();
			gbc_transExcel.insets = new Insets(0, 0, 5, 0);
			gbc_transExcel.gridx = 9;
			gbc_transExcel.gridy = 0;
			add(transExcel, gbc_transExcel);
		}
		

		{
			lblBookingDetails = new JLabel("Check Out");
			GridBagConstraints gbc_lblBookingDetails = new GridBagConstraints();
			gbc_lblBookingDetails.anchor = GridBagConstraints.SOUTHWEST;
			gbc_lblBookingDetails.gridwidth = 5;
			gbc_lblBookingDetails.insets = new Insets(0, 0, 5, 5);
			gbc_lblBookingDetails.gridx = 1;
			gbc_lblBookingDetails.gridy = 1;
			add(lblBookingDetails, gbc_lblBookingDetails);
		}
		tableModel = new DefaultTableModel(Constants.checkOutEntryNames, 0);
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
	    header.setToolTipStrings(Constants.checkOutEntryTipStr);
	    header.setToolTipText("Default ToolTip TEXT");
	    table.setTableHeader(header);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setFont(new Font("Tahoma",Font.PLAIN,14));
		if(MainPage.user_role.equals(Constants.ADMIN)&&MainPage.user_mode.equals(Constants.MODE_OFF))
			TableDialog.tableRecordDetails(mpg, table, DatabaseConstants.SELECT_CHECKOUT_BOOKING_ID);		
		else			
			TableDialog.tableRecordDetails(mpg, table, DatabaseConstants.SELECT_CHECKOUT_BOOKING_ID_USER);
		//table.getColumn("SL NO").setMaxWidth(50);
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.gridheight = 12;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 6;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);
		
		table.setForeground(new Color(SetColor.cColor));
		setBackground(new Color(SetColor.bkColor));
		table.setBackground(new Color(SetColor.bkColor));
		table.setFont(new Font(SFont.ctFType,SFont.ctfProp, SFont.ctSize));
 
		
		checkOut_service = new CheckOutService(tableModel, table);
		checkOut_service.retrieveCheckOutDetails();
		
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
                          ExcelExporter.fillData(table, filePath,"Checkout Details");
                          JOptionPane.showMessageDialog(null, "Data saved at "+filePath+" successfully", "Message",JOptionPane.INFORMATION_MESSAGE);
                          Desktop.getDesktop().open(new File(filePath));
            		}                } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});

		{
			lblBookingId = new JLabel("Booking ID");
			GridBagConstraints gbc_lblBookingId = new GridBagConstraints();
			gbc_lblBookingId.fill = GridBagConstraints.BOTH;
			gbc_lblBookingId.insets = new Insets(0, 0, 5, 5);
			gbc_lblBookingId.gridx = 1;
			gbc_lblBookingId.gridy = 2;
			add(lblBookingId, gbc_lblBookingId);
		}
		{
			text_bookingID = new JComboBox();
			text_bookingID.setEditable(false);
			GridBagConstraints gbc_text_bookingID = new GridBagConstraints();
			gbc_text_bookingID.insets = new Insets(0, 0, 5, 5);
			gbc_text_bookingID.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_bookingID.gridx = 2;
			gbc_text_bookingID.gridy = 2;
			//text_bookingID.setEditable(true);
			sbm_consignCom = new CheckoutSearchModel(text_bookingID, DatabaseConstants.CHECKOUT_BOOKING_ID, Constants.CHECKIN, this);
			text_bookingID.setModel(sbm_consignCom);
			text_bookingID.addItemListener(sbm_consignCom);
			text_bookingID.addPopupMenuListener(sbm_consignCom);
			//text_bookingID.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXX");
			add(text_bookingID, gbc_text_bookingID);
			text_bookingID.requestFocus(true);
			if(bookingID!=null)
				sbm_consignCom.cb.setSelectedItem(bookingID);
//			text_bookingID.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
//
//			    @Override
//			    public void keyReleased(KeyEvent event) {
//			        if (event.getKeyChar() == KeyEvent.VK_ENTER) {
//			            if (((JTextComponent) ((JComboBox) ((Component) event
//			                    .getSource()).getParent()).getEditor()
//			                    .getEditorComponent()).getText().isEmpty())
//			            {
//			            	checkOut_service.retrieveCheckOutDetails();
//			            }
//			            else
//			            {		            	
//			        		
//			        		rptDet = checkOut_service.retrieveCheckOutDetails(""+text_bookingID.getSelectedItem());
//			        		checkOut_controller = new CheckOutController(rptDet);
//			        		setData(rptDet);
//			        		
//			            }
//			            	
//			        }
//			    }
//			});
			//text_bookingID.setText(bookingID);
		}
		{
			text_facilities_cost = new JTextField();
			text_facilities_cost.setEditable(false);
			GridBagConstraints gbc_text_facilities_cost = new GridBagConstraints();
			gbc_text_facilities_cost.gridwidth = 2;
			gbc_text_facilities_cost.insets = new Insets(0, 0, 5, 5);
			gbc_text_facilities_cost.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_facilities_cost.gridx = 4;
			gbc_text_facilities_cost.gridy = 2;
			add(text_facilities_cost, gbc_text_facilities_cost);
			text_facilities_cost.setColumns(10);

		}
		{
			lblExtraPersons = new JLabel("Extra Persons");
			GridBagConstraints gbc_lblExtraPersons = new GridBagConstraints();
			gbc_lblExtraPersons.insets = new Insets(0, 0, 5, 5);
			gbc_lblExtraPersons.anchor = GridBagConstraints.WEST;
			gbc_lblExtraPersons.gridx = 3;
			gbc_lblExtraPersons.gridy = 4;
			add(lblExtraPersons, gbc_lblExtraPersons);
		}
		{
			text_extrapersons = new JTextField();
			GridBagConstraints gbc_text_extrapersons = new GridBagConstraints();
			gbc_text_extrapersons.gridwidth = 2;
			gbc_text_extrapersons.insets = new Insets(0, 0, 5, 5);
			gbc_text_extrapersons.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_extrapersons.gridx = 4;
			gbc_text_extrapersons.gridy = 4;
			add(text_extrapersons, gbc_text_extrapersons);
			text_extrapersons.setColumns(10);
			text_extrapersons.setEditable(false);
		}
		{
			lblExtraPerson = new JLabel("Charges");
			GridBagConstraints gbc_lblExtraPerson = new GridBagConstraints();
			gbc_lblExtraPerson.fill = GridBagConstraints.BOTH;
			gbc_lblExtraPerson.insets = new Insets(0, 0, 5, 5);
			gbc_lblExtraPerson.gridx = 3;
			gbc_lblExtraPerson.gridy = 5;
			add(lblExtraPerson, gbc_lblExtraPerson);
		}
		{
			text_extrabed = new JTextField();
			GridBagConstraints gbc_text_extrabed = new GridBagConstraints();
			gbc_text_extrabed.gridwidth = 2;
			gbc_text_extrabed.insets = new Insets(0, 0, 5, 5);
			gbc_text_extrabed.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_extrabed.gridx = 4;
			gbc_text_extrabed.gridy = 5;
			add(text_extrabed, gbc_text_extrabed);
			text_extrabed.setColumns(10);
			text_extrabed.setInputVerifier(new DoubleValidator(null, text_extrabed, "Enter only numeric values > 0"));
			text_extrabed.getDocument().addDocumentListener(new DocumentListener(){				
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					// TODO Auto-generated method stub
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					// TODO Auto-generated method stub
					
					
				}				
			});
		}
		{
			lblCoupon = new JLabel("Coupon");
			GridBagConstraints gbc_lblCoupon = new GridBagConstraints();
			gbc_lblCoupon.insets = new Insets(0, 0, 5, 5);
			gbc_lblCoupon.anchor = GridBagConstraints.WEST;
			gbc_lblCoupon.gridx = 3;
			gbc_lblCoupon.gridy = 7;
			add(lblCoupon, gbc_lblCoupon);
		}
		{
			combo_coupon = new JComboBox();
			GridBagConstraints gbc_combo_coupon = new GridBagConstraints();
			gbc_combo_coupon.gridwidth = 2;
			gbc_combo_coupon.insets = new Insets(0, 0, 5, 5);
			gbc_combo_coupon.fill = GridBagConstraints.HORIZONTAL;
			gbc_combo_coupon.gridx = 4;
			gbc_combo_coupon.gridy = 7;
			combo_coupon.setEditable(true);
			scm = new SearchCouponModel(combo_coupon, DatabaseConstants.COUPON_TYPE, Constants.COUPON_TYPE_GENERAL);
			combo_coupon.setModel(scm);
			
			if(seasonCoupon()!=null)
				scm.db.add(seasonCoupon());
			
			combo_coupon.addItemListener(scm);
			combo_coupon.addPopupMenuListener(scm);
			add(combo_coupon, gbc_combo_coupon);
		}
		{
			panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.HORIZONTAL;
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.gridx = 4;
			gbc_panel.gridy = 9;
			add(panel, gbc_panel);
			GridBagLayout gbl_panel1 = new GridBagLayout();
			gbl_panel1.columnWidths = new int[]{0, 0, 0};
			gbl_panel1.rowHeights = new int[]{0, 0};
			gbl_panel1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_panel1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel1);
			{
				rdbtnCash = new JRadioButton("CASH");
				GridBagConstraints gbc_rdbtnCash = new GridBagConstraints();
				gbc_rdbtnCash.fill = GridBagConstraints.HORIZONTAL;
				gbc_rdbtnCash.insets = new Insets(0, 0, 0, 5);
				gbc_rdbtnCash.gridx = 0;
				gbc_rdbtnCash.gridy = 0;
				panel.add(rdbtnCash, gbc_rdbtnCash);
			}
			bg.add(rdbtnCash);
			{
				rdbtnCard = new JRadioButton("CARD");
				GridBagConstraints gbc_rdbtnCard = new GridBagConstraints();
				gbc_rdbtnCard.fill = GridBagConstraints.HORIZONTAL;
				gbc_rdbtnCard.gridx = 1;
				gbc_rdbtnCard.gridy = 0;
				panel.add(rdbtnCard, gbc_rdbtnCard);
			}
			bg.add(rdbtnCard);
			rdbtnCard.addActionListener(this);
			rdbtnCash.addActionListener(this);
		}
		
		{
			tglbtnTax = new JToggleButton("No Tax");
			GridBagConstraints gbc_tglbtnTax = new GridBagConstraints();
			gbc_tglbtnTax.fill = GridBagConstraints.HORIZONTAL;
			gbc_tglbtnTax.insets = new Insets(0, 0, 5, 5);
			gbc_tglbtnTax.gridx = 5;
			gbc_tglbtnTax.gridy = 9;
			add(tglbtnTax, gbc_tglbtnTax);
			tglbtnTax.setCursor(new Cursor(Cursor.HAND_CURSOR));

		}
		
		tglbtnTax.setVisible(false);
		tglbtnTax.addActionListener(this);
		{
			lblRoomCost = new JLabel("Room Cost");
			GridBagConstraints gbc_lblRoomCost = new GridBagConstraints();
			gbc_lblRoomCost.fill = GridBagConstraints.BOTH;
			gbc_lblRoomCost.insets = new Insets(0, 0, 5, 5);
			gbc_lblRoomCost.gridx = 1;
			gbc_lblRoomCost.gridy = 11;
			add(lblRoomCost, gbc_lblRoomCost);
		}
		{
			text_room_cost = new JTextField();
			text_room_cost.setEditable(false);
			GridBagConstraints gbc_text_room_cost = new GridBagConstraints();
			gbc_text_room_cost.insets = new Insets(0, 0, 5, 5);
			gbc_text_room_cost.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_room_cost.gridx = 2;
			gbc_text_room_cost.gridy = 11;
			add(text_room_cost, gbc_text_room_cost);
			text_room_cost.setColumns(10);
			
		}
		{
			lblCustomerName = new JLabel("Customer Name");
			GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
			gbc_lblCustomerName.fill = GridBagConstraints.BOTH;
			gbc_lblCustomerName.insets = new Insets(0, 0, 5, 5);
			gbc_lblCustomerName.gridx = 1;
			gbc_lblCustomerName.gridy = 3;
			add(lblCustomerName, gbc_lblCustomerName);
		}
		{
			text_customer_name = new JTextField();
			text_customer_name.setEditable(false);
			GridBagConstraints gbc_text_customer_name = new GridBagConstraints();
			gbc_text_customer_name.insets = new Insets(0, 0, 5, 5);
			gbc_text_customer_name.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_customer_name.gridx = 2;
			gbc_text_customer_name.gridy = 3;
			add(text_customer_name, gbc_text_customer_name);
			text_customer_name.setColumns(10);
			
		}
		{
			lblFacilitiesCost = new JLabel("Facilities Cost");
			GridBagConstraints gbc_lblFacilitiesCost = new GridBagConstraints();
			gbc_lblFacilitiesCost.fill = GridBagConstraints.BOTH;
			gbc_lblFacilitiesCost.insets = new Insets(0, 0, 5, 5);
			gbc_lblFacilitiesCost.gridx = 3;
			gbc_lblFacilitiesCost.gridy = 2;
			add(lblFacilitiesCost, gbc_lblFacilitiesCost);
		}
		{
			lblMobile = new JLabel("Mobile Number");
			GridBagConstraints gbc_lblMobile = new GridBagConstraints();
			gbc_lblMobile.fill = GridBagConstraints.BOTH;
			gbc_lblMobile.insets = new Insets(0, 0, 5, 5);
			gbc_lblMobile.gridx = 1;
			gbc_lblMobile.gridy = 4;
			add(lblMobile, gbc_lblMobile);
		}
		{
			text_mobile = new JTextField();
			text_mobile.setEditable(false);
			GridBagConstraints gbc_text_mobile = new GridBagConstraints();
			gbc_text_mobile.insets = new Insets(0, 0, 5, 5);
			gbc_text_mobile.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_mobile.gridx = 2;
			gbc_text_mobile.gridy = 4;
			add(text_mobile, gbc_text_mobile);
			text_mobile.setColumns(10);
			
		}
		{
			lblMiscellaneous = new JLabel("Miscellaneous");
			GridBagConstraints gbc_lblMiscellaneous = new GridBagConstraints();
			gbc_lblMiscellaneous.fill = GridBagConstraints.BOTH;
			gbc_lblMiscellaneous.insets = new Insets(0, 0, 5, 5);
			gbc_lblMiscellaneous.gridx = 3;
			gbc_lblMiscellaneous.gridy = 6;
			add(lblMiscellaneous, gbc_lblMiscellaneous);
		}
		{
			text_miscellaneous = new JTextField();
			text_miscellaneous.setEditable(false);
			GridBagConstraints gbc_text_miscellaneous = new GridBagConstraints();
			gbc_text_miscellaneous.insets = new Insets(0, 0, 5, 5);
			gbc_text_miscellaneous.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_miscellaneous.gridx = 4;
			gbc_text_miscellaneous.gridy = 6;
			add(text_miscellaneous, gbc_text_miscellaneous);
			text_miscellaneous.setColumns(10);
			text_miscellaneous.setInputVerifier(new DoubleValidator(null, text_miscellaneous, "Enter only numeric values > 0"));
			text_miscellaneous.getDocument().addDocumentListener(new DocumentListener(){
				
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					// TODO Auto-generated method stub
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					// TODO Auto-generated method stub
					
//					days = Integer.parseInt(text_days.getText());
//					room_cost = Double.parseDouble(text_room_cost.getText());
//					facilities_cost = Double.parseDouble(text_facilities_cost.getText());
//					extra_person = Double.parseDouble(text_extrabed.getText());
//					miscellaneous_cost = Double.parseDouble(text_miscellaneous.getText());
//					
//					total_room_cost = days*(room_cost+facilities_cost);
//					booking_cost = total_room_cost + extra_person;	
					
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					// TODO Auto-generated method stub
					
					
				}

				
			});
		}
		{
			btnAdd = new JButton("+");
			GridBagConstraints gbc_btnAdd = new GridBagConstraints();
			gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
			gbc_btnAdd.gridx = 5;
			gbc_btnAdd.gridy = 6;
			add(btnAdd, gbc_btnAdd);
			btnAdd.addActionListener(this);
		}
		{
			lblBookingDate = new JLabel("Booking Date");
			GridBagConstraints gbc_lblBookingDate = new GridBagConstraints();
			gbc_lblBookingDate.fill = GridBagConstraints.BOTH;
			gbc_lblBookingDate.insets = new Insets(0, 0, 5, 5);
			gbc_lblBookingDate.gridx = 1;
			gbc_lblBookingDate.gridy = 5;
			add(lblBookingDate, gbc_lblBookingDate);
		}
		{
			text_booking_date = new JTextField();
			text_booking_date.setEditable(false);
			GridBagConstraints gbc_text_booking_date = new GridBagConstraints();
			gbc_text_booking_date.insets = new Insets(0, 0, 5, 5);
			gbc_text_booking_date.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_booking_date.gridx = 2;
			gbc_text_booking_date.gridy = 5;
			add(text_booking_date, gbc_text_booking_date);
			text_booking_date.setColumns(10);
			
		}
//		{
//			lblNetTotal = new JLabel("Net Total");
//			GridBagConstraints gbc_lblNetTotal = new GridBagConstraints();
//			gbc_lblNetTotal.fill = GridBagConstraints.BOTH;
//			gbc_lblNetTotal.insets = new Insets(0, 0, 5, 5);
//			gbc_lblNetTotal.gridx = 4;
//			gbc_lblNetTotal.gridy = 6;
//			//add(lblNetTotal, gbc_lblNetTotal);
//		}
//		{
//			text_net_total = new JTextField();
//			text_net_total.setEditable(false);
//			GridBagConstraints gbc_text_net_total = new GridBagConstraints();
//			gbc_text_net_total.gridwidth = 2;
//			gbc_text_net_total.insets = new Insets(0, 0, 5, 5);
//			gbc_text_net_total.fill = GridBagConstraints.HORIZONTAL;
//			gbc_text_net_total.gridx = 5;
//			gbc_text_net_total.gridy = 6;
//			//add(text_net_total, gbc_text_net_total);
//			text_net_total.setColumns(10);
//			//text_net_total.setText(rptDet.getNet_amount());
//		}
		{
			lblCheckinDate = new JLabel("Check-In Date");
			GridBagConstraints gbc_lblCheckinDate = new GridBagConstraints();
			gbc_lblCheckinDate.fill = GridBagConstraints.BOTH;
			gbc_lblCheckinDate.insets = new Insets(0, 0, 5, 5);
			gbc_lblCheckinDate.gridx = 1;
			gbc_lblCheckinDate.gridy = 6;
			add(lblCheckinDate, gbc_lblCheckinDate);
		}
		{
			text_checkin_date = new JTextField();
			text_checkin_date.setEditable(false);
			GridBagConstraints gbc_text_checkin_date = new GridBagConstraints();
			gbc_text_checkin_date.insets = new Insets(0, 0, 5, 5);
			gbc_text_checkin_date.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_checkin_date.gridx = 2;
			gbc_text_checkin_date.gridy = 6;
			add(text_checkin_date, gbc_text_checkin_date);
			text_checkin_date.setColumns(10);
			
		}
//		{
//			lblGst = new JLabel("GST ");
//			GridBagConstraints gbc_lblGst = new GridBagConstraints();
//			gbc_lblGst.fill = GridBagConstraints.BOTH;
//			gbc_lblGst.insets = new Insets(0, 0, 5, 5);
//			gbc_lblGst.gridx = 4;
//			gbc_lblGst.gridy = 7;
//			//add(lblGst, gbc_lblGst);
//		}
//		{
//			text_gst = new JTextField();
//			text_gst.setEditable(false);
//			GridBagConstraints gbc_text_gst = new GridBagConstraints();
//			gbc_text_gst.gridwidth = 2;
//			gbc_text_gst.insets = new Insets(0, 0, 5, 5);
//			gbc_text_gst.fill = GridBagConstraints.HORIZONTAL;
//			gbc_text_gst.gridx = 5;
//			gbc_text_gst.gridy = 7;
//			//add(text_gst, gbc_text_gst);
//			text_gst.setColumns(10);
//			//text_gst.setText(rptDet.getGstValue());
//		}
		{
			lblCheckoutDate = new JLabel("Check-Out Date");
			GridBagConstraints gbc_lblCheckoutDate = new GridBagConstraints();
			gbc_lblCheckoutDate.fill = GridBagConstraints.BOTH;
			gbc_lblCheckoutDate.insets = new Insets(0, 0, 5, 5);
			gbc_lblCheckoutDate.gridx = 1;
			gbc_lblCheckoutDate.gridy = 7;
			add(lblCheckoutDate, gbc_lblCheckoutDate);
		}
		{
			text_checkout_date = new JTextField();
			text_checkout_date.setEditable(false);
			GridBagConstraints gbc_text_checkout_date = new GridBagConstraints();
			gbc_text_checkout_date.insets = new Insets(0, 0, 5, 5);
			gbc_text_checkout_date.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_checkout_date.gridx = 2;
			gbc_text_checkout_date.gridy = 7;
			add(text_checkout_date, gbc_text_checkout_date);
			text_checkout_date.setColumns(10);
			
		}
//		{
//			lblGrossTotal = new JLabel("Gross Total");
//			GridBagConstraints gbc_lblGrossTotal = new GridBagConstraints();
//			gbc_lblGrossTotal.fill = GridBagConstraints.BOTH;
//			gbc_lblGrossTotal.insets = new Insets(0, 0, 5, 5);
//			gbc_lblGrossTotal.gridx = 4;
//			gbc_lblGrossTotal.gridy = 8;
//			//add(lblGrossTotal, gbc_lblGrossTotal);
//		}
//		{
//			text_gross_total = new JTextField();
//			text_gross_total.setEditable(false);
//			GridBagConstraints gbc_text_gross_total = new GridBagConstraints();
//			gbc_text_gross_total.gridwidth = 2;
//			gbc_text_gross_total.insets = new Insets(0, 0, 5, 5);
//			gbc_text_gross_total.fill = GridBagConstraints.HORIZONTAL;
//			gbc_text_gross_total.gridx = 5;
//			gbc_text_gross_total.gridy = 8;
//			//add(text_gross_total, gbc_text_gross_total);
//			text_gross_total.setColumns(10);
//			//text_gross_total.setText(rptDet.getGross_amount());
//		}
		{
			lblRoomNo = new JLabel("Room No.");
			GridBagConstraints gbc_lblRoomNo = new GridBagConstraints();
			gbc_lblRoomNo.fill = GridBagConstraints.BOTH;
			gbc_lblRoomNo.insets = new Insets(0, 0, 5, 5);
			gbc_lblRoomNo.gridx = 1;
			gbc_lblRoomNo.gridy = 8;
			add(lblRoomNo, gbc_lblRoomNo);
		}
		{
			text_room_no = new JTextField();
			text_room_no.setEditable(false);
			GridBagConstraints gbc_text_room_no = new GridBagConstraints();
			gbc_text_room_no.insets = new Insets(0, 0, 5, 5);
			gbc_text_room_no.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_room_no.gridx = 2;
			gbc_text_room_no.gridy = 8;
			add(text_room_no, gbc_text_room_no);
			text_room_no.setColumns(10);
			
		}
		{
			lblDiscount = new JLabel("Discount");
			GridBagConstraints gbc_lblDiscount = new GridBagConstraints();
			gbc_lblDiscount.fill = GridBagConstraints.BOTH;
			gbc_lblDiscount.insets = new Insets(0, 0, 5, 5);
			gbc_lblDiscount.gridx = 3;
			gbc_lblDiscount.gridy = 8;
			add(lblDiscount, gbc_lblDiscount);
		}
		{
			text_discount = new JTextField();
			text_discount.setEditable(false);
			GridBagConstraints gbc_text_discount = new GridBagConstraints();
			gbc_text_discount.gridwidth = 2;
			gbc_text_discount.insets = new Insets(0, 0, 5, 5);
			gbc_text_discount.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_discount.gridx = 4;
			gbc_text_discount.gridy = 8;
			add(text_discount, gbc_text_discount);
			text_discount.setColumns(10);
			
			
		}
		{
			lblRoomType = new JLabel("Room Type");
			GridBagConstraints gbc_lblRoomType = new GridBagConstraints();
			gbc_lblRoomType.fill = GridBagConstraints.BOTH;
			gbc_lblRoomType.insets = new Insets(0, 0, 5, 5);
			gbc_lblRoomType.gridx = 1;
			gbc_lblRoomType.gridy = 9;
			add(lblRoomType, gbc_lblRoomType);
		}
		{
			text_room_type = new JTextField();
			text_room_type.setEditable(false);
			GridBagConstraints gbc_text_room_type = new GridBagConstraints();
			gbc_text_room_type.insets = new Insets(0, 0, 5, 5);
			gbc_text_room_type.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_room_type.gridx = 2;
			gbc_text_room_type.gridy = 9;
			add(text_room_type, gbc_text_room_type);
			text_room_type.setColumns(10);
			
		}
		{
			lblAdvance = new JLabel("Advance Paid");
			GridBagConstraints gbc_lblAdvance = new GridBagConstraints();
			gbc_lblAdvance.fill = GridBagConstraints.BOTH;
			gbc_lblAdvance.insets = new Insets(0, 0, 5, 5);
			gbc_lblAdvance.gridx = 3;
			gbc_lblAdvance.gridy = 3;
			add(lblAdvance, gbc_lblAdvance);
		}
		{
			text_advance = new JTextField();
			text_advance.setEditable(false);
			GridBagConstraints gbc_text_advance = new GridBagConstraints();
			gbc_text_advance.gridwidth = 2;
			gbc_text_advance.insets = new Insets(0, 0, 5, 5);
			gbc_text_advance.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_advance.gridx = 4;
			gbc_text_advance.gridy = 3;
			add(text_advance, gbc_text_advance);
			text_advance.setColumns(10);
			
		}
		{
			lblNoOfDays = new JLabel("No. of Days");
			GridBagConstraints gbc_lblNoOfDays = new GridBagConstraints();
			gbc_lblNoOfDays.fill = GridBagConstraints.BOTH;
			gbc_lblNoOfDays.insets = new Insets(0, 0, 5, 5);
			gbc_lblNoOfDays.gridx = 1;
			gbc_lblNoOfDays.gridy = 10;
			add(lblNoOfDays, gbc_lblNoOfDays);
		}
		{
			text_days = new JTextField();
			text_days.setEditable(false);
			GridBagConstraints gbc_text_days = new GridBagConstraints();
			gbc_text_days.insets = new Insets(0, 0, 5, 5);
			gbc_text_days.fill = GridBagConstraints.HORIZONTAL;
			gbc_text_days.gridx = 2;
			gbc_text_days.gridy = 10;
			add(text_days, gbc_text_days);
			text_days.setColumns(10);
				
		}
//		{
//			lblBalance = new JLabel("Balance");
//			GridBagConstraints gbc_lblBalance = new GridBagConstraints();
//			gbc_lblBalance.fill = GridBagConstraints.BOTH;
//			gbc_lblBalance.insets = new Insets(0, 0, 5, 5);
//			gbc_lblBalance.gridx = 4;
//			gbc_lblBalance.gridy = 11;
//			//add(lblBalance, gbc_lblBalance);
//		}
//		{
//			text_balance = new JTextField();
//			text_balance.setEditable(false);
//			GridBagConstraints gbc_text_balance = new GridBagConstraints();
//			gbc_text_balance.gridwidth = 2;
//			gbc_text_balance.insets = new Insets(0, 0, 5, 5);
//			gbc_text_balance.fill = GridBagConstraints.HORIZONTAL;
//			gbc_text_balance.gridx = 5;
//			gbc_text_balance.gridy = 11;
//			//add(text_balance, gbc_text_balance);
//			text_balance.setColumns(10);
//			double advance_discount = Double.parseDouble(rptDet.getAdvance());
//			double discount = checkOut_controller.getCouponDiscount(rptDet.getCouponName());
//			//advance_discount = advance_discount + discount;
//			//double balanceAmount = Double.parseDouble(rptDet.getGross_amount()) - advance_discount;
//			//text_balance.setText(""+BigDecimalType.roundDown(balanceAmount));
//		}
		{
			lblPaymentmode = new JLabel("Payment Mode");
			GridBagConstraints gbc_lblPaymentmode = new GridBagConstraints();
			gbc_lblPaymentmode.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblPaymentmode.insets = new Insets(0, 0, 5, 5);
			gbc_lblPaymentmode.gridx = 3;
			gbc_lblPaymentmode.gridy = 9;
			add(lblPaymentmode, gbc_lblPaymentmode);
		}
		{

				okButton = new JButton("Checkout");
				GridBagConstraints gbc_btnCheckOut = new GridBagConstraints();
				gbc_btnCheckOut.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnCheckOut.insets = new Insets(0, 0, 5, 5);
				gbc_btnCheckOut.gridx = 4;
				gbc_btnCheckOut.gridy = 10;
				add(okButton, gbc_btnCheckOut);
				okButton.addActionListener(this);
				
				
				cancelButton = new JButton("Clear");
				GridBagConstraints gbc_btnClear = new GridBagConstraints();
				gbc_btnClear.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnClear.insets = new Insets(0, 0, 5, 5);
				gbc_btnClear.gridx = 5;
				gbc_btnClear.gridy = 10;
				add(cancelButton, gbc_btnClear);
				cancelButton.addActionListener(this);
				
				btnPrint = new JButton("Print");
				GridBagConstraints gbc_btnPrint = new GridBagConstraints();
				gbc_btnPrint.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnPrint.gridwidth = 2;
				gbc_btnPrint.insets = new Insets(0, 0, 5, 5);
				gbc_btnPrint.gridx = 4;
				gbc_btnPrint.gridy = 11;
				btnPrint.addActionListener(this);
				add(btnPrint, gbc_btnPrint);
				btnPrint.setEnabled(false);
				

			
		}


		paybkColor();
		uplmtColor();
		uplcColor();
		uplcFont(SFont.ctFType,SFont.ctfProp,SFont.ctSize);
		uplSTFont(SFont.stFType,SFont.stfProp,SFont.stSize);
//		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	public void setData(ReportDetails rptDet)
	{
		text_facilities_cost.setText(rptDet.getFacilitiesCost());
		text_room_cost.setText(rptDet.getRoomCost());
		text_customer_name.setText(rptDet.getCustomerName());
		text_mobile.setText(rptDet.getMobileNumber());
		text_booking_date.setText(""+rptDet.getBookedDate());
		text_checkin_date.setText(""+rptDet.getCheckinDate());
		text_checkout_date.setText(""+rptDet.getCheckoutDate());
		text_room_no.setText(rptDet.getRoomNo());
		//text_discount.setText(rptDet.getDiscount());
		text_room_type.setText(rptDet.getRoomType());
		text_advance.setText(rptDet.getAdvancePaid());  
		Date current_date = new Date();
		Date checkin_date = null;
		try {
			checkin_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rptDet.getCheckinDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int days = Integer.parseInt(DateDifferenceCalculator.calculateTimePeriod(checkin_date, current_date));
		if(days == 0)
			days = 1;
		text_days.setText(rptDet.getDays()+ "  "+days);
		rptDet.setDays(""+days);
		
	}
	private String seasonCoupon() {
		Connection con = DBConnection.getDBConnection();
		String couponName = null;
		
		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rk=stmt.executeQuery(DatabaseConstants.SEASONS_COUPONS);
			java.util.Date currentDate = new java.util.Date();
			while(rk.next())
			{
				java.util.Date startDate = new java.util.Date(rk.getDate(1).getTime());
				java.util.Date endDate = new java.util.Date(rk.getDate(2).getTime());
				if(startDate.compareTo(currentDate) * currentDate.compareTo(endDate) > 0)
				{
					couponName = rk.getString(3);
					//coupon_discount = Double.parseDouble(rk.getString(4));
				}
			}
			//System.out.println("coupon name is"+couponName);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			return couponName;	
	}
	

	
	public void setClear()
	{
		text_extrapersons.setText("");
		text_extrabed.setText("");
		text_bookingID.setSelectedItem("");
		text_facilities_cost.setText("");
		text_room_cost.setText("");
		text_customer_name.setText("");
		text_mobile.setText("");
		text_booking_date.setText("");
		text_checkin_date.setText("");
		text_checkout_date.setText("");
		text_room_no.setText("");
		text_discount.setText("");
		text_room_type.setText("");
		text_advance.setText("");
		text_days.setText("");
		sbm_consignCom.cb.setSelectedItem("");
		scm.cb.setSelectedItem("NO_DISCOUNT");
		bg.clearSelection();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==okButton)
		{	
			int s=0;
			if(rdbtnValue != null)
			{
						   
				
				if(text_extrabed.getInputVerifier().verify(text_extrabed))
				 {				
					
						   try{	
							   if(text_miscellaneous.getText().trim().length()==0)
								   miscellaneous_cost = 0;
							   else
								   miscellaneous_cost = Double.parseDouble(text_miscellaneous.getText());
							   if(text_days.getText().length() > 5)
								   days = 1;
							   else
								   days = Integer.parseInt(text_days.getText());
								room_cost = Double.parseDouble(text_room_cost.getText());
								facilities_cost = Double.parseDouble(text_facilities_cost.getText());
								extra_person = Double.parseDouble(text_extrabed.getText());
								
								
								booking_cost = days*(room_cost+facilities_cost+extra_person);
							   
								if(checkCouponInDB(""+combo_coupon.getSelectedItem()))
								{ 
									s = checkOut_controller.submitService();
								}
								else
								{
									combo_coupon.requestFocus(true);
									JOptionPane.showMessageDialog(this,"Select coupon from the list","Failure",JOptionPane.ERROR_MESSAGE);
								}
							
								if(s>0)
								{
					
								rdbtnValue = null;
								tglbtnTaxValue = false;								
								checkOut_service.retrieveCheckOutDetails();
								table.updateUI();								
								btnPrint.setEnabled(true);
								okButton.setEnabled(false);
								cancelButton.setEnabled(false);
								setClear();
								setClear();
								sbm_consignCom.db.remove(text_bookingID.getSelectedItem());
								updateUI();
								}
								else
								{
									JOptionPane.showMessageDialog(this,"Enter the details correctly","Failure",JOptionPane.ERROR_MESSAGE);
								}
							
								}catch(NumberFormatException ee){
									text_bookingID.requestFocus(true);
									System.out.println(ee);
									JOptionPane.showMessageDialog(this,"Enter the values correctly","Failure",JOptionPane.ERROR_MESSAGE);
								}catch (Exception ee) {
									// TODO Auto-generated catch block
									//JOptionPane.showMessageDialog(this,"Enter the details correctly","Failure",JOptionPane.ERROR_MESSAGE);
									ee.printStackTrace();
								}
							 }
							
			}
			else
			{
				JOptionPane.showMessageDialog(this,"Select the payment mode","Failure",JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else if(e.getSource()==btnPrint)
		{
			checkOut_controller.generateReport(mpg);			
		}
		else if(e.getSource()==btnAdd)
		{
			obj_ms.text_roomFacilitiesName.requestFocus(true);
			obj_ms.setVisible(true);			
		}
		else if(e.getSource()==cancelButton)
		{
			setClear();
			setClear();
		}
		else if(e.getSource()==rdbtnCash)
		{
			if(MainPage.user_role.equals(Constants.ADMIN)&&MainPage.user_mode.equals(Constants.MODE_OFF))
				tglbtnTax.setVisible(true);
			else if(MainPage.user_role.equals(Constants.USER)&&MainPage.user_mode.equals(Constants.MODE_OFF))
				tglbtnTax.setVisible(true);
			else
				tglbtnTax.setVisible(false);
			
			rdbtnValue = Constants.CASH;
		}
		else if(e.getSource()==rdbtnCard)
		{
			tglbtnTax.setVisible(false);
			rdbtnValue = Constants.CARD;
		}
		else if(e.getSource()==tglbtnTax)
		{
			if(tglbtnTax.isSelected())
			{
				tglbtnTaxValue = true;
			}
			else
			{
				tglbtnTaxValue = false;
			}
		}
		
	}
	
	public void paybkColor()
	{
		setBackground(new Color(SetColor.bkColor));
		panel.setBackground(new Color(SetColor.bkColor));
		
	}
	public void uplmtColor()
	{
		lblBookingDetails.setForeground(new Color(SetColor.mtColor));
	}
	public void uplcColor()
	{
		lblBookingId.setForeground(new Color(SetColor.cColor));
		lblRoomCost.setForeground(new Color(SetColor.cColor));
		lblCustomerName.setForeground(new Color(SetColor.cColor));
		lblFacilitiesCost.setForeground(new Color(SetColor.cColor));
		lblMobile.setForeground(new Color(SetColor.cColor));
		lblMiscellaneous.setForeground(new Color(SetColor.cColor));
		lblBookingDate.setForeground(new Color(SetColor.cColor));
		//lblNetTotal.setForeground(new Color(SetColor.cColor));
		lblCheckinDate.setForeground(new Color(SetColor.cColor));
		//lblGst.setForeground(new Color(SetColor.cColor));
		lblCheckoutDate.setForeground(new Color(SetColor.cColor));
		//lblGrossTotal.setForeground(new Color(SetColor.cColor));
		lblRoomNo.setForeground(new Color(SetColor.cColor));
		lblDiscount.setForeground(new Color(SetColor.cColor));
		lblRoomType.setForeground(new Color(SetColor.cColor));
		lblAdvance.setForeground(new Color(SetColor.cColor));
		lblNoOfDays.setForeground(new Color(SetColor.cColor));
		//lblBalance.setForeground(new Color(SetColor.cColor));
		lblPaymentmode.setForeground(new Color(SetColor.cColor));
		rdbtnCard.setForeground(new Color(SetColor.cColor));
		rdbtnCash.setForeground(new Color(SetColor.cColor));
		lblExtraPerson.setForeground(new Color(SetColor.cColor));
		lblExtraPersons.setForeground(new Color(SetColor.cColor));
		lblCoupon.setForeground(new Color(SetColor.cColor));
		lblRows.setForeground(new Color(SetColor.cColor));
	}
	public void uplSTFont(String stFType,int stfProp,int stSize)
	{
		lblBookingDetails.setFont(new Font(stFType,stfProp,stSize));
	}
	public void uplcFont(String ctFType,int ctfProp,int ctSize)
	{
		lblBookingDetails.setFont(new Font(ctFType,ctfProp,ctSize));
		lblBookingId.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRoomCost.setFont(new Font(ctFType,ctfProp,ctSize));
		lblCustomerName.setFont(new Font(ctFType,ctfProp,ctSize));
		lblFacilitiesCost.setFont(new Font(ctFType,ctfProp,ctSize));
		lblMobile.setFont(new Font(ctFType,ctfProp,ctSize));
		lblMiscellaneous.setFont(new Font(ctFType,ctfProp,ctSize));
		lblBookingDate.setFont(new Font(ctFType,ctfProp,ctSize));
		//lblNetTotal.setFont(new Font(ctFType,ctfProp,ctSize));
		lblCheckinDate.setFont(new Font(ctFType,ctfProp,ctSize));
		//lblGst.setFont(new Font(ctFType,ctfProp,ctSize));
		lblCheckoutDate.setFont(new Font(ctFType,ctfProp,ctSize));
		//lblGrossTotal.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRoomNo.setFont(new Font(ctFType,ctfProp,ctSize));
		lblDiscount.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRoomType.setFont(new Font(ctFType,ctfProp,ctSize));
		lblAdvance.setFont(new Font(ctFType,ctfProp,ctSize));
		lblNoOfDays.setFont(new Font(ctFType,ctfProp,ctSize));
		//lblBalance.setFont(new Font(ctFType,ctfProp,ctSize));
		lblPaymentmode.setFont(new Font(ctFType,ctfProp,ctSize));
		rdbtnCash.setFont(new Font(ctFType,ctfProp,ctSize));
		rdbtnCard.setFont(new Font(ctFType,ctfProp,ctSize));
		lblExtraPerson.setFont(new Font(ctFType,ctfProp,ctSize));
		lblExtraPersons.setFont(new Font(ctFType,ctfProp,ctSize));
		lblCoupon.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRows.setFont(new Font(ctFType,ctfProp,ctSize));
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public boolean checkCouponInDB(String coupon)
	{
		boolean b = false;
		if(coupon.length()>0)
		{
			for(String item : scm.db)
			{
				if(item.equals(coupon))
				{
					b =  true;
					rptDet.setCouponName(coupon);
					rptDet.setDiscount(text_discount.getText());
					break;
				}
			}
		}
		return b;		
	}

	

}
