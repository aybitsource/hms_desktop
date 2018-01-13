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

import com.hms.controller.HotelController;
import com.hms.controller.RoomController;
import com.hms.model.Room;
import com.hms.util.Constants;
import com.hms.util.DatabaseConstants;
import com.hms.util.ExcelExporter;
import com.hms.util.SearchBoxModel;
import com.hotelmanagement.MainPage;
import com.hotelmanagement.SFont;
import com.hotelmanagement.SetColor;

public class RoomEntry extends JPanel implements ActionListener,FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField text_roomID;
	private JTextField text_roomDoorNo;
    private JLabel lblRoomDoorNo;
	private JButton btnSave;
	private JLabel  lblRoomId;
	private JButton btnCancel_1;
	private JLabel lblRoomCategoryID;
	private JLabel lblRoomCount;
	public static JComboBox <String> text_roomCategoryID;
	private JTextField text_roomCount;
	private JLabel lblRoomStatus;
	private JComboBox <String> text_status;
	private JPanel panel;
	private ButtonGroup bg;
	private RoomController room_controller;
	java.sql.Date tdate;
	Object[][] dat;
	int rows=0;
	Statement st;
	ResultSet rs;
	

	private JLabel lblCustomerDetails_1;
	private JLabel lblEnterRoomDetails;
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
	List<String> categoryList;
	public RoomEntry(){		
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
		
		lblCustomerDetails_1 = new JLabel("Room Details");
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
		            	room_controller.retrieveAllRooms(DatabaseConstants.TABLE_ROOM_COLS);
		            }
		            else
		            {		            	
		            	room_controller.retrieveRoom(DatabaseConstants.TABLE_ROOM_COLS_DNO, ""+combo_search.getSelectedItem());
		            }
		            	
		        }
		    }
		});
		
		combo_search.setMaximumRowCount(10);
		combo_search.setEditable(true);
		sbm_consignCom = new SearchBoxModel(combo_search, DatabaseConstants.ROOM_DOOR_NUMBER);
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
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 14, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblEnterRoomDetails = new JLabel("Room Details");
		GridBagConstraints gbc_lblEnterRoomDetails = new GridBagConstraints();
		gbc_lblEnterRoomDetails.fill = GridBagConstraints.BOTH;
		gbc_lblEnterRoomDetails.gridwidth = 4;
		gbc_lblEnterRoomDetails.insets = new Insets(0, 0, 5, 0);
		gbc_lblEnterRoomDetails.gridx = 0;
		gbc_lblEnterRoomDetails.gridy = 1;
		panel.add(lblEnterRoomDetails, gbc_lblEnterRoomDetails);
		lblRoomId = new JLabel("Room ID  ");
		GridBagConstraints gbc_lblRoomId = new GridBagConstraints();
		gbc_lblRoomId.anchor = GridBagConstraints.WEST;
		gbc_lblRoomId.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomId.gridx = 0;
		gbc_lblRoomId.gridy = 2;
		//panel.add(lblRoomId, gbc_lblRoomId);
		
		text_roomID = new JTextField();
		GridBagConstraints gbc_text_roomID = new GridBagConstraints();
		gbc_text_roomID.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_roomID.gridwidth = 2;
		gbc_text_roomID.insets = new Insets(0, 0, 5, 0);
		gbc_text_roomID.gridx = 2;
		gbc_text_roomID.gridy = 2;
		//panel.add(text_roomID, gbc_text_roomID);
		text_roomID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		text_roomID.setColumns(10);
		
		lblRoomDoorNo = new JLabel("Room No.");
		GridBagConstraints gbc_lblRoomDoorNo = new GridBagConstraints();
		gbc_lblRoomDoorNo.anchor = GridBagConstraints.WEST;
		gbc_lblRoomDoorNo.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomDoorNo.gridx = 0;
		gbc_lblRoomDoorNo.gridy = 3;
		panel.add(lblRoomDoorNo, gbc_lblRoomDoorNo);
		
		text_roomDoorNo = new JTextField();
		GridBagConstraints gbc_text_roomDoorNo = new GridBagConstraints();
		gbc_text_roomDoorNo.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_roomDoorNo.gridwidth = 2;
		gbc_text_roomDoorNo.insets = new Insets(0, 0, 5, 0);
		gbc_text_roomDoorNo.gridx = 2;
		gbc_text_roomDoorNo.gridy = 3;
		panel.add(text_roomDoorNo, gbc_text_roomDoorNo);
		text_roomDoorNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		text_roomDoorNo.setColumns(10);
		//text_roomDoorNo.setInputVerifier(new IntegerValidator(null, text_roomDoorNo, "Enter only digits > 0"));
		
		
		lblRoomCount = new JLabel("Room Count");
		GridBagConstraints gbc_lblRoomCount = new GridBagConstraints();
		gbc_lblRoomCount.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRoomCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomCount.gridx = 0;
		gbc_lblRoomCount.gridy = 6;
		//panel.add(lblRoomCount, gbc_lblRoomCount);
		
		text_roomCount = new JTextField();
		GridBagConstraints gbc_text_roomCount = new GridBagConstraints();
		gbc_text_roomCount.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_roomCount.gridwidth = 2;
		gbc_text_roomCount.insets = new Insets(0, 0, 5, 0);
		gbc_text_roomCount.gridx = 2;
		gbc_text_roomCount.gridy = 6;
		//panel.add(text_roomCount, gbc_text_roomCount);
		text_roomCount.setColumns(10);
		
		lblRoomStatus = new JLabel("Room Status");
		GridBagConstraints gbc_lblRoomStatus = new GridBagConstraints();
		gbc_lblRoomStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRoomStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomStatus.gridx = 0;
		gbc_lblRoomStatus.gridy = 7;
		//panel.add(lblRoomStatus, gbc_lblRoomStatus);
		
		text_status = new JComboBox<String>();
		GridBagConstraints gbc_text_status = new GridBagConstraints();
		gbc_text_status.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_status.gridwidth = 2;
		gbc_text_status.insets = new Insets(0, 0, 5, 0);
		gbc_text_status.gridx = 2;
		gbc_text_status.gridy = 7;
	
		text_status.addItem("");
		text_status.addItem(Constants.FREE);
		text_status.addItem(Constants.BOOKED);
		
		//panel.add(text_status, gbc_text_status);
		text_roomCount.addFocusListener(this);
		text_roomDoorNo.addFocusListener(this);
		text_roomID.addFocusListener(this);
		
		transExcel = new JLabel();
		GridBagConstraints gbc_lblCustomerDetails_excel = new GridBagConstraints();
		gbc_lblCustomerDetails_excel.insets = new Insets(0, 0, 5, 0);
		gbc_lblCustomerDetails_excel.anchor = GridBagConstraints.EAST;
		gbc_lblCustomerDetails_excel.gridx = 6;
		gbc_lblCustomerDetails_excel.gridy = 0;
		add(transExcel, gbc_lblCustomerDetails_excel);	
		

		tableModel = new DefaultTableModel(Constants.roomsEntryNames, 0);
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
	    header.setToolTipStrings(Constants.roomsEntryTipStr);
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
		
		room_controller = new RoomController(tableModel, table);
		room_controller.retrieveAllRooms(DatabaseConstants.TABLE_ROOM_COLS);
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
                          ExcelExporter.fillData(table, filePath,"Room Details");
                          JOptionPane.showMessageDialog(null, "Data saved at "+filePath+" successfully", "Message",JOptionPane.INFORMATION_MESSAGE);
                          Desktop.getDesktop().open(new File(filePath));
            		}                } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});
		
		
		lblRoomCategoryID = new JLabel("Room Category");
		GridBagConstraints gbc_lblRoomCategoryID = new GridBagConstraints();
		gbc_lblRoomCategoryID.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRoomCategoryID.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomCategoryID.gridx = 0;
		gbc_lblRoomCategoryID.gridy = 4;
		panel.add(lblRoomCategoryID, gbc_lblRoomCategoryID);
		
		text_roomCategoryID = new JComboBox<String>();
		GridBagConstraints gbc_text_roomCategoryID = new GridBagConstraints();
		gbc_text_roomCategoryID.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_roomCategoryID.gridwidth = 2;
		gbc_text_roomCategoryID.insets = new Insets(0, 0, 5, 0);
		gbc_text_roomCategoryID.gridx = 2;
		gbc_text_roomCategoryID.gridy = 4;
		panel.add(text_roomCategoryID, gbc_text_roomCategoryID);
		categoryList = room_controller.roomCategoryIDList(DatabaseConstants.ROOMCATEGORY_ID);
		for(String item: categoryList)
		text_roomCategoryID.addItem(item);
		
		TableColumn  col1 = table.getColumnModel().getColumn(1);
		//TableColumn  col2 = table.getColumnModel().getColumn(2);
		
		String[] status = new String[] { Constants.FREE, Constants.BOOKED};
		categoryList.remove("");
		String[] categories = categoryList.toArray(new String[categoryList.size()]);
		col1.setCellEditor(new MyComboBoxEditor(categories));
	    //col2.setCellEditor(new MyComboBoxEditor((status)));
		
		btnSave = new JButton("Submit");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 2;
		gbc_btnSave.gridy = 9;
		panel.add(btnSave, gbc_btnSave);
		btnSave.setMnemonic(KeyEvent.VK_B);
		btnSave.addActionListener(this);
		
		btnCancel_1 = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel_1 = new GridBagConstraints();
		gbc_btnCancel_1.fill = GridBagConstraints.BOTH;
		gbc_btnCancel_1.gridx = 3;
		gbc_btnCancel_1.gridy = 9;
		panel.add(btnCancel_1, gbc_btnCancel_1);
		btnCancel_1.setMnemonic(KeyEvent.VK_C);
		uplcColor();
		uplmtColor();
		uplbkColor();
		uplcFont(SFont.ctFType,SFont.ctfProp,SFont.ctSize);
		uplSTFont(SFont.stFType,SFont.stfProp,SFont.stSize);
		btnCancel_1.addActionListener(this);
		//text_roomID.setText(room_service.generateRoomID());
		text_roomID.setEditable(false);
		
	}

	private void setClear1()
	{
		text_roomID.setText("");
		text_roomDoorNo.setText("");
		text_roomCount.setText("");


 
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
					HotelController hc = new HotelController();
					if(room_controller.roomCount()<hc.totalRooms(DatabaseConstants.HOTEL_ROOMS))
					{			
							
							if(text_roomDoorNo.getText().trim().length() == 0)
							{
								text_roomDoorNo.requestFocus(true);
								text_roomDoorNo.selectAll();
								JOptionPane.showMessageDialog(this, "Enter the room  number", "Error", JOptionPane.ERROR_MESSAGE);
							}
							else if(text_roomCategoryID.getSelectedItem().toString().length() == 0)
							{
								text_roomCategoryID.requestFocus(true);
								JOptionPane.showMessageDialog(this, "Select the room category", "Error", JOptionPane.ERROR_MESSAGE);
							}
//							else if(text_status.getSelectedItem().toString().length() == 0)
//							{
//								text_status.requestFocus(true);
//								JOptionPane.showMessageDialog(this, "Select the room status", "Error", JOptionPane.ERROR_MESSAGE);
//							}
							else
							{
										try{
										Room obj_room = new Room();
										obj_room.setRoom_ID(room_controller.generateRoomID());
										obj_room.setRoomDoorNumber(text_roomDoorNo.getText().trim().toUpperCase());
										obj_room.setRoomCategoryID((String)text_roomCategoryID.getSelectedItem());
										obj_room.setRoom_count(text_roomCount.getText().trim());
										obj_room.setRoom_status((String)text_status.getSelectedItem());
										RoomController obj_room_controller = new RoomController(obj_room);
										int s = obj_room_controller.submitRoom();
					
										if(s>0)
										{
										JOptionPane.showMessageDialog(this,"Room Created Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
										//text_roomID.requestFocus(true);
										text_roomDoorNo.requestFocus(true);
										room_controller.retrieveAllRooms(DatabaseConstants.TABLE_ROOM_COLS);
										text_roomCategoryID.setSelectedItem("");
										text_status.setSelectedItem("");
										
										sbm_consignCom.db.add(text_roomDoorNo.getText().trim().toUpperCase());
										updateUI();
										
										setClear1();
										}
										else
										{
										JOptionPane.showMessageDialog(this,"Duplicate value for room no.","Failure",JOptionPane.ERROR_MESSAGE);
										}
										
									
										}catch(NumberFormatException ee){
											//text_roomID.requestFocus(true);
											text_roomDoorNo.requestFocus(true);
											JOptionPane.showMessageDialog(this,"Enter the values correctly","Failure",JOptionPane.ERROR_MESSAGE);}
										catch (Exception ee) {
											// TODO Auto-generated catch block
											JOptionPane.showMessageDialog(this,""+ee,"Failure",JOptionPane.ERROR_MESSAGE);
										}
								}
							
					}
					else
					{
						JOptionPane.showMessageDialog(this,"You have created all the rooms","Failure",JOptionPane.ERROR_MESSAGE);
					}
				}
				else if(e.getSource()==btnSubmit)
				{
					room_controller.updateRoom(DatabaseConstants.UPDATE_ROOM, ""+combo_search.getSelectedItem());
				}
			}
	
 

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==text_roomID)
			text_roomID.selectAll();
		else if(arg0.getSource()==text_roomDoorNo)
			text_roomDoorNo.selectAll();
		else if(arg0.getSource()==text_roomCount)
			text_roomCount.selectAll();
		else {}
	}
	@Override
	public void focusLost(FocusEvent arg0) {

		if(arg0.getSource()==text_roomID)
			text_roomID.setText(text_roomID.getText().trim().toUpperCase());
		else if(arg0.getSource()==text_roomDoorNo)
			text_roomDoorNo.setText(text_roomDoorNo.getText().trim().toUpperCase());
		else if(arg0.getSource()==text_roomCount)
			text_roomCount.setText(text_roomCount.getText().trim().toUpperCase());
		
 
 

		else{}
 
	}

 
 
 
	public void uplcColor()
	{
		lblRoomId.setForeground(new Color(SetColor.cColor));
		lblRoomCategoryID.setForeground(new Color(SetColor.cColor));
		lblRoomCount.setForeground(new Color(SetColor.cColor));
		lblRoomDoorNo.setForeground(new Color(SetColor.cColor));
		lblRoomStatus.setForeground(new Color(SetColor.cColor));
		lblRows.setForeground(new Color(SetColor.cColor));
		
	}
	public void uplmtColor()
	{
		lblCustomerDetails_1.setForeground(new Color(SetColor.mtColor));
		lblEnterRoomDetails.setForeground(new Color(SetColor.mtColor));
	}
	public void uplbkColor()
	{
		setBackground(new Color(SetColor.bkColor));
		panel.setBackground(new Color(SetColor.bkColor));
	}
	public void uplcFont(String ctFType,int ctfProp,int ctSize)
	{
		lblRoomStatus.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRoomId.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRoomCategoryID.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRoomCount.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRoomDoorNo.setFont(new Font(ctFType,ctfProp,ctSize));
		btnSave.setFont(new Font(ctFType,ctfProp,ctSize));
		btnCancel_1.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRows.setFont(new Font(ctFType,ctfProp,ctSize));
	}
	public void uplSTFont(String stFType,int stfProp,int stSize)
	{
		lblCustomerDetails_1.setFont(new Font(stFType,stfProp,stSize));
		lblEnterRoomDetails.setFont(new Font(stFType,stfProp,stSize));
	}
	
	
	
	
}
