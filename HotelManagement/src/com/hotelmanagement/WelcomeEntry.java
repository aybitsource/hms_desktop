package com.hotelmanagement;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hms.controller.WelcomeController;
import com.hms.model.Welcome;
import com.hms.util.CustomDialog;
import com.hms.util.CustomGridDialog;
import com.hms.util.DatabaseConstants;
import com.hms.view.ImagePanel;

public class WelcomeEntry extends JPanel {
	/**
	 * Create the panel.
	 */
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel lblCheckin;
	private JLabel lblCheckout;
	private JLabel lblCivalue;
	private JLabel lblCovalue;
	MainPage mpg;
	WelcomeController obj_con;
	JPanel panel_1;
	String checkins;
	JLabel listCheckinLabels[];
	String listCheckinID[];
	String listCheckinRoomNo[];
	int currCheckins = 0;
	String checkout;
	String listCheckoutRoomNo[];
	JLabel listCheckoutLabels[];
	String listCheckoutID[];
	int currCheckouts = 0;
	String cancel;
	JLabel listCancelLabels[];
	String listCancelRoomNo[];
	String listCancelID[];
	int currCancels = 0;
	
	private JLabel lblCustomer;
	private JLabel lblCustomersNotArrived;
	public WelcomeEntry(final MainPage mpg) {
		this.mpg = mpg;
		obj_con = new WelcomeController();
		List<Welcome> checkinList = obj_con.retriveCheckinCheckout(DatabaseConstants.CURRENT_BOOKED_DATE, DatabaseConstants.CUSTOMER_NAMES);
		List<Welcome> checkoutList = obj_con.retriveCheckinCheckout(DatabaseConstants.CURRENT_CHECKOUT_DATE, DatabaseConstants.CUSTOMER_NAMES); 
		List<Welcome> cancelList = obj_con.retriveCheckinCheckout(DatabaseConstants.CURRENT_CANCEL, DatabaseConstants.CUSTOMER_NAMES);
		checkins = "<html>Rooms to be checked in: <br><br>";
		checkout = "<html>Rooms to be checked out: <br><br>";
		cancel = "<html>Rooms to be canceled: <br><br>";
		
		listCheckinLabels = new JLabel[checkinList.size()];
		listCheckinID = new String[checkinList.size()];
		listCheckinRoomNo = new String[checkinList.size()];
		
		
		listCheckoutRoomNo = new String[checkoutList.size()];
		listCheckoutLabels = new JLabel[checkoutList.size()];
		listCheckoutID = new String[checkoutList.size()];
		
		listCancelLabels = new JLabel[cancelList.size()];
		listCancelID = new String[cancelList.size()];
		listCancelRoomNo = new String[cancelList.size()];
		
		int i = 0;
		for(Welcome wel : checkinList)
		{
			String checkins = "<html>";
			checkins += "Booking ID: "+ wel.getBookingID()+"<br>";
			checkins += "Customer Name: "+ wel.getCustomerName()+"<br>"; 
			checkins += "Room No: "+ wel.getRoomNo()+"<br>";
			checkins += "Room Type: "+wel.getRoomType()+"<br>";
			checkins += "Check-In Date: "+wel.getBookedDate()+"<br>";
			checkins += "Mobile No: "+wel.getMobileNo()+"<br></html>";
			Dimension d = new Dimension(150,100);
			listCheckinLabels[i] = new JLabel(checkins);
			listCheckinLabels[i].setMinimumSize(d);
			listCheckinLabels[i].setPreferredSize(d);
			listCheckinLabels[i].setMaximumSize(d);
			listCheckinID[i] = wel.getBookingID();
			listCheckinRoomNo[i] = wel.getRoomNo();
			EmptyBorder border = new EmptyBorder(5,10,5,10);
			listCheckinLabels[i].setBorder(border);
			currCheckins++;
			i++;
		}
		int j = 0;
		for(Welcome wel : checkoutList)
		{
			String checkout = "<html>";
			checkout += "Booking ID: "+ wel.getBookingID()+"<br>";
			checkout += "Room No: "+ wel.getRoomNo()+"<br>";
			checkout += "Room Type: "+wel.getRoomType()+"<br>";
			checkout += "Check-In Date: "+wel.getBookedDate()+"<br>";
			checkout += "Mobile No: "+wel.getMobileNo()+"<br></html>";
			listCheckoutLabels[j] = new JLabel(checkout);
			listCheckoutID[j] = wel.getBookingID();
			//listCheckoutRoomNo[i] = wel.getRoomNo();
			EmptyBorder border = new EmptyBorder(5,10,5,10);
			listCheckoutLabels[j].setBorder(border);
			currCheckouts++;
			j++;
		}
		int k = 0;
		for(Welcome wel : cancelList)
		{
			String cancel = "<html>";
			cancel += "Booking ID: "+ wel.getBookingID()+"<br>";
			cancel += "Customer Name: "+ wel.getCustomerName()+"<br>"; 
			cancel += "Room No: "+ wel.getRoomNo()+"<br>";
			cancel += "Room Type: "+wel.getRoomType()+"<br>";
			cancel += "Check-In Date: "+wel.getBookedDate()+"<br>";
			cancel += "Mobile No: "+wel.getMobileNo()+"<br></html>";
			Dimension d = new Dimension(150,100);
			listCancelLabels[k] = new JLabel(cancel);
			listCancelLabels[k].setMinimumSize(d);
			listCancelLabels[k].setPreferredSize(d);
			listCancelLabels[k].setMaximumSize(d);
			listCancelID[k] = wel.getBookingID();
			listCancelRoomNo[k] = wel.getRoomNo();
			EmptyBorder border = new EmptyBorder(5,10,5,10);
			listCancelLabels[k].setBorder(border);
			currCancels++;
			k++;
		}
		
		
		checkins += "</html>";
		checkout += "</html>";
		cancel += "</html>";
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0};
		setLayout(gridBagLayout);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridheight = 3;
		gbc_panel_1.insets = new Insets(0, 0, 0, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 10, 0, 0, 10, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		panel_1.setLayout(gbl_panel_1);
		
		lblCivalue = new JLabel(" "+currCheckins+" ");
		GridBagConstraints gbc_lblCivalue = new GridBagConstraints();
		gbc_lblCivalue.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblCivalue.insets = new Insets(0, 0, 5, 0);
		gbc_lblCivalue.gridx = 1;
		gbc_lblCivalue.gridy = 0;
		panel_1.add(lblCivalue, gbc_lblCivalue);
		lblCivalue.setBackground(Color.red);
		lblCivalue.setOpaque(true);
		lblCivalue.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e)
			{
				lblCivalue.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mousePressed(MouseEvent e)
			{
//				NotificationPopup np = new NotificationPopup(mpg);
//				np.setLocationRelativeTo(lblCivalue);
//				Point p = np.getLocation();
//				np.setLocation(p.x - 100, p.y);
//				
//				List<Welcome> welList = obj_con.retriveCheckins(DatabaseConstants.CURRENT_BOOKED_DATE); 
//				String checkins = "<html>Rooms to be checked in: <br>";
//				for(Welcome wel : welList)
//				{
//					checkins += "Room No: "+ wel.getRoomNo()+"<br>";
//					checkins += "Room Type: "+wel.getRoomType()+"<br>";
//					checkins += "Check-In Date: "+wel.getBookedDate()+"<br>";
//					checkins += "Mobile No: "+wel.getMobileNo()+"<br><br>";					
//				}
//				checkins += "</html>";
//				System.out.println(checkins);
//				//np.lbl.setText(checkins);
//				//np.setVisible(true);
				//JOptionPane.showMessageDialog(mpg, checkins, "Alert", JOptionPane.PLAIN_MESSAGE);
//				  final JOptionPane pane = new JOptionPane(checkins);
//				    final JDialog d = pane.createDialog(mpg, "Today's Checkin list");
//				    d.setLocationRelativeTo(lblCivalue);
//				    Point p = d.getLocation();
//				    d.setLocation(p.x + 75, p.y);
//				    d.setVisible(true);
				//new CustomDialog(mpg, checkins, "Alert !! (Today's checkin list)", lblCivalue, 75, 0, CustomDialog.INFO_ICON);
				new CustomGridDialog(mpg, listCheckinLabels, listCheckinID, listCheckinRoomNo, com.hms.util.Constants.CHECKIN, "Today's Check-in list");
				//CustomDialog dlg = new CustomDialog(mpg, checkins, "Today's Checkin list", lblCivalue, 75, 0);
				
				
			}
			
		});
		
		lblCovalue = new JLabel(" "+currCheckouts+" ");
		GridBagConstraints gbc_lblCovalue = new GridBagConstraints();
		gbc_lblCovalue.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblCovalue.insets = new Insets(0, 0, 5, 0);
		gbc_lblCovalue.gridx = 1;
		gbc_lblCovalue.gridy = 3;
		panel_1.add(lblCovalue, gbc_lblCovalue);
		lblCovalue.setBackground(Color.red);
		lblCovalue.setOpaque(true);
		lblCovalue.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e)
			{
				lblCovalue.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mousePressed(MouseEvent e)
			{

				//new CustomDialog(mpg, checkout, "Alert !! (Today's checkout list)", lblCivalue, 75, 0, CustomDialog.INFO_ICON);
				new CustomGridDialog(mpg, listCheckoutLabels, listCheckoutID, listCheckoutRoomNo, com.hms.util.Constants.CHECKOUT, "Today's Check-out list");
				
			}
			
		});
		
		lblCheckin = new JLabel("");
		GridBagConstraints gbc_lblCheckin = new GridBagConstraints();
		gbc_lblCheckin.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblCheckin.insets = new Insets(0, 0, 5, 5);
		gbc_lblCheckin.gridx = 0;
		gbc_lblCheckin.gridy = 1;
		panel_1.add(lblCheckin, gbc_lblCheckin);
		lblCheckin.setIcon(new ImageIcon("C:\\HotelManagement\\boot\\images\\checkin_micro.png"));
		lblCheckin.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e)
			{
				lblCheckin.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mousePressed(MouseEvent e)
			{
	
				new CustomDialog(mpg, checkins, "Alert !! (Today's checkin list)", lblCivalue, 75, 0, CustomDialog.INFO_ICON);
				
			}
		});
		


		lblCheckout = new JLabel("");
		GridBagConstraints gbc_lblCheckout = new GridBagConstraints();
		gbc_lblCheckout.gridwidth = 2;
		gbc_lblCheckout.anchor = GridBagConstraints.NORTH;
		gbc_lblCheckout.insets = new Insets(0, 0, 5, 0);
		gbc_lblCheckout.gridx = 0;
		gbc_lblCheckout.gridy = 4;
		panel_1.add(lblCheckout, gbc_lblCheckout);
		lblCheckout.setIcon(new ImageIcon("C:\\HotelManagement\\boot\\images\\checkout_micro.png"));
		
		lblCheckout.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e)
			{
				lblCheckout.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mousePressed(MouseEvent e)
			{

				new CustomDialog(mpg, checkout, "Alert !! (Today's checkout list)", lblCivalue, 75, 0, CustomDialog.INFO_ICON);
				
			}
		});
		
		lblCustomersNotArrived = new JLabel(" "+currCancels+" ");
		lblCustomersNotArrived.setOpaque(true);
		lblCustomersNotArrived.setForeground(Color.BLACK);
		lblCustomersNotArrived.setBackground(Color.RED);
		GridBagConstraints gbc_lblCustomersNotArrived = new GridBagConstraints();
		gbc_lblCustomersNotArrived.insets = new Insets(0, 0, 5, 0);
		gbc_lblCustomersNotArrived.gridx = 1;
		gbc_lblCustomersNotArrived.gridy = 6;
		panel_1.add(lblCustomersNotArrived, gbc_lblCustomersNotArrived);
		lblCustomersNotArrived.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e)
			{
				lblCustomersNotArrived.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mousePressed(MouseEvent e)
			{
				new CustomGridDialog(mpg, listCancelLabels, listCancelID, listCancelRoomNo, com.hms.util.Constants.CANCEL, "Customers not arrived list");
				
			}
		});		
		
		lblCustomer = new JLabel("");
		GridBagConstraints gbc_lblCustomer = new GridBagConstraints();
		gbc_lblCustomer.gridwidth = 2;
		gbc_lblCustomer.insets = new Insets(0, 0, 5, 0);
		gbc_lblCustomer.gridx = 0;
		gbc_lblCustomer.gridy = 7;
		panel_1.add(lblCustomer, gbc_lblCustomer);
		lblCustomer.setIcon(new ImageIcon("C:\\HotelManagement\\boot\\images\\customer.png"));
		
		ImagePanel panel = new ImagePanel(new ImageIcon("C:\\HotelManagement\\boot\\images\\welcome.jpg").getImage());
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		welbkColor();
		cColor();
		cFont(SFont.mtFType, SFont.mtfProp, 12);

		
	}
	public void cFont(String ctFType,int ctfProp,int ctSize)
	{
		lblCheckin.setFont(new Font(ctFType,ctfProp,ctSize));
		lblCivalue.setFont(new Font(ctFType,ctfProp,ctSize));
	}
	public  void cColor()
	{
		lblCheckin.setForeground(new Color(SetColor.cColor));
		lblCivalue.setForeground(new Color(SetColor.cColor));
		lblCheckout.setForeground(new Color(SetColor.cColor));
		lblCovalue.setForeground(new Color(SetColor.cColor));
		lblCustomersNotArrived.setForeground(new Color(SetColor.cColor));
	}
	public void welMTColor()
	{

	}
	public void welMTFont(String mtFType,int mtfProp,int mtSize)
	{
		
	}

	public void welbkColor()
	{
		setBackground(new Color(SetColor.bkColor));
		panel_1.setBackground(Color.gray);
	}

}

