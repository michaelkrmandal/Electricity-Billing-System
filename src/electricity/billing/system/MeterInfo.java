package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MeterInfo extends JFrame implements ActionListener {
	
	JButton next, cancel;
	Choice meterlocation, metertype, phasecode, billtype;
	String meternumber;
	
	MeterInfo(String meternumber) {
		this.meternumber = meternumber;
		setSize(700, 500);
		setLocation(400, 200);
		
		JPanel p = new JPanel();
		p.setLayout(null);
		p.setBackground(new Color(173, 216, 230));
		add(p);
		
		JLabel heading = new JLabel("Meter Information");
		heading.setBounds(180, 10, 200, 25);
		heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
		p.add(heading);
		
		JLabel lblmeterno = new JLabel("Meter Number");
		lblmeterno.setBounds(100, 80, 100, 20);
		p.add(lblmeterno);
		
		JLabel lblmeternumber = new JLabel(meternumber);
		lblmeternumber.setBounds(240, 80, 200, 20);
		p.add(lblmeternumber);
	
		JLabel lblmeterloc = new JLabel("Meter Location");
		lblmeterloc.setBounds(100, 120, 100, 20);
		p.add(lblmeterloc);
		
		meterlocation = new Choice();
		meterlocation.add("Outside");
		meterlocation.add("Inside");
		meterlocation.setBounds(240, 120, 200, 20);
		p.add(meterlocation);
		
		JLabel lblmetertype = new JLabel("Meter Type");
		lblmetertype.setBounds(100, 160, 100, 20);
		p.add(lblmetertype);
		
		metertype = new Choice();
		metertype.add("Electric Meter");
		metertype.add("Solar Meter");
		metertype.add("Smart Meter");
		metertype.setBounds(240, 160, 200, 20);
		p.add(metertype);
		
		JLabel lblcode = new JLabel("Phase Code");
		lblcode.setBounds(100, 200, 100, 20);
		p.add(lblcode);
		
		phasecode = new Choice();
		phasecode.add("011");
		phasecode.add("022");
		phasecode.add("033");
		phasecode.add("044");
		phasecode.add("055");
		phasecode.add("066");
		phasecode.add("077");
		phasecode.add("088");
		phasecode.add("099");
		phasecode.setBounds(240, 200, 200, 20);
		p.add(phasecode);
		
		JLabel lblbilltype = new JLabel("Bill Types");
		lblbilltype.setBounds(100, 240, 100, 20);
		p.add(lblbilltype);
		
		billtype = new Choice();
		billtype.add("Normal");
		billtype.add("Industrial");
		billtype.setBounds(240, 240, 200, 20);
		p.add(billtype);
		
		JLabel lbldays = new JLabel("Days");
		lbldays.setBounds(100, 280, 100, 20);
		p.add(lbldays);
		
		JLabel lblday = new JLabel("30 Days");
		lblday.setBounds(240, 280, 100, 20);
		p.add(lblday);
		
		JLabel lblnote = new JLabel("Note");
		lblnote.setBounds(100, 320, 100, 20);
		p.add(lblnote);
		
		JLabel lblnotes = new JLabel("By Default Bill is calculated for 30 days only");
		lblnotes.setBounds(240, 320, 500, 20);
		p.add(lblnotes);
		
		next = new JButton("Submit");
		next.setBounds(220, 390, 100, 25);
		next.setBackground(Color.BLACK);
		next.setForeground(Color.WHITE);
		next.addActionListener(this);
		p.add(next);
		
		
		setLayout(new BorderLayout());
		add(p, "Center");
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/hicon1.jpg"));
		Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel image = new JLabel(i3);
		add(image, "West");
		
		getContentPane().setBackground(Color.WHITE);
		setVisible(true);
	}
	
	public void actionPerformed (ActionEvent ae) {
		if(ae.getSource() == next) {
			String meter = meternumber;
			String location = meterlocation.getSelectedItem();
			String type = metertype.getSelectedItem();
			String code = phasecode.getSelectedItem();
			String btype = billtype.getSelectedItem();
			String days = "30";
			
			try {
				Connect c = new Connect();
				
				String query = "INSERT INTO meter_info VALUES('"+meter+"', '"+location+"', '"+type+"', '"+code+"', '"+btype+"', '"+days+"')";
			    
			    c.s.executeUpdate(query);
			    
			    JOptionPane.showMessageDialog(null, "Meter Information Added Successfully");
			    setVisible(false);
	
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		} else {
			setVisible(false);
		}
	}

	public static void main(String[] args) {
	  new MeterInfo("");

	}

}
