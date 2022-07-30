

import java.time.*;
import java.util.ArrayList;

public class Client {

	private int clientID=-1;
	private String clientName;
	private String clientEmail;
	private String clientPhoneNumber;
	private String clientAddress;
	private int shipmentsLeft = 0;
	
	Client(){}
	
	public Client(String clientName, String clientEmail, String clientPhoneNumber, String clientAddress) {//Ctor
		this.clientName = clientName;
		this.clientEmail = clientEmail;
		this.clientPhoneNumber = clientPhoneNumber;
		this.clientAddress = clientAddress;
	}

	public Client(int clientID, String clientName, String clientEmail, String clientPhoneNumber, String clientAddress) {//Ctor
		this.clientID = clientID;
		this.clientName = clientName;
		this.clientEmail = clientEmail;
		this.clientPhoneNumber = clientPhoneNumber;
		this.clientAddress = clientAddress;
	}
	
	public Client(int clientID, String clientName, String clientEmail, String clientPhoneNumber, String clientAddress, int Shipments) {//Ctor
		this.clientID = clientID;
		this.clientName = clientName;
		this.clientEmail = clientEmail;
		this.clientPhoneNumber = clientPhoneNumber;
		this.clientAddress = clientAddress;
		this.shipmentsLeft = Shipments;
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public String getClientPhoneNumber() {
		return clientPhoneNumber;
	}

	public void setClientPhoneNumber(String clientPhoneNumber) {
		this.clientPhoneNumber = clientPhoneNumber;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	

	public int getShipmentsLeft() {
		return shipmentsLeft;
	}

	public void setShipmentsLeft(int shipmentsLeft) {
		this.shipmentsLeft = shipmentsLeft;
	}

	@Override
	public String toString() {
		return "Client [clientID=" + clientID + ", clientName=" + clientName + ", clientEmail=" + clientEmail
				+ ", clientPhoneNumber=" + clientPhoneNumber + ", clientAddress=" + clientAddress + ", shipmentsLeft="
				+ shipmentsLeft + "]";
	}
	

}
