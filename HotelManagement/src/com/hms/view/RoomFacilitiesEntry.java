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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import com.hms.controller.RoomFacilitiesController;
import com.hms.model.RoomFacilities;
import com.hms.util.BigDecimalType;
import com.hms.util.Constants;
import com.hms.util.DatabaseConstants;
import com.hms.util.ExcelExporter;
import com.hms.util.SearchBoxModel;
import com.hms.validators.DoubleValidator;
import com.hotelmanagement.MainPage;
import com.hotelmanagement.SFont;
import com.hotelmanagement.SetColor;

public class RoomFacilitiesEntry extends JPanel implements ActionListener,FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField text_roomFacilitiesID;
	private JTextField text_roomFacilitiesName;
    private JLabel lblRoomFacilitiesName;
	private JButton btnSave;
	private JLabel  lblRoomFacilitiesId;
	private JButton btnCancel_1;
	private JLabel lblRoomFacilitiesDesc;
	private JTextArea text_roomFacilitiesDesc;
	private JPanel panel;
	private ButtonGroup bg;
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
	private JLabel lblPrice;
	private JTextField text_price;
	public static JButton btnSubmit;
	public static JComboBox combo_search;
	public static  JLabel lblRows;
	public static SearchBoxModel sbm_consignCom;
	RoomFacilitiesController room_facilities_controller;
	public RoomFacilitiesEntry(){
		
		
		
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
		
		lblCustomerDetails_1 = new JLabel("Room Facilities Details");
		GridBagConstraints gbc_lblCustomerDetails_1 = new GridBagConstraints();
		gbc_lblCustomerDetails_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblCustomerDetails_1.gridx = 3;
		gbc_lblCustomerDetails_1.gridy = 0;
		add(lblCustomerDetails_1, gbc_lblCustomerDetails_1);
		
		combo_search = new JComboBox();
		GridBagConstraints gbc_combo_search = new GridBagConstraints();
		gbc_combo_search.anchor = GridBagConstraints.SOUTH;
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
		            	room_facilities_controller.retrieveAll(DatabaseConstants.TABLE_ROOM_FACILITES_COLS);
		            }
		            else
		            {		            	
		            	room_facilities_controller.retrieve(DatabaseConstants.TABLE_ROOM_FACILITES_NAME, ""+combo_search.getSelectedItem());
		            }
		            	
		        }
		    }
		});
		
		combo_search.setMaximumRowCount(10);
		combo_search.setEditable(true);
		sbm_consignCom = new SearchBoxModel(combo_search, DatabaseConstants.ROOM_FACILITIES_NAME );
		combo_search.setModel(sbm_consignCom);
		combo_search.addItemListener(sbm_consignCom);
		combo_search.addPopupMenuListener(sbm_consignCom);

		add(combo_search, gbc_combo_search);
		
		btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
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
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblEnterRoomDetails = new JLabel("Room Facilities Details");
		GridBagConstraints gbc_lblEnterRoomDetails = new GridBagConstraints();
		gbc_lblEnterRoomDetails.fill = GridBagConstraints.BOTH;
		gbc_lblEnterRoomDetails.gridwidth = 4;
		gbc_lblEnterRoomDetails.insets = new Insets(0, 0, 5, 0);
		gbc_lblEnterRoomDetails.gridx = 0;
		gbc_lblEnterRoomDetails.gridy = 1;
		panel.add(lblEnterRoomDetails, gbc_lblEnterRoomDetails);
		lblRoomFacilitiesId = new JLabel("Room Facility ID  ");
		GridBagConstraints gbc_lblRoomFacilitiesId = new GridBagConstraints();
		gbc_lblRoomFacilitiesId.anchor = GridBagConstraints.WEST;
		gbc_lblRoomFacilitiesId.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomFacilitiesId.gridx = 0;
		gbc_lblRoomFacilitiesId.gridy = 2;
		//panel.add(lblRoomFacilitiesId, gbc_lblRoomFacilitiesId);
		
		text_roomFacilitiesID = new JTextField();
		GridBagConstraints gbc_text_roomFacilitiesID = new GridBagConstraints();
		gbc_text_roomFacilitiesID.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_roomFacilitiesID.gridwidth = 2;
		gbc_text_roomFacilitiesID.insets = new Insets(0, 0, 5, 0);
		gbc_text_roomFacilitiesID.gridx = 2;
		gbc_text_roomFacilitiesID.gridy = 2;
		//panel.add(text_roomFacilitiesID, gbc_text_roomFacilitiesID);
		text_roomFacilitiesID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		text_roomFacilitiesID.setColumns(10);
		
		lblRoomFacilitiesName = new JLabel("Facility Name");
		GridBagConstraints gbc_lblRoomFacilitiesName = new GridBagConstraints();
		gbc_lblRoomFacilitiesName.anchor = GridBagConstraints.WEST;
		gbc_lblRoomFacilitiesName.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomFacilitiesName.gridx = 0;
		gbc_lblRoomFacilitiesName.gridy = 3;
		panel.add(lblRoomFacilitiesName, gbc_lblRoomFacilitiesName);
		
		text_roomFacilitiesName = new JTextField();
		GridBagConstraints gbc_text_roomFacilitiesName = new GridBagConstraints();
		gbc_text_roomFacilitiesName.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_roomFacilitiesName.gridwidth = 2;
		gbc_text_roomFacilitiesName.insets = new Insets(0, 0, 5, 0);
		gbc_text_roomFacilitiesName.gridx = 2;
		gbc_text_roomFacilitiesName.gridy = 3;
		panel.add(text_roomFacilitiesName, gbc_text_roomFacilitiesName);
		text_roomFacilitiesName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		text_roomFacilitiesName.setColumns(10);
		//text_roomFacilitiesName.setInputVerifier(new StringValidator(null, text_roomFacilitiesName, "Enter only text values"));
		
		lblRoomFacilitiesDesc = new JLabel("Description");
		GridBagConstraints gbc_lblRoomFacilitiesDesc = new GridBagConstraints();
		gbc_lblRoomFacilitiesDesc.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRoomFacilitiesDesc.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomFacilitiesDesc.gridx = 0;
		gbc_lblRoomFacilitiesDesc.gridy = 4;
		panel.add(lblRoomFacilitiesDesc, gbc_lblRoomFacilitiesDesc);
		
		text_roomFacilitiesDesc = new JTextArea(2,1);
		text_roomFacilitiesDesc.setWrapStyleWord(true);
		text_roomFacilitiesDesc.setLineWrap(true);
		JScrollPane spane = new JScrollPane(text_roomFacilitiesDesc);
		GridBagConstraints gbc_text_roomFacilitiesDesc = new GridBagConstraints();
		gbc_text_roomFacilitiesDesc.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_roomFacilitiesDesc.gridwidth = 2;
		gbc_text_roomFacilitiesDesc.insets = new Insets(0, 0, 5, 0);
		gbc_text_roomFacilitiesDesc.gridx = 2;
		gbc_text_roomFacilitiesDesc.gridy = 4;
		panel.add(spane, gbc_text_roomFacilitiesDesc);
		
		//text_roomFacilitiesDesc.setInputVerifier(new StringValidator(null, text_roomFacilitiesDesc, "Enter only text values"));
		
		
		
		text_roomFacilitiesName.addFocusListener(this);
		text_roomFacilitiesID.addFocusListener(this);


		transExcel = new JLabel();
		GridBagConstraints gbc_lblCustomerDetails_excel = new GridBagConstraints();
		gbc_lblCustomerDetails_excel.insets = new Insets(0, 0, 5, 0);
		gbc_lblCustomerDetails_excel.anchor = GridBagConstraints.EAST;
		gbc_lblCustomerDetails_excel.gridx = 6;
		gbc_lblCustomerDetails_excel.gridy = 0;
		add(transExcel, gbc_lblCustomerDetails_excel);	
		
		tableModel = new DefaultTableModel(Constants.roomFaclitiesColNames, 0);
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
	    header.setToolTipStrings(Constants.roomFacilitiesTipStr);
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
		
		room_facilities_controller = new RoomFacilitiesController(tableModel, table);
		room_facilities_controller.retrieveAll(DatabaseConstants.TABLE_ROOM_FACILITES_COLS);
		
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
                          ExcelExporter.fillData(table, filePath,"Facilities Details");
                          JOptionPane.showMessageDialog(null, "Data saved at "+filePath+" successfully", "Message",JOptionPane.INFORMATION_MESSAGE);
                          Desktop.getDesktop().open(new File(filePath));
            		}                } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
		});
		
		lblPrice = new JLabel("Price");
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.anchor = GridBagConstraints.WEST;
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 0;
		gbc_lblPrice.gridy = 5;
		panel.add(lblPrice, gbc_lblPrice);
		
		text_price = new JTextField();
		GridBagConstraints gbc_text_price = new GridBagConstraints();
		gbc_text_price.gridwidth = 2;
		gbc_text_price.insets = new Insets(0, 0, 5, 0);
		gbc_text_price.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_price.gridx = 2;
		gbc_text_price.gridy = 5;
		panel.add(text_price, gbc_text_price);
		text_price.setColumns(10);
		text_price.setInputVerifier(new DoubleValidator(null, text_price, "Enter only numeric values > 0"));
		
		btnSave = new JButton("Submit");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 2;
		gbc_btnSave.gridy = 9;
		panel.add(btnSave, gbc_btnSave);
		btnSave.setMnemonic(KeyEvent.VK_B);
		btnSave.addActionListener(this);
		
		btnCancel_1 = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel_1 = new GridBagConstraints();
		gbc_btnCancel_1.insets = new Insets(0, 0, 5, 0);
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
		
		
		//text_roomFacilitiesID.setText(room_facilities_controller.generateFacilitiesId());
		text_roomFacilitiesID.setEditable(false);
	}

	private void setClear1()
	{
		text_roomFacilitiesID.setText("");
		text_roomFacilitiesName.setText("");
		text_roomFacilitiesDesc.setText("");
		text_price.setText("");
	
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
					if(text_roomFacilitiesName.getText().trim().length() == 0)
					{
						text_roomFacilitiesName.requestFocus(true);
						text_roomFacilitiesName.selectAll();
						JOptionPane.showMessageDialog(this, "Enter facility name", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(text_roomFacilitiesDesc.getText().trim().length() == 0)
					{
						text_roomFacilitiesDesc.requestFocus(true);
						text_roomFacilitiesDesc.selectAll();
						JOptionPane.showMessageDialog(this, "Enter facility description", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(text_price.getText().trim().length() == 0)
					{
						text_price.requestFocus(true);
						text_price.selectAll();
						JOptionPane.showMessageDialog(this, "Enter facility price > 0", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
					try{
					double facility_price = Double.parseDouble(text_price.getText().trim());
					RoomFacilities obj_roomFacilities = new RoomFacilities();
					obj_roomFacilities.setRoom_facilities_ID(room_facilities_controller.generateID());
					obj_roomFacilities.setRoom_facilities_name(text_roomFacilitiesName.getText().trim().toUpperCase());
					obj_roomFacilities.setRoom_facilities_desc(text_roomFacilitiesDesc.getText().trim().toUpperCase());
					obj_roomFacilities.setRoom_facilities_price(""+BigDecimalType.roundDown(facility_price));
					RoomFacilitiesController obj_controller = new RoomFacilitiesController(obj_roomFacilities);
					int s = obj_controller.submitFacility();
					
					if(s>0)
					{
					JOptionPane.showMessageDialog(this,"Facilities Updated Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
					//text_roomFacilitiesID.requestFocus(true);
					text_roomFacilitiesName.requestFocus(true);
					room_facilities_controller.retrieveAll(DatabaseConstants.TABLE_ROOM_FACILITES_COLS);
					sbm_consignCom.db.add(text_roomFacilitiesName.getText().trim().toUpperCase());
					updateUI();
					setClear1();
					}
					else
					{
					JOptionPane.showMessageDialog(this,"Duplicate value for room facility name","Failure",JOptionPane.ERROR_MESSAGE);
					}
					
				
					}catch(NumberFormatException ee){
						//text_roomFacilitiesID.requestFocus(true);
						text_roomFacilitiesName.requestFocus(true);
						JOptionPane.showMessageDialog(this,"Enter the values correctly","Failure",JOptionPane.ERROR_MESSAGE);}
					catch (Exception ee) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(this,""+ee,"Failure",JOptionPane.ERROR_MESSAGE);
					}
					
			

					}
					
				}
				else if(e.getSource()==btnSubmit)
				{
					room_facilities_controller.updateService(DatabaseConstants.UPDATE_FACILITIES, DatabaseConstants.TABLE_ROOM_FACILITES_COLS, ""+combo_search.getSelectedItem());
				}
			}
 

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==text_roomFacilitiesID)
			text_roomFacilitiesID.selectAll();
		else if(arg0.getSource()==text_roomFacilitiesName)
			text_roomFacilitiesName.selectAll();
		else if(arg0.getSource()==text_roomFacilitiesDesc)
			text_roomFacilitiesDesc.selectAll();	
		else if(arg0.getSource()==text_price)
			text_price.selectAll();	
 
 
		else {}
	}
	@Override
	public void focusLost(FocusEvent arg0) {

		if(arg0.getSource()==text_roomFacilitiesID)
			text_roomFacilitiesID.setText(text_roomFacilitiesID.getText().trim().toUpperCase());
		else if(arg0.getSource()==text_roomFacilitiesName)
			text_roomFacilitiesName.setText(text_roomFacilitiesName.getText().trim().toUpperCase());
		else if(arg0.getSource()==text_roomFacilitiesDesc)
			text_roomFacilitiesDesc.setText(text_roomFacilitiesDesc.getText().trim().toUpperCase());		
		
		
 

		else{}
 
	}

 
 
 
	public void uplcColor()
	{
		lblRoomFacilitiesId.setForeground(new Color(SetColor.cColor));
		lblRoomFacilitiesDesc.setForeground(new Color(SetColor.cColor));
		lblRoomFacilitiesName.setForeground(new Color(SetColor.cColor));
		lblPrice.setForeground(new Color(SetColor.cColor));
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
		lblRoomFacilitiesId.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRoomFacilitiesDesc.setFont(new Font(ctFType,ctfProp,ctSize));
		lblRoomFacilitiesName.setFont(new Font(ctFType,ctfProp,ctSize));
		lblPrice.setFont(new Font(ctFType,ctfProp,ctSize));
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
