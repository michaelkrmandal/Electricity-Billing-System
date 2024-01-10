package electricity.billing.system;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class PayBill extends JFrame implements ActionListener{
 Choice cmonths;
 JButton pay, back;
 String meter;
 
	PayBill(String meter) {
		this.meter = meter;
		
		setLayout(null);
	    setBounds(300, 150, 900, 600);
	    
	    JLabel heading = new JLabel("Electricity Bill");
	    heading.setFont(new Font("Tahoma", Font.BOLD, 24));
	    heading.setBounds(120, 5, 400, 30);
	    add(heading);
	    
	    JLabel lblmeternumber = new JLabel("Meter Number");
	    lblmeternumber.setBounds(35, 80, 200, 20);
	    add(lblmeternumber);
	    
	    JLabel meternumber = new JLabel("");
	    meternumber.setBounds(300, 80, 200, 20);
	    add(meternumber);
	    
	    JLabel lblname = new JLabel("Name");
	    lblname.setBounds(35, 140, 200, 20);
	    add(lblname);
	    
	    JLabel labelname = new JLabel("");
	    labelname.setBounds(300, 140, 200, 20);
	    add(labelname);
	    
	    JLabel lblmonth = new JLabel("Month");
	    lblmonth.setBounds(35, 200, 200, 20);
	    add(lblmonth);
	    
	    cmonths = new Choice();
		cmonths.setBounds(300, 200, 200, 20);
		cmonths.add("January");
		cmonths.add("February");
		cmonths.add("March");
		cmonths.add("April");
		cmonths.add("May");
		cmonths.add("June");
		cmonths.add("July");
		cmonths.add("August");
		cmonths.add("September");
		cmonths.add("October");
		cmonths.add("November");
		cmonths.add("December");
		add(cmonths);
	    
	  JLabel lblunits = new JLabel("Units");
	  lblunits.setBounds(35, 260, 200, 20);
	    add(lblunits);
	    
      JLabel labelunits = new JLabel("");
      labelunits.setBounds(300, 260, 200, 20);
      add(labelunits);
      
      JLabel lbltotalbill = new JLabel("Total Bill");
      lbltotalbill.setBounds(35, 320, 200, 20);
	    add(lbltotalbill);
	    
      JLabel labeltotalbill = new JLabel("");
      labeltotalbill.setBounds(300, 320, 200, 20);
      add(labeltotalbill);
      
      JLabel lblstatus = new JLabel("Status");
      lblstatus.setBounds(35, 380, 200, 20);
	    add(lblstatus);
	    
      JLabel labelstatus = new JLabel("");
      labelstatus.setBounds(300, 380, 200, 20);
      labelstatus.setForeground(Color.RED);
      add(labelstatus);
	    
      try {
    	  Connect c = new Connect();
    	  ResultSet rs = c.s.executeQuery("SELECT * FROM customer WHERE meter_no = '"+meter+"'");
    	  while(rs.next()) {
    		  meternumber.setText(meter);
    		  labelname.setText(rs.getString("name"));
    	  }
    	  
    	  rs = c.s.executeQuery("SELECT * FROM bill WHERE meter_no = '"+meter+"' AND month = 'January'");
    	  while(rs.next()) {
    		  labelunits.setText(rs.getString("units"));
    		  labeltotalbill.setText(rs.getString("totalbill"));
    		  labelstatus.setText(rs.getString("status"));
    	  }
    	  
      } catch (Exception e) {
    	  e.printStackTrace();
      }
		    
      cmonths.addItemListener(new ItemListener() {
    	 public void itemStateChanged(ItemEvent ae) {
    	     try {
    	    	  Connect c = new Connect();
    	    	
    	    	  ResultSet rs = c.s.executeQuery("SELECT * FROM bill WHERE meter_no = '"+meter+"' AND month = '"+cmonths.getSelectedItem()+"'");
    	    	  while(rs.next()) {
    	    		  labelunits.setText(rs.getString("units"));
    	    		  labeltotalbill.setText(rs.getString("totalbill"));
    	    		  labelstatus.setText(rs.getString("status"));
    	    	  }
    	    	  
    	      } catch (Exception e) {
    	    	  e.printStackTrace();
    	      }
    	 }	  
      });
      
      pay = new JButton("Pay");
      pay.setBackground(Color.BLACK);
      pay.setForeground(Color.WHITE);
      pay.setBounds(100, 460, 100, 25);
      pay.addActionListener(this);
      add(pay);
      
      back = new JButton("Back");
      back.setBackground(Color.BLACK);
      back.setForeground(Color.WHITE);
      back.setBounds(230, 460, 100, 25);
      back.addActionListener(this);
      add(back);
	    
      getContentPane().setBackground(Color.WHITE);
      
      ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/bill.png"));
      Image i2 = i1.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
      ImageIcon i3 = new ImageIcon(i2);
      JLabel image = new JLabel(i3);
      image.setBounds(400, 120, 600, 300);
      add(image);
      
	    setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == pay) {
			try {
				Connect c = new Connect();
			    c.s.executeUpdate("UPDATE bill SET status = 'Paid' WHERE meter_no = '"+meter+"' AND month = '"+cmonths.getSelectedItem()+"'");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			setVisible(false);
			new Paytm(meter);
			
		} else {
			setVisible(false);
		}
	}
	
	public static void main(String[] args) {
		new PayBill("");

	}

}
