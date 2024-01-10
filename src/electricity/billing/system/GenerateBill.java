package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GenerateBill extends JFrame implements ActionListener{
	String meter;
	Choice cmonths;
	JButton bill;
	JTextArea area;
	
      GenerateBill(String meter) {
    	  this.meter = meter;
    	  setSize(500, 700);
    	  setLocation(550, 30);
    	  
    	  setLayout(new BorderLayout());
    	  
    	  JPanel panel = new JPanel();
    	  
    	  JLabel heading = new JLabel("Generate Bill");
    	  JLabel meternumber = new JLabel(meter);
    	  
    	    cmonths = new Choice();
    	  
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
			
		    area = new JTextArea(50, 15);
			area.setText("\n\n\t-------Click on the------\n\tGenerate Bill Button to get \n\t the bill of the Selected Month");
			area.setFont(new Font("Senserif", Font.ITALIC, 18 ));
			
			JScrollPane sp = new JScrollPane(area);
			
			bill = new JButton("Generate Bill");
			bill.addActionListener(this);
			
			panel.add(heading);
			panel.add(meternumber);
			panel.add(cmonths);
			add(panel, "North");
			add(sp, "Center");
			add(bill, "South");
	    	  
    	 
    	  setVisible(true);
      }
      
      public void actionPerformed(ActionEvent ae) {
    	  try {
    		  
    		  Connect c = new Connect();
    		  
    		  String month = cmonths.getSelectedItem();
    		  
    		  area.setText("\tReliance Power Limited\nELECTRICITY BILL GENERATED FOR THE MONTH\n\tOF "+month+", 2023");
    		 
    		  ResultSet rs = c.s.executeQuery("SELECT * FROM customer WHERE meter_no = '"+meter+"'");
    		  if(rs.next()) {
    			  area.append("\n    Customer Name: " + rs.getString("name"));
    			  area.append("\n    Meter Number: " + rs.getString("meter_no"));
    			  area.append("\n    Address: " + rs.getString("address"));
    			  area.append("\n    City: " + rs.getString("city"));
    			  area.append("\n    State: " + rs.getString("state"));
    			  area.append("\n    Email: " + rs.getString("email"));
    			  area.append("\n    Phone: " + rs.getString("phone"));
    			  area.append("\n------------------------------------------------------------------");
    			  area.append("\n");
    		  }
    		  
    		  rs = c.s.executeQuery("SELECT * FROM meter_info WHERE meter_no = '"+meter+"'");
    		  if(rs.next()) {
    			  area.append("\n    Meter Location: " + rs.getString("meter_loc"));
    			  area.append("\n    Meter Type: " + rs.getString("meter_type"));
    			  area.append("\n    Phase Code: " + rs.getString("phase_code"));
    			  area.append("\n    Bill Type: " + rs.getString("bill_type"));
    			  area.append("\n    Days: " + rs.getString("days"));
    			  area.append("\n------------------------------------------------------------------");
    			  area.append("\n");
    		  }
    		  
    		  rs = c.s.executeQuery("SELECT * FROM tax");
    		  if(rs.next()) {
    			  area.append("\n");
    			  area.append("\n    Cost Per Unit: " + rs.getString("cost_per_unit"));
    			  area.append("\n    Meter Rent: " + rs.getString("meter_rent"));
    			  area.append("\n    Service Charge: " + rs.getString("service_charge"));
    			  area.append("\n    Service Tax: " + rs.getString("service_tax"));
    			  area.append("\n    Swacch Bharat Cess: " + rs.getString("swacch_bharat_cess"));
    			  area.append("\n    Fixed Tax: " + rs.getString("fixed_tax"));
    			  area.append("\n");
    		  }
    		  
    		  rs = c.s.executeQuery("SELECT * FROM bill WHERE meter_no = '"+meter+"' AND month = '"+month+"'");
    		  if(rs.next()) {
    			  area.append("\n");
    			  area.append("\n    Current Month: " + rs.getString("month"));
    			  area.append("\n    Units Consumed: " + rs.getString("units"));
    			  area.append("\n    Total Bill: " + rs.getString("totalbill"));
    			  area.append("\n------------------------------------------------------------------");
    			  area.append("\n    Total Payable: " + rs.getString("totalbill"));
    			  area.append("\n");
    		  }
    		  
    		  
    	  } catch (Exception e) {
    		 e.printStackTrace();
    	  }
      }
      
	public static void main(String[] args) {
		new GenerateBill("");

	}

}
