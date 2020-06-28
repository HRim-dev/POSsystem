package pos.domainlayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnect {
	
	//DB 객체를 위한 변수
	private Connection myConnection;
	private Statement myStatement;
	
	//DB연결
	public DBConnect(String dbFileName) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				myConnection=DriverManager.getConnection("jdbc:ucanaccess://"+dbFileName);
				myStatement=myConnection.createStatement();
			}catch(Exception e) {
				e.printStackTrace();
			}
	 }

	public Statement getMyStatement() {
		return myStatement;
	}
	
	
		
}
