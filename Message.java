public class Message {
	private String wrongAddress = " Wrong Address ! ";
	private String done = " Order has received ";
	private String reacDest = " Reaches destination ";
	private String reacOrig = " Reaches origin ";
	private String message;

	

	public Message(String str) {
		message = str;
	}
	
	public String getMessage() {
		return message;
	}

	 
	public String getWrongAddress() {
		return wrongAddress;
	}
	
	public String getDone() {
		return done;
	}
	
	public String getReacDest() {
		return reacDest;
	}
	
	public String getReacOrig() {
		return reacOrig;
	}	
}
