package com.hms.learning;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Main extends JFrame implements ActionListener{

	JButton b, tb;
	Main()
	{
	     try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel() {

			     @Override
			     public UIDefaults getDefaults() {
			      UIDefaults ret = super.getDefaults();
			      //ret.put("defaultFont", new Font(Font.MONOSPACED, Font.BOLD, 16)); // supersize me
			      ret.put("Button.disabledText", Color.red);
			      return ret;
			     }

			    });
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	          setDefaultCloseOperation(EXIT_ON_CLOSE);
	          setLayout(new FlowLayout(FlowLayout.LEFT));

	          setSize(500, 500);
	          setLocationRelativeTo(null);

	          add(new JLabel("someLabel 1"));
	          b = new JButton("someButton 1");
	          add(b);
	          b.addActionListener(this);

	          add(new JLabel("someLabel 2"));
	          tb = new JButton("someButton 2");
	          add(tb);
	          setVisible(true);
	         

	            
	       
	}
 public static void main(String[] args)
 {
	 new Main();
 }


@Override
public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	if(arg0.getSource()==b)
	{
		UIManager.put("Button.disabledText", Color.red);
		b.setEnabled(false);
		tb.setEnabled(false);
	}
}

}