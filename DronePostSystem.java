import java.time.*;
import java.util.ArrayList;

public class DronePostSystem 
{
	
	/**
	 * DronePostSystem Simulate the System Environment for the dronepost product
	 * the simulation consist of one server and one client
	 * the class also contain Arraylist of the Clients so to simulate msg exchange between the server and the clients
	 * **/

	public PostServer postServer = new PostServer(this);
	public ClientGUI Subscriber = new ClientGUI(this);

	private ArrayList<Client> SystemClients = this.postServer.getClientList();
	
	DronePostSystem(){};
	
	public void addClient(Client client)
	{
		SystemClients.ensureCapacity(SystemClients.size()+1);
		SystemClients.add(client);
		this.postServer.addClient(client);
	}
	
	public void transferMsgToClient(int clientID, String curMsg)
	//Simulate the passing of messages in the dronepost system
	{
		if(clientID == getGUIClientId(Subscriber))
			// Check if required message destination is the client GUI
			// If yes, then send the message to display on screen.
		{
			Subscriber.resultArea.append(curMsg);
		}
		else
		{
			// if required destination is simulated client, simulate the message passing to client 
			String destinationString = SystemClients.get(clientID-1).toString();
			System.out.println("Message Sending Simulation!!");
			System.out.println("Transfering message to:"+destinationString+"  Message Sent:" + curMsg);
		}
		
	}
	
	
	public int getGUIClientId(ClientGUI gui)
	{
		int resultID = gui.myClient.getClientID();
		if (resultID >= 0)
			return resultID;
		return -1;
	}
	
	
}





