import java.time.*;

/***
 * Drone Class 
 * Implements the simulation of physical drone operation
 * include methods for package delivery and messaging to customers
 * */
public class Drone extends Thread {
	private int droneID;
	private Order curOrder;
	private double battaryPercentage;
	private boolean isAvailable;
	private DronePostSystem DronePostSystem;
	
	public int secondsCounter;
	public boolean stop = false; //stop=true means: drone is disabled
	public boolean isCharged = false; // false means, drone has not been charged
	public int orderEstimatedTime =4;
	public int internalCounter = 0;
	
	private long timeToSource = 5; // for timer usage, in seconds for readability -> need to multiply by 1000 for timer usage  
	private long timeToDestination = 4; // for timer usage, in seconds for readability -> need to multiply by 1000 for timer usage
	private int consumptionRate = 5;// amount of battaryPercentage consumed each second of operation
	
	public ShipmentStage shipmentStage = ShipmentStage.IDLE;
	
	Drone(){}
	
	
	Drone(DronePostSystem DronePostSystem) // Ctor for Empty initialization only connection to system
	{
		this.droneID = -1;
		this.curOrder = null;
		this.battaryPercentage = 100.0;
		this.isAvailable = true;
		this.DronePostSystem = DronePostSystem;
	}
	
	
	Drone(int droneId,DronePostSystem DronePostSystem)// Ctor for Drone intializaton with ID and connection to system
	{
		this.droneID = droneId;
		this.curOrder = null;
		this.battaryPercentage = 100.0;
		this.isAvailable = true;
		this.DronePostSystem = DronePostSystem;
	}
	
	


	public Drone(int droneID, double battaryPercentage, boolean isAvailable, DronePostSystem dronePostSystem) {
		this.droneID = droneID;
		this.battaryPercentage = battaryPercentage;
		this.isAvailable = isAvailable;
		DronePostSystem = dronePostSystem;
	}


	public void performShipment(Order requstedOrder) {
		this.curOrder = requstedOrder;
		Client srcClient = DronePostSystem.postServer.clientList.get(requstedOrder.getSrcClientID()-1);
		Client destClient = DronePostSystem.postServer.clientList.get(requstedOrder.getDesClientID()-1);
		this.isAvailable = false;
		this.gotoSource(srcClient);
		this.gotoDestination(destClient,srcClient);
		this.DronePostSystem.postServer.sendMsgToClient(srcClient.getClientID(),"Package arrived at Destination:"+destClient.getClientName() + "at ADDRESS: "+destClient.getClientAddress()+"\n" );
		this.DronePostSystem.postServer.sendMsgToClient(destClient.getClientID(),"Package arrived to you, From:" +srcClient.getClientName() +"\n");
		this.curOrder = null;
		this.isAvailable = true;
	}
	
	private void gotoSource(Client srcClient)
	{
	  System.out.println("Heading to Source:" + srcClient.getClientName());
	  this.DronePostSystem.postServer.sendMsgToClient(srcClient.getClientID(),"Shipment Approved, Drone inbound\n");
	  System.out.println("Arrived at Source:" + srcClient.getClientName());	
	}
	
	
	
	private void gotoDestination(Client destClient,Client srcClient)
	{
		System.out.println("Heading to Destination:" + destClient.getClientName());
		this.DronePostSystem.postServer.sendMsgToClient(srcClient.getClientID(),"Package picked, heading to Destination:"+destClient.getClientName() + "at ADDRESS: "+destClient.getClientAddress()+"\n" );
		this.DronePostSystem.postServer.sendMsgToClient(destClient.getClientID(),"Package Inbound to you, From:" +srcClient.getClientName() +"\n");
		//this.setOrder(curOrder);
		System.out.println("Arrived at Destination:" + destClient.getClientName()+"\n");
	}
	
	private void sendMsgToClient(int ClientId, String Message)
	{
	  
	
	}
	
	//	Assign the Drone for an order.
	//start(): start new thread, seconds counter (timer) will start from zero.
	//resumeThread(): thread (timer) resumes from the last point that it was ended.
	public void setOrder(Order order){
		this.curOrder = order;
//		//if(this.isAvailable()){
//			//this.isAvailable=false;
//			if(secondsCounter==0 && !isCharged){
//				this.shipmentStage = ShipmentStage.TO_SOURCE;
//				start();
//			} else if (isCharged || secondsCounter != 0){
//				resumeThread();
//			}
//		//} 
	}
	
	public Order getOrder(){
		return curOrder;
	}
	
	
	public int getTimer(){
		return secondsCounter;
	}
	
	//Set estimated time for order
	public void setEstimatedTime(int orderEstimatedTime){
		this.orderEstimatedTime = orderEstimatedTime;
	}
	
	
	//Timer Thread
	@Override
	public void run() {
		try{
			while(stop==false){ // while drone is working
				
				internalCounter++; // Will count from zero and ended when thread is stopped
				secondsCounter++; // Will count from the last value of the counter when the thread was stopped
				this.battaryPercentage = this.battaryPercentage-1;//Full charged battery can work 10 seconds (for short time testing)
				
				//if battery is empty, stop the thread and charge the battery
				if(battaryPercentage <= 0){
					System.out.println("Battery low");
					batteryCharge();
				} else {
					sleep(1000);

					System.out.println("Seconds passed: " + secondsCounter + ", Battery: " + battaryPercentage);// + "%");
				}
				
				//if the seconds counter reach the estimated time then the shipment was succeeded -> stop thread
				if(internalCounter == orderEstimatedTime){
					if(shipmentStage == ShipmentStage.TO_SOURCE)
					{
						System.out.println("Arrived at Source:" + curOrder.getSrcClientID());
					}
					System.out.println("Shipment succeeeded!");
					internalCounter = 0;
					stopThread();
				}
				
				//if stop is true, the thread will be sat to WAIT mode
				synchronized (this) {
					while(stop == true){
						wait();
					}
				}
			}
		}
		catch(InterruptedException e){}
	}
	
	public synchronized void stopThread() {
		stop = true;
	}
	
	public synchronized void resumeThread() {
		stop = false;
		notify();
	}
	
	//Charge the battery and change the relevant parameters and print a message to the Console
	public synchronized void batteryCharge(){
		stop = true;
		try {
			sleep(2000);
		} catch (InterruptedException e) {}
		
		secondsCounter = 0;
		battaryPercentage = 10;
		isCharged = true;
		System.out.println("Battery has been charged: " + battaryPercentage);
	}

	public int getDroneID() {
		return droneID;
	}

	public void setDroneID(int droneID) {
		this.droneID = droneID;
	}

	public Order getCurOrder() {
		return curOrder;
	}

	public void setCurOrder(Order curOrder) {
		this.curOrder = curOrder;
	}

	public double getBattaryPercentage() {
		return battaryPercentage;
	}

	public void setBattaryPercentage(float battaryPercentage) {
		this.battaryPercentage = battaryPercentage;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "Drone [droneID=" + droneID + ", curOrder=" + curOrder + ", battaryPercentage=" + battaryPercentage
				+ ", isAvailable=" + isAvailable + "]";
	}
	
}

