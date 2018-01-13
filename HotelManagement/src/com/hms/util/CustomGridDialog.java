package com.hms.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.hms.view.BookingCancel;
import com.hms.view.BookingCheckout;
import com.hms.view.CheckInEntry;
import com.hotelmanagement.MainPage;

public class CustomGridDialog extends JDialog implements MouseListener {

	/**
	 * Create the dialog.
	 */
	GridBagConstraints gc;
	int flag;
	int row=1, col=1;
	JLabel list[];
	String bookingList[];
	String roomNos[];
	int i = 0;
	String text_enter;
	String text_exit;
	Dimension d;
	Dimension dd = Toolkit.getDefaultToolkit().getScreenSize();
	String trans_type;
	public CustomGridDialog(JFrame obj, JLabel listP[], String listB[], String listR[], String type, String title) {
		super(obj, title, true);
		this.list = listP;
		this.bookingList = listB;
		this.roomNos = listR; 
		trans_type = type;
		setLocation(100,100);
		

		setLayout(new GridBagLayout());
		setBackground(Color.black);
		gc = new GridBagConstraints();
		if(list.length==0)
		{
			JLabel l;
			if(trans_type.equals(Constants.CHECKIN))					
				l = new JLabel("    No checkins on current date");
			else if(trans_type.equals(Constants.CHECKOUT))
				l = new JLabel("    No checkouts on current date");
			else if(trans_type.equals(Constants.CANCEL))
				l = new JLabel("    No cancellations on current date");
			else
				l = new JLabel("    New entry");
			
			l.setForeground(Color.white);
			l.setHorizontalTextPosition(JLabel.CENTER);
			l.setOpaque(true);
			l.setBackground(Color.black);
			
			addComponent(l, 1,1,1,3);
		}
		else
		{	
		for(i=0;i<list.length;i++)
		{
			
			if(flag==5)
			{
				flag = 0;
				row+=3;
				col=1;
			}

			addComponent(list[i], row,col,1,3);
			list[i].setOpaque(true);
			list[i].setBackground(Color.black);
			list[i].setForeground(Color.white);
			
			

		    col++;
			flag++;
			list[i].addMouseListener(this);
			
		}
		}
		pack();
		setResizable(false);
		
		setVisible(true);

	}
	public void addComponent(JComponent jc,int r, int c, int w, int h)
	{
		gc.gridx=c;
		gc.gridy=r;
		gc.gridwidth=w;
		gc.ipady = 30;
		gc.ipadx = 30;
		gc.gridheight=h;
		gc.fill=GridBagConstraints.BOTH;
		gc.insets = new Insets(5,5,5,5);
		add(jc,gc);
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		for(int i=0;i<list.length;i++)
		{
			if(arg0.getSource()==list[i])
			{

					d = list[i].getSize();
					d.width = d.width - 30;
					d.height = d.height - 30;
//					System.out.println("THE SIZE IS"+d.getWidth());
//					System.out.println("THE SIZE IS"+d.getHeight());
					list[i].setMinimumSize(d);
					list[i].setPreferredSize(d);
					list[i].setMaximumSize(d);
					list[i].setCursor(new Cursor(Cursor.HAND_CURSOR));					
					list[i].setOpaque(true);					
					list[i].setBackground(Color.orange);
					d= list[i].getSize();
					text_enter = list[i].getText();
					if(trans_type.equals(Constants.CHECKIN))					
						list[i].setText("CHECK IN");
					else if(trans_type.equals(Constants.CHECKOUT))
						list[i].setText("CHECK OUT");
					else if(trans_type.equals(Constants.CANCEL))
						list[i].setText("CANCEL");
					else 
						list[i].setText("NEW ENTRY");
					list[i].setHorizontalAlignment(JLabel.CENTER);
					
			}
		}
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		for(int i=0;i<list.length;i++)
		{
			if(arg0.getSource()==list[i])
			{
				list[i].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						list[i].setBackground(Color.black);
						list[i].setOpaque(true);	
						list[i].setText(text_enter);
			}
		}		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		for(int i=0;i<list.length;i++)
		{
			if(trans_type.equals(Constants.CHECKIN))
			{
				if(arg0.getSource()==list[i])
				{
					String bookingID = bookingList[i];
					String roomNo = roomNos[i];
					CheckInEntry.bookingID = bookingID;
					CheckInEntry.text_roomNo.setText(roomNo);
					MainPage.tabbedPane.setSelectedIndex(3);
					CheckInEntry.text_BookingID.setPopupVisible(true);
					CheckInEntry.text_BookingID.setPopupVisible(false);
					CheckInEntry.text_BookingID.setSelectedItem(bookingID);
					dispose();
	
				}
			}
			else if(trans_type.equals(Constants.CHECKOUT))
			{
				if(arg0.getSource()==list[i])
				{
					String bookingID = bookingList[i];
					String roomNo = roomNos[i];
					//CheckOutEntry.text_roomNo.setText(roomNo);
					BookingCheckout.bookingID = bookingID;
					MainPage.tabbedPane.setSelectedIndex(4);
					BookingCheckout.text_bookingID.setPopupVisible(true);
					BookingCheckout.text_bookingID.setPopupVisible(false);
					BookingCheckout.text_bookingID.setSelectedItem(bookingID);
					dispose();

				}
			}
			else if(trans_type.equals(Constants.CANCEL))
			{
				if(arg0.getSource()==list[i])
				{
					String bookingID = bookingList[i];
					String roomNo = roomNos[i];
					//CheckOutEntry.text_roomNo.setText(roomNo);
					BookingCancel.bookingID = bookingID;
					MainPage.tabbedPane.setSelectedIndex(5);
					BookingCancel.combo_booking_id.setPopupVisible(true);
					BookingCancel.combo_booking_id.setPopupVisible(false);
					BookingCancel.combo_booking_id.setSelectedItem(bookingID);
					dispose();

				}
			}
		}
	}

}
