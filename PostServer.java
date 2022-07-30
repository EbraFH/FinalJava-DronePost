
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PostServer {
	
	ArrayList<Client> clientList = new ArrayList<Client>(0);
	ArrayList<Order> orderList = new ArrayList<Order>(0);
	ArrayList<Drone> droneList = new ArrayList<Drone>(0);
	private DronePostSystem DronePostSystem;
	private DBHandler dbHandler = new DBHandler();
	
	
	
	public PostServer(DronePostSystem dronePostSystem) {//Ctor
		DronePostSystem = dronePostSystem;
		this.dbHandler.setDronePostSystem(dronePostSystem);
		this.clientList = this.dbHandler.getClientList();
		this.droneList = this.dbHandler.getDroneList();
		this.orderList = this.dbHandler.getOrderList();
	}

	public void addClient(Client curClient)
	{
		clientList.ensureCapacity(clientList.size()+1);
		clientList.add(curClient);
		dbHandler.addClient(clientList);// updating server table 
		System.out.println("Client Added: " + curClient.toString());
	}
	
	public void addOrder(Order curOrder)
	{
		orderList.ensureCapacity(orderList.size()+1);
		orderList.add(curOrder);
		dbHandler.addOrder(orderList);// updating server table 
		System.out.println("Order Added: " + curOrder.toString());
	}
	
	public void addDrone(Drone curDrone)
	{
		droneList.ensureCapacity(droneList.size()+1);
		droneList.add(curDrone);
		dbHandler.addDrone(droneList);// updating server table 
		System.out.println("Drone Added: " + curDrone.toString());
	}
	
	public int findVacantClientIndex()
	{
		//Returns available index number for client.
		return this.clientList.size() + 1;
	}
	
	
	public void addClientByDetails(String cName, String cEmail, String cPhone, String cAddress)
	{
		// adds Client object, according to client details
		addClient(new Client(cName,cEmail,cPhone,cAddress));
	}
	
	public Client getClientbyEmail (String ReqEmail)
	{
		// retrive client object according to email, returns null if no such client exist
		//for(int i=0; i<clientList.size();i++)
		for(Client client : clientList)
		{
			if(client.getClientEmail().equals(ReqEmail))
				return client;
		}		
		return null;
	}
	
	public ArrayList<Client> getClientList() {
		return clientList;
	}

	public void setClientList(ArrayList<Client> clientList) {
		this.clientList = clientList;
	}
	
	public void addShipmentRequest(int sourceId, int desId) 
	{
		// Handler for shipment request, allocate drone, opens and order, and assign to drone
		// errors are printed to system.out, and reported to client.
		Client sourceClient = clientList.get(sourceId-1);
		Client desClient = clientList.get(desId-1);
		
		// Find Available Drone
		int ShipmentDroneIndex = this.getAvailableDroneIndex();
		if(ShipmentDroneIndex == -1)
		{
		  this.DronePostSystem.transferMsgToClient(sourceId, "Cannot Find Available Drone - Please try later!");
		  return;
		}
		Drone curDrone =droneList.get(ShipmentDroneIndex);
		
		// Build the Order object
		Calendar curTime = Calendar.getInstance();// get time of order
		Order newOrder = new Order (orderList.size(),sourceClient.getClientID(),desClient.getClientID(),curTime.getTime());
		this.addOrder(newOrder);
		System.out.println("Shipment Request recived on: " + curTime.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().toString() + ", From:" +sourceClient.getClientName()+" ID="+sourceClient.getClientID() + " To:" + desClient.getClientName()+" ID="+desClient.getClientID() );
		
		//Dispatch drone to shipment
		curDrone.performShipment(newOrder);

	}
	
	public void sendMsgToClient(int clientID, String curMsg)
	{
		// used to send messages to clients using the simulated DronePostSystem API.
		// on operational system will forward the message according to requested client ID 
		this.DronePostSystem.transferMsgToClient(clientID, curMsg);
	}
	
	private int getAvailableDroneIndex()
	{
		// Search for next available drone, return drone index at dronelist, -1 if not found
		for(int i=0; i<droneList.size();i++)
		{
		  Drone curDrone = droneList.get(i);
		  if(curDrone.isAvailable())
			  return i;
		}
		return -1;
	}

}
