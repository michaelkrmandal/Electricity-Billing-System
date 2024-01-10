package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CalculateBill extends JFrame implements ActionListener {
	JTextField tfunits;
	JButton submit, cancel;
	JLabel labelname, labeladdress;
	Choice meternumber, cmonths;
	
	CalculateBill() {
		setSize(700, 500);
		setLocation(400, 160);
		
		JPanel p = new JPanel();
		p.setLayout(null);
		p.setBackground(new Color(173, 216, 230));
		add(p);
		
		JLabel heading = new JLabel("Calculate Electricity Bill");
		heading.setBounds(130, 10, 400, 25);
		heading.setFont(new Font("Tahoma", Font.PLAIN, 24));         
		p.add(heading);
		
		JLabel lblmeterno = new JLabel("Meter Number");    
		lblmeterno.setBounds(100, 80, 100, 20);
		p.add(lblmeterno);
		
		meternumber = new Choice();
		
		try {
			Connect c = new Connect();
			ResultSet rs = c.s.executeQuery("SELECT * FROM customer");
			while(rs.next()) {
				meternumber.add(rs.getString("meter_no"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		meternumber.setBounds(240, 80, 200, 20);
		p.add(meternumber);
	
		JLabel lblname = new JLabel("Name");
		lblname.setBounds(100, 120, 100, 20);
		p.add(lblname);
		
		labelname = new JLabel("");
		labelname.setBounds(240, 120, 200, 20);
		p.add(labelname);
		
		JLabel lbladdress = new JLabel("Address");
		lbladdress.setBounds(100, 160, 100, 20);
		p.add(lbladdress);
		
		labeladdress = new JLabel("");
		labeladdress.setBounds(240, 160, 200, 20);
		p.add(labeladdress);
		
		try {
			Connect c = new Connect();
			ResultSet rs = c.s.executeQuery("SELECT * FROM customer WHERE meter_no = '"+meternumber.getSelectedItem()+"'");
			while(rs.next()) {
				labelname.setText(rs.getString("name"));
				labeladdress.setText(rs.getString("address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		meternumber.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				try {
					Connect c = new Connect();
					ResultSet rs = c.s.executeQuery("SELECT * FROM customer WHERE meter_no = '"+meternumber.getSelectedItem()+"'");
					while(rs.next()) {
						labelname.setText(rs.getString("name"));
						labeladdress.setText(rs.getString("address"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JLabel lblunits = new JLabel("Units Consumed");
		lblunits.setBounds(100, 200, 100, 20);
		p.add(lblunits);
		
		tfunits = new JTextField();
		tfunits.setBounds(240, 200, 200, 20);
		p.add(tfunits);
		
		JLabel lblmonths = new JLabel("Month");
		lblmonths.setBounds(100, 240, 100, 20);
		p.add(lblmonths);
		
		cmonths = new Choice();
		cmonths.setBounds(240, 240, 200, 20);
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
		p.add(cmonths);
		
		
		submit = new JButton("Submit");
		submit.setBounds(120, 350, 100, 25);
		submit.setBackground(Color.BLACK);
		submit.setForeground(Color.WHITE);
		submit.addActionListener(this);
		p.add(submit);
		
		cancel = new JButton("Cancel");
		cancel.setBounds(250, 350, 100, 25);
		cancel.setBackground(Color.BLACK);
		cancel.setForeground(Color.WHITE);
		cancel.addActionListener(this);
		p.add(cancel);
		
		setLayout(new BorderLayout());
		
		add(p, "Center");
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/hicon2.jpg"));
		Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel image = new JLabel(i3);
		add(image, "West");
		
		getContentPane().setBackground(Color.WHITE);
		
		setVisible(true);
	}
	
	public void actionPerformed (ActionEvent ae) {
		if(ae.getSource() == submit) {
			String meter = meternumber.getSelectedItem();
			String units = tfunits.getText();
			String months = cmonths.getSelectedItem();
			
			int totalbill = 0;
			int unit_consumed = Integer.parseInt(units);
			String query = "SELECT * FROM tax";
			
			try {
				Connect c = new Connect();
			    ResultSet rs = c.s.executeQuery(query);
			   
			    while(rs.next()) {
			    	totalbill += unit_consumed * Integer.parseInt(rs.getString("cost_per_unit"));
			    	totalbill += Integer.parseInt(rs.getString("meter_rent"));
			    	totalbill += Integer.parseInt(rs.getString("service_charge"));
			    	totalbill += Integer.parseInt(rs.getString("service_tax"));
			    	totalbill += Integer.parseInt(rs.getString("swacch_bharat_cess"));
			    	totalbill += Integer.parseInt(rs.getString("fixed_tax"));	
			    }
			    
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			
			String query2 = "INSERT INTO bill VALUES('"+meter+"', '"+months+"', '"+units+"', '"+totalbill+"', 'Not Paid')";
			try {
				Connect c = new Connect();
				c.s.executeUpdate(query2);
				
				JOptionPane.showMessageDialog(null, "Customer Bill Updated Successfully");
				setVisible(false);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
		} else {
			setVisible(false);
		}
	}

	public static void main(String[] args) {
	  new CalculateBill();

	}
}

