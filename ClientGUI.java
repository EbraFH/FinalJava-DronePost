

import java.time.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;



import java.io.IOException;

public class ClientGUI extends Frame implements ActionListener {
	
	// Varibles to Handle	
		protected  int clientNumber;
		protected  String clientName;
		protected  String clientEmail;
		protected  String clientPhoneNumber;
		protected  String clientAddress;
		protected  int clientShipmentsLeft;
		
		protected Client myClient;
		protected DronePostSystem DronePostSystem;
		protected ArrayList<Client> avialClients;
		
		
		
		// Frame Elements
		
		
		//Gui Elemnts To Delcare
		protected JFrame ClientGUI;
		protected JPanel loginPanel, RegisterPanel, UserPanel, cardPane;
		CardLayout card;
		
		/* Login Card */
		
		protected JButton btnSign = new JButton("Sign In");
		protected JButton btnLogRegister = new JButton("Register");
		protected JTextField txtLogEmail = new JTextField();
		protected Label lblLogEmail = new Label("Email");
		
		/* Register Card */
		protected JButton btnRegRegister = new JButton("Register");
		protected Choice serviceType = new Choice();
		
		JTextField txtRegName = new JTextField(10);
		JTextField txtRegEmail = new JTextField(10);
		JTextField txtRegPhone = new JTextField(10);
		JTextField txtRegAddress = new JTextField(10);
		Label lblRegName = new Label("Name");
		Label lblRegEmail = new Label("Email");
		Label lblRegPhone = new Label("Phone Number");
		Label lblRegAddress = new Label("Address");
		Label lblPackgeSelct = new Label("Select Service Package:");
		
		/* User Card */
		JPanel userP = new JPanel(new GridLayout(0,2));
		JPanel buttonsP = new JPanel(new FlowLayout());
		JPanel textP = new JPanel(new BorderLayout());

		//Text and Labels
		Label lblUsrName = new Label();
		Label lblUsrEmail = new Label();
		Label lblUsrPhone = new Label();
		Label lblUsrAddress = new Label();
		Label lblName = new Label("Name");
		Label lblEmail = new Label("Email");
		Label lblPhone = new Label("Phone number");
		Label lblAddress = new Label("Address");
		Label lblTxtBox = new Label("Enter Message Text Below");
		protected JTextField txtMsg = new JTextField(1);
		
		protected Choice chDesClient = new Choice();
		
		
		protected JTextArea resultArea = new JTextArea(10,10);
		protected JScrollPane sp = new JScrollPane(resultArea);
		
		//Buttons
		protected JButton btnShipment = new JButton("Request Shipment");
		protected JButton btnHistory = new JButton("Get History");
		protected JButton btnSendMsg = new JButton("Send Message");
		
		
		
		
		public ClientGUI() // Ctor
		{
			makeForm();
		}
		
		
		public ClientGUI(DronePostSystem DronePostSystem) // Ctor
		{
			this.DronePostSystem = DronePostSystem;
			makeForm();
		}
		
		public void makeForm()
		{
			// Create the Form in orderly Fashion
			createMyWindow();
			arrangeCards();
			addComponents();
			actionEventFunc();
		}
		
		
		public void createMyWindow()
		{
			// Handle the Creation and parameters of main frame
			ClientGUI = new JFrame();
			ClientGUI.setTitle("DronePost Window - Beta");
			ClientGUI.setBounds(450,100,550,450);
			//ClientGUI.getContentPane().setLayout(null);
			//frame.getContentPane().setBackground(Color.blue);
			//ClientGUI.setVisible(true);
			ClientGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ClientGUI.setResizable(false);
		}
		
