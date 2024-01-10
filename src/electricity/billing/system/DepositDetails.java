package electricity.billing.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class DepositDetails extends JFrame implements ActionListener{
	
  Choice meternumber, cmonths;
  JTable table;
  JButton search, print;
  
   DepositDetails() {
	super("Deposit Details");
	
	setSize(700, 700);
	setLocation(400, 100);
	
	setLayout(null);
	getContentPane().setBackground(Color.WHITE);
	
	JLabel lblmeternumber = new JLabel("Search By Meter Number");
	lblmeternumber.setBounds(20, 20, 150, 20);
	add(lblmeternumber);
	
	meternumber = new Choice();
	meternumber.setBounds(180, 20, 150, 20);
	add(meternumber);
	
	try {
		Connect c = new Connect();
		ResultSet rs = c.s.executeQuery("SELECT * FROM customer");
		while(rs.next()) {
			meternumber.add(rs.getString("meter_no"));
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	JLabel lblmonth = new JLabel("Search By Month");
	lblmonth.setBounds(400, 20, 100, 20);
	add(lblmonth);
	
	cmonths = new Choice();
	cmonths.setBounds(520, 20, 150, 20);
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
	
	table = new JTable();
	try {
		Connect c = new Connect();
		ResultSet rs = c.s.executeQuery("SELECT * FROM bill");
		
		table.setModel(DbUtils.resultSetToTableModel(rs));
		
	} catch(Exception e) {
		e.printStackTrace();
	}
	
	JScrollPane sp = new JScrollPane(table);
	sp.setBounds(0, 100, 700, 600);
	add(sp);
	
	search = new JButton("Search");
	search.setBounds(20, 70, 80, 20);
	search.addActionListener(this);
	add(search);
	
	print = new JButton("Print");
	print.setBounds(120, 70, 80, 20);
	print.addActionListener(this);
	add(print);
	
	setVisible(true);
	}
   
   public void actionPerformed(ActionEvent ae) {
	   if(ae.getSource() == search) {
		   String query = "SELECT * FROM bill WHERE meter_no = '"+meternumber.getSelectedItem()+"' AND month = '"+cmonths.getSelectedItem()+"'";
	       
		   try {
			   Connect c = new Connect();
			   ResultSet rs = c.s.executeQuery(query);
			   
			   table.setModel(DbUtils.resultSetToTableModel(rs));
			   
		   } catch (Exception e) {
			   e.printStackTrace();
		   }
	   } else {  
		   try {
			   table.print();
			   
		   } catch (Exception e) {
			   e.printStackTrace();
		   }
	   }
   }
	
	public static void main(String[] args) {
		new DepositDetails();

	}

}
