import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class ConnectionDB {
	public static Connection conn;
	
	public static Connection getConn() throws ClassNotFoundException
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalproj","root","");
			System.out.println("Connected...");
			return conn;
			
		}catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}
	
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
 