		public void arrangeCards()
		{
			// Handle the setup of the different panels
			// Define the Login panel
			loginPanel = new JPanel();
			//add components
			loginPanel.setLayout(new GridLayout(0,2));
			
			loginPanel.add(lblLogEmail);
			loginPanel.add(txtLogEmail);
			loginPanel.add(btnSign);
			loginPanel.add(btnLogRegister);
			
			RegisterPanel = new JPanel();
			
			serviceType.add("Basic: 50 Shipments for 99$");
			serviceType.add("Premium: 150 Shipments for 179$");
			
			RegisterPanel.setLayout(new GridLayout(0,2));
			RegisterPanel.add(lblRegName);
			RegisterPanel.add(txtRegName);
			RegisterPanel.add(lblRegEmail);
			RegisterPanel.add(txtRegEmail);
			RegisterPanel.add(lblRegPhone);
			RegisterPanel.add(txtRegPhone);
			RegisterPanel.add(lblRegAddress);
			RegisterPanel.add(txtRegAddress);
			RegisterPanel.add(lblPackgeSelct);
			RegisterPanel.add(serviceType);
			RegisterPanel.add(btnRegRegister);
			
			// Define User Card
			UserPanel = new JPanel();
			
			lblName.setBounds(20,20,100,23);
			lblEmail.setBounds(20,70,100,23);
			lblPhone.setBounds(20,120,100,23);
			lblAddress.setBounds(20,190,100,23);
			
			lblUsrName.setBounds(180,20,165,23);
			lblUsrEmail.setBounds(180,70,165,23);
			lblUsrPhone.setBounds(180,120,165,23);
			lblUsrAddress.setBounds(180,190,165,23);
			buttonsP.setSize(50, 100);
			
			userP.add(lblName);
			userP.add(lblUsrName);
			userP.add(lblEmail);
			userP.add(lblUsrEmail);
			userP.add(lblPhone);
			userP.add(lblUsrPhone);
			userP.add(lblAddress);
			userP.add(lblUsrAddress);
			
			buttonsP.add(btnShipment);
			buttonsP.add(btnHistory);
			buttonsP.add(btnSendMsg);
			buttonsP.add(chDesClient);
			
			textP.add(lblTxtBox,BorderLayout.NORTH);
			textP.add(txtMsg,BorderLayout.CENTER);
			textP.add(sp,BorderLayout.SOUTH);
			
			UserPanel.setLayout(new BorderLayout());
			UserPanel.add(userP,BorderLayout.NORTH);
			UserPanel.add(buttonsP,BorderLayout.CENTER);
			
			UserPanel.add(textP,BorderLayout.SOUTH);
			pack();
		}
		
		
		public void addComponents()
		{
		// Adds components to the main GUI Frame
		  card = new CardLayout();
		  cardPane = new JPanel();
		  cardPane.setLayout(card);
		  cardPane.add(loginPanel,"login");
		  cardPane.add(RegisterPanel,"register");
		  cardPane.add(UserPanel,"user");
		  
		  ClientGUI.add(cardPane);
		  ClientGUI.setVisible(true);
			
		}
		
		public void actionEventFunc()
		{
			// Sign buttons to action listener
			btnSign.addActionListener(this);
			btnLogRegister.addActionListener(this);
			btnRegRegister.addActionListener(this);
			btnShipment.addActionListener(this);
			btnHistory.addActionListener(this);
			btnSendMsg.addActionListener(this);
		} 
		
