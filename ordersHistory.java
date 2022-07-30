
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.swing.*;
import java.util.*;
public class ordersHistory extends JFrame implements ActionListener{
	
	//UI components
	public JTextField user_Name = new JTextField(20);
	public JTextField phone = new JTextField(20);
	protected JButton closeBtn = new JButton ("Close");
	
	public ordersHistory(Client client){
		
		JPanel p2 = new JPanel(new GridLayout(2,2));
		p2.add(closeBtn);
		p2.add(new Label(""));
		p2.add(new Label(""));
		p2.setAlignmentX(CENTER_ALIGNMENT);
		
		closeBtn.addActionListener(this);
		
		//Order table UI
		String data[][] = null;
		String column[]={"orderID", "srcClientID", "desClientID", "orderDate"};
		String query;
		int orderID, srcClientID, desClientID, i=0,k=0,arrSize=0;
		Date orderDate;
		Connection conn;
		Statement stmt;
		ResultSet rs;
		
		//Getting the user order history from Orders DB
		try{
			conn = ConnectionDB.getConn();
			query ="SELECT * from orders";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				arrSize++;
			}
			
			data = new String[arrSize][20];
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				k=0;
				orderID = rs.getInt("orderID");
				srcClientID = rs.getInt("srcClientID");
				desClientID = rs.getInt("desClientID");
				orderDate = rs.getDate("orderDate");
				
				int clientID = client.getClientID();
				
				if(srcClientID == clientID) {
									
					data[i][k++] = "" + orderID;
					data[i][k++] = "" + srcClientID;
					data[i][k++] = "" +desClientID;
					data[i][k++] = orderDate.toString();
					
					i++;
				}
			}
			
			rs.close();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		//Table setting
		JTable myTable=new JTable(data,column);
		myTable.setBounds(30,40,200,300);
		JScrollPane sp=new JScrollPane(myTable);
		
		setLayout(new BorderLayout());
		add(sp, BorderLayout.CENTER);
		add(p2, BorderLayout.SOUTH);
		pack();
		setSize(550,600);
	}
	
	@Override
	public void actionPerformed(ActionEvent av) {
		
		String arg = av.getActionCommand();
		
		if(arg.equals("Close")){
			this.setVisible(false);
		} 
	}
	

}
