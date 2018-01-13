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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import com.hms.controller.BookingCancelController;
import com.hms.model.Booking;
import com.hms.services.BookingCancelService;
import com.hms.util.CancelSearchModel;
import com.hms.util.Constants;
import com.hms.util.DatabaseConstants;
import com.hms.util.ExcelExporter;
import com.hms.util.SearchBoxModel;
import com.hms.util.TableDialog;
import com.hotelmanagement.MainPage;
import com.hotelmanagement.SFont;
import com.hotelmanagement.SetColor;


public class BookingCancel extends JPanel implements ActionListener{

	
	public static JComboBox combo_booking_id;
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
	private JTextField text_miscellaneous;
	private JTextField text_net_total;
	private JTextField text_gst;
	private JTextField text_gross_total;
	private JTextField text_discount;
	private JTextField text_advance;
	private JTextField text_balance;
	
	private JLabel lblBookingDetails;
	private JLabel lblBookingId;
	private JLabel lblRoomCost;
	private JLabel lblCustomerName;
	private JLabel lblFacilitiesCost;
	private JLabel lblMobile;
	private JLabel lblMiscellaneous;
	private JLabel lblBookingDate;
	private JLabel lblNetTotal;
	private JLabel lblCheckinDate;
	private JLabel lblGst;
	private JLabel lblCheckoutDate;
	private JLabel lblGrossTotal;
	private JLabel lblRoomNo;
	private JLabel lblDiscount;
	private JLabel lblRoomType;
	private JLabel lblAdvance;
	private JLabel lblNoOfDays;
	private JLabel lblBalance;
	JButton okButton;
	JTable table;
	DefaultTableModel tableModel;
	String filePath;
	public static boolean tglbtnTaxValue = false;
	public static String rdbtnValue = null;
	private static CancelSearchModel sbm_consignCom;
	private static SearchBoxModel sbm_cancel_ids;
	public static String bookingID;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			BookigCheckout dialog = new BookigCheckout();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	public BookingCancelService bcs;
	BookingCancelController obj_bcc;
	public BookingCancelController obj_bcc1;
	public Booking obj_booking = new Booking();
	private JLabel lblPaymentmode;
	private JRadioButton rdbtnCash;
	private JRadioButton rdbtnCard;
	ButtonGroup bg;
	//public BookingCancel(String bookingID, CheckOutService chs) {
	TitledBorder border;
	private JScrollPane scrollPane;
	private JLabel transExcel;
	public static JButton btnSubmit;
	private JComboBox combo_search;
	public static JLabel lblRows;
	public BookingCancel(MainPage mpg) {
		
		bg = new ButtonGroup();
    	bcs = new BookingCancelService(obj_booking);
    	


		{
			
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{10, 0, 0, 100, 0, 0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			setLayout(gbl_panel);
			{
				lblRows = new JLabel("No. of Rows: ");
				GridBagConstraints gbc_lblRows = new GridBagConstraints();
				gbc_lblRows.insets = new Insets(0, 0, 5, 5);
				gbc_lblRows.anchor = GridBagConstraints.SOUTHWEST;
				gbc_lblRows.gridx = 3;
				gbc_lblRows.gridy = 0;
				add(lblRows, gbc_lblRows);
			}
			{
				combo_search = new JComboBox();
				GridBagConstraints gbc_combo_search = new GridBagConstraints();
				gbc_combo_search.insets = new Insets(0, 0, 5, 5);
				gbc_combo_search.fill = GridBagConstraints.HORIZONTAL;
				gbc_combo_search.gridx = 4;
				gbc_combo_search.gridy = 0;
				add(combo_search, gbc_combo_search);
				combo_search.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
				    @Override
				    public void keyReleased(KeyEvent event) {
				        if (event.getKeyChar() == KeyEvent.VK_ENTER) {
				            if (((JTextComponent) ((JComboBox) ((Component) event
				                    .getSource()).getParent()).getEditor()
				                    .getEditorComponent()).getText().isEmpty())
				            {
				            	obj_bcc.retrieveAll(DatabaseConstants.TABLE_BOOKING_CANCEL, Constants.CANCEL);
				            }
				            else
				            {					        
				            	obj_bcc.retrieve(DatabaseConstants.TABLE_BOOKING_ID_CANCEL, ""+combo_search.getSelectedItem());			             
				            }
				            	
				        }
				    }
				});
				combo_search.setMaximumRowCount(10);
				combo_search.setEditable(true);
				sbm_cancel_ids = new SearchBoxModel(combo_search, DatabaseConstants.BOOKING_ID_CANCEL);
				combo_search.setModel(sbm_cancel_ids);
				combo_search.addItemListener(sbm_cancel_ids);
				combo_search.addPopupMenuListener(sbm_cancel_ids);
			}
			{
				btnSubmit = new JButton("Submit");
				GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
				gbc_btnSubmit.insets = new Insets(0, 0, 5, 5);
				gbc_btnSubmit.gridx = 5;
				gbc_btnSubmit.gridy = 0;
				add(btnSubmit, gbc_btnSubmit);
			}
			{
				transExcel = new JLabel("");
				GridBagConstraints gbc_transExcel = new GridBagConstraints();
				gbc_transExcel.fill = GridBagConstraints.HORIZONTAL;
				gbc_transExcel.insets = new Insets(0, 0, 5, 0);
				gbc_transExcel.gridx = 6;
				gbc_transExcel.gridy = 0;
				add(transExcel, gbc_transExcel);
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
		                          ExcelExporter.fillData(table, filePath,"Canceled Bookings");
		                          JOptionPane.showMessageDialog(null, "Data saved at "+filePath+" successfully", "Message",JOptionPane.INFORMATION_MESSAGE);
		                          Desktop.getDesktop().open(new File(filePath));
		            		}		                } catch (Exception ex) {
		                    ex.printStackTrace();
		                }
					}
				});

			}
			

		
			

			{
				lblBookingDetails = new JLabel("Cancel Booking");
				GridBagConstraints gbc_lblBookingDetails = new GridBagConstraints();
				gbc_lblBookingDetails.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblBookingDetails.gridwidth = 2;
				gbc_lblBookingDetails.insets = new Insets(0, 0, 5, 5);
				gbc_lblBookingDetails.gridx = 1;
				gbc_lblBookingDetails.gridy = 2;
				add(lblBookingDetails, gbc_lblBookingDetails);
			}
			{
				lblBookingId = new JLabel("Booking ID");
				GridBagConstraints gbc_lblBookingId = new GridBagConstraints();
				gbc_lblBookingId.fill = GridBagConstraints.BOTH;
				gbc_lblBookingId.insets = new Insets(0, 0, 5, 5);
				gbc_lblBookingId.gridx = 1;
				gbc_lblBookingId.gridy = 3;
				add(lblBookingId, gbc_lblBookingId);
			}
			{
				combo_booking_id = new JComboBox();
				GridBagConstraints gbc_combo_booking_id = new GridBagConstraints();
				gbc_combo_booking_id.fill = GridBagConstraints.HORIZONTAL;
				gbc_combo_booking_id.insets = new Insets(0, 0, 5, 5);
				gbc_combo_booking_id.gridx = 2;
				gbc_combo_booking_id.gridy = 3;
				add(combo_booking_id, gbc_combo_booking_id);
				
				
//				combo_booking_id.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
//
//				    @Override
//				    public void keyReleased(KeyEvent event) {
//				        if (event.getKeyChar() == KeyEvent.VK_ENTER) {
//				            if (((JTextComponent) ((JComboBox) ((Component) event
//				                    .getSource()).getParent()).getEditor()
//				                    .getEditorComponent()).getText().isEmpty())
//				            {
//				            	JOptionPane.showMessageDialog(null, "No Bookings", "Success", JOptionPane.INFORMATION_MESSAGE);
//				            }
//				            else
//				            {
//				            	obj_booking = bcs.retrieveBookings(""+combo_booking_id.getSelectedItem());
//				            	obj_bcc1 = new BookingCancelController(obj_booking);
//				            	setData(obj_booking);
//				            	
//				            }
//				            	
//				        }
//				    }
//				});
				combo_booking_id.setEditable(false);
				sbm_consignCom = new CancelSearchModel(combo_booking_id, DatabaseConstants.BOOKING_ID_BOOKED, Constants.BOOKED, this);
				if(bookingID!=null)
					sbm_consignCom.cb.setSelectedItem(bookingID);
				
				combo_booking_id.setModel(sbm_consignCom);
				{

					tableModel = new DefaultTableModel(Constants.bookingCancelTransactions, 0);
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
				    header.setToolTipStrings(Constants.bookingCancelTipStr);
				    header.setToolTipText("Default ToolTip TEXT");
				    table.setTableHeader(header);
					table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					table.getTableHeader().setFont(new Font("Tahoma",Font.PLAIN,14));
					//table.getColumn("SL NO").setMaxWidth(50);
					table.setFillsViewportHeight(true);
					obj_bcc = new BookingCancelController(tableModel, table);
					obj_bcc.retrieveAll(DatabaseConstants.TABLE_BOOKING_CANCEL, Constants.CANCEL);

					TableDialog.tableRecordDetails(mpg, table, DatabaseConstants.BOOKING_ID_CANCEL);
					
					scrollPane = new JScrollPane(table);
					GridBagConstraints gbc_scrollPane = new GridBagConstraints();
					gbc_scrollPane.gridheight = 15;
					gbc_scrollPane.gridwidth = 4;
					gbc_scrollPane.fill = GridBagConstraints.BOTH;
					gbc_scrollPane.gridx = 3;
					gbc_scrollPane.gridy = 1;
					add(scrollPane, gbc_scrollPane);
				}

				{
					lblCustomerName = new JLabel("Customer Name");
					GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
					gbc_lblCustomerName.fill = GridBagConstraints.BOTH;
					gbc_lblCustomerName.insets = new Insets(0, 0, 5, 5);
					gbc_lblCustomerName.gridx = 1;
					gbc_lblCustomerName.gridy = 4;
					add(lblCustomerName, gbc_lblCustomerName);
				}
				{
					text_customer_name = new JTextField();
					GridBagConstraints gbc_text_customer_name = new GridBagConstraints();
					gbc_text_customer_name.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_customer_name.insets = new Insets(0, 0, 5, 5);
					gbc_text_customer_name.gridx = 2;
					gbc_text_customer_name.gridy = 4;
					add(text_customer_name, gbc_text_customer_name);
					text_customer_name.setEditable(false);
					text_customer_name.setColumns(10);
					text_customer_name.setText(obj_booking.getCustomerName());
				}

				{
					lblMobile = new JLabel("Mobile Number");
					GridBagConstraints gbc_lblMobile = new GridBagConstraints();
					gbc_lblMobile.fill = GridBagConstraints.BOTH;
					gbc_lblMobile.insets = new Insets(0, 0, 5, 5);
					gbc_lblMobile.gridx = 1;
					gbc_lblMobile.gridy = 5;
					add(lblMobile, gbc_lblMobile);
				}
				{
					text_mobile = new JTextField();
					GridBagConstraints gbc_text_mobile = new GridBagConstraints();
					gbc_text_mobile.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_mobile.insets = new Insets(0, 0, 5, 5);
					gbc_text_mobile.gridx = 2;
					gbc_text_mobile.gridy = 5;
					add(text_mobile, gbc_text_mobile);
					text_mobile.setEditable(false);
					text_mobile.setColumns(10);
					text_mobile.setText(obj_booking.getBooking_customer_mobile());
				}
				{
					lblMiscellaneous = new JLabel("Miscellaneous");
					GridBagConstraints gbc_lblMiscellaneous = new GridBagConstraints();
					gbc_lblMiscellaneous.fill = GridBagConstraints.BOTH;
					gbc_lblMiscellaneous.insets = new Insets(0, 0, 5, 5);
					gbc_lblMiscellaneous.gridx = 3;
					gbc_lblMiscellaneous.gridy = 4;
					//add(lblMiscellaneous, gbc_lblMiscellaneous);
				}
				{
//					text_miscellaneous = new JTextField();
//					GridBagConstraints gbc_text_miscellaneous = new GridBagConstraints();
//					gbc_text_miscellaneous.fill = GridBagConstraints.HORIZONTAL;
//					gbc_text_miscellaneous.insets = new Insets(0, 0, 5, 0);
//					gbc_text_miscellaneous.gridx = 4;
//					gbc_text_miscellaneous.gridy = 4;
//					//add(text_miscellaneous, gbc_text_miscellaneous);
//					text_miscellaneous.setEditable(false);
//					text_miscellaneous.setColumns(10);
				}
				{
					lblBookingDate = new JLabel("Booking Date");
					GridBagConstraints gbc_lblBookingDate = new GridBagConstraints();
					gbc_lblBookingDate.fill = GridBagConstraints.BOTH;
					gbc_lblBookingDate.insets = new Insets(0, 0, 5, 5);
					gbc_lblBookingDate.gridx = 1;
					gbc_lblBookingDate.gridy = 6;
					add(lblBookingDate, gbc_lblBookingDate);
				}
				{
					text_booking_date = new JTextField();
					GridBagConstraints gbc_text_booking_date = new GridBagConstraints();
					gbc_text_booking_date.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_booking_date.insets = new Insets(0, 0, 5, 5);
					gbc_text_booking_date.gridx = 2;
					gbc_text_booking_date.gridy = 6;
					add(text_booking_date, gbc_text_booking_date);
					text_booking_date.setEditable(false);
					text_booking_date.setColumns(10);
					text_booking_date.setText(""+obj_booking.getBookingDate());
				}
				{
					lblNetTotal = new JLabel("Net Total");
					GridBagConstraints gbc_lblNetTotal = new GridBagConstraints();
					gbc_lblNetTotal.fill = GridBagConstraints.BOTH;
					gbc_lblNetTotal.insets = new Insets(0, 0, 5, 5);
					gbc_lblNetTotal.gridx = 3;
					gbc_lblNetTotal.gridy = 5;
					//add(lblNetTotal, gbc_lblNetTotal);
				}
				{
					text_net_total = new JTextField();
					GridBagConstraints gbc_text_net_total = new GridBagConstraints();
					gbc_text_net_total.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_net_total.insets = new Insets(0, 0, 5, 0);
					gbc_text_net_total.gridx = 4;
					gbc_text_net_total.gridy = 5;
					//add(text_net_total, gbc_text_net_total);
					text_net_total.setEditable(false);
					text_net_total.setColumns(10);

				}
				{
					lblCheckinDate = new JLabel("Check-In Date");
					GridBagConstraints gbc_lblCheckinDate = new GridBagConstraints();
					gbc_lblCheckinDate.fill = GridBagConstraints.BOTH;
					gbc_lblCheckinDate.insets = new Insets(0, 0, 5, 5);
					gbc_lblCheckinDate.gridx = 1;
					gbc_lblCheckinDate.gridy = 7;
					add(lblCheckinDate, gbc_lblCheckinDate);
				}
				{
					text_checkin_date = new JTextField();
					GridBagConstraints gbc_text_checkin_date = new GridBagConstraints();
					gbc_text_checkin_date.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_checkin_date.insets = new Insets(0, 0, 5, 5);
					gbc_text_checkin_date.gridx = 2;
					gbc_text_checkin_date.gridy = 7;
					add(text_checkin_date, gbc_text_checkin_date);
					text_checkin_date.setEditable(false);
					text_checkin_date.setColumns(10);
					text_checkin_date.setText(""+obj_booking.getBookedDate());
				}
				{
					lblGst = new JLabel("GST ");
					GridBagConstraints gbc_lblGst = new GridBagConstraints();
					gbc_lblGst.fill = GridBagConstraints.BOTH;
					gbc_lblGst.insets = new Insets(0, 0, 5, 5);
					gbc_lblGst.gridx = 3;
					gbc_lblGst.gridy = 6;
					//add(lblGst, gbc_lblGst);
				}
				{
					text_gst = new JTextField();
					GridBagConstraints gbc_text_gst = new GridBagConstraints();
					gbc_text_gst.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_gst.insets = new Insets(0, 0, 5, 0);
					gbc_text_gst.gridx = 4;
					gbc_text_gst.gridy = 6;
					//add(text_gst, gbc_text_gst);
					text_gst.setEditable(false);
					text_gst.setColumns(10);

				}
				{
					lblCheckoutDate = new JLabel("Check-Out Date");
					GridBagConstraints gbc_lblCheckoutDate = new GridBagConstraints();
					gbc_lblCheckoutDate.fill = GridBagConstraints.BOTH;
					gbc_lblCheckoutDate.insets = new Insets(0, 0, 5, 5);
					gbc_lblCheckoutDate.gridx = 1;
					gbc_lblCheckoutDate.gridy = 8;
					add(lblCheckoutDate, gbc_lblCheckoutDate);
				}
				{
					text_checkout_date = new JTextField();
					GridBagConstraints gbc_text_checkout_date = new GridBagConstraints();
					gbc_text_checkout_date.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_checkout_date.insets = new Insets(0, 0, 5, 5);
					gbc_text_checkout_date.gridx = 2;
					gbc_text_checkout_date.gridy = 8;
					add(text_checkout_date, gbc_text_checkout_date);
					text_checkout_date.setEditable(false);
					text_checkout_date.setColumns(10);
					text_checkout_date.setText(""+obj_booking.getCheckoutDate());
				}
				{
					lblGrossTotal = new JLabel("Gross Total");
					GridBagConstraints gbc_lblGrossTotal = new GridBagConstraints();
					gbc_lblGrossTotal.fill = GridBagConstraints.BOTH;
					gbc_lblGrossTotal.insets = new Insets(0, 0, 5, 5);
					gbc_lblGrossTotal.gridx = 3;
					gbc_lblGrossTotal.gridy = 7;
					//add(lblGrossTotal, gbc_lblGrossTotal);
				}
				{
					text_gross_total = new JTextField();
					GridBagConstraints gbc_text_gross_total = new GridBagConstraints();
					gbc_text_gross_total.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_gross_total.insets = new Insets(0, 0, 5, 0);
					gbc_text_gross_total.gridx = 4;
					gbc_text_gross_total.gridy = 7;
					//add(text_gross_total, gbc_text_gross_total);
					text_gross_total.setEditable(false);
					text_gross_total.setColumns(10);
					text_gross_total.setText(obj_booking.getGross_amount());
				}
				{
					lblRoomNo = new JLabel("Room No.");
					GridBagConstraints gbc_lblRoomNo = new GridBagConstraints();
					gbc_lblRoomNo.fill = GridBagConstraints.BOTH;
					gbc_lblRoomNo.insets = new Insets(0, 0, 5, 5);
					gbc_lblRoomNo.gridx = 1;
					gbc_lblRoomNo.gridy = 9;
					add(lblRoomNo, gbc_lblRoomNo);
				}
				{
					text_room_no = new JTextField();
					GridBagConstraints gbc_text_room_no = new GridBagConstraints();
					gbc_text_room_no.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_room_no.insets = new Insets(0, 0, 5, 5);
					gbc_text_room_no.gridx = 2;
					gbc_text_room_no.gridy = 9;
					add(text_room_no, gbc_text_room_no);
					text_room_no.setEditable(false);
					text_room_no.setColumns(10);
					text_room_no.setText(obj_booking.getBooking_room_door_number());
				}
				{
					lblDiscount = new JLabel("Discount");
					GridBagConstraints gbc_lblDiscount = new GridBagConstraints();
					gbc_lblDiscount.fill = GridBagConstraints.BOTH;
					gbc_lblDiscount.insets = new Insets(0, 0, 5, 5);
					gbc_lblDiscount.gridx = 3;
					gbc_lblDiscount.gridy = 8;
					//add(lblDiscount, gbc_lblDiscount);
				}
				{
					text_discount = new JTextField();
					GridBagConstraints gbc_text_discount = new GridBagConstraints();
					gbc_text_discount.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_discount.insets = new Insets(0, 0, 5, 0);
					gbc_text_discount.gridx = 4;
					gbc_text_discount.gridy = 8;
					//add(text_discount, gbc_text_discount);
					text_discount.setEditable(false);
					text_discount.setColumns(10);
					text_discount.setText(obj_booking.getDiscount());
				}
				{
					lblRoomType = new JLabel("Room Type");
					GridBagConstraints gbc_lblRoomType = new GridBagConstraints();
					gbc_lblRoomType.fill = GridBagConstraints.BOTH;
					gbc_lblRoomType.insets = new Insets(0, 0, 5, 5);
					gbc_lblRoomType.gridx = 1;
					gbc_lblRoomType.gridy = 10;
					add(lblRoomType, gbc_lblRoomType);
				}
				{
					text_room_type = new JTextField();
					GridBagConstraints gbc_text_room_type = new GridBagConstraints();
					gbc_text_room_type.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_room_type.insets = new Insets(0, 0, 5, 5);
					gbc_text_room_type.gridx = 2;
					gbc_text_room_type.gridy = 10;
					add(text_room_type, gbc_text_room_type);
					text_room_type.setEditable(false);
					text_room_type.setColumns(10);
					text_room_type.setText(obj_booking.getBooking_room_category());
				}
				{
					lblAdvance = new JLabel("Advance Paid");
					GridBagConstraints gbc_lblAdvance = new GridBagConstraints();
					gbc_lblAdvance.fill = GridBagConstraints.BOTH;
					gbc_lblAdvance.insets = new Insets(0, 0, 5, 5);
					gbc_lblAdvance.gridx = 3;
					gbc_lblAdvance.gridy = 9;
					//add(lblAdvance, gbc_lblAdvance);
				}
				{
					text_advance = new JTextField();
					GridBagConstraints gbc_text_advance = new GridBagConstraints();
					gbc_text_advance.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_advance.insets = new Insets(0, 0, 5, 0);
					gbc_text_advance.gridx = 4;
					gbc_text_advance.gridy = 9;
					//add(text_advance, gbc_text_advance);
					text_advance.setEditable(false);
					text_advance.setColumns(10);
					
				}
				{
					lblNoOfDays = new JLabel("No. of Days");
					GridBagConstraints gbc_lblNoOfDays = new GridBagConstraints();
					gbc_lblNoOfDays.fill = GridBagConstraints.BOTH;
					gbc_lblNoOfDays.insets = new Insets(0, 0, 5, 5);
					gbc_lblNoOfDays.gridx = 1;
					gbc_lblNoOfDays.gridy = 11;
					add(lblNoOfDays, gbc_lblNoOfDays);
				}
				{
					text_days = new JTextField();
					GridBagConstraints gbc_text_days = new GridBagConstraints();
					gbc_text_days.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_days.insets = new Insets(0, 0, 5, 5);
					gbc_text_days.gridx = 2;
					gbc_text_days.gridy = 11;
					add(text_days, gbc_text_days);
					text_days.setEditable(false);
					text_days.setColumns(10);
					text_days.setText(obj_booking.getBooking_total_nights());
				}
				{
					lblBalance = new JLabel("Balance");
					GridBagConstraints gbc_lblBalance = new GridBagConstraints();
					gbc_lblBalance.fill = GridBagConstraints.BOTH;
					gbc_lblBalance.insets = new Insets(0, 0, 5, 5);
					gbc_lblBalance.gridx = 3;
					gbc_lblBalance.gridy = 10;
					//add(lblBalance, gbc_lblBalance);
				}
				{
					text_balance = new JTextField();
					GridBagConstraints gbc_text_balance = new GridBagConstraints();
					gbc_text_balance.fill = GridBagConstraints.HORIZONTAL;
					gbc_text_balance.insets = new Insets(0, 0, 5, 0);
					gbc_text_balance.gridx = 4;
					gbc_text_balance.gridy = 10;
					//add(text_balance, gbc_text_balance);
					text_balance.setEditable(false);
					text_balance.setColumns(10);

				}
				//		{
				//			lblPaymentmode = new JLabel("Payment Mode");
				//			GridBagConstraints gbc_lblPaymentmode = new GridBagConstraints();
				//			gbc_lblPaymentmode.fill = GridBagConstraints.HORIZONTAL;
				//			gbc_lblPaymentmode.insets = new Insets(0, 0, 5, 5);
				//			gbc_lblPaymentmode.gridx = 4;
				//			gbc_lblPaymentmode.gridy = 12;
				//			add(lblPaymentmode, gbc_lblPaymentmode);
				//		}
				//		
				//		{
				//			rdbtnCash = new JRadioButton("CASH");
				//			GridBagConstraints gbc_rdbtnCash = new GridBagConstraints();
				//			gbc_rdbtnCash.fill = GridBagConstraints.HORIZONTAL;
				//			gbc_rdbtnCash.insets = new Insets(0, 0, 5, 5);
				//			gbc_rdbtnCash.gridx = 5;
				//			gbc_rdbtnCash.gridy = 12;
				//			add(rdbtnCash, gbc_rdbtnCash);
				//		}
				//		{
				//			rdbtnCard = new JRadioButton("CARD");
				//			GridBagConstraints gbc_rdbtnCard = new GridBagConstraints();
				//			gbc_rdbtnCard.fill = GridBagConstraints.HORIZONTAL;
				//			gbc_rdbtnCard.insets = new Insets(0, 0, 5, 5);
				//			gbc_rdbtnCard.gridx = 6;
				//			gbc_rdbtnCard.gridy = 12;
				//			add(rdbtnCard, gbc_rdbtnCard);
				//		}
						{
							{
								lblRoomCost = new JLabel("Room Cost");
								GridBagConstraints gbc_lblRoomCost = new GridBagConstraints();
								gbc_lblRoomCost.fill = GridBagConstraints.BOTH;
								gbc_lblRoomCost.insets = new Insets(0, 0, 5, 5);
								gbc_lblRoomCost.gridx = 1;
								gbc_lblRoomCost.gridy = 12;
								add(lblRoomCost, gbc_lblRoomCost);
							}
									{
										text_room_cost = new JTextField();
										GridBagConstraints gbc_text_room_cost = new GridBagConstraints();
										gbc_text_room_cost.fill = GridBagConstraints.HORIZONTAL;
										gbc_text_room_cost.insets = new Insets(0, 0, 5, 5);
										gbc_text_room_cost.gridx = 2;
										gbc_text_room_cost.gridy = 12;
										add(text_room_cost, gbc_text_room_cost);
										text_room_cost.setEditable(false);
										text_room_cost.setColumns(10);
										text_room_cost.setText(obj_booking.getRoomCost());
									}
									{
										lblFacilitiesCost = new JLabel("Facilities Cost");
										GridBagConstraints gbc_lblFacilitiesCost = new GridBagConstraints();
										gbc_lblFacilitiesCost.fill = GridBagConstraints.BOTH;
										gbc_lblFacilitiesCost.insets = new Insets(0, 0, 5, 5);
										gbc_lblFacilitiesCost.gridx = 1;
										gbc_lblFacilitiesCost.gridy = 13;
										add(lblFacilitiesCost, gbc_lblFacilitiesCost);
									}
									{
										text_facilities_cost = new JTextField();
										GridBagConstraints gbc_text_facilities_cost = new GridBagConstraints();
										gbc_text_facilities_cost.fill = GridBagConstraints.HORIZONTAL;
										gbc_text_facilities_cost.insets = new Insets(0, 0, 5, 5);
										gbc_text_facilities_cost.gridx = 2;
										gbc_text_facilities_cost.gridy = 13;
										add(text_facilities_cost, gbc_text_facilities_cost);
										text_facilities_cost.setEditable(false);
										text_facilities_cost.setColumns(10);
										text_facilities_cost.setText(obj_booking.getFacilitiesCost());
									}
						}
						okButton = new JButton("Cancel Booking");
						GridBagConstraints gbc_okButton = new GridBagConstraints();
						gbc_okButton.fill = GridBagConstraints.HORIZONTAL;
						gbc_okButton.insets = new Insets(0, 0, 5, 5);
						gbc_okButton.gridx = 2;
						//gbc_okButton.gridy = 11;
						gbc_okButton.gridy = 14;
						add(okButton, gbc_okButton);
						okButton.addActionListener(this);
				combo_booking_id.addItemListener(sbm_consignCom);
				combo_booking_id.addPopupMenuListener(sbm_consignCom);
				//text_bookingID.setText(bookingID);
			}
		}
		//okButton.addActionListener(this);
		//cancelButton.addActionListener(this);
		//rdbtnCard.addActionListener(this);
		//rdbtnCash.addActionListener(this);

		paybkColor();
		uplmtColor();
		uplcColor();
		uplcFont(SFont.ctFType,SFont.ctfProp,SFont.ctSize);
		uplSTFont(SFont.stFType,SFont.stfProp,SFont.stSize);
		//setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	public void setClear1()
	{
		//BookingCancel.sbm_consignCom.db.remove(BookingCancel.text_bookingID.getSelectedItem());
		BookingCancel.sbm_consignCom.cb.setSelectedItem("");
		text_customer_name.setText("");
		text_mobile.setText("");
		text_booking_date.setText("");
		text_checkin_date.setText("");
		text_checkout_date.setText("");
		text_room_no.setText("");
		text_room_type.setText("");
		text_days.setText("");
		text_room_cost.setText("");
		text_facilities_cost.setText("");
		text_net_total.setText("");
		text_gst.setText("");
		text_gross_total.setText("");
		text_discount.setText("");
		text_advance.setText("");
		text_balance.setText("");
	}
	public void setClear()
	{

		BookingCancel.sbm_consignCom.cb.setSelectedItem("");
		text_customer_name.setText("");
		text_mobile.setText("");
		text_booking_date.setText("");
		text_checkin_date.setText("");
		text_checkout_date.setText("");
		text_room_no.setText("");
		text_room_type.setText("");
		text_days.setText("");
		text_room_cost.setText("");
		text_facilities_cost.setText("");
		text_net_total.setText("");
		text_gst.setText("");
		text_gross_total.setText("");
		text_discount.setText("");
		text_advance.setText("");
		text_balance.setText("");
	}
	public void setData(Booking obj_booking)
	{
		text_customer_name.setText(obj_booking.getCustomerName());
		text_mobile.setText(obj_booking.getBooking_customer_mobile());
		text_booking_date.setText(""+obj_booking.getBookingDate());
		text_checkin_date.setText(""+obj_booking.getBookedDate());
		text_checkout_date.setText(""+obj_booking.getCheckoutDate());
		text_room_no.setText(obj_booking.getBooking_room_door_number());
		text_room_type.setText(obj_booking.getBooking_room_category());
		text_days.setText(obj_booking.getBooking_total_nights());
		text_room_cost.setText(obj_booking.getRoomCost());
		text_facilities_cost.setText(obj_booking.getFacilitiesCost());

		text_gross_total.setText(obj_booking.getGross_amount());
		text_discount.setText(obj_booking.getDiscount());
		
	}
	public boolean checkBookingInDB(String bookingID)
	{
		boolean b = false;
		if(bookingID.length()>0)
		{
			for(String item : sbm_consignCom.db)
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
		// TODO Auto-generated method stub
		if(e.getSource()==okButton)
		{			
			if(checkBookingInDB(""+combo_booking_id.getSelectedItem()))
			{
			int answer = JOptionPane.showConfirmDialog(this, "Do you wish to cancel the Booking: "+obj_booking.getBooking_Id()
					+"\n with Room Number: "+obj_booking.getBooking_room_door_number(), null, JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION)
			{				
			int s = obj_bcc1.submitCancelService();
			if(s>0)
			{		
			setClear();
			sbm_cancel_ids.db.add(""+combo_booking_id.getSelectedItem());
			sbm_consignCom.db.remove(combo_booking_id.getSelectedItem());
			obj_bcc.retrieveAll(DatabaseConstants.TABLE_BOOKING_CANCEL, Constants.CANCEL);
			}
			}
			else if (answer == JOptionPane.NO_OPTION) {
				setClear1();
			}
			}
			else
			{
				combo_booking_id.requestFocus(true);
				JOptionPane.showMessageDialog(this,"Select the Booking ID from the list","Failure",JOptionPane.ERROR_MESSAGE);
			}

				
			
		}
		
		
	}
	
	public void paybkColor()
	{
		table.setBackground(new Color(SetColor.bkColor));
		setBackground(new Color(SetColor.bkColor));
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
		lblNetTotal.setForeground(new Color(SetColor.cColor));
		lblCheckinDate.setForeground(new Color(SetColor.cColor));
		lblGst.setForeground(new Color(SetColor.cColor));
		lblCheckoutDate.setForeground(new Color(SetColor.cColor));
		lblGrossTotal.setForeground(new Color(SetColor.cColor));
		lblRoomNo.setForeground(new Color(SetColor.cColor));
		lblDiscount.setForeground(new Color(SetColor.cColor));
		lblRoomType.setForeground(new Color(SetColor.cColor));
		lblAdvance.setForeground(new Color(SetColor.cColor));
		lblNoOfDays.setForeground(new Color(SetColor.cColor));
		lblBalance.setForeground(new Color(SetColor.cColor));
		lblRows.setForeground(new Color(SetColor.cColor));
		table.setForeground(new Color(SetColor.cColor));
		//lblPaymentmode.setForeground(new Color(SetColor.cColor));
		//rdbtnCard.setForeground(new Color(SetColor.cColor));
		//rdbtnCash.setForeground(new Color(SetColor.cColor));
	}
	public void uplSTFont(String stFType,int stfProp,int stSize)
	{
		lblBookingDetails.setFont(new Font(stFType,stfProp,stSize));
	}
	public void uplcFont(String ctFType,int ctfProp,int ctSize)
	{
		table.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRows.setFont(new Font(ctFType,ctfProp,ctSize));
		lblBookingDetails.setFont(new Font(ctFType,ctfProp,ctSize));
		lblBookingId.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRoomCost.setFont(new Font(ctFType,ctfProp,ctSize));
		lblCustomerName.setFont(new Font(ctFType,ctfProp,ctSize));
		lblFacilitiesCost.setFont(new Font(ctFType,ctfProp,ctSize));
		lblMobile.setFont(new Font(ctFType,ctfProp,ctSize));
		lblMiscellaneous.setFont(new Font(ctFType,ctfProp,ctSize));
		lblBookingDate.setFont(new Font(ctFType,ctfProp,ctSize));
		lblNetTotal.setFont(new Font(ctFType,ctfProp,ctSize));
		lblCheckinDate.setFont(new Font(ctFType,ctfProp,ctSize));
		lblGst.setFont(new Font(ctFType,ctfProp,ctSize));
		lblCheckoutDate.setFont(new Font(ctFType,ctfProp,ctSize));
		lblGrossTotal.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRoomNo.setFont(new Font(ctFType,ctfProp,ctSize));
		lblDiscount.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRoomType.setFont(new Font(ctFType,ctfProp,ctSize));
		lblAdvance.setFont(new Font(ctFType,ctfProp,ctSize));
		lblNoOfDays.setFont(new Font(ctFType,ctfProp,ctSize));
		lblBalance.setFont(new Font(ctFType,ctfProp,ctSize));
		//lblPaymentmode.setFont(new Font(ctFType,ctfProp,ctSize));
		//rdbtnCash.setFont(new Font(ctFType,ctfProp,ctSize));
		//rdbtnCard.setFont(new Font(ctFType,ctfProp,ctSize));
	}

	

}
