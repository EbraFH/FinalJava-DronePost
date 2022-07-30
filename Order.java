
import java.util.Calendar;
import java.util.Date;



public class Order {
	private int orderID;
	private int srcClientID;
	private int desClientID;
	private Date orderDate;


	
	public Order() {} //Default Ctor 

	public Order(int orderID, int srcClientID, int destClientID, Date orderDate) {
		this.orderID = orderID;
		this.srcClientID = srcClientID;
		this.desClientID = destClientID;
		this.orderDate = orderDate;
	}
	
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getSrcClientID() {
		return srcClientID;
	}
	public void setSrcClientID(int srcClientID) {
		this.srcClientID = srcClientID;
	}
	public int getDesClientID() {
		return desClientID;
	}
	public void setDestClientID(int destClientID) {
		this.desClientID = destClientID;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", srcClientID=" + srcClientID + ", destClientID=" + desClientID
				+ ", orderDate=" + orderDate + "]";
	}

}