		public void actionPerformed(ActionEvent ev)
		{
			// Definitions of actions to be preformed by each button
			// btnShow Button Handler
			  // Read data From the data file to local array
			  if(ev.getSource()==btnSign)
			  {// Handler for Login attempt -> send quary to server with email, asking of Client
				  // If no Client, Login Fail
				  String reqEmail = txtLogEmail.getText();
				  this.myClient = this.DronePostSystem.postServer.getClientbyEmail(reqEmail);
				  if(this.myClient != null)
				  {
					 this.clientNumber = myClient.getClientID();
					 this.clientName = myClient.getClientName();
					 this.clientEmail = myClient.getClientEmail();
					 this.clientPhoneNumber = myClient.getClientPhoneNumber();
					 this.clientAddress = myClient.getClientAddress();
					 this.clientShipmentsLeft = myClient.getShipmentsLeft();
					 
					 //Handle data operations required for user interface- available clients list update, etc.
					 this.avialClients = this.DronePostSystem.postServer.getClientList();
					 populateClientChoise();
					 
					 //Update User Specific display Fields: Name, Addres, etc.
					 lblUsrName.setText(this.clientName);
					 lblUsrEmail.setText(this.clientEmail);
					 lblUsrPhone.setText(this.clientPhoneNumber);
					 lblUsrAddress.setText(this.clientAddress);
					 
					 card.show(cardPane,"user");
				  }
				  //card.show(cardPane,"user");
			  }
			  
			  if(ev.getSource()==btnLogRegister)
			  {
				 
				  card.show(cardPane,"register");
				  
			  }
			  
			  if(ev.getSource()==btnRegRegister)
			  {
				  
				// Set the ClientGui data right
				  this.clientNumber = this.DronePostSystem.postServer.findVacantClientIndex();// will return the next available index
				  this.clientName = txtRegName.getText();
				  this.clientEmail = txtRegEmail.getText();
				  this.clientPhoneNumber = txtRegPhone.getText();
				  this.clientAddress = txtRegAddress.getText(); 
				  
				  int selection = this.serviceType.getSelectedIndex();
				  if(selection == 0)//Case Basic package
					  this.clientShipmentsLeft = 50;
				  else if(selection == 1)//Case Premium package
					  this.clientShipmentsLeft = 150;
				  else
					  this.clientShipmentsLeft = -99;// Some error Accord
				  
				// Add Client to system
				  this.myClient = new Client (clientNumber,clientName,clientEmail,clientPhoneNumber,clientAddress,clientShipmentsLeft);
				  this.DronePostSystem.addClient(this.myClient);
				  
				//Handle data operations required for user interface- available clients list update, etc.
				 this.avialClients = this.DronePostSystem.postServer.getClientList();
				 populateClientChoise();
					 
					 //Update User Specific display Fields: Name, Addres, etc.
				 lblUsrName.setText(this.clientName);
				 lblUsrEmail.setText(this.clientEmail);
				 lblUsrPhone.setText(this.clientPhoneNumber);
				 lblUsrAddress.setText(this.clientAddress);
					 
				 card.show(cardPane,"user");
				  
			  }
			  
			  if(ev.getSource()==btnShipment)
			  {
				  int selection = this.chDesClient.getSelectedIndex();
				  int reqDestinationId =  avialClients.get(selection).getClientID();
				  if(reqDestinationId!=myClient.getClientID())
				  {
					  System.out.println("Sending Shipment request from: "+myClient.toString() + " To:  " + avialClients.get(selection).toString());
					  this.DronePostSystem.postServer.addShipmentRequest(myClient.getClientID(), reqDestinationId);
				  }
				  else
				  {
					 this.resultArea.append("Choose Self as Shipment Destination - Please select another Customer\n");
				  }
				  	
			  }
			  
			  if(ev.getSource()==btnHistory)
			  {
				  ordersHistory ordersHistory = new ordersHistory(myClient);
				  ordersHistory.show();
			  }
			  
			  if(ev.getSource()==btnSendMsg)
			  {
				  int selection = this.chDesClient.getSelectedIndex();
				  int reqDestinationId =  avialClients.get(selection).getClientID();
				  if(reqDestinationId!=myClient.getClientID())// Send The Message
				  {
					  String msgToSend = txtMsg.getText() + "\n";
					  this.resultArea.append("Sending Message to:" + avialClients.get(selection).getClientName() +"\n");
					  this.resultArea.append("Message Sent:" + msgToSend);
					  this.DronePostSystem.postServer.sendMsgToClient(reqDestinationId, msgToSend);
				  }
				  else
					  this.resultArea.append("Choose Self as Message Destination - Please select another Customer\n");    
			  }
		
		}// End of Action Listener
		
		public void populateClientChoise()
		{
			Client curClient = new Client(); 
			for(int i=0; i<this.avialClients.size();i++)
			{
			  curClient = avialClients.get(i);
			  String entry = "Name: " + curClient.getClientName() +"  " + curClient.getClientAddress();
			  chDesClient.add(entry);
			}
		}
		
		private void updateDisplay()
		{
			myClient = DronePostSystem.postServer.getClientbyEmail(txtLogEmail.getText());
			if(myClient!=null)
				System.out.println(myClient.toString());
			else
				System.out.println("Null!");
		}
		
		public DronePostSystem getDronePostsystem()
		{
			return this.DronePostSystem;
		}

}
