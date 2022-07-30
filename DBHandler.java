import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBHandler extends ConnectionDB {
	private  PreparedStatement preStmt;
	private DronePostSystem DronePostSystem;
	
	public DBHandler(){
		super();
	}
	
	public DBHandler(DronePostSystem DronePostSystem){
		super();
		this.DronePostSystem = DronePostSystem;
	}
	
	public DronePostSystem getDronePostSystem() {
		return DronePostSystem;
	}

	public void setDronePostSystem(DronePostSystem dronePostSystem) {
		DronePostSystem = dronePostSystem;
	}

	public void addClient(Client client)  {
		// Method inserts client into the DB
			Connection conn;
			String query = "insert into client (clientID, clientName, clientEmail, clientPhoneNumber, clientAddress, shipmentsLeft)" + "values (?, ?, ?, ?, ?)";
			try {
				conn = ConnectionDB.getConn();
				this.preStmt = (PreparedStatement) conn.prepareStatement(query);
				this.preStmt.setInt(1, client.getClientID());
				this.preStmt.setString(2, client.getClientName());
				this.preStmt.setString(3, client.getClientEmail());
				this.preStmt.setString(4, client.getClientPhoneNumber());
				this.preStmt.setString(5, client.getClientAddress());
				this.preStmt.setInt(6, client.getShipmentsLeft());
				this.preStmt.execute();
			}
			catch (SQLException e) {
				System.out.println(e);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public void addClient(ArrayList<Client> clientList){
	// Method inserts client into the DB
		Connection conn;
		String query = "insert into client (clientID, clientName, clientEmail, clientPhoneNumber, clientAddress, shipmentsLeft)" + "values (?, ?, ?, ?, ?, ?)";
		try {
			conn = ConnectionDB.getConn();
			this.preStmt = conn.prepareStatement(query);
			this.preStmt.setInt(1, clientList.get(clientList.size()-1).getClientID());
			this.preStmt.setString(2, clientList.get(clientList.size()-1).getClientName());
			this.preStmt.setString(3, clientList.get(clientList.size()-1).getClientEmail());
			this.preStmt.setString(4, clientList.get(clientList.size()-1).getClientPhoneNumber());
			this.preStmt.setString(5, clientList.get(clientList.size()-1).getClientAddress());
			this.preStmt.setInt(6, clientList.get(clientList.size()-1).getShipmentsLeft());
			this.preStmt.execute();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void addDrone(Drone drone) 
	{
		// Method add Drone to DB
		Connection conn;
		String query = "insert into drone (droneID, isAvailable, battaryPercentage)" + "values (?, ?, ?)";
		try {
			conn = ConnectionDB.getConn();
			this.preStmt = (PreparedStatement) conn.prepareStatement(query);
			this.preStmt.setInt(1, drone.getDroneID());
			this.preStmt.setBoolean(2, drone.isAvailable());
			this.preStmt.setDouble(3, drone.getBattaryPercentage());
			this.preStmt.execute(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addDrone(ArrayList<Drone> droneList) {
		// Method add Drone to DB
		Connection conn;
		String query = "insert into drone (droneID,  isAvailable, battaryPercentage)" + "values (?, ?, ?)";
		try {
			conn = ConnectionDB.getConn();
			this.preStmt = (PreparedStatement) conn.prepareStatement(query);
			this.preStmt.setInt(1, droneList.get(droneList.size()-1).getDroneID());
			this.preStmt.setBoolean(2, droneList.get(droneList.size()-1).isAvailable());
			this.preStmt.setDouble(3, droneList.get(droneList.size()-1).getBattaryPercentage());
			this.preStmt.execute(); 
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	public void addOrder(Order order) {
		// Method add order to DB
		Connection conn;
		try {
			conn = ConnectionDB.getConn();
			String query = "insert into orders (orderID, srcClientID, desClientID, orderDate)" + "values (?, ?, ?, ?)";
			this.preStmt = (PreparedStatement) conn.prepareStatement(query);
			this.preStmt.setInt(1, order.getOrderID());
			this.preStmt.setInt(2, order.getSrcClientID());
			this.preStmt.setInt(3, order.getDesClientID());
			this.preStmt.setObject(2, order.getOrderDate());
			this.preStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addOrder(ArrayList<Order> orderList) {
		// Method add order to DB
		Connection conn;
		String query = "insert into orders (orderID, srcClientID, desClientID, orderDate)" + "values (?, ?, ?, ?)";
		try {
			conn = ConnectionDB.getConn();
			this.preStmt = conn.prepareStatement(query);
			this.preStmt.setInt(1, orderList.get(orderList.size()-1).getOrderID());
			this.preStmt.setInt(2, orderList.get(orderList.size()-1).getSrcClientID());
			this.preStmt.setInt(3, orderList.get(orderList.size()-1).getDesClientID());
			this.preStmt.setObject(4, orderList.get(orderList.size()-1).getOrderDate());
			this.preStmt.execute();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public ArrayList<Order> getClientOrderHistory(Client client) {
		
		ArrayList<Order> HisOrderList =  new ArrayList<Order>();
		int orderID, srcClientID, desClientID;
		Date orderDate;
		Statement stmt;
		ResultSet rs;
		Connection conn;

		try{
			conn = ConnectionDB.getConn();
			String query ="SELECT * from orders";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				orderID = rs.getInt("orderID");
				srcClientID = rs.getInt("srcClientID");
				desClientID = rs.getInt("desClientID");
				orderDate = rs.getDate("orderDate");
					
				if(srcClientID == client.getClientID()) {
					HisOrderList.ensureCapacity(HisOrderList.size()+1);
					HisOrderList.add(new Order(orderID,srcClientID,desClientID,orderDate));
				}
			}
			rs.close();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return HisOrderList;
	}
	
	public ArrayList<Client> getClientList()
	{
		ArrayList<Client> clientList =  new ArrayList<Client>();
		int clientID,shipmentsLeft;
		String clientName,clientEmail,clientPhoneNumber,clientAddress;
		Statement stmt;
		ResultSet rs;
		Connection conn;

		//Getting the user order history from Orders DB
		try{
			conn = ConnectionDB.getConn();
			String query ="SELECT * from client";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				clientID = rs.getInt("clientID");
				clientName = rs.getString("clientName");
				clientEmail = rs.getString("clientEmail");
				clientPhoneNumber = rs.getString("clientPhoneNumber");
				clientAddress = rs.getString("clientAddress");
				shipmentsLeft = rs.getInt("shipmentsLeft");
					
				clientList.ensureCapacity(clientList.size()+1);
				clientList.add(new Client(clientID,clientName,clientEmail,clientPhoneNumber,clientAddress,shipmentsLeft));
			}
			rs.close();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return clientList;
	}
	
	public ArrayList<Drone> getDroneList()
	{
		ArrayList<Drone> droneList =  new ArrayList<Drone>();
		int droneID;
		boolean isAvailable;
		double battaryPercentage;
		Statement stmt;
		ResultSet rs;
		Connection conn;

		//Getting the user order history from Orders DB
		try{
			conn = ConnectionDB.getConn();
			String query ="SELECT * from drone";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				droneID = rs.getInt("droneID");
				isAvailable = rs.getBoolean("isAvailable");
				battaryPercentage = rs.getDouble("battaryPercentage");
				
				droneList.ensureCapacity(droneList.size()+1);
				droneList.add(new Drone(droneID,battaryPercentage,isAvailable,getDronePostSystem()));
			}
			rs.close();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return droneList;
	}
	
	public ArrayList<Order> getOrderList()
	{
		ArrayList<Order> orderList =  new ArrayList<Order>();
		int orderID, srcClientID, desClientID;
		Date orderDate;
		Statement stmt;
		ResultSet rs;
		Connection conn;
		try{
			conn = ConnectionDB.getConn();
			String query ="SELECT * from orders";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				orderID = rs.getInt("orderID");
				srcClientID = rs.getInt("srcClientID");
				desClientID = rs.getInt("desClientID");
				orderDate = rs.getDate("orderDate");
					
				orderList.ensureCapacity(orderList.size()+1);
				orderList.add(new Order(orderID,srcClientID,desClientID,orderDate));
			}
			rs.close();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return orderList;
	}
	
}